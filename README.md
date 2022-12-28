# Test_Twitter
This code is a JavaFX application that searches for tweets on Twitter based on a given search string. When the "Search" button is clicked, the fetchTweets method is called with the search string from the text field as an argument. 
This method uses the Twitter4J library to authenticate with the Twitter API using the provided keys and secrets, and then performs a search on Twitter using the given search string. 
The search results are returned as a list of Status objects, which represent tweets. These tweets are then added to the tweetListView list view to be displayed in the UI.
The fetchTweets method performs the search in a loop, in order to retrieve more than the default number of tweets returned by a single search. 
The loop continues until the desired number of tweets is reached, or until there are no more tweets to retrieve.
The lastID variable is used to keep track of the ID of the last tweet retrieved, in order to avoid retrieving the same tweets multiple times.

This code also imports various other classes from the JavaFX and Twitter4J libraries, such as Stage, Scene, FlowPane, TextField, Button, Insets, EventHandler, List, and ArrayList, which are used to create the UI and interact with the Twitter API.
