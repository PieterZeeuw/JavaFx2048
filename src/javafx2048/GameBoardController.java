/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048;

import javafx2048.game.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 *
 * @author pczee
 */
public class GameBoardController  {
    // Class Fields
    private Game game;
    
    // JavaFX Components    
    @FXML
    private GridPane boardGridPane;
    
    
    public void GameBoardController () {
        
    }
    
    @FXML
    public void initialize() {
        System.out.println("second");
    }

    
}
