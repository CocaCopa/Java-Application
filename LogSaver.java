import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogSaver {

    public static final String fileName = "output.json";
    private static final String str_dateKey = "date";
    private static final String str_timeKey = "time";

    private static JSONObject tasksObject;
    
    public enum Task {
        Dates,
        Times
    };

    /**
     * Saves a task to the json file.
     * @param taskName The name of the task.
     */
    public static void logTask(String taskName) {
        File jsonFile = new File(fileName);
        if (jsonFile.exists()) {
            tasksObject = readLogFile();
            if (tasksObject.has(taskName)) {
                updateExistingTask(taskName);
            }
            else {
                addNewTask(taskName);
            }
        }
        else {
            tasksObject = new JSONObject();
            addNewTask(taskName);
        }
        writeToJSONFile();
    }

    /**
     * Retrieves the task keys saved in the json file.
     * @return An array containing task names, or null if an error occurs.
     */
    public static String[] getTasks() {
        try {
            tasksObject = readLogFile();
            return JSONObject.getNames(tasksObject);
        }
        catch (JSONException e) {
            Utilities.errorPopup("JSONException", e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves information about the provided task.
     * @return A string array where index 0 represents the date and index 1 represents the time.
     */
    public static String[] getTaskInfo(Task info, String taskName) {
        try {
            File jsonFile = new File(fileName);
            String[] taskInfo = null;
            if (jsonFile.exists()) {
                tasksObject = readLogFile();
                String[] tasks = JSONObject.getNames(tasksObject);
                for (String task : tasks) {
                    if (taskName.equals(task)) {
                        JSONArray taskArray = tasksObject.getJSONArray(taskName);
                        JSONObject innerObject = taskArray.getJSONObject(0);
                        
                        if (info == Task.Dates) {
                            JSONArray datesArray = innerObject.getJSONArray(str_dateKey);
                            taskInfo = splitTaskInfo(datesArray);
                        }
                        else if (info == Task.Times) {
                            JSONArray timesArray = innerObject.getJSONArray(str_timeKey);
                            taskInfo = splitTaskInfo(timesArray);
                        }
                    }
                }
            }
            return taskInfo;
        }
        catch (NullPointerException | JSONException e) {
            Utilities.errorPopup("NullPointerException | JSONException", e.getMessage());
            return null;
        }
    }

    private static String[] splitTaskInfo(JSONArray keyArray) {
        String timesString = keyArray.toString();
        timesString = timesString.replace('"', ' ').replace(" ", "");
        timesString = timesString.replace("[", "").replace("]", "");
        return timesString.split(",");
    }
    
    /**
     * Searches for a task in the json file.
     * @param taskName The name of the task to search for.
     * @return True if the task was found, otherwise false.
     */
    public static boolean searchTask(String taskName) {
        try {
            File jsonFile = new File(fileName);
            if (jsonFile.exists()) {
                tasksObject = readLogFile();
                String[] tasks = JSONObject.getNames(tasksObject);
                for (String task : tasks) {
                    if (taskName.equals(task)) {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (NullPointerException | JSONException e) {
            Utilities.errorPopup("NullPointerException | JSONException", e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a task from the json file.
     * @param taskName The name of the task to delete.
     */
    public static void deleteTask(String taskName) {
        try {
            tasksObject = readLogFile();
            tasksObject.remove(taskName);
            writeToJSONFile();
            InfoPanel.clearInfoContainer();
        }
        catch (JSONException e) {
            Utilities.errorPopup("JSONException", e.getMessage());
        }
    }

    /**
     * Reads the json file and returns its contents as a JSONObject.
     * @return The contents of the json file as a JSONObject.
     */
    private static JSONObject readLogFile() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            return new JSONObject(jsonContent);
        }
        catch (IOException e) {
            Utilities.errorPopup("IOException", e.getMessage());
            return null;
        }
    }

    /**
     * Updates the date and time of an existing task in the json file.
     * @param taskName The name of the task to update.
     */
    private static void updateExistingTask(String taskName) {
        try {
            JSONArray taskArray = tasksObject.getJSONArray(taskName);
            JSONObject innerObject = taskArray.getJSONObject(0);
            JSONArray dateArray = innerObject.getJSONArray(str_dateKey);
            JSONArray timeArray = innerObject.getJSONArray(str_timeKey);
            dateArray.put(Utilities.getCurrentDate());
            timeArray.put(Utilities.getCurrentTime());
        }
        catch (JSONException e) {
            Utilities.errorPopup("JSONException", e.getMessage());
        }
    }

    /**
     * Adds a new task to the json file.
     * @param taskName The name of the new task.
     */
    private static void addNewTask(String taskName) {
        JSONObject newTask = createTask(Utilities.getCurrentDate(), Utilities.getCurrentTime());
        tasksObject.put(taskName, new JSONArray().put(newTask));
    }

    /**
     * Creates a new task object with the given date and time.
     * @param date The date of the task.
     * @param time The time of the task.
     * @return The created task object.
     */
    private static JSONObject createTask(String date, String time) {
        JSONObject task = new JSONObject();
        task.put(str_dateKey, new JSONArray().put(date));
        task.put(str_timeKey, new JSONArray().put(time));
        return task;
    }

    /**
     * Writes the contents of the tasksObject to the json file.
     */
    private static void writeToJSONFile() {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(tasksObject.toString(4)); // Indent with 4 spaces for better readability
        } catch (IOException e) {
            Utilities.errorPopup("IOException", e.getMessage());
        }
    }
}
