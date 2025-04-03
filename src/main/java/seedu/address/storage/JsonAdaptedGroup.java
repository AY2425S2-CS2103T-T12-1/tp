package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.model.AddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String name;

    private final ArrayListMap<String, JsonAdaptedGroupMemberDetails> groupMembers = new ArrayListMap<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final ArrayList<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String name,
                            @JsonProperty("persons") ArrayListMap<String, JsonAdaptedGroupMemberDetails>
                                    persons,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.name = name;
        if (persons != null) {
            this.groupMembers.putAll(persons);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        name = source.getGroupName();
        for (Map.Entry<Person, GroupMemberDetail> entry : source.getGroupMembersMap().entrySet()) {
            String key = entry.getKey().getName().fullName;
            JsonAdaptedGroupMemberDetails value = new JsonAdaptedGroupMemberDetails(entry.getValue());
            this.groupMembers.put(key, value);
        }
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        assignments.addAll(source.getAssignments().stream()
                .map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Group toModelType(AddressBook addressBook) throws IllegalValueException {
        final ArrayListMap<Person, GroupMemberDetail> modelGroupMembers = new ArrayListMap<>();
        for (Map.Entry<String, JsonAdaptedGroupMemberDetails> entry : groupMembers.entrySet()) {
            String personName = entry.getKey();
            GroupMemberDetail groupMemberDetail = entry.getValue().toModelType();
            Person person;
            try {
                person = addressBook.getPerson(personName);
                modelGroupMembers.put(person, groupMemberDetail);
            } catch (PersonNotFoundException e) {
                // Person not found in addressbook, remove from group as well.
            }
        }

        final List<Tag> modelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Group.isValidGroupName(name)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        final String modelName = name;

        final List<Assignment> modelAssignments = new ArrayList<>();
        for (JsonAdaptedAssignment assignment : assignments) {
            modelAssignments.add(assignment.toModelType());
        }

        Group modelGroup = new Group(modelName, modelGroupMembers, modelTags, modelAssignments);
        // Set all GroupMemberDetail.group to this
        for (GroupMemberDetail value : modelGroupMembers.values()) {
            value.setGroup(modelGroup);
        }
        return modelGroup;
    }
}
