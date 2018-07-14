package uk.co.akm.util.collection.model;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class Visit {
    public final Venue venue;
    public final Day date;

    public Visit(Venue venue, Day date) {
        this.venue = venue;
        this.date = date;
    }

    @Override
    public String toString() {
        return ("" + venue + " (" + venue.type + ") on " + date + " in " + venue.city + ", " + venue.city.country);
    }
}
