package ija2020;

/**
 * DropSpot class, represents place where trolley drops the order
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class DropSpot {
    private Coordinates coordinates;

    public DropSpot() {}

    public DropSpot(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns coordinates of dropspot
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates of dropspot
     * @param coordinates of dropspot
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

