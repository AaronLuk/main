package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;

import mams.commons.util.StringUtil;
import mams.logic.commands.ClashCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClashCommand object
 */
public class ClashCommandParser implements Parser<ClashCommand> {

    @Override
    public ClashCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE, PREFIX_APPEAL);
        ClashCommand.ClashCommandParameters parameters = new ClashCommand.ClashCommandParameters();

        if (argMultimap.areAllPrefixesAbsent(PREFIX_APPEAL, PREFIX_MODULE, PREFIX_STUDENT)
                || !hasOnlyOneField(argMultimap)) {
            throw new ParseException(ClashCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getValue(PREFIX_APPEAL).isPresent() && argMultimap.getValueSize(PREFIX_APPEAL) == 1) {
            parameters.setAppealIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APPEAL).get()));
        } else if (argMultimap.getValue(PREFIX_MODULE).isPresent() && argMultimap.getValueSize(PREFIX_MODULE) == 2) {
            List<String> modules = argMultimap.getAllValues(PREFIX_MODULE);
            if (isModuleCode(modules)) {
                parameters.setModuleCodes(modules.get(0), modules.get(1));
            } else {
                parameters.setModuleIndices(ParserUtil.parseIndex(modules.get(0)),
                        ParserUtil.parseIndex(modules.get(1)));
            }
        } else if (argMultimap.getValue(PREFIX_STUDENT).isPresent() && argMultimap.getValueSize(PREFIX_STUDENT) == 1) {
            parameters.setStudentIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get()));
        } else {
            throw new ParseException(ClashCommand.MESSAGE_USAGE);
        }

        return new ClashCommand(parameters);
    }

    /**
     * Returns true if PREFIX_MODULE comes with module codes, not indices.
     * @param modules List of inputs after PREFIX_MODULE
     * @return true if PREFIX_MODULE comes with module codes, not indices
     */
    private boolean isModuleCode(List<String> modules) {
        return modules.get(0).toLowerCase().contains("cs");
    }

    /**
     * Returns true if only 1 field is present since clash command is only able to clash 1 type of clash cases
     * each time.
     * @param argMultimap an ArgumentMultimap object
     * @return true if only 1 field is present
     */
    private boolean hasOnlyOneField(ArgumentMultimap argMultimap) {
        return (argMultimap.getValue(PREFIX_MODULE).isPresent()
                && argMultimap.getValue(PREFIX_STUDENT).isEmpty()
                && argMultimap.getValue(PREFIX_APPEAL).isEmpty())
                || (argMultimap.getValue(PREFIX_MODULE).isEmpty()
                        && argMultimap.getValue(PREFIX_STUDENT).isPresent()
                        && argMultimap.getValue(PREFIX_APPEAL).isEmpty())
                || (argMultimap.getValue(PREFIX_MODULE).isEmpty()
                        && argMultimap.getValue(PREFIX_STUDENT).isEmpty()
                        && argMultimap.getValue(PREFIX_APPEAL).isPresent());
    }
}

