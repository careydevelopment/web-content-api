 package us.careydevevlopment.model.activities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Defining activity types is left to you, the developer.
 * 
 * You can define (and persist) types as you see fit.
 * 
 * Example types include appointment, meeting, chat, email, or taking out the garbage.
 */
public abstract class BaseActivityType<T extends BaseActivityOutcome> {

    @NotBlank(message = "Please provide a name for this activity type")
    @Size(max = 20, message = "Activity type name must be between 1 and 20 characters")
    protected String name;
    
    @NotNull(message = "Please include an activity type creator")
    protected ActivityTypeCreator activityTypeCreator;
    
    protected Boolean requiresOutcome = true;
    protected Boolean usesLocation = false;
    protected Boolean usesEndDate = false;
    
    protected List<T> possibleOutcomes = new ArrayList<>();
        
    public Boolean getRequiresOutcome() {
        return requiresOutcome;
    }
    public void setRequiresOutcome(Boolean requiresOutcome) {
        this.requiresOutcome = requiresOutcome;
    }
    
    public abstract List<T> getPossibleOutcomes();
    
    public abstract void setPossibleOutcomes(List<T> possibleOutcomes);
    
    public Boolean getUsesEndDate() {
        return usesEndDate;
    }
    public void setUsesEndDate(Boolean usesEndDate) {
        this.usesEndDate = usesEndDate;
    }
    
    public Boolean getUsesLocation() {
        return usesLocation;
    }
    public void setUsesLocation(Boolean usesLocation) {
        this.usesLocation = usesLocation;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ActivityTypeCreator getActivityTypeCreator() {
        return activityTypeCreator;
    }
    public void setActivityTypeCreator(ActivityTypeCreator activityTypeCreator) {
        this.activityTypeCreator = activityTypeCreator;
    }
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
