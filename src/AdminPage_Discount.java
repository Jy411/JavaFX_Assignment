import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminPage_Discount {

	private static ObservableList<Items> data1 = FXCollections.observableArrayList();

    public AdminPage_Discount(Stage primaryStage) throws IOException {
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,10,10,10));

        // Label to show that we are in the stocks page
        Label stocksLabel = new Label("Discount Management");
        stocksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        stocksLabel.setStyle("-fx-border-color: black");
        stocksLabel.setPadding(new Insets(5,5,5,5));
        borderPane.setTop(stocksLabel);
        BorderPane.setAlignment(stocksLabel, Pos.CENTER);

        // Current Inventory Table
        TableView<Items> tableView = new TableView<>();
        TableColumn<Items, String> nameCol=new TableColumn<>("Name");
        TableColumn<Items, Double> costCol=new TableColumn<>("Price (RM)");
        TableColumn<Items, Double> discountCol=new TableColumn<>("Discount (%)");
        TableColumn<Items, Double> newCostCol=new TableColumn<>("New Price (RM)");
        tableView.getColumns().addAll(nameCol,costCol,discountCol, newCostCol);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("itemCost"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("itemDiscount"));
        newCostCol.setCellValueFactory(new PropertyValueFactory<>("itemNewCost"));
        tableView.setPadding(new Insets(10,10,10,10));
        tableView.setStyle("-fx-focus-color: transparent");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: #f0f4f5");

        // File reader to read itemsLog.txt line by line and add to Table
        File itemsLog = new File("itemsLog.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
        String readLine = "";
        System.out.println("Loading data1 from itemsLog.txt");
        // clears the table again so that there are no duplicates
        data1 = FXCollections.observableArrayList();
        while ((readLine = bufferedReader.readLine()) != null){
            // Splits the string read into tokens
            String delimiter = ",";
            String[] tokens = readLine.split(delimiter);
            String itemNameToken = tokens[1];
            double itemCostToken = Double.parseDouble(tokens[3]);
            double itemDiscountToken = Double.parseDouble(tokens[4]);
            double itemNewCostToken = itemCostToken - (itemDiscountToken * itemCostToken);
            // Use tokens to create Items object
            Items item = new Items(itemNameToken,itemCostToken,itemDiscountToken,itemNewCostToken);
            if (data1.contains(item)){
                System.out.println("EXISTS");
            }
            // if the array list does not contain the item already and does not have a quantity of 0
            // add item to the list
            else if ((!data1.contains(item))){
                data1.add(item);
            }
        }
        // add items in data1 to the tableview
        tableView.setItems(data1);

        // button at bottom to set discount 
        Button setDiscountButton = new Button("Set Item Discount");

        // opens window to set discount
        setDiscountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // the add item window
                VBox itemDiscount = new VBox();
                itemDiscount.setAlignment(Pos.CENTER);
                itemDiscount.setMinSize(250,300);
                itemDiscount.setSpacing(8);
                itemDiscount.setPadding(new Insets(10,10,10,10));

                Label itemNameLabel=new Label("Name");
                itemNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label itemSetDicount=new Label("Set Discount");
                itemSetDicount.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                
                ComboBox itemNameBox = new ComboBox();
                
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
                        String itemName = tokens[1];
                        itemNameBox.getItems().addAll(itemName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                TextField itemDiscountField=new TextField();

                HBox hBox = new HBox();
                Button confirm=new Button("Confirm");
                Button back=new Button("Back");
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(5);
                hBox.getChildren().addAll(confirm,back);

                itemDiscount.getChildren().addAll(itemNameLabel,itemNameBox,itemSetDicount,itemDiscountField,hBox);

                Scene scene=new Scene(itemDiscount);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

                // confirm button functionality to discounts
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    	//Gets item name from Dropdown box
                        String itemName = (String) itemNameBox.getValue();
                        //Get user input item discount form text field
                        double itemDiscount = Double.parseDouble(itemDiscountField.getText()) / 100;
                        File itemsLog = new File ("itemsLog.txt");
                        //Holds old file content
                        String oldContent = "";
                        String newContent = "";

                        try {
                        	BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsLog));
                        	String readLine = "", newLine = "";
                        	while ((readLine = bufferedReader.readLine()) != null) {
                        		//appends all file into oldContent
                        		oldContent = oldContent + readLine + System.lineSeparator();
                        		
                        		//Splits the string read into tokens
                        		String delimiter = ",";
                        		String[] tokens = readLine.split(delimiter);
                        		String itemTypeToken = tokens[0];
                        		String itemNameToken = tokens[1];
                        		int itemQuantityToken = Integer.parseInt(tokens[2]);
                        		double itemCostToken = Double.parseDouble(tokens[3]);
                        		double itemDiscountToken = itemDiscount;
                        		double itemNewCostToken = itemCostToken - (itemDiscount * itemCostToken);
                        		String itemDateToken = tokens[6];
                        		Items items = new Items(itemTypeToken,itemNameToken,itemQuantityToken,itemCostToken,
                        					itemDiscountToken,itemNewCostToken,itemDateToken);
                        		// replaces old line with new updated line
                        		if (readLine.contains(itemName)) {
                        			newLine = items.toString();
                        			newContent = oldContent.replace(readLine,newLine);
                        			oldContent = "";
                        		}
                            }
                        	newContent = newContent + oldContent;
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("itemsLog.txt"));
                            bufferedWriter.write(newContent);
                            bufferedWriter.close();
                            bufferedReader.close();
                        } catch (IOException e) {
                        	e.printStackTrace();
                        }
                        // File reader to read itemsLog.txt line by line and add to Table
                        BufferedReader bufferedReader = null;
                        try {
                            bufferedReader = new BufferedReader(new FileReader(itemsLog));
                            String readLine = "";
                            System.out.println("Loading data1 from itemsLog.txt");
                            // clears the table again so that there are no duplicates
                            data1 = FXCollections.observableArrayList();
                            while ((readLine = bufferedReader.readLine()) != null){
                                // Splits the string read into tokens
                                String delimiter = ",";
                                String[] tokens = readLine.split(delimiter);
                                String itemNameToken = tokens[1];
                                double itemCostToken = Double.parseDouble(tokens[3]);
                                double itemDiscountToken = Double.parseDouble(tokens[4]);
                                double itemNewCostToken = itemCostToken - (itemDiscountToken * itemCostToken);
                                // Use tokens to create Items object
                                Items item = new Items(itemNameToken,itemCostToken,itemDiscountToken,itemNewCostToken);
                                if (data1.contains(item)){
                                    System.out.println("EXISTS");
                                }
                                // if the array list does not contain the item already and does not have a quantity of 0
                                // add item to the list
                                else if ((!data1.contains(item))){
                                    data1.add(item);
                                }
                            }
                            // add items in data1 to the tableview
                            tableView.setItems(data1);
                            tableView.refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.close();
                    }
                }); 

                // Goes back to table
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // closes the set item discount window
                        tableView.refresh();
                        stage.close();
                    }
                });

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
        setDiscountButton.setId("menuButton");
        prevButton.setId("menuButton");
        hBox.getChildren().addAll(setDiscountButton,prevButton);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        tableView.refresh();
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    public void AdminPage(Stage primaryStage) throws IOException {
        AdminMainPage adminMainPage = new AdminMainPage(primaryStage);
    }
	
}
