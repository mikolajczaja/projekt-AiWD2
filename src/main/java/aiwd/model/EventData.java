package aiwd.model;

import aiwd.exception.MissingArgumentException;
import aiwd.util.ParsingUtil;

import java.text.ParseException;
import java.util.Date;

public class EventData {

    private String eventName;
    private Date beignEventDateAndTime;
    private Date endEventDateAndTime;

    public EventData(String... eventData) {
        this.eventName = eventData[3];
        try {
            this.beignEventDateAndTime = ParsingUtil.parseDateAndTime(eventData[0],eventData[1]);
            this.endEventDateAndTime = ParsingUtil.parseDateAndTime(eventData[0],eventData[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MissingArgumentException e) {
            e.printStackTrace();
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getBeignEventDateAndTime() {
        return beignEventDateAndTime;
    }

    public void setBeignEventDateAndTime(Date beignEventDateAndTime) {
        this.beignEventDateAndTime = beignEventDateAndTime;
    }

    public Date getEndEventDateAndTime() {
        return endEventDateAndTime;
    }

    public void setEndEventDateAndTime(Date endEventDateAndTime) {
        this.endEventDateAndTime = endEventDateAndTime;
    }
}
