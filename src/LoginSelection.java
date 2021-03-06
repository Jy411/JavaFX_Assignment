import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginSelection extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // layout
        BorderPane loginPage = new BorderPane();
        // Customizing the look of the window
        loginPage.setMinSize(500,500);
        loginPage.setStyle("-fx-background-color: #f0f4f5");

        // grocery logo and name set in TOP borderpane
        Label grocerInfo = new Label("Your grocer of choice!");
        BorderPane.setAlignment(grocerInfo, Pos.CENTER); // sets the label center
        grocerInfo.setFont(Font.font("Arial", FontPosture.ITALIC,20));
        grocerInfo.setPadding(new Insets(30,0,0,0));
        Image grocerImage = new Image(getClass().getResourceAsStream("images/shop.png"));
        ImageView imageView = new ImageView(grocerImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        grocerInfo.setGraphic(imageView);
        loginPage.setTop(grocerInfo);

        // Labels for login
        Label loginGreeting = new Label("Login");
        loginGreeting.setFont(Font.font("Arial", FontWeight.BOLD,30));
        GridPane.setHalignment(loginGreeting, HPos.CENTER);

        // Buttons for login
        Button loginUser = new Button("User\nLogin");
        loginUser.setStyle("-fx-text-alignment: center");
        GridPane.setHalignment(loginUser, HPos.LEFT);
        Button loginAdmin = new Button("Admin\nLogin");
        loginAdmin.setStyle("-fx-text-alignment: center");
        GridPane.setHalignment(loginAdmin, HPos.CENTER);
        Button Register = new Button("Register");
        Register.setStyle("-fx-text-alignment: center");
        GridPane.setHalignment(Register, HPos.RIGHT);
        loginAdmin.setId("loginButton");
        loginUser.setId("loginButton");
        Register.setId("loginButton");

        // ALL the login stuff in CENTER
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(loginAdmin,loginUser,Register);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(loginGreeting,hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setMaxWidth(200);
        vBox.setPadding(new Insets(0,0,80,0));
        loginPage.setCenter(vBox);

        // admin login function brings to admin page
        loginAdmin.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    LoginPage_Admin(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        loginUser.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    LoginPage_User(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Register.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    RegisterSelection(primaryStage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        Scene loginScene = new Scene(loginPage);
        loginScene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("The Grocer Stock Management System");
        primaryStage.show();
    }

    // create an object to link to the admins page
    public void LoginPage_Admin(Stage primaryStage) throws IOException {
        LoginPage_Admin loginPageAdmin = new LoginPage_Admin(primaryStage);
    }

    // create an object to link to the users page
    public void LoginPage_User(Stage primaryStage) throws IOException{
        LoginPage_User userPage = new LoginPage_User(primaryStage);
    }

    // create an object to link to the registration page
    public void RegisterSelection(Stage primaryStage) throws IOException{
        RegisterSelection userPage = new RegisterSelection(primaryStage);
    }
}
