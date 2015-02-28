package org.ravens.pentomino;


import org.ravens.pentomino.util.Grid;
import org.ravens.pentomino.util.GridUtil;
import org.ravens.pentomino.util.Point;


public class PentominoBacktracker {
    
    private final Board      board;
    
    private final Pentominos pentominos;
    
    
    public PentominoBacktracker(final Board board, final Pentominos pentominos) {
        this.board = board;
        this.pentominos = pentominos;
    }
    
    
    public static void main(final String[] args) {
        final Pentomino[] pentominoArray = {
            PentominoBacktracker.pentomino("F", "011", "110", "010"),   // f
            PentominoBacktracker.pentomino("Z", "011", "010", "110"),   // Z
            PentominoBacktracker.pentomino("T", "111", "010", "010"),   // T
            PentominoBacktracker.pentomino("V", "100", "100", "111"),   // v
            PentominoBacktracker.pentomino("W", "100", "110", "011"),   // w
            PentominoBacktracker.pentomino("X", "010", "111", "010"),   // x
            PentominoBacktracker.pentomino("L", "1000", "1111"),        // L
            PentominoBacktracker.pentomino("N", "1110", "0011"),        // N
            PentominoBacktracker.pentomino("Y", "0010", "1111"),        // y
            PentominoBacktracker.pentomino("P", "111", "011"),          // p
            PentominoBacktracker.pentomino("U", "101", "111"),          // u
            PentominoBacktracker.pentomino("I", "11111"),               // I
        };
        final Pentominos pentominos = new Pentominos(pentominoArray);
        final Board board = Board.newBoard(6, 10);
        
        new PentominoBacktracker(board, pentominos).execute();
    }
    
    
    private static Pentomino pentomino(final String symbol, final String... parts) {
        final Grid grid = GridUtil.array2grid(symbol, parts);
        
        return PentominoFactory.create(grid);
    }
    
    
    public void execute() {
        if (!this.doBacktrack()) {
            System.err.println("Error: Pentominos could not be placed on the Board.");
            
            return;
        }
        
        this.pentominos.display();
    }
    
    
    private boolean canBeCandidate(final Pentomino.Candidate candidate) {
        if (!candidate.canBeOn(this.board)) {
            return false;
        }
        
        return !this.board.hasOtherThan(candidate);
    }
    
    
    private boolean doBacktrack() {
        for (final Pentomino.Candidate candidate : this.pentominos) {
            final Point minimumPoint = this.board.minPoint();
            candidate.moveTo(minimumPoint);
            
            if (!this.canBeCandidate(candidate)) {
                continue;
            }
            
            this.board.place(candidate);
            candidate.adoptAsCandidate();
            
            if (this.board.hasFilled()) {
                return true;
            }
            
            if (this.doBacktrack()) {
                return true;
            }
            
            this.board.purge(candidate);
            candidate.ruleOutAsCandidate();
        }
        
        return false;
    }
    
}
