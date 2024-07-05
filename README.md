# CatsAppChallenge

### Code challenge:
This application is built using Kotlin and utilizes the Cat API (https://thecatapi.com/). The primary goal of this project is to display a list of cat breeds, allow users to search and favorite them, and provide detailed information on each breed.


## Libraries used
- **Room**: Local Database for offline functionality
- **Jetpack compose**: Toolkit for building the UI
- **Navigation compose**: For navigation between screens
- **Kotlinx serialization Json**: For the type converter ( To use a List in Room's database )
- **Retrofit**: A type-safe HTTP client to access TheCatAPI
- **Coil**: Helps with image loading and caching (for offline usage)


## Project Structure
Usage of the MVVM architecture:
- **data**: Contains data models, database setup and Data Access Objects
  - **dao**: Includes the Data Access Object
  - **database**: Includes Room database setup and type converters
  - **model**: Includes the entity and data transfer objects
- **network**: Contains Retrofit's API implementation
- **repository**: Contains the Repository, linking the ViewModel to the DAO
- **ui**: Houses the UI components, including screens, composables and components
- **utils**: Contains helpers used in multiple places
- **viewmodel**: Manages UI-related data


## Screens
- **BreedsScreen**: Displays a list of all breeds
- **DetailedBreedScreen**: Shows information about a specific breed
- **FavouriteBreedScreen**: Lists all favourite breeds


## Error Handling
- Blocking certain features if something goes wrong, informing the users through a Toast notification
- Checking internet connection before an API fetch and Performing the fetch after the connection is reestablished
- Disabling the return button from TopAppBar for 1200ms after a click, to avoid popping the root stack
- Informing the user and sending them back to the previous stack if the accessed detailed breed doesn't exist/can't be retrieved
