package ija2020;

import java.util.LinkedList;

public class Order {
    String id;
    LinkedList<Item> toDoList;
    LinkedList<Item> doneList;

    public Order() {}

    public Order(LinkedList<Item> toDoList) {
        this.toDoList = toDoList;
        this.doneList = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Item> getToDoList() {
        return toDoList;
    }

    public void setToDoList(LinkedList<Item> toDoList) {
        this.toDoList = toDoList;
    }

    public LinkedList<Item> getDoneList() {
        return doneList;
    }

    public void setDoneList(LinkedList<Item> doneList) {
        this.doneList = doneList;
    }

}
