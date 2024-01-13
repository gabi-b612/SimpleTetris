package tryit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThreePanelsExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Three Panels Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel1 = createPanel(Color.RED);
            JPanel panel2 = createPanel(Color.GREEN);
            JPanel panel3 = createPanel(Color.BLUE);

            frame.setLayout(new GridLayout(1, 3));
            frame.add(panel1);
            frame.add(panel2);
            frame.add(panel3);

            frame.setVisible(true);
        });
    }

    private static JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);

        // Ajouter un écouteur de souris pour gérer le focus lors du clic sur le panneau
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });

        // Activer le focus pour les composants du panneau
        panel.setFocusable(true);
        return panel;
    }
}
