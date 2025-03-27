package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.GroupMemberDetail;

public class GroupDetailCard extends UiPart<Region> {
    private static final String FXML = "GroupDetailListCard.fxml";
    private static final int MAX_MEMBERS_TO_DISPLAY = 3;
    private static final String MORE_MEMBERS_LABEL = "...and %d more";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final GroupMemberDetail detail;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private Label attendance;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public GroupDetailCard(GroupMemberDetail detail, int displayedIndex) {
        super(FXML);

        this.detail = detail;
        id.setText("Member #" + displayedIndex + " of " + detail.getGroup().size());
        name.setText("Name: " + detail.getPerson().getName().fullName);
        role.setText("Role: " + detail.getRole().toString());

        StringBuilder attendanceSb = new StringBuilder();
        attendanceSb.append("Attendance: ");
        var detailAttendance = detail.getAttendance();
        boolean hasAttended = false;
        for (int i = 0; i < detailAttendance.length; ++i) {
            if (detailAttendance[i]) {
                attendanceSb.append("W").append(i + 1).append(" ");
                hasAttended = true;
            }
        }
        if (!hasAttended) {
            attendanceSb.append("None");
        }
        attendance.setText(attendanceSb.toString());

        // TODO: show assignment scores
    }
}
