package ija2020;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static javafx.scene.paint.Color.*;

/**
 * Main controller of application
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */
public class Controller {
    @FXML
    private AnchorPane textPanel;
    @FXML
    private Pane mainPane;
    @FXML
    private Slider speedSlider;

    private WarehouseData warehouseData;
    private Rectangle lastClickedShelf;
    private Circle lastClickedTrolley;
    private ArrayList<Line> paintedPath;
    private ArrayList<Circle> paintedCircles;
    private ArrayList<String> allGoodsList;
    private Timer timer;
    private Timer timer2;
    private boolean stopClicked = false;
    private boolean playClicked = true;
    private long simulationSpeed;

    public void setWarehouseData(WarehouseData warehouseData) {
        this.warehouseData = warehouseData;
    }

    /**
     * Sets list of avaiable goods
     */
    public void setAllGoodsList(){
        allGoodsList = new ArrayList<>();
        warehouseData.setGoodsList();
        allGoodsList = warehouseData.getGoodsList();
    }

    /**
     * Removes last painted things
     * Used when user clicks on something else
     */
    public void removeLastClickedStuff(){
        if(paintedPath != null){
            for(Line line:paintedPath){
                mainPane.getChildren().remove(line); }
        }
        if(paintedCircles != null){
            for(Circle stop: paintedCircles){
                mainPane.getChildren().remove(stop);
            }
        }
        if (lastClickedShelf != null) {
            lastClickedShelf.setFill(PERU);
        }
        if (lastClickedTrolley != null) {
            lastClickedTrolley.setFill(RED);
        }
        lastClickedTrolley = null;
    }


    /**
     * Paints all Isles and Goods from warehouseData
     * Shows info on mouse entered and exited
     */
    public void paintIsles() {

        Text storeGoodsInfo = new Text(10, 20, "");

        for(Isle isle : warehouseData.getIsles()){
            //spocitani ceny ulicky
            isle.setCost();
            Line line = new Line(isle.getStart().getX(), isle.getStart().getY(), isle.getEnd().getX(), isle.getEnd().getY());
            line.setStrokeWidth(6);
            line.setStroke(SLATEGRAY);
            mainPane.getChildren().add(line);

            line.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(isle.getClosed()){
                        isle.setOpen();
                        line.setStroke(SLATEGRAY);
                        System.out.println("Otevreni ulicky" + isle);

                    }else{
                        isle.setClosed();
                        line.setStroke(RED);
                        System.out.println("Uzavreni ulicky" + isle);
                    }
                    //System.out.println("Ulicka" + isle);
                }
            });

            if (isle.getStoreGoodsList() != null) {
                for (StoreGoods storeGoods : isle.getStoreGoodsList()) {
                    //store goods vi na ktere je isle
                    storeGoods.setIsle(isle);
                    Rectangle shelf = new Rectangle(storeGoods.getCoordinates().getX(), storeGoods.getCoordinates().getY(), 20, 40);
                    shelf.setStroke(BLACK);
                    shelf.setStrokeWidth(2);
                    shelf.setFill(PERU);
                    mainPane.getChildren().add(shelf);

                    shelf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            removeLastClickedStuff();
                            lastClickedShelf = shelf;
                            shelf.setFill(LIME);
                            textPanel.getChildren().clear();
                            textPanel.getChildren().add(storeGoodsInfo);
                            storeGoodsInfo.setText("Obsah: " + storeGoods.getName() + "\nHmotnost: " + storeGoods.getItemWeight() + "kg" +"\nZbývá: " + storeGoods.getItemsCount() + "\nK vyzvednutí: " + storeGoods.getReadyToDispatch());
                        }
                    });
                }
            }
        }
    }

    public void updateTrolleyInfo (Trolley trolley){
        Text trolleyInfo = new Text(10, 20, "");


        textPanel.getChildren().clear();
        textPanel.getChildren().add(trolleyInfo);
        String trolleyString = "";
        trolleyString = "ID: " + trolley.getId() +"\nMaximální kapacita: " + trolley.getCapacity() + "kg\nVyužitá kapacita: " + trolley.getUsedCapacity() + "kg\n\n";
        if(trolley.getOrder() != null){
            Order order = trolley.getOrder();
            String todoList = "     Zbývá: \n";
            String doneList = "     Hotovo: \n";
            if(order.getToDoList() != null){
                for (Map.Entry<String,Integer> entry : order.getToDoList().entrySet()){
                    todoList = todoList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
                }
            }
            if(order.getDoneList() != null){
                for (Map.Entry<String,Integer> entry : order.getDoneList().entrySet()){
                    doneList = doneList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
                }
            }

            trolleyString = trolleyString + "Objednávka ID :" + order.getId() + "\n"+ todoList + doneList;
        }
        trolleyInfo.setText(trolleyString);
    }

    public void updateTrolleyPath (Trolley trolley) {
        removeLastClickedStuff();

        lastClickedTrolley = trolley.circulusMaximus();
        trolley.circulusMaximus().setFill(LIME);
        //zvyrazneni trasy
        if(trolley.getPath() != null) {
            Coordinates start = null;
            Coordinates end = trolley.getWholePathFirst();
            paintedPath = new ArrayList<>();
            for(Coordinates coordinates: trolley.getWholePath()){
                start = end;
                end = coordinates;
                if(start != null && end != null){
                    Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
                    paintedPath.add(line);
                    line.setStrokeWidth(4);
                    line.setStroke(LIME);
                    mainPane.getChildren().add(line);
                }
            }
            //nakresleni bodu zastavky
            if(trolley.getStoreGoodsStops() != null){
                paintedCircles = new ArrayList<>();
                for(StoreGoods stop: trolley.getStoreGoodsStops()){
                    Circle stopCircle= new Circle(stop.getStopCoordinates().getX(), stop.getStopCoordinates().getY(), 4);
                    stopCircle.setFill(RED);
                    //pridani do seznamu bodu, aby se mohl pote vymazat
                    paintedCircles.add(stopCircle);
                    mainPane.getChildren().add(stopCircle);
                }
            }
        }
    }

    /**
     * Paints all Trolleys from warehouseData
     * Shows info on mouse entered and exited
     */
    public void paintTrolleys() {

//        Text trolleyInfo = new Text(10, 20, "");

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getStartCoordinates().getX(), trolley.getStartCoordinates().getY(), 9);
            circle.setFill(RED);
            mainPane.getChildren().add(circle);
            trolley.addCircle(circle);

            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    updateTrolleyPath(trolley);
                    updateTrolleyInfo(trolley);

                }
            });

        }
    }

    /**
     * On button clicked shows all orders in system
     * for now to terminal
     */
    @FXML
    public void btn1Clicked() {
        removeLastClickedStuff();

        textPanel.getChildren().clear();
        Text info = new Text(10, 20, "");
        textPanel.getChildren().add(info);

        String textToView = "";
        for (Order order: warehouseData.getOrders()) {
            textToView = textToView.concat(order.getId() + ":\n");
            for (HashMap.Entry<String,Integer> entry : order.getToDoList().entrySet()){
                textToView = textToView.concat("    "+ entry.getKey() +" "+entry.getValue() + "x\n");
            }
            textToView = textToView + "\n";
        }
        String trolleyString = "";
        for (Trolley trolley: warehouseData.getTrolleys()) {
            //trolleyString = "ID: " + trolley.getId();
            if(trolley.getOrder() != null){
                Order order = trolley.getOrder();
                String todoList = "     Zbývá: \n";
                String doneList = "     Hotovo: \n";
                if(order.getToDoList() != null){
                    for (Map.Entry<String,Integer> entry : order.getToDoList().entrySet()){
                        todoList = todoList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
                    }
                }
                if(order.getDoneList() != null){
                    for (Map.Entry<String,Integer> entry : order.getDoneList().entrySet()){
                        doneList = doneList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
                    }
                }

                trolleyString = trolleyString + order.getId() +"\n Vozík: " + trolley.getId() +"\n"+ todoList + doneList + "\n";
            }

        }
        info.setText(trolleyString+textToView);
    }

    /**
     * On button Add new Order clicked creates new window with listview where user can add new order
     */
    @FXML
    public void btn2Clicked() {
        removeLastClickedStuff();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newOrder.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewOrderController newOrderController = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Nová objednávka");
        stage.setScene(new Scene(root1));

        this.setAllGoodsList();

        Map<String, Integer> superStoreMap = new HashMap<>();
        for (String name:allGoodsList) {
            superStoreMap.put(name, 0);
        }

        for (StoreGoods storeGoods:warehouseData.getGoodsStoredList()){
            superStoreMap.replace(storeGoods.getName(),superStoreMap.get(storeGoods.getName()),superStoreMap.get(storeGoods.getName()) + storeGoods.getItemsCount());
        }

        for (Order order:warehouseData.getOrders()){
            for (Map.Entry<String, Integer> entry:order.getToDoList().entrySet()) {
                superStoreMap.replace(entry.getKey(), superStoreMap.get(entry.getKey()), superStoreMap.get(entry.getKey()) - entry.getValue());
            }
        }

        for(String name: allGoodsList) {
            newOrderController.loadOrderList(name, superStoreMap.get(name));
        }


        newOrderController.setWarehouseData(warehouseData);

        stage.show();
    }

    /**
     * On scroll zooms main pane with warehouse
     */
    @FXML
    public void zoom(ScrollEvent event) {
        double scroll = event.getDeltaY();
        double zoomScale;
        if (scroll > 0){
            zoomScale = 1.1;
            if (mainPane.getScaleX() > 2.5){
               return;
            }
        } else {
            zoomScale = 0.9;
            if (mainPane.getScaleX() < 1){
                return;
            }
        }
        mainPane.setScaleX(zoomScale * mainPane.getScaleX());
        mainPane.setScaleY(zoomScale * mainPane.getScaleY());
    }

    /**
     * Stops running trolley
     */
    @FXML
    public void stopButtonClicked(){
        if(stopClicked){
            return;
        }
        timer.cancel();
        stopClicked = true;
        playClicked = false;
    }

    /**
     * Trolley starts running
     */
    @FXML
    public void playButtonClicked(){
        if(playClicked){
            return;
        }
        simulationTime();
        playClicked = true;
        stopClicked = false;
    }

    /**
     * Returns simulation to begin state
     */
    @FXML
    public void replayButtonClicked() throws IOException {
        YAMLFactory yaml_factory = new YAMLFactory();
        ObjectMapper obj_mapper = new ObjectMapper(yaml_factory);
        WarehouseData warehouseData = obj_mapper.readValue(new File("data/dropspot4.yml"), WarehouseData.class);
        stopButtonClicked();
        mainPane.getChildren().clear();
        this.warehouseData = warehouseData;
        paintIsles();
        paintTrolleys();
        setAllGoodsList();
        playButtonClicked();
    }

    /**
     * Setups speed to 1
     */
    public void setupSpeed(){
        simulationSpeed = 1;
    }

    /**
     * changes speed based on slider
     */
    public void changeSpeed(){
        simulationSpeed = (long) speedSlider.getValue() * 3;
        timer.cancel();
        simulationTime();
        if(stopClicked == true){
            timer.cancel();
        }
    }

    /**
     * Calculates path for trolley to finish its order
     * if max capacity is filled, return to dropspot
     */
    public List<Coordinates> calculatePath(Order order, Trolley trolley){
        double usedCapacity = 0.0;
        //trolley.setUsedCapacityCount(0.0);
        //seznam zastavek pro pozdejsi aktualizace obsahu kdyz k nim vozik prijede
        List<Coordinates> orderPath = new ArrayList<>();
        List<StoreGoods> storeGoodsStops = new ArrayList<>();
        Coordinates startCoordinates = warehouseData.getDropSpot().getCoordinates();
        Path open = new Path();
        Path closed = new Path();
        boolean returnToDropSpot = false;
        Coordinates nextCoordinates;

        Isle nextStopIsle;
        List<Isle> possibleNextIsles;

        possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);
        open.addIsleSegment(possibleNextIsles.get(0), 0);

        //find closest goods
        HashMap<String, Integer> allGoods = new HashMap<>(order.getToDoList());
        while(!returnToDropSpot){

            if (allGoods.isEmpty()) {
                returnToDropSpot = true;
                nextCoordinates = orderPath.get(0);
            }else {
                StoreGoods closest = null;
                if (usedCapacity < trolley.getCapacity()) {
                    closest = warehouseData.findNextClosestGoods(startCoordinates, allGoods);
                }

                //vse je ve voziku -> vymazat ze seznamu
                if(!allGoods.isEmpty()){
                    allGoods.remove(closest.getName());
                }
                storeGoodsStops.add(closest);
                //je tu vse co chceme -> smazat ze seznamu
                if (closest.getItemsCount() >= allGoods.get(closest.getName())) {
                    //dokud je pocet co chceme vetsi nez 0
                    while (allGoods.get(closest.getName()) > 0) {
                        usedCapacity += closest.getItemWeight();
                        //zbozi se nevleze do voziku
                        if (usedCapacity > trolley.getCapacity()) {
                            //zbozi nedat do voziku
                            System.out.println("Zbozi se nevleze do voziku 1");
                            usedCapacity -= closest.getItemWeight();
                            allGoods.clear();
                            break;

                        } else {//zbozi se vleze do voziku
                            closest.setItemsCount(closest.getItemsCount() - 1);
                            closest.setReadyToDispatch(closest.getReadyToDispatch() + 1);
                        }

                        //zmena seznamu zbozi -1
                        allGoods.replace(closest.getName(), allGoods.get(closest.getName()) - 1);
                    }
                    //pricist vahu
                    //trolley.setUsedCapacityCount(trolley.getUsedCapacityCount() + allGoods.get(closest.getName())*closest.getItemWeight());
                    //odecist co jsme vzali
                    //closest.setItemsCount(closest.getItemsCount() - allGoods.get(closest.getName()));
                    //closest.setReadyToDispatch(closest.getReadyToDispatch() + allGoods.get(closest.getName()));

                    //vse je ve voziku -> vymazat ze seznamu
                    if (!allGoods.isEmpty()) {
                        allGoods.remove(closest.getName());
                    }


                } else { //neni vse co chceme -> vezmeme co muzem a nastavime na 0 -> v listu zustane zbytek co musime hledat jinde
                    //pricist vahu
                    int countLeft = allGoods.get(closest.getName()) - closest.getItemsCount();
                    while (allGoods.get(closest.getName()) > countLeft) {
                        usedCapacity += closest.getItemWeight();
                        //zbozi se nevleze do voziku
                        if (usedCapacity > trolley.getCapacity()) {
                            //zbozi nedat do voziku
                            System.out.println("Zbozi se nevleze do voziku 2");
                            usedCapacity -= closest.getItemWeight();
                            allGoods.clear();
                            break;
                        } else {//zbozi se vleze do voziku
                            closest.setItemsCount(closest.getItemsCount() - 1);
                            closest.setReadyToDispatch(closest.getReadyToDispatch() + 1);
                        }
                        //zmena seznamu zbozi -1
                        allGoods.replace(closest.getName(), allGoods.get(closest.getName()) - 1);
                    }
                    //allGoods.clear();
                    //trolley.setUsedCapacityCount(trolley.getUsedCapacityCount() + closest.getItemsCount()*closest.getItemWeight());
                    //closest.setReadyToDispatch(closest.getItemsCount() + closest.getReadyToDispatch());
                    //allGoods.replace(closest.getName(), countLeft);
                    //closest.setItemsCount(0);
                }
                nextCoordinates = closest.getStopCoordinates();
            }
                //hledat odkud jsem ted

                nextStopIsle = warehouseData.getIsleFromCoords(nextCoordinates).get(0);
                possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);
                Coordinates coordinate1 = new Coordinates(startCoordinates.getX(), startCoordinates.getY());


            while (!nextStopIsle.getStart().equals(startCoordinates) && !nextStopIsle.getEnd().equals(startCoordinates)){
                System.out.println("Current coordinate: " + startCoordinates); //TODO delete me
                System.out.println("Open: " + open + " End of open"); //TODO delete me
                System.out.println("Closed: " + closed + " End of closed"); //TODO delete me

                // skip if we are at the isle of the searched coordinates
                if (nextStopIsle.equals(warehouseData.getIsleFromCoords(startCoordinates).get(0))){
                    break;
                }

                // add new coordinates to open
                for (Isle neighborIsle:possibleNextIsles) {
                    Coordinates possibleNextCoordinate;


                    // Ignore closed isles
                    if(neighborIsle.getClosed()){
                        System.out.println("Ignoruju ulicku " + neighborIsle + " protoze je zavrena.");
                        continue;
                    }

                    if (closed.isEmpty()){
                        double distanceStart = neighborIsle.getStart().calcDistance(nextCoordinates) + neighborIsle.getCost();
                    }

                    // find new coordinate
                    if(neighborIsle.getStart().equals(startCoordinates)){
                        possibleNextCoordinate = neighborIsle.getEnd();
                    }else if(neighborIsle.getEnd().equals(startCoordinates)) {
                        possibleNextCoordinate = neighborIsle.getStart();
                    }else {
                        double distanceStart = neighborIsle.getStart().calcDistance(nextCoordinates);
                        double distanceEnd = neighborIsle.getEnd().calcDistance(nextCoordinates);
                        open.addCoordinateSegment(startCoordinates, neighborIsle.getStart(), distanceStart);
                        open.addCoordinateSegment(startCoordinates, neighborIsle.getEnd(), distanceEnd);
                        continue;
                    }

                    // Ignore isles that are in closed list
                    if(closed.isCoordinateInPath(possibleNextCoordinate)){
                        continue;
                    }

                    // save new coordinate
                    double distance = possibleNextCoordinate.calcDistance(nextCoordinates) + neighborIsle.getCost();
                    open.addCoordinateSegment(startCoordinates, possibleNextCoordinate, distance);

                    if(open.isEmpty()){
                        System.out.println("No valid path - list Open is empty!"); //TODO edit me
                    }
                }


                PathSegment closestPathSegment = open.getLowestCostPathSegment();

                if(!closed.isPathSegmentInPath(closestPathSegment)) {
                    closed.addPathSegment(closestPathSegment);
                }

                open.removeSegment(closestPathSegment);

                startCoordinates = closestPathSegment.getEnd();
                possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);
            }
            closed.addCoordinateSegment(startCoordinates, nextCoordinates, 1);
            startCoordinates = nextCoordinates;


            for (Coordinates coordinate: closed.backtrackPath(startCoordinates, coordinate1)){
                if (orderPath.isEmpty()){
                    orderPath.add(coordinate);
                    continue;
                }
                if (!orderPath.get(orderPath.size()-1).equals(coordinate)){
                    orderPath.add(coordinate);
                }

            }
//            orderPath.addAll(closed.backtrackPath(startCoordinates, coordinate1));
            open = new Path();
            closed = new Path();
        }

        //way  back home
        trolley.setStoreGoodsStops(storeGoodsStops);
        System.out.println(trolley.getUsedCapacityCount());
        System.out.println("Backtrack path is" + orderPath);
        return orderPath;
    }

    /**
     * Creates simulation time and start running trolleys, calculates path for trolley if he didnt finish his order because of capacity
     */
    public void simulationTime() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Trolley trolley: warehouseData.getTrolleys()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int result = trolley.updateCoords();
                            if(result == 1){
                                List<Coordinates> path = calculatePath(trolley.getOrder(), trolley);
                                trolley.setPath(path);
                                trolley.setWholePath();
                                if (lastClickedTrolley != null){
                                    if (lastClickedTrolley == trolley.circulusMaximus()){
                                        updateTrolleyPath(trolley);
                                        updateTrolleyInfo(trolley);
                                    }
                                }else {
                                    btn1Clicked();
                                }
                            }else if(result == 2){
                                if (lastClickedTrolley != null){
                                    if (lastClickedTrolley == trolley.circulusMaximus()){
                                        updateTrolleyInfo(trolley);
                                    }
                                }else {
                                    btn1Clicked();
                                }
                            }
                        }
                    });
                    //Platform.runLater(()->trolley.updateCoords());
                }
            }
        }, 0, 100/simulationSpeed);
    }

    /**
     * Every 3 seconds checks if there are orders and trolleys -> gives order to trolley
     */
    public void checkForOrder() {
        timer2 = new Timer(false);
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Trolley trolley: warehouseData.getTrolleys()) {
                    if(trolley.getOrder() == null) {
                        Order order = warehouseData.takeNextOrder();
                        if(order != null){
                            Platform.runLater(()->trolley.setOrder(order));
                            List<Coordinates> path = calculatePath(order, trolley);
                            Platform.runLater(()->trolley.setPath(path));
                            Platform.runLater(()->trolley.setWholePath());
                            if (lastClickedTrolley != null){
                                if (lastClickedTrolley == trolley.circulusMaximus()){

                                    Platform.runLater(()->updateTrolleyPath(trolley));
                                }
                            }
                        }
                    }
                }
            }
        }, 0, 3000);
    }

}
