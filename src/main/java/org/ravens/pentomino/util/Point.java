package org.ravens.pentomino.util;

public class Point extends java.awt.Point {
    
    private static final long serialVersionUID = 8173819727048091755L;
    
    
    public Point(final int x, final int y) {
        super(x, y);
    }
    
    
    public Point add(final Point that) {
        return new Point(this.x + that.x, this.y - that.y);
    }


    public Point subtract(final Point that) {
        return new Point(this.x - that.x, this.y - that.y);
    }
    
}
