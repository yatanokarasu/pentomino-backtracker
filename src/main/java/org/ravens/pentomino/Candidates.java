/*
 *   Copyright 2015 yatanokarasu.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.ravens.pentomino;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ravens.pentomino.Pentomino.Candidate;
import org.ravens.pentomino.util.Grid;


public class Candidates implements Iterable<Candidate> {
    
    private static interface Closure<T> {
        
        void run(final T object);
        
    }
    
    
    private class Itr implements Iterator<Candidate> {
        
        private final Iterator<Candidate> candidates;
        
        
        private Itr() {
            this.candidates = Candidates.this.candidates.iterator();
        }
        
        
        @Override
        public boolean hasNext() {
            if (!this.candidates.hasNext()) {
                return false;
            }
            
            return this.noExistAdoptedCandidate();
        }
        
        
        private boolean noExistAdoptedCandidate() {
            for (final Candidate candidate : Candidates.this.candidates) {
                if (candidate.isAdopted()) {
                    return false;
                }
            }
            
            return true;
        }
        
        
        @Override
        public Candidate next() {
            return this.candidates.next();
        }
        
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    private final List<Candidate> candidates;
    
    
    public Candidates(final Grid grid) {
        this.candidates = this.createCandidates(grid);
    }
    
    
    @Override
    public Iterator<Candidate> iterator() {
        return new Itr();
    }
    
    
    private List<Candidate> createCandidates(final Grid grid) {
        final List<Candidate> list = new ArrayList<Candidate>();
        final Closure<Grid> closure = new Closure<Grid>() {
            
            @Override
            public void run(final Grid grid) {
                final Candidate candidate = new Candidate(grid);
                
                if (list.contains(candidate)) {
                    return;
                }
                
                list.add(candidate);
            }
            
        };
        
        this.repeat(4, grid, closure);
        final Grid reverseGrid = this.turnOver(grid);
        this.repeat(4, reverseGrid, closure);
        
        return list;
    }
    
    
    private void repeat(final int count, final Grid grid, final Closure<Grid> closure) {
        Grid candidateGrid = grid;
        
        for (int i = 0; i < count; i++) {
            closure.run(candidateGrid);
            candidateGrid = candidateGrid.rotate90();
        }
    }
    
    
    private Grid turnOver(final Grid grid) {
        return grid.flipLeftRight();
    }


    public void write(final String[][] viewer) {
        for (final Candidate candidate : this.candidates) {
            if (!candidate.isAdopted()) {
                continue;
            }
            
            final Grid grid = candidate.grid();
            grid.write(viewer);
            
            return;
        }
    }
    
}
