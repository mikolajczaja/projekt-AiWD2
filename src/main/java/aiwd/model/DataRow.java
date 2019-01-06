package aiwd.model;

import aiwd.exception.DataLoadingException;
import aiwd.exception.MissingArgumentException;
import aiwd.util.ParsingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Row structure:
 * <br>1.  Flow ID: 7 is out flow, 9 is in flow
 * <br>2.  Date: MM/DD/YY
 * <br>3.  Time: HH:MM:SS
 * <br>4.  Count: Number of counts reported for the previous half hour
 * <br>
 * <br>Rows: Each half hour time slice is represented by 2 rows: one row for
 * the out flow during that time period (ID=7) and one row for the in flow
 * during that time period (ID=9)
 */
public class DataRow {

    private static Log LOGGER = LogFactory.getLog(DataRow.class);

    private Date dateAndTime;
    private int inCount = 0;
    private int outCount = 0;

    public DataRow(String[] csvRow1, String[] csvRow2) {

        if ((csvRow1 != null) && (csvRow2 != null)) {
            try {
                Date dateAndTime1 = ParsingUtil.parseDateAndTime(csvRow1[1], csvRow1[2]);
                Date dateAndTime2 = ParsingUtil.parseDateAndTime(csvRow2[1], csvRow2[2]);

                if (dateAndTime1.equals(dateAndTime2)) {
                    parseFlowCountBasedOnDirection(ParsingUtil.parseFlowDirection(csvRow1[0]), ParsingUtil.parseCount(csvRow1[3]));
                    parseFlowCountBasedOnDirection(ParsingUtil.parseFlowDirection(csvRow2[0]), ParsingUtil.parseCount(csvRow2[3]));
                }
                this.dateAndTime = dateAndTime1;

            } catch (MissingArgumentException | ParseException | DataLoadingException e) {
                LOGGER.error(e);
            }
        }
    }

    private void parseFlowCountBasedOnDirection(FlowDirection flowDirection, int count) throws DataLoadingException {
        if (flowDirection.equals(FlowDirection.OUT)) {
            if (this.outCount != 0) {
                throw new DataLoadingException("loaded two rows with same flow direction");
            }
            this.outCount = count;
        } else {
            if (this.inCount != 0) {
                throw new DataLoadingException("loaded two rows with same flow direction");
            }
            this.inCount = count;
        }
    }

    @Override
    public String toString() {
        return "DataRow{" +
                " dateAndTime=" + dateAndTime +
                ", inCount=" + inCount +
                ", outCount=" + outCount +
                '}';
    }
}