// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BeanPlayerRoom.proto

package org.okraAx.v3.beans.roomPub;

/**
 * <pre>
 *  RoomPublicService#onChat
 * </pre>
 *
 * Protobuf type {@code org.okraAx.v3.PmOnChat}
 */
public  final class PmOnChat extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.okraAx.v3.PmOnChat)
    PmOnChatOrBuilder {
  // Use PmOnChat.newBuilder() to construct.
  private PmOnChat(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PmOnChat() {
    channel_ = 0;
    roomId_ = 0L;
    message_ = "";
    from_ = 0L;
    fromName_ = "";
    to_ = 0L;
    toName_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private PmOnChat(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
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
          case 8: {

            channel_ = input.readInt32();
            break;
          }
          case 16: {

            roomId_ = input.readInt64();
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            message_ = s;
            break;
          }
          case 32: {

            from_ = input.readInt64();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            fromName_ = s;
            break;
          }
          case 48: {

            to_ = input.readInt64();
            break;
          }
          case 58: {
            java.lang.String s = input.readStringRequireUtf8();

            toName_ = s;
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
    return org.okraAx.v3.beans.roomPub.GpcBnPlayerRoom.internal_static_org_okraAx_v3_PmOnChat_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.okraAx.v3.beans.roomPub.GpcBnPlayerRoom.internal_static_org_okraAx_v3_PmOnChat_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.okraAx.v3.beans.roomPub.PmOnChat.class, org.okraAx.v3.beans.roomPub.PmOnChat.Builder.class);
  }

  public static final int CHANNEL_FIELD_NUMBER = 1;
  private int channel_;
  /**
   * <pre>
   *  聊天频道
   * </pre>
   *
   * <code>optional int32 channel = 1;</code>
   */
  public int getChannel() {
    return channel_;
  }

  public static final int ROOMID_FIELD_NUMBER = 2;
  private long roomId_;
  /**
   * <pre>
   *  房间ID
   * </pre>
   *
   * <code>optional int64 roomId = 2;</code>
   */
  public long getRoomId() {
    return roomId_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 3;
  private volatile java.lang.Object message_;
  /**
   * <pre>
   *  聊天内容
   * </pre>
   *
   * <code>optional string message = 3;</code>
   */
  public java.lang.String getMessage() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      message_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *  聊天内容
   * </pre>
   *
   * <code>optional string message = 3;</code>
   */
  public com.google.protobuf.ByteString
      getMessageBytes() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      message_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FROM_FIELD_NUMBER = 4;
  private long from_;
  /**
   * <pre>
   *  发送人
   * </pre>
   *
   * <code>optional int64 from = 4;</code>
   */
  public long getFrom() {
    return from_;
  }

  public static final int FROMNAME_FIELD_NUMBER = 5;
  private volatile java.lang.Object fromName_;
  /**
   * <pre>
   *  发送人名称
   * </pre>
   *
   * <code>optional string fromName = 5;</code>
   */
  public java.lang.String getFromName() {
    java.lang.Object ref = fromName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      fromName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *  发送人名称
   * </pre>
   *
   * <code>optional string fromName = 5;</code>
   */
  public com.google.protobuf.ByteString
      getFromNameBytes() {
    java.lang.Object ref = fromName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      fromName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TO_FIELD_NUMBER = 6;
  private long to_;
  /**
   * <pre>
   *  收信人
   * </pre>
   *
   * <code>optional int64 to = 6;</code>
   */
  public long getTo() {
    return to_;
  }

  public static final int TONAME_FIELD_NUMBER = 7;
  private volatile java.lang.Object toName_;
  /**
   * <pre>
   *  收信人名称
   * </pre>
   *
   * <code>optional string toName = 7;</code>
   */
  public java.lang.String getToName() {
    java.lang.Object ref = toName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      toName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *  收信人名称
   * </pre>
   *
   * <code>optional string toName = 7;</code>
   */
  public com.google.protobuf.ByteString
      getToNameBytes() {
    java.lang.Object ref = toName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      toName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (channel_ != 0) {
      output.writeInt32(1, channel_);
    }
    if (roomId_ != 0L) {
      output.writeInt64(2, roomId_);
    }
    if (!getMessageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, message_);
    }
    if (from_ != 0L) {
      output.writeInt64(4, from_);
    }
    if (!getFromNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, fromName_);
    }
    if (to_ != 0L) {
      output.writeInt64(6, to_);
    }
    if (!getToNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, toName_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (channel_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, channel_);
    }
    if (roomId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, roomId_);
    }
    if (!getMessageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, message_);
    }
    if (from_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(4, from_);
    }
    if (!getFromNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, fromName_);
    }
    if (to_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(6, to_);
    }
    if (!getToNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, toName_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.okraAx.v3.beans.roomPub.PmOnChat)) {
      return super.equals(obj);
    }
    org.okraAx.v3.beans.roomPub.PmOnChat other = (org.okraAx.v3.beans.roomPub.PmOnChat) obj;

    boolean result = true;
    result = result && (getChannel()
        == other.getChannel());
    result = result && (getRoomId()
        == other.getRoomId());
    result = result && getMessage()
        .equals(other.getMessage());
    result = result && (getFrom()
        == other.getFrom());
    result = result && getFromName()
        .equals(other.getFromName());
    result = result && (getTo()
        == other.getTo());
    result = result && getToName()
        .equals(other.getToName());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + CHANNEL_FIELD_NUMBER;
    hash = (53 * hash) + getChannel();
    hash = (37 * hash) + ROOMID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getRoomId());
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    hash = (37 * hash) + FROM_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getFrom());
    hash = (37 * hash) + FROMNAME_FIELD_NUMBER;
    hash = (53 * hash) + getFromName().hashCode();
    hash = (37 * hash) + TO_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTo());
    hash = (37 * hash) + TONAME_FIELD_NUMBER;
    hash = (53 * hash) + getToName().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.roomPub.PmOnChat parseFrom(
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
  public static Builder newBuilder(org.okraAx.v3.beans.roomPub.PmOnChat prototype) {
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
   * <pre>
   *  RoomPublicService#onChat
   * </pre>
   *
   * Protobuf type {@code org.okraAx.v3.PmOnChat}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.okraAx.v3.PmOnChat)
      org.okraAx.v3.beans.roomPub.PmOnChatOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.okraAx.v3.beans.roomPub.GpcBnPlayerRoom.internal_static_org_okraAx_v3_PmOnChat_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.okraAx.v3.beans.roomPub.GpcBnPlayerRoom.internal_static_org_okraAx_v3_PmOnChat_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.okraAx.v3.beans.roomPub.PmOnChat.class, org.okraAx.v3.beans.roomPub.PmOnChat.Builder.class);
    }

    // Construct using org.okraAx.v3.beans.roomPub.PmOnChat.newBuilder()
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
      channel_ = 0;

      roomId_ = 0L;

      message_ = "";

      from_ = 0L;

      fromName_ = "";

      to_ = 0L;

      toName_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.okraAx.v3.beans.roomPub.GpcBnPlayerRoom.internal_static_org_okraAx_v3_PmOnChat_descriptor;
    }

    public org.okraAx.v3.beans.roomPub.PmOnChat getDefaultInstanceForType() {
      return org.okraAx.v3.beans.roomPub.PmOnChat.getDefaultInstance();
    }

    public org.okraAx.v3.beans.roomPub.PmOnChat build() {
      org.okraAx.v3.beans.roomPub.PmOnChat result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.okraAx.v3.beans.roomPub.PmOnChat buildPartial() {
      org.okraAx.v3.beans.roomPub.PmOnChat result = new org.okraAx.v3.beans.roomPub.PmOnChat(this);
      result.channel_ = channel_;
      result.roomId_ = roomId_;
      result.message_ = message_;
      result.from_ = from_;
      result.fromName_ = fromName_;
      result.to_ = to_;
      result.toName_ = toName_;
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
      if (other instanceof org.okraAx.v3.beans.roomPub.PmOnChat) {
        return mergeFrom((org.okraAx.v3.beans.roomPub.PmOnChat)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.okraAx.v3.beans.roomPub.PmOnChat other) {
      if (other == org.okraAx.v3.beans.roomPub.PmOnChat.getDefaultInstance()) return this;
      if (other.getChannel() != 0) {
        setChannel(other.getChannel());
      }
      if (other.getRoomId() != 0L) {
        setRoomId(other.getRoomId());
      }
      if (!other.getMessage().isEmpty()) {
        message_ = other.message_;
        onChanged();
      }
      if (other.getFrom() != 0L) {
        setFrom(other.getFrom());
      }
      if (!other.getFromName().isEmpty()) {
        fromName_ = other.fromName_;
        onChanged();
      }
      if (other.getTo() != 0L) {
        setTo(other.getTo());
      }
      if (!other.getToName().isEmpty()) {
        toName_ = other.toName_;
        onChanged();
      }
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
      org.okraAx.v3.beans.roomPub.PmOnChat parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.okraAx.v3.beans.roomPub.PmOnChat) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int channel_ ;
    /**
     * <pre>
     *  聊天频道
     * </pre>
     *
     * <code>optional int32 channel = 1;</code>
     */
    public int getChannel() {
      return channel_;
    }
    /**
     * <pre>
     *  聊天频道
     * </pre>
     *
     * <code>optional int32 channel = 1;</code>
     */
    public Builder setChannel(int value) {
      
      channel_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  聊天频道
     * </pre>
     *
     * <code>optional int32 channel = 1;</code>
     */
    public Builder clearChannel() {
      
      channel_ = 0;
      onChanged();
      return this;
    }

    private long roomId_ ;
    /**
     * <pre>
     *  房间ID
     * </pre>
     *
     * <code>optional int64 roomId = 2;</code>
     */
    public long getRoomId() {
      return roomId_;
    }
    /**
     * <pre>
     *  房间ID
     * </pre>
     *
     * <code>optional int64 roomId = 2;</code>
     */
    public Builder setRoomId(long value) {
      
      roomId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  房间ID
     * </pre>
     *
     * <code>optional int64 roomId = 2;</code>
     */
    public Builder clearRoomId() {
      
      roomId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object message_ = "";
    /**
     * <pre>
     *  聊天内容
     * </pre>
     *
     * <code>optional string message = 3;</code>
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *  聊天内容
     * </pre>
     *
     * <code>optional string message = 3;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *  聊天内容
     * </pre>
     *
     * <code>optional string message = 3;</code>
     */
    public Builder setMessage(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      message_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  聊天内容
     * </pre>
     *
     * <code>optional string message = 3;</code>
     */
    public Builder clearMessage() {
      
      message_ = getDefaultInstance().getMessage();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  聊天内容
     * </pre>
     *
     * <code>optional string message = 3;</code>
     */
    public Builder setMessageBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      message_ = value;
      onChanged();
      return this;
    }

    private long from_ ;
    /**
     * <pre>
     *  发送人
     * </pre>
     *
     * <code>optional int64 from = 4;</code>
     */
    public long getFrom() {
      return from_;
    }
    /**
     * <pre>
     *  发送人
     * </pre>
     *
     * <code>optional int64 from = 4;</code>
     */
    public Builder setFrom(long value) {
      
      from_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  发送人
     * </pre>
     *
     * <code>optional int64 from = 4;</code>
     */
    public Builder clearFrom() {
      
      from_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object fromName_ = "";
    /**
     * <pre>
     *  发送人名称
     * </pre>
     *
     * <code>optional string fromName = 5;</code>
     */
    public java.lang.String getFromName() {
      java.lang.Object ref = fromName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fromName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *  发送人名称
     * </pre>
     *
     * <code>optional string fromName = 5;</code>
     */
    public com.google.protobuf.ByteString
        getFromNameBytes() {
      java.lang.Object ref = fromName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        fromName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *  发送人名称
     * </pre>
     *
     * <code>optional string fromName = 5;</code>
     */
    public Builder setFromName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      fromName_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  发送人名称
     * </pre>
     *
     * <code>optional string fromName = 5;</code>
     */
    public Builder clearFromName() {
      
      fromName_ = getDefaultInstance().getFromName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  发送人名称
     * </pre>
     *
     * <code>optional string fromName = 5;</code>
     */
    public Builder setFromNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      fromName_ = value;
      onChanged();
      return this;
    }

    private long to_ ;
    /**
     * <pre>
     *  收信人
     * </pre>
     *
     * <code>optional int64 to = 6;</code>
     */
    public long getTo() {
      return to_;
    }
    /**
     * <pre>
     *  收信人
     * </pre>
     *
     * <code>optional int64 to = 6;</code>
     */
    public Builder setTo(long value) {
      
      to_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  收信人
     * </pre>
     *
     * <code>optional int64 to = 6;</code>
     */
    public Builder clearTo() {
      
      to_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object toName_ = "";
    /**
     * <pre>
     *  收信人名称
     * </pre>
     *
     * <code>optional string toName = 7;</code>
     */
    public java.lang.String getToName() {
      java.lang.Object ref = toName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        toName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *  收信人名称
     * </pre>
     *
     * <code>optional string toName = 7;</code>
     */
    public com.google.protobuf.ByteString
        getToNameBytes() {
      java.lang.Object ref = toName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        toName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *  收信人名称
     * </pre>
     *
     * <code>optional string toName = 7;</code>
     */
    public Builder setToName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      toName_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  收信人名称
     * </pre>
     *
     * <code>optional string toName = 7;</code>
     */
    public Builder clearToName() {
      
      toName_ = getDefaultInstance().getToName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  收信人名称
     * </pre>
     *
     * <code>optional string toName = 7;</code>
     */
    public Builder setToNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      toName_ = value;
      onChanged();
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


    // @@protoc_insertion_point(builder_scope:org.okraAx.v3.PmOnChat)
  }

  // @@protoc_insertion_point(class_scope:org.okraAx.v3.PmOnChat)
  private static final org.okraAx.v3.beans.roomPub.PmOnChat DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.okraAx.v3.beans.roomPub.PmOnChat();
  }

  public static org.okraAx.v3.beans.roomPub.PmOnChat getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PmOnChat>
      PARSER = new com.google.protobuf.AbstractParser<PmOnChat>() {
    public PmOnChat parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new PmOnChat(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PmOnChat> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PmOnChat> getParserForType() {
    return PARSER;
  }

  public org.okraAx.v3.beans.roomPub.PmOnChat getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
