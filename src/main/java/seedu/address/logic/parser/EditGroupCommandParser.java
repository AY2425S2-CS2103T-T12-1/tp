package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code EditGroupCommand} object.
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditGroupCommand}
     * and returns an {@code EditGroupCommand} object for execution.
     *
     * @param args The user input arguments as a {@code String}.
     * @return An {@code EditGroupCommand} object containing the parsed index and new group name.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public EditGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGroupCommand.MESSAGE_USAGE), ive);
        }

        String newGroupName = argMultimap.getValue(PREFIX_NAME).orElse("");

        return new EditGroupCommand(index, newGroupName);
    }
}
