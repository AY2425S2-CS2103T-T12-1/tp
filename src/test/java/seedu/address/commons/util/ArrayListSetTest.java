package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ArrayListSetTest {
    private ArrayListSet<Person> persons;

    @BeforeEach
    public void setUp() {
        persons = new ArrayListSet<>();
    }

    @Test
    public void init_success() {
        assertEquals(0, persons.size());
        assertTrue(persons.isEmpty());
    }

    @Test
    public void add_success() {
        Person p = new PersonBuilder().build();
        boolean added = persons.add(p);

        assertTrue(added);
        assertEquals(1, persons.size());
        assertTrue(persons.contains(p));
    }

    @Test
    public void add_duplicatePerson_doesNotChangeTheSet() {
        Person p = new PersonBuilder().build();
        persons.add(p);
        boolean readded = persons.add(p);

        assertFalse(readded);
        assertEquals(1, persons.size());
    }

    @Test
    public void remove_validPerson_success() {
        Person p = new PersonBuilder().build();
        persons.add(p);

        int oldSize = persons.size();
        boolean removed = persons.remove(p);
        int newSize = persons.size();

        assertTrue(removed);
        assertFalse(persons.contains(p));
        assertEquals(oldSize - 1, newSize);
    }

    @Test
    public void remove_invalidPerson_doesNotChangeTheSet() {
        Person p = new PersonBuilder().build();
        persons.add(p);

        Person p2 = new PersonBuilder().withName("New name 1").build();
        int oldSize = persons.size();
        boolean removed = persons.remove(p2);
        int newSize = persons.size();

        assertFalse(removed);
        assertTrue(persons.contains(p));
        assertFalse(persons.contains(p2));
        assertEquals(oldSize, newSize);
    }

    @Test
    public void remove_validIndex_success() {
        Person p = new PersonBuilder().build();
        persons.add(p);

        int oldSize = persons.size();
        boolean removed = persons.remove(0);
        int newSize = persons.size();

        assertTrue(removed);
        assertFalse(persons.contains(p));
        assertEquals(oldSize - 1, newSize);
    }

    @Test
    public void remove_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> persons.remove(persons.size()));
        assertThrows(IndexOutOfBoundsException.class, () -> persons.remove(-1));
    }

    @Test
    public void indexOf_success() {
        Person p = new PersonBuilder().build();
        persons.add(p);
        Person p2 = new PersonBuilder().withName("New name 1").build();
        persons.add(p2);

        int pIndex = persons.indexOf(p);
        int p2Index = persons.indexOf(p2);

        assertEquals(0, pIndex);
        assertEquals(1, p2Index);

        Person p3 = new PersonBuilder().withName("New name 2").build();
        int p3Index = persons.indexOf(p3);

        assertEquals(-1, p3Index);
    }

    @Test
    public void clear_success() {
        Person p = new PersonBuilder().build();
        persons.add(p);

        persons.clear();

        assertEquals(0, persons.size());
        assertTrue(persons.isEmpty());
    }
}
