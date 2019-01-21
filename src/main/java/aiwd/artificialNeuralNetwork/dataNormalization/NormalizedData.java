package aiwd.artificialNeuralNetwork.dataNormalization;


import aiwd.artificialNeuralNetwork.DataVector;

import java.util.ArrayList;
import java.util.List;

public class NormalizedData implements DataVector {

    private List<Double> data = new ArrayList<>();

    @Override
    public List<Double> getValueList() {
        return null;
    }

    public void addData(Double normalizedData){
        this.data.add(normalizedData);
    }
}
