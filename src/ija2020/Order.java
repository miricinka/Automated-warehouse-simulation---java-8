package ija2020;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Order class represents one order of goods in system
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Order {
    String id;
    HashMap<String, Integer> toDoMap;
    HashMap<String, Integer> doneMap;

    public Order() {}

    public Order(HashMap<String, Integer> toDoList) {
        this.toDoMap = toDoList;
        this.doneMap = new HashMap<>();
    }

    /**
     * Returns id of order (name)
     * @return x coordinate
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id of order (name)
     * @param id name of order
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns toDoList items that has not been picked up
     * @return list of items to be picked up
     */
    public HashMap<String, Integer> getToDoList() {
        return toDoMap;
    }

    /**
     * Sets toDoList
     * @param toDoList items to be picked up
     */
    public void setToDoList(HashMap<String, Integer> toDoList) {
        this.toDoMap = toDoList;
    }

    /**
     * Returns list of item that has been picked up
     * @return x coordinate
     */
    public HashMap<String, Integer> getDoneList() {
        return doneMap;
    }

    /**
     * Sets donelist of items that has been picked up
     * @param doneList
     */
    public void setDoneList(HashMap<String, Integer> doneList) {
        this.doneMap = doneList;
    }

}
