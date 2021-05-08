package ija2020;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Path class represents the way trolley has to go in order to pick up items
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Path {
    private ArrayList<PathSegment> pathList;

    public Path() {
        this.pathList = new ArrayList<>();
    }

    public boolean isPathSegmentInPath (PathSegment segment){
        for (PathSegment currentPathSegment:pathList) {
            if (segment.equals(currentPathSegment) ){
                return true;
            }
        }
        return false;
    }

    public boolean isCoordinateInPath(Coordinates coordinate){

        for (PathSegment currentPathSegment:pathList) {
            if (coordinate.equals(currentPathSegment.getStart()) || coordinate.equals(currentPathSegment.getEnd())){
                return true;
            }
        }
        return false;
    }

    public void addIsleSegment (Isle isle, double cost) {
        pathList.add(new PathSegment(isle, cost));
    }

    public void addCoordinateSegment (Coordinates start, Coordinates end, double cost){
        pathList.add(new PathSegment(start, end, cost));
    }

    public void addPathSegment (PathSegment segment){
        pathList.add(segment);
    }

    public void removeSegment (PathSegment segment){
        pathList.remove(segment);
    }

    public PathSegment getLowestCostPathSegment () {
        double lowestCost = Double.POSITIVE_INFINITY;
        PathSegment lowestCostSegment = null;

        for (PathSegment currentPathSegment:pathList) {
            if (currentPathSegment.getCost() < lowestCost){
                lowestCost = currentPathSegment.getCost();
                lowestCostSegment = new PathSegment(currentPathSegment.getStart(), currentPathSegment.getEnd(), currentPathSegment.getCost());
            }
        }

        if (lowestCostSegment == null){
            System.out.println("Error: List open is empty");
        }
        return lowestCostSegment;
    }

    public ArrayList<Coordinates> backtrackPath(Coordinates endCoordinate, Coordinates startCoordinate){
        ArrayList<Coordinates> path = new ArrayList<>();
        Coordinates currentCoordinate = endCoordinate;

        path.add(endCoordinate);

        while (!currentCoordinate.equals(startCoordinate)){
            for (PathSegment currentSegment:pathList) {
                System.out.println("\t" + currentSegment);
                if(currentSegment.getEnd().equals(currentCoordinate)){
                    currentCoordinate = currentSegment.getStart();
                    break;
                }
            }
            path.add(currentCoordinate);
        }
        Collections.reverse(path);
        System.out.println(path + "\n");
        return path;
    }

    public boolean isEmpty (){
        return pathList.isEmpty();
    }

    @Override
    public String toString() {
        return "Path{" +
                "pathList=" + pathList +
                '}';
    }
}
