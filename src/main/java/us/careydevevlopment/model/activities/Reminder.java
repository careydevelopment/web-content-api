package us.careydevevlopment.model.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Contains info about reminders for activities.
 * 
 * It's up to the application to implement the solution for reminders.
 * This code just handles the models.
 */
public class Reminder {

    private Long date;
    
    /**
     * Each reminder can have multiple "alert" types;
     */
    private List<ReminderType> types = new ArrayList<ReminderType>();

    
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public List<ReminderType> getTypes() {
        return types;
    }

    public void setTypes(List<ReminderType> types) {
        this.types = types;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
