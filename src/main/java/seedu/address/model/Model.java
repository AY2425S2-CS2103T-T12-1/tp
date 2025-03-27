package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.ui.Result;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the result list
     */
    ObservableList<Result> getResultList();

    boolean hasGroup(Group group);

    /**
     * Deletes the given group.
     * The group must exist in the address book.
     */
    void deleteGroup(Group target);

    void setGroup(Group target, Group editedGroup);

    /**
     * Returns a unmodifiable view of the filtered group list
     */
    ObservableList<Group> getFilteredGroupList();


    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Adds the given group.
     * The group must not already exist in the address book.
     */
    void addGroup(Group group);

    /**
     * Shows all the details of the specified group.
     *
     * @param groupToShow The group whose details should be shown.
     */
    void showGroupDetails(Group groupToShow);

    /**
     * Adds the given person to the given group.
     * The person must not already exist in the group.
     */
    void addPersonToGroup(Person personToAdd, Group groupToBeAddedTo);

    /**
     * Removes the given person from the given group.
     * The person must exist in the group.
     */
    void deletePersonFromGroup(Person personToRemove, Group groupToRemoveFrom);

    /**
     * Removes the given person from the all groups they were in.
     */
    void deletePersonFromAllGroups(Person personToRemove);

    /**
     * Retrieves a group matching the provided group name.
     */
    Group getGroup(String groupName);

    /**
     * Retrieves a person matching the provided person name.
     */
    Person getPerson(String personName);
}
