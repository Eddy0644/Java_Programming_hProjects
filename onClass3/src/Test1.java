import javax.swing.*;
import java.awt.event.*;

public class Test1 {
    public static void main(String[] args) {
//        new MouseDemo();
        new MultiDemo();
    }
}

class MouseDemo {
    private JFrame win;
    private JLabel btn;

    public MouseDemo() {
        win = new JFrame("MouseDemo");
        win.setLayout(null);
        win.setSize(400, 400);
        ImageIcon icon = new ImageIcon("asset/1.png");
        btn = new JLabel(icon);
        btn.setBounds(150, 150, 200, 192);
        win.add(btn);
//        win.addMouseListener(new MouseHandler(btn));
        win.addMouseMotionListener(new MotionHandler(btn));
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MouseHandler implements MouseListener {
    private JLabel btn;

    public MouseHandler(JLabel incomingBtn) {
        btn = incomingBtn;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        btn.setLocation(x, y);

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

class MotionHandler implements MouseMotionListener {
    private JLabel btn;

    public MotionHandler(JLabel incomingBtn) {
        btn = incomingBtn;
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        btn.setLocation(e.getX() - 10, e.getY() - 10);
    }
}

class MultiDemo implements MouseListener, MouseMotionListener, KeyListener {
    private JLabel targetBtn;
    private JFrame win;
    private int[] cur;

    public MultiDemo() {
        win = new JFrame("MultiDemo");
        win.setLayout(null);
        win.setSize(400, 400);
        ImageIcon icon = new ImageIcon("asset/1.png");
        targetBtn = new JLabel(icon);
        targetBtn.setBounds(150, 150, 200, 192);
        cur = new int[]{150, 150};
        win.add(targetBtn);
//        win.addMouseListener(new MouseHandler(btn));
        win.addMouseMotionListener(this);
        win.addKeyListener(this);
        win.setVisible(true);
        win.setFocusable(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateBtn() {
        targetBtn.setLocation(cur[0], cur[1]);
    }

    private void updateBtn(int[] incomingCur) {
        cur = incomingCur;
        updateBtn();
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.printf("%c %d\n", e.getKeyChar(), e.getKeyCode());

        return;
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyCode());
        System.out.printf("%c-%d\n", e.getKeyChar(), e.getKeyCode());
        switch (e.getKeyChar()) {
            case 'w' -> cur[1] -= 10;
            case 'a' -> cur[0] -= 10;
            case 's' -> cur[1] += 10;
            case 'd' -> cur[0] += 10;
        }
        switch (e.getKeyCode()) {
            case 38 -> cur[1] -= 10;
            case 37 -> cur[0] -= 10;
            case 40 -> cur[1] += 10;
            case 39 -> cur[0] += 10;
        }
        updateBtn();
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int[] now = new int[]{e.getX(), e.getY()};
        updateBtn(now);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}