package aiwd.loader;

import aiwd.model.DataRow;
import aiwd.model.EventData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DataLoader {

    private static Log LOGGER = LogFactory.getLog(DataLoader.class);

    public static LinkedList<DataRow> loadDataFromFile(String fileName) {
        LinkedList<DataRow> entryList = new LinkedList<>();

        ArrayList<String> allEntries = readCsvDataToList(fileName);

        for (int i = 0; i < allEntries.size(); i = i + 2) {
            String firstEntry = allEntries.get(i);
            String[] firstEntrySplitted = firstEntry.split(",");
            String secondEntry = allEntries.get(i + 1);
            String[] secondEntrySplitted = secondEntry.split(",");

            entryList.add(new DataRow(firstEntrySplitted, secondEntrySplitted));
        }
        return entryList;
    }

    private static ArrayList<String> readCsvDataToList(String fileName) {
        ArrayList<String> allEntries = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String csvDataLine;

            while ((csvDataLine = bufferedReader.readLine()) != null) {
                allEntries.add(csvDataLine);
            }

            bufferedReader.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return allEntries;
    }

    public static LinkedList<EventData> loadEventData(String fileName) {
        LinkedList<EventData> eventList = new LinkedList<>();
        ArrayList<String> allEntries = readCsvDataToList(fileName);
        allEntries.forEach(s -> eventList.add(new EventData(s.split(","))));
        return eventList;
    }
}