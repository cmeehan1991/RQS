/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignIn;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author cmeehan
 */
public class SignInMain extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignInFXML.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("Styles/signinfxml.css");
        
        //Listen for enter key to be pressed
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case ENTER:
                    new SignInFXMLController().logIn(new ActionEvent());
                    break;
                default:
                    break;
            }
        });
        
        stage.setTitle("RQS");
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
