package aiwd.artificialNeuralNetwork.dataNormalization;

import aiwd.artificialNeuralNetwork.DataVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MinMaxDataNormalization
{

    private List<MinMaxForColumn> columnsData;
    private List<Integer> columnsToNormalize;
    private boolean normalized = false;

    public MinMaxDataNormalization(Integer... columnsToNormalize){
        this.columnsToNormalize = Arrays.asList(columnsToNormalize);

    }

    public List<DataVector> denormalizeData(List<DataVector> normalizedData){
        if(validateDataToDenormalization(normalizedData)) {
            return new ArrayList<>();
        }
        return denormalize(normalizedData);
    }

    private List<DataVector> denormalize(List<DataVector> normalizedData) {
        List<DataVector> denormalizedData = new ArrayList<>();
        for(DataVector normalizedVector: normalizedData){
            NormalizedData denormalizedVector = denormalizeVector(normalizedVector);
            denormalizedData.add(denormalizedVector);
        }
        return denormalizedData;
    }

    private NormalizedData denormalizeVector(DataVector normalizedVector) {
        NormalizedData denormalizedVector = new NormalizedData();
        for(int i = 0; i < columnsToNormalize.size(); i++){
            if(columnsToNormalize.contains(i)){
                denormalizedVector.addData(normalizedVector.getValueList().get(i)
                        * (columnsData.get(i).getMax() - columnsData.get(i).getMin()) + columnsData.get(i).getMin());
            }else{
                denormalizedVector.addData(normalizedVector.getValueList().get(i));
            }
        }
        return denormalizedVector;
    }

    public List<DataVector> normalizeData(List<DataVector> dataVectors){
        if (validateDataToNormalization(dataVectors)) {
            return new ArrayList<>();
        }
        createColumnsData(dataVectors);
        initializeColumnsData(dataVectors);
        this.normalized = !this.normalized;
        return normalize(dataVectors);
    }

    private boolean validateDataToNormalization(List<DataVector> dataVectors) {
        return dataVectors == null || dataVectors.isEmpty() || columnsToNormalize.isEmpty() || normalized;
    }

    private boolean validateDataToDenormalization(List<DataVector> dataVectors) {
        return dataVectors == null || dataVectors.isEmpty() || columnsToNormalize.isEmpty() || !normalized;
    }

    private List<DataVector> normalize(List<DataVector> dataVectors) {
        List<DataVector> normalizedData = new ArrayList<>();
        for (DataVector dataVector : dataVectors) {
            NormalizedData newVector = countMinMaxNormalizationForVector(dataVector);
            normalizedData.add(newVector);
        }
        return normalizedData;
    }

    private NormalizedData countMinMaxNormalizationForVector(DataVector dataVector) {
        NormalizedData newVector = new NormalizedData();
        for (int i = 0; i < dataVector.getValueList().size(); i++){
            if(columnsToNormalize.contains(i)) {
                newVector.addData(dataVector.getValueList().get(i) - columnsData.get(i).getMin()
                        / columnsData.get(i).getMax() - columnsData.get(i).getMin());

            }else {
                newVector.addData(dataVector.getValueList().get(i));
            }
        }
        return newVector;
    }

    private void initializeColumnsData(List<DataVector> dataVectors) {
        for (DataVector dataVector : dataVectors) {
            List<Double> row = dataVector.getValueList();
            for (Integer indexOfColumn: columnsToNormalize){
                columnsData.get(indexOfColumn)
                        .setMin(row.get(indexOfColumn))
                        .setMax(row.get(indexOfColumn));
            }
        }
    }

    private void createColumnsData(List<DataVector> dataVectors) {
        columnsData = new ArrayList<>();
        DataVector firstDataVector = dataVectors.get(0);
        for (Integer indexOfColumn: columnsToNormalize){
            columnsData.add(new MinMaxForColumn(firstDataVector.getValueList().get(indexOfColumn)
                    ,firstDataVector.getValueList().get(indexOfColumn)));
        }
    }
}
