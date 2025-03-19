package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Adds a Person to a Group
 */
public class AddPersonToGroupCommand extends Command {
    public static final String COMMAND_WORD = "add-to-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the group. "
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_PERSON + " Alex Yeoh "
            + PREFIX_GROUP + " Group 1";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the group";

    /**
     * Index of Person to be added
     */
    private final Index toAdd;
    /**
     * Index of Group to be added to
     */
    private final Index toBeAddedTo;

    /**
     * Constructor for AddPersonToGroupCommand that takes two Index identifiers for
     * Person and Group
     *
      * @param toAdd Index of Person to be added
     * @param toBeAddedTo Index of Group to be added to
     */
    public AddPersonToGroupCommand(Index toAdd, Index toBeAddedTo) {
        requireNonNull(toAdd);
        requireNonNull(toBeAddedTo);
        this.toAdd = toAdd;
        this.toBeAddedTo = toBeAddedTo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        List<Group> groupList = model.getFilteredGroupList();

        if (toAdd.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (toBeAddedTo.getZeroBased() >= groupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Person personToAdd = personList.get(toAdd.getZeroBased());
        Group groupToBeAddedTo = groupList.get(toBeAddedTo.getZeroBased());

        if (groupToBeAddedTo.contains(personToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPersonToGroup(personToAdd, groupToBeAddedTo);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToAdd)));
    }
}
