package aiwd;

import aiwd.artificialNeuralNetwork.DataVector;
import aiwd.artificialNeuralNetwork.LearningResults;
import aiwd.artificialNeuralNetwork.NeuralNetwork;
import aiwd.artificialNeuralNetwork.NeuralNetworkProperties;
import aiwd.artificialNeuralNetwork.dataNormalization.MinMaxDataNormalization;
import aiwd.loader.DataLoader;
import aiwd.model.DataRow;
import aiwd.model.EventData;
import aiwd.model.LearningData;
import aiwd.model.ResultData;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AppRunner {

    public static void main(String[] args) {

        LinkedList<DataRow> dataRows = DataLoader.loadDataFromFile("data.csv");
        LinkedList<EventData> eventDataList = DataLoader.loadEventData("events.csv");

        List<LearningData> learningData = prepareLearningDataList(dataRows, eventDataList);
        List<DataVector> resultData = prepareResultList(learningData);
        List<DataVector> ld = new ArrayList<>(learningData);

//        ld.remove(ld.size() - 1);
//        resultData.remove(0);

        NeuralNetworkProperties properties = new NeuralNetworkProperties();
        properties.setNumberOfInputs(5);
        properties.setNumberOfHiddenLayers(2);
        properties.setNumberOfNeuronsPerLayer(2);
        properties.setNumberOfOutputs(1);
        properties.setQ(0.02d);
        properties.setBiasValue(1.0d);
        NeuralNetwork neuralNetwork = new NeuralNetwork(properties);

        MinMaxDataNormalization normalization = new MinMaxDataNormalization(0,1,2,3);
        List<DataVector> learningDataNormalized = normalization.normalizeData(ld);

        for(int i = 0; i < 10000; i ++) {
           LearningResults results = neuralNetwork.learn(learningDataNormalized, resultData);
           System.out.println("Iteration " + i + " result " + results.getResult());
        }

        DataVector noEventDataVector = new DataVector() {
            @Override
            public List<Double> getValueList() {
                return Arrays.asList(6.0,20.0,0.0,1.0,0.0);
            }
        };

        DataVector eventDataVector = new DataVector() {
            @Override
            public List<Double> getValueList() {
                return Arrays.asList(4.0,13.0,40.0,2.0,0.0);
            }
        };

        Double noevent = neuralNetwork.evaluate(normalization.normalizeDataVector(noEventDataVector)).get(0);
        Double event = neuralNetwork.evaluate(normalization.normalizeDataVector(eventDataVector)).get(0);
    }

    private static List<DataVector> prepareResultList(List<LearningData> learningData) {
        return learningData
                .stream()
                .map(l -> new ResultData(l.getDateAndTime(), l.getInCount(), l.getOutCount(), l.getIsEvent()))
                .collect(Collectors.toList());
    }

    private static List<LearningData> prepareLearningDataList(LinkedList<DataRow> dataRows, LinkedList<EventData> eventDataList) {
        Set<LearningData> learningDataSet = new HashSet<>();
        for (DataRow dataRow : dataRows) {
            for (EventData eventData : eventDataList) {
                if (dataRow.getDateAndTime().compareTo(eventData.getBeignEventDateAndTime()) == 0) {
                    learningDataSet.add(new LearningData(dataRow.getDateAndTime(), dataRow.getInCount(), dataRow.getOutCount(), 1));
                }
            }
            learningDataSet.add(new LearningData(dataRow.getDateAndTime(), dataRow.getInCount(), dataRow.getOutCount(), 0));
        }
        return learningDataSet.stream().collect(Collectors.toList());
    }
}
