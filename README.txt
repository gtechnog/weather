Attachment: 
1. Sample video is attached for reference
2. ARCHITECTURE.jpg is attached for architecture

I have made TODO to some problems and possible improvements for them.

Some Known Problems: 
1. Keep turn on the location before using the app otherwise it will remain in loading state as this case is not handled (Not listening to GPS on/off changes currently)
2. If User denied the permission it stuck on empty screen, could be better handle with snack bar or something
3. Loading progress bar is cutting at corners
4. City Name is not proper as api is returning empty city/region/country name while searching with locations
5. Not listening GPS on/off changes
6. Single Responsibility Principle: Activity's view model should get only location and apixu network call should be made by only fragment's view model Ex. Assign responsibility of getting temperature to TempDetailViewModel from CurrentWeatherViewModel
7. I have used the font-family proper but didn't check whether they are available for api level 16
8. character in font-style might be
9. Recyclerview Divider line is missing