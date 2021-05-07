package ija2020;

import java.util.Objects;

/**
 * Coordinates class
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */

public class Coordinates {
    private double x;
    private double y;

    public Coordinates() {}

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x coordinate
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets coordinate x
     * @param x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns coordinate y
     * @return coordinate y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets coordinate y
     * @param y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculates distanes between this and other coordinates
     * @param coordinates
     */
    public double calcDistance(Coordinates coordinates){
        return Math.sqrt((x-coordinates.getX())*(x-coordinates.getX()) + (y-coordinates.getY())*(y-coordinates.getY()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
