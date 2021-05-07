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

    public void setAllGoodsList(){
        allGoodsList = new ArrayList<>();
        warehouseData.setGoodsList();
        allGoodsList = warehouseData.getGoodsList();
    }

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

    /**
     * Paints all Trolleys from warehouseData
     * Shows info on mouse entered and exited
     */
    public void paintTrolleys() {

        Text trolleyInfo = new Text(10, 20, "");

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getStartCoordinates().getX(), trolley.getStartCoordinates().getY(), 9);
            circle.setFill(RED);
            mainPane.getChildren().add(circle);
            trolley.addCircle(circle);

            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    removeLastClickedStuff();

                    lastClickedTrolley = circle;
                    circle.setFill(LIME);
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
            });

        }
    }

    /**
     * On button clicked shows all orders in system
     * for now to terminal
     */
    @FXML
    public void btn1Clicked() {
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
        for(String name: allGoodsList) {
            newOrderController.loadOrderList(name);
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

    @FXML
    public void stopButtonClicked(){
        if(stopClicked == true){
            return;
        }
        timer.cancel();
        stopClicked = true;
        playClicked = false;
    }

    @FXML
    public void playButtonClicked(){
        if(playClicked == true){
            return;
        }
        simulationTime();
        playClicked = true;
        stopClicked = false;
    }

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


    public void setupSpeed(){
        simulationSpeed = 1;
    }

    public void changeSpeed(){
        simulationSpeed = (long) speedSlider.getValue() * 3;
        timer.cancel();
        simulationTime();
        if(stopClicked == true){
            timer.cancel();
        }
    }

    public List<Coordinates> calculatePath(Order order, Trolley trolley){
        double usedCapacity = 0.0;
        //trolley.setUsedCapacityCount(0.0);
        boolean endThisOrder = false;
        List<Coordinates> listCoords = new ArrayList<>();
        //seznam zastavek pro pozdejsi aktualizace obsahu kdyz k nim vozik prijede
        List<StoreGoods> storeGoodsStops = new ArrayList<>();
        Coordinates startCoordinates = warehouseData.getDropSpot().getCoordinates();
        listCoords.add(startCoordinates);

        Isle nextStopIsle = null;
        List<Isle> possibleNextIsles;

        possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);

        //find closest goods
        HashMap<String, Integer> allGoods = new HashMap<>(order.getToDoList());
        while(!allGoods.isEmpty()){
            StoreGoods closest = null;
            if(usedCapacity < trolley.getCapacity()){
                closest = warehouseData.findNextClosestGoods(startCoordinates, allGoods);
            }
            if(closest == null){
                System.out.println("V systemu neni dostatek zbozi z objednavky pro " + order.getId() +"!");
                break;
            }
            storeGoodsStops.add(closest);
            //je tu vse co chceme -> smazat ze seznamu
            if(closest.getItemsCount() >= allGoods.get(closest.getName())){
                //dokud je pocet co chceme vetsi nez 0
                while(allGoods.get(closest.getName()) > 0){
                    usedCapacity += closest.getItemWeight();
                    //zbozi se nevleze do voziku
                    if(usedCapacity > trolley.getCapacity()){
                        //zbozi nedat do voziku
                        System.out.println("Zbozi se nevleze do voziku 1");
                        usedCapacity -= closest.getItemWeight();
                        allGoods.clear();
                        break;

                    }else{//zbozi se vleze do voziku
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
                if(!allGoods.isEmpty()){
                    allGoods.remove(closest.getName());
                }


            }else{ //neni vse co chceme -> vezmeme co muzem a nastavime na 0 -> v listu zustane zbytek co musime hledat jinde
                //pricist vahu
                int countLeft = allGoods.get(closest.getName())- closest.getItemsCount();
                while(allGoods.get(closest.getName()) > countLeft){
                    usedCapacity += closest.getItemWeight();
                    //zbozi se nevleze do voziku
                    if(usedCapacity > trolley.getCapacity()){
                        //zbozi nedat do voziku
                        System.out.println("Zbozi se nevleze do voziku 2");
                        usedCapacity -= closest.getItemWeight();
                        allGoods.clear();
                        break;
                    }else{//zbozi se vleze do voziku
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

            //hledat odkud jsem ted
            Coordinates nextCoordinates = closest.getStopCoordinates();
            nextStopIsle = warehouseData.getIsleFromCoords(nextCoordinates).get(0);

            while (!nextStopIsle.getStart().equals(startCoordinates) && !nextStopIsle.getEnd().equals(startCoordinates)){
                if (nextCoordinates.getX() == startCoordinates.getX()){
                    break;
                }

                double closestNextCoordDistance = Double.POSITIVE_INFINITY;
                Coordinates closestNextCoord = null;

                for (Isle neighborIsle:possibleNextIsles) {
                    if(neighborIsle.getClosed()){
                        System.out.println("Ignoruju ulicku " + neighborIsle + " protoze je zavrena.");
                        continue;
                    }

                    double distanceStart = neighborIsle.getStart().calcDistance(nextCoordinates) + neighborIsle.getCost();
                    double distanceEnd = neighborIsle.getEnd().calcDistance(nextCoordinates) + neighborIsle.getCost();

                    if(distanceStart < closestNextCoordDistance){
                        closestNextCoord = neighborIsle.getStart();
                        closestNextCoordDistance = distanceStart;
                    }
                    if(distanceEnd < closestNextCoordDistance){
                        closestNextCoord = neighborIsle.getEnd();
                        closestNextCoordDistance = distanceEnd;
                    }
                }

                if(closestNextCoord == null){
                    System.out.println("Chyba, next coord je null. Start coord je " + startCoordinates);
                }

                startCoordinates = closestNextCoord;
                listCoords.add(startCoordinates);
                possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);
            }

            listCoords.add(nextCoordinates);
            startCoordinates = nextCoordinates;
            possibleNextIsles = warehouseData.getIsleFromCoords(startCoordinates);

        }

        //way  back home
        listCoords.add(new Coordinates(startCoordinates.getX(), warehouseData.getDropSpot().getCoordinates().getY()));
        listCoords.add(warehouseData.getDropSpot().getCoordinates());
        trolley.setStoreGoodsStops(storeGoodsStops);
        return listCoords;
    }

    public void simulationTime() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Trolley trolley: warehouseData.getTrolleys()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(trolley.updateCoords()){
                                List<Coordinates> path = calculatePath(trolley.getOrder(), trolley);
                                trolley.setPath(path);
                                trolley.setWholePath();
                            }
                        }
                    });
                    //Platform.runLater(()->trolley.updateCoords());
                }
            }
        }, 0, 100/simulationSpeed);
    }

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
                        }
                    }
                }
            }
        }, 0, 3000);
    }

}
