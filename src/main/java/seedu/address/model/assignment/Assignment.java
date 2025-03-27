package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Repesents an assignment.
 */
public class Assignment {

    /**
     * The assignment name.
     */
    private String name;

    /**
     * The assignment deadline.
     */
    private Date deadline;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param name The assignment name.
     * @param deadline deadline of the assignment.
     */
    public Assignment(String name, Date deadline) {
        requireAllNonNull(name, deadline);
        this.name = name;
        this.deadline = deadline;
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
