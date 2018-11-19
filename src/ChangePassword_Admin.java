import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ChangePassword_Admin extends Application{
    public static void main (String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label lblusername = new Label("Username ");
        lblusername.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        TextField tfusername = new TextField();
        Label lblpassword = new Label("Password" );
        lblpassword.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        PasswordField pfold = new PasswordField();
        Label lblnewpass = new Label("New Password" );
        lblnewpass.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        PasswordField pfnew = new PasswordField();
        Label lblconfirmpass = new Label("Confirm New Password" );
        lblconfirmpass.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        PasswordField pfconfirmnew = new PasswordField();
        Label lblmessage = new Label();
        lblmessage.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        Button btnsubmit = new Button("Submit");
        Button btnBack = new Button("Back");
        btnBack.setId("loginButton");
        btnsubmit.setId("loginButton");

        VBox Info = new VBox();
        Info.setAlignment(Pos.CENTER);
        Info.setMinSize(250,300);
        Info.setSpacing(8);
        Info.setPadding(new Insets(10,10,10,10));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(btnsubmit,btnBack);

        Info.getChildren().addAll(lblusername,tfusername,lblpassword,pfold,lblnewpass,pfnew,lblconfirmpass,pfconfirmnew,hBox);

        BorderPane bpane = new BorderPane();
        bpane.setCenter(Info);
        bpane.setRight(lblmessage);

        btnsubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String line, username, password;
                    Scanner readFile = new Scanner(new File ("admin.txt"));
                    HashMap<String,String> idandpass = new HashMap<String,String>();

                    while (readFile.hasNext()) {
                        line = readFile.nextLine();
                        String[] parts = line.split(" ", 2);
                        idandpass.put(parts[0], parts[1]);
                    }

                    username = tfusername.getText();
                    password = pfold.getText();

                    if (idandpass.containsKey(username)) {
                        if (password.equals(idandpass.get(username))) {
                            if (pfnew.getText().equals(pfconfirmnew.getText()) && !pfnew.getText().isEmpty()) {
                                password = pfnew.getText();
                                idandpass.put(username, password);
                                PrintWriter writeFile = new PrintWriter("details.txt");
                                idandpass.forEach((key, value) -> writeFile.println(key + " " + value));
                                lblmessage.setText("Hello " + username + ", Your password has been updated");
                                writeFile.close();
                            }
                            else {
                                lblmessage.setText("Your new password is \n not correct.\nPlease try again!");
                            }
                        }
                        else {
                            lblmessage.setText("Error username or \n old password\n not found in record!");
                        }
                    }
                    else {
                        lblmessage.setText("Error username or \nold password\n not found in record!");
                    }


                    readFile.close();

                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                }
            }
        });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PasswordChange_Selection(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Scene scene1 = new Scene(bpane);
        stage.setScene(scene1);
        stage.show();
    }
    public void PasswordChange_Selection(Stage primaryStage)throws IOException {
        PasswordChange_Selection passChange =new PasswordChange_Selection(primaryStage);

    }

}
