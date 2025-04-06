package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LATE_PENALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_NAME;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAssignmentCommand object.
 */
public class EditAssignmentCommandParser implements Parser<EditAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAssignmentCommand
     * and returns an EditAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP, PREFIX_NEW_NAME,
                PREFIX_DATE, PREFIX_LATE_PENALTY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GROUP, PREFIX_NEW_NAME, PREFIX_DATE);
        String assignmentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).toString();
        String groupName = ParserUtil.parseName(argMultimap.getValue(PREFIX_GROUP).get()).toString();
        String newName = null;
        if (argMultimap.getValue(PREFIX_NEW_NAME).isPresent()) {
            newName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NEW_NAME).get()).toString();
        }
        LocalDate deadline = null;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            deadline = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }
        Float penalty = null;
        if (argMultimap.getValue(PREFIX_LATE_PENALTY).isPresent()) {
            penalty = ParserUtil.parsePenalty(argMultimap.getValue(PREFIX_LATE_PENALTY).get());
        }
        if (!CollectionUtil.isAnyNonNull(newName, deadline, penalty)) {
            throw new ParseException(EditAssignmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAssignmentCommand(assignmentName, groupName, newName, deadline, penalty);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
