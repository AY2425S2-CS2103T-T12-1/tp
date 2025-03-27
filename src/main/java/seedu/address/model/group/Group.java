package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.scene.layout.Region;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.ui.GroupCard;
import seedu.address.ui.Result;
import seedu.address.ui.UiPart;

/**
 * Represents a Group in the address book.
 * A {@code Group} consists of a unique name and an ordered list of unique members.
 */
public class Group implements Result {

    /**
     * Message to indicate the constraints for group names.
     */
    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";

    /**
     * Regular expression to validate group names.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    /**
     * The name of the group.
     */
    private String groupName;

    /**
     * The map of all members in the group.
     */
    private final ArrayListMap<Person, GroupMemberDetail> groupMembers;

    private final Set<Tag> tags;

    /**
     * Constructs a {@code Group} with a specified name.
     * Initializes an empty list of group members.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        this(groupName, (Collection<Person>) null, null);
    }

    /**
     * Constructs a {@code Group} with a specified name and an existing list of members.
     * Assumes groupMembers are students.
     *
     * @param groupName    A valid group name.
     * @param groupMembers The list of members in the group.
     */
    public Group(String groupName, Collection<Person> groupMembers) {
        this(groupName, groupMembers, null);
    }

    /**
     * Constructs a {@code Group} with a specified name, existing list of members, and a set of tags.
     *
     * @param groupName    A valid group name.
     * @param groupMembers The collection of members in the group.
     * @param tags         The collection of tags for the group.
     */
    public Group(String groupName, Collection<Person> groupMembers, Collection<Tag> tags) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.groupMembers = new ArrayListMap<>();
        if (groupMembers != null) {
            for (Person p : groupMembers) {
                this.groupMembers.put(p, new GroupMemberDetail(p, this));
            }
        }
        this.tags = tags == null ? new HashSet<>() : new HashSet<>(tags);
    }

    /**
     * Constructs a {@code Group} with a specified name, existing Map and set of tags.
     *
     * @param groupName     A valid group name.
     * @param groupMembers  A map of Person as key to GroupMemberDetail as value.
     * @param tags          The collection of tags for the group.
     */
    public Group(String groupName, ArrayListMap<Person, GroupMemberDetail> groupMembers, Collection<Tag> tags) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.tags = tags == null ? new HashSet<>() : new HashSet<>(tags);
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
        return new ArrayList<>(groupMembers.keySet());
    }
    public ArrayListMap<Person, GroupMemberDetail> getGroupMembersMap() {
        ArrayListMap<Person, GroupMemberDetail> copied = new ArrayListMap<>();
        copied.putAll(this.groupMembers);
        return copied;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two gorups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }
        return otherGroup != null && otherGroup.getGroupName().equals(groupName);
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
        if (!(other instanceof Group otherGroup)) {
            return false;
        }

        return groupName.equals(otherGroup.groupName) && tags.equals(otherGroup.tags);
    }

    /**
     * Computes the hash code for this group based on its name.
     *
     * @return The hash code of the group.
     */
    @Override
    public int hashCode() {
        return Objects.hash(groupName, tags);
    }

    /**
     * Checks if the group contains a specific person.
     *
     * @param toCheck The person to check for membership in the group.
     * @return True if the person exists in the group, false otherwise.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return groupMembers.containsKey(toCheck);
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
        this.groupMembers.put(p, new GroupMemberDetail(p, this));
    }

    /**
     * Removes a person from the group.
     * Ensures that the person exists before attempting removal.
     *
     * @param p The person to be removed.
     * @throws PersonNotFoundException If the person is not found in the group.
     */
    public void remove(Person p) {
        if (groupMembers.remove(p) == null) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Retrieves a person at a specified index in the group.
     *
     * @param i The index of the person to retrieve.
     * @return The person at the given index.
     */
    public Person get(int i) {
        return groupMembers.get(i).getPerson();
    }

    /**
     * Gets the number of members in the group.
     *
     * @return The size of the group.
     */
    public int size() {
        return this.groupMembers.size();
    }

    public GroupMemberDetail getGroupMemberDetail(Person person) {
        return groupMembers.get(person);
    }

    /**
     * Returns a string representation of the group in the format "[GroupName]".
     *
     * @return A formatted string representing the group.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", groupName)
                .add("tags", tags)
                .toString();
    }

    @Override
    public UiPart<Region> createCard(int displayedIndex) {
        return new GroupCard(this, displayedIndex);
    }

    public ArrayList<GroupMemberDetail> getGroupDetails() {
        return groupMembers.values();
    }
}
