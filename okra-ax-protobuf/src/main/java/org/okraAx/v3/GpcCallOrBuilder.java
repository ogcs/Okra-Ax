// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Gpc.proto

package org.okraAx.v3;

public interface GpcCallOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.okraAx.v3.GpcCall)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *  the remote call produce name.
   * </pre>
   *
   * <code>optional string method = 1;</code>
   */
  java.lang.String getMethod();
  /**
   * <pre>
   *  the remote call produce name.
   * </pre>
   *
   * <code>optional string method = 1;</code>
   */
  com.google.protobuf.ByteString
      getMethodBytes();

  /**
   * <pre>
   *  the request param message. the message must be gpb message.
   *  the gpb param message will be covert to args array for java method.
   *  example:
   *      take the {&#64;link GpcError} in this proto file as an example.
   *  define the gpb service:
   *  ...
   *  service  XXXService {
   *      rpc doAction (GpcError) returns (GpcVoid);
   *      ... define any more rpc.
   *  }
   *  map the java method:
   *      void doAction(int state, String msg).
   * </pre>
   *
   * <code>optional bytes params = 2;</code>
   */
  com.google.protobuf.ByteString getParams();
}
