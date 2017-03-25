

1. service定义RPC接口
2. message定义接口的参数，在程序中不会真实存在，用于数据传递。代码中由代理模式转换为接口的参数
3. 程序中需要的message, 由protoc生成，程序中当做数据接口的bean使用







