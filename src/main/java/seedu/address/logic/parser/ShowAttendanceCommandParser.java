package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowAttendanceCommand object
 */
public class ShowAttendanceCommandParser implements Parser<ShowAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowAttendanceCommand
     * and returns a ShowAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowAttendanceCommand.MESSAGE_USAGE));
        }

        String personName = argMultimap.getValue(PREFIX_PERSON).get();
        String groupName = argMultimap.getValue(PREFIX_GROUP).get();

        return new ShowAttendanceCommand(personName, groupName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
