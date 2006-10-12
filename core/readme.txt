
There are two build scripts. One is for maven named "pom.xml". One is for ant named "build.xml".
I would like to use maven as build script. If you'd like too, please use "mvn idea:idea" to rebuild
IntelliJ project file first. And you can use "mvn clean install" to build project.

For those who like ant, I create a simple ant script which only has tow targets: "test" and "jar".
So after you open the IntelliJ project file exists, you should set up the project modules libraries
first. 

Project shadow dependents on "ant-1.6.5", "junit-4.1" and "turtlemock-1.0-SNAPSHOT" which can
been found at "../dependent-libs".