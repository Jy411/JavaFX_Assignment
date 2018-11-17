import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChangePassword_User extends Application{
    public static void main (String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub

        Label lblusername = new Label("Username ");
        TextField tfusername = new TextField();
        Label lblpassword = new Label("Password" );
        PasswordField pfold = new PasswordField();
        Label lblnewpass = new Label("New Password" );
        PasswordField pfnew = new PasswordField();
        Label lblconfirmpass = new Label("Confirm New Password" );
        PasswordField pfconfirmnew = new PasswordField();
        Label lblmessage = new Label();

        Button btnsubmit = new Button("Submit");
        Button btnBack = new Button("Back");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblusername, 0, 0);
        grid.add(tfusername, 1, 0);
        grid.add(lblpassword, 0, 1);
        grid.add(pfold, 1, 1);
        grid.add(lblnewpass, 0, 2);
        grid.add(pfnew, 1, 2);
        grid.add(lblconfirmpass, 0, 3);
        grid.add(pfconfirmnew, 1, 3);
        grid.add(btnsubmit, 0, 4);
        grid.add(btnBack, 1, 4);

        BorderPane bpane = new BorderPane();
        bpane.setCenter(grid);
        bpane.setRight(lblmessage);

        btnsubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String line, username, password;
                    Scanner readFile = new Scanner(new File ("user.txt"));
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


            }
        });

        Scene scene1 = new Scene(bpane);

        stage.setScene(scene1);
        stage.show();
    }

}
