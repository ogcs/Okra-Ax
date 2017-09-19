package org.okraAx.internal.core;

/**
 * The changeable object interface use to defined if the object is changeable.
 * <pre>
 *     接口用于定义和区分Java对象是否是易变的.
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.07.20
 */
public interface Changeable {

    /**
     * Is the object changed.
     *
     * @return return true if the java object is changed. otherwise false.
     */
    boolean isChanged();

    /**
     * set the changeable status.
     *
     * @param status the changeable status.
     */
    void setChanged(boolean status);

}
