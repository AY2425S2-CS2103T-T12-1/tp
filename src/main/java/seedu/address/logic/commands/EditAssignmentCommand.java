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
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
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

    public static final String MESSAGE_USAGE = String.format("""
                    %s: Edits the assignment in the specified group.
                    Parameters: %sASSIGNMENT_NAME %sGROUP_NAME [%sNEW_NAME] [%sDEADLINE]
                    Example: %s %sHW 1 %sCS2103T T12 %sHW 1 %s21-04-2025
                    """,
            COMMAND_WORD, PREFIX_NAME, PREFIX_GROUP, PREFIX_NEW_NAME, PREFIX_DATE,
            COMMAND_WORD, PREFIX_NAME, PREFIX_GROUP, PREFIX_NEW_NAME, PREFIX_DATE);

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
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
     * Late penalty for assignment.
     */
    private final Float penalty;


    /**
     * Creates an EditAssignmentCommand to edit an assignment.
     *
     * @param name      The name of the assignment to be added.
     * @param groupName The name of the group to be added.
     */
    public EditAssignmentCommand(String name, String groupName, String newName, LocalDate deadline, Float penalty) {
        requireAllNonNull(name, groupName);
        this.name = name;
        this.groupName = groupName;
        if (name.equals(newName)) {
            this.newName = null;
        } else {
            this.newName = newName;
        }
        this.deadline = deadline;
        this.penalty = penalty;
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
            model.editAssignment(name, newName, deadline, group, penalty);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException("Assignment not found!");
        } catch (DuplicateAssignmentException d) {
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
