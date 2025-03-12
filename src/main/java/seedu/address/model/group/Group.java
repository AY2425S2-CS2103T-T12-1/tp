package seedu.address.model.group;

import java.util.ArrayList;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group in the address book.
 * A {@code Group} is an ordered list of unique members, ordered by order of insertion.
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
     * Returns true if the group contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return groupMembers.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the group.
     * Returns true if successful.
     * 
     * @param p A valid Person object.
     */
    public void add(Person p) {
        requireNonNull(p);
        if (contains(p)) {
            throw new DuplicatePersonException();
        }
        this.groupMembers.add(p);
    }

    /**
     * Remove a person to the group. 
     * Returns true if successful.
     * 
     * @param p A valid Person object that exists in the group. 
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

