package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.ui.Result;

/**
 * Model containing the data backing the result list in the UI.
 */
public class ResultList {
    /**
     * Possible sources for the results in the list.
     */
    public enum Source {
        Persons,
        Groups,
    }

    private final ObservableList<Person> persons;
    private final ObservableList<Group> groups;
    private final ObservableList<Result> results;
    private Source source;

    /**
     * Creates a new ResultList backed by the given ObservableLists.
     *
     * @param persons The backing list of persons.
     * @param groups  The backing list of groups.
     */
    public ResultList(ObservableList<Person> persons, ObservableList<Group> groups) {
        this.persons = persons;
        this.groups = groups;

        // Start by showing persons by default.
        this.source = Source.Persons;
        this.results = FXCollections.observableArrayList(persons);

        persons.addListener((ListChangeListener<Person>) c -> {
            if (this.source == Source.Persons) {
                processListChange(c);
            }
        });
        groups.addListener((ListChangeListener<Group>) c -> {
            if (this.source == Source.Groups) {
                processListChange(c);
            }
        });
    }

    public ObservableList<Result> getObservableResults() {
        return results;
    }

    public void setSource(Source source) {
        this.source = source;
        results.setAll(source == Source.Persons ? persons : groups);
    }

    private void processListChange(ListChangeListener.Change<? extends Result> c) {
        while (c.next()) {
            if (c.wasRemoved()) {
                results.remove(c.getFrom(), c.getFrom() + c.getRemovedSize());
            }
            if (c.wasAdded()) {
                results.addAll(c.getFrom(), c.getAddedSubList());
            }
            if (c.wasUpdated()) {
                results.removeAll(c.getRemoved());
                results.addAll(c.getAddedSubList());
            }
        }
    }
}
