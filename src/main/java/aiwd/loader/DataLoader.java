package aiwd.loader;

import aiwd.model.DataRow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DataLoader {

    private static Log LOGGER = LogFactory.getLog(DataLoader.class);

    public static LinkedList<DataRow> loadDataFromFile(String fileName) {

        BufferedReader bufferedReader;
        LinkedList<DataRow> entryList = new LinkedList<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String csvDataLine;
            ArrayList<String> allEntries = new ArrayList<>();

            while ((csvDataLine = bufferedReader.readLine()) != null) {
                allEntries.add(csvDataLine);
            }

            for (int i = 0; i < allEntries.size(); i = i + 2) {
                String firstEntry = allEntries.get(i);
                String[] firstEntrySplitted = firstEntry.split(",");
                String secondEntry = allEntries.get(i + 1);
                String[] secondEntrySplitted = secondEntry.split(",");

                entryList.add(new DataRow(firstEntrySplitted, secondEntrySplitted));
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }
        return entryList;
    }
}