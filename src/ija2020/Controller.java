package ija2020;

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
    private ArrayList<String> allGoodsList;
    private Timer timer;
    private Timer timer2;
    private long simulationSpeed;

    public void setWarehouseData(WarehouseData warehouseData) {
        this.warehouseData = warehouseData;
    }

    public void setAllGoodsList(){
        allGoodsList = new ArrayList<>();
        warehouseData.setGoodsList();
        allGoodsList = warehouseData.getGoodsList();
        System.out.println(allGoodsList);
    }


    /**
     * Paints all Isles and Goods from warehouseData
     * Shows info on mouse entered and exited
     * @param root of borderpane
     */
    public void paintIsles(BorderPane root) {

        Text storeGoodsInfo = new Text(10, 20, "");

        for(Isle isle : warehouseData.getIsles()){
            //spocitani ceny ulicky
            isle.setCost();
            Line line = new Line(isle.getStart().getX(), isle.getStart().getY(), isle.getEnd().getX(), isle.getEnd().getY());
            line.setStrokeWidth(4);
            line.setStroke(SLATEGRAY);
            mainPane.getChildren().add(line);
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
                            if(paintedPath != null){
                                for(Line line:paintedPath){
                                    mainPane.getChildren().remove(line); }
                            }
                            if (lastClickedShelf != null) {
                                lastClickedShelf.setFill(PERU);
                            }
                            if (lastClickedTrolley != null) {
                                lastClickedTrolley.setFill(RED);
                            }
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
     * @param root of borderpane
     */
    public void paintTrolleys(BorderPane root) {

        Text trolleyInfo = new Text(10, 20, "");

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getStartCoordinates().getX(), trolley.getStartCoordinates().getY(), 9);
            circle.setFill(RED);
            mainPane.getChildren().add(circle);
            trolley.addCircle(circle);

            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(paintedPath != null){
                        for(Line line:paintedPath){
                            mainPane.getChildren().remove(line); }
                    }
                    if (lastClickedShelf != null) {
                        lastClickedShelf.setFill(PERU);
                    }
                    if (lastClickedTrolley != null) {
                        lastClickedTrolley.setFill(RED);
                    }
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
                                todoList = todoList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
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
                            if(start != null && start != null){
                                Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
                                paintedPath.add(line);
                                line.setStrokeWidth(4);
                                line.setStroke(LIME);
                                mainPane.getChildren().add(line);
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
                        todoList = todoList +"          "+ entry.getKey() + " " + entry.getValue() + "x\n";
                    }
                }

                trolleyString = trolleyString + order.getId() +"\n Vozík: " + trolley.getId() +"\n"+ todoList + doneList + "\n";
            }

        }
        info.setText(trolleyString+textToView);
    }

    @FXML
    public void btn2Clicked() {
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

        for(String name: allGoodsList) {
            newOrderController.loadOrderList(name);
        }

        newOrderController.setWarehouseData(warehouseData);

        stage.show();
    }

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

    public void setupSpeed(){
        simulationSpeed = 1;
    }

    public void changeSpeed(){
        System.out.println(speedSlider.getValue());
        simulationSpeed = (long) speedSlider.getValue();
        timer.cancel();
        simulationTime();
    }

    public List<Coordinates> calculatePath(Order order){
        List<Coordinates> listCoords = new ArrayList<>();
        listCoords.add(new Coordinates(520, 250));
        listCoords.add(new Coordinates(470, 250));

        Coordinates startCoordinates = new Coordinates(520, 250);
        HashMap<String, Integer> allGoods = new HashMap<>(order.getToDoList());
        while(!allGoods.isEmpty()){
            StoreGoods closest = warehouseData.findNextClosestGoods(startCoordinates, allGoods);
            //je tu vse co chceme -> smazat ze seznamu
            if(closest.getItemsCount() >= allGoods.get(closest.getName())){
                //odecist co jsme vzali
                closest.setItemsCount(closest.getItemsCount() - allGoods.get(closest.getName()));
                closest.setReadyToDispatch(closest.getReadyToDispatch() + allGoods.get(closest.getName()));
                allGoods.remove(closest.getName());
            }else{ //neni vse co chceme -> vezmeme co muzem
                int countLeft = allGoods.get(closest.getName())- closest.getItemsCount();
                allGoods.replace(closest.getName(), countLeft);
                closest.setReadyToDispatch(closest.getReadyToDispatch() + allGoods.get(closest.getName()));
                closest.setItemsCount(0);
            }

            //hledat odkud jsem ted
            Coordinates nextCoordinates = closest.getStopCoordinates();
            while (startCoordinates.getX() != nextCoordinates.getX()){
                    if(startCoordinates.getY() != 250){
                        listCoords.add(new Coordinates(startCoordinates.getX(), 250));
                        startCoordinates = new Coordinates(startCoordinates.getX(), 250);
                    }else {
                        listCoords.add(new Coordinates(nextCoordinates.getX(), 250));
                        startCoordinates = new Coordinates(nextCoordinates.getX(), 250);
                    }

            }
            listCoords.add(nextCoordinates);
            startCoordinates = nextCoordinates;
        }
        listCoords.add(new Coordinates(startCoordinates.getX(), 250));
        listCoords.add(new Coordinates(520, 250));
        return listCoords;
    }

    public void simulationTime() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Trolley trolley: warehouseData.getTrolleys()) {
                    Platform.runLater(()->trolley.updateCoords());
                }
            }
        }, 0, 200/simulationSpeed);
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
                            List<Coordinates> path = calculatePath(order);
                            Platform.runLater(()->trolley.setOrder(order));
                            Platform.runLater(()->trolley.setPath(path));
                            Platform.runLater(()->trolley.setWholePath());
                        }
                    }
                }
            }
        }, 0, 3000);
    }

}
