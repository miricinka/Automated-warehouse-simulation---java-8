package ija2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class representing Warehouse Data
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class WarehouseData {
    private DropSpot dropSpot;
    private LinkedList<Trolley> trolleys;
    private LinkedList<Isle> isles;
    private LinkedList<Order> orders;
    private ArrayList<String> goodsList = new ArrayList<>();

    public WarehouseData() {}

    public WarehouseData(DropSpot dropSpot, LinkedList<Trolley> trolleys, LinkedList<Isle> isles, LinkedList<Order> orders) {
        this.dropSpot = dropSpot;
        this.trolleys = trolleys;
        this.isles = isles;
        this.orders = orders;
    }


    /**
     * Returns dropspot in system
     * @return dropspot in system
     */
    public DropSpot getDropSpot() {
        return dropSpot;
    }

    /**
     * Sets dropspot
     * @param dropSpot of warehouse
     */
    public void setDropSpot(DropSpot dropSpot) {
        this.dropSpot = dropSpot;
    }

    /**
     * Returns list of trolleys in system
     * @return list of trolleys
     */
    public LinkedList<Trolley> getTrolleys() {
        return trolleys;
    }

    /**
     * Sets list of trolleys in system
     * @param trolleys in warehouse
     */
    public void setTrolleys(LinkedList<Trolley> trolleys) {
        this.trolleys = trolleys;
    }

    public void addTrolley(Trolley trolley){
        trolleys.add(trolley);
    }

    /**
     * Returns list of isles in system
     * @return list of isles in system
     */
    public LinkedList<Isle> getIsles() {
        return isles;
    }

    /**
     * Sets list of isles in system
     * @param isles list of isles
     */
    public void setIsles(LinkedList<Isle> isles) {
        this.isles = isles;
    }

    /**
     * Returns list of orders in system
     * @return list of orders in system
     */
    public LinkedList<Order> getOrders() {
        return orders;
    }

    public Order takeNextOrder(){
        if (orders.size() != 0){
            return orders.pollFirst();
        }
        return null;
    }

    /**
     * Sets list of orders
     * @param orders list of orders
     */
    public void setOrders(LinkedList<Order> orders) {
        this.orders = orders;
    }

    public void setGoodsList() {
        for (Isle isle : this.getIsles()){
            if (isle.getStoreGoodsList() != null) {
                for (StoreGoods storeGoods : isle.getStoreGoodsList()) {
                    String name = storeGoods.getName();
                    if(storeGoods.getItemsCount() == 0){
                        continue;
                    }
                    if(!goodsList.contains(name)){
                        goodsList.add(name);
                    }
                    //goodsList.add(name);
                }
            }
        }
    }

    public ArrayList<String> getGoodsList() {
        return goodsList;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public StoreGoods findGoods(String goodsName){
        for(Isle isle: isles){
            if(!isle.getClosed()){ //pokud neni zavrena
                if(isle.getStoreGoodsList() != null){
                    for(StoreGoods goods: isle.getStoreGoodsList()){
                        if(goods.getName().equals(goodsName)){
                            if(goods.getItemsCount() != 0){
                                return goods;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public StoreGoods findNextClosestGoods(Coordinates coordinates, HashMap<String, Integer> allGoods){
        double shortestDistance = 1000000000;
        StoreGoods closest = null;

        for (Map.Entry<String,Integer> entry : allGoods.entrySet()){
            StoreGoods goods = findGoods(entry.getKey());
            Double x1 = goods.getStopCoordinates().getX();
            Double y1 = goods.getStopCoordinates().getY();
            Double x2 = coordinates.getX();
            Double y2 = coordinates.getY();
            double distance=Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
            if(distance < shortestDistance) {
                shortestDistance = distance;
                closest = goods;
            }
        }
        return closest;
    }
}
