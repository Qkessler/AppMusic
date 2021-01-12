<h1 align="center">AppMusic</h1>

:musical_note: Maven Desktop App created with functionality that allows for xml
importing, and implemented with patterns from the GoF Design Patterns book. 

All the code has been refactored in order to allow for a better code reading time
and easier reviewing in the future.

## Summary

- [Structure](#structure)
- [Functionality](#functionality)
- [Patterns](#patterns)
- [Contributing](#contributing)
- [Development](#development)
- [Tests](#tests)
- [Licence](#licence)

## Structure
The structure of AppMusic can be appreciated in the subsequent image:

<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/diagrama_profesores.png" alt="AppMusic structure">
</p>

## Functionality
If we had to give a functionality list it would be the following:

###  :file\_folder: Persistence
The Persistence was configured using the H2 engine, which provides with the 
possibility to register entities, and allows for editing certain values of the
mentioned entities. The important information regarding the H2 documentation can 
be found at: 

- [H2 official documentation](https://www.h2database.com/html/main.html)
- [H2 Github](https://github.com/h2database/h2database)

###  :computer:	UI
The UI was created using the Swing Framework. Swing allows for different elements which
composes every "view". The views in our app are composed of JFrame, which may or may not
have different pannels inside, containing the relevant "Components". Examples of the views:


<p align="center">
  <img width="320" src="https://github.com/Qkessler/AppMusic/blob/main/src/registro.png" alt="Register View">
</p>
<h5 align="center">Register View</h5>


<p align="center">
  <img width="200" src="https://github.com/Qkessler/AppMusic/blob/main/src/login.png" alt="Login View">
</p>
<h5 align="center">Login View</h5>



<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/recents.png" alt="Main View">
</p>
<h5 align="center">Main View</h5>


### :mag: Search Songs
You have the possibility to access the the search songs tab, which allows you to
access all the songs that have been persisted and play a certain one. You could also
play songs by double clicking them in this tab. The search possibilities are with the
following sections:

- Genre: Genres are calculated dinamically with the keys read while parsing the xml files.
- Artist: Search by the name of the artist.
- Title: Search by the name of the song.

<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/explore1.png" alt="Filtering by genre">
</p>
<h5 align="center">Filtering by genre example</h5>


<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/explore2.png" alt="Filtering by artist and title">
</p>
<h5 align="center">Filtering by artist and title example</h5>

### :arrows\_counterclockwise: Recent Songs
The Recent songs tab is the default tab when you enter the app and contains the list of the
10 recently played songs. You could still access the media functionality.

<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/recents.png" alt="Recent Songs Tab">
</p>
<h5 align="center">Recent Songs Tab</h5>

### :twisted\_rightwards\_arrows: PlayLists
Following the functionality list, playlist functionality has been
implemented. While being a normal user, you have access to creation of ilimited
playlists (New Playlist Tab) and media functionality for your created playlists
on the My Lists Tab. 

If you upgraded your account (fictional upgrade), you would be applied a discount code
depending on defined codes being applicable. When multiple codes are applicable, the highest
discount is applied.

<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/newplaylist1.png" alt="New Playlist confirmation">
</p>
<h5 align="center">New Playlist confirmation</h5>


<p align="center">
  <img width="600" src="https://github.com/Qkessler/AppMusic/blob/main/src/newplaylist2.png" alt="Playlist Creation UI">
</p>
<h5 align="center">Playlist Creation UI</h5>


## Patterns

:warning: Working on this section.

## Contributing

Contributions are warmly welcomed. Doesn't have to be implementing new functionality, issues are also opened for documentation, support, etc. 

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Development

:warning: Working on this section.

## Tests

:warning: Working on this section.

## Licence
This project is licenced under the [MIT](https://github.com/Qkessler/AppMusic/blob/main/LICENSE) Licence - see the [LICENCE](https://github.com/Qkessler/AppMusic/blob/main/LICENSE) file for details.
