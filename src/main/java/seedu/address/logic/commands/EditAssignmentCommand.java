package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_NAME;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentsException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Edits the specified assignment.
 */
public class EditAssignmentCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "edit-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new assignment to the specified group. "
            + "Parameters: "
            + PREFIX_NAME + "ASSIGNMENT NAME "
            + PREFIX_GROUP + "GROUP ["
            + PREFIX_NEW_NAME + "NEW NAME] ["
            + PREFIX_DATE + "DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "HW 1 "
            + PREFIX_GROUP + "CS2103T T12 "
            + PREFIX_NEW_NAME + "Assignment 1 "
            + PREFIX_DATE + "21-04-2025\n";

    private static final String MESSAGE_SUCCESS = "Assignment in group %s has been edited: %s";

    /**
     * Name of the assignment.
     */
    private final String name;

    /**
     * Name of the group the assignment belongs in.
     */
    private final String groupName;

    /**
     * New name of the assignment.
     */
    private final String newName;

    /**
     * New deadline of the assignment.
     */
    private final LocalDate deadline;


    /**
     * Creates an EditAssignmentCommand to edit an assignment.
     *
     * @param name The name of the assignment to be added.
     * @param groupName The name of the group to be added.
     */
    public EditAssignmentCommand(String name, String groupName, String newName, LocalDate deadline) {
        requireAllNonNull(name, groupName);
        this.name = name;
        this.groupName = groupName;
        if (name.equals(newName)) {
            this.newName = null;
        } else {
            this.newName = newName;
        }
        this.deadline = deadline;
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
            model.editAssignment(name, newName, deadline, group);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException("Assignment not found!");
        } catch (DuplicateAssignmentsException d) {
            throw new CommandException(d.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, newName == null ? name : newName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentCommand otherEditAssignmentCommand)) {
            return false;
        }

        return name.equals(otherEditAssignmentCommand.name)
                && groupName.equals(otherEditAssignmentCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("groupName", groupName)
                .add("newName", newName)
                .add("deadline", deadline)
                .toString();
    }
}
