package aiwd.artificialNeuralNetwork.dataNormalization;

import aiwd.artificialNeuralNetwork.DataVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MinMaxDataNormalization
{

    private List<MinMaxForColumn> columnsData;
    private List<Integer> columnsToNormalize;

    private MinMaxDataNormalization(){}

    public MinMaxDataNormalization(Integer... columnsToNormalize){
        this.columnsToNormalize = Arrays.asList(columnsToNormalize);

    }

    public List<DataVector> denormalizeData(List<DataVector> normalizedData){
        if(validateData(normalizedData)) {
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
        for(int i = 0; i < normalizedVector.getValueList().size(); i++){
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
        if (validateData(dataVectors)) {
            return new ArrayList<>();
        }
        createColumnsData(dataVectors);
        initializeColumnsData(dataVectors);
        return normalize(dataVectors);
    }

    public DataVector normalizeDataVector(DataVector dataVector){
        if(validateData(Arrays.asList(dataVector))){
            return new NormalizedData();
        }
        return countMinMaxNormalizationForVector(dataVector);
    }

    public DataVector denormalizeDataVector(DataVector dataVector){
        if(validateData(Arrays.asList(dataVector))){
            return new NormalizedData();
        }
        return denormalizeVector(dataVector);
    }

    private boolean validateData(List<DataVector> dataVectors) {
        return dataVectors == null || dataVectors.isEmpty() ||
                validateValueListOfDataVectors(dataVectors) ||
                validateColumnsToNormalize(dataVectors.get(0).getValueList().size()) ||
                columnsToNormalize.isEmpty();
    }

    private boolean validateValueListOfDataVectors(List<DataVector> dataVectors){
        for (DataVector dataVector : dataVectors) {
            if(dataVector.getValueList() == null || dataVector.getValueList().isEmpty()){
                return true;
            }
        }
        return false;
    }

    private boolean validateColumnsToNormalize(Integer numberOfColumns){
        for (Integer columnNumber : columnsToNormalize) {
            if(columnNumber < 0 || columnNumber > numberOfColumns)
                return true;
        }
        return false;
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
                newVector.addData((dataVector.getValueList().get(i) - columnsData.get(i).getMin())
                        / (columnsData.get(i).getMax() - columnsData.get(i).getMin()));

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
