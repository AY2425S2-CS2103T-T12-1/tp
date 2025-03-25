package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;

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
}
