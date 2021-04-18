@echo OFF

echo Compiling the java files
mkdir tmp_Compile

GOTO :WordGame_Java

:WordGame_Java
if exist .\bin\main\WordGame.class (
    GOTO :WordGame_Has_Class
) else (
    GOTO :Compile_WordGame
)

:WordGame_Has_Class
copy .\src\main\WordGame.java .\tmp_Compile
copy .\bin\main\WordGame.class .\tmp_Compile
cd .\tmp_Compile
FOR /F %%i IN ('DIR /B /O:D WordGame.java WordGame.class') DO SET NEWEST=%%i
cd ..
if "%NEWEST%"=="WordGame.java" (
    GOTO :Compile_WordGame
) else (
    echo WordGame.java already Compiled
    GOTO :DemoGame_Java
)

:Compile_WordGame
echo Compiling WordGame.java...
cd src\main
javac WordGame.java -d .\..\..\bin
cd ..\..
echo Compiling WordGame.java Completed

:DemoGame_Java
if exist .\bin\main\DemoGame.class (
    GOTO :DemoGame_Has_Class
) else (
    GOTO :Compile_DemoGame
)

:DemoGame_Has_Class
copy .\src\main\DemoGame.java .\tmp_Compile
copy .\bin\main\DemoGame.class .\tmp_Compile
cd .\tmp_Compile
FOR /F %%i IN ('DIR /B /O:D DemoGame.java DemoGame.class') DO SET NEWEST=%%i
cd ..
if "%NEWEST%"=="DemoGame.java" (
    GOTO :Compile_DemoGame
) else (
    echo DemoGame.java already Compiled
    GOTO :End_Compiling
)

:Compile_DemoGame
echo Compiling DemoGame.java...
cd src\main
javac -classpath ..\..\bin DemoGame.java -d ..\..\bin
cd ..\..
echo Compiling DemoGame.java Completed

:End_Compiling
echo Java files compilation Successfull
rmdir /s /q .\tmp_Compile
pause