package ija2020;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main of application
 * @version 1.0
 * @author <a href="xkolar76@stud.fit.vutbr.cz">Mirka Kolarikova</a>
 * @author <a href="xzovin00@stud.fit.vutbr.cz">Martin Zovinec</a>
 */


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //parse yaml
        YAMLFactory yaml_factory = new YAMLFactory();
        ObjectMapper obj_mapper = new ObjectMapper(yaml_factory);
        WarehouseData warehouseData = obj_mapper.readValue(new File("data/dropspot4.yml"), WarehouseData.class);

        //load GUI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("projekt.fxml"));
        BorderPane root = loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Warehouse Simulation");
        primaryStage.setScene(new Scene(root));

        //paint warehouse
        controller.setWarehouseData(warehouseData);
        controller.paintIsles();
        controller.paintTrolleys();
        controller.setAllGoodsList();

        controller.setAllGoodsList();
        //setup speed
        controller.setupSpeed();

        //run simulation
        controller.simulationTime();
        controller.checkForOrder();

        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
