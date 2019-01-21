package aiwd;

import aiwd.artificialNeuralNetwork.DataVector;
import aiwd.artificialNeuralNetwork.NeuralNetwork;
import aiwd.artificialNeuralNetwork.NeuralNetworkProperties;
import aiwd.loader.DataLoader;
import aiwd.model.DataRow;
import aiwd.model.EventData;
import aiwd.model.LearningData;
import aiwd.model.ResultData;

import java.util.*;
import java.util.stream.Collectors;

public class AppRunner {

    public static void main(String[] args) {
        LinkedList<DataRow> dataRows = DataLoader.loadDataFromFile("data.csv");
        LinkedList<EventData> eventDataList = DataLoader.loadEventData("events.csv");

        List<LearningData> learningData = prepareLearningDataList(dataRows, eventDataList);
        List<DataVector> resultData = prepareResultList(learningData);
        List<DataVector> ld = new ArrayList<>(learningData);

        //learningData.remove(learningData.size() - 1);
        //resultData.remove(0);

        NeuralNetworkProperties properties = new NeuralNetworkProperties();
        properties.setNumberOfInputs(2);
        properties.setNumberOfLayers(1);
        properties.setNumberOfNeuronsPerLayer(3);
        properties.setNumberOfOutputs(1);
        properties.setQ(0.05d);
        properties.setBiasValue(1.0d);

        NeuralNetwork neuralNetwork = new NeuralNetwork(properties);

        for(int i = 0; i < 10000; i ++) {
            neuralNetwork.learn(ld, resultData);
        }

        System.out.println("dataRows = " + dataRows);

        neuralNetwork.learn(ld, resultData);
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
