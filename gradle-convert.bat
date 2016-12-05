@echo off
set target=%1
set location=%~p0
cd %target%
rmdir nbproject /s /q
rmdir /s /q release
del build.xml
rename src resources
mkdir main
move resources main
mkdir main\java
rename test resources
mkdir test
move resources test
mkdir test\java
mkdir src
move main src
move test src
xcopy src\main\resources\*.java src\main\java /e
del src\main\resources\*.java /s
xcopy src\test\resources\*.java src\test\java /e
del src\test\resources\*.java /s
for /f "delims=" %%a in ('dir src\* /b /s /ad ^| sort /r') do rmdir %%a
cd %location%