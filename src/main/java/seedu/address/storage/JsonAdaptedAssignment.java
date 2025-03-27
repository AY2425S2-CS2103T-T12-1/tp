package seedu.address.storage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Name;


/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private String name;
    private Date deadline;

    /**
     * Creates a {@code JsonAdaptedAssignment} from the given details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("name") String name, @JsonProperty("date") Date deadline) {
        this.name = name;
        this.deadline = deadline;
    }
    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this.name = source.getName();
        this.deadline = source.getDeadline();
    }
    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Assignment.isValidName(name)) {
            throw new IllegalValueException(Assignment.MESSAGE_CONSTRAINTS);
        }
        final String modelName = name;
        final Date modelDate = deadline;
        return new Assignment(modelName, modelDate);
    }
}
