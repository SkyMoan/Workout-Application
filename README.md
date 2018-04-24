# MemeEX

Repository for group 36, TDT4140, Spring 2018. 
This project was started as a university assignment to learn more about programming and agile development in teams and was based on a the template project [tdt4140-gr18nn](https://gitlab.stud.iie.ntnu.no/tdt4140-staff/examples/tree/master/tdt4140-gr18nn).

This is an app where a user can register their workouts and get feedback from different coaches by their choosing. 
The app began as a simple workout logger app, but developed into a more specific Triathlon app as you can post run, swim or bike sessions. 
The user will be able to track their progress in different types of exercises and see their workout history which is stored using Firebase.
When the user has gotten more registered workouts in, they can implement our analytical services to compare themselves with other users in their territory and search for other users with a similiar set of workouts.
This can be used by users looking for partners to work out with or to simply compare themselves with others. 


## Getting Started

To get started download the project files from GitLab.

### Prerequisites

    
    1. Download and install Eclipse with JDK1.8. 
    2. Download and install the JavaFX extension plugin to Eclipse. This is used to build the user interface.
    3. To build the project download and install Maven. Then import the project using Eclipse "Import"... > "Existing Projects into Workspace".
    4. To run the program navigate to App.UI.FxApp.java and run this as an java applet.
    
    Extras:
    - If you want to open the FXML files in SceneBuilder you need to import JFoenix.
    
    
Our project implements an MVC architecture with a model communicating with a database class which communicates with Firebase. In the installed Maven project you should find the following modules:

 * [app.ui](tdt4140-gr1836/app.ui/README.md) contains our main code for managing the JavaFX application
 * [app.core](tdt4140-gr1836/app.core/README.md) contains our main model for communcating with the Firebase and managing our data objects





## Running the tests

Simply run as Maven test. The core of the app is tested with some basic junit testing. The ui is tested with TestFX which goes through the scenes automatically, testing Button, Labels etc.



## Deployment

To deploy this on a live system you need to have Java installed, then run the FxApp as a java applet using your IDE with **Run as > Java Application**

### Usage
When you first run the FxApp you will be greeted with a login, here you will either log in using an existing user or register a new user before logging in.
From here you have a few options:

    As a regular user:
    
    - Register workouts
    - Look up previous workouts
    - View available coaches which you can select
    - Send messages to others users and coaches
    - View statistics on your workouts and compare with users in your city.
    
    As a coach:
    
    - See which users and have selected you as their coach.
    - Send messages to other coaches and users.
    - View your clients progress and give them feedback.



## Built With

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - General development
* [JavaFX](http://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html) - The GUI building tool
* [Maven](https://maven.apache.org/) - Dependency Management
* [TestFX](https://github.com/TestFX/TestFX) - Testing JavaFX
* [JFoenix](https://github.com/jfoenixadmin/JFoenix) - JavaFX design


##Database

*[Firebase](https://console.firebase.google.com/project/tdt4140-g36/database/tdt4140-g36/data)
    -   Requires Permission


## Authors

To be decided.





See also the list of [contributors](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/36/graphs/master) who participated in this project.

## License

To be decided.

