@echo off

REM ===发现异常请检查是否存在BOM头

SET PROTOC_PATH=E:\C\OkraAx\okra-ax\okra-ax-protobuf\proto3\protoc\bin\protoc.exe
SET GPB_INCLUDE_PATH=%~dp0protoc\include\google\protobuf\
SET ROOT_PATH=%~dp0
SET JAVA_OUT_PATH=E:\C\OkraAx\okra-ax\okra-ax-protobuf\src\main\java\
SET CSHARP_OUT_PATH=E:\C\OkraAx\okra-ax\okra-ax-protobuf\proto3\CHARP_OUT\
REM SET JAVA_OUT_PATH=%ROOT_PATH%JAVA_OUT

setlocal ENABLEDELAYEDEXPANSION
REM ===在这里设置你的后缀名
set suffix=.proto
REM ===在这里设置你要搜索的路径,这里设置为批处理所在目录

echo [begin]Lookup *.proto And Start Compile.
for /R "%ROOT_PATH%" %%i in (*.proto) do (
REM echo %%~fi
REM echo %%~nxi
REM echo %PROTOC_PATH% -IPATH=%ROOT_PATH% -IPATH=%GPB_INCLUDE_PATH% --java_out=%JAVA_OUT_PATH% %%~fi
REM echo %PROTOC_PATH% -I=./ --java_out=./JAVA_OUT %%~nxi
REM %PROTOC_PATH% -I=./ --java_out=./JAVA_OUT %%~nxi
%PROTOC_PATH% -I=%ROOT_PATH% --java_out=%JAVA_OUT_PATH% --csharp_out=%CSHARP_OUT_PATH% %%~fi

REM echo 相对路径

echo ...[do]Compile '%%~nxi' Completed.
)
echo [end]Compile Done.
pause
exit