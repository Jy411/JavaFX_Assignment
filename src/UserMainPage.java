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


public class UserMainPage {

    public UserMainPage(Stage primaryStage) throws IOException {
        // The main borderpane which will hold everything else
        BorderPane borderPane = new BorderPane();
        borderPane.setMinSize(500,500);
        borderPane.setStyle("-fx-background-color: #f0f4f5");
        borderPane.setPadding(new Insets(10,0,0,0));

        // TOP shows login status
        Label loginAs = new Label("You are logged in as: USER");
        loginAs.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        loginAs.setStyle("-fx-border-color: black");
        BorderPane.setAlignment(loginAs, Pos.CENTER);
        loginAs.setPadding(new Insets(15,15,15,15));
        borderPane.setTop(loginAs); // sets to top of borderpane

        // CENTER shows menus, the buttons will be placed in a hbox
        HBox hBox = new HBox();
        // Button 1 : PURCHASE
        Image stockImage = new Image(getClass().getResourceAsStream("images/basket.png"));
        ImageView stockImageView = new ImageView(stockImage);
        stockImageView.setPreserveRatio(true);
        stockImageView.setFitHeight(40);
        Button purchaseButton = new Button("Purchase",stockImageView);
        purchaseButton.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
        purchaseButton.setContentDisplay(ContentDisplay.TOP);
        purchaseButton.setId("regularButton");
        // Button 2 : Reports Button
        Image reportsImage = new Image(getClass().getResourceAsStream("images/report.png"));
        ImageView reportsImageView = new ImageView(reportsImage);
        reportsImageView.setPreserveRatio(true);
        reportsImageView.setFitHeight(40);
        Button reportsButton = new Button("Sales", reportsImageView);
        reportsButton.setId("regularButton");
        reportsButton.setContentDisplay(ContentDisplay.TOP);
        reportsButton.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
        // Button 3 : Discounts
        Image discountImage = new Image(getClass().getResourceAsStream("images/discount.png"));
        ImageView discountImageView = new ImageView(discountImage);
        discountImageView.setPreserveRatio(true);
        discountImageView.setFitHeight(40);
        Button discountButton = new Button("Discounts",discountImageView);
        discountButton.setFont(Font.font("Arial", FontWeight.LIGHT, 15));
        discountButton.setContentDisplay(ContentDisplay.TOP);
        discountButton.setId("regularButton");

        // adding buttons to hbox in CENTER
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(purchaseButton, reportsButton, discountButton);
        hBox.setSpacing(10);
        borderPane.setCenter(hBox);

        // Button functions
        purchaseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UserPurchasePage(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // last login time function
        Label lastLogin = new Label("");
        File loginInfo = new File("adminLoginData.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(loginInfo));
        if (loginInfo.createNewFile()){
            System.out.println("File created");
        }
        else{
            String st;
            while ((st = bufferedReader.readLine()) != null){
                lastLogin.setText("Last Login: "+st);
            }
        }

        // BOTTOM will have admin profile info and logout
        Button logoutButton = new Button("Logout");
        logoutButton.setId("logoutButton");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(lastLogin,logoutButton);

        // logout button functionality
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    LoginSelection(primaryStage);
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

    // this method calls the Login Page
    public void LoginPage(Stage primaryStage) throws IOException{
        LoginPage_Admin loginPageAdmin = new LoginPage_Admin(primaryStage);
    }

    public void LoginSelection(Stage primaryStage) throws IOException{
        LoginSelection loginSelection = new LoginSelection();
        loginSelection.start(primaryStage);
    }

    public void UserPurchasePage(Stage primaryStage) throws IOException{
        UserPurchasePage userPurchasePage = new UserPurchasePage(primaryStage);
    }

}
