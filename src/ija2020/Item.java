package ija2020;


/**
 * Item class
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */

public class Item {
    private StoreGoods store;

    public Item() {}

    public Item(StoreGoods type){
        this.store = type;
    }

    /**
     * Returns StoreGoods the item belongs to
     * @return store
     */
    public StoreGoods getStore() {
        return store;
    }

    /**
     * Sets storeGoods the item belongs to
     * @param store StoreGoods
     */
    public void setStore(StoreGoods store) {
        this.store = store;
    }
}
