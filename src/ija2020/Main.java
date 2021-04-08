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
import javafx.scene.shape.*;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        YAMLFactory yaml_factory = new YAMLFactory();
        ObjectMapper obj_mapper = new ObjectMapper(yaml_factory);
        WarehouseData warehouseData = obj_mapper.readValue(new File("data/dropspot.yml"), WarehouseData.class);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root = new Group();
        primaryStage.setTitle("Warehouse Simulation");
        primaryStage.setScene(new Scene(root, 800, 500));

        for(Isle isle : warehouseData.getIsles()){
            Line line = new Line(isle.getStart().getX(), isle.getStart().getY(), isle.getEnd().getX(), isle.getEnd().getY());
            line.setStrokeWidth(4);
            line.setStroke(SLATEGRAY);
            root.getChildren().add(line);
            for (StoreGoods storeGoods : isle.getStoreGoodsList()) {
                Rectangle shelf = new Rectangle(storeGoods.getCoordinates().getX(), storeGoods.getCoordinates().getY(), 10, 20);
                shelf.setFill(BROWN);
                root.getChildren().add(shelf);
            }
        }

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getCoordinates().getX(), trolley.getCoordinates().getY(), 6);
            circle.setFill(RED);
            root.getChildren().add(circle);
        }


        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }


}
