/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import javafx2048.GameBoardController;

/**
 *
 * @author pczee
 */
public class GameManager {

    private volatile boolean movingTiles = false;
    private final List<Location> locations = new ArrayList<>();
    private final Map<Location, Tile> gameGrid;
    private final Set<Tile> mergedToBeRemoved = new HashSet<>();

    private final GameBoardController board;
    private final GridOperator gridOperator;

    public GameManager(GameBoardController board) {
        this(GridOperator.DEFAULT_GRID_SIZE, board);
    }

    public GameManager(int gridSize, GameBoardController board) {
        
        this.board = board;
        this.gameGrid = new HashMap<>();

        gridOperator = new GridOperator(gridSize);
        //board = new Board(gridOperator);
        //this.getChildren().add(board);
    }

    /**
     * Initializes all cells in gameGrid map to null
     */
    private void initializeGameGrid() {
        gameGrid.clear();
        locations.clear();
        gridOperator.traverseGrid((x, y) -> {
            Location thisloc = new Location(x, y);
            locations.add(thisloc);
            gameGrid.put(thisloc, null);
            return 0;
        });
    }

    /**
     * Starts the game by adding 1 or 2 tiles at random locations
     */
    private void startGame() {
        Tile tile0 = Tile.newRandomTile();
        List<Location> randomLocs = new ArrayList<>(locations);
        Collections.shuffle(randomLocs);
        Iterator<Location> locs = randomLocs.stream().limit(2).iterator();
        tile0.setLocation(locs.next());

        Tile tile1 = null;
        if (new Random().nextFloat() <= 0.8) { // gives 80% chance to add a second tile
            tile1 = Tile.newRandomTile();
            if (tile1.getValue() == 4 && tile0.getValue() == 4) {
                tile1 = Tile.newTile(2);
            }
            tile1.setLocation(locs.next());
        }

        Arrays.asList(tile0, tile1).stream().filter(Objects::nonNull)
                .forEach(t -> gameGrid.put(t.getLocation(), t));

        //redrawTilesInGameGrid();
        //board.startGame();
    }

}
