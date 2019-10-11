package mams.logic.commands;

import static java.util.Objects.requireNonNull;
import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.appeal.Appeal;

import java.util.List;


/**
 * Approves an appeal and marks it as resolved
 */
public class ApproveCommand extends Command {

    public static final String COMMAND_WORD = "approve";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_APPROVE_APPEAL_SUCCESS = "Approved appeal: %1$s";
    public static final String MESSAGE_ALREADY_APPROVED_APPEAL = "Appeal %1$s was approved already";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Approves the appeal identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Module has reached full capacity and is not able to take anymore students.";


    private final Index index;
    private final String remark;


    public ApproveCommand(Index index, String remark){
        requireNonNull(index);

        this.index = index;
        this.remark = remark;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Appeal> lastShownList = model.getFilteredAppealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
        }

        Appeal appealToApprove = lastShownList.get(index.getZeroBased());

        if (!appealToApprove.getResult().equals("APPROVED")) {
            Appeal approvedAppeal = new Appeal(appealToApprove.getAppealId(), appealToApprove.getAppealType(), appealToApprove.getStudentId(), appealToApprove.getAcademicYear(), appealToApprove.getStudentWorkload(), appealToApprove.getAppealDescription(),
                    appealToApprove.getPreviousModule(), appealToApprove.getNewModule(), appealToApprove.getModule_to_add(), appealToApprove.getModule_to_drop(), true, "APPROVED",remark);

            model.setAppeal(appealToApprove, approvedAppeal);
            model.updateFilteredAppealList(model.PREDICATE_SHOW_ALL_APPEALS);
            return new CommandResult(String.format(MESSAGE_APPROVE_APPEAL_SUCCESS, approvedAppeal));
        } else {
            return new CommandResult(String.format(MESSAGE_ALREADY_APPROVED_APPEAL, appealToApprove));
        }


    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApproveCommand)) {
            return false;
        }

        // state check
        ApproveCommand e = (ApproveCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
