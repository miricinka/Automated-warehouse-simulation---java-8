package ija2020;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.HashMap;

public class NewOrderController {

    @FXML
    public Button saveButton;
    @FXML
    private ListView orderList;
    @FXML
    private TextField newOrderID;

    private WarehouseData warehouseData;

    /**
     * Loads goods to listView
     * @param name of goods
     */
    public void loadOrderList(String name){
        Label label1 = new Label(name);
        label1.setMinWidth(70);
        TextField textField = new TextField ("0");
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        orderList.getItems().add(hb);
    }

    /**
     * Sets warehouse data
     * @param warehouseData
     */
    public void setWarehouseData(WarehouseData warehouseData) {
        this.warehouseData = warehouseData;
    }

    /**
     * Reacts on save button click
     * creates and saves new order
     * if order ID is missing or no goods wanted, new order is not created
     * closes the NewOrder window
     */
    public void saveButtonClicked() {

        HashMap<String, Integer> toDoList= new HashMap<>();

        //get items and count
        for (Object each: orderList.getItems())
        {
            if (each instanceof HBox) {
                HBox eachBox = (HBox) each;
                Label label = (Label) eachBox.getChildren().get(0);
                String goodsName = label.getText();
                TextField textField = (TextField) eachBox.getChildren().get(1);
                String goodsCount = textField.getText();
                //convert string to integer
                try {
                    int goodsCountInt = Integer.parseInt(goodsCount.trim());
                    if(goodsCountInt > 0) {
                        toDoList.put(goodsName, goodsCountInt);
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                }
            }
        }

        //create new order
        if(toDoList.size() > 0 && !newOrderID.getText().equals("")){
            Order newOrder = new Order();
            newOrder.setId(newOrderID.getText());
            newOrder.setToDoList(toDoList);
            //add order to data
            warehouseData.addOrder(newOrder);
        }

        //close window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
