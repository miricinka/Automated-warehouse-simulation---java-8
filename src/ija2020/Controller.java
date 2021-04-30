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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
    private ArrayList<String> allGoodsList;
    private Timer timer;
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
            Line line = new Line(isle.getStart().getX(), isle.getStart().getY(), isle.getEnd().getX(), isle.getEnd().getY());
            line.setStrokeWidth(4);
            line.setStroke(SLATEGRAY);
            mainPane.getChildren().add(line);
            if (isle.getStoreGoodsList() != null) {
                for (StoreGoods storeGoods : isle.getStoreGoodsList()) {
                    Rectangle shelf = new Rectangle(storeGoods.getCoordinates().getX(), storeGoods.getCoordinates().getY(), 40, 60);
                    shelf.setStroke(BLACK);
                    shelf.setStrokeWidth(3);
                    shelf.setFill(PERU);
                    mainPane.getChildren().add(shelf);

                    shelf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
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
                            storeGoodsInfo.setText("Obsah: " + storeGoods.getName() + "\nPocet: " + storeGoods.getItemsCount() + "x\nHmotnost: " + storeGoods.getItemWeight() + "kg");
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
            Circle circle = new Circle(trolley.getCoordinates().getX(), trolley.getCoordinates().getY(), 6);
            circle.setFill(RED);
            mainPane.getChildren().add(circle);
            trolley.addCircle(circle);

            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
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
                    trolleyInfo.setText("Maximální kapacita: " + trolley.getCapacity() + "kg\nVyužitá kapacita: " + trolley.getUsedCapacity() + "kg");
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
        }
        info.setText(textToView);
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

    public void simulationTime() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Trolley trolley: warehouseData.getTrolleys()) {
                    Platform.runLater(()->trolley.updateCoords());
                }
            }
        }, 0, 300/simulationSpeed);
    }

}
