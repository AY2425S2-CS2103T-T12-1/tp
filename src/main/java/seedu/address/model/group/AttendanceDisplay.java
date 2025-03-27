package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import javafx.scene.layout.Region;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.ui.AttendanceCard;
import seedu.address.ui.Result;
import seedu.address.ui.UiPart;

/**
 * Represents a view of attendance data for display in the UI.
 */
public class AttendanceDisplay implements Result {
    private final Person person;
    private final Group group;
    private final boolean[] attendance;

    /**
     * Creates an AttendanceDisplay with the given person, group, and attendance data.
     *
     * @param person The person whose attendance is being displayed.
     * @param group The group in which the attendance is being tracked.
     * @param attendance The attendance data for the person in the group.
     */
    public AttendanceDisplay(Person person, Group group, boolean[] attendance) {
        requireNonNull(person);
        requireNonNull(group);
        requireNonNull(attendance);
        this.person = person;
        this.group = group;
        this.attendance = attendance;
    }

    /**
     * Gets the person associated with this attendance display.
     *
     * @return The person.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Gets the group associated with this attendance display.
     *
     * @return The group.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Gets the attendance data.
     *
     * @return The attendance array.
     */
    public boolean[] getAttendance() {
        return attendance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", person.getName())
                .add("group", group.getGroupName())
                .toString();
    }

    @Override
    public UiPart<Region> createCard(int displayedIndex) {
        return new AttendanceCard(this, displayedIndex);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceDisplay otherAttendance)) {
            return false;
        }

        return person.equals(otherAttendance.person)
                && group.equals(otherAttendance.group);
    }
}
