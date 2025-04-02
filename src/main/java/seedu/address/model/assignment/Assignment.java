package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Repesents an assignment.
 */
public class Assignment {
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";
    /**
     * The assignment name.
     */
    private String name;

    /**
     * The assignment deadline.
     */
    private LocalDate deadline;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param name The assignment name.
     * @param deadline deadline of the assignment.
     */
    public Assignment(String name, LocalDate deadline) {
        requireAllNonNull(name, deadline);
        this.name = name;
        this.deadline = deadline;
    }

    /**
     * Edits the assignment details.
     *
     * @param name The assignment name.
     * @param deadline deadline of the assignment.
     */
    public void editAssignment(String name, LocalDate deadline) {
        if (name != null) {
            this.name = name;
        }
        if (deadline != null) {
            this.deadline = deadline;
        }
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }
    /**
     * Computes the hash code for this assignment based on its name and group.
     *
     * @return The hash code of the group.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
