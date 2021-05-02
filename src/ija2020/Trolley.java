package ija2020;

import javafx.scene.shape.Circle;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class representing one trolley
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Trolley {
    private String id;
    private double capacity; //kg
    private double usedCapacity;
    private Order order;
    private Coordinates coordinates;
    private Coordinates startCoordinates;
    private List<Coordinates> wholePath;
    private List<Coordinates> path;
    private Circle circle;

    public Trolley() {}

    public Trolley(double capacity, Coordinates coordinates) {
        this.capacity = capacity;
        this.startCoordinates = coordinates;
        usedCapacity = 0.0;
        order = null;
    }

    public String getId() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
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

    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(Coordinates startCoordinates) {
        this.startCoordinates = startCoordinates;
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

    public List<Coordinates> getPath(){
        return path;
    }

    public void setWholePath(){
        wholePath = new ArrayList<>(path);
    }

    public List<Coordinates> getWholePath(){
        return wholePath;
    }

    public Coordinates getWholePathFirst(){
        return wholePath.get(0);
    }

    public void addCircle(Circle circle){
        this.circle = circle;
    }

    public void updateCoords() {
        if(coordinates == null) {
            coordinates = new Coordinates(startCoordinates.getX(), startCoordinates.getY());
        }
        if(path == null || path.isEmpty()) {
            return;
        }
        Coordinates wayToGo = path.get(0);
        if(wayToGo.getX() == coordinates.getX() && wayToGo.getY()==coordinates.getY()) {
            //zastavit se
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
        //update on gui GUI
        circle.setCenterX(coordinates.getX());
        circle.setCenterY(coordinates.getY());
    }

}
