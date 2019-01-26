package aiwd.artificialNeuralNetwork;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class Neuron {

    private ArrayList<Connection> connections = new ArrayList<>();

    private double output;

    private double errorPropagationSignal;

    private boolean isBias = false;

    Neuron(){}

    public Connection getConnectionForNeuron(Neuron neuron) {
        return connections.stream()
                .filter(c -> c.getNeuron().equals(neuron))
                .findFirst()
                .orElse(null);
    }

    public void addConnection(Neuron neuron) {
        connections.add(new Connection(neuron));
    }

    public void initializeWeights(double lowerBound, double upperBound) {
        if (connections == null || connections.isEmpty()) {
            return;
        }
        connections.forEach(c -> c.setWeight(ThreadLocalRandom.current().nextDouble(lowerBound, upperBound)));
    }

    public void adjustWeights(double q) {
        if (connections == null || connections.isEmpty()) {
            return;
        }
        for (Connection connection : connections) {
            if (!connection.getNeuron().isBias) {
                connection.setWeight(connection.getWeight() + ( -q * connection.getErrorPropagationSignal() * connection.getNeuron().getOutput()));
            } else {
                connection.setWeight(connection.getWeight() + ( -q * connection.getErrorPropagationSignal()));
            }
        }
    }

    private double sum() {
        double sum = 0.0d;
        for (Connection connection : connections) {
            sum += connection.getNeuron().getOutput() * connection.getWeight();
        }
        return sum;
    }

    private void sigmoid(double sum) {
        output = 1d / (1d + Math.exp(-1d * sum));
    }

    public void execute() {
       if(!isBias) {
           sigmoid(sum());
       }
    }

    public double getErrorPropagationSignal() {
        return errorPropagationSignal;
    }

    public void propagateErrorSignal(double errorPropagationSignal) {
        this.errorPropagationSignal = errorPropagationSignal;
        this.connections.forEach(connection -> connection.setErrorPropagationSignal(errorPropagationSignal));
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neuron neuron = (Neuron) o;
        return Double.compare(neuron.output, output) == 0 &&
                Double.compare(neuron.errorPropagationSignal, errorPropagationSignal) == 0 &&
                Objects.equals(connections, neuron.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connections, output, errorPropagationSignal);
    }

    public boolean isBias() {
        return isBias;
    }

    public void setBias(boolean bias) {
        isBias = bias;
    }
}
