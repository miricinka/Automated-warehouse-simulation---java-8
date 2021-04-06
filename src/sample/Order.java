package sample;

import java.util.LinkedList;

public class Order {
    LinkedList<Item> toDoList;
    LinkedList<Item> doneList;

    public Order(LinkedList<Item> toDoList) {
        this.toDoList = toDoList;
        this.doneList = new LinkedList<>();
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
