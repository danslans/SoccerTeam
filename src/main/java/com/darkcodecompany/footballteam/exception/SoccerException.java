package com.darkcodecompany.footballteam.exception;

import java.util.function.Supplier;

public class SoccerException extends Exception implements Supplier<SoccerException> {
    public static final String ERROR_RECORD_TRAINING =  "Error recording training";
    public static final String ERROR_CONVERTING_TRAINING =  "Error converting training";
    public static final String ERROR_SAVING_CONFIGURATION =  "Error saving configuration";
    public static final String ERROR_SAVING_WEEK =  "Error saving week";
    public static final String CONFIGURATION_NOT_FOUND =  "Configuration not found";
    public static final String ERROR_WEEK_NOT_FOUND = "Week not found";
    public static final String ERROR_DATA_INSUFFICIENT = "Data insufficient";
    public static final String ERROR_CONFIGURATION_NOT_FOUND = "configuration not found";
    public static final String ERROR_CALCULATE_SCORE = "Error try calculate Score";
    public static final String ERROR_UNEXPECTED = "Error unexpected";

    public SoccerException() {
    }

    public SoccerException(String message) {
        super(message);
    }

    public SoccerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public SoccerException get() {
        return this;
    }
}
