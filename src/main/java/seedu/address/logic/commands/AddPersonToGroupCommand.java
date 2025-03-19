package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.List;

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
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_PERSON + " 1 "
            + PREFIX_GROUP + " 2";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the group";

    /**
     * Index of Person to be added
     */
    private final String toAdd;
    /**
     * Index of Group to be added to
     */
    private final String toBeAddedTo;

    /**
     * Constructor for AddPersonToGroupCommand that takes two Index identifiers for
     * Person and Group
     *
      * @param toAdd Index of Person to be added
     * @param toBeAddedTo Index of Group to be added to
     */
    public AddPersonToGroupCommand(String toAdd, String toBeAddedTo) {
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
        Person personToAdd = null;
        Group groupToBeAddedTo = null;
        for (Person person : personList) {
            if (person.getName().fullName.equals(toAdd)) {
                personToAdd = person;
            }
        }
        for (Group group : groupList) {
            if (group.getGroupName().equals(toBeAddedTo)) {
                groupToBeAddedTo = group;
            }
        }

        if (personToAdd == null) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }
        if (groupToBeAddedTo == null) {
            throw new CommandException(Messages.MESSAGE_GROUP_NOT_FOUND);
        }

        if (groupToBeAddedTo.contains(personToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPersonToGroup(personToAdd, groupToBeAddedTo);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToAdd)));
    }
}
