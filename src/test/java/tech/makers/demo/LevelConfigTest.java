package tech.makers.demo;

import org.junit.jupiter.api.Test;
import tech.makers.demo.levelManagement.LevelConfig;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LevelConfigTest {

    @Test
    public void testLevel1Layout() {
        int[][] expectedLevel1Layout = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        assertArrayEquals(expectedLevel1Layout, LevelConfig.LEVEL_1_LAYOUT);
    }

    @Test
    public void testLevel2Layout() {
        int[][] expectedLevel2Layout = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        assertArrayEquals(expectedLevel2Layout, LevelConfig.LEVEL_2_LAYOUT);
    }
}

