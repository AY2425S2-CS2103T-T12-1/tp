package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Deletes an existing group identified using its displayed index.
 */
public class DeleteGroupCommand extends Command {
    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "delete-group";

    public static final String MESSAGE_USAGE = String.format("""
                    %s: Deletes the specified group from the group list. Useful for removing a tutorial group"""
                    + """
                     that is no longer needed.
                    Parameters: INDEX (must be a positive integer)
                    Note: INDEX refers to the index number of the group in the last displayed group list."""
                    + """
                     It must be a positive integer, i.e., 1, 2, 3, ...
                    Example: %s 2
                    """,
            COMMAND_WORD, COMMAND_WORD);

    private static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted group:\n%1$s";

    /**
     * Index of the group to be edited.
     */
    private final Index targetIndex;

    /**
     * Creates an DeleteGroupCommand to delete an existing group.
     *
     * @param targetIndex The index of the group to be deleted.
     */
    public DeleteGroupCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteGroup(groupToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, Messages.format(groupToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGroupCommand otherDeleteGroupCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteGroupCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
