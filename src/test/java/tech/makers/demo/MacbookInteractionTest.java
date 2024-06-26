package tech.makers.demo;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.makers.demo.levelManagement.levels.LevelThree.interactions.MacbookInteraction;


import static org.mockito.Mockito.*;

public class MacbookInteractionTest {

    private MacbookInteraction macbookInteraction;

    @BeforeEach
    public void setUp() {
        macbookInteraction = new MacbookInteraction(100, 100, "sprites/Router.png");
    }

    @Test
    public void testInteract_FirstTime() {
        Alert mockAlert = Mockito.mock(Alert.class);
        doNothing().when(mockAlert).showAndWait();

        macbookInteraction.interact();
        assert (macbookInteraction.hasMacbook());
        verify(mockAlert, times(1)).showAndWait();
    }

    @Test
    public void testInteract_SecondTime() {
        Alert mockAlert = Mockito.mock(Alert.class);
        doNothing().when(mockAlert).showAndWait(); // Mock alert behavior

        macbookInteraction.interact();
        verify(mockAlert, times(2)).showAndWait();
    }

    @Test
    public void testHasMacbook() {
        assert (!macbookInteraction.hasMacbook());
        macbookInteraction.interact();
        assert (macbookInteraction.hasMacbook());
    }
}

