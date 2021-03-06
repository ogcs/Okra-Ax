// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BeanPlayer.proto

package org.okraAx.v3.beans.player;

/**
 * Protobuf type {@code org.okraAx.v3.CallbackSyncTime}
 */
public  final class CallbackSyncTime extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.okraAx.v3.CallbackSyncTime)
    CallbackSyncTimeOrBuilder {
  // Use CallbackSyncTime.newBuilder() to construct.
  private CallbackSyncTime(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CallbackSyncTime() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private CallbackSyncTime(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_CallbackSyncTime_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_CallbackSyncTime_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.okraAx.v3.beans.player.CallbackSyncTime.class, org.okraAx.v3.beans.player.CallbackSyncTime.Builder.class);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.okraAx.v3.beans.player.CallbackSyncTime)) {
      return super.equals(obj);
    }
    org.okraAx.v3.beans.player.CallbackSyncTime other = (org.okraAx.v3.beans.player.CallbackSyncTime) obj;

    boolean result = true;
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.CallbackSyncTime parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.okraAx.v3.beans.player.CallbackSyncTime prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code org.okraAx.v3.CallbackSyncTime}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.okraAx.v3.CallbackSyncTime)
      org.okraAx.v3.beans.player.CallbackSyncTimeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_CallbackSyncTime_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_CallbackSyncTime_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.okraAx.v3.beans.player.CallbackSyncTime.class, org.okraAx.v3.beans.player.CallbackSyncTime.Builder.class);
    }

    // Construct using org.okraAx.v3.beans.player.CallbackSyncTime.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_CallbackSyncTime_descriptor;
    }

    public org.okraAx.v3.beans.player.CallbackSyncTime getDefaultInstanceForType() {
      return org.okraAx.v3.beans.player.CallbackSyncTime.getDefaultInstance();
    }

    public org.okraAx.v3.beans.player.CallbackSyncTime build() {
      org.okraAx.v3.beans.player.CallbackSyncTime result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.okraAx.v3.beans.player.CallbackSyncTime buildPartial() {
      org.okraAx.v3.beans.player.CallbackSyncTime result = new org.okraAx.v3.beans.player.CallbackSyncTime(this);
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.okraAx.v3.beans.player.CallbackSyncTime) {
        return mergeFrom((org.okraAx.v3.beans.player.CallbackSyncTime)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.okraAx.v3.beans.player.CallbackSyncTime other) {
      if (other == org.okraAx.v3.beans.player.CallbackSyncTime.getDefaultInstance()) return this;
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.okraAx.v3.beans.player.CallbackSyncTime parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.okraAx.v3.beans.player.CallbackSyncTime) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:org.okraAx.v3.CallbackSyncTime)
  }

  // @@protoc_insertion_point(class_scope:org.okraAx.v3.CallbackSyncTime)
  private static final org.okraAx.v3.beans.player.CallbackSyncTime DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.okraAx.v3.beans.player.CallbackSyncTime();
  }

  public static org.okraAx.v3.beans.player.CallbackSyncTime getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CallbackSyncTime>
      PARSER = new com.google.protobuf.AbstractParser<CallbackSyncTime>() {
    public CallbackSyncTime parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new CallbackSyncTime(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CallbackSyncTime> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CallbackSyncTime> getParserForType() {
    return PARSER;
  }

  public org.okraAx.v3.beans.player.CallbackSyncTime getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

