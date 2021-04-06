package sample;

import java.util.LinkedList;

public class Trolley {
    private double capacity; //kg
    private double usedCapacity;
    private Order order;
    private LinkedList<Item> carriedItemsList;
    private Coordinates coordinates;

    public Trolley(double capacity, Coordinates coordinates) {
        this.capacity = capacity;
        this.coordinates = coordinates;
        usedCapacity = 0.0;
        carriedItemsList = new LinkedList<>();
        order = null;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LinkedList<Item> getCarriedItemsList() {
        return carriedItemsList;
    }

    public void setCarriedItemsList(LinkedList<Item> carriedItemsList) {
        this.carriedItemsList = carriedItemsList;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
