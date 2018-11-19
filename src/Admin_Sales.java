import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Admin_Sales {

    public Admin_Sales(Stage primaryStage) throws IOException {
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,0,0,0));

        // TOP shows login status
        Label loginAs = new Label("Sales Monitoring Page");
        loginAs.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        loginAs.setStyle("-fx-border-color: black");
        BorderPane.setAlignment(loginAs, Pos.CENTER);
        loginAs.setPadding(new Insets(15,15,15,15));
        borderPane.setTop(loginAs); // sets to top of borderpane

        // CENTER shows menus, the buttons will be placed in a hbox
        HBox hBox = new HBox();
        // Button 1 : Best Seller Button
        Image bestsellerImage = new Image(getClass().getResourceAsStream("images/bestseller.png"));
        ImageView bestsellerImageView = new ImageView(bestsellerImage);
        bestsellerImageView.setPreserveRatio(true);
        bestsellerImageView.setFitHeight(40);
        Button bestsellerButton = new Button("Top Items",bestsellerImageView);
        bestsellerButton.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
        bestsellerButton.setContentDisplay(ContentDisplay.TOP);
        bestsellerButton.setId("regularButton");

        // Button 2 : Total Sales Button
        Image totalsalesImage = new Image(getClass().getResourceAsStream("images/sales.png"));
        ImageView totalsalesImageView = new ImageView(totalsalesImage);
        totalsalesImageView.setPreserveRatio(true);
        totalsalesImageView.setFitHeight(40);
        Button totalsalesButton = new Button("Total Sales", totalsalesImageView);
        totalsalesButton.setId("regularButton");
        totalsalesButton.setContentDisplay(ContentDisplay.TOP);
        totalsalesButton.setFont(Font.font("Arial", FontWeight.LIGHT, 15));

        // adding buttons to hbox in CENTER
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(bestsellerButton, totalsalesButton);
        hBox.setSpacing(10);
        borderPane.setCenter(hBox);

        // Button functions
        bestsellerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    TopSellingItems_admin(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        totalsalesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    TopSellingItems_admin(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Back button functionality
        Button backButton = new Button("Back");
        backButton.setId("logoutButton");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(backButton);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AdminPage(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        BorderPane borderPane1 = new BorderPane();
        borderPane1.setBottom(vBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(0,0,10,0));
        borderPane.setBottom(borderPane1);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void AdminPage(Stage primaryStage) throws IOException {
        AdminMainPage adminMainPage = new AdminMainPage(primaryStage);
    }

    public void TopSellingItems_admin (Stage primaryStage) throws IOException {
        TopSellingItems_admin topSellingItems_admin = new TopSellingItems_admin(primaryStage);
    }






}

