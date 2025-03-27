package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupMemberDetail;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class ArrayListMapTest {
    private ArrayListMap<Person, GroupMemberDetail> members;
    private Person p;
    private Group g;
    private GroupMemberDetail detail;

    @BeforeEach
    public void setUp() {
        members = new ArrayListMap<>();
        p = new PersonBuilder().build();
        g = new GroupBuilder().build();
        detail = new GroupMemberDetail(p, g);
    }

    @Test
    public void init_success() {
        assertEquals(0, members.size());
        assertTrue(members.isEmpty());
    }

    @Test
    public void put_success() {
        GroupMemberDetail prev = members.put(p, detail);

        assertNull(prev);
        assertFalse(members.isEmpty());
        assertEquals(1, members.size());
        assertTrue(members.containsKey(p));
        assertEquals(detail, members.get(p));
    }

    @Test
    public void put_existingKey_success() {
        members.put(p, detail);

        Group g2 = new GroupBuilder().withName("New Name").build();
        GroupMemberDetail detail2 = new GroupMemberDetail(p, g2);
        GroupMemberDetail prev = members.put(p, detail2);

        assertEquals(detail, prev);
        assertTrue(members.containsKey(p));
        assertEquals(detail2, members.get(p));
        assertEquals(1, members.size());
    }

    @Test
    public void keySet_success() {
        members.put(p, detail);

        var keySet = members.keySet();

        assertTrue(keySet.contains(p));
        assertEquals(1, members.size());
    }

    @Test
    public void containsKey_exists_success() {
        members.put(p, detail);

        assertTrue(members.containsKey(p));
    }

    @Test
    public void containsKey_doesNotExist_success() {
        assertFalse(members.containsKey(p));
    }
}
