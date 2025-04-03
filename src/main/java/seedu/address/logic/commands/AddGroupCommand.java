package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds a new group to the address book.
 */
public class AddGroupCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "add-group";

    public static final String MESSAGE_USAGE = String.format("""
                    %s: Adds a new group to the group list. Useful for adding new tutorial groups.
                    Parameters: %sGROUP_NAME [%sTAG]...
                    Example: %s %sCS2103T T12 %sCS""",
            COMMAND_WORD, PREFIX_NAME, PREFIX_TAG, COMMAND_WORD, PREFIX_NAME, PREFIX_TAG);

    private static final String MESSAGE_SUCCESS = "New group added: %1$s";
    private static final String MESSAGE_DUPLICATE_GROUP = "Another group with the same name"
            + " already exists in the address book!";

    /**
     * Name of the new group to be added.
     */
    private final String groupName;
    private final Set<Tag> tags;

    /**
     * Creates an AddGroupCommand to add a new group with the specified name.
     *
     * @param groupName The name of the group to be added.
     */
    public AddGroupCommand(String groupName, Set<Tag> tags) {
        requireAllNonNull(groupName, tags);
        this.groupName = groupName;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Create a new group with the given name and an empty list of members
        Group groupToAdd = new Group(groupName, new ArrayList<Person>(), tags);

        // Check if the group already exists
        if (model.hasGroup(groupToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        // Add group to the model
        model.addGroup(groupToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(groupToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddGroupCommand otherAddGroupCommand)) {
            return false;
        }

        return groupName.equals(otherAddGroupCommand.groupName) && tags.equals(otherAddGroupCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupName", groupName)
                .add("tags", tags)
                .toString();
    }
}
