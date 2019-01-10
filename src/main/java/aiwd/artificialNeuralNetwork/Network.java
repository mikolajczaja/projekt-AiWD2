package aiwd.artificialNeuralNetwork;

import aiwd.model.LearningData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private static Log LOGGER = LogFactory.getLog(Network.class);

    private List<Neural> neuralListFirstLayer = new ArrayList<>();
    private List<Neural> neuralListSecondLayer = new ArrayList<>();

    private static final double q = 0.05d;

    public Network(){
        neuralListFirstLayer.add(new Neural(3,3));
        neuralListFirstLayer.add(new Neural(3,3));
        neuralListSecondLayer.add(new Neural(2,2));

        neuralListFirstLayer.forEach(n -> n.initializeWeights());
        neuralListSecondLayer.forEach(n -> n.initializeWeights());
    }

    public void learn(List<LearningData> learningDataList){
        for(int i = 0; i<learningDataList.size(); i = i + 2){
            //propagation
            neuralListFirstLayer.get(0).inputValues(learningDataList.get(i).getValues());
            neuralListFirstLayer.get(1).inputValues(learningDataList.get(i+1).getValues());
            neuralListFirstLayer.forEach(n -> n.sigmoid(n.sum()));
            double[] firstLayerResult = new double[2];
            firstLayerResult[0] = neuralListFirstLayer.get(0).getY();
            firstLayerResult[1] = neuralListFirstLayer.get(1).getY();
            neuralListSecondLayer.get(0).inputValues(firstLayerResult);
            neuralListSecondLayer.forEach(n -> n.sigmoid(n.sum()));

            //check the error
            double outputValue = neuralListSecondLayer.get(0).getY();

            LOGGER.info("Parameters: "+ learningDataList.get(i).getValues().toString() +"Output value: "+ outputValue);

            double targetValue = learningDataList.get(i).getIsEvent();

            //backwards propagation (output layer)
            double errorSignalInput = (targetValue - outputValue) * transferDerivative(outputValue);
            neuralListSecondLayer.get(0).setErrorPropagationSingal(errorSignalInput);

            // weight set
            double weights[] = new double[2];
            weights[0] += q * errorSignalInput * firstLayerResult[0];
            weights[1] += q * errorSignalInput * firstLayerResult[1];
            neuralListSecondLayer.get(0).setW(weights);

            //backwards propagation (inner layer)
            double sum = 0.0d;
            for ( int j = 0; j < weights.length; j ++){
               sum += weights[j] * neuralListSecondLayer.get(0).getErrorPropagationSingal();
            }
            neuralListFirstLayer.get(0).setErrorPropagationSingal(sum * transferDerivative(neuralListFirstLayer.get(0).getY()));
            neuralListFirstLayer.get(1).setErrorPropagationSingal(sum * transferDerivative(neuralListFirstLayer.get(1).getY()));

            // weight set
            double w[] = new double[3];
            for (int j = 0; j < neuralListFirstLayer.get(0).getW().length; j++) {
                w = neuralListFirstLayer.get(0).getW();
                w[j] += q * neuralListFirstLayer.get(0).getErrorPropagationSingal() * learningDataList.get(i).getValues()[j];
            }
            neuralListFirstLayer.get(0).setW(w);
            for (int j = 0; j < neuralListFirstLayer.get(1).getW().length; j++) {
                w = neuralListFirstLayer.get(1).getW();
                double c =  q * neuralListFirstLayer.get(1).getErrorPropagationSingal() * learningDataList.get(i).getValues()[j];
                w[j] += q * neuralListFirstLayer.get(1).getErrorPropagationSingal() * learningDataList.get(i).getValues()[j];
            }
            neuralListFirstLayer.get(1).setW(w);
        }

    }

    private double transferDerivative(double output){
        return output * (1 - output);
    }


}
