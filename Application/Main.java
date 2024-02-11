import java.io.*;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class Main {

    private static final String applicationTitle = "Log Tracker";
    private static final float taskPanelWidth = 0.4f;
    private static final float infoPanelWidth = 0.6f;

    public static void main(String[] args) {
        createApplication();
        loadExistingTasks();
    }

    /**
     * Creates the window application.
     */
    private static void createApplication() {
        JFrame frame = createApplicationFrame();
        JPanel mainPanel = createMainPanel();
        JScrollPane tasksScrollPane = TaskPanel.createTasksPanel();
        JPanel infoPanel = InfoPanel.createInfoPanel();
        addComponentsToMainPanel(mainPanel, tasksScrollPane, infoPanel);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Creates the main application frame.
     * @return The created JFrame.
     */
    private static JFrame createApplicationFrame() {
        JFrame frame = new JFrame(applicationTitle);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    /**
     * Creates the parent panel.
     * @return The created JPanel.
     */
    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        return mainPanel;
    }

    /**
     * Adds the sub panels to the main panel.
     * @param mainPanel The main panel to which sub panels are added.
     * @param tasksScrollPane The tasks scroll pane.
     * @param infoPanel The info panel.
     */
    private static void addComponentsToMainPanel(JPanel mainPanel, Component tasksScrollPane, JPanel infoPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = taskPanelWidth;
        gbc.weighty = 1.0; // 100% height
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tasksScrollPane, gbc);

        gbc.gridx = 1;
        gbc.weightx = infoPanelWidth;
        mainPanel.add(infoPanel, gbc);
    }

    /**
     * Loads the saved tasks from the json file (if it exists) and creates them on the tasks panel.
     */
    private static void loadExistingTasks() {
        File jsonFile = new File(LogSaver.fileName);
        if (jsonFile.exists()) {
            String[] tasks = LogSaver.getTasks();
            Arrays.sort(tasks);
            for (var task : tasks) {
                TaskPanel.createNewTask(task, true);
            }
        }
    }
}
