package uk.co.akm.util.collection.model;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public class Base {
    private final String name;

    public Base(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
