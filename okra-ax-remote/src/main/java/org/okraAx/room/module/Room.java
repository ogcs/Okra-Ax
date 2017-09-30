package org.okraAx.room.module;


import org.okraAx.room.fy.RemoteUser;

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
     * Return the room's status.
     */
    int status();

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
    Set<RemoteUser> players();

    /**
     * Player join game.
     */
    boolean onEnter(RemoteUser user);

    /**
     * Join room and sit in the designated position.
     *
     * @param user     {@link RemoteUser}
     * @param position the designated position.
     */
    boolean onEnterWithPosition(RemoteUser user, int position);

    /**
     * Player get ready or unready.
     *
     * @param ready If true means player is get ready, otherwise false.
     */
    void onReady(RemoteUser user, boolean ready);

    void onGameStart();

    /**
     * Player exit game.
     */
    void onExit(Long uid);

    void onGameEnd();

    /**
     * When the game is over. clear the game data or cache.
     */
    void onDestroy();
}
