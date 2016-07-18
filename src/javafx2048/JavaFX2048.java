/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx2048.game.GameManager;

/**
 *
 * @author pczee
 */
public class JavaFX2048 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader ();        
        loader.setLocation(getClass().getResource("GameBoard.fxml"));
        
        Parent root = loader.load();
        GameBoardController controller = (GameBoardController) loader.getController();
        
        GameManager gameManager = new GameManager (controller);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("javafx2048/game.css");
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
