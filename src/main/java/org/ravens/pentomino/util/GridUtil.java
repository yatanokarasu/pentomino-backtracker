package org.ravens.pentomino.util;

public class GridUtil {
    
    public static Grid array2grid(final String symbol, final String... parts) {
        final int row = parts.length;
        final boolean[][] bitmap = new boolean[row][];
        
        for (int i = 0; i < row; i++) {
            bitmap[i] = GridUtil.column(parts[i]);
        }
        
        return Grid.newGrid(symbol, bitmap);
    }
    
    
    public static Grid string2grid(final String symbol, final String bitmap, final int row) {
        if (!GridUtil.dividable(bitmap, row)) {
            throw new IllegalArgumentException();
        }
        
        final String[] rows = GridUtil.divide(bitmap, row);
        
        return GridUtil.array2grid(symbol, rows);
    }
    
    
    private static boolean[] column(final String row) {
        final int column = row.length();
        final boolean[] rowmap = new boolean[column];
        
        for (int i = 0; i < column; i++) {
            rowmap[i] = row.charAt(i) == '1' ? true : false;
        }
        
        return rowmap;
    }
    
    
    private static boolean dividable(final String charBitmap, final int row) {
        return (charBitmap.length() % row) == 0;
    }
    
    
    private static String[] divide(final String bitmap, final int row) {
        final String[] rows = new String[row];
        
        for (int i = 0; i < row; i++) {
            final int start = i * row;
            
            rows[i] = bitmap.substring(start, start + row);
        }
        
        return rows;
    }
    
}
