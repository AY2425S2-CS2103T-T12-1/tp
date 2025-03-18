package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * Represents an item in the result list in the UI.
 */
public interface Result {
    /**
     * Returns a custom graphic that displays the contents of this result in a ListView.
     *
     * @param displayedIndex the index of the item in the list.
     * @return the custom graphic.
     */
    public UiPart<Region> createCard(int displayedIndex);
}
