package org.ravens.pentomino;


import org.ravens.pentomino.Pentomino;
import org.ravens.pentomino.util.Grid;


public class PentominoFactory {
    

    public static Pentomino create(final Grid grid) {
        return new Pentomino(grid);
    }

}
