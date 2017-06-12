package org.baconeers.utils;

/**
 * Created by Shaun on 11/06/2017.
 */

public class MovingAverageTimer {

    private int num_elements;
    private long count;
    private long times[];
    private long moving_total;
    private long running_total;
    private long previousTime;
    private int current_index;
    private double moving_average;
    private double resolution;
    private String format_str;
    private String format2_str;

    public enum Resolution {
        SECONDS,
        MILLISECONDS
    }

    /**
     * the number of nanoseconds in a second
     */
    public static final double SECOND_IN_NANO = 1000000000.0;

    /**
     * the number of nanoseconds in a millisecond
     */
    public static final double MILLIS_IN_NANO = 1000000.0;

    public MovingAverageTimer() {
        this(100, Resolution.MILLISECONDS);
    }

    public MovingAverageTimer(int num) {
        this(num, Resolution.MILLISECONDS);
    }

    public MovingAverageTimer(int num, Resolution resolution) {
        count = 0;
        moving_total = 0;
        running_total = 0;
        previousTime = System.nanoTime();
        num_elements = num;
        times = new long[num_elements];
        current_index = 0;
        moving_average = 0.0;
        switch (resolution) {
            case SECONDS:
                this.resolution = SECOND_IN_NANO;
                format_str = "%3.3f secs";
                format2_str = "\nLoops      TotalTime   MovingAvg Average (in sec)\n%-10d%-12.3f%-10.3f%-10.3f";

                break;
            case MILLISECONDS:
            default:
                this.resolution = MILLIS_IN_NANO;
                format_str = "%3.3f msecs";
                format2_str = "\nLoops      TotalTime   MovingAvg Average (in msec)\n%-10d%-12.3f%-10.3f%-10.3f";
                break;
        }
    }

    public void update() {
        long now = System.nanoTime();
        long diff = now - previousTime;
        previousTime = now;

        // Adjust the running total
        moving_total = moving_total - times[current_index] + diff;
        running_total = running_total + diff;

        // Add the new value
        times[current_index] = diff;

        // wrap the current index
        current_index = (current_index + 1) % num_elements;

        count += 1;

        if (count < num_elements) {
            if (count == 0) {
                moving_average = 0.0;
            } else {
                moving_average = (double) moving_total / (double) count / resolution;
            }
        } else {
            moving_average = (double) moving_total / (double) num_elements / resolution;
        }
    }

    public long count() {
        return count;
    }

    public double movingAverage() {
        return moving_average;
    }

    public String movingAverageString() {
        return String.format(format_str, moving_average);
    }

    public double average() {
        if (count == 0) {
            return 0.0;
        } else {
            return (double) running_total / (double) count / resolution;
        }
    }

    public String averageString() {
        return String.format(format_str, average());
    }

    @Override
    public String toString() {
        return String.format(format2_str,count,running_total/resolution,moving_average,average());
    }
}
