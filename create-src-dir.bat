md dontmakemetest-src
md dontmakemetest-src\core
md dontmakemetest-src\fireworks
md dontmakemetest-src\dependent-libs

xcopy core dontmakemetest-src\core /E
xcopy fireworks dontmakemetest-src\fireworks /E
xcopy "C:\Documents and Settings\xli\.m2\repository\turtlemock\turtlemock\1.0-SNAPSHOT\turtlemock-1.0-SNAPSHOT.jar" dontmakemetest-src\dependent-libs
xcopy "C:\Documents and Settings\xli\.m2\repository\ant\ant\1.6.5\ant-1.6.5.jar" dontmakemetest-src\dependent-libs
