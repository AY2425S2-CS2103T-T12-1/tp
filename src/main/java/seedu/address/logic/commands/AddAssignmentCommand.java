package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LATE_PENALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentsException;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the specified assignment in the specified group. "
            + "Parameters: "
            + PREFIX_NAME + "ASSIGNMENT NAME "
            + PREFIX_GROUP + "GROUP "
            + PREFIX_DATE + "DEADLINE"
            + "[" + PREFIX_LATE_PENALTY + "LATE PENALTY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "HW 1 "
            + PREFIX_GROUP + "CS2103T T12-1 "
            + PREFIX_DATE + "21-04-2025\n";

    private static final String MESSAGE_SUCCESS = "New assignment added to group %s: %s";

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
     * @param name The name of the assignment to be added.
     * @param groupName The name of the group to be added.
     * @param deadline The deadline of the assignment.
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
            throw new CommandException("Group not found!");
        }
        try {
            model.addAssignmentToGroup(name, deadline, group, penalty);
        } catch (DuplicateAssignmentsException d) {
            throw new CommandException(d.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, name));
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

