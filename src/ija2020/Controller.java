package ija2020;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

import static javafx.scene.paint.Color.*;

public class Controller {
    @FXML
    private AnchorPane textPanel;
    @FXML
    private Button btn1;

    private WarehouseData warehouseData;

    public void setWarehouseData(WarehouseData warehouseData) {
        this.warehouseData = warehouseData;
    }

    public void paintIsles(BorderPane root) {
        Text StoreGoodsInfo = new Text(560, 160, "");
        //Label StoreGoodsInfo = new Label("Ahoj");
        root.getChildren().add(StoreGoodsInfo);
        //textPanel.getChildren().add(StoreGoodsInfo);

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
    }

    public void paintTrolleys(BorderPane root) {

        Text TrolleyInfo = new Text(560, 160, "");
        root.getChildren().add(TrolleyInfo);

        for(Trolley trolley : warehouseData.getTrolleys()){
            Circle circle = new Circle(trolley.getCoordinates().getX(), trolley.getCoordinates().getY(), 6);
            circle.setFill(RED);
            root.getChildren().add(circle);
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(LIME);
                    TrolleyInfo.setText("Maximální kapacita: " + trolley.getCapacity() + "kg\nVyužitá kapacita: " + trolley.getUsedCapacity() + "kg");
                }
            });

            circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(RED);
                    TrolleyInfo.setText("");
                }
            });
        }
    }

    @FXML
    public void btn1Clicked() {
        textPanel.getChildren().clear();
        System.out.print(textPanel.getChildren().isEmpty());
        Text info = new Text(560, 160, "AAAAAANJDEDJOCKEJNDJKOXDKSJNJXKO");
        textPanel.getChildren().add(info);

        String textToView = "";
        for (Order order: warehouseData.getOrders()) {
            textToView = textToView.concat(order.getId() + ":\n");
            for(Item item: order.getToDoList()) {
                textToView = textToView.concat("  "+ item.getStore().getName() + "\n");
            }
        }
        info.setText(textToView);
        System.out.print("text" + textToView);
        System.out.print(textPanel.getChildren());
    }

}
