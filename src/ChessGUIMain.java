import ChessGUI.ChessFrame;

import javax.swing.SwingUtilities;

public class ChessGUIMain {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            ChessFrame frame = new ChessFrame();
            frame.setVisible(true);
        });
    }
}
