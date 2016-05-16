REM [未完成]脚本用于快速启动整个集群
@echo on

SET JAVA_HOME=
SET JAVA_HOME_BIN=
REM -ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m
SET JAVA_OPTS=-server

SET APP_JAR=okra-ax.jar




for /r "./" %%i in (*jar) do (

    @echo %%~dpi

    IF {%%~nxi}=={%APP_JAR%} (
    REM IF EXIST "%APP_JAR%" (
	echo "存在"
	REM echo %%i
        REM %%~dpi 示例：E:\C\Git@Lj\okra-ax\out\artifacts\okra_ax-chess0
	cd %%~dpi
	%JAVA_HOME%javaw %JAVA_OPTS% -jar %APP_JAR%
	cd ..

    ) ELSE (echo "不存在")
)


pause