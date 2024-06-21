package tech.makers.demo;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levels.LevelManager;
import tech.makers.demo.levels.Puzzle;
import tech.makers.demo.player.Player;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DoorTest extends ApplicationTest {

    @Mock
    private Puzzle mockPuzzle;

    @Mock
    private LevelManager mockLevelManager;

    @Mock
    private Player mockPlayer;

    private Door door;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        door = new Door(100, 200, "/sprites/door.png"); // Inject mock image
    }


    @Test
    public void testRender_drawsImageCorrectly() {
        GraphicsContext mockGraphicsContext = mock(GraphicsContext.class);
        door.render(mockGraphicsContext);
        verify(mockGraphicsContext).drawImage(door.doorImage, 100, 200, 96, 144);
    }

    @Mock
    private Alert alertMock; // Mock the Alert class (or Dialog if applicable)

    @Test
    public void testShowLockedMessage_createsAlert() {
        Door door = new Door(100, 100, "/sprites/door.png"); // Create a Door object

        // Simulate a locked door and inRange player
        door.locked = true;

        // Mock the Alert class
        when(alertMock.showAndWait()).thenReturn(Optional.empty());

        // Stub the Alert mock to return expected values (after verification)
        when(alertMock.getTitle()).thenReturn("Door Locked");
        when(alertMock.getContentText()).thenReturn("The door is locked until the puzzle is completed.");

        // Call the showLockedMessage method
        door.showLockedMessage();
    }

    @Test
    public void testInteract_locked_inRange_showsMessage() {
        when(mockPlayer.getX()).thenReturn(150.0);
        when(mockPlayer.getY()).thenReturn(250.0);
        door.checkPlayerInRange(mockPlayer);
        door.interact(mockPuzzle, mockLevelManager);
        // Focus on verifying no LevelManager interaction
        verifyNoInteractions(mockLevelManager);

        // Verify player interaction for checking range
        verify(mockPlayer).getX();
        verify(mockPlayer).getY();
    }




    @Test
    public void testInteract_unlocked_inRange_completesLevel() {
        when(mockPuzzle.isSolved()).thenReturn(true);
        when(mockPlayer.getX()).thenReturn(150.0);
        when(mockPlayer.getY()).thenReturn(250.0);
        door.checkPlayerInRange(mockPlayer);
        door.checkUnlock(mockPuzzle);
        door.interact(mockPuzzle, mockLevelManager);
        verify(mockPuzzle, times(1)).isSolved(); // Verify puzzle is checked
        verify(mockLevelManager).completeLevel(); // Level manager called to complete
        verify(mockPlayer).getX(); // Player X checked for range
        verify(mockPlayer).getY(); // Player Y checked for range
    }

    @Test
    public void testCheckUnlock_solvedPuzzle_unlocksDoor() {
        when(mockPuzzle.isSolved()).thenReturn(true);
        door.checkUnlock(mockPuzzle);
        assertFalse(door.isLocked());
    }

    @Test
    public void testCheckUnlock_unsolvedPuzzle_staysLocked() {
        when(mockPuzzle.isSolved()).thenReturn(false);
        door.checkUnlock(mockPuzzle);
        assertTrue(door.isLocked());
    }

    @Test
    public void testCheckPlayerInRange_inRange_setsInRange() {
        when(mockPlayer.getX()).thenReturn(150.0);
        when(mockPlayer.getY()).thenReturn(250.0);
        door.checkPlayerInRange(mockPlayer);
        assertTrue(door.isInRange());
    }

    @Test
    public void testCheckPlayerInRange_outOfRange_notInRange() {
        when(mockPlayer.getX()).thenReturn(300.0);
        when(mockPlayer.getY()).thenReturn(400.0);
        door.checkPlayerInRange(mockPlayer);
        assertFalse(door.isInRange());
    }

    @Test
    public void testIntersects_inside_returnsTrue() {
        assertTrue(door.intersects(120, 230)); // Player position inside door hitbox
    }

    @Test
    public void testIntersects_outsideTop_returnsFalse() {
        assertFalse(door.intersects(120, 1)); // Player position outside door hitbox
    }


    @Test
    public void testIntersects_outsideLeft_returnsFalse() {
        assertFalse(door.intersects(1, 1)); // Player position outside left of door hitbox
    }

    @Test
    public void testIntersects_outsideRight_returnsFalse() {
        assertFalse(door.intersects(350, 350)); // Player position outside right of door hitbox
    }

    @Test
    public void testIntersects_outsideBottom_returnsFalse() {
        assertFalse(door.intersects(120, 350)); // Player position outside bottom of door hitbox
    }

    @Test
    public void testIsLocked_returnsTrueByDefault() {
        assertTrue(door.isLocked()); // Door starts locked
    }

    @Test
    public void testIsLocked_afterUnlock_returnsFalse() {
        when(mockPuzzle.isSolved()).thenReturn(true);
        door.checkUnlock(mockPuzzle);
        assertFalse(door.isLocked()); // Door unlocked after check
    }

    @Test
    public void testIsInRange_returnsFalseByDefault() {
        assertFalse(door.isInRange()); // Door not in range by default
    }

}



