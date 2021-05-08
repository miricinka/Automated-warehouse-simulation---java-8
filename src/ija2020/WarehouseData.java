package ija2020;

import java.util.*;

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
    private ArrayList<String> goodsList;

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

    /**
     * Returns next order in system
     * @return next order in system
     */
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

    /**
     * creates list of available goods (sorted)
     */
    public List<StoreGoods> getGoodsStoredList(){
        List<StoreGoods> storeGoodsList = new ArrayList<>();
        for (Isle isle : this.getIsles()) {
            if (isle.getStoreGoodsList() != null) {
                storeGoodsList.addAll(isle.getStoreGoodsList());
            }
        }
        return storeGoodsList;
    }

    public void setGoodsList() {
        goodsList = new ArrayList<>();
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
                }
            }
        }
        java.util.Collections.sort(goodsList);
    }

    /**
     * Returns list of available goods
     * @return list of available goods
     */
    public ArrayList<String> getGoodsList() {
        return goodsList;
    }

    /**
     * Adds new order to system
     * @param order we want to add to system
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Finds goods in warehouse by name and returns it
     * @param goodsName name of goods we want to find
     * @return goods or null if count == 0 or is on a closed isle
     */
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

    /**
     * From map of goods returns the closest one based on coordinates
     * @param coordinates from where we find closest
     * @param allGoods map of goods
     * @return closest StoreGoods or null
     */
    public StoreGoods findNextClosestGoods(Coordinates coordinates, HashMap<String, Integer> allGoods){
        double shortestDistance = 1000000000;
        StoreGoods closest = null;

        for (Map.Entry<String,Integer> entry : allGoods.entrySet()){
            StoreGoods goods = findGoods(entry.getKey());
            if(goods == null) {
                return null;
            }
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

    public List<Isle> getIsleFromCoords (Coordinates searchedCoordinate){
        List<Isle> resultIsleList = new ArrayList<>();

        for (Isle isle: isles) {
            Coordinates coordinate1 = isle.getStart();
            Coordinates coordinate2 = isle.getEnd();


            // vertikalni ulicka
            if (coordinate1.getX() == coordinate2.getX()){

                if(searchedCoordinate.getX() != coordinate1.getX()){
                    // jsme ve vertikalni ulicce, ale cil je v horizontalni
                    continue;
                }

                double maxY, minY;
                if (coordinate1.getY() > coordinate2.getY()){
                    maxY = coordinate1.getY();
                    minY = coordinate2.getY();
                }else{
                    maxY = coordinate2.getY();
                    minY = coordinate1.getY();
                }

                if(searchedCoordinate.getY() <= maxY && searchedCoordinate.getY() >= minY){
                    resultIsleList.add(isle);
                }

            // horizontalni ulicka
            }else {
                if(searchedCoordinate.getY() != coordinate1.getY()){
                    // jsme v horizontalni ulicce, ale cil je v vertikalni
                    continue;
                }

                double maxX, minX;
                if (coordinate1.getX() > coordinate2.getX()){
                    maxX = coordinate1.getX();
                    minX = coordinate2.getX();
                }else{
                    maxX = coordinate2.getX();
                    minX = coordinate1.getX();
                }
                if(searchedCoordinate.getX() <= maxX && searchedCoordinate.getX() >= minX){
                    resultIsleList.add(isle);
                }
            }
        }

        return resultIsleList;
    }
}
