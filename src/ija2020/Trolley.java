package ija2020;

import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing one trolley
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Trolley {
    private double capacity; //kg
    private double usedCapacity;
    private Order order;
    private LinkedList<StoreGoods> carriedItemsList;
    private Coordinates coordinates;
    private List<Coordinates> path;
    private Circle circle;

    public Trolley() {}

    public Trolley(double capacity, Coordinates coordinates) {
        this.capacity = capacity;
        this.coordinates = coordinates;
        usedCapacity = 0.0;
        carriedItemsList = new LinkedList<>();
        order = null;
    }

    /**
     * Gets max capacity of trolley
     * @return max capacity
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Sets max capacity of trolley
     * @param capacity max capacity of trolley
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets used capacity of trolley
     * @return used capacity
     */
    public double getUsedCapacity() {
        return usedCapacity;
    }

    /**
     * Sets used capacity of trolley
     * @param usedCapacity used capacity of trolley
     */
    public void setUsedCapacity(double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    /**
     * Gets current order of trolley
     * @return current order of trolley
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets current order of trolley
     * @param order current order of trolley
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets list of carried items
     * @return list of carried items
     */
    public LinkedList<StoreGoods> getCarriedItemsList() {
        return carriedItemsList;
    }

    /**
     * Sets carried items list
     * @param carriedItemsList list of carried items
     */
    public void setCarriedItemsList(LinkedList<StoreGoods> carriedItemsList) {
        this.carriedItemsList = carriedItemsList;
    }

    /**
     * Gets coordinates of trolley
     * @return coordinates of trolley
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates of trolley
     * @param coordinates of trolley
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setPath(List<Coordinates> path){
        this.path = path;
    }

    public void addCircle(Circle circle){
        this.circle = circle;
    }

    public void updateCoords() {
        if(path == null || path.isEmpty()) {
            return;
        }
        Coordinates wayToGo = path.get(0);
        if(wayToGo.getX() == coordinates.getX() && wayToGo.getY()==coordinates.getY()) {
            path.remove(0);
            return;
        } else if(wayToGo.getX() == coordinates.getX()) {
            if(wayToGo.getY() > coordinates.getY()) {
                coordinates.setY(coordinates.getY() + 1.0);
            }else {
                coordinates.setY(coordinates.getY() - 1.0);
            }
        }else {
            if(wayToGo.getX() > coordinates.getX()){
                coordinates.setX(coordinates.getX() + 1.0);
            }else{
                coordinates.setX(coordinates.getX() - 1.0);
            }
        }
        //System.out.println(coordinates.getX());
        //System.out.println(coordinates.getY());
        //update on gui GUI
        circle.setCenterX(coordinates.getX());
        circle.setCenterY(coordinates.getY());
    }
}
