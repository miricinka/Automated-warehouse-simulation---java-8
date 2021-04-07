package sample;

import java.util.LinkedList;

public class WarehouseData {
    private DropSpot dropSpot;
    private LinkedList<Trolley> trolleys;
    private LinkedList<Isle> isles;
    private LinkedList<StoreGoods> storeGoods;
    private LinkedList<Order> orders;

    public WarehouseData(DropSpot dropSpot, LinkedList<Trolley> trolleys, LinkedList<Isle> isles, LinkedList<StoreGoods> storeGoods, LinkedList<Order> orders) {
        this.dropSpot = dropSpot;
        this.trolleys = trolleys;
        this.isles = isles;
        this.storeGoods = storeGoods;
        this.orders = orders;
    }

    public DropSpot getDropSpot() {
        return dropSpot;
    }

    public void setDropSpot(DropSpot dropSpot) {
        this.dropSpot = dropSpot;
    }

    public LinkedList<Trolley> getTrolleys() {
        return trolleys;
    }

    public void setTrolleys(LinkedList<Trolley> trolleys) {
        this.trolleys = trolleys;
    }

    public LinkedList<Isle> getIsles() {
        return isles;
    }

    public void setIsles(LinkedList<Isle> isles) {
        this.isles = isles;
    }

    public LinkedList<StoreGoods> getStoreGoods() {
        return storeGoods;
    }

    public void setStoreGoods(LinkedList<StoreGoods> storeGoods) {
        this.storeGoods = storeGoods;
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<Order> orders) {
        this.orders = orders;
    }
}
