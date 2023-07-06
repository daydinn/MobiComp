## Overview

  Name: Recipe Rhapsody
  Recipe Phapsody is a mobile app that allows users to search recipes from the Spoonacular database, to save them and to manage them. 
  The app provide a wide range of search option for very specific searches, but also suits well for easy searches.

## Components
 ### Recipe Class
This class contains all information the app needs from a recipe. This class was created with the json result of the Spoonacular API result in mind and therefore 
has an Ingredient subclass and a Nutrition subclass that contains a Nutrient subclass. Additionally the class contains a favorite value.

  ### Shortinfo class
When Spoonacural API returns a list of recipe – like search results – each recipe contains an id, a name and an image URL. 
The ShortInfo class exists to store these values and show them on search result page or in the cooking book. Additionally the class contains a favorite value. 
    
  ### SearchManager
The SearchManager class takes care of all search requests and the connection to the Spoonacular API. It uses the OkHttp library to set up an 
http client for API connection. The SearchManager provides different methods including: Starting a complex search request including all data from the search page; 
a quick search that uses the complex search, but reduce it to the recipe name; getting similar recipes as the one provided by id (this one eventually is never used 
in the app); getting random recipes; getting a certain recipe by its id; getting test data. 
The class also has a method that takes all the values from the search page and turns them into a valid URL for the complex search.
    
 ### DBHandler
The DBHandler class takes care of the persistence of the app. It creates a new SQLite database, 
if not already existent and provides differentmethods to read and write data.
The database contains two tables, on for online recipes and one for offline recipes. 
For both tables DBHandler provides: Adding a new recipe; checking if recipe already exists; getting all existing recipes; getting all existing recipes that 
are set as favorite; getting latest added recipes; updating favorite information; checking if a certain recipe is favorite; deleting a certain recipe. 
The offline table also provides a method to get a recipe by its id.
    
  ### MainActivity
Holds a navigation bar, a toolbar and the online/offline state of the app and loads other pages as fragments.
    
  ### Navigation bar
Located at bottom of the app, providing navigation to home, search, cooking book and favorite list pages.
    
  ### Toolbar
Located at top of the app, providing the name of the current page and an option menu used for switching between online and offline mode, saving/removing 
recipes and favorites and sharing recipes.
    
  ### HomeFragment
The First page seen when starting the app. Shows a search bar for a quick search including only the recipe name and ten random recipes when in online mode. 
Shows up to ten recently added recipes in both modes.
    
  ### SearchFragment
This page allows the user to set a large range of search filters and eventually start the search and navigate to SearchResultFragment.
    
  ### SearchResultFragment
This page displays the found recipes and shows each one in its own card.
By clicking on a card, the user can navigate to the detailed view of the respective recipe.
    
  ### RecipePageFragment
This page shows a recipe with all its details, including ingredients, cooking time, region and several diets/intolerances and nutrients. 
The user can use the toolbar to save or remove and online and/or offline version of the current recipe, set saved recipes as favorites and to share a link 
to this recipe with friends.
    
  ### CookingBookFragment
This pages displays a list of all saved recipes, each in a card. Depending on offline/online mode it shows those recipes stored in the database for online 
respectively offline recipes.
By clicking on a card the user navigates to the detailed view of the respective recipe.
At the same time this page is the favorite list, sharing the same functionality, but only displaying those recipes that are set as favorites.

## Libaries
These external libraries are used for this project:
  - Gson – Used for parsing API results (json objects) into Recipe or ShortInfo class objects.
      Also used for parsing Recipe objects into json and vice versa for storing and loading offline recipes.
  - Picasso – Used to put images from URLs into ImageViews
  - OkHttp – Used to connect to the Spoonacular API via http/s

## Functions
Provides online and offline mode
  
### Online-Mode
- View of randomly suggested recipes on start page
- View of latest added recipes on start page
- Quick search for recipe name on start page
	-Several search options on search page
	+ Recipe name
	+ Ingredients
	+ Excluded ingredients
	+ Cuisine/Region
	+ Diets
	+ Intolerances
	+ Dish type
	+ Max. cooking time
	+ Several macro nutrients
	+ Several micro nutrients
	+ Several vitamins
- View of search results
- Detailed view of a recipe
- Save/Remove of online recipes 
- Save/Remove of offline recipes
- Adding saved online recipe to (online) favorites
- Recipe Sharing
- View of saved online recipes in cooking book
- View of (online) favorites in favorite list

### Offline-Mode
- View of latest added recipes on start page 
- Detailed view of a recipe
- Save/Remove of online recipes 
- Save/Remove of offline recipes
- Adding saved offline recipe to (offline) favorites
- Recipe Sharing
- View of saved offline recipes in cooking book
- View of (offline) favorites in favorite list


