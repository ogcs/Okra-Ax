package org.okraAx.common;

/**
 * the room module's service.
 *
 * @author TinyZ
 * @version 2017.03.12
 */
public interface RoomService {

    //  service

    void enterChannel();

    //  callback

    void callbackVerifyPlayerAuth(int ret);

    void callbackRegister(int ret);

}
