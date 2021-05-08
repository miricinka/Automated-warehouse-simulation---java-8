package ija2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

    /**
     * Checks for a segment in this Path
     * @param segment segment to search
     * @return true if segment is in this path, else false
     */
    public boolean isPathSegmentInPath (PathSegment segment){
        for (PathSegment currentPathSegment:pathList) {
            if (segment.equals(currentPathSegment) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a coordinate is in any segment of this path
     * @param coordinate coordinate to search
     * @return true if coordinate is in any segment of this Path, else false
     */
    public boolean isCoordinateInPath(Coordinates coordinate){

        for (PathSegment currentPathSegment:pathList) {
            if (coordinate.equals(currentPathSegment.getStart()) || coordinate.equals(currentPathSegment.getEnd())){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a segment from two coordinates and cost
     * @param isle isle from which the segment will get start and end coordinates
     * @param cost cost of the segment
     */
    public void addIsleSegment (Isle isle, double cost) {
        pathList.add(new PathSegment(isle, cost));
    }

    /**
     * Adds a segment from two coordinates and cost
     * @param start start coordinate of the segment
     * @param end end coordinate of the segment
     * @param cost cost of the segment
     */
    public void addCoordinateSegment (Coordinates start, Coordinates end, double cost){
        pathList.add(new PathSegment(start, end, cost));
    }

    /**
     * Adds a segment to this Path
     * @param segment segment to be added
     */
    public void addPathSegment (PathSegment segment){
        pathList.add(segment);
    }

    /**
     * Adds a segment from two coordinates and cost
     * @param segment segment to be removed
     */
    public void removeSegment (PathSegment segment){
        pathList.remove(segment);
    }

    /**
     * Finds the segment with lowest cost
     * @return segment with the lowest cost
     */
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

    /**
     * Creates final path of coordinates from pathSegments in this Path
     * @param endCoordinate final coordinate where the backtracking begins
     * @param startCoordinate start coordinate from where the path was calculated
     * @return list of coordinates
     */
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

    /**
     * Checks if this Path is empty
     */
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
