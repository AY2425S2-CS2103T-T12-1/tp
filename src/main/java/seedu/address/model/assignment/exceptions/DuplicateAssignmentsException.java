package seedu.address.model.assignment.exceptions;
/**
 * Signals that the operation will result in duplicate Groups (Groups are considered duplicates if they have the same
 * identity).
 */
public class DuplicateAssignmentsException extends RuntimeException {
    public DuplicateAssignmentsException() {
        super("Operation would result in duplicate assignments");
    }
}
