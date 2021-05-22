package us.careydevevlopment.model.activities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * In this object model, everything is an activity.
 * 
 * Whether it's a task, a to-do, an appointment, or taking out the garbage,
 * it's an activity.
 */
public class Activity {

    @NotNull(message = "Please include an activity type")
    protected ActivityType type;
    
    @NotBlank(message = "Please provide a title for this activity")
    @Size(max = 50, message = "Activity name must be between 1 and 50 characters")
    protected String title;
    
    protected ActivityOutcome outcome;
    
    @Size(max = 512, message = "Activity notes must not exceed 512 characters")
    protected String notes;

    @Size(max = 50, message = "Activity location must not exceed 50 characters")
    protected String location;
    
    @NotNull(message = "Please include an activity start date")
    protected Long startDate;

    @NotNull(message = "Please include an activity end date")
    protected Long endDate;
    
    
    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ActivityOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(ActivityOutcome outcome) {
        this.outcome = outcome;
    }

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
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
