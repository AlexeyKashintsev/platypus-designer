# Platypus.js designer
This project contains plugins for NetBeans IDE. Theese plugins allow edit Platypus.js fomrs, datamodels, entities based on Sql queries.

## Build instructions
To build plugins as NetBeans modules cluster from sources, run the following command:
```
gradlew suite
```
While building, you will need Platypus.js jars. They will be resolved as dependencies from either remote or local maven repository.
If you what to build Theese plugins in isolation, please, clone Platypus.js sources and bild them with the following commands:
```
git clone https://github.com/altsoft/PlatypusJS.git
cd PlatypusJS
gradlew install
```
Theese commands will build Platypus.js jars and put them in local Maven repository.

To build plugins as NetBeans plugin archives (nbm) from sources, run the following command:
```
gradlew nbms
```

## Run
If you whant to run just assembled plugins as a cluster of modules, execute the following command:
```
gradlew netBeansRun "-PnetBeansInstallDir=c:\program files\netbeans 8.2"
```
Note, that you need NetBeans 8.2 installed to use it with theese plugins.
