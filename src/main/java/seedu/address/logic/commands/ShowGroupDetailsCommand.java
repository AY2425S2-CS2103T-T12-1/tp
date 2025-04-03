package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Shows the main details regarding a {@code Group}.
 */
public class ShowGroupDetailsCommand extends Command {

    public static final String COMMAND_WORD = "show-group-details";

    public static final String MESSAGE_USAGE = String.format("""
            %s: Shows all the core details regarding the specified group.
            Parameters: INDEX
            Notes:
            * Shows details including:
              * Group name and tags
              * Number of group members
              * Name, role, and attendance of every group member
            * INDEX refers to the index number of the group in the last displayed group list."""
            + """
             It must be a positive integer, i.e., 1, 2, 3, ...
            Example: %s 2
            """, COMMAND_WORD, COMMAND_WORD);

    private static final String MESSAGE_SHOW_GROUP_DETAILS_SUCCESS = "Showing details for group:\n%1$s";

    private final Index targetIndex;

    /**
     * Creates a new ShowGroupDetailsCommand.
     *
     * @param targetIndex The index of the group to show. This refers to the index in the last displayed group list.
     */
    public ShowGroupDetailsCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToShow = lastShownList.get(targetIndex.getZeroBased());
        model.showGroupDetails(groupToShow);
        return new CommandResult(String.format(MESSAGE_SHOW_GROUP_DETAILS_SUCCESS, Messages.format(groupToShow)),
                true, groupToShow);
    }
}
