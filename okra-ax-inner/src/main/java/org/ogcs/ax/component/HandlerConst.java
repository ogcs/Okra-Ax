/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public interface HandlerConst {

    ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);

    ChannelHandler GPB_ENCODER = new ProtobufEncoder();

}
