package uk.co.akm.util.collection.model;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class Day {
    public final int day;
    public final int month;
    public final int year;

    public Day(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return ("" + day + "/" + month + "/" + year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day1 = (Day) o;

        if (day != day1.day) return false;
        if (month != day1.month) return false;
        return year == day1.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}
