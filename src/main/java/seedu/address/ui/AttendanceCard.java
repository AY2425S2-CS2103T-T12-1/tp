package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.AttendanceDisplay;

/**
 * An UI component that displays information of an {@code AttendanceDisplay}.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final AttendanceDisplay attendanceDisplay;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label personName;
    @FXML
    private Label groupName;
    @FXML
    private Label attendanceTitle;
    @FXML
    private FlowPane weeks;

    /**
     * Creates a {@code AttendanceCard} with the given {@code AttendanceDisplay} and index to display.
     */
    public AttendanceCard(AttendanceDisplay attendanceDisplay, int displayedIndex) {
        super(FXML);
        this.attendanceDisplay = attendanceDisplay;
        id.setText(displayedIndex + ". ");
        personName.setText(attendanceDisplay.getPerson().getName().fullName);
        groupName.setText("Group: " + attendanceDisplay.getGroup().getGroupName());
        attendanceTitle.setText("Attendance Record");

        populateWeeks(attendanceDisplay.getAttendance());
    }

    /**
     * Populates the weeks FlowPane with attendance information.
     *
     * @param attendance The attendance data to display.
     */
    private void populateWeeks(boolean[] attendance) {
        int presentCount = 0;

        for (int i = 0; i < attendance.length; i++) {
            if (attendance[i]) {
                presentCount++;
            }

            Label weekLabel = new Label("Week " + (i + 1) + ": " + (attendance[i] ? "Present" : "Absent"));
            weekLabel.getStyleClass().add(attendance[i] ? "attendance-present" : "attendance-absent");
            weeks.getChildren().add(weekLabel);
        }

        // Add a summary label
        Label summaryLabel = new Label(String.format("Total: %d/%d weeks attended", presentCount, attendance.length));
        summaryLabel.getStyleClass().add("attendance-summary");
        weeks.getChildren().add(summaryLabel);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceCard)) {
            return false;
        }

        // state check
        AttendanceCard card = (AttendanceCard) other;
        return id.getText().equals(card.id.getText())
                && attendanceDisplay.equals(card.attendanceDisplay);
    }
}
