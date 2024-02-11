import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskPanel {
    
    private static final String warningBoxTitle = "Invalid Name";
    private static final String warningMessage = "Task name should not be empty.";
    private static final String taskPanelTitle = "Task List";
    private static final String buttonLogText = "Log";
    private static final String buttonDeleteText = "Delete";
    private static final int tasksHeaderHeight = 25;
    private static final int taskHeight = 45;
    private static final int scrollwheelSpeed = 15;

    private static JPanel tasksPanel;

    /**
     * Creates the task panel of the application.
     * @return JScrollPane containing the task panel.
     */
    public static JScrollPane createTasksPanel() {
        tasksPanel = ContainerCreator.createBoxContainer(BoxLayout.Y_AXIS, null);
        drawHeaderDivision();

        // Allows the tasks panel to overflow if needed.
        JScrollPane tasksScrollPane = new JScrollPane(tasksPanel);
        tasksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tasksScrollPane.getVerticalScrollBar().setUnitIncrement(scrollwheelSpeed);

        return tasksScrollPane;
    }

    // Creates the header of the tasks panel.
    private static void drawHeaderDivision() {
        JPanel taskContainer = createTaskContainer(tasksHeaderHeight);
        JLabel label = new JLabel(taskPanelTitle);
        tasksPanel.add(taskContainer);
        taskContainer.add(label);
    }
    
    /**
     * Creates a new task and includes it in the task panel.
     * @param taskLabel The label of the task.
     * @param loadTasks Flag indicating whether to load tasks.
     */
    public static void createNewTask(String taskLabel, boolean loadTasks) {
        if (Utilities.isStringEmpty(taskLabel)) {
            Utilities.warningPopup(warningBoxTitle, warningMessage);
            return;
        }

        if (!loadTasks && LogSaver.searchTask(taskLabel)) {
            Utilities.errorPopup("Task Exists", "Task '" + taskLabel + "' already exists.");
        }
        else {
            JPanel taskContainer = createTaskContainer(taskHeight);
            addTaskLabel(taskContainer, taskLabel);
            addTaskButtons(taskContainer);
            tasksPanel.add(taskContainer);
            tasksPanel.revalidate();
        }
    }

    // Create a container that holds the task label and the 2 buttons.
    private static JPanel createTaskContainer(int containerHeight) {
        JPanel container = ContainerCreator.createBorderContainer(Integer.MAX_VALUE, containerHeight, null);
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InfoPanel.clearInfoContainer();
                String taskName = getLabelFromTaskContainer(container);
                InfoPanel.populateInfoContainer(taskName);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                container.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                container.setCursor(Cursor.getDefaultCursor());
            }
        });
        return container;
    }

    // Adds the task label to the task container.
    private static void addTaskLabel(JPanel taskContainer, String taskLabel) {
        JLabel label = new JLabel(taskLabel);
        taskContainer.add(label, BorderLayout.WEST);
    }

    // Adds 2 buttons to the task container.
    private static void addTaskButtons(JPanel taskContainer) {
        JPanel buttonPanel = ContainerCreator.createBoxContainer(BoxLayout.X_AXIS, null);
        JButton buttonMore = new JButton(buttonLogText);
        JButton buttonDelete = new JButton(buttonDeleteText);
        buttonMore.addActionListener(e -> logTargetTask(buttonMore));
        buttonDelete.addActionListener(e -> deleteTargetTask(buttonPanel, buttonDelete));
        buttonPanel.add(buttonMore);
        buttonPanel.add(buttonDelete);
        taskContainer.add(buttonPanel, BorderLayout.EAST);
    }

    // Deletes the provided task from the tasks panel and from the json file.
    private static void deleteTargetTask(JPanel bp, JButton button) {
        JPanel parentPanel = (JPanel) button.getParent().getParent();
        
        tasksPanel.remove(parentPanel);
        tasksPanel.revalidate();
        tasksPanel.repaint();

        String taskName = getLabelFromTaskContainer(parentPanel);
        LogSaver.deleteTask(taskName);
    }

    // Saves the task associated with the provided button the json file.
    private static void logTargetTask(JButton button) {
        String taskName = getLabelFromTaskContainer((JPanel) button.getParent().getParent());
        LogSaver.logTask(taskName);
    }

    // Gets the name of the task associated to the provided container.
    private static String getLabelFromTaskContainer(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                return label.getText();
            }
        }
        return null;
    }
}
