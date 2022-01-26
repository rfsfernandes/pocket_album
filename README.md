# PocketAlbum
This app's purpose is to work as a portable favorite albums compendium.
You can search for artists, get their top albums, see the album details and then add it to your favorites, or remove it.
When you're without internet, fear not! The albums list and the album detail will remain visible!
All the data is gathered from [LastFM Api](https://www.last.fm/api). Props to them.


## Design Pattern
In the development of this app it was used the MVVM design pattern, with a Single Source Of Truth, using the Repository pattern. It is also using Dependency Injection with the help of the Hilt Library.
The updates to the UI are done using an Helper Class called Resource, that tells the UI what to do with the content that class has.

## Architecture
This app has the Clean Architecture with 3 main layers: presentation, domain and data.

## Libraries and Components Used
- Navigation Component;
- Collapsible AppBar Layout;
 - Retrofit;
 - Coroutines;
 - Lifecycle Extensions;
 - Picasso;
 - GSon;
 - Hilt.

## Use Cases
 This are the actions that you can make inside the app.

> #### Search For An Artist 
> When the search is clicked, requests the data from the api, using the `artist.search` method , stores it in the database and then presents the data from the database. When an Artist is selected, goes to the Top Albums of the artist.

>#### Get Top Albums
>When an artist is picked, requests the data from the api, using the `artist.gettopalbums` method, stores it in the database and then presents the data from the database. When an Album is selected, goes to the Album Info.

>#### Get Album Info
>When an album is picked, requests the data from the api, using the `album.getinfo` method, stores it in the database and then presents the data from the database. Here the user can add the album to the favorites and/or remove it, read a description of the album and see the tracks that are present.

>#### Add Album To Favorites
>Saves the album information into the database, when the heart Icon is clicked and isn't filled. It also stores the album image in the device.

>#### Remove Album from Favorites
>Deletes the album information from the database, when the heart Icon is clicked and is filled. It also deletes the album image from the device.

>#### Get Stored Albums
>Returns all the stored albums in a constant stream of data, so the list is always up to date with the changes that occur in the Album table.