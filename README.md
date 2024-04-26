# Horse Race Simulator
This project is made of two parts:

 - Part 1: A simple, CLI-only horse race application.
 - Part 2: A fully-fledged JavaFX GUI application.

#### Dependencies
 - OpenJFX: Already supplied in Part 2.

#### Cloning and Navigation
To clone the Repository, run

    git clone https://github.com/mountainaught/HorceRaceSimulator
    cd HorseRaceSimulator
From there, you can change directory into Part 1 or Part 2.

## Part 1

Task 1, will update this area in the future once documentation phase begins.



## Part 2
Changing Directories:

    cd Part2\
    

### Compiling and Executing

If not done already, clone the repository and change directory into Part 2.

First to compile the Class files:

    javac --module-path lib -d manual\ src\main\java\module-info.java src\main\java\race\horse\*.java

Secondly, you execute:

    java -p "manual\;lib\" --add-modules=javafx.controls,javafx.fxml -m race.horse/race.horse.startRaceGUI


