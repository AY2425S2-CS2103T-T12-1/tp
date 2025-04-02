package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Deletes the specified assignment.
 */
public class DeleteAssignmentCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "delete-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified assignment. "
            + "Parameters: "
            + PREFIX_NAME + "ASSIGNMENT NAME "
            + PREFIX_GROUP + "GROUP "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "HW 1 "
            + PREFIX_GROUP + "CS2103T T12-1\n";

    private static final String MESSAGE_SUCCESS = "Assignment %s deleted from group %s.";

    /**
     * Name of the assignment to be deleted.
     */
    private final String name;

    /**
     * Name of the group the assignment belongs in.
     */
    private final String groupName;

    /**
     * Creates a DeleteAssignmentCommand to add a new assignment with the specified name, group, and deadline.
     *
     * @param name The name of the assignment to be added.
     * @param groupName The name of the group to be added.
     */
    public DeleteAssignmentCommand(String name, String groupName) {
        requireAllNonNull(name, groupName);
        this.name = name;
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group group;
        try {
            group = model.getGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException("Group not found!");
        }

        try {
            model.removeAssignmentFromGroup(name, group);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException("Assignment not found!");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, name, groupName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAssignmentCommand otherDeleteAssignmentCommand)) {
            return false;
        }

        return name.equals(otherDeleteAssignmentCommand.name)
                && groupName.equals(otherDeleteAssignmentCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("groupName", groupName)
                .toString();
    }
}


