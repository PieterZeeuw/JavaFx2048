/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Map.Entry;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx2048.game.GameManager;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx2048.game.GridOperator;
import javafx2048.game.Location;
import javafx2048.game.SessionManager;
import javafx2048.game.Tile;

/**
 *
 * @author pczee
 */
public class GameBoardController {

    // Class Fields
    private GameManager game;

    // JavaFX Components    
    @FXML
    private GridPane boardGridPane;

//  Properties die score bijhouden, en game won/lost. Score properties bound to label
    private final IntegerProperty gameScoreProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty gameBestProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty gameMovePoints = new SimpleIntegerProperty(0);
    private final BooleanProperty gameWonProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameOverProperty = new SimpleBooleanProperty(false);

//  Properties die bij de knoppen horen. Moet nog implementeren.
//    private final BooleanProperty gameAboutProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty gamePauseProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty gameTryAgainProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty gameSaveProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty gameRestoreProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty gameQuitProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty layerOnProperty = new SimpleBooleanProperty(false);
//    private final BooleanProperty resetGame = new SimpleBooleanProperty(false);
//    private final BooleanProperty clearGame = new SimpleBooleanProperty(false);
//    private final BooleanProperty restoreGame = new SimpleBooleanProperty(false);
//    private final BooleanProperty saveGame = new SimpleBooleanProperty(false);
//  Properties voor tijd. Clock property bound to timeLabel
    private LocalTime time;
    private Timeline timer;
    private final StringProperty clock = new SimpleStringProperty("00:00:00");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

//  Labels, gekoppelt aan fxID in GameBoard.fxml
    @FXML
    private VBox vBoxScore;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblSubtitle;

    @FXML
    private VBox vBoxBestScore;
    @FXML
    private Label lblTit;
    @FXML
    private Label lblScore;

    @FXML
    private Label lblTitBest;
    @FXML
    private Label lblBest;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblPoints;

//  Overlay, toont twee knoppen over het spel heen als er op een knop wordt gedrukt (bv Quit, Save/Load, etc.)
//    private final HBox overlay = new HBox();
//    private final VBox txtOverlay = new VBox(10);
//    private final Label lOvrText= new Label();
//    private final Label lOvrSubText= new Label();
//    private final HBox buttonsOverlay = new HBox();
//  Knoppen: Later implementeren
//    private final Button bTry = new Button("Try again");
//    private final Button bContinue = new Button("Keep going");
//    private final Button bContinueNo = new Button("No, keep going");
//    private final Button bSave = new Button("Save");
//    private final Button bRestore = new Button("Restore");
//    private final Button bQuit = new Button("Quit");
//    private final HBox hToolbar = new HBox(); // Hier zitten de knoppen in.
//    private HostServices hostServices;
    private Timeline timerPause;

    private int gridWidth;
    private GridOperator gridOperator;
    private SessionManager sessionManager;

    private GameManager gameManager;

    public void GameBoardController() {
        gameManager = new GameManager();
        gameManager.setGridOperator(new GridOperator());
        startGame ();
    }

    @FXML
    public void initialize() {
        initTitleLabels();
        initScoreLabels();
        initBestScoreLabels();
        initTimeLabel();
        initPointsLabel();
        //redrawGameGrid();
        
    }

    public void startGame() {
//        time = LocalTime.now();
//        timer.playFromStart();
        redrawGameGrid(gameManager.getGameGrid());
    }

    // Function for adding style to labels:
    private void initTitleLabels() {
        vBoxScore.getStyleClass().add("game-vbox");
        lblTitle.getStyleClass().addAll("game-label", "game-title");
        lblSubtitle.getStyleClass().addAll("game-label", "game-subtitle");
    }

    private void initScoreLabels() {
        vBoxBestScore.getStyleClass().add("game-vbox");
        lblTit.getStyleClass().addAll("game-label", "game-titScore");
        lblScore.getStyleClass().addAll("game-label", "game-score");
        lblScore.textProperty().bind(gameScoreProperty.asString());
    }

    private void initBestScoreLabels() {
        lblTitBest.getStyleClass().addAll("game-label", "game-titScore");
        lblBest.getStyleClass().addAll("game-label", "game-score");
        lblBest.textProperty().bind(gameBestProperty.asString());

    }

    private void initTimeLabel() {
        lblTime.getStyleClass().addAll("game-label", "game-time");
        lblTime.textProperty().bind(clock);
        timer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }), new KeyFrame(Duration.seconds(1)));
        timer.setCycleCount(Animation.INDEFINITE);
    }

    private void initPointsLabel() {
        lblPoints.getStyleClass().addAll("game-label", "game-points");
    }

    public void setGridOpererator(GridOperator gridOperator) {
        this.gridOperator = gridOperator;
    }

    private void redrawGameGrid(Map<Location, Tile> gameGrid) {

        for (Entry<Location, Tile> entry : gameGrid.entrySet()) {
            if (entry.getKey() != null) {
                System.out.println("" + entry.getKey().convertToArrayIndex());
//                StackPane pane = (StackPane) boardGridPane.getChildren().get(entry.getKey().convertToArrayIndex());
                updateGridNode((StackPane) boardGridPane.getChildren().get(entry.getKey().convertToArrayIndex()), entry.getValue());
            }
        }
    }

    private void updateGridNode(StackPane pane, Tile tile) {
        Text text = (Text) pane.getChildren().get(0);
        if (text.getStyleClass().size() > 0) {
            text.getStyleClass().remove(0, text.getStyleClass().size());
        }

        if (pane.getStyleClass().size() > 0) {
            pane.getStyleClass().remove(0, pane.getStyleClass().size());
        }

        if (tile == null) {
            text.getStyleClass().addAll("game-text-0");
            pane.getStyleClass().addAll("game-tile-0");
            text.setText("");
        } else {
            text.getStyleClass().addAll("game-text-" + tile.getValue());
            pane.getStyleClass().addAll("game-tile-" + tile.getValue());
            text.setText("" + tile.getValue());
        }
    }

    public void addTile(Tile tile) {
//        double layoutX = tile.getLocation().getLayoutX(CELL_SIZE) - (tile.getMinWidth() / 2);
//        double layoutY = tile.getLocation().getLayoutY(CELL_SIZE) - (tile.getMinHeight() / 2);
//
//        tile.setLayoutX(layoutX);
//        tile.setLayoutY(layoutY);
//        gridGroup.getChildren().add(tile);
    }

}
