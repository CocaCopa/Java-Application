import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.*;

public class ContainerCreator {

    public static JPanel createBoxContainer(int width, int height, int boxLayout, Color borderColor) {
        JPanel container = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(width, height);
            }
        };
        container.setLayout(new BoxLayout(container, boxLayout));
        if (borderColor != null) {
            container.setBorder(BorderFactory.createLineBorder(borderColor));
        }

        return container;
    }

    public static JPanel createBoxContainer(int boxLayout, Color borderColor) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, boxLayout));
        if (borderColor != null) {
            container.setBorder(BorderFactory.createLineBorder(borderColor));
        }

        return container;
    }

    public static JPanel createFlowContainer(int width, int height, int flowLayout, Color borderColor) {
        JPanel container = new JPanel(new FlowLayout(flowLayout)) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(width, height);
            }
        };
        if (borderColor != null) {
            container.setBorder(BorderFactory.createLineBorder(borderColor));
        }

        return container;
    }

    public static JPanel createFlowContainer(int flowLayout, Color borderColor) {
        JPanel container = new JPanel(new FlowLayout(flowLayout));
        if (borderColor != null) {
            container.setBorder(BorderFactory.createLineBorder(borderColor));
        }

        return container;
    }

    public static JPanel createBorderContainer(int width, int height, Color borderColor) {
        JPanel container = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(width, height);
            }
        };
        container.setPreferredSize(new Dimension(container.getWidth(), height));
        if (borderColor != null) {
            container.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
        
        return container;
    }
}
