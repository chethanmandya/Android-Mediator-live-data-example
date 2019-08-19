
## How to Build project
You can open the project in Android studio and press run.

### Testing
The project uses both instrumentation tests that run on the device and local unit tests that run on the computer.

- UI Tests :
The projects uses Espresso for UI testing. Since each fragment is limited to a ViewModel, each test mocks related ViewModel to run the tests.

- Database Tests :
The project creates an in memory database for each database test but still runs them on the device.

- Local Unit Tests :
ViewModel Tests : Each ViewModel is tested using local unit tests with mock Repository implementations.

- Repository Tests :
Each Repository is tested using local unit tests with mock web service and mock database.


### Libraries
- Android Support Library, support-lib
- Android Architecture Components
- Android Data Binding data-binding
- Dagger 2 for dependency injection
- Retrofit for REST api communication
- Glide for image loading
- Espresso for UI tests
- mockito for mocking in tests


### About app architecture
App uses latest MVVM architecture design guidelines. I have written some of the key things in the below page. Please follow up in the this link
https://github.com/chethu/Android-Architecture-Component

