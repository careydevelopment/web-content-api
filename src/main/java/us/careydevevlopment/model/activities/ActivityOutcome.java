package us.careydevevlopment.model.activities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Mainly for CRM apps.
 * 
 * Some activites have an outcome.
 * Maybe the contact wants to talk more about the product next week.
 * Or maybe the contact says he's definitely not interested.
 */
public class ActivityOutcome {

    @NotBlank(message = "Please provide a name for this activity outcome")
    @Size(max = 20, message = "Activity outcome name must be between 1 and 20 characters")
    protected String name;

    protected OutcomeSentiment sentiment = OutcomeSentiment.NEUTRAL;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
    public OutcomeSentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(OutcomeSentiment sentiment) {
        this.sentiment = sentiment;
    }
}
