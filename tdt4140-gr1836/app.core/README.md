# Source code for core

This is where all our code for the core of our application lays.

Here we have sorted our code in several packages.

* **app.core**: Contains our main App.java class which our JavaFx application communicates with. This class is the connector between all other classes in app.core.


* **app.db**: Contains our main communicator with the database. Sorted in four different classes for handling different sections of datbase, **Inbox**, **Users**, **Workouts** and **Statistics**.
 
 
* **app.orkouts**: Contains classes for handling workout objects fetched from database.


* **app.tatistics**: Contains classes for handling workout objects fetched from database. Statistics.java has three main functions, *calculateAverageInCity()* which find the average statistics in your city, *findPartners()* which finds potential workout partners based on statistics and *updateMyStatistics()* which updates your statistics based on your Workout objects.


* **app.sers**: Contains classes for handling user objects fetched from database. 


* **app.inbox**: Contains classes for handling Message objects fetched from database. 


