package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.ui.Result;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Group> filteredGroups;
    private final ResultList results;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
        results = new ResultList(filteredPersons, filteredGroups);
    }


    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Result} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Result> getResultList() {
        return results.getObservableResults();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        results.setSource(ResultList.Source.Persons);
    }

    //=========== Filtered Group List Accessors =============================================================

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
        results.setSource(ResultList.Source.Groups);
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    @Override
    public void deleteGroup(Group target) {
        requireNonNull(target);
        addressBook.removeGroup(target);
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        addressBook.setGroup(target, editedGroup);
        showGroupDetails(editedGroup);
    }

    @Override
    public void addGroup(Group group) {
        requireNonNull(group);
        addressBook.addGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public void showGroupDetails(Group groupToShow) {
        requireNonNull(groupToShow);
        var details = groupToShow.getGroupDetails();
        results.setSource(ResultList.Source.GroupDetails, details);
    }

    @Override
    public Group getGroup(String groupName) {
        return addressBook.getGroup(groupName);
    }

    @Override
    public Person getPerson(String personName) {
        return addressBook.getPerson(personName);
    }

    @Override
    public Float getGrade(Person person, Group group, String assignmentName) {
        requireAllNonNull(person, group, assignmentName);
        return addressBook.getGrade(person, group, assignmentName);
    }

    @Override
    public boolean isPersonInGroup(Person person, Group group) {
        requireAllNonNull(person, group);
        return group.contains(person);
    }

    @Override
    public void addPersonToGroup(Person personToAdd, Group groupToBeAddedTo) {
        requireAllNonNull(personToAdd, groupToBeAddedTo);
        addressBook.addPersonToGroup(personToAdd, groupToBeAddedTo);
        showGroupDetails(groupToBeAddedTo);
    }

    @Override
    public void deletePersonFromGroup(Person personToRemove, Group groupToRemoveFrom) {
        requireAllNonNull(personToRemove, groupToRemoveFrom);
        addressBook.deletePersonFromGroup(personToRemove, groupToRemoveFrom);
        showGroupDetails(groupToRemoveFrom);
    }

    @Override
    public void deletePersonFromAllGroups(Person personToRemove) {
        requireNonNull(personToRemove);
        addressBook.deletePersonFromAllGroups(personToRemove);
    }

    @Override
    public Assignment addAssignmentToGroup(String assignmentName, LocalDate deadline, Group group, Float penalty) {
        requireAllNonNull(assignmentName, deadline, group, penalty);
        Assignment assignment = addressBook.addAssignmentToGroup(assignmentName, deadline, group, penalty);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return assignment;
    }

    @Override
    public void removeAssignmentFromGroup(String assignmentName, Group group) {
        requireAllNonNull(assignmentName, group);
        addressBook.removeAssignmentFromGroup(assignmentName, group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public void editAssignment(String assignmentName, String newName, LocalDate deadline, Group group, Float penalty) {
        requireAllNonNull(assignmentName, group);
        addressBook.editAssignment(assignmentName, newName, deadline, group, penalty);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public boolean isAssignmentInGroup(String assignmentName, Group group) {
        requireAllNonNull(assignmentName, group);
        return addressBook.isAssignmentInGroup(assignmentName, group);
    }

    @Override
    public void gradeAssignment(Person person, Group group, String assignmentName, Float score) {
        requireAllNonNull(person, group, assignmentName, score);
        addressBook.gradeAssignment(person, group, assignmentName, score);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public void markAttendance(Person person, Group group, int week) {
        requireAllNonNull(person, group, week);
        addressBook.markAttendance(person, group, week);
        showGroupDetails(group);
    }

    @Override
    public void unmarkAttendance(Person person, Group group, int week) {
        requireAllNonNull(person, group, week);
        addressBook.unmarkAttendance(person, group, week);
        showGroupDetails(group);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }
}
