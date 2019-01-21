package aiwd.model;

import aiwd.artificialNeuralNetwork.DataVector;

import java.util.*;

public class LearningData implements DataVector {

    protected Date dateAndTime;
    protected int inCount = 0;
    protected int outCount = 0;
    protected int isEvent;

    public LearningData(Date dateAndTime, int inCount, int outCount, int isEvent) {
        this.dateAndTime = dateAndTime;
        this.inCount = inCount;
        this.outCount = outCount;
        this.isEvent = isEvent;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public int getInCount() {
        return inCount;
    }

    public int getOutCount() {
        return outCount;
    }

    public int getIsEvent() {
        return isEvent;
    }

    public double[] getValues(){
        return new double[]{dateAndTime.getTime(),inCount, outCount, isEvent};
    }

    public List<Double> getValueList(){
        List<Double> values = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAndTime);

//        values.add((double) cal.get(Calendar.DAY_OF_MONTH));
//        values.add((double) cal.get(Calendar.MONTH));
//        values.add((double) cal.get(Calendar.DAY_OF_WEEK));
//        values.add((double) cal.get(Calendar.HOUR_OF_DAY));
//        values.add((double) cal.get(Calendar.MINUTE));
//        //values.add((double) dateAndTime.getTime());
//        values.add((double) inCount);
        values.add((double) outCount);
        values.add((double) isEvent);
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningData that = (LearningData) o;
        return inCount == that.inCount &&
                outCount == that.outCount &&
                Objects.equals(dateAndTime, that.dateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateAndTime, inCount, outCount);
    }
}
