package org.ravens.pentomino.util;


import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class GridTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalGridBecauseArrayIndexIsZero() {
        Grid.newGrid("", new boolean[0]);
        
        Assert.fail();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalGridBecauseHeightIndexIsZero() {
        Grid.newGrid("", new boolean[0][1]);
        
        Assert.fail();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalGridBecauseInitializedMatrixIsEmpty() {
        Grid.newGrid("", new boolean[][] {});
        
        Assert.fail();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalGridBecauseOfIncludingWrongSizeRows() {
        Grid.newGrid("", new boolean[][] {
            {false, false, false},
            {false, false},
        });
        
        Assert.fail();
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalGridBecauseWidthIndexIsZero() {
        Grid.newGrid("", new boolean[1][0]);
        
        Assert.fail();
    }
    
    
    @Test
    public void heightAndWidth() {
        final class TestData {
            
            private final int expectedHeight;
            
            private final int expectedWidth;
            
            private final Grid target;
            
            
            private TestData(final int expectedHeight, final int expectedWidth, final Grid target) {
                this.expectedHeight = expectedHeight;
                this.expectedWidth = expectedWidth;
                this.target = target;
            }
            
        };
        final List<TestData> testDataList = Arrays.asList(new TestData[] {
            new TestData(1, 2, Grid.newGrid("", new boolean[] {false, false})),
            new TestData(1, 1, Grid.newGrid("", new boolean[][] {{false}})),
            new TestData(1, 2, Grid.newGrid("", new boolean[][] {{false, false}})),
            new TestData(2, 1, Grid.newGrid("", new boolean[][] {{false}, {false}})),
            new TestData(1, 1, Grid.newGrid("", new boolean[1])),
            new TestData(1, 1, Grid.newGrid("", new boolean[1][1])),
            new TestData(1, 2, Grid.newGrid("", new boolean[1][2])),
            new TestData(2, 1, Grid.newGrid("", new boolean[2][1])),
            new TestData(2, 2, Grid.newGrid("", new boolean[][] {{false, false}, {false, false}}))
        });
        
        for (final TestData testData : testDataList) {
            this.testHeight(testData.expectedHeight, testData.target);
            this.testWidth(testData.expectedWidth, testData.target);
        }
    }
    
    
    private void testHeight(final int expectedHeight, final Grid target) {
        Assert.assertEquals(expectedHeight, target.height());
    }
    
    
    private void testWidth(final int expectedWidth, final Grid target) {
        Assert.assertEquals(expectedWidth, target.width());
    }
    
}
