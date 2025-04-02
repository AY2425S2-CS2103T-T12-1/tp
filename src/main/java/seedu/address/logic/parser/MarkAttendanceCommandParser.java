package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new {@code MarkAttendanceCommand} object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkAttendance}
     * and returns an {@code MarkAttendance} object for execution.
     *
     * @param args The user input arguments as a {@code String}.
     * @return An {@code MarkAttendanceCommand} object containing the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_GROUP, PREFIX_WEEK);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_GROUP, PREFIX_WEEK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON, PREFIX_GROUP, PREFIX_WEEK);
        String personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get()).toString();
        String groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP).get());
        int week = Integer.parseInt(argMultimap.getValue(PREFIX_WEEK).get());
        return new MarkAttendanceCommand(personName, groupName, week);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
