package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import javafx.scene.layout.Region;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.ui.GroupDetailCard;
import seedu.address.ui.Result;
import seedu.address.ui.UiPart;

/**
 * Represents a GroupMemberDetail in the address book to associate a Person with a Group.
 * A {@code GroupMemberDetail} consists of a person, group, attendance, and assignment grades.
 */
public class GroupMemberDetail implements Result {
    /**
     * Different roles of memebers in a group
     */
    public enum Role {
        Student,
        TeachingAssistant,
        Lecturer
    }

    public static final int WEEKS_PER_SEMESTER = 13;

    /**
     * Message to indicate the constraints for week number
     */
    public static final String MESSAGE_CONSTRAINTS = String.format(
            "Weeks should be between 1 and %d", WEEKS_PER_SEMESTER);

    /**
     * The {@code Person} whose detail is describing.
     */
    private Person person;
    /**
     * The {@code Group} whose person belong to.
     */
    private Group group;

    /**
     * The {@code Role} that the person has.
     */
    private Role role;

    /**
     * The list of members in the group, stored in order of insertion.
     */
    private boolean[] attendance;

    /**
     * The grades of the assignments of this person in this group.
     */
    private ArrayListMap<Assignment, Float> grades;

    /**
     * Constructs a {@code GroupMemberDetail} with a specified group member {@code Person}.
     * Initializes an empty list of attendance.
     * Assumes the person is a student
     *
     * @param Person A valid person.
     * @param Group  A valid group.
     */
    public GroupMemberDetail(Person person, Group group) {
        this(person, group, Role.Student, new boolean[WEEKS_PER_SEMESTER]);
    }

    /**
     * Constructs a {@code GroupMemberDetail} with a specified group member {@code Person}.
     * Initializes an empty list of attendance.
     * Assumes the person is a student
     *
     * @param Person A valid person.
     * @param Group  A valid group.
     * @param Role   A valid role.
     */
    public GroupMemberDetail(Person person, Group group, Role role, boolean[] attendance) {
        requireAllNonNull(person, role);
        this.person = person;
        this.group = group;
        this.role = role;
        this.attendance = attendance;
        this.grades = new ArrayListMap<>();
    }

    public GroupMemberDetail copy(Person newPerson) {
        return new GroupMemberDetail(newPerson, this.group, this.role, this.attendance);
    }

    /**
     * Constructs a {@code GroupMemberDetail} with a specified group member {@code Person}.
     * Initializes an empty list of attendance.
     * Assumes the person is a student.
     * Used by storage
     *
     * @param person        A valid person.
     * @param role          A valid role.
     * @param attendance    A valid attendance array.
     * @param grades        A valid grade map.
     */
    public GroupMemberDetail(Person person, Role role, boolean[] attendance,
                             ArrayListMap<Assignment, Float> grades) {
        requireAllNonNull(person, role, attendance, grades);
        this.person = person;
        this.role = role;
        this.attendance = attendance;
        this.grades = grades;
    }

    /**
     * Checks if the week is between 1 and the number of weeks in a semester.
     *
     * @param test The week number to test.
     * @return True if the week is valid, false otherwise.
     */
    public static boolean isValidWeek(int test) {
        return test >= 1 && test <= WEEKS_PER_SEMESTER;
    }

    /**
     * Gets the person.
     *
     * @return The person associated with this object.
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Gets the group.
     *
     * @return The group associated with this object.
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * Sets the Group.
     *
     * @param group A valid group.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Gets the role.
     *
     * @return The role of the person.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Sets the role.
     *
     * @param role The role of the person.
     */
    public void setRole(Role role) {
        requireNonNull(role);
        this.role = role;
    }

    /**
     * Gets the attendance.
     *
     * @return The attendance.
     */
    public boolean[] getAttendance() {
        return this.attendance;
    }

    /**
     * Gets the grades.
     *
     * @return The grades.
     */
    public ArrayListMap<Assignment, Float> getGrades() {
        return this.grades;
    }

    /**
     * Marks attendance for a particular week;
     *
     * @param week The week to mark the attendance
     */
    public void markAttendance(int week) {
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        this.attendance[week - 1] = true;
    }

    /**
     * Unmarks attendance for a particular week;
     *
     * @param week The week to unmark the attendance
     */
    public void unmarkAttendance(int week) {
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        this.attendance[week - 1] = false;
    }

    /**
     * Assigns a specified grade to an Assignment.
     * Calculates a simple penalty for late submission
     */
    public void gradeAssignment(Assignment assignment, Float score) {
        Float penalty = assignment.getPenalty();
        if (LocalDate.now().isAfter(assignment.getDeadline())) {
            score = score * penalty;
        }
        grades.put(assignment, score);
    }

    /**
     * Gets the grade for the specified assignment.
     */
    public Float getAssignmentGrade(Assignment assignment) {
        return this.grades.get(assignment);
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
        if (!(other instanceof GroupMemberDetail otherGroupMemberDetail)) {
            return false;
        }

        return group.equals(otherGroupMemberDetail.getGroup()) && person.equals(otherGroupMemberDetail.getPerson());
    }

    /**
     * Computes the hash code for this group based on its name.
     *
     * @return The hash code of the group.
     */
    @Override
    public int hashCode() {
        return Objects.hash(person, group);
    }

    /**
     * Returns a string representation of the group in the format "[GroupName]".
     *
     * @return A formatted string representing the group.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", person)
                .add("group", group)
                .add("attendance", attendance)
                .add("role", role)
                .toString();
    }

    @Override
    public UiPart<Region> createCard(int displayedIndex) {
        return new GroupDetailCard(this, displayedIndex);
    }
}

