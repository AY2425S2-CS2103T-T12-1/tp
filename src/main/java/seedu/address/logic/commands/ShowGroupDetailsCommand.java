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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the details of the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_SHOW_GROUP_DETAILS_SUCCESS = "Showing details for Group: %1$s";

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
        return new CommandResult(String.format(MESSAGE_SHOW_GROUP_DETAILS_SUCCESS, Messages.format(groupToShow)));
    }
}
