package aiwd.artificialNeuralNetwork;

public class NeuralNetworkProperties {

    private int numberOfInputs;
    private int numberOfLayers;
    private int numberOfNeuronsPerLayer;
    private int numberOfOutputs;
    private double q = 0.01d;
    private double biasValue = 1.0d;
    private double weightInitialUpperBound = 0.7d;
    private double weightInitialLowerBound = 0.1d;

    public double getWeightInitialUpperBound() {
        return weightInitialUpperBound;
    }

    public void setWeightInitialUpperBound(double weightInitialUpperBound) {
        this.weightInitialUpperBound = weightInitialUpperBound;
    }

    public double getWeightInitialLowerBound() {
        return weightInitialLowerBound;
    }

    public void setWeightInitialLowerBound(double weightInitialLowerBound) {
        this.weightInitialLowerBound = weightInitialLowerBound;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public int getNumberOfLayers() {
        return numberOfLayers;
    }

    public void setNumberOfLayers(int numberOfLayers) {
        this.numberOfLayers = numberOfLayers;
    }

    public int getNumberOfNeuronsPerLayer() {
        return numberOfNeuronsPerLayer;
    }

    public void setNumberOfNeuronsPerLayer(int numberOfNeuronsPerLayer) {
        this.numberOfNeuronsPerLayer = numberOfNeuronsPerLayer;
    }

    public int getNumberOfOutputs() {
        return numberOfOutputs;
    }

    public void setNumberOfOutputs(int numberOfOutputs) {
        this.numberOfOutputs = numberOfOutputs;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getBiasValue() {
        return biasValue;
    }

    public void setBiasValue(double biasValue) {
        this.biasValue = biasValue;
    }
}
