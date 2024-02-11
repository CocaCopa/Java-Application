import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoPanel {

    private static final int textFieldPaddingLEFT = 5;
    private static final int textFieldPaddingTOP = 5;
    private static final int textFieldPaddingRIGHT = 5;
    private static final int textFieldPaddingBOTTOM = 5;
    private static final int textFieldColumns = 10;

    private static final int infoContainerScrollSpeed = 15;

    private static final int taskInfoWidth = 300;
    private static final int taskInfoHeight = 35;
    
    private static final String newTaskButtonName = "Create Task";
    
    private static JPanel infoContainer;
    
    /**
     * Creates the info panel of the application.
     * @return JPanel representing the info panel.
     */
    public static JPanel createInfoPanel() {
        JPanel infoPanel = ContainerCreator.createBorderContainer(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(taskCreationPanel(), BorderLayout.PAGE_START);
        infoPanel.add(createInfoContainer(), BorderLayout.CENTER);
        return infoPanel;
    }

    /**
     * Creates the container that holds the information of the task.
     * @return The created container for the information of the tasks.
     */
    private static JScrollPane createInfoContainer() {
        infoContainer = ContainerCreator.createBoxContainer(BoxLayout.Y_AXIS, null);
        // Allows the tasks panel to overflow if needed.
        JScrollPane infoScrollPane = new JScrollPane(infoContainer);
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.getVerticalScrollBar().setUnitIncrement(infoContainerScrollSpeed);

        return infoScrollPane;
    }

    /**
     * Creates the text field and the button necessary for the creation of a new task.
     * @return The task creation panel.
     */
    private static JPanel taskCreationPanel() {
        JPanel containerPanel = ContainerCreator.createFlowContainer(FlowLayout.CENTER, null);
        JTextField textField = new JTextField(textFieldColumns);
        textField.setBorder(new EmptyBorder(textFieldPaddingTOP, textFieldPaddingLEFT, textFieldPaddingBOTTOM, textFieldPaddingRIGHT));
        JButton submitButton = new JButton(newTaskButtonName);
        submitButton.addActionListener(e -> TaskPanel.createNewTask(textField.getText(), false));
        containerPanel.add(textField);
        containerPanel.add(submitButton);

        return containerPanel;
    }

    public static void populateInfoContainer(String taskName) {
        String[] datesInfo = LogSaver.getTaskInfo(LogSaver.Task.Dates, taskName);
        String[] timesInfo = LogSaver.getTaskInfo(LogSaver.Task.Times, taskName);

        for (int i=0; i<datesInfo.length; i++) {
            JPanel container = ContainerCreator.createBorderContainer(taskInfoWidth, taskInfoHeight, null);
            JLabel date = new JLabel(datesInfo[i]);
            JLabel time = new JLabel(timesInfo[i]);
            container.add(date, BorderLayout.WEST);
            container.add(time, BorderLayout.EAST);
            infoContainer.add(container);
        }
        infoContainer.revalidate();
        infoContainer.repaint();
    }

    public static void clearInfoContainer() {
        Component[] containerComponents = infoContainer.getComponents();
        for (Component component : containerComponents) {
            infoContainer.remove(component);
        }
        infoContainer.revalidate();
        infoContainer.repaint();
    }
}
