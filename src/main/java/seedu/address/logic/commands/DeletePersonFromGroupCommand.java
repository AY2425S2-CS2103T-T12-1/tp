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
 * Removes a specific Person from a specified Group
 */
public class DeletePersonFromGroupCommand extends Command {
    public static final String COMMAND_WORD = "delete-from-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a person from the group. "
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_PERSON + " Alex Yeoh "
            + PREFIX_GROUP + " Group 1";

    public static final String MESSAGE_SUCCESS = "Person removed from group: %1$s";
    public static final String MESSAGE_PERSON_NONEXIST = "This person does not exist in the group";
    /**
     * Index of Person to be added
     */
    private final String toDelete;
    /**
     * Index of Group to be added to
     */
    private final String toBeDeletedFrom;

    /**
     * Constructor for AddPersonToGroupCommand that takes two Index identifiers for
     * Person and Group
     *
      * @param toDelete Index of Person to be added
     * @param toBeDeletedFrom Index of Group to be added to
     */
    public DeletePersonFromGroupCommand(String toDelete, String toBeDeletedFrom) {
        requireNonNull(toDelete);
        requireNonNull(toBeDeletedFrom);
        this.toDelete = toDelete;
        this.toBeDeletedFrom = toBeDeletedFrom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        List<Group> groupList = model.getFilteredGroupList();

        Person personToDelete = null;
        Group groupToBeDeletedFrom = null;

        for (Person person : personList) {
            if (person.getName().fullName.equals(toDelete)) {
                personToDelete = person;
            }
        }
        for (Group group : groupList) {
            if (group.getGroupName().equals(toBeDeletedFrom)) {
                groupToBeDeletedFrom = group;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        if (groupToBeDeletedFrom == null) {
            throw new CommandException(Messages.MESSAGE_GROUP_NOT_FOUND);
        }

        if (groupToBeDeletedFrom.contains(personToDelete)) {
            model.deletePersonFromGroup(personToDelete, groupToBeDeletedFrom);
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToDelete)));
        } else {
            throw new CommandException(MESSAGE_PERSON_NONEXIST);
        }
    }
}
