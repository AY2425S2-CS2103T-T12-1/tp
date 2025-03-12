package seedu.address.model.group.exceptions;
/**
 * Signals that the operation will result in duplicate Groups (Groups are considered duplicates if they have the same
 * identity).
 */
public class DuplicateGroupsException extends RuntimeException {
    public DuplicateGroupsException() {
        super("Operation would result in duplicate groups");
    }
}
