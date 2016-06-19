package com.ensicaen.htmledit.main;

import com.ensicaen.htmledit.fxml.RootLayoutController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Jérémie Leclerc : jeremie.leclerc@fime.com
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/ensicaen/htmledit/fxml/RootLayout.fxml"));
            BorderPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.getIcons().add(new Image("/com/ensicaen/htmledit/images/icon.png"));
            primaryStage.setTitle("HTMLEdit v1.0");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            RootLayoutController rlc = loader.getController();
            rlc.setPrimaryStage(primaryStage);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Unable to load fxml file.");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
