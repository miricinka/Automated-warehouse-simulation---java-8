package ija2020;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import static javafx.scene.paint.Color.*;

/**
 * Main of application
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        YAMLFactory yaml_factory = new YAMLFactory();
        ObjectMapper obj_mapper = new ObjectMapper(yaml_factory);
        WarehouseData warehouseData = obj_mapper.readValue(new File("data/dropspot2.yml"), WarehouseData.class);

        BorderPane root = FXMLLoader.load(getClass().getResource("projekt.fxml"));
        primaryStage.setTitle("Warehouse Simulation");
        primaryStage.setScene(new Scene(root));


        Text StoreGoodsInfo = new Text(500, 160, "Mirinka");
        root.getChildren().add(StoreGoodsInfo);

        Text TrolleyInfo = new Text(500, 160, "");
        root.getChildren().add(TrolleyInfo);

        Text CanBeSeen = new Text(20, 20, "Načtení skladu ze souboru YAML a zobrazení v GUI\nPo najetí myší na vozík a regál se zobrazí informace o obsahu");
        root.getChildren().add(CanBeSeen);

        Button button = new Button("Zobrazit objednávky");
        button.setLayoutX(500);
        button.setLayoutY(40);
        root.getChildren().add(button);

        Button button2 = new Button("Zadat novou objednávku");
        button2.setLayoutX(630);
        button2.setLayoutY(40);
        root.getChildren().add(button2);



        for(Isle isle : warehouseData.getIsles()){
            Line line = new Line(isle.getStart().getX(), isle.getStart().getY(), isle.getEnd().getX(), isle.getEnd().getY());
            line.setStrokeWidth(4);
            line.setStroke(SLATEGRAY);
            root.getChildren().add(line);
            if (isle.getStoreGoodsList() != null) {
                for (StoreGoods storeGoods : isle.getStoreGoodsList()) {
                    Rectangle shelf = new Rectangle(storeGoods.getCoordinates().getX(), storeGoods.getCoordinates().getY(), 40, 60);
                    shelf.setStroke(BLACK);
                    shelf.setStrokeWidth(3);
                    shelf.setFill(BROWN);
                    root.getChildren().add(shelf);
                    shelf.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            shelf.setFill(LIME);
                            StoreGoodsInfo.setText("Obsah: " + storeGoods.getName() + "\nPocet: " + storeGoods.getItemsList().size() + "x\nHmotnost: " + storeGoods.getItemWeight() + "kg");
                        }
                    });

                    shelf.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            shelf.setFill(BROWN);
                            StoreGoodsInfo.setText("");
                        }
                    });
                }
            }
        }

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getCoordinates().getX(), trolley.getCoordinates().getY(), 6);
            circle.setFill(RED);
            root.getChildren().add(circle);
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(LIME);
                    StoreGoodsInfo.setText("Maximální kapacita: " + trolley.getCapacity() + "kg\nVyužitá kapacita: " + trolley.getUsedCapacity() + "kg");
                }
            });

            circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(RED);
                    StoreGoodsInfo.setText("");
                }
            });
        }

//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                String textToView = "";
//                for (Order order: warehouseData.getOrders()) {
//                    textToView = textToView.concat(order.getId() + ":\n");
//                    for(Item item: order.getToDoList()) {
//                        textToView = textToView.concat("  "+ item.getStore().getName() + "\n");
//                    }
//                }
//                StoreGoodsInfo.setText(textToView);
//                System.out.print("text" + textToView);
//            }
//        });


        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
