import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Register_User extends Application {

    public Register_User(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        // Add Header
        Label headerLabel = new Label("User Registration Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add Name Label
        Label nameLabel = new Label("Username : ");
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 2);

        // Add Password Text Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 2);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setId("loginButton");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 4, 2, 1);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        // Add back Button
        Button back = new Button("Back");
        back.setId("loginButton");
        back.setPrefHeight(40);
        back.setDefaultButton(true);
        back.setPrefWidth(100);
        gridPane.add(back, 0, 4, 2, 1);
        GridPane.setHalignment(back, HPos.LEFT);
        GridPane.setMargin(back, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter username");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your password");
                    return;
                }
                if(passwordField!=null) {
                    try (PrintWriter out = new PrintWriter(new BufferedWriter(
                            new FileWriter("user.txt", true)))) {
                        out.printf(nameField.getText());
                        out.printf(",");
                        out.println(passwordField.getText());
                        showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Successfully Registered " + nameField.getText());
                        LoginSelection loginSelection = new LoginSelection();
                        loginSelection.start(primaryStage);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    RegisterSelection(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 500, 500);
        scene.getStylesheets().add(getClass().getResource("Buttons.css").toExternalForm());
        // Set the scene in primary stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }


    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }



    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void RegisterSelection(Stage primaryStage) throws IOException{
        RegisterSelection registerSelection = new RegisterSelection(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}