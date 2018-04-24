# The UI source code, a JavaFX/FXML UI.

Here resides all our code for our JavaFX application.

We have sorted our code in four packets:

* **app.ui**: General code for our application, *AppPreloader.java* which shows a load sign when first starting the app (which can take a few seconds). *DummyApp.java* a mock for App when testing the user interface without connecting to backend. *FxApp.java* main code for running our JavaFX application. *LayoutHandler.java* which stores all our locations for our .fxml documents. *NavigationHandler.java*, a superclass for all Controllers, which controlls the navigation between scenes.


* **app.ui.controllers.coach**: All controllers used mainly by the coach section of our JavaFX application.


* **app.ui.controllers.common**: Common controllers used by the coach and the user in our JavaFX application.


* **app.ui.controllers.user**: All controllers used by mainly the user in our JavaFX application and for presenting data relevant for the client users type.