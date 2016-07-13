/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048.game;

/**
 * Virtual Gameboard
 * @author pczee
 */
public class GameBoard {
    private GameTile[] gameTiles;
    
    public GameBoard () {
        gameTiles = new GameTile[12];
        for (int i=0; i<12;i++) {
            gameTiles[i] = new GameTile ();
        }
    }
}
