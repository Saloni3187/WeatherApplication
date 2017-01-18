# WeatherApplication

WeatherApp Application has been designed to provide weather data details specific to a city & region. The application retrieves the Weather Details in two .
Key Features:
1) Application can be run in “development” & “production” mode by changing the application.properties file src/assets.
   To run application in development mode, keep “environment=development” To run application in production mode, keep “environment=production"
2) Application implements Dependency Injection by using Dagger 2.
3) While running application in development mode, data is read from Json file placed in src/assets folder, whereas in production mode it displays real time data fetched from free weather api.
4) Following free weather API has been used to fetch data for application in production mode.
   http://api.openweathermap.org/data/2.5/forecast/daily?q=London&cnt=5&APPID=a0 ec0967e7166a134413ae29c4b3a18e
5) Currently application displays forecast for next 5 days , this can be modified by changing the forecast variable
6) The toggle between development & production mode has been performed by externalizing the environment variable in application.properties.

The Application's GUI performs the following:
1) Displays current weather like Rain, Sunny, Cloudy & current temperature.
2)  Displays weather forecast for the next 5 days. This is configurable as well by changing the Forecast variable.  Fe  atures horizontal display of forecast for next days.
3) Shows proper icons/images pertaining to the weather
4) Shows the maximum, minimum and current temperature.
5) The GUI also shows the current location.
6)  On the click of a forecast tile shows further details pertaining to that location
