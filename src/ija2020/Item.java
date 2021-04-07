package ija2020;

public class Item {
    private StoreGoods store;
    private double weight;

    public Item() {}

    public Item(StoreGoods type, double weight) throws Exception {
        if (weight <= 0.0)
            throw new Exception("Weight must be > 0");
        this.store = type;
        this.weight = weight;
    }

    public StoreGoods getStore() {
        return store;
    }

    public void setStore(StoreGoods store) {
        this.store = store;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) throws Exception {
        if (weight <= 0.0)
            throw new Exception("Weight must be > 0");
        this.weight = weight;
    }
}
