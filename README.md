# Platypus.js designer
This project contains plugins for NetBeans IDE. These plugins allow to edit Platypus.js forms, datamodels, entities based on Sql queries, database structure diagrams, etc.

Platypus.js applications creation, editing, deployment, debugging and maintenance all may be performed using the NetBeans IDE with Platypus.js designer plugins.

The Platypus.js designer plugins for NetBeans IDE include the following development tools:

* Database structure visual editor.
* Sql queries visual editor.
* NetBeans JavaScript code editor with Platypus.js specific code completion, etc.
* ORM configuration visual editor for application modules.
* User interface visual editor.

## Build instructions
To build plugins as NetBeans modules cluster from sources, run the following command:
```
gradlew suite
```

## Run
If you whant to run just assembled plugins as a cluster of modules, execute the following command:
```
gradlew netBeansRun "-PnetBeansInstallDir=c:\program files\netbeans 8.2"
```
Note, that you need NetBeans 8.2 installed to use it with these plugins.

## Notes
While building, you will need Platypus.js artifacts. They will be resolved as dependencies from either remote or local maven repository.
Normally, you wouldn't need to build Platypus.js by yourself and Platypus.js artifacts will be fetched from JCenter repository.
But, if you what to build these Platypus.js designer modules in isolation, please, clone Platypus.js sources and build them with the following commands:
```
git clone https://github.com/marat-gainullin/platypus-js.git
cd PlatypusJS
gradlew install
```
These commands will build Platypus.js jars and install artifacts in local Maven repository.

To build Platypus.js designer modules as NetBeans plugin archives (nbm) from sources, run the following command:
```
gradlew nbms
```
