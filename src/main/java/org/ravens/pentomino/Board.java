package org.ravens.pentomino;


import java.awt.Rectangle;

import org.ravens.pentomino.Pentomino.Candidate;
import org.ravens.pentomino.util.Grid;
import org.ravens.pentomino.util.Point;


public class Board {
    
    private final Grid grid;
    
    
    private Board(final int row, final int column) {
        this.grid = Grid.newGrid("-", new boolean[row][column]);
    }
    
    
    public static Board newBoard(final int row, final int column) {
        return new Board(row, column);
    }
    
    
    public boolean hasFilled() {
        return this.grid.allTrue();
    }
    
    
    public boolean hasOtherThan(final Candidate pentomino) {
        final Grid trimedGrid = this.grid.trim(pentomino.grid());
        trimedGrid.and(pentomino.grid());
        
        return trimedGrid.anyTrue();
    }
    
    
    public Point minPoint() {
        return this.grid.firstEmptyPoint();
    }
    
    
    public void place(final Candidate candidate) {
        this.grid.add(candidate.grid());
    }
    
    
    public void purge(final Candidate candidate) {
        this.grid.subtract(candidate.grid());
    }


    public Rectangle region() {
        return this.grid.region();
    }
    
}
