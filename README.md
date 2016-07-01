# app
The goal of this app is to search a database of songs for those tagged with coordinates within a certain range. The process I have in mind is:
1. Acquire location coordinates of user (Latitude and Longitude from a GPS Tracker class)
2. Pass them to a php page via httpurlconnection
3. Get back json data from that php page
4. Convert the json data into an array
5. Use the custom adapter to apply that array to the listview in the main layout file with a custom row layout.

I do steps 2-5 within an executed AsyncTask.

Furthermore, if you have any information on how to use the media player, that'd be great. I have it set up in the custom adapter so that each button is associated with the specific file of that row.

It's my first fully built, custom app. It's not pretty code, but I tried to piece together different things in accordance with the steps above. Any help is greatly appreciated!!
