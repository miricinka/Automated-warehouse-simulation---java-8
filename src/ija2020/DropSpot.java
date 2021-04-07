package ija2020;

public class DropSpot {
    private Coordinates coordinates;

    public DropSpot() {}

    public DropSpot(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

