package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.MessageFormat;
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
     * Penalty for grading should there be late submission.
     */
    private Float penalty;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param name     The assignment name.
     * @param deadline deadline of the assignment.
     */
    public Assignment(String name, LocalDate deadline, Float penalty) {
        requireAllNonNull(name, deadline, penalty);
        this.name = name;
        this.deadline = deadline;
        this.penalty = penalty;
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

    public Float getPenalty() {
        return this.penalty;
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

    @Override
    public String toString() {
        return MessageFormat.format("Assignment'{'name=''{0}'', deadline={1}, penalty={2}'}'", name, deadline, penalty);
    }
}
