package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a command that edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "edit-group";

    /**
     * Usage message for the command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the group identified "
            + "by the index number used in the displayed group list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "CS2103T T12-1 ";

    /**
     * Success message for editing a group.
     */
    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";

    /**
     * Error message if a duplicate group exists in the address book.
     */
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book.";

    /**
     * Index of the group to be edited.
     */
    private final Index index;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * Creates an EditGroupCommand to update a group's name.
     *
     * @param index        The index of the group to be edited.
     * @param editGroupDescriptor The descriptor of the group to be edited.
     */
    public EditGroupCommand(Index index, EditGroupDescriptor editGroupDescriptor) {
        requireAllNonNull(index, editGroupDescriptor);

        this.index = index;
        this.editGroupDescriptor = editGroupDescriptor;
    }

    /**
     * Executes the command to edit a group.
     *
     * @param model The model in which the command should be executed.
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException If the index is invalid or if the new group name already exists.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid Group");
        }

        Group groupToEdit = lastShownList.get(index.getZeroBased());

        model.editGroup(groupToEdit, editGroupDescriptor);
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, editGroupDescriptor.getGroupName()));
    }

    /**
     * Creates a new Group object with the updated name while retaining the group members.
     *
     * @param groupToEdit  The existing group to be edited.
     * @param editGroupDescriptor The new descriptor for the group.
     * @return A new Group object with the updated name and existing members.
     */
    private static Group createEditedGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;
        String groupName = editGroupDescriptor.getGroupName().orElse(groupToEdit.getGroupName());
        ArrayList<Person> personArrayList = groupToEdit.getGroupMembers();
        Set<Tag> taglist = editGroupDescriptor.getTagList().orElse(groupToEdit.getTags());
        return new Group(groupName, personArrayList, taglist);
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if both objects are EditGroupCommand instances with the same index and new group name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null cases
        if (!(other instanceof EditGroupCommand otherCmd)) {
            return false;
        }

        return index.equals(otherCmd.index)
                && editGroupDescriptor.equals(otherCmd.editGroupDescriptor);
    }

    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the
     * corresponding field value of the group.
      */
    public static class EditGroupDescriptor {
        private String groupName;
        private Set<Tag> tagList;
        public EditGroupDescriptor() {}
        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setGroupName(toCopy.groupName);
            setTagList(toCopy.tagList);
        }
        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(groupName, tagList);
        }
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
        public Optional<String> getGroupName() {
            return Optional.ofNullable(groupName);
        }
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTagList(Set<Tag> taglist) {
            this.tagList = taglist != null ? new HashSet<>(taglist) : null;
        }
        public Optional<Set<Tag>> getTagList() {
            return (tagList != null) ? Optional.of(Collections.unmodifiableSet(tagList)) : Optional.empty();
        }
    }
}
