package uk.co.akm.util.collection.collections.group;

import uk.co.akm.util.collection.model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thanos Mavroidis on 14/07/2018.
 */
public final class GroupTestData {
    public final Country uk = new Country("United Kingdom");
    public final Country spain = new Country("Spain");
    public final Country italy = new Country("Italy");

    public final City london = new City("London", uk);

    public final City madrid = new City("Madrid", spain);
    public final City segovia = new City("Segovia", spain);

    public final City rome = new City("Rome", italy);
    public final City venice = new City("Venice", italy);

    public final Venue britishMuseum = new Venue("British Museum", Type.INDOOR, london);
    public final Venue maritimeMuseum = new Venue("Maritime Museum", Type.INDOOR, london);
    public final Venue somersetHouse = new Venue("Somerset House", Type.OUTDOOR, london);
    public final Venue kewGardens = new Venue("Kew Gardens", Type.OUTDOOR, london);

    public final Venue palacioReal = new Venue("Palacio Real", Type.INDOOR, madrid);
    public final Venue pradoMuseum = new Venue("Prado Museum", Type.INDOOR, madrid);
    public final Venue retiro = new Venue("Buen Retiro Park", Type.OUTDOOR, madrid);
    public final Venue plazaDelSol = new Venue("Plaza el Sol", Type.OUTDOOR, madrid);

    public final Venue aqueduct = new Venue("Aqueduct", Type.OUTDOOR, segovia);
    public final Venue alcazar = new Venue("Alcazar", Type.INDOOR, segovia);
    public final Venue casaDeLaMoneda = new Venue("Casa de la Moneda", Type.INDOOR, segovia);
    public final Venue antonioMachadoMuseum = new Venue("Antonio Machado Museum", Type.INDOOR, segovia);

    public final Venue colosseum = new Venue("Colosseum", Type.OUTDOOR, rome);
    public final Venue romanForum = new Venue("Roman Forum", Type.OUTDOOR, rome);
    public final Venue sistineChapel = new Venue("Sistine Chapel", Type.INDOOR, rome);

    public final Venue piazzaSanMarco = new Venue("Piazza San Marco", Type.OUTDOOR, venice);
    public final Venue dogesPalace = new Venue("Doge's Palace", Type.INDOOR, venice);
    public final Venue stMarkBasilica = new Venue("St Mark Basilica", Type.INDOOR, venice);


    public final Collection<Visit> visits = new ArrayList();

    public GroupTestData() {
        visit(britishMuseum, 4, 6, 2018);
        visit(somersetHouse, 4, 6, 2018);
        visit(maritimeMuseum, 4, 6, 2018);
        visit(kewGardens, 6, 6, 2018);

        visit(palacioReal, 8, 7, 2018);
        visit(pradoMuseum, 8, 7, 2018);
        visit(retiro, 8, 7, 2018);
        visit(plazaDelSol, 8, 7, 2018);

        visit(alcazar, 10, 7, 2018);
        visit(casaDeLaMoneda, 10, 7, 2018);
        visit(aqueduct, 12, 7, 2018);
        visit(antonioMachadoMuseum, 12, 7, 2018);

        visit(colosseum, 2, 8, 2018);
        visit(romanForum, 2, 8, 2018);
        visit(sistineChapel, 4, 8, 2018);

        visit(piazzaSanMarco, 6, 8, 2018);
        visit(stMarkBasilica, 6, 8, 2018);
        visit(dogesPalace, 8, 8, 2018);
    }

    private void visit(Venue venue, int day, int month, int year) {
        visits.add(new Visit(venue, new Day(day, month, year)));
    }
}
