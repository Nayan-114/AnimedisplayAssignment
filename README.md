# Anime Display App

An Android app built with **Jetpack Compose**, **MVVM**, and **Clean Architecture**

---
## Features:-

### Home Screen (Anime List)
- Fetches top anime list from `https://api.jikan.moe/v4/top/anime`
- Displays a list of top anime fetched from the Jikan API
- Scrollable list of anime using `LazyColumn`
- Clickable anime cards to navigate to the detail screen
- Loading and error handling

### Anime Detail Screen
- Fetches anime details from `https://api.jikan.moe/v4/anime/{anime_id}`
- Displays:
    -  **YouTube Trailer Player** using an **open-source YouTube Player**  
       [YouTube Android Player - open-source alternative](https://github.com/PierfrancescoSoffritti/android-youtube-player)  
      (Fallback to poster image if trailer is not available)
    -  Title
    -  Plot / Synopsis
    -  Genres (styled tags)
    -  Number of Episodes
    -  Rating with styled badge

### Architecture
- **MVVM** (Model-View-ViewModel)
- **Jetpack Compose** UI
- **Clean Architecture** structure:
    - `data` (API, repository)
    - `domain` (models, use cases)
    - `presentation` (ViewModels, UI)

### Networking
- Uses **Retrofit** with **GsonConverter** for parsing JSON responses
- Single shared `AnimeService` instance

### ViewModel
- One ViewModel for the anime list
- One ViewModel for anime details
- Proper `UiState` handling (Loading, Success, Error)

### Assumptions
-  The Main Cast field could not be populated as no directly mappable attribute was found in the API response for character or voice actor details.
-  I have assumed that any open-source YouTube player library can be used to load and display the video trailers

---

