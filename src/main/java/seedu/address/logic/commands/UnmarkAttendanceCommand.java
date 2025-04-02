package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a command that unmarks a student's attendance for a particular group and week.
 */
public class UnmarkAttendanceCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "unmark-attendance";

    /**
     * Usage message for the command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the attendance of the person identified "
            + "by their name, their group and the week.\n"
            + "Parameters: "
            + PREFIX_PERSON + "NAME "
            + PREFIX_GROUP + "GROUP NAME "
            + PREFIX_WEEK + "WEEK NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "Jensen Huang "
            + PREFIX_GROUP + "CS2103T T12 "
            + PREFIX_WEEK + "10";

    /**
     * Success message for command.
     */
    public static final String MESSAGE_UNMARK_ATTENDANCE_SUCCESS = "Unmarked attendance for %s, %s, week %d";


    /**
     * Group Member Detail object to be updated.
     */
    private final String personName;
    private final String groupName;
    private final int week;


    /**
     * Creates a {@code M=UnmarkAttendanceCommand} to unmark a student's attendance.
     *
     * @param personName The name of the student.
     * @param groupName  The name of the group the student belongs to.
     * @param week       The week number for which attendance is being unmarked.
     */
    public UnmarkAttendanceCommand(String personName, String groupName, int week) {
        requireAllNonNull(personName, groupName, week);
        this.personName = personName;
        this.groupName = groupName;
        this.week = week;
    }

    /**
     * Executes the command to unmark attendance.
     *
     * @param model The model in which the command should be executed.
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException  if week number is invalid
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

        if (!GroupMemberDetail.isValidWeek(week)) {
            throw new CommandException("Week number must be between 1 and 13 (inclusive)!");
        }

        try {
            model.unmarkAttendance(person, group, week);
        } catch (PersonNotFoundException e) {
            throw new CommandException("Person does not exist in group!");
        }

        return new CommandResult(String.format(MESSAGE_UNMARK_ATTENDANCE_SUCCESS, personName, groupName, week));
    }


    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if both objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null cases
        if (!(other instanceof UnmarkAttendanceCommand otherCmd)) {
            return false;
        }

        return personName.equals(otherCmd.personName)
                && groupName.equals(otherCmd.groupName)
                && week == otherCmd.week;
    }
}
