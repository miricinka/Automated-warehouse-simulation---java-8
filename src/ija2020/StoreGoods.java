package ija2020;

import java.util.LinkedList;

/**
 * StoreGoods class, represents shelf in the store with items of the same product
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */

public class StoreGoods {
    private String name;
    private double itemWeight;
    private int itemsCount;
    private int readyToDispatch = 0;
    private Coordinates coordinates;
    private Coordinates stopCoordinates;
    private Isle isle;

    public StoreGoods() {}

    public StoreGoods(String name, Coordinates cords, Isle isle, Coordinates stopCoordinates, Double itemWeight) {
        this.name = name;
        this.itemWeight = itemWeight;
        this.coordinates = cords;
        this.isle = isle;
        this.stopCoordinates = stopCoordinates;
    }

    /**
     * Returns StoreGoods name of product
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets StoreGoods the item belongs to
     * @param name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns weight of one item
     * @return weight of one item
     */
    public double getItemWeight() {
        return itemWeight;
    }

    /**
     * Sets weight of one item, weight must be > 0
     * @return weight of item
     */
    public void setItemWeight(double itemWeight) throws Exception {
        if (itemWeight <= 0.0)
            throw new Exception("Weight must be > 0");
        this.itemWeight = itemWeight;
    }

    /**
     * Returns Coordinates of the shelf
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates of the shelf
     * @param coordinates coordinates of shelf
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    /**
     * Returns Isle this shelf belongs to
     * @return isle it belongs to
     */
    public Isle getIsle() {
        return isle;
    }

    /**
     * Sets Isle it belongs to
     * @param isle it belongs to
     */
    public void setIsle(Isle isle) {
        this.isle = isle;
    }

    /**
     * Returns coordinates where the trolley stops
     * @return coordinates where the trolley stops
     */
    public Coordinates getStopCoordinates() {
        return stopCoordinates;
    }

    /**
     * Sets coordinates where the trolley stops
     * @param stopCoordinates where the trolley stops
     */
    public void setStopCoordinates(Coordinates stopCoordinates) {
        this.stopCoordinates = stopCoordinates;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getReadyToDispatch() {
        return readyToDispatch;
    }

    public void setReadyToDispatch(int readyToDispatch) {
        this.readyToDispatch = readyToDispatch;
    }
}
