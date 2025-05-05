Практическая работа 2. Реализация пользовательского интерфейса.
Необходимые инструменты для сборки APK: Android SDK, Java, и Gradle.

# Android-приложение WeatherApp

## Инструкция по сборке apk-файла

1. Клонирование репозитория
 ``` Shell
 git clone https://github.com/LyubomirChumachenko/WeatherApp_PR2.git
 ```
2. Открытие директории
 ``` cmd
 cd WeatherApp_PR2
 ```
3. Установка переменных окружения (если не установлены в системе)
 ``` cmd
 export JAVA_HOME=/путь/к/jdk
 export ANDROID_HOME=$HOME/Android/Sdk
 export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
 ```
4. Сборка
 ``` cmd
 gradlew.bat assembleDebug
 ```
5. Готовый `.apk` файл будет лежать
 ``` lua
 app\build\outputs\apk\debug\app-debug.apk
 ```

![Главный экран - нет добаленных городов](./screensApp//activity_main.jpg)

![Добавление города - карта](./screensApp//activity_map.jpg)

![Главный экран - добавлен город](./screensApp//city_item.jpg)
