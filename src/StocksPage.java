import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
        System.out.println("Reading File with BufferedReader");
        // clears the table again so that there are no duplicates
        data = FXCollections.observableArrayList();
        while ((readLine = bufferedReader.readLine()) != null){
            String delimiter = ",";
            String[] tokens = readLine.split(delimiter);
            String itemType = tokens[0];
            String itemName = tokens[1];
            int itemQuan = Integer.parseInt(tokens[2]);
            double itemCost = Double.parseDouble(tokens[3]);
            String itemDate = tokens[4];
            Items item = new Items(itemType,itemName,itemQuan,itemCost,itemDate);
            if (data.contains(item)){
                System.out.println("EXISTS");
            }
            else if (!data.contains(item)){
                data.add(item);
            }
        }
        tableView.setItems(data);

        // button at bottom to add stuff
        Button addButton = new Button("Add Item");

        // opens window to add items
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                VBox itemsInfo = new VBox();
                itemsInfo.setMinSize(250,300);
                itemsInfo.setSpacing(8);
                itemsInfo.setPadding(new Insets(10,10,10,10));

                Label itemType=new Label("Type: ");
                Label itemName=new Label("Name: ");
                Label itemQuan=new Label("Quantity: ");
                Label itemCost=new Label("Cost: ");
                ComboBox itemTypeBox = new ComboBox();
                itemTypeBox.getItems().addAll("Beverages", "Bakery", "Canned", "Dairy", "Dry/Baking", "Frozen Foods", "Meat",
                        "Produce", "Cleaning", "Paper Goods", "Personal Care", "Other");
                TextField itemNameField=new TextField();
                TextField itemQuanField=new TextField();
                TextField itemCostField=new TextField();
                Button add=new Button("Add");

                itemsInfo.getChildren().addAll(itemType,itemTypeBox,itemName,itemNameField,itemQuan,itemQuanField,itemCost,itemCostField,add);

                Scene scene=new Scene(itemsInfo);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

                // add button functionality
                add.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String itemType = (String) itemTypeBox.getValue();
                        String itemName = itemNameField.getText();
                        int itemQuan = Integer.parseInt(itemQuanField.getText());
                        double itemCost = Double.parseDouble(itemCostField.getText());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                        String dateAdded = simpleDateFormat.format(new Date());
                        System.out.println(itemType + " " + itemName + " " + itemQuan + " " + itemCost + " " + dateAdded);
                        Items items = new Items(itemType,itemName,itemQuan,itemCost,dateAdded);
                        // file io for writing item data in
                        try (Writer fileWriter = new FileWriter("itemsLog.txt", true)){
                            fileWriter.write(items.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(items.toString());
                        data.add(items);
                        tableView.setItems(data);
                        stage.close();
                    }
                });
            }
        });

        // another button to go back to previous screen
        Button prevButton = new Button("Back to menu");
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
        hBox.getChildren().addAll(addButton,prevButton);
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
