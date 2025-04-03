package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LATE_PENALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object.
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAssignmentCommand
     * and returns an AddAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP,
                PREFIX_DATE, PREFIX_LATE_PENALTY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUP, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GROUP, PREFIX_DATE, PREFIX_LATE_PENALTY);
        String assignmentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).toString();
        String groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP).get());
        LocalDate deadline = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Float penalty = null;
        if (argMultimap.getValue(PREFIX_LATE_PENALTY).isPresent()) {
            penalty = Float.parseFloat(argMultimap.getValue(PREFIX_LATE_PENALTY).get());
        }

        return new AddAssignmentCommand(assignmentName, groupName, deadline, penalty);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

