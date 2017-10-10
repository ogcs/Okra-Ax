// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BeanCommon.proto

package org.okraAx.v3.beans.common;

/**
 * <pre>
 *  Client上报的日志. 用于分析客户端运行状况
 * </pre>
 *
 * Protobuf type {@code org.okraAx.v3.ClientLogMessage}
 */
public  final class ClientLogMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.okraAx.v3.ClientLogMessage)
    ClientLogMessageOrBuilder {
  // Use ClientLogMessage.newBuilder() to construct.
  private ClientLogMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ClientLogMessage() {
    logLevel_ = 0;
    message_ = "";
    throwable_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private ClientLogMessage(
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
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              context_ = com.google.protobuf.MapField.newMapField(
                  ContextDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
            context__ = input.readMessage(
                ContextDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            context_.getMutableMap().put(
                context__.getKey(), context__.getValue());
            break;
          }
          case 16: {

            logLevel_ = input.readInt32();
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            message_ = s;
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            throwable_ = s;
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
    return org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 1:
        return internalGetContext();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.okraAx.v3.beans.common.ClientLogMessage.class, org.okraAx.v3.beans.common.ClientLogMessage.Builder.class);
  }

  private int bitField0_;
  public static final int CONTEXT_FIELD_NUMBER = 1;
  private static final class ContextDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.String, java.lang.String> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.String, java.lang.String>newDefaultInstance(
                org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_ContextEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.STRING,
                "");
  }
  private com.google.protobuf.MapField<
      java.lang.String, java.lang.String> context_;
  private com.google.protobuf.MapField<java.lang.String, java.lang.String>
  internalGetContext() {
    if (context_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          ContextDefaultEntryHolder.defaultEntry);
    }
    return context_;
  }

  public int getContextCount() {
    return internalGetContext().getMap().size();
  }
  /**
   * <pre>
   *  携带的数据
   * </pre>
   *
   * <code>map&lt;string, string&gt; context = 1;</code>
   */

  public boolean containsContext(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    return internalGetContext().getMap().containsKey(key);
  }
  /**
   * Use {@link #getContextMap()} instead.
   */
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.String> getContext() {
    return getContextMap();
  }
  /**
   * <pre>
   *  携带的数据
   * </pre>
   *
   * <code>map&lt;string, string&gt; context = 1;</code>
   */

  public java.util.Map<java.lang.String, java.lang.String> getContextMap() {
    return internalGetContext().getMap();
  }
  /**
   * <pre>
   *  携带的数据
   * </pre>
   *
   * <code>map&lt;string, string&gt; context = 1;</code>
   */

  public java.lang.String getContextOrDefault(
      java.lang.String key,
      java.lang.String defaultValue) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, java.lang.String> map =
        internalGetContext().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <pre>
   *  携带的数据
   * </pre>
   *
   * <code>map&lt;string, string&gt; context = 1;</code>
   */

  public java.lang.String getContextOrThrow(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, java.lang.String> map =
        internalGetContext().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  public static final int LOGLEVEL_FIELD_NUMBER = 2;
  private int logLevel_;
  /**
   * <pre>
   *  日志等级
   * </pre>
   *
   * <code>optional int32 logLevel = 2;</code>
   */
  public int getLogLevel() {
    return logLevel_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 3;
  private volatile java.lang.Object message_;
  /**
   * <pre>
   *  附加信息
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
   *  附加信息
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

  public static final int THROWABLE_FIELD_NUMBER = 4;
  private volatile java.lang.Object throwable_;
  /**
   * <pre>
   *  异常信息
   * </pre>
   *
   * <code>optional string throwable = 4;</code>
   */
  public java.lang.String getThrowable() {
    java.lang.Object ref = throwable_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      throwable_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *  异常信息
   * </pre>
   *
   * <code>optional string throwable = 4;</code>
   */
  public com.google.protobuf.ByteString
      getThrowableBytes() {
    java.lang.Object ref = throwable_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      throwable_ = b;
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
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetContext(),
        ContextDefaultEntryHolder.defaultEntry,
        1);
    if (logLevel_ != 0) {
      output.writeInt32(2, logLevel_);
    }
    if (!getMessageBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, message_);
    }
    if (!getThrowableBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, throwable_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
         : internalGetContext().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
      context__ = ContextDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, context__);
    }
    if (logLevel_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, logLevel_);
    }
    if (!getMessageBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, message_);
    }
    if (!getThrowableBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, throwable_);
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
    if (!(obj instanceof org.okraAx.v3.beans.common.ClientLogMessage)) {
      return super.equals(obj);
    }
    org.okraAx.v3.beans.common.ClientLogMessage other = (org.okraAx.v3.beans.common.ClientLogMessage) obj;

    boolean result = true;
    result = result && internalGetContext().equals(
        other.internalGetContext());
    result = result && (getLogLevel()
        == other.getLogLevel());
    result = result && getMessage()
        .equals(other.getMessage());
    result = result && getThrowable()
        .equals(other.getThrowable());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    if (!internalGetContext().getMap().isEmpty()) {
      hash = (37 * hash) + CONTEXT_FIELD_NUMBER;
      hash = (53 * hash) + internalGetContext().hashCode();
    }
    hash = (37 * hash) + LOGLEVEL_FIELD_NUMBER;
    hash = (53 * hash) + getLogLevel();
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    hash = (37 * hash) + THROWABLE_FIELD_NUMBER;
    hash = (53 * hash) + getThrowable().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.common.ClientLogMessage parseFrom(
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
  public static Builder newBuilder(org.okraAx.v3.beans.common.ClientLogMessage prototype) {
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
   *  Client上报的日志. 用于分析客户端运行状况
   * </pre>
   *
   * Protobuf type {@code org.okraAx.v3.ClientLogMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.okraAx.v3.ClientLogMessage)
      org.okraAx.v3.beans.common.ClientLogMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 1:
          return internalGetContext();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 1:
          return internalGetMutableContext();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.okraAx.v3.beans.common.ClientLogMessage.class, org.okraAx.v3.beans.common.ClientLogMessage.Builder.class);
    }

    // Construct using org.okraAx.v3.beans.common.ClientLogMessage.newBuilder()
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
      internalGetMutableContext().clear();
      logLevel_ = 0;

      message_ = "";

      throwable_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.okraAx.v3.beans.common.GpcBnCommon.internal_static_org_okraAx_v3_ClientLogMessage_descriptor;
    }

    public org.okraAx.v3.beans.common.ClientLogMessage getDefaultInstanceForType() {
      return org.okraAx.v3.beans.common.ClientLogMessage.getDefaultInstance();
    }

    public org.okraAx.v3.beans.common.ClientLogMessage build() {
      org.okraAx.v3.beans.common.ClientLogMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.okraAx.v3.beans.common.ClientLogMessage buildPartial() {
      org.okraAx.v3.beans.common.ClientLogMessage result = new org.okraAx.v3.beans.common.ClientLogMessage(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.context_ = internalGetContext();
      result.context_.makeImmutable();
      result.logLevel_ = logLevel_;
      result.message_ = message_;
      result.throwable_ = throwable_;
      result.bitField0_ = to_bitField0_;
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
      if (other instanceof org.okraAx.v3.beans.common.ClientLogMessage) {
        return mergeFrom((org.okraAx.v3.beans.common.ClientLogMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.okraAx.v3.beans.common.ClientLogMessage other) {
      if (other == org.okraAx.v3.beans.common.ClientLogMessage.getDefaultInstance()) return this;
      internalGetMutableContext().mergeFrom(
          other.internalGetContext());
      if (other.getLogLevel() != 0) {
        setLogLevel(other.getLogLevel());
      }
      if (!other.getMessage().isEmpty()) {
        message_ = other.message_;
        onChanged();
      }
      if (!other.getThrowable().isEmpty()) {
        throwable_ = other.throwable_;
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
      org.okraAx.v3.beans.common.ClientLogMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.okraAx.v3.beans.common.ClientLogMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.MapField<
        java.lang.String, java.lang.String> context_;
    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetContext() {
      if (context_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            ContextDefaultEntryHolder.defaultEntry);
      }
      return context_;
    }
    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetMutableContext() {
      onChanged();;
      if (context_ == null) {
        context_ = com.google.protobuf.MapField.newMapField(
            ContextDefaultEntryHolder.defaultEntry);
      }
      if (!context_.isMutable()) {
        context_ = context_.copy();
      }
      return context_;
    }

    public int getContextCount() {
      return internalGetContext().getMap().size();
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public boolean containsContext(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetContext().getMap().containsKey(key);
    }
    /**
     * Use {@link #getContextMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getContext() {
      return getContextMap();
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public java.util.Map<java.lang.String, java.lang.String> getContextMap() {
      return internalGetContext().getMap();
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public java.lang.String getContextOrDefault(
        java.lang.String key,
        java.lang.String defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetContext().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public java.lang.String getContextOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetContext().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearContext() {
      getMutableContext().clear();
      return this;
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public Builder removeContext(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      getMutableContext().remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String>
    getMutableContext() {
      return internalGetMutableContext().getMutableMap();
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */
    public Builder putContext(
        java.lang.String key,
        java.lang.String value) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      if (value == null) { throw new java.lang.NullPointerException(); }
      getMutableContext().put(key, value);
      return this;
    }
    /**
     * <pre>
     *  携带的数据
     * </pre>
     *
     * <code>map&lt;string, string&gt; context = 1;</code>
     */

    public Builder putAllContext(
        java.util.Map<java.lang.String, java.lang.String> values) {
      getMutableContext().putAll(values);
      return this;
    }

    private int logLevel_ ;
    /**
     * <pre>
     *  日志等级
     * </pre>
     *
     * <code>optional int32 logLevel = 2;</code>
     */
    public int getLogLevel() {
      return logLevel_;
    }
    /**
     * <pre>
     *  日志等级
     * </pre>
     *
     * <code>optional int32 logLevel = 2;</code>
     */
    public Builder setLogLevel(int value) {
      
      logLevel_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  日志等级
     * </pre>
     *
     * <code>optional int32 logLevel = 2;</code>
     */
    public Builder clearLogLevel() {
      
      logLevel_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object message_ = "";
    /**
     * <pre>
     *  附加信息
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
     *  附加信息
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
     *  附加信息
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
     *  附加信息
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
     *  附加信息
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

    private java.lang.Object throwable_ = "";
    /**
     * <pre>
     *  异常信息
     * </pre>
     *
     * <code>optional string throwable = 4;</code>
     */
    public java.lang.String getThrowable() {
      java.lang.Object ref = throwable_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        throwable_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *  异常信息
     * </pre>
     *
     * <code>optional string throwable = 4;</code>
     */
    public com.google.protobuf.ByteString
        getThrowableBytes() {
      java.lang.Object ref = throwable_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        throwable_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *  异常信息
     * </pre>
     *
     * <code>optional string throwable = 4;</code>
     */
    public Builder setThrowable(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      throwable_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  异常信息
     * </pre>
     *
     * <code>optional string throwable = 4;</code>
     */
    public Builder clearThrowable() {
      
      throwable_ = getDefaultInstance().getThrowable();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *  异常信息
     * </pre>
     *
     * <code>optional string throwable = 4;</code>
     */
    public Builder setThrowableBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      throwable_ = value;
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


    // @@protoc_insertion_point(builder_scope:org.okraAx.v3.ClientLogMessage)
  }

  // @@protoc_insertion_point(class_scope:org.okraAx.v3.ClientLogMessage)
  private static final org.okraAx.v3.beans.common.ClientLogMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.okraAx.v3.beans.common.ClientLogMessage();
  }

  public static org.okraAx.v3.beans.common.ClientLogMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ClientLogMessage>
      PARSER = new com.google.protobuf.AbstractParser<ClientLogMessage>() {
    public ClientLogMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ClientLogMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ClientLogMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ClientLogMessage> getParserForType() {
    return PARSER;
  }

  public org.okraAx.v3.beans.common.ClientLogMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

