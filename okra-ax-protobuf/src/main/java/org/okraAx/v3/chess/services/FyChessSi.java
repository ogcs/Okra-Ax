// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FyChess.proto

package org.okraAx.v3.chess.services;

public final class FyChessSi {
  private FyChessSi() {}
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
      "\n\rFyChess.proto\022\rorg.okraAx.v3\032\013AxAny.pr" +
      "oto\032\tGpc.proto\032\024FyChessMessage.proto2\226\001\n" +
      "\014ChessService\022B\n\020onShowRoomStatus\022\026.org." +
      "okraAx.v3.GpcVoid\032\026.org.okraAx.v3.GpcVoi" +
      "d\022B\n\013onMoveChess\022\033.org.okraAx.v3.MsgChes" +
      "sMove\032\026.org.okraAx.v3.GpcVoid2[\n\017PyChess" +
      "Callback\022H\n\021callbackMoveChess\022\033.org.okra" +
      "Ax.v3.MsgChessMove\032\026.org.okraAx.v3.GpcVo" +
      "idB+\n\034org.okraAx.v3.chess.servicesB\tFyCh" +
      "essSiH\001b\006proto3"
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
          org.okraAx.v3.chess.beans.FyChessMi.getDescriptor(),
        }, assigner);
    org.okraAx.v3.AxAnyProto.getDescriptor();
    org.okraAx.v3.Gpc.getDescriptor();
    org.okraAx.v3.chess.beans.FyChessMi.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}