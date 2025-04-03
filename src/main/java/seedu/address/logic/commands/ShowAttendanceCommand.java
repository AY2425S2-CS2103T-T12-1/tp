package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a command that shows a student's attendance for a particular group.
 */
public class ShowAttendanceCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "show-attendance";

    /**
     * Usage message for the command.
     */
    public static final String MESSAGE_USAGE = String.format("""
            %s: Displays the attendance record of the specified person in the specified group.
            Parameters: %sPERSON_NAME %sGROUP_NAME
            Example: %s %sJensen Huang %sCS2103T T12
            """,
            COMMAND_WORD, PREFIX_PERSON, PREFIX_GROUP, COMMAND_WORD, PREFIX_PERSON, PREFIX_GROUP);

    /**
     * Success message for command.
     */
    public static final String MESSAGE_SHOW_ATTENDANCE_SUCCESS = "Showing attendance for %s in %s";

    private final String personName;
    private final String groupName;

    /**
     * Creates a {@code ShowAttendanceCommand} to show a student's attendance.
     *
     * @param personName The name of the student.
     * @param groupName  The name of the group the student belongs to.
     */
    public ShowAttendanceCommand(String personName, String groupName) {
        requireAllNonNull(personName, groupName);
        this.personName = personName;
        this.groupName = groupName;
    }

    /**
     * Executes the command to show attendance.
     *
     * @param model The model in which the command should be executed.
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException if group or person is not found, or person is not in the group
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group group;
        try {
            group = model.getGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException("Group not found!");
        }

        Person person;
        try {
            person = model.getPerson(personName);
        } catch (PersonNotFoundException e) {
            throw new CommandException("Person not found!");
        }

        if (!group.contains(person)) {
            throw new CommandException("Person does not exist in group!");
        }

        GroupMemberDetail groupMemberDetail = group.getGroupMemberDetail(person);
        boolean[] attendance = groupMemberDetail.getAttendance();

        // Count present weeks for the feedback message
        int presentCount = 0;
        for (boolean present : attendance) {
            if (present) {
                presentCount++;
            }
        }

        // Create a detailed text representation for display
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Attendance for %s in %s:\n", personName, groupName));
        sb.append(String.format("Total attendance: %d/%d weeks\n\n", presentCount, attendance.length));

        for (int i = 0; i < attendance.length; i++) {
            sb.append(String.format("Week %d: %s\n", i + 1, attendance[i] ? "Present" : "Absent"));
        }

        return new CommandResult(sb.toString());
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if both objects are ShowAttendanceCommand instances with the same person and group names.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null cases
        if (!(other instanceof ShowAttendanceCommand otherCmd)) {
            return false;
        }

        return personName.equals(otherCmd.personName)
                && groupName.equals(otherCmd.groupName);
    }
}
