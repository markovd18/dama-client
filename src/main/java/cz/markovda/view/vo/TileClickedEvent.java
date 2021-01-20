package cz.markovda.view.vo;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Event that is fired when a tile in the game board is clicked.
 *
 * @author David Markov
 * @since 19. 1. 2021
 */
public class TileClickedEvent extends Event {

    public static final EventType<TileClickedEvent> TILE_CLICKED =
            new EventType<>(Event.ANY, "TILE_CLICKED");

    private final int x;
    private final int y;

    public TileClickedEvent(EventType<? extends Event> eventType, final int x, final int y) {
        super(eventType);
        this.x = x;
        this.y = y;
    }

    public TileClickedEvent(Object o, EventTarget eventTarget, EventType<? extends Event> eventType, final int x, final int y) {
        super(o, eventTarget, eventType);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
