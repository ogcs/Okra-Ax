# How To Define Google Protocol Buffers(.proto) File 

　　按照一定规范定义pb文件，可以有效避免歧义，方便理解，降低维护成本。
文档提供一种简单的命名规范建议。另外用户可以增加、删除或修改此命名规范，以适应实际业务。

## 命名规范

 1. Bean开头的proto文件，用于定义使用到的message。示例:BeanRoom.proto
 1. 普通message命名没有要求，根据业务需求，尽可能贴合模块命名即可。示例:PlayerInfo
 2. Service结尾的proto文件用于定义service。对应的Java类名为Pro+Service名称。示例:RelayService.proto => ProRelay.java
 4. service中定义的rpc接口参数message，以Rpc开头，用于和普通bean区别。示例:RpcPlayerInfo
 5. service中定义的rpc需要和common包下的接口一一对应。
 
## 其他

 1. 增加option java_multiple_files = true;参数。生成独立的多个bean文件
 
## **系统保留文件**

 1. **Gpc.proto**   核心文件，定义消息传输的基本结构和常用message。
 2. AxAny.proto 类似于pb的Any.proto。区别在于Any的key由字符串组成。AxAny由long实现。
 Any由pb内部实现支持。 AxAny依赖AxOptions的messageId（用户指定， 缺省值为message名称的hashCode）
 3. AxOptions.proto 定义一些内部方便反射使用的设置.

