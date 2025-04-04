package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.person.Person;

/**
 * A detail box for ui for displaying group details
 */
public class DetailBox extends UiPart<Region> {
    private static final String FXML = "DetailBox.fxml";

    @FXML
    private FlowPane tags;
    @FXML
    private Label name;
    @FXML
    private Label numStudent;
    @FXML
    private Label numTa;
    @FXML
    private Label numProf;
    @FXML
    private Label numAssignment;

    /**
     * Construct a DetailBox for UI with the given Group
     */
    public DetailBox(Group group) {
        super(FXML);
        update(group);
    }

    /**
     * Update group to be shown in the UI
     */
    public void update(Group group) {
        name.setText("Group Name: " + group.getGroupName());
        ArrayListMap<Person, GroupMemberDetail> groupMembers = group.getGroupMembersMap();
        int numStudents = 0;
        int numTAs = 0;
        int numLecturer = 0;
        for (GroupMemberDetail detail : groupMembers.values()) {
            switch (detail.getRole()) {
            case Student:
                numStudents++;
                break;
            case TeachingAssistant:
                numTAs++;
                break;
            case Lecturer:
                numLecturer++;
                break;
            default:
                break;
            }
        }
        numStudent.setText("No. Students: " + numStudents);
        numTa.setText("No. TAs: " + numTAs);
        numProf.setText("No. Professors: " + numLecturer);
        numAssignment.setText("No. Assignments: " + group.getAssignments().size());
        group.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
