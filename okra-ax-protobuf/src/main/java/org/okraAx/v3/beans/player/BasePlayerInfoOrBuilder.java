// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BeanPlayer.proto

package org.okraAx.v3.beans.player;

public interface BasePlayerInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.okraAx.v3.BasePlayerInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *  玩家唯一ID
   * </pre>
   *
   * <code>optional int64 uid = 1;</code>
   */
  long getUid();

  /**
   * <pre>
   *  角色名
   * </pre>
   *
   * <code>optional string name = 2;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   *  角色名
   * </pre>
   *
   * <code>optional string name = 2;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   *  形象
   * </pre>
   *
   * <code>optional int32 figure = 3;</code>
   */
  int getFigure();
}