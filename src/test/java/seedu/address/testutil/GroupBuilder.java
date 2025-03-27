package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import seedu.address.model.group.Group;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {
    public static final String DEFAULT_NAME = "CS2101 T12";

    private String name;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        name = DEFAULT_NAME;
    }

    /**
     * Sets the {@code Name} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String name) {
        requireNonNull(name);
        this.name = name;
        return this;
    }

    public Group build() {
        return new Group(name);
    }
}
