/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesMain;

import NewQuote.NewQuoteMain;
import UserInformation.UserInformationMain;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.lang.String;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class MainFXMLDocumentController implements Initializable {

    @FXML
    private void userInformationClick(ActionEvent event) {
        new UserInformationMain().start(new Stage());
    }

    @FXML
    private void newQuote(ActionEvent event) throws IOException, SQLException {
        String userID = "1";
        new NewQuoteMain(userID).start(new Stage());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
