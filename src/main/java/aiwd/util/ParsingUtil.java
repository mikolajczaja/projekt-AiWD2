package aiwd.util;

import aiwd.exception.MissingArgumentException;
import aiwd.model.FlowDirection;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParsingUtil {

    public static FlowDirection parseFlowDirection(String directionCode) throws MissingArgumentException {
        if (!StringUtils.isEmpty(directionCode)) {
            return FlowDirection.valueOf(Integer.parseInt(directionCode));
        }
        throw new MissingArgumentException("direction code missing");
    }

    public static Date parseDate(String dateString) throws ParseException, MissingArgumentException {
        if (!StringUtils.isEmpty(dateString)) {
            Date date = new SimpleDateFormat("MM/dd/yy").parse(dateString);
            return date;
        }
        throw new MissingArgumentException("date missing");
    }

    public static Date parseTime(String timeString) throws ParseException, MissingArgumentException {
        if (!StringUtils.isEmpty(timeString)) {
            Date time = new SimpleDateFormat("HH:mm:ss").parse(timeString);
            return time;
        }
        throw new MissingArgumentException("time missing");
    }

    public static int parseCount(String countString) throws MissingArgumentException {
        if (!StringUtils.isEmpty(countString)) {
            return Integer.parseInt(countString);
        }
        throw new MissingArgumentException("count missing");
    }

    public static Date parseDateAndTime(String dateString, String timeString) throws ParseException, MissingArgumentException {
        if ((!StringUtils.isEmpty(dateString)) && (!StringUtils.isEmpty(timeString))) {
            return new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(dateString + " " + timeString);
        }
        throw new MissingArgumentException("date missing");
    }
}