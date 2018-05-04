package animation;
/**
 * Key handler interface.  This is what the Keyboard module needs to know
 * about liveobject that receive their control from the keyboard.
 */

public interface KeyHandler {
    /**
     * Method called when a key is pressed.
     *
     * @param keyCode The code of the key that was pressed.
     */
    void keyPressed(int keyCode);

    /**
     * Method called when a key is released.
     *
     * @param keyCode The code of the key that was released.
     */
    void keyReleased(int keyCode);
}

