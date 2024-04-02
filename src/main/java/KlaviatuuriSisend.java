import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KlaviatuuriSisend implements KeyListener {
    private StringBuilder klaviatuuriSisend;
    private int vertikaalLiikumine;

    public KlaviatuuriSisend() {
        klaviatuuriSisend = new StringBuilder();
        vertikaalLiikumine = 0;
    }

    @Override
    public void keyPressed(KeyEvent vajutus) {
        System.out.println("Klahv vajutatud!");
        int kood = vajutus.getKeyCode();
        switch (kood) {
            case KeyEvent.VK_UP -> vertikaalLiikumine -= 1;
            case KeyEvent.VK_DOWN -> vertikaalLiikumine += 1;
            default -> klaviatuuriSisend.append(vajutus.getKeyChar());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getVertikaalLiikumine() {
        return vertikaalLiikumine;
    }
}
