
## Common模块

由模块化的服务接口(common), 内部实现(internal)以及工具集(utilities)三部分组成.

## 目录结构
    |-  org.okraAx
        |-  common      
        |-  internal
            |-  core    核心接口
            |-  handler Netty的handler和编解码器
            |-  inner   旧版本的内容. 用于内部组件间建立连接和通信.
            |-  v3      基于Google Protocol Buffers协议实现的异步远程过程调用(ARPC).
        |-  utilities   通用工具集

