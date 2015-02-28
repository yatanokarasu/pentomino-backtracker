package org.ravens.pentomino.util;


import java.awt.Rectangle;
import java.util.Arrays;


public class Grid {
    
    private final boolean[][] elements;
    
    private final String      symbol;
    
    private final Rectangle   region;
    
    
    private Grid(final String symbol, final boolean[] elements) {
        this.assertGridSize(elements);
        
        this.symbol = symbol;
        this.elements = new boolean[][] {
            elements
        };
        
        this.region = new Rectangle(0, 0, this.elements[0].length, this.elements.length);
    }
    
    
    private Grid(final String symbol, final boolean[][] elements) {
        this.assertGridSize(elements);
        this.assertRowSizeIsAllEqual(elements);
        
        this.symbol = symbol;
        this.elements = elements;
        this.region = new Rectangle(0, 0, this.elements[0].length, this.elements.length);
    }
    
    
    public static Grid newGrid(final String symbol, final boolean[] elements) {
        return new Grid(symbol, elements);
    }
    
    
    public static Grid newGrid(final String symbol, final boolean[][] elements) {
        return new Grid(symbol, elements);
    }
    
    
    public boolean allTrue() {
        for (final boolean[] row : this.elements) {
            for (final boolean element : row) {
                if (!element) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    public void and(final Grid that) {
        final int height = that.region.height;
        final int width = that.region.width;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.elements[i][j] = this.elements[i][j] && that.elements[i][j];
            }
        }
    }
    
    
    public boolean anyTrue() {
        final int row = this.elements.length;
        final int column = this.elements[0].length;
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.elements[i][j]) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    @Override
    public boolean equals(final Object target) {
        if (this == target) {
            return true;
        }
        
        if (!(target instanceof Grid)) {
            return false;
        }
        
        final Grid that = (Grid) target;
        
        if ((this.elements.length != that.elements.length) || (this.elements[0].length != that.elements[0].length)) {
            return false;
        }
        
        for (int i = 0; i < this.elements.length; i++) {
            if (!Arrays.equals(this.elements[i], that.elements[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    
    public Point firstEmptyPoint() {
        for (int i = 0; i < this.elements.length; i++) {
            for (int j = 0; j < this.elements[i].length; j++) {
                if (!this.elements[i][j]) {
                    return new Point(j, i);
                }
            }
        }
        
        return new Point(0, 0);
    }
    
    
    public Grid flipLeftRight() {
        final int row = this.elements.length;
        final boolean[][] newElements = new boolean[row][];
        
        for (int i = 0; i < row; i++) {
            newElements[i] = this.reverse(this.elements[i]);
        }
        
        return new Grid(this.symbol, newElements);
    }
    
    
    @Override
    public int hashCode() {
        final int FACTOR = 37;
        int hashCode = 17;
        
        for (final boolean[] column : this.elements) {
            hashCode = (hashCode * FACTOR) + Arrays.hashCode(column);
        }
        
        return hashCode;
    }
    
    
    public int height() {
        return this.elements.length;
    }
    
    
    public Grid invert() {
        final int row = this.elements.length;
        final int column = this.elements[0].length;
        final boolean[][] elements = new boolean[row][column];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                elements[i][j] = !this.elements[i][j];
            }
        }
        
        final Grid grid = Grid.newGrid("", elements);
        grid.region.setBounds(this.region);
        
        return grid;
    }
    
    
    public void moveMinPointTo(final Point locate) {
        final Point newPoint = locate.subtract(this.firstElementPoint());
        
        this.region.setLocation(newPoint.x, newPoint.y);
    }
    
    
    public void add(final Grid that) {
        final Rectangle thatRegion = that.region;
        
        for (int i = 0, y = thatRegion.y; i < that.elements.length; i++, y++) {
            for (int j = 0, x = thatRegion.x; j < that.elements[0].length; j++, x++) {
                this.elements[y][x] = this.elements[y][x] || that.elements[i][j];
            }
        }
    }
    
    
    public Rectangle region() {
        return this.region;
    }
    
    
    public Grid rotate90() {
        final int column = this.elements[0].length;
        final boolean[][] newElements = new boolean[column][];
        
        for (int i = (column - 1), j = 0; i >= 0; i--, j++) {
            newElements[j] = this.columnVector(i);
        }
        
        return new Grid(this.symbol, newElements);
    }
    
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.region);
        builder.append("\n");
        
        for (final boolean[] element : this.elements) {
            builder.append("[");
            
            for (final boolean element2 : element) {
                builder.append(element2 ? "1" : "0");
            }
            
            builder.append("]\n");
        }
        
        return builder.toString();
    }
    
    
    public Grid trim(final Grid that) {
        final Rectangle trimer = that.region();
        final int x = trimer.x;
        final int y = trimer.y;
        final int height = trimer.height;
        final int width = trimer.width;
        
        final boolean[][] trimElement = new boolean[height][width];
        
        for (int i = 0, row = y; i < height; i++, row++) {
            for (int j = 0, column = x; j < width; j++, column++) {
                trimElement[i][j] = this.elements[row][column];
            }
        }
        
        final Grid grid = Grid.newGrid("", trimElement);
        grid.region.setBounds(that.region);
        
        return grid;
    }
    
    
    public int width() {
        return this.elements[0].length;
    }
    
    
    private void assertGridSize(final boolean[] elements) {
        if (elements.length < 1) {
            throw new IllegalArgumentException();
        }
    }
    
    
    private void assertGridSize(final boolean[][] elements) {
        if (elements.length < 1) {
            throw new IllegalArgumentException();
        }
        
        if (elements[0].length < 1) {
            throw new IllegalArgumentException();
        }
    }
    
    
    private void assertRowSizeIsAllEqual(final boolean[][] elements) {
        final int firstRowSize = elements[0].length;
        
        for (final boolean[] row : elements) {
            this.compareRowSize(row.length, firstRowSize);
        }
    }
    
    
    private boolean[] columnVector(final int column) {
        final int row = this.elements.length;
        final boolean[] vector = new boolean[row];
        
        for (int i = 0; i < row; i++) {
            vector[i] = this.elements[i][column];
        }
        
        return vector;
    }
    
    
    private void compareRowSize(final int mediumRowSize, final int firstRowSize) {
        if (mediumRowSize != firstRowSize) {
            throw new IllegalArgumentException();
        }
    }
    
    
    private Point firstElementPoint() {
        for (int i = 0; i < this.elements.length; i++) {
            for (int j = 0; j < this.elements[0].length; j++) {
                if (this.elements[i][j]) {
                    return new Point(j, i);
                }
            }
        }
        
        return new Point(0, 0);
    }
    
    
    private boolean[] reverse(final boolean[] row) {
        final int column = row.length;
        final boolean[] newColumn = new boolean[column];
        
        for (int i = column - 1, j = 0; i >= 0; i--, j++) {
            newColumn[j] = row[i];
        }
        
        return newColumn;
    }
    
    
    public void subtract(final Grid that) {
        final Rectangle thatRegion = that.region;
        
        for (int i = 0, y = thatRegion.y; i < that.elements.length; i++, y++) {
            for (int j = 0, x = thatRegion.x; j < that.elements[0].length; j++, x++) {
                this.elements[y][x] = this.elements[y][x] ^ that.elements[i][j];
            }
        }
    }
    
    
    /**
     * @param viewer
     */
    public void write(final String[][] viewer) {
        for (int i = 0, y = this.region.y; i < this.region.height; i++, y++) {
            for (int j = 0, x = this.region.x; j < this.region.width; j++, x++) {
                if (this.elements[i][j]) {
                    viewer[y][x] = this.symbol;
                }
            }
        }
    }
}
