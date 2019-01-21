package aiwd.artificialNeuralNetwork.dataNormalization;

public class MinMaxForColumn {

    private double max;
    private double min;

    public MinMaxForColumn(double max, double min) {
        this.max = max;
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public MinMaxForColumn setMax(double max) {
        if(this.max < max) {
            this.max = max;
        }
        return this;
    }

    public double getMin() {
        return min;
    }

    public MinMaxForColumn setMin(double min) {
        if(this.min > min) {
            this.min = min;
        }
        return this;
    }
}
