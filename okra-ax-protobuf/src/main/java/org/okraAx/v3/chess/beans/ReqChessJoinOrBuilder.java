// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FyChessMessage.proto

package org.okraAx.v3.chess.beans;

public interface ReqChessJoinOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.okraAx.v3.ReqChessJoin)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *  昵称
   * </pre>
   *
   * <code>optional string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   *  昵称
   * </pre>
   *
   * <code>optional string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   *  房间唯一ID
   * </pre>
   *
   * <code>optional int64 roomId = 2;</code>
   */
  long getRoomId();
}
