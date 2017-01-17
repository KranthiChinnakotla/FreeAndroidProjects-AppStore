# FreeAndroidProjects-AppStore
Published Free Android Projects Source Code

The source code for free android apps published in Google Play app store is available here.

Project 1: Movie Filter
 
  This application let's you select the movies based on their categories like, most recently release, popular movies etc.
  The TMDB apis, are used. Also local SQL lite data base with content providers is used to save the data and retrieve the data
  when the user wants add movies and delete movies to and from database.
  Async Loaders are used to get the cursor of the database. To perform the back ground task of calling the webservices api and parsing the JSON file is done using Intent Services and broadcast receivers.
