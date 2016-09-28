/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewQuote;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author cmeehan
 */
public class NewQuoteMain extends Application {
    protected final String userID;
    public NewQuoteMain(String userID){
        this.userID = userID;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuoteFXMLDocument.fxml"));
        Parent root = loader.load();
        NewQuoteFXMLDocumentController controller = loader.getController();
                
        Scene scene = new Scene(root);
        scene.getStylesheets().add("Styles/newquotefxmldocument.css");
       
        
        primaryStage.setTitle("New Quote | RQS");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("Images/drive_green_project.jpg"));    
        
        try{
        // Set Data to FXML through controller
        controller.setTradeLanes();
        controller.setCommodities();
        controller.setHandling();
        controller.setRateUnit();
        controller.setDeclineReasons();
        controller.userID = userID;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
