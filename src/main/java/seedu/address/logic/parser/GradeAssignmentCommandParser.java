package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradeAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input and create a new {@code GradeAssignmentCommand} class
 */
public class GradeAssignmentCommandParser implements Parser<GradeAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code GradeAssignmentCommand}
     * and returns an {@code GradeAssignmentCommand} object for execution.
     *
     * @param args The user input arguments as a {@code String}.
     * @return An {@code GradeAssignmentCommand} object containing the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public GradeAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_GROUP,
                PREFIX_ASSIGNMENT, PREFIX_SCORE);
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_GROUP, PREFIX_ASSIGNMENT, PREFIX_SCORE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON, PREFIX_GROUP, PREFIX_ASSIGNMENT, PREFIX_SCORE);
        String personName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get()).toString();
        String groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP).get());
        String assignmentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_ASSIGNMENT).get()).toString();
        Float score = Float.parseFloat(argMultimap.getValue(PREFIX_SCORE).get());
        return new GradeAssignmentCommand(personName, groupName, assignmentName, score);

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
