package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit-group";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    private final Index index;
    private final String newGroupName;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the group identified "
            + "by the index number used in the displayed group list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "CS2103T T12-1 ";

    /**
     * Creates an EditGroupCommand to update a group's name.
     *
     * @param index The index of the group to be edited.
     * @param newGroupName The new name for the group.
     */
    public EditGroupCommand(Index index, String newGroupName) {
        requireAllNonNull(index, newGroupName);

        this.index = index;
        this.newGroupName = newGroupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), newGroupName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGroupCommand)) {
            return false;
        }

        EditGroupCommand e = (EditGroupCommand) other;
        return index.equals(e.index)
                && newGroupName.equals(e.newGroupName);
    }
}