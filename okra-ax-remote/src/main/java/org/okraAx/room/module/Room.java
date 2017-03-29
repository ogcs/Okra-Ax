package org.okraAx.room.module;


import org.okraAx.room.fy.Player;

import java.util.Set;

/**
 * Game Room Interface. A room means an independent game.
 *
 * @author TinyZ.
 * @since 1.0
 */
public interface Room {

    /**
     * @return Return the room's unique ID.
     */
    long id();

    /**
     * Get the room's logic type.
     *
     * @return Return the room's type. room type means the unique game logic.
     */
    int type();

    /**
     * initialize the room. initialize the base room info or data before the game start.
     */
    void init();

    /**
     * Is the room is fully.
     *
     * @return Return true if the player count large than the {@link #maxPlayer()}
     */
    boolean isFully();

    /**
     * @return Return the max player count allowed by the game rules.
     */
    int maxPlayer();

    /**
     * Get all players.
     *
     * @return Return all players.
     */
    Set<Player> players();

    /**
     * Player join game.
     */
    void onEnter(Player player);

    /**
     * Player get ready or unready.
     * @param ready If true means player is get ready, otherwise false.
     */
    void onReady(Player player, boolean ready);

    /**
     * Player exit game.
     */
    void onExit(Long uid);

    /**
     * When the game is over. clear the game data or cache.
     */
    void onDestroy();
}
