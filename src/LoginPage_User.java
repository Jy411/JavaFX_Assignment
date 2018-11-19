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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class LoginPage_User extends Application {

    public LoginPage_User(Stage primaryStage) throws IOException {
        // Variable to store the focus on stage load
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);

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
        Label loginGreeting = new Label("User Login");
        loginGreeting.setFont(Font.font("Arial", FontWeight.BOLD,30));
        GridPane.setHalignment(loginGreeting, HPos.CENTER);

        // TextFields for login
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPrefColumnCount(8);
        passwordField.setPrefColumnCount(8);
        usernameField.setPromptText("Enter username");
        passwordField.setPromptText("Enter password");
        usernameField.focusedProperty().addListener((observable,  oldValue,  newValue) -> { // this is to remove focus from textfield when it runs
            if(newValue && firstTime.get()){
                loginPage.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        // Buttons for login
        Button loginUser = new Button("User\nLogin");
        loginUser.setStyle("-fx-text-alignment: center");
        GridPane.setHalignment(loginUser, HPos.LEFT);
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-text-alignment: center");
        GridPane.setHalignment(loginUser, HPos.RIGHT);
        backButton.setId("loginButton");
        loginUser.setId("loginButton");

        // ALL the login stuff in CENTER
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(loginUser, backButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(loginGreeting,usernameField,passwordField,hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setMaxWidth(200);
        vBox.setPadding(new Insets(0,0,80,0));
        loginPage.setCenter(vBox);

        // login button function
        loginUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    File adminLogin = new File("user.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(adminLogin));
                    String readLine;
                    while ((readLine = reader.readLine()) != null) {
                        String delimiter = " ";
                        String[] tokens = readLine.split(delimiter);
                        String username = tokens[0];
                        String password = tokens[1];
                        if (usernameField.getText().equals(username) && passwordField.getText().equals(password)){
                            // write date of login to adminLoginData.txt here
                            recordUserLogin();
                            // creates pop up notification at the corner
                            Notifications.create()
                                    .title("Login Successful!")
                                    .text("You have Logged In as User")
                                    .hideAfter(new Duration(2000))
                                    .showInformation();
                            UserPage(primaryStage);
                        }
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    LoginSelection(primaryStage);
                } catch (IOException e) {
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

    }

    // create an object to link to the admins page
    public void AdminPage(Stage primaryStage) throws IOException {
        AdminMainPage adminMainPage = new AdminMainPage(primaryStage);
    }

    public void UserPage(Stage primaryStage) throws IOException{
        UserMainPage userMainPage = new UserMainPage(primaryStage);
    }

    public void LoginSelection(Stage primaryStage) throws IOException{
        LoginSelection loginSelection = new LoginSelection();
        loginSelection.start(primaryStage);
    }

    private void recordUserLogin() throws IOException {
        // to log login date and time
        Date date = new Date();
        SimpleDateFormat simpleDate =
                new SimpleDateFormat("E, dd/MM/yyyy 'at' hh:mm:ss a");
        File loginInfo = new File("userLoginData.txt");
        FileWriter writer = new FileWriter(loginInfo, true);
        try {
            if (loginInfo.createNewFile()){
                System.out.println("File created");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // write date of login to adminLoginData.txt here
        writer.write(simpleDate.format(date) + "\n");
        writer.close();
    }

}
