package aiwd.model;

import java.util.Date;
import java.util.Objects;

public class LearningData {

    private Date dateAndTime;
    private int inCount = 0;
    private int outCount = 0;
    private int isEvent;

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
