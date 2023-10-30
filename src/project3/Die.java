package project3;

import edu.princeton.cs.algs4.*;

public class Die implements Comparable<Die> {
    /**
     * The face value
     */
    private int value;

    /**
     * Constructs a die
     */
    public Die() { this.value = 0; }

    /**
     * Rolls this die
     * @apiNote the deprecated {@link StdRandom#uniform(int) uniform()} method has been replaced
     */
    public void roll() {
        value = StdRandom.uniformInt(1, 7);
    }

    /**
     * Getter for {@link #value}
     * @return the face value of this die
     */
    public int value() {
        return value;
    }

    /**
     * @param other the other die
     * @return {@code true} if this die is the same as {@code other}, {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof Die b) {
            return this .value == b.value;
        } else {
            return false;
        }
    }

    /**
     * @param that the object to be compared
     * @return a comparison of this die with other, by their face values
     */
    public int compareTo(Die that) {
        return this.value - that.value;
    }

    /**
     * @return the string representation of this die
     */
    public String toString() {
        return switch (this.value) {
            case 1 -> "     \n  *  \n     ";
            case 2 -> "*    \n     \n    *";
            case 3 -> "*    \n  *  \n    *";
            case 4 -> "*   *\n     \n*   *";
            case 5 -> "*   *\n  *  \n*   *";
            case 6 -> "* * *\n     \n* * *";
            default -> "Not rolled yet";
        };
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Die a = new Die();
        a.roll();
        while (a.value() != x) {
            a.roll();
        }
        Die b = new Die();        
        b.roll();
        while (b.value() != y) {
            b.roll();
        }
        Die c = new Die();        
        c.roll();
        while (c.value() != z) {
            c.roll();
        }
        StdOut.println("Dice a, b, and c:");
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(c);
        StdOut.println("a.equals(b)    = " + a.equals(b));
        StdOut.println("b.equals(c)    = " + b.equals(c));
        StdOut.println("a.compareTo(b) = " + a.compareTo(b));
        StdOut.println("b.compareTo(c) = " + b.compareTo(c));
    }
}
