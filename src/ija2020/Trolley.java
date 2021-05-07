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
    private double usedCapacity = 0.0;
    private Order order;
    private Coordinates coordinates;
    private Coordinates startCoordinates;
    private List<Coordinates> wholePath;
    private List<Coordinates> path;
    private Circle circle;
    private List<StoreGoods> storeGoodsStops;
    private int sleep = 0;

    public Trolley() {}

    public Trolley(double capacity, Coordinates coordinates) {
        this.capacity = capacity;
        this.startCoordinates = coordinates;
        usedCapacity = 0.0;
        order = null;
    }

    /**
     * Gets ID of trolley
     * @return id of trolley
     */
    public String getId() {
        return id;
    }

    /**
     * Sets ID of trolley
     * @param ID of trolley
     */
    public void setID(String ID) {
        this.id = ID;
    }

    /**
     * returns list of store goods where this trolley stops on its way to get all Goods
     * @return list of storeGoods
     */
    public List<StoreGoods> getStoreGoodsStops() {
        return storeGoodsStops;
    }

    /**
     * Sets list of  store goods where this trolley stops on its way to get all Goods
     * @param storeGoodsStops of storeGoods
     */
    public void setStoreGoodsStops(List<StoreGoods> storeGoodsStops) {
        this.storeGoodsStops = storeGoodsStops;
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
     * Gets start coordinates of trolley
     * @return start coordinates of trolley
     */
    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    /**
     * Sets start coordinates of trolley
     * @param startCoordinates of trolley
     */
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

    /**
     * Sets path of trolley
     * @param path list of coordinates
     */
    public void setPath(List<Coordinates> path){
        this.path = path;
    }

    /**
     * Gets path of trolley
     * @return path of trolley
     */
    public List<Coordinates> getPath(){
        return path;
    }

    /**
     * Creates copy of path for painting path -> from real path are coordinates taken
     */
    public void setWholePath(){
        wholePath = new ArrayList<>(path);
    }

    /**
     * Gets whole path of trolley
     * @return whole path of trolley
     */
    public List<Coordinates> getWholePath(){
        return wholePath;
    }

    /**
     * Gets first coordinate in whole path
     * @return coordinate first coordinate in whole path
     */
    public Coordinates getWholePathFirst(){
        return wholePath.get(0);
    }

    /**
     * Sets reference to circle the trolley belongs to
     * @param circle created and painted circle
     */
    public void addCircle(Circle circle){
        this.circle = circle;
    }

    /**
     * Updates coordinates on map according to path
     * when trolley is on a stop, sleeps for few seconds
     * when trolley stops and takes goods, info is updated
     * removes stop from path
     * @return boolean true if there are some items left to do
     */
    public boolean updateCoords() {
        if(sleep > 0){
            sleep--;
            return false;
        }
        if(coordinates == null) {
            coordinates = new Coordinates(startCoordinates.getX(), startCoordinates.getY());
        }
        if(path == null) {
            return false;
        }
        //dojel na konec cesty -> splnil objednavku
        //nastaveni defaultnich hodnot
        if(path.isEmpty()){
            usedCapacity = 0.0;
            storeGoodsStops = null;
            path = null;
            if(!order.getToDoList().isEmpty()){
                return true;
            }
            order = null;
            return false;
        }
        Coordinates wayToGo = path.get(0);
        //jsme na krizovatce/u zbozi
        if(wayToGo.getX() == coordinates.getX() && wayToGo.getY()==coordinates.getY()) {
            //update zbozi pokud je na zbozi
            //podivat se jestli jsme u zastavky kde vyzvedavame zbozi
            for(StoreGoods store :storeGoodsStops){
                if(store.getStopCoordinates().equals(wayToGo)){
                    //jsme u zbozi -> update informaci
                    int countToDo = order.getToDoList().get(store.getName());

                    if(order.getDoneList() == null) {
                        order.setDoneList(new HashMap<String, Integer>());
                    }
                    if(countToDo == store.getReadyToDispatch()){
                        store.setReadyToDispatch(0);
                        order.getToDoList().remove(store.getName());
                        if(!order.getDoneList().containsKey(store.getName())){
                            order.getDoneList().put(store.getName(), countToDo);
                        }else{
                            int countDone = order.getDoneList().get(store.getName());
                            order.getDoneList().replace(store.getName(), countDone + countToDo);
                        }
                        usedCapacity = usedCapacity + store.getItemWeight() * countToDo;
                    }
                    if(countToDo < store.getReadyToDispatch()){
                        store.setReadyToDispatch(store.getReadyToDispatch() - countToDo);
                        order.getToDoList().remove(store.getName());  //?
                        if(!order.getDoneList().containsKey(store.getName())){
                            order.getDoneList().put(store.getName(), countToDo);
                        }else{
                            int countDone = order.getDoneList().get(store.getName());
                            order.getDoneList().replace(store.getName(), countDone + countToDo);
                        }
                        usedCapacity = usedCapacity + store.getItemWeight() * countToDo;
                    }else{
                        usedCapacity = usedCapacity + store.getItemWeight() * store.getReadyToDispatch();
                        order.getToDoList().replace(store.getName(), countToDo - store.getReadyToDispatch());
                        if(!order.getDoneList().containsKey(store.getName())){
                            order.getDoneList().put(store.getName(), store.getReadyToDispatch());
                        }else{
                            int countDone = order.getDoneList().get(store.getName());
                            order.getDoneList().replace(store.getName(), countDone + store.getReadyToDispatch());
                        }
                        store.setReadyToDispatch(0);
                    }

                    //zastavit se
                    sleep = 20;
                }
            }
            path.remove(0);
            return false;

        //pohyb dal
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

        return false;
    }


}
