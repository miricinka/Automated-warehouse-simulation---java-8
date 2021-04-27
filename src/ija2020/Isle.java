package ija2020;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Class Isle represents one isle in warehouse with list of storeGoods
 * isle can be closed
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Isle {
    private Coordinates start;
    private Coordinates end;
    private Boolean closed;
    private LinkedList<StoreGoods> storeGoodsList;

    public Isle() {}

    public Isle(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
        this.closed = false;
        storeGoodsList = new LinkedList<>();
    }

    /**
     * Returns start coordinate
     * @return start coordinate
     */
    public Coordinates getStart() {
        return start;
    }

    /**
     * Sets coordinate start
     * @param start coordinate
     */
    public void setStart(Coordinates start) {
        this.start = start;
    }

    /**
     * Returns end coordinate
     * @return end coordinate
     */
    public Coordinates getEnd() {
        return end;
    }

    /**
     * Sets coordinate end
     * @param end coordinate
     */
    public void setEnd(Coordinates end) {
        this.end = end;
    }

    /**
     * Returns boolean closed
     * @return closed
     */
    public Boolean getClosed() {
        return closed;
    }

    /**
     * Sets if isle is closed or not
     * @param closed boolean
     */
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    /**
     * Returns storeGoodsList
     * @return storeGoodsList list of storeGoods on Isle
     */
    public LinkedList<StoreGoods> getStoreGoodsList() {
        return storeGoodsList;
    }

    /**
     * Sets list of goods on isle
     * @param storeGoodsList on isle
     */
    public void setStoreGoodsList(LinkedList<StoreGoods> storeGoodsList) {
        this.storeGoodsList = storeGoodsList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Isle)) return false;
        Isle isle = (Isle) o;
        return Objects.equals(start, isle.start) && Objects.equals(end, isle.end) && Objects.equals(storeGoodsList, isle.storeGoodsList) && Objects.equals(closed, isle.closed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, storeGoodsList, closed);
    }
}
