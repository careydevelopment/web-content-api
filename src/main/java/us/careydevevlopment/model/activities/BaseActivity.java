package us.careydevevlopment.model.activities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * In this object model, everything is an activity.
 * 
 * Whether it's a task, a to-do, an appointment, or taking out the garbage,
 * it's an activity.
 * 
 * Note the generics: you need to specify concrete implementations of
 * BaseActivityOutcome, BaseActivityType, and BaseActivityOwner
 */
public abstract class BaseActivity<T extends BaseActivityOutcome, U extends BaseActivityType<T>, V extends BaseActivityOwner> {

    /**
     * The type of activity. It can be an appointment, to-do, etc.
     * It's up to individual developers to specify specific activity types.
     */
    @NotNull(message = "Please include an activity type")
    protected U type;
    
    /**
     * The person responsible for this activity
     */
    protected V owner;
    
    @NotBlank(message = "Please provide a title for this activity")
    @Size(max = 50, message = "Activity name must be between 1 and 50 characters")
    protected String title;
    
    /**
     * The outcome of the activity, if the activity has an outcome
     */
    protected T outcome;
    
    @Size(max = 512, message = "Activity notes must not exceed 512 characters")
    protected String notes;

    @Size(max = 50, message = "Activity location must not exceed 50 characters")
    protected String location;
    
    /**
     * All activities have a start date, but not all activities have an end date.
     * 
     * Some activities are fixed point in time (like taking out the garbage) and 
     * have no end date.
     * 
     * Other activities take a specified amount of time (like meetings)
     * 
     * And note that "date" here means both date and time.
     * 
     * All activity dates are stored as numbers in UTC time zone. It's up
     * the developer to present the dates based on formatting reqs and local time zones.
     * 
     * The number here represents the number of milliseconds since 1970 began.
     */
    @NotNull(message = "Please include an activity start date")
    protected Long startDate;

    @NotNull(message = "Please include an activity end date")
    protected Long endDate;
    
    protected List<Reminder> reminders;
    
    protected ActivityStatus status;
    
    public abstract V getOwner();       
    
    public abstract void setOwner(V owner);
    
    public abstract U getType();

    public abstract void setType(U type);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract T getOutcome();

    public abstract void setOutcome(T outcome);

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
    
    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
    
    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
