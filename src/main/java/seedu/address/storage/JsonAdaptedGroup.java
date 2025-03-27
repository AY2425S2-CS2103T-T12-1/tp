package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ArrayListMap;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String name;

    private final ArrayListMap<String, JsonAdaptedGroupMemberDetails> groupMembers = new ArrayListMap<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String name,
                            @JsonProperty("persons") ArrayListMap<String, JsonAdaptedGroupMemberDetails>
                                    persons,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        if (persons != null) {
            this.groupMembers.putAll(persons);
        }
        if (tags != null) {
            this.tags.addAll(tags);
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
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Group toModelType() throws IllegalValueException {
        final ArrayListMap<Person, GroupMemberDetail> modelGroupMembers = new ArrayListMap<>();
        for (Map.Entry<String, JsonAdaptedGroupMemberDetails> entry : groupMembers.entrySet()) {
            Person key = entry.getValue().toModelType().getPerson();
            GroupMemberDetail value = entry.getValue().toModelType();
            modelGroupMembers.put(key, value);
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

        return new Group(modelName, modelGroupMembers, modelTags);
    }

}

