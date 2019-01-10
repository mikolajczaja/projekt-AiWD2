package aiwd.artificialNeuralNetwork;

import java.util.concurrent.ThreadLocalRandom;

public class Neural {

    public static final double WEIGHT_INITIAL_BOUND = 0.7d;
    public static final double LEAST_INITIAL_VALUE = 0.5d;
    private double x [];

    private double w [];

    private double errorPropagationSingal;

    private double y;

    public Neural(int inputNumber, int weightNumber){
        x = new double[inputNumber];
        w = new double[weightNumber];
    }

    public void inputValues(double... x){
        for(int i = 0; i < this.x.length; i++){
            this.x[i] = x[i];
        }
    }

    public void initializeWeights(){
        if (w == null){
            return;
        }
        for (int i=0; i< w.length; i++){
            w[i] = ThreadLocalRandom.current().nextDouble(LEAST_INITIAL_VALUE,WEIGHT_INITIAL_BOUND);
        }
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double sum(){
        double sum = 0;
        for(int i = 0; i < x.length; i++){
            sum = sum + x[i] * w[i];
        }
        return sum;
    }

    public double sigmoid(double sum){
        y = 1d / (1 + Math.exp(-sum));
        return y;
    }

    public double getY(){
        return y;
    }

    public double getErrorPropagationSingal() {
        return errorPropagationSingal;
    }

    public void setErrorPropagationSingal(double errorPropagationSingal) {
        this.errorPropagationSingal = errorPropagationSingal;
    }
}
