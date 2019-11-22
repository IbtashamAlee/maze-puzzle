package layoutStrategies;

import interfaces.ILayoutStrategy;
import objects.Board;

public enum LayoutStrategy implements ILayoutStrategy {
    RECURSIVE_BACK_TRACK {
        public void layoutBoard(Board board) {
            new RecursiveBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    SIMPLE_BACK_TRACK {
        public void layoutBoard(Board board) {
            new SimpleBackTrackLayoutStrategy().layoutBoard(board);
        }
    },
    PRIM_RANDOMIZATION {
        @Override
        public void layoutBoard(Board board) {
            new PrimRandomizationLayoutStrategy().layoutBoard(board);
        }
    }
}
