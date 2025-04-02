package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represent a command that will grade a student's assignment for that group
 */
public class GradeAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "grade-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grade the assignment of Person in a Group "
            + "by their name, their group and the assignment name as well as a float score"
            + "Parameters: "
            + PREFIX_PERSON + "NAME "
            + PREFIX_GROUP + "GROUP NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT NAME "
            + PREFIX_SCORE + "SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "John Doe "
            + PREFIX_GROUP + "CS2103T "
            + PREFIX_ASSIGNMENT + "HW 1 "
            + PREFIX_SCORE + "70.3";
    public static final String MESSAGE_GRADE_ASSIGNMENT_SUCCESS = "Graded Assignment %s for %s, %s with %.2f score";
    private final String personName;
    private final String groupName;
    private final String assignmentName;
    private final Float score;

    /**
     * Creates a {@code GradeAssignmentCommand} to grade student assignment.
     *
     * @param personName        Name of the person.
     * @param groupName         Name of the group.
     * @param assignmentName    Name of the assignment.
     * @param score             Score that person obtained.
     */
    public GradeAssignmentCommand(String personName, String groupName, String assignmentName, Float score) {
        requireAllNonNull(personName, groupName, assignmentName, score);
        this.personName = personName;
        this.groupName = groupName;
        this.assignmentName = assignmentName;
        this.score = score;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person;
        try {
            person = model.getPerson(personName);
        } catch (PersonNotFoundException e) {
            throw new CommandException("Person not found!");
        }
        Group group;
        try {
            group = model.getGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException("Group not found!");
        }
        model.gradeAssignment(person, group, this.assignmentName, this.score);
        Float finalScore = model.getGrade(person, group, this.assignmentName);
        return new CommandResult(String.format(MESSAGE_GRADE_ASSIGNMENT_SUCCESS, assignmentName, personName,
                groupName, finalScore));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GradeAssignmentCommand otherCmd)) {
            return false;
        }
        return personName.equals(otherCmd.personName)
                && groupName.equals(otherCmd.groupName)
                && assignmentName.equals(otherCmd.assignmentName)
                && score.equals(otherCmd.score);
    }
}
