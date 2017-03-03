/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 * Ax分布式架构
 * 基于Okra框架的分布式架构方案.
 *
 *
 *
 * 服务器结构:
 *
 *  Client * N  <-> Login * N
 *      \         /
 *       Gate * N  <----------> Remote * N
 *                \              /
 *                 Monitor * N
 *
 * [Client]:    用户创建的连接
 * [Login]:     负责验证用户的登录信息, 负责长连接的负载均衡。分配Client到Gate集群中.
 * [Gate]:      负责负载Client的长连接，分发上行(客户端发送给服务器的称之为上行)请求，推送下行消息(服务器发送给客户端的消息称之为下行)。
 * [Remote]:    分布式服务节点, 提供系统所需要的各种服务.
 * [Monitor]:   负责监控服务器运行状况，查看节点负载情况等
 *
 * 注释:
 * 1. 内部通信使用Google Protocol Buffer作为序列化/反序列化协议. 减少通信量, 增加IO吞吐量. 见{@link org.ogcs.ax.gpb.OkraAx}
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
package org.okraAx;