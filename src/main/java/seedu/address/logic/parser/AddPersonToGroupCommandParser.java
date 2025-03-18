package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPersonToGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and create a new AddPersonToGroupCommand
 */
public class AddPersonToGroupCommandParser implements Parser<AddPersonToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of AddPersonToGroupCommand
     * and returns a AddPersonToGroupCommand object for execution
     */
    @Override
    public AddPersonToGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_GROUP)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToGroupCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON, PREFIX_GROUP);
        Index personIndex = parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
        Index groupIndex = parseIndex(argMultimap.getValue(PREFIX_GROUP).get());

        return new AddPersonToGroupCommand(personIndex, groupIndex);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
