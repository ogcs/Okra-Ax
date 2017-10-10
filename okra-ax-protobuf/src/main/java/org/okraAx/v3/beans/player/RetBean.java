// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BeanPlayer.proto

package org.okraAx.v3.beans.player;

/**
 * <pre>
 *  callback
 *  Simple callback bean
 * </pre>
 *
 * Protobuf type {@code org.okraAx.v3.RetBean}
 */
public  final class RetBean extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.okraAx.v3.RetBean)
    RetBeanOrBuilder {
  // Use RetBean.newBuilder() to construct.
  private RetBean(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RetBean() {
    ret_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RetBean(
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

            ret_ = input.readInt32();
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
    return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_RetBean_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_RetBean_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.okraAx.v3.beans.player.RetBean.class, org.okraAx.v3.beans.player.RetBean.Builder.class);
  }

  public static final int RET_FIELD_NUMBER = 1;
  private int ret_;
  /**
   * <code>optional int32 ret = 1;</code>
   */
  public int getRet() {
    return ret_;
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
    if (ret_ != 0) {
      output.writeInt32(1, ret_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (ret_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, ret_);
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
    if (!(obj instanceof org.okraAx.v3.beans.player.RetBean)) {
      return super.equals(obj);
    }
    org.okraAx.v3.beans.player.RetBean other = (org.okraAx.v3.beans.player.RetBean) obj;

    boolean result = true;
    result = result && (getRet()
        == other.getRet());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + RET_FIELD_NUMBER;
    hash = (53 * hash) + getRet();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.okraAx.v3.beans.player.RetBean parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.RetBean parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.RetBean parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.okraAx.v3.beans.player.RetBean parseFrom(
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
  public static Builder newBuilder(org.okraAx.v3.beans.player.RetBean prototype) {
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
   *  callback
   *  Simple callback bean
   * </pre>
   *
   * Protobuf type {@code org.okraAx.v3.RetBean}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.okraAx.v3.RetBean)
      org.okraAx.v3.beans.player.RetBeanOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_RetBean_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_RetBean_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.okraAx.v3.beans.player.RetBean.class, org.okraAx.v3.beans.player.RetBean.Builder.class);
    }

    // Construct using org.okraAx.v3.beans.player.RetBean.newBuilder()
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
      ret_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.okraAx.v3.beans.player.GpcBnPlayer.internal_static_org_okraAx_v3_RetBean_descriptor;
    }

    public org.okraAx.v3.beans.player.RetBean getDefaultInstanceForType() {
      return org.okraAx.v3.beans.player.RetBean.getDefaultInstance();
    }

    public org.okraAx.v3.beans.player.RetBean build() {
      org.okraAx.v3.beans.player.RetBean result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.okraAx.v3.beans.player.RetBean buildPartial() {
      org.okraAx.v3.beans.player.RetBean result = new org.okraAx.v3.beans.player.RetBean(this);
      result.ret_ = ret_;
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
      if (other instanceof org.okraAx.v3.beans.player.RetBean) {
        return mergeFrom((org.okraAx.v3.beans.player.RetBean)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.okraAx.v3.beans.player.RetBean other) {
      if (other == org.okraAx.v3.beans.player.RetBean.getDefaultInstance()) return this;
      if (other.getRet() != 0) {
        setRet(other.getRet());
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
      org.okraAx.v3.beans.player.RetBean parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.okraAx.v3.beans.player.RetBean) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int ret_ ;
    /**
     * <code>optional int32 ret = 1;</code>
     */
    public int getRet() {
      return ret_;
    }
    /**
     * <code>optional int32 ret = 1;</code>
     */
    public Builder setRet(int value) {
      
      ret_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 ret = 1;</code>
     */
    public Builder clearRet() {
      
      ret_ = 0;
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


    // @@protoc_insertion_point(builder_scope:org.okraAx.v3.RetBean)
  }

  // @@protoc_insertion_point(class_scope:org.okraAx.v3.RetBean)
  private static final org.okraAx.v3.beans.player.RetBean DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.okraAx.v3.beans.player.RetBean();
  }

  public static org.okraAx.v3.beans.player.RetBean getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RetBean>
      PARSER = new com.google.protobuf.AbstractParser<RetBean>() {
    public RetBean parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new RetBean(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RetBean> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RetBean> getParserForType() {
    return PARSER;
  }

  public org.okraAx.v3.beans.player.RetBean getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
