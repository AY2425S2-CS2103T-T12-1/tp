package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a Person to a Group
 */
public class AddPersonToGroupCommand extends Command {
    public static final String COMMAND_WORD = "add-to-group";

    public static final String MESSAGE_USAGE = String.format("""
            %s: Adds the specified person to the specified group.
            Parameters: %sPERSON_NAME %sGROUP_NAME
            Example: add-to-group P/Jensen Huang g/CS2103T T12
            """, COMMAND_WORD, PREFIX_PERSON, PREFIX_GROUP);

    public static final String MESSAGE_SUCCESS = """
            Added person to group:
            Person: %1$s
            Group: %2$s""";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the group!";

    /**
     * Index of Person to be added
     */
    private final String personName;
    /**
     * Index of Group to be added to
     */
    private final String groupName;

    /**
     * Constructor for AddPersonToGroupCommand that takes two Index identifiers for
     * Person and Group
     *
      * @param personName Index of Person to be added
     * @param groupName Index of Group to be added to
     */
    public AddPersonToGroupCommand(String personName, String groupName) {
        requireNonNull(personName);
        requireNonNull(groupName);
        this.personName = personName;
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person;
        try {
            person = model.getPerson(this.personName);
        } catch (PersonNotFoundException e) {
            throw new CommandException("Person not found!");
        }
        Group group;
        try {
            group = model.getGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException("Group not found!");
        }

        if (model.isPersonInGroup(person, group)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPersonToGroup(person, group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person), Messages.format(group)));
    }
}
