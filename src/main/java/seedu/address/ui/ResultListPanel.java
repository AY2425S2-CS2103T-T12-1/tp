package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of results.
 */
public class ResultListPanel extends UiPart<Region> {
    private static final String FXML = "ResultListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ResultListPanel.class);

    @FXML
    private ListView<Result> resultListView;

    /**
     * Creates a {@code ResultListPanel} with the given {@code ObservableList}.
     */
    public ResultListPanel(ObservableList<Result> resultList) {
        super(FXML);
        resultListView.setItems(resultList);
        resultListView.setCellFactory(listView -> new ResultListViewCell());
    }

    public void refresh() {
        resultListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Result}.
     */
    class ResultListViewCell extends ListCell<Result> {
        @Override
        protected void updateItem(Result result, boolean empty) {
            super.updateItem(result, empty);

            if (empty || result == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(result.createCard(getIndex() + 1).getRoot());
            }
        }
    }

}
