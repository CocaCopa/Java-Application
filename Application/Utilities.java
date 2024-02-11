import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Utilities {

    private static final String dateFormat = "dd-MM-yyyy";
    private static final String timeFormat = "HH:mm:ss";

    /**
     * Displays an information window popup.
     * @param windowTitle The title of the window.
     * @param message The message to be displayed.
     */
    public static void infoPopup(String windowTitle, String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            windowTitle,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Displays a warning window popup.
     * @param windowTitle The title of the window.
     * @param message The message to be displayed.
     */
    public static void warningPopup(String windowTitle, String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            windowTitle,
            JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Displays an error window popup.
     * @param windowTitle The title of the window.
     * @param message The message to be displayed.
     */
    public static void errorPopup(String windowTitle, String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            windowTitle,
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Checks if the provided string is empty.
     * @param string The string to be checked.
     * @return True if the string is empty, otherwise false.
     */
    public static boolean isStringEmpty(String string) {
        String trimString = string.trim();
        return trimString.isEmpty();
    }

    /**
     * Retrieves the current date.
     * @return The current date in the format dd-MM-yyyy.
     */
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return currentDate.format(formatter);
    }

    /**
     * Retrieves the current time.
     * @return The current time in the format HH:mm:ss.
     */
    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        return currentTime.format(formatter);
    }
}
