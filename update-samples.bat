@echo off
set initial_dir=%cd%

call :makeSample ui-demo
call :makeSample pet-hotel
call :makeSample web-socket-demo
call :makeSample platypus-gradle-skeleton
goto end

:makeSample
cd %~dp0\..\%1
cmd /C gradlew clean > nul
"%JAVA_HOME%\bin\jar" -cMf %1.zip gradle src tomcat-conf .gitignore build.gradle gradlew gradlew.bat README.md
move /Y %1.zip ..\platypus-designer\platypus-js-samples\src\main\resources\com\eas\designer\samples > nul
cd %initial_dir%
exit /B

:end