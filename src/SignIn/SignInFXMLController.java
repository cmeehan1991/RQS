/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignIn;

import Connections.DBConnection;
import SalesMain.SalesMain;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class SignInFXMLController implements Initializable {

    @FXML private Button signInButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    
    @FXML protected void logIn(ActionEvent event){
        Stage stage = (Stage) signInButton.getScene().getWindow();
        UserSignIn userSignIn = new UserSignIn(usernameTextField.getText(), passwordTextField.getText(), stage);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
