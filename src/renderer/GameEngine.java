package renderer;

import enums.GameState;
import interfaces.Constants;
import interfaces.GameActions;
import interfaces.RenderAction;
import interfaces.WonSignal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import layoutStrategies.LayoutStrategy;
import objects.Player;
import objects.Puzzle;

import static javafx.scene.input.KeyCode.*;

public class GameEngine extends Application implements Constants, GameActions, WonSignal, RenderAction {
    private static GameState gameState;
    private Puzzle puzzle;
    private Stage primaryStage;
    private Scene scene;
    private Player player;
    private RenderEngine renderEngine;

    private EventHandler<KeyEvent> KbdEventsHandler = this::kbdEvents;
    private boolean maximized = DEFAULT_WINDOW_MAXIMIZED;

    public static void runGame() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        scene = new Scene(new Label("Loading..."));
        newGame(DEFAULT_MAZE_WIDTH, DEFAULT_MAZE_HEIGHT, LayoutStrategy.RECURSIVE_BACK_TRACK);
    }

    private void kbdEvents(KeyEvent ke) {
        KeyCode kc = ke.getCode();
        if ((kc == UP || kc == DOWN || kc == LEFT || kc == RIGHT) && gameState == GameState.PLAYING) {
            player.move(kc);
        } else {
            switch (kc) {
                case R:
                    player.reset();
                    gameState = GameState.PLAYING;
                    break;
            }
        }
        ke.consume();
    }

    private void newGame(int width, int height, LayoutStrategy layoutStrategy) {
        puzzle = new Puzzle(width, height, layoutStrategy);
        player = new Player(puzzle.getBoard(), this);
        gameState = GameState.PLAYING;

        renderEngine = new RenderEngine(puzzle, player, this, this);

        scene.setRoot(renderEngine.getRoot());
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        adjustStageSize(maximized);

        scene.getStylesheets().add(getClass().getResource("../styling/style.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, KbdEventsHandler);
        primaryStage.maximizedProperty().addListener((ov, t, t1) -> {
            maximized = t1;
            adjustStageSize(maximized);
        });

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(maximized);
        primaryStage.show();
        renderEngine.animateRandom();
    }

    private void adjustStageSize(boolean maximixed) {
        if (!maximixed) {
            primaryStage.setWidth((puzzle.getBoard().getWidth() + MAZE_PADDING) * PIXEL_SIZE);
            primaryStage.setHeight((puzzle.getBoard().getHeight() + MAZE_PADDING) * PIXEL_SIZE);
        }
    }

    @Override
    public void shuffle() {
        newGame(puzzle.getBoard().getWidth(), puzzle.getBoard().getHeight(), puzzle.getLayoutStrategy());
    }

    @Override
    public void solve() {
        // TODO:: SOLVE
    }

    @Override
    public void changeSize(int width, int height) {
        newGame(width, height, puzzle.getLayoutStrategy());
    }

    @Override
    public void gameWon() {
        gameState = GameState.WON;
        System.out.println("WON");
        System.out.println("SCORE : " + player.getScore());
    }

    @Override
    public void cellUpdated(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
