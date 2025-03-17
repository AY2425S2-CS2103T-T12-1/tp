package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Adds a new group to the address book.
 */
public class AddGroupCommand extends Command {

    /**
     * The command word to trigger this command.
     */
    public static final String COMMAND_WORD = "add-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new group to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103T T12-1";

    private static final String MESSAGE_SUCCESS = "New group added: %1$s";
    private static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    /**
     * Name of the new group to be added.
     */
    private final String groupName;

    /**
     * Creates an AddGroupCommand to add a new group with the specified name.
     *
     * @param groupName The name of the group to be added.
     */
    public AddGroupCommand(String groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Create a new group with the given name and an empty list of members
        Group groupToAdd = new Group(groupName, new ArrayList<Person>());

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

        return groupName.equals(otherAddGroupCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupName", groupName)
                .toString();
    }
}
