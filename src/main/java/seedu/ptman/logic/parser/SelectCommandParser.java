package seedu.ptman.logic.parser;

import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ptman.commons.core.index.Index;
import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.logic.commands.SelectCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
