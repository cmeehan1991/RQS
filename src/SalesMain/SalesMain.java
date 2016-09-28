/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesMain;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author cmeehan
 */
public final class SalesMain extends Application {

    public final String userID;

    public SalesMain(String userID) throws IOException {
        this.userID = userID;
        start(new Stage());
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainFXMLDocument.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("Styles/mainfxmldocument.css");

        stage.setY(10.00);
        stage.setX(screenWidth() - stage.getWidth() / 2);
        stage.setTitle("RQS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
        });
        stage.show();
        stage.getIcons().add(new Image("Images/drive_green_project.ico"));
    }

    private double screenWidth() {
        double width;
        Rectangle2D rectangle = Screen.getPrimary().getBounds();
        width = rectangle.getWidth();
        return width;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
