// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FyRoom.proto

package org.okraAx.v3.room.services;

public final class FyRoomSi {
  private FyRoomSi() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014FyRoom.proto\022\rorg.okraAx.v3\032\013AxAny.pro" +
      "to\032\tGpc.proto\032\023FyRoomMessage.proto2\276\004\n\013R" +
      "oomService\0226\n\004ping\022\026.org.okraAx.v3.GpcVo" +
      "id\032\026.org.okraAx.v3.GpcVoid\022C\n\014onCreateRo" +
      "om\022\033.org.okraAx.v3.VoCreateRoom\032\026.org.ok" +
      "raAx.v3.GpcVoid\022A\n\013onEnterRoom\022\032.org.okr" +
      "aAx.v3.VoEnterRoom\032\026.org.okraAx.v3.GpcVo" +
      "id\022<\n\nonExitRoom\022\026.org.okraAx.v3.GpcVoid" +
      "\032\026.org.okraAx.v3.GpcVoid\022:\n\nonShowHall\022\024" +
      ".org.okraAx.v3.AxAny\032\026.org.okraAx.v3.Gpc",
      "Void\022?\n\nonGetReady\022\031.org.okraAx.v3.VoGet" +
      "Ready\032\026.org.okraAx.v3.GpcVoid\022=\n\013onGameS" +
      "tart\022\026.org.okraAx.v3.GpcVoid\032\026.org.okraA" +
      "x.v3.GpcVoid\022;\n\tonGameEnd\022\026.org.okraAx.v" +
      "3.GpcVoid\032\026.org.okraAx.v3.GpcVoid\0228\n\006onC" +
      "hat\022\026.org.okraAx.v3.GpcVoid\032\026.org.okraAx" +
      ".v3.GpcVoid2\210\001\n\016PyRoomCallback\0226\n\004pong\022\026" +
      ".org.okraAx.v3.GpcVoid\032\026.org.okraAx.v3.G" +
      "pcVoid\022>\n\014callbackChat\022\026.org.okraAx.v3.G" +
      "pcVoid\032\026.org.okraAx.v3.GpcVoidB)\n\033org.ok",
      "raAx.v3.room.servicesB\010FyRoomSiH\001b\006proto" +
      "3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.okraAx.v3.AxAnyProto.getDescriptor(),
          org.okraAx.v3.Gpc.getDescriptor(),
          org.okraAx.v3.room.beans.FyRoomMi.getDescriptor(),
        }, assigner);
    org.okraAx.v3.AxAnyProto.getDescriptor();
    org.okraAx.v3.Gpc.getDescriptor();
    org.okraAx.v3.room.beans.FyRoomMi.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}