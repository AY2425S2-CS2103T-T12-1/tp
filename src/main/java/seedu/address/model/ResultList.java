package seedu.address.model;

import java.util.Collection;

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
        GroupDetails,
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
            processListChange(c, Source.Persons);
        });
        groups.addListener((ListChangeListener<Group>) c -> {
            processListChange(c, Source.Groups);
        });
    }

    public ObservableList<Result> getObservableResults() {
        return results;
    }

    public void setSource(Source source) {
        this.source = source;
        switch (source) {
        case Persons:
            results.setAll(persons);
            break;
        case Groups:
            results.setAll(groups);
            break;
        case GroupDetails:
            // Use ResultList#setSource(Source, Collection<Result>) instead.
        default:
            throw new IllegalArgumentException("Invalid source");
        }
    }

    public void setSource(Source source, Collection<? extends Result> results) {
        switch (source) {
        case GroupDetails:
            this.results.setAll(results);
            break;
        case Persons:
        case Groups:
            // Use ResultList#setSource(Source) instead.
        default:
            throw new IllegalArgumentException("Invalid source");
        }
        this.source = source;
    }

    private void processListChange(ListChangeListener.Change<? extends Result> c, Source expectedSource) {
        if (this.source != expectedSource) {
            return;
        }
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
