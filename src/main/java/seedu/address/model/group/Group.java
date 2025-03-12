package seedu.address.model.group;

import java.util.ArrayList;

import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group in the address book.
 * A {@code Group} is an ordered list of members, ordered by order of insertion.
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public String groupName;
    private ArrayList<Person> groupMembers;

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid tag name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.groupMembers = new ArrayList<>();
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherTag = (Group) other;
        return groupName.equals(otherTag.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Adds a person to the group
     * 
     * @param p A valid Person object.
     */
    public boolean addMember(Person p) {
        try {
            this.groupMembers.add(p);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public Person get(int i) {
        return this.groupMembers.get(i);
    }

    /**
     * Returns the size of the group
     */
    public int size() {
        return this.groupMembers.size();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + this.groupName + ']';
    }
}

