package renderer;

import enums.Direction;
import enums.LayoutStrategy;
import interfaces.Constants;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import objects.Puzzle;

public class Init extends Application implements Constants {
    private Puzzle puzzle;

    public static void main(String[] args) {
        Init.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        puzzle = new Puzzle(20, LayoutStrategy.SIMPLE_BACK_TRACK);


        Parent root = (new RenderEngine(puzzle)).getRoot();
        Scene scene = new Scene(root, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::kbdEventsHandler);

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void kbdEventsHandler(KeyEvent ke){
        System.out.println("Key Pressed: " + ke.getCode());
        switch (ke.getCode()){
            case UP:
                puzzle.movePlayer(Direction.UP);
                break;
            case RIGHT:
                puzzle.movePlayer(Direction.RIGHT);
                break;
            case LEFT:
                puzzle.movePlayer(Direction.LEFT);
                break;
            case DOWN:
                puzzle.movePlayer(Direction.DOWN);
                break;
            case R:
                puzzle.getPlayer().reset();
                break;
        }
        ke.consume();
    }

}
