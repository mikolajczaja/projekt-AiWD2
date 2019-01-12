package aiwd.artificialNeuralNetwork;

public class Connection {

    private Neuron neuron;

    private double weight;

    private double errorPropagationSignal;

    public Connection(Neuron neuron) {
        this.neuron = neuron;
    }

    public Neuron getNeuron() {
        return neuron;
    }

    public void setNeuron(Neuron neuron) {
        this.neuron = neuron;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getErrorPropagationSignal() {
        return errorPropagationSignal;
    }

    public void setErrorPropagationSignal(double errorPropagationSignal) {
        this.errorPropagationSignal = errorPropagationSignal;
    }
}
