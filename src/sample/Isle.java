package sample;

import java.util.LinkedList;
import java.util.Objects;

public class Isle {
    private Coordinates start;
    private Coordinates end;
    private LinkedList<StoreGoods> storeGoodsList;
    private Boolean closed;

    public Isle(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
        this.closed = false;
        storeGoodsList = new LinkedList<>();
    }

    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public LinkedList<StoreGoods> getStoreGoodsList() {
        return storeGoodsList;
    }

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
