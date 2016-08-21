/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import softviewerfx.*;

/**
 *
 * @author Roger
 */
public class SoftViewerFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/softviewerfx/views/AppLayout.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(700);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
