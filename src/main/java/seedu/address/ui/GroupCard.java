package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";
    private static final int MAX_MEMBERS_TO_DISPLAY = 3;
    private static final String MORE_MEMBERS_LABEL = "...and %d more";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Group group;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane members;
    @FXML
    private Label memberCount;

    /**
     * Creates a {@code GroupCode} with the given {@code Group} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        name.setText(group.getGroupName());

        // Display tags
        group.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Display first 3 members
        displayTruncatedMembers();
    }


    /**
     * Displays the first MAX_MEMBERS_TO_DISPLAY members of the group,
     * and indicates how many more members there are if the group has more members.
     */
    private void displayTruncatedMembers() {
        ArrayList<Person> groupMembers = group.getGroupMembers();
        int totalMembers = groupMembers.size();

        // Display count of members
        memberCount.setText(totalMembers + " members");

        // If there are no members, return early
        if (totalMembers == 0) {
            return;
        }

        // Determine how many members to display
        int membersToShow = Math.min(totalMembers, MAX_MEMBERS_TO_DISPLAY);

        // Add the first few members
        for (int i = 0; i < membersToShow; i++) {
            Person person = groupMembers.get(i);
            Label memberLabel = new Label(person.getName().fullName);
            memberLabel.getStyleClass().add("member-label");
            members.getChildren().add(memberLabel);
        }

        // Add an indicator if there are more members than shown
        if (totalMembers > MAX_MEMBERS_TO_DISPLAY) {
            Label moreLabel = new Label(String.format(MORE_MEMBERS_LABEL, totalMembers - MAX_MEMBERS_TO_DISPLAY));
            moreLabel.getStyleClass().add("more-members-label");
            members.getChildren().add(moreLabel);
        }
    }
}
