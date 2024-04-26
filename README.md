# Horse Race Simulator
This project is made of two parts:

 - Part 1: A simple, CLI-only horse race application.
 - Part 2: A fully-fledged JavaFX GUI application.

#### Dependencies
 - OpenJFX: Already supplied in Part 2.

#### Installation and Navigation
To clone the Repository, run

    git clone https://github.com/mountainaught/HorceRaceSimulator
    cd HorseRaceSimulator
From there, you can change directory into Part 1 or Part 2 using the following commands.

    cd Part1\
    cd Part2\

## Part 1
Part 1 is a basic, Command-Line based Horse racing application with fixed configurations.

#### Compiling and Executing
Compilation:

    javac -d out\ .\src\horserace\*.java .\src\horserace\utils\*.java
Execution:

    java -cp out\ horserace.startRace


## Part 2

Part 2 is a Graphical application written in Java, utilizing JavaFX libraries for a aesthetically pleasing UI. 

The main Application is split between the Race Application itself, a Settings panel which offers the main avenue for customization, and internal objects defined within the scope of the project and built upon abstract JavaFX foundations.
    

### Compiling and Executing

If not done already, clone the repository and change directory into Part 2.

First to compile the Class files:

    javac --module-path lib -d manual\ src\main\java\module-info.java src\main\java\race\horse\*.java

Secondly, you execute:

    java -p "manual\;lib\" --add-modules=javafx.controls,javafx.fxml -m race.horse/race.horse.startRaceGUI


## Demo
#### Click on the video below for a demonstration.
[![Now in Android: 55](https://i.ytimg.com/vi/k0_FbJoyYQA/maxresdefault.jpg)](https://www.youtube.com/watch?v=k0_FbJoyYQA "Now in Android: 55")
