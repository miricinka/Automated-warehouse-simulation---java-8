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
    private LinkedList<Item> itemsList;
    private Coordinates coordinates;
    private Coordinates stopCoordinates;
    private Isle isle;

    public StoreGoods() {}

    public StoreGoods(String name, Coordinates cords, Isle isle, Coordinates stopCoordinates, Double itemWeight) {
        itemsList = new LinkedList<>();
        this.name = name;
        this.itemWeight = itemWeight;
        this.coordinates = cords;
        this.isle = isle;
        this.stopCoordinates = stopCoordinates;
    }

    /**
     * Returns StoreGoods name of product
     * @return name
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
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Adds new item to the list of items
     * @param newItem
     */
    public void addNewItem(Item newItem) {
        itemsList.add(newItem);
    }

    /**
     * Returns last item in list of items
     * @return item
     */
    public Item removeItem() {
        return itemsList.pollLast();
    }

    public Isle getIsle() {
        return isle;
    }

    public void setIsle(Isle isle) {
        this.isle = isle;
    }

    public Coordinates getStopCoordinates() {
        return stopCoordinates;
    }

    public void setStopCoordinates(Coordinates stopCoordinates) {
        this.stopCoordinates = stopCoordinates;
    }

    public LinkedList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(LinkedList<Item> itemsList) {
        this.itemsList = itemsList;
    }
}
