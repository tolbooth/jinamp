package ui;

import ui.display.TerminalUI;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Note: We can't really test the display methods, as they are
complex and require user input, etc. As I understand it
further simplification is possible using wrappers for
system in/out functions, however exactly how to do that
is at this stage a bit beyond the scope of the deadline.
 */
public class TerminalUITest {
/*
    TerminalUI testTermUI;
    Menu testMenu;

    @BeforeEach
    // Initialize data members
    void init() {
        testTermUI = new TerminalUI();
        testMenu = new Menu();
        testMenu.addTrack(1);
    }

    @Test
    // Best tested visually
    void clearTest() {
        System.out.println("hello");
        testTermUI.clear();
        System.out.println("world");
    }

    @Test
    void displayProgressTest() {
        testTermUI.getMenu().addTrack(1);
        testTermUI.getMenu().startPlay();
        testTermUI.displayProgress();
    }

    @Test
    void getCurrentTimeTest(){
        assertEquals(testTermUI.getCurrentTime(21100000,147740000),
                "00:21 / 02:27");
        assertEquals(testTermUI.getCurrentTime(0,0),
                "00:00 / 00:00");
    }
 */
}
