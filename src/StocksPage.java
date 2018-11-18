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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class StocksPage {

    private static ObservableList<Items> data = FXCollections.observableArrayList();

    public StocksPage(Stage primaryStage) throws IOException {
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,10,10,10));

        // Label to show that we are in the stocks page
        Label stocksLabel = new Label("Inventory Management");
        stocksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        stocksLabel.setStyle("-fx-border-color: black");
        stocksLabel.setPadding(new Insets(5,5,5,5));
        borderPane.setTop(stocksLabel);
        BorderPane.setAlignment(stocksLabel, Pos.CENTER);

        // Current Inventory Table
        TableView<Items> tableView = new TableView<>();
        TableColumn<Items, String> typeCol=new TableColumn<>("Type");
        TableColumn<Items, String> nameCol=new TableColumn<>("Name");
        TableColumn<Items, Integer> quanCol=new TableColumn<>("Quantity");
        TableColumn<Items, Double> costCol=new TableColumn<>("Price (RM)");
        TableColumn<Items, Date> dateCol=new TableColumn<>("Date Added");
        tableView.getColumns().addAll(typeCol,nameCol,quanCol,costCol,dateCol);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quanCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        tableView.setPadding(new Insets(10,10,10,10));
        tableView.setStyle("-fx-focus-color: transparent");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: #f0f4f5");

        // File reader to read itemsLog.txt line by line and add to Table
        File itemsLog = new File("itemsLog.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
        String readLine = "";
        System.out.println("Loading data from itemsLog.txt");
        // clears the table again so that there are no duplicates
        data = FXCollections.observableArrayList();
        if (bufferedReader.readLine() != null){
            while ((readLine = bufferedReader.readLine()) != null){
                // Splits the string read into tokens
                String delimiter = ",";
                String[] tokens = readLine.split(delimiter);
                String itemType = tokens[0];
                String itemName = tokens[1];
                int itemQuan = Integer.parseInt(tokens[2]);
                double itemCost = Double.parseDouble(tokens[3]);
                String itemDate = tokens[6];
                // Use tokens to create Items object
                Items item = new Items(itemType,itemName,itemQuan,itemCost,itemDate);
                if (data.contains(item)){
                    System.out.println("EXISTS");
                }
                // if the array list does not contain the item already and does not have a quantity of 0
                // add item to the list
                else if ((!data.contains(item) && ((item.getItemQuantity()) != 0))){
                    data.add(item);
                }
            }
        }

        // add items in data to the tableview
        tableView.setItems(data);

        // button at bottom to add stuff
        Button addButton = new Button("Add Item");

        // opens window to add items
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // the add item window
                VBox itemsInfo = new VBox();
                itemsInfo.setAlignment(Pos.CENTER);
                itemsInfo.setMinSize(250,300);
                itemsInfo.setSpacing(8);
                itemsInfo.setPadding(new Insets(10,10,10,10));

                Label itemType=new Label("Type");
                itemType.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label itemName=new Label("Name");
                itemName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label itemQuan=new Label("Quantity");
                itemQuan.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label itemCost=new Label("Cost");
                itemCost.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                ComboBox itemTypeBox = new ComboBox();
                itemTypeBox.getItems().addAll("Beverages", "Bakery", "Canned", "Dairy", "Dry/Baking", "Frozen Foods", "Meat",
                        "Produce", "Cleaning", "Paper Goods", "Personal Care", "Other");
                TextField itemNameField=new TextField();
                TextField itemQuanField=new TextField();
                TextField itemCostField=new TextField();

                HBox hBox = new HBox();
                Button add=new Button("Add");
                Button back=new Button("Back");
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(5);
                hBox.getChildren().addAll(add,back);

                itemsInfo.getChildren().addAll(itemType,itemTypeBox,itemName,itemNameField,itemQuan,itemQuanField,itemCost,itemCostField,hBox);

                Scene scene=new Scene(itemsInfo);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

                // add button functionality to add items
                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String itemType = (String) itemTypeBox.getValue();
                        String itemName = itemNameField.getText();
                        int itemQuan = Integer.parseInt(itemQuanField.getText());
                        double itemCost = Double.parseDouble(itemCostField.getText());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                        String dateAdded = simpleDateFormat.format(new Date());
                        Items items = new Items(itemType,itemName,itemQuan,itemCost,dateAdded);
                        // file io for writing item data in
                        try (Writer fileWriter = new FileWriter("itemsLog.txt", true)){
                            fileWriter.write(items.toString() + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        data.add(items);
                        tableView.setItems(data);
                        stage.close();
                    }
                });

                // Goes back to table
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // closes the add item window
                        stage.close();
                    }
                });

            }
        });

        // button to remove items
        Button removeButton = new Button("Remove Item");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox removeItem = new VBox();
                removeItem.setMinSize(250,200);
                removeItem.setSpacing(8);
                removeItem.setPadding(new Insets(10,10,10,10));
                removeItem.setAlignment(Pos.CENTER);
                Scene scene = new Scene(removeItem);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Dropdown to show all items in inventory
                ComboBox itemNameBox = new ComboBox();
                File itemsLog = new File("itemsLog.txt");
                // HashMap to store Item name and its value
                HashMap<String,Integer> itemNameQuan = new HashMap<>();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
                    String readLine = "";
                    while ((readLine = bufferedReader.readLine()) != null){
                        // Splits the string read into tokens
                        String delimiter = ",";
                        String[] tokens = readLine.split(delimiter);
                        String itemName = tokens[1];
                        int itemQuan = Integer.parseInt(tokens[2]);

                        // adds all item names to dropdown menu
                        if (itemQuan != 0){
                            itemNameBox.getItems().addAll(itemName);
                        }
                        // adds item and quantity pair into HashMap
                        itemNameQuan.put(itemName, itemQuan);
                    }
                    // labels for dropdown and fields
                    Label dropdownLabel = new Label("Item Name");
                    dropdownLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    Label quantityLabel = new Label("Quantity");
                    quantityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    Label itemQuanLabel = new Label();
                    Label removeQuantity = new Label("How much to remove?");
                    removeQuantity.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    TextField itemQuanField = new TextField();

                    String finalItemName = "";
                    // change listener for dropdown box
                    itemNameBox.valueProperty().addListener(new ChangeListener() {
                        @Override
                        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                            // searches HashMap for the selected Item and shows the quantity
                            if (itemNameQuan.containsKey(newValue.toString())){
                                // gets value of key
                                int quantity = itemNameQuan.get(newValue.toString());
                                itemQuanLabel.setText(Integer.toString(quantity));

                            }
                        }
                    });

                    // confirm button to remove set number of items
                    Button confirmRemove = new Button("Confirm");
                    confirmRemove.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // gets the removal quantity
                            int removeQuan = Integer.parseInt(itemQuanField.getText());
                            // name of item to be removed
                            String removeItem = itemNameBox.getValue().toString();
                            File itemsLog = new File("itemsLog.txt");
                            // holds old file content
                            String oldContent = "";
                            String newContent = "";
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
                                String readLine = "", newLine = "";
                                while ((readLine = bufferedReader.readLine()) != null){
                                    // appends all of file into oldContent
                                    oldContent = oldContent + readLine + System.lineSeparator();
                                    // Splits the string read into tokens
                                    String delimiter = ",";
                                    String[] tokens = readLine.split(delimiter);
                                    String itemType = tokens[0];
                                    String itemName = tokens[1];
                                    int oldItemQuan = Integer.parseInt(tokens[2]);
                                    double itemCost = Double.parseDouble(tokens[3]);
                                    String itemDate = tokens[4];
                                    int newItemQuan = oldItemQuan - removeQuan; // after removing set quantity

                                    Items items = new Items(itemType,itemName,newItemQuan,itemCost,itemDate);
                                    // if line is the selected item
                                    if (readLine.contains(removeItem)) {
                                        // readLine is whats needed to be replaced, and newLine is what is replacing it
                                        newLine = items.toString();
                                        // replaces readLine with newLine in newContent buffer strings
                                        newContent = oldContent.replace(readLine, newLine);
                                        oldContent = "";
                                    }
                                }
                                // appends remaining unchanged lines to newContent
                                newContent = newContent + oldContent;
                                // writes everything to itemsLog.txt
                                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("itemsLog.txt"));
                                bufferedWriter.write(newContent);
                                bufferedWriter.close();
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                stage.close();
                                StocksPage stocksPage = new StocksPage(primaryStage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        }
                    });

                    // button to go back
                    Button backButton = new Button("Back");
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(confirmRemove,backButton);

                    // Goes back to table
                    backButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // closes the add item window
                            stage.close();
                        }
                    });

                    // all the components of remove item window
                    removeItem.getChildren().addAll(dropdownLabel, itemNameBox, quantityLabel, itemQuanLabel,removeQuantity,itemQuanField,hBox);
                    removeItem.setAlignment(Pos.TOP_CENTER);
                    removeItem.setSpacing(5);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        // another button to go back to previous screen
        Button prevButton = new Button("Back");
        prevButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AdminPage(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        addButton.setId("menuButton");
        prevButton.setId("menuButton");
        removeButton.setId("menuButton");
        hBox.getChildren().addAll(addButton,removeButton,prevButton);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        tableView.refresh();
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // create an object to link to the admins page
    public void AdminPage(Stage primaryStage) throws IOException {
        AdminMainPage adminMainPage = new AdminMainPage(primaryStage);
    }
}
