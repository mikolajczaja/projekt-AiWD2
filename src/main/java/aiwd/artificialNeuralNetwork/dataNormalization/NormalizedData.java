package aiwd.artificialNeuralNetwork.dataNormalization;


import aiwd.artificialNeuralNetwork.DataVector;

import java.util.ArrayList;
import java.util.List;

 class NormalizedData implements DataVector {

    private List<Double> data = new ArrayList<>();

    NormalizedData(){}

    @Override
    public List<Double> getValueList() {
        return data;
    }

    public void addData(Double normalizedData){
        this.data.add(normalizedData);
    }
}
