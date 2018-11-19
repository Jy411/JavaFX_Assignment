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

public class TopSellingItems_admin {

    private static ObservableList<Items> data = FXCollections.observableArrayList();

    public TopSellingItems_admin(Stage primaryStage) throws IOException {
        Button prevButton = new Button("Back");
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,10,10,10));

        // Label to show that we are in the stocks page
        Label stocksLabel = new Label("Top Selling Items");
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
        TableColumn<Items, Double> totalCol=new TableColumn<>("Total Sales");

        tableView.getColumns().addAll(typeCol,nameCol,quanCol,totalCol);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quanCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("Total Sales"));

        tableView.setPadding(new Insets(10,10,10,10));
        tableView.setStyle("-fx-focus-color: transparent");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: #f0f4f5");

        // Load itemsLog.txt to TableView
        loadTable(tableView);
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
                Label itemCost=new Label("Total");
                itemCost.setFont(Font.font("Arial", FontWeight.BOLD, 15));

                TextField itemNameField=new TextField();
                TextField itemQuanField=new TextField();
                TextField itemCostField=new TextField();

                HBox hBox1 =new HBox();
                hBox1.setAlignment(Pos.CENTER);
                hBox1.setSpacing(5);
                hBox1.getChildren().addAll( prevButton);
                itemsInfo.getChildren().addAll(itemType,itemName,itemNameField,itemQuan,itemQuanField,itemCost,itemCostField,hBox1);

                Scene scene=new Scene(itemsInfo);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();





            }
        });




        // another button to go back to previous screen

        prevButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Admin_Sales(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        prevButton.setId("menuButton");

        hBox.getChildren().addAll(prevButton);
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
    public void Admin_Sales(Stage primaryStage) throws IOException {
        Admin_Sales adminMainPage = new Admin_Sales(primaryStage);
    }

    private void loadTable(TableView tableView) throws IOException {
        // File reader to read itemsLog.txt line by line and add to Table
        File itemsLog = new File("itemsLog.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
        String readLine;
        System.out.println("Loading data from itemsLog.txt");
        // clears the table again so that there are no duplicates
        data = FXCollections.observableArrayList();
        while ((readLine = bufferedReader.readLine()) != null){
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
            int totalSale = Integer.parseInt(tokens[7]);

            System.out.println("READLINE:" + readLine);
            System.out.println("ITEMTYPE:" + itemType);
            System.out.println("ITEMNAME:" + itemName);
            System.out.println("ITEMQUAN:" + itemQuan);
            System.out.println("ITEMSale:" + totalSale);

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
        tableView.setItems(data);
    }

}
