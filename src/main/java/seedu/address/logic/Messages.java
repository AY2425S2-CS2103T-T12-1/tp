package seedu.address.logic;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. Type \"help\" to see the list of commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!\n"
            + "Ensure that it is not out of range.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d groups listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_INVALID_GROUP_DISPLAYED_INDEX = "The group index provided is invalid!\n"
            + "Ensure that it is not out of range.";

    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist!";
    public static final String MESSAGE_GROUP_NOT_FOUND = "This group does not exist!";
    public static final String MESSAGE_PERSON_NOT_IN_GROUP = "This person does not exist in the group!";
    public static final String MESSAGE_INVALID_WEEK_NUM = "Week number must be between 1 and 13 (inclusive)!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code group} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: [");
        person.getTags().forEach(builder::append);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Group group) {
        final StringBuilder builder = new StringBuilder();
        builder.append(group.getGroupName())
                .append("\nTags: [");
        group.getTags().forEach(builder::append);
        builder.append("]\nMembers: [\n");
        group.getGroupMembersMap().forEach((key, value) -> builder.append(Messages.format(key))
                .append("; Role: ")
                .append(value.getRole())
                .append(", Attendance: ")
                .append(Arrays.toString(value.getAttendance()))
                .append("\n"));
        builder.append("]");
        return builder.toString();
    }

}
