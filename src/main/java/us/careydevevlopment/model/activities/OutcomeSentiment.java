package us.careydevevlopment.model.activities;

/**
 * This is mainly for CRM apps.
 * 
 * Some sales activities may generate an outcome.
 * And that outcome can be positive (contact wants a demo) or
 * negative (contact hangs up on the sales rep). 
 *
 */
public enum OutcomeSentiment {

    POSITIVE, NEGATIVE, NEUTRAL;
}
