package project3;

import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class Location implements Comparable<Location> {
    /**
     * Location name
     */
    private final String name;
    /**
     * Latitude
     */
    private final double lat;
    /**
     * Longitude
     */
    private final double lon;

    /**
     * Constructs a new location given its name, latitude, and longitude
     * @param name the location's name
     * @param lat the location's latitude
     * @param lon the location's longitude
     */
    public Location(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * @param other the other {@link Location}
     * @return the great circle distance between this location and {@code other}
     */
    public double distanceTo(Location other) {
        double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        double lat2 = Math.toRadians(other.lat);
        double lon2 = Math.toRadians(other.lon);

        return 111 * Math.toDegrees(Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1-lon2)));
    }

    /**
     * @param other the other {@link Location}
     * @return {@code true} if this location is the same as {@code other}, {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == null || (other.getClass() != this.getClass())) {
            return false;
        }
        if (other == this) {
            return true;
        }
        Location a = this;
        Location b = (Location) other;
        return a.lat == b.lat && a.lon == b.lon;
    }

    /**
     * @return the string representation of this {@link Location} object
     */
    public String toString() {
        return name + " (" + lat + ", " + lon + ")";
    }

    /**
     * Compares this location with other based on their respective distances to the origin, the
     * Parthenon (Greece) @ 37.971525, 23.726726
     * @param that the object to be compared
     * @return {@code -1} if this location is closer, {@code 1} if it is farther, {@code 0} if they are the same
     */
    public int compareTo(Location that) {
        Location origin = new Location("Parthenon (Greece)", 37.971525, 23.726726);
        double a = distanceTo(origin);
        double b = that.distanceTo(origin);
        return Double.compare(a, b);
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int rank = Integer.parseInt(args[0]);
        String name = args[1];
        double lat = Double.parseDouble(args[2]);
        double lon = Double.parseDouble(args[3]);
        Location[] wonders = new Location[7];
        wonders[0] = new Location("The Great Wall of China (China)", 40.6769, 117.2319);
        wonders[1] = new Location("Petra (Jordan)", 30.3286, 35.4419);
        wonders[2] = new Location("The Colosseum (Italy)", 41.8902, 12.4923);
        wonders[3] = new Location("Chichen Itza (Mexico)", 20.6829, -88.5686);
        wonders[4] = new Location("Machu Picchu (Peru)", -13.1633, -72.5456);
        wonders[5] = new Location("Taj Mahal (India)", 27.1750, 78.0419);
        wonders[6] = new Location("Christ the Redeemer (Brazil)", 22.9519, -43.2106);
        Arrays.sort(wonders);
        StdOut.println("Seven wonders, in the order of their distance to Parthenon (Greece):");
        for (Location wonder : wonders) {
            StdOut.println("  " + wonder);
        }
        Location loc = new Location(name, lat, lon);
        StdOut.print("wonders[" + rank + "] == " + loc + "? ");
        StdOut.println(wonders[rank].equals(loc));
    }
}
