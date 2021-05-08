package ija2020;

import java.util.Objects;

public class PathSegment {
    private Coordinates start;
    private Coordinates end;
    private double cost;

    public PathSegment() { }

    public PathSegment(Coordinates start, Coordinates end, double cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public PathSegment(Isle isle,  double cost) {
        this.start = isle.getStart();
        this.end = isle.getEnd();
        this.cost = cost;
    }

    /**
     * Returns start coordinate
     * @return segment start coordinate
     */
    public Coordinates getStart() {return start; }

    /**
     * Returns end coordinate
     * @return segment enf coordinate
     */
    public Coordinates getEnd() {return end; }

    /**
     * Returns cost
     * @return cost
     */
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return "PathSegment{" +
                "start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathSegment)) return false;
        PathSegment that = (PathSegment) o;
        return Double.compare(that.cost, cost) == 0 && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, cost);
    }
}
