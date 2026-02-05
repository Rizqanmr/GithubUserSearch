# Github User Search

## Features
- User list screen
- Search user
- User detail
- Local data persistence for data caching user list
- Chucker debugging
- Unit test and UI test using espresso+MockK

## architecture and Libraries
This project implement a clean architecture pattern MVVM. Because to create applications that are clean, 
manageable, and easy to maintain. Another reason is because MVVM is highly recommended by Google 
for Android application development

This project using the libraries:
- [XML](https://developer.android.com/develop/ui/views/layout/declaring-layout) (UI)

- [Dagger-Hilt](https://dagger.dev/hilt/) (Dependency Injection)

- [LifeCycle Components](https://developer.android.com/topic/libraries/architecture/livedata) (ViewModel and LiveData)

- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) (Asynchronous programming)

- [Retrofit](https://square.github.io/retrofit/) (Networking)

- [Moshi](https://github.com/square/moshi) (Json Parsing)

- [Okhttp](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp) (Logging Interceptor)

- [Chucker](https://github.com/ChuckerTeam/chucker) (Network Debugging)

- [Glide](https://github.com/bumptech/glide) (Image Loading and caching)

- [Espresso](https://developer.android.com/training/testing/espresso?hl=id) (Espresso)

- [MockK](https://github.com/mockk/mockk) (MockK)


## Screenshots
<img src="https://github.com/Rizqanmr/GithubUserSearch/blob/master/screenshots/Screenshot_Main.jpg" width="25%" alt="Main"></img>
<img src="https://github.com/Rizqanmr/GithubUserSearch/blob/master/screenshots/Screenshot_User_Detail.jpg" width="25%" alt="User_Detail"></img>
<img src="https://github.com/Rizqanmr/GithubUserSearch/blob/master/screenshots/Screenshot_Search_Result.jpg" width="25%" alt="Search_Result"></img>
<img src="https://github.com/Rizqanmr/GithubUserSearch/blob/master/screenshots/Screenshot_Chucker.jpg" width="25%" alt="Chucker"></img>

## NOTES
- Download this project
- Open this project with Android Studio
- Before build and run this project. Go to local.properties and add 2 lines code:

```
BASE_URL = "https://api.github.com"
API_KEY = "Enter your github personal access token"
```

- Sync Project with Gradle File
- Run 

How to get personal access token github
[Here](https://docs.github.com/en/enterprise-server@3.9/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)
