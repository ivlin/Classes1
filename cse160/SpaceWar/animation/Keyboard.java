package animation;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


/**
 * Keyboard module.  Tracks keypresses and forwards control
 * information to liveobject that take their control from the keyboard.
 */

public class Keyboard implements KeyListener {

    /** Hashtable mapping key codes to Vectors of handlers. */
    private HashMap<Integer,List<KeyHandler>> keyHandlers =
        new HashMap<Integer,List<KeyHandler>>();
    
    /** Hashtable mapping key codes to key state (pressed/released). */
    private HashMap<Integer,Boolean> keyStates = new HashMap<Integer,Boolean>();

    /**
     * Register a key handler with the keyboard module.
     *
     * @param keyCode The code of the key to be handled.
     * @param handler The handler object to be informed about key activity.
     */
    public void addHandler(int keyCode, KeyHandler handler) {
        Integer key = new Integer(keyCode);
        List<KeyHandler> v = keyHandlers.get(key);
        if(v == null) {
            v = new ArrayList<KeyHandler>();
            keyHandlers.put(key, v);
        }
        v.add(handler);
    }

    /**
     * Remove a key handler that was previously registered.
     *
     * @param keyCode The code of the key to be handled.
     * @param handler The handler object to be informed about key activity.
     */
    public void removeHandler(int keyCode, KeyHandler handler) {
        Integer key = new Integer(keyCode);
        List<KeyHandler> v = keyHandlers.get(key);
        if(v != null)
            v.remove(handler);
    }

    /**
     * Handle a keyTyped event.
     * These are currently ignored.
     *
     * @param KeyEvent Object containing information about the
     * key that was pressed.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handle a keyPressed event.
     * Looks for handlers for the key that was pressed, and
     * invokes their keyPressed handler.
     *
     * @param KeyEvent Object containing information about the
     * key that was pressed.
     */
    public void keyPressed(KeyEvent e) {
        Integer key = new Integer(e.getKeyCode());
        List<KeyHandler> v = keyHandlers.get(key);
        if(v != null) {
            Boolean b = keyStates.get(key);
            keyStates.put(key, true);
            // Workaround for inconsistency between Windows/X11 behavior.
            // On Windows, holding a key down generates multiple keyPressed
            // events without intervening keyReleased events.
            // On X11 this doesn't happen.
            // Deal with this situation by generating a virtual keyReleased
            // event in case we get a keyPressed event for a key that is
            // already pressed.
            if(b != null  && b) {
                for(KeyHandler h : v) {
                    h.keyReleased(e.getKeyCode());
                }
            }
            for(KeyHandler h : v) {
                h.keyPressed(e.getKeyCode());
            }
        }
    }

    /**
     * Handle a keyReleased event.
     * Looks for handlers for the key that was released, and
     * invokes their keyReleased handler.
     *
     * @param KeyEvent Object containing information about the
     * key that was released.
     */
    public void keyReleased(KeyEvent e) {
        Integer key = new Integer(e.getKeyCode());
        List<KeyHandler> v = keyHandlers.get(key);
        if(v != null) {
            Boolean b = keyStates.get(key);
            keyStates.put(key, false);
            // Suppress the event if the key is already released.
            if(b != null && b) {
                for(KeyHandler h: v) {
                    h.keyReleased(e.getKeyCode());
                }
            }
        }
    }
}
