package uk.co.akm.util.collection.model;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class Venue extends Base {
    public final Type type;
    public final City city;

    public Venue(String name, Type type, City city) {
        super(name);
        this.type = type;
        this.city = city;
    }
}
