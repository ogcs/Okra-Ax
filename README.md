
## Okra-Ax分布式架构

简单的分布式服务器实现方案.

目前处于**试验状态**.代码会非常不稳定.

## 服务器结构




```
  Client * N  <-> Login * N
      \         /
       Gate * N  <----------> Remote * N
                \              /
                 Monitor * N
```


 注释:
 
 ## 特性
 1.  使用netty和disruptor库
 2.  Google Protocol Buffer作序列化/反序列化. 优化进程间通信. 
 
 
 
 
 
 
 
 
 
 
 
 
 








