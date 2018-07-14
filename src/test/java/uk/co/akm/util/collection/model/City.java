package uk.co.akm.util.collection.model;

/**
 * Created by Thanos Mavroidis on 07/07/2018.
 */
public final class City extends Base {
    public final Country country;

    public City(String name, Country country) {
        super(name);
        this.country = country;
    }
}
