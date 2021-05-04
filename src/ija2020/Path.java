package ija2020;

import java.util.LinkedList;

/**
 * Path class represents the way trolley has to go in order to pick up items
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Path {
    private LinkedList<Coordinates> pathList;

    public Path() {
    }

    /**
     * Returns list of all coordinates in path
     * @return pathList list of coordinates in path
     */
    public LinkedList<Coordinates> getPathList() {
        return pathList;
    }

    /**
     * Sets list of all coordinates in path
     * @param pathList list of all coordinates in path
     */
    public void setPathList(LinkedList<Coordinates> pathList) {
        this.pathList = pathList;
    }

    /**
     * Returns first coordinate in path
     * @return first coordinate
     */
    public Coordinates getFirstCoordinate(){
        return pathList.getFirst();
    }

    /**
     * Returns and deletes first coordinate in path
     * @return first coordinate
     */
    public Coordinates removeFirstCoordinate(){
        return pathList.pollFirst();
    }

}
