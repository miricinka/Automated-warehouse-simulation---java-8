package ija2020;

import java.util.LinkedList;

public class StoreGoods {
    private String name;
    private LinkedList<Item> itemsList;
    private Coordinates coordinates;
    private Isle isle;

    public StoreGoods() {}

    public StoreGoods(String name, Coordinates cords, Isle isle) {
        itemsList = new LinkedList<>();
        this.name = name;
        this.coordinates = cords;
        this.isle = isle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void addNewItem(Item newItem) {
        itemsList.add(newItem);
    }

    public Item removeItem() {
        return itemsList.pollLast();
    }

    public Isle getIsle() {
        return isle;
    }

    public void setIsle(Isle isle) {
        this.isle = isle;
    }

    public LinkedList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(LinkedList<Item> itemsList) {
        this.itemsList = itemsList;
    }
}
