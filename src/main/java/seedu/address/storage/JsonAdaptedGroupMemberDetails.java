package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.group.GroupMemberDetail.Role;
import seedu.address.model.person.Person;
/**
 * Jackson-friendly version of {@link GroupMemberDetail}.
 */
public class JsonAdaptedGroupMemberDetails {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "GroupMember's %s field is missing!";

    private JsonAdaptedPerson person;
    private Role role;
    private List<Boolean> attendance = new ArrayList<>();
    private ArrayListMap<String, Float> grades = new ArrayListMap<>();
    private ArrayListMap<String, JsonAdaptedAssignment> assignments = new ArrayListMap<>();

    /**
     * Constructs a {@code JsonAdaptedGroupMemberDetails} from the given details.
     */
    @JsonCreator
    public JsonAdaptedGroupMemberDetails(@JsonProperty("person") JsonAdaptedPerson person,
                                         @JsonProperty("Role") Role role,
                                         @JsonProperty("attendance") List<Boolean> attendance,
                                         @JsonProperty("grades")
                                         ArrayListMap<String, Float> grades,
                                         @JsonProperty("assignments")
                                         ArrayListMap<String, JsonAdaptedAssignment> assignments) {
        this.person = person;
        this.role = role;
        if (attendance != null) {
            this.attendance.addAll(attendance);
        }
        if (grades != null) {
            this.grades.putAll(grades);
        }
        if (assignments != null) {
            this.assignments.putAll(assignments);
        }
    }

    /**
     * Converts a given {@code GroupMemberDetail} into this class for Jackson use.
     */
    public JsonAdaptedGroupMemberDetails(GroupMemberDetail source) {
        this.person = new JsonAdaptedPerson(source.getPerson());
        this.role = source.getRole();
        for (boolean attendance : source.getAttendance()) {
            this.attendance.add(attendance);
        };
        for (Map.Entry<Assignment, Float> entry : source.getGrades().entrySet()) {
            Float valueFloat = entry.getValue();
            JsonAdaptedAssignment valueAssignment = new JsonAdaptedAssignment(entry.getKey());
            String key = entry.getKey().getName();
            this.grades.put(key, valueFloat);
            this.assignments.put(key, valueAssignment);
        }
    }
    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code GroupMemberDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public GroupMemberDetail toModelType() throws IllegalValueException {
        if (this.person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        Person modelPerson = this.person.toModelType();
        Role modelRole = this.role;
        boolean[] modelAttendance = new boolean[GroupMemberDetail.WEEKS_PER_SEMESTER];
        for (int i = 0; i < GroupMemberDetail.WEEKS_PER_SEMESTER; i++) {
            modelAttendance[i] = this.attendance.get(i);
        }
        ArrayListMap<Assignment, Float> modelGrade = new ArrayListMap<>();

        for (Map.Entry<String, JsonAdaptedAssignment> entry : this.assignments.entrySet()) {
            Assignment key = entry.getValue().toModelType();
            Float value = this.grades.get(entry.getKey());
            modelGrade.put(key, value);
        }
        return new GroupMemberDetail(modelPerson, modelRole, modelAttendance, modelGrade);
    }
}
