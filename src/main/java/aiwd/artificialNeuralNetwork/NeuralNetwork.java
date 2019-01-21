package aiwd.artificialNeuralNetwork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NeuralNetwork {

    private static Log LOGGER = LogFactory.getLog(NeuralNetwork.class);

    private double weightInitialUpperBound;

    private double weightInitialLowerBound;

    private double q;

    private double biasValue;

    private ArrayList<Neuron> inputLayer = new ArrayList<>();

    private LinkedList<ArrayList<Neuron>> network = new LinkedList<>();

    private ArrayList<Neuron> outputLayer = new ArrayList<>();

    public NeuralNetwork(NeuralNetworkProperties properties) {
        initializeInputLayer(properties.getNumberOfInputs());
        initializeNeuralNetwork(properties.getNumberOfLayers(), properties.getNumberOfNeuronsPerLayer());
        initializeOutputLayer(properties.getNumberOfOutputs(), properties);
        q = properties.getQ();
        biasValue = properties.getBiasValue();
        weightInitialLowerBound = properties.getWeightInitialLowerBound();
        weightInitialUpperBound = properties.getWeightInitialUpperBound();
        addBiasToNeuralNetwork();
        initializeWeightsForNeuralNetwork();
    }

    private void initializeWeightsForNeuralNetwork() {
        initializeWeights(outputLayer);
        network.forEach(layer -> initializeWeights(layer));
    }

    private void addBiasToNeuralNetwork(){
        network.forEach(layer -> layer.forEach(neuron -> addBias(neuron)));
        outputLayer.forEach(n -> addBias(n));
    }

    private void addBias(Neuron neuron) {
        Neuron bias = new Neuron();
        bias.setOutput(this.biasValue);
        bias.setBias(true);
        neuron.addConnection(bias);
    }

    private void initializeInputLayer(int numberOfNeurons) {
        for (int i = 0; i < numberOfNeurons; i++) {
            inputLayer.add(new Neuron());
        }
    }

    private void initializeOutputLayer(int numberOfOutputNeurons, NeuralNetworkProperties properties) {
        for (int i = 0; i < numberOfOutputNeurons; i++) {
            outputLayer.add(new Neuron());
        }
        if(properties.getNumberOfLayers() <= 0 || properties.getNumberOfNeuronsPerLayer() <=0){
            connectOutputWithInputLayer();
        }
        connectWithOutputLayer();
    }

    private void initializeNeuralNetwork(int numberOfLayers, int numberOfNeuronsPerLayer) {
        for (int i = 0; i < numberOfLayers; i++) {
            network.add(new ArrayList<>());
            createNeuronsOnLayer(i, numberOfNeuronsPerLayer);
            int indexOfPreviousLayer = i - 1;
            connectNeuronsBetweenLayers(indexOfPreviousLayer, i);
        }
    }

    private void createNeuronsOnLayer(int layer, int numberOfNeurons) {
        if (network == null || network.isEmpty()) {
            return;
        }
        for (int i = 0; i < numberOfNeurons; i++) {
            network.get(layer).add(new Neuron());
        }
    }

    private void connectNeuronsBetweenLayers(int previousLayer, int currentLayer) {
        if (previousLayer < 0) {
            connectWithInputLayer(currentLayer);
        } else {
            connectLayers(previousLayer, currentLayer);
        }
    }

    private void connectLayers(int previousLayer, int currentLayer) {
        for (Neuron neuron : network.get(currentLayer)) {
            for (Neuron neronFromPreviousLayer : network.get(previousLayer)) {
                neuron.addConnection(neronFromPreviousLayer);
            }
        }
    }

    private void connectWithInputLayer(int currentLayer) {
        if (inputLayer == null || inputLayer.isEmpty()) {
            return;
        }
        for (Neuron neuron : network.get(currentLayer)) {
            for (Neuron neronFromPreviousLayer : inputLayer) {
                neuron.addConnection(neronFromPreviousLayer);
            }
        }
    }

    private void connectWithOutputLayer() {
        if (network == null || network.isEmpty()) {
            return;
        }
        for (Neuron neuron : outputLayer) {
            for (Neuron neronFromPreviousLayer : network.getLast()) {
                neuron.addConnection(neronFromPreviousLayer);
            }
        }
    }

    private void connectOutputWithInputLayer() {
        if (inputLayer == null || inputLayer.isEmpty()) {
            return;
        }
        for (Neuron neuron : outputLayer) {
            for (Neuron neronFromPreviousLayer : inputLayer) {
                neuron.addConnection(neronFromPreviousLayer);
            }
        }
    }

    private void initializeWeights(List<Neuron> neuronList) {
        neuronList.forEach(neuron -> neuron.initializeWeights(weightInitialLowerBound,weightInitialUpperBound));
    }

    public void provideInputData(List<Double> values) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(values.get(i));
        }
    }

    public void learn(List<DataVector> learningDataVector, List<DataVector> resultDataVector) {
        for (int i = 0; i < learningDataVector.size(); i++) {
            provideInputData(learningDataVector.get(i).getValueList());
            network.forEach(neuronsLayer -> neuronsLayer.forEach(neuron -> neuron.execute()));
            outputLayer.forEach(neuron -> neuron.execute());
            countErrorSignalForOutputLayer(resultDataVector.get(i));
            countErrorSignalForInnerLayers();
            adjustWeights(outputLayer);
            network.forEach(l -> adjustWeights(l));
        }

    }

    private void countErrorSignalForInnerLayers() {
        if(network == null || network.isEmpty()){
            return;
        }
        for (Neuron neuron : network.getLast()) {
            countErrorPropagationForNeuron(neuron, outputLayer);
        }
        for (int i = network.size() - 2; i >= 0; i--) {
            ArrayList<Neuron> neuronsFromCurrentLayer = network.get(i);
            for (Neuron neuron : neuronsFromCurrentLayer) {
                int indexOfNextLayer = i + 1;
                countErrorPropagationForNeuron(neuron, network.get(indexOfNextLayer));
            }
        }
    }

    private void countErrorPropagationForNeuron(Neuron neuron, List<Neuron> neuronsFromNextLayer) {
        List<Connection> connectionsToPreviousLayer = neuronsFromNextLayer.stream()
                .map(n -> n.getConnectionForNeuron(neuron))
                .collect(Collectors.toList());
        double sum = countSumForLayer(connectionsToPreviousLayer);
        neuron.propagateErrorSignal(sum * transferDerivative(neuron.getOutput()));
    }

    private double countSumForLayer(List<Connection> connections) {
        double sum = 0.0d;
        for (Connection connection : connections) {
            sum += connection.getWeight() * connection.getErrorPropagationSignal();
        }
        return sum;
    }

    private void adjustWeights(List<Neuron> neurons) {
        for (Neuron neuron : neurons) {
            neuron.adjustWeights(q);
        }
    }

    private void countErrorSignalForOutputLayer(DataVector resultDataVector) {
        List<Double> targetValueList = resultDataVector.getValueList();
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron neuron = outputLayer.get(i);
            neuron.propagateErrorSignal((neuron.getOutput() - targetValueList.get(i)) * transferDerivative(neuron.getOutput()));
            if(targetValueList.get(i) != 0)
                LOGGER.info("Parameters: "+ targetValueList.get(i) +" Output value: "+ neuron.getOutput());
        }
    }

    private double transferDerivative(double output) {
        return output * (1.0d - output);
    }
}
