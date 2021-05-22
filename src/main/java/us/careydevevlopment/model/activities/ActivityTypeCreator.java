package us.careydevevlopment.model.activities;

/**
 * In some applications, activity types are automagically generated.
 * 
 * For example, in a CRM when the user replies to an email from a contact, 
 * the application will generate a "Reply to Email" activity type.
 * 
 * The model distinguishes between different creators because it's often the
 * case that you don't want users editing system-generated activity types.
 */
public enum ActivityTypeCreator {
    USER, SYSTEM;
}
