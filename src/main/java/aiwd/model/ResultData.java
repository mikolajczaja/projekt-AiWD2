package aiwd.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultData extends LearningData{
    public ResultData(Date dateAndTime, int inCount, int outCount, int isEvent) {
        super(dateAndTime, inCount, outCount, isEvent);
    }

    @Override
    public List<Double> getValueList() {
        List<Double> values = new ArrayList<>();
        values.add((double) super.isEvent);
        return values;
    }
}
