import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;

public class UserPurchasePage {

    private static ObservableList<Items> data = FXCollections.observableArrayList();

    public UserPurchasePage(Stage primaryStage) throws IOException {
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,10,10,10));

        // Label to show that we are in the stocks page
        Label purchaseLabel = new Label("Purchase");
        purchaseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        purchaseLabel.setStyle("-fx-border-color: black");
        purchaseLabel.setPadding(new Insets(5,5,5,5));
        borderPane.setTop(purchaseLabel);
        BorderPane.setAlignment(purchaseLabel, Pos.CENTER);

        // the add item window
        VBox itemsInfo = new VBox();
        itemsInfo.setAlignment(Pos.CENTER);
        itemsInfo.setMinSize(250,300);
        itemsInfo.setSpacing(8);
        itemsInfo.setPadding(new Insets(10,10,10,10));

        Label itemType=new Label("Type");
        itemType.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label catalogLabel=new Label("Catalog");
        catalogLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        // dropdown box showing item categories
        ComboBox itemTypeBox = new ComboBox();
        itemTypeBox.getItems().addAll("Beverages", "Bakery", "Canned", "Dairy", "Dry/Baking", "Frozen Foods", "Meat",
                "Produce", "Cleaning", "Paper Goods", "Personal Care", "Other");
        itemTypeBox.setValue("Beverages");

        // LISTVIEW HERE
        ListView itemCatalog = new ListView();

        // returns the old value and the new selected value
        String[] newItem = new String[1];
        itemTypeBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                // clears listview so that it refreshes each time a new category is selected
                itemCatalog.getItems().clear();
                newItem[0] = newValue.toString(); // selected category value
                // File reader to read itemsLog.txt line by line
                File itemsLog = new File("itemsLog.txt");
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(itemsLog));
                    String readLine = "";
                    while ((readLine = bufferedReader.readLine()) != null){
                        // Splits the string read into tokens
                        String delimiter = ",";
                        String[] tokens = readLine.split(delimiter);
                        String itemType1 = tokens[0];
                        // when reading through text file, if selected category is same as itemtype in text file
                        if (itemType1.equals(newItem[0])){
                            // then get the item name
                            String itemName = tokens[1];
                            // if item name is not in the listView
                            if (!itemCatalog.getItems().contains(itemName)){
                                itemCatalog.getItems().addAll(itemName);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        // ListView mouse click listener to get new value selected
        // add to cart by double clicking on item
        File userCart = new File("userCart.txt");
        itemCatalog.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    // creates pop up notification at the corner
                    Notifications.create()
                    .title("Success!")
                    .text(itemCatalog.getSelectionModel().getSelectedItem().toString() + " Successfully Added to Cart.")
                    .hideAfter(new Duration(1000))
                    .showInformation();

                    // File reader to read itemsLog.txt line by line
                    File itemsLog = new File("itemsLog.txt");
                    BufferedReader bufferedReader;
                    try {
                        FileWriter fileWriter = new FileWriter(userCart, true);
                        bufferedReader = new BufferedReader(new FileReader(itemsLog));
                        String readLine;
                        // clears the table again so that there are no duplicates
                        while ((readLine = bufferedReader.readLine()) != null){
                            // Splits the string read into tokens
                            String delimiter = ",";
                            String[] tokens = readLine.split(delimiter);
                            String itemName = tokens[1];
                            double itemCost = Double.parseDouble(tokens[3]);
                            // if selected item in listview is in the itemLog file
                            if (itemCatalog.getSelectionModel().getSelectedItem().toString().equals(itemName)){
                                // write down items in userCart.txt
                                fileWriter.write(itemName + "," + itemCost + System.lineSeparator());
                            }
                        }
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // the two buttons at the bottom
        HBox hBox = new HBox();
        Button cart=new Button("Cart");
        Button back=new Button("Back");

        // opens cart
        TableView<Items> itemCart = new TableView<>();
        TableColumn<Items, String> itemNameCol = new TableColumn<>("Name");
        TableColumn<Items, Double> itemPriceCol = new TableColumn<>("Price (RM)");
        itemCart.getColumns().addAll(itemNameCol,itemPriceCol);
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
        itemCart.setPadding(new Insets(10,10,10,10));
        itemCart.setStyle("-fx-focus-color: transparent");
        itemCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        itemCart.setStyle("-fx-background-color: #f0f4f5");
        data = FXCollections.observableArrayList();
        cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox vBox = new VBox();
                Label cartLabel = new Label("Cart");
                cartLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
                // File reader to read userCart.txt line by line and add to the tableview
                File userCart = new File("userCart.txt");
                BufferedReader bufferedReader;
                try {
                    bufferedReader = new BufferedReader(new FileReader(userCart));
                    String readLine;
                    // clears the table again so that there are no duplicates
                    while ((readLine = bufferedReader.readLine()) != null){
                        // Splits the string read into tokens
                        String delimiter = ",";
                        String[] tokens = readLine.split(delimiter);
                        String itemName = tokens[0];
                        double itemCost = Double.parseDouble(tokens[1]);
                        Items items = new Items(itemName, itemCost);
                        if (data.contains(items)){
                            System.out.println("EXISTS");
                        }
                        // add item and price to the tableview
                        else if ((!data.contains(items))){
                            data.add(items);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                itemCart.setItems(data);

                // cost label
                Label totalCostLabel = new Label("Total Cost (RM)");
                totalCostLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label totalCost = new Label();
                // purchase button
                Button purchaseButton = new Button("Purchase");
                purchaseButton.setId("menuButton");
                // back button
                Button back = new Button("Back");
                back.setId("menuButton");
                // clear cart button
                Button clearCartButton = new Button("Clear");
                clearCartButton.setId("menuButton");

                HBox hBox1 = new HBox();
                hBox1.setAlignment(Pos.CENTER);
                hBox1.setSpacing(5);
                hBox1.getChildren().addAll(purchaseButton,clearCartButton, back);

                // total cost calculator, loops through all the items in the list and adds up their cost
                double totalItemCost = 0;
                for (Items i : itemCart.getItems()){
                    double itemCost = i.getItemCost();
                    totalItemCost = totalItemCost + itemCost;
                }

                totalCost.setText(Double.toString(totalItemCost));
                vBox.setMinSize(300,450);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(cartLabel,itemCart,totalCostLabel,totalCost,hBox1);
                vBox.setPadding(new Insets(10,10,10,10));
                vBox.setSpacing(5);
                Scene scene = new Scene(vBox);
                scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Cart");
                stage.show();

                // purchase button function
                purchaseButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // File reader to read itemsLog.txt line by line
                        File itemsLog = new File("itemsLog.txt");
                        BufferedReader bufferedReader;
                        //Holds old file content
                        String oldContent = "";
                        String newContent = "";
                        try {
                            bufferedReader = new BufferedReader(new FileReader(itemsLog));
                            String readLine, newLine;
                            // read each line
                            while ((readLine = bufferedReader.readLine()) != null){
                                //appends all file into oldContent
                                oldContent = oldContent + readLine + System.lineSeparator();
                                // Splits the string read into tokens
                                String delimiter = ",";
                                String[] tokens = readLine.split(delimiter);
                                String itemType = tokens[0];
                                String itemName = tokens[1];
                                int itemQuan = Integer.parseInt(tokens[2]);
                                double itemCost = Double.parseDouble(tokens[3]);
                                double itemDiscount = Double.parseDouble(tokens[4]);
                                double itemNewCost = Double.parseDouble(tokens[5]);
                                String itemDate = tokens[6];
                                int itemNewQuantity = itemQuan - 1;

                                // item with new quantity
                                Items items = new Items(itemType,itemName,itemNewQuantity,itemCost,itemDiscount,itemNewCost,itemDate);
                                System.out.println("CURRENT ITEM: " + items);

                                // for each item in itemCart
                                for (Items i : itemCart.getItems()){
                                    // if item in the TableView is also in itemsLog.txt
                                    if (i.getItemName().equals(itemName)){
                                        // updated line with updated item quantity
                                        newLine = items.toString();
                                        newContent = oldContent.replaceAll(readLine,newLine);
                                        oldContent = newContent;
                                    }
                                }
                            }
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("itemsLog.txt"));
                            bufferedWriter.write(newContent);
                            bufferedWriter.close();
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // creates pop up notification at the corner
                        Notifications.create()
                                .title("Purchase Complete")
                                .text("Have a good day!")
                                .hideAfter(new Duration(1000))
                                .showInformation();
                        stage.close();
                    }
                });

                // to go back to purchase screen
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            stage.close();
                            UserPurchasePage userPurchasePage = new UserPurchasePage(primaryStage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // clear cart by deleting data in userCart.txt
                clearCartButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        itemCart.getItems().clear();
                        try {
                            FileWriter fileWriter = new FileWriter(userCart);
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

        // Goes back to User Main Menu
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserMainPage userMainPage = new UserMainPage(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        cart.setId("menuButton");
        back.setId("menuButton");
        hBox.getChildren().addAll(cart, back);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        itemsInfo.getChildren().addAll(itemType,itemTypeBox,catalogLabel,itemCatalog,hBox);
        borderPane.setCenter(itemsInfo);

        Scene scene1 = new Scene(borderPane);
        scene1.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    // create an object to link to the admins page
    public void UserMainPage(Stage primaryStage) throws IOException {
        UserMainPage userMainPage = new UserMainPage(primaryStage);
    }


}
