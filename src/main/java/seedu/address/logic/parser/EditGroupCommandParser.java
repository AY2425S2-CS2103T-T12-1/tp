package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code EditGroupCommand} object.
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code EditGroupCommand}
     * and returns an {@code EditGroupCommand} object for execution.
     *
     * @param args The user input arguments as a {@code String}.
     * @return An {@code EditGroupCommand} object containing the parsed index and
     *     new group name.
     * @throws ParseException If the user input does not conform to the expected
     *                        format.
     */
    public EditGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGroupCommand.MESSAGE_USAGE), ive);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TAG);

        String newGroupName = "";
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            newGroupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_NAME).get());
        }

        Collection<Tag> tags = null;
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tags = ParserUtil.parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).get();
        }

        return new EditGroupCommand(index, newGroupName, tags);
    }
}
