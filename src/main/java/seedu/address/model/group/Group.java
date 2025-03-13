package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a Group in the address book.
 * A {@code Group} consists of a unique name and an ordered list of unique members.
 */
public class Group {

    /** Message to indicate the constraints for group names. */
    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";

    /** Regular expression to validate group names. */
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    /** The name of the group. */
    private String groupName;

    /** The list of members in the group, stored in order of insertion. */
    private ArrayList<Person> groupMembers;

    /**
     * Constructs a {@code Group} with a specified name.
     * Initializes an empty list of group members.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.groupMembers = new ArrayList<>();
    }

    /**
     * Constructs a {@code Group} with a specified name and an existing list of members.
     *
     * @param groupName A valid group name.
     * @param groupMembers The list of members in the group.
     */
    public Group(String groupName, ArrayList<Person> groupMembers) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        if (groupMembers != null) {
            this.groupMembers = groupMembers;
        } else {
            this.groupMembers = new ArrayList<>();
        }
    }

    /**
     * Checks if the given string is a valid group name.
     *
     * @param test The string to test.
     * @return True if the name is valid, false otherwise.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Gets the name of the group.
     *
     * @return The group's name.
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * Sets a new name for the group.
     *
     * @param groupName The new name to assign to the group.
     */
    public void setGroupName(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
    }

    /**
     * Retrieves the list of group members.
     *
     * @return An ArrayList of group members.
     */
    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }

    /**
     * Checks whether this group is equal to another object.
     * Two groups are considered equal if they have the same name.
     *
     * @param other The object to compare with.
     * @return True if both groups have the same name, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return groupName.equals(otherGroup.groupName);
    }

    /**
     * Computes the hash code for this group based on its name.
     *
     * @return The hash code of the group.
     */
    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Checks if the group contains a specific person.
     *
     * @param toCheck The person to check for membership in the group.
     * @return True if the person exists in the group, false otherwise.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return groupMembers.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the group.
     * Ensures that the person does not already exist in the group.
     *
     * @param p The person to be added.
     * @throws DuplicatePersonException If the person already exists in the group.
     */
    public void add(Person p) {
        requireNonNull(p);
        if (contains(p)) {
            throw new DuplicatePersonException();
        }
        this.groupMembers.add(p);
    }

    /**
     * Removes a person from the group.
     * Ensures that the person exists before attempting removal.
     *
     * @param p The person to be removed.
     * @throws PersonNotFoundException If the person is not found in the group.
     */
    public void remove(Person p) {
        for (int i = 0; i < this.groupMembers.size(); ++i) {
            if (this.groupMembers.get(i).equals(p)) {
                this.groupMembers.remove(i);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Retrieves a person at a specified index in the group.
     *
     * @param i The index of the person to retrieve.
     * @return The person at the given index.
     */
    public Person get(int i) {
        return this.groupMembers.get(i);
    }

    /**
     * Gets the number of members in the group.
     *
     * @return The size of the group.
     */
    public int size() {
        return this.groupMembers.size();
    }

    /**
     * Returns a string representation of the group in the format "[GroupName]".
     *
     * @return A formatted string representing the group.
     */
    @Override
    public String toString() {
        return '[' + this.groupName + ']';
    }
}
