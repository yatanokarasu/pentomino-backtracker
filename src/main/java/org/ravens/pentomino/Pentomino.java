package org.ravens.pentomino;


import java.awt.Rectangle;
import java.util.Iterator;

import org.ravens.pentomino.util.Grid;
import org.ravens.pentomino.util.Point;


public class Pentomino {
    
    public static class Candidate {
        
        private final Grid grid;
        
        private boolean    adopted;
        
        
        public Candidate(final Grid grid) {
            this.grid = grid;
            this.adopted = false;
        }
        
        
        public void adoptAsCandidate() {
            this.adopted = true;
        }
        
        
        public boolean canBeOn(final Board board) {
            final Rectangle candidateRegion = this.grid.region();
            final Rectangle boardRegion = board.region();
            
            return boardRegion.contains(candidateRegion);
        }
        
        
        @Override
        public boolean equals(final Object target) {
            if (this == target) {
                return true;
            }
            
            if (!(target instanceof Candidate)) {
                return false;
            }
            
            final Candidate that = (Candidate) target;
            
            return this.grid.equals(that.grid);
        }
        
        
        public Grid grid() {
            return this.grid;
        }
        
        
        @Override
        public int hashCode() {
            return this.grid.hashCode();
        }
        
        
        public boolean isAdopted() {
            return this.adopted;
        }
        
        
        public void moveTo(final Point locate) {
            this.grid.moveMinPointTo(locate);
        }
        
        
        public void ruleOutAsCandidate() {
            this.adopted = false;
        }
        
        
        @Override
        public String toString() {
            return this.grid.toString();
        }
        
    }
    
    private final Candidates candidates;
    
    
    public Pentomino(final Grid grid) {
        this.candidates = new Candidates(grid);
    }
    
    
    public Iterator<Candidate> candidates() {
        return this.candidates.iterator();
    }
    
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        
        /*for (int i = 0; i < this.candidates.size(); i++) {
            builder.append(String.format("\nCandidate-%02d:\n", Integer.valueOf(i + 1)));
            builder.append(this.candidates.get(i).toString());
        }*/
        
        return builder.toString();
    }


    public void write(final String[][] viewer) {
        this.candidates.write(viewer);
    }
    
}
