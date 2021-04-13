package ija2020;

import sun.tools.tree.OrExpression;

import java.util.LinkedList;

public class Path {
    private LinkedList<Coordinates> pathList;

    public Path(LinkedList<Coordinates> pathList) {
        this.pathList = pathList;
    }

    public Coordinates getFirstCoordinate(){
        return pathList.getFirst();
    }

    public Coordinates removeFirstCoordinate(){
        return pathList.pollFirst();
    }

    //public LinkedList<Coordinates> findPath(Coordinates trolleyCoordinates, Order currentOrder, LinkedList<Isle> allIsles){

    //}
}
