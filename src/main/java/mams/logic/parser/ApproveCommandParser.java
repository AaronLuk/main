package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CliSyntax.PREFIX_REMARK;


import mams.commons.core.index.Index;
import mams.commons.exceptions.IllegalValueException;
import mams.logic.commands.ApproveCommand;
import mams.logic.parser.exceptions.ParseException;


public class ApproveCommandParser implements Parser<ApproveCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApproveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApproveCommand.MESSAGE_USAGE), ive);
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new ApproveCommand(index, remark);
    }
}
