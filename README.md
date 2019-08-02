Mercari Android  Coding assignment
Requirement :
Please find below link to understand the requirement -
https://github.com/m-rec/b8ae8811e0b9fbcd5edd2bcfc6c7f16a6c495e8f/blob/master/SKILL_TEST.en.md

Building
You can open the project in Android studio and press run.

Testing
The project uses both instrumentation tests that run on the device and local unit tests that run on the computer.

To run both of them and generate a coverage report, you can run: ./gradlew fullCoverageReport (requires a connected device or an emulator)

UI Tests
The projects uses Espresso for UI testing. Since each fragment is limited to a ViewModel, each test mocks related ViewModel to run the tests.

Database Tests
The project creates an in memory database for each database test but still runs them on the device.

Local Unit Tests
ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository implementations.

Repository Tests
Each Repository is tested using local unit tests with mock web service and mock database.

Webservice Tests
The project uses [MockWebServer][mockwebserver] project to test REST api interactions.

Libraries
Android Support Library, support-lib
Android Architecture Components
Android Data Binding data-binding
Dagger 2 for dependency injection
Retrofit for REST api communication
Glide for image loading
Espresso for UI tests
mockito for mocking in tests