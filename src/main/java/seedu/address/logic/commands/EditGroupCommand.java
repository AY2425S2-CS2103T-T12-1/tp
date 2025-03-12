package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit-group";

    private final Index index;
    private final String newGroupName;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the group identified "
            + "by the index number used in the displayed group list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "CS2103T T12-1 ";

    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book.";

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
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid Group");
        }

        Group groupToEdit = lastShownList.get(index.getZeroBased());
        Group editedGroup = createEditedGroup(groupToEdit, newGroupName);

        if (model.hasGroup(editedGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.setGroup(groupToEdit, editedGroup);
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, newGroupName));

    }

    private static Group createEditedGroup(Group groupToEdit, String newGroupName) {
        assert groupToEdit != null;

        ArrayList<Person> list = groupToEdit.getGroupMembers();
        return new Group(newGroupName, list);
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
