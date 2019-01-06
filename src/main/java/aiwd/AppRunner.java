package aiwd;

import aiwd.loader.DataLoader;
import aiwd.model.DataRow;

import java.util.LinkedList;

public class AppRunner {

    public static void main(String[] args) {
        LinkedList<DataRow> dataRows = DataLoader.loadDataFromFile("data.csv");
        System.out.println("dataRows = " + dataRows);
    }
}
