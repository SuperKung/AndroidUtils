package com.anderson.categories;



import com.anderson.AndroidUtils;
import com.anderson.exceptions.InvalidKeyForTimeStampException;

import java.util.Map;


/**
 * Created by cesarferreira on 28/05/14.
 */
public class timer {

    private static final String TIMER_PREFIX = "TIMER-";

    protected timer() {
    }

    /**
     * Start counting time
     *
     * @param key the key for the timestamp
     * @return the timestamp
     */
    public static long start(String key) {

        if (key.isEmpty()) {
            throw new InvalidKeyForTimeStampException();
        }

        // current timestamp
        long timestamp = AndroidUtils.date.getCurrentTimeInMiliseconds();

        // save the timestamp
        AndroidUtils.prefs.save(TIMER_PREFIX + key, timestamp);

        return timestamp;
    }

    /**
     * Reset the timer
     *
     * @param key the key for the timestamp
     */
    public static void reset(String key) {
        // delete the key
        AndroidUtils.prefs.remove(TIMER_PREFIX + key);
    }

    /**
     * @param key the key for the timestamp
     * @return the difference since the timer started
     */
    public static long tick(String key) {

        long pastTimestamp = AndroidUtils.prefs.getLong(TIMER_PREFIX + key, -1);

        if (pastTimestamp == -1) {
            throw new InvalidKeyForTimeStampException();
        }

        // current timestamp
        long timestamp = AndroidUtils.date.getCurrentTimeInMiliseconds();

        // the difference
        long difference = timestamp - pastTimestamp;

        // invalid timestamp
        if (difference < 0) {
            throw new InvalidKeyForTimeStampException();
        }

        return difference;
    }

    /**
     * Stop the timer and deletes the key
     *
     * @param key the key for the timestamp
     * @return return the difference
     */
    public static long stop(String key) {

        long pastTimestamp = AndroidUtils.prefs.getLong(TIMER_PREFIX + key, -1);

        if (pastTimestamp == -1) {
            throw new InvalidKeyForTimeStampException();
        }

        // current timestamp
        long timestamp = AndroidUtils.date.getCurrentTimeInMiliseconds();

        // the difference
        long difference = timestamp - pastTimestamp;

        if (difference < 0) {
            throw new InvalidKeyForTimeStampException();
        }
        // delete the key
        AndroidUtils.prefs.remove(TIMER_PREFIX + key);

        return difference;
    }

    /**
     * Resets all the timestamps
     */
    public static void resetAllTimestamps() {
        Map<String, ?> allEntries = AndroidUtils.prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith(TIMER_PREFIX)) {
                AndroidUtils.prefs.remove(entry.getKey());
            }
        }
    }
}