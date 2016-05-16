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
 * 1. 内部通信使用Google Protocol Buffer作为序列化/反序列化协议. 减少通信量, 增加IO吞吐量
 *
 *
 * [Gate]:负责负载
 *
 *
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
package org.ogcs.ax;