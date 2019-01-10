package aiwd;

import aiwd.artificialNeuralNetwork.Network;
import aiwd.loader.DataLoader;
import aiwd.model.DataRow;
import aiwd.model.EventData;
import aiwd.model.LearningData;

import java.util.*;
import java.util.stream.Collectors;

public class AppRunner {

    public static void main(String[] args) {
        LinkedList<DataRow> dataRows = DataLoader.loadDataFromFile("data.csv");
        LinkedList<EventData> eventDataList = DataLoader.loadEventData("events.csv");
        Set<LearningData> learningDataSet = new HashSet<>();
        for(EventData eventData:eventDataList){
            for(DataRow dataRow:dataRows){
                if(dataRow.getDateAndTime().compareTo(eventData.getBeignEventDateAndTime()) == 0){
                    learningDataSet.add(new LearningData(dataRow.getDateAndTime(),dataRow.getInCount(),dataRow.getOutCount(),1));

                }else{
                    learningDataSet.add(new LearningData(dataRow.getDateAndTime(),dataRow.getInCount(),dataRow.getOutCount(),0));
                }
            }
        }


        Network neuralNetwork = new Network();

        neuralNetwork.learn(learningDataSet.stream().collect(Collectors.toList()));

        System.out.println("dataRows = " + dataRows);
    }
}
