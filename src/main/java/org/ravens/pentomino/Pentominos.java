package org.ravens.pentomino;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.ravens.pentomino.Pentomino.Candidate;


public class Pentominos implements Iterable<Candidate> {
    
    private class Itr implements Iterator<Candidate> {
        
        private final Iterator<Pentomino> pentominos;
        
        private Iterator<Candidate>       candidates;
        
        
        private Itr() {
            this.pentominos = Pentominos.this.pentominos.iterator();
        }
        
        
        @Override
        public boolean hasNext() {
            if ((this.candidates != null) && this.candidates.hasNext()) {
                return true;
            }
            
            return this.hasMoreCandidate();
        }
        
        
        @Override
        public Candidate next() {
            if ((this.candidates != null) && this.candidates.hasNext()) {
                return this.candidates.next();
            }
            
            if (this.hasMoreCandidate()) {
                return this.candidates.next();
            }
            
            throw new NoSuchElementException();
        }
        
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        
        private boolean hasMoreCandidate() {
            while (this.pentominos.hasNext()) {
                final Pentomino pentomino = this.pentominos.next();
                this.candidates = pentomino.candidates();
                
                if (this.candidates.hasNext()) {
                    return true;
                }
            }
            
            return false;
        }
    }
    
    private final List<Pentomino> pentominos;
    
    
    public Pentominos(final Pentomino[] pentominos) {
        final List<Pentomino> list = new ArrayList<Pentomino>();
        
        for (final Pentomino pentomino : pentominos) {
            list.add(pentomino);
        }
        
        Collections.shuffle(list);
        this.pentominos = list;
    }
    
    
    @Override
    public Iterator<Candidate> iterator() {
        return new Itr();
    }


    public void display() {
        final String[][] viewer = new String[6][10];
        
        for (final Pentomino pentomino : this.pentominos) {
            pentomino.write(viewer);
        }
        
        for (final String[] elements : viewer) {
            for (final String str : elements) {
                System.out.print(str);
            }
            
            System.out.println();
        }
    }
    
}
