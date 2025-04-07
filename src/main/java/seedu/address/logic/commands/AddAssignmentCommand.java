package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Adds a new assignment to the specified group.
 */
public class AddAssignmentCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "add-assignment";

    public static final String MESSAGE_USAGE = String.format("""
                    %s: Adds a new assignment in the specified group.
                    Parameters: %sASSIGNMENT_NAME %sGROUP_NAME %sDEADLINE
                    Example: %s %sHW 1 %sCS2103T T12 %s21-04-2025
                    """,
            COMMAND_WORD, PREFIX_NAME, PREFIX_GROUP, PREFIX_DATE, COMMAND_WORD, PREFIX_NAME,
            PREFIX_GROUP, PREFIX_DATE);

    private static final String MESSAGE_SUCCESS = "Added new assignment to group!\nGroup: %s\nAssignment: %s";

    /**
     * Name of the new assignment to be added.
     */
    private final String name;

    /**
     * Name of the group to be added to.
     */
    private final String groupName;

    /**
     * Deadline of the assignment
     */
    private final LocalDate deadline;

    /**
     * Grade penalty for late submission, default value is 1.0.
     */
    private final Float penalty;

    /**
     * Creates an AddAssignmentCommand to add a new assignment with the specified name, group, and deadline.
     *
     * @param name      The name of the assignment to be added.
     * @param groupName The name of the group to be added.
     * @param deadline  The deadline of the assignment.
     */
    public AddAssignmentCommand(String name, String groupName, LocalDate deadline, Float penalty) {
        requireAllNonNull(name, groupName, deadline);
        this.name = name;
        this.groupName = groupName;
        this.deadline = deadline;
        this.penalty = penalty == null ? 1.0F : penalty;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group group;
        try {
            group = model.getGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        if (model.isAssignmentInGroup(name, group)) {
            throw new CommandException("Another assignment with the same name already exists in group!");
        }

        try {
            Assignment assignment = model.addAssignmentToGroup(name, deadline, group, penalty);
            return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, Messages.format(assignment)));
        } catch (DuplicateAssignmentException d) {
            throw new CommandException(d.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAssignmentCommand otherAddAssignmentCommand)) {
            return false;
        }

        return name.equals(otherAddAssignmentCommand.name) && groupName.equals(otherAddAssignmentCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("groupName", groupName)
                .add("deadline", deadline)
                .toString();
    }
}

