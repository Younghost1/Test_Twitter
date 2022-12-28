import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.List;
import twitter4j.*;
import java.util.ArrayList;
import javafx.scene.control.*;

// import twitter4j.Status;
// import twitter4j.TwitterException;
// import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetFetcher extends Application {
   // GUI controls
   private TextField tfSearch;
   private Button btnSearch;
   private ListView<Status> tweetListView;

   // Constants for API keys and secrets
   private static final String CONSUMER_KEY = "MZWiPCVj0krFlN9MjaYH1VOuz";
   private static final String CONSUMER_SECRET = "E4wCRHsB2HLEz97YusTohoHsplgqt70Z7BV3xUnLKZtixkNmfJ";
   private static final String ACCESS_TOKEN = "1511467391682859009-5AWQURJVI7iGskDvOgmqI6OpaY43Qi";
   private static final String ACCESS_TOKEN_SECRET = "AkALbcLDuRYnExe8yc0kMWPR6HtO2y8fWXxOUd8z9vaFA";

   //public static void main(String[] args) {
       //launch(args);
   //}

   @Override
   public void start(Stage primaryStage) {
       // Window title
       primaryStage.setTitle("Twitter Client");

       // Search text field
       tfSearch = new TextField();
       tfSearch.setPrefColumnCount(20);
       tfSearch.setPromptText("Enter a search string");

       // Search button
       btnSearch = new Button("Search");

       // Tweet list view
       tweetListView = new ListView<>();

        // Handle button event
       btnSearch.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               try {
                   String searchString = tfSearch.getText();
                   List<Status> tweets = fetchTweets(searchString);
                   tweetListView.getItems().setAll(tweets);
               } catch (TwitterException e) {
                   e.printStackTrace();
               }
           }
       });

       // Pane
       FlowPane pane = new FlowPane();
       pane.setHgap(5);
       pane.setVgap(5);
       pane.getChildren().addAll(tfSearch, btnSearch, tweetListView);
       
       pane.setPadding(new Insets(10, 10, 10, 10));

       // Scene
       Scene scene = new Scene(pane);
       primaryStage.setScene(scene);
       primaryStage.sizeToScene();
       primaryStage.show();
   }

   public static List<Status> fetchTweets(String searchString) throws TwitterException {
       ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
       configurationBuilder.setDebugEnabled(false);
       configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
       configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);
       configurationBuilder.setOAuthAccessToken(ACCESS_TOKEN);
       configurationBuilder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
       //configurationBuilder.setUseSSL(true);
       TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
       Twitter twitter = tf.getInstance();
       Query query = new Query(searchString);
       int desiredTweetCount = 12;
       long lastID = Long.MAX_VALUE;
       ArrayList<Status> tweets = new ArrayList<Status>();
       while (tweets.size () < desiredTweetCount) {
           if (desiredTweetCount - tweets.size() > 100)
           query.setCount(100);
           else 
           query.setCount(desiredTweetCount - tweets.size());
           try {
               QueryResult result = twitter.search(query);
               tweets.addAll(result.getTweets());
               System.out.println("Gathered " + tweets.size() + " tweets");
               for (Status t: tweets) 
               if(t.getId() < lastID) lastID = t.getId();
           }
           catch (TwitterException te) {
               System.out.println("Couldn't connect: " + te);
           }; 
           query.setMaxId(lastID-1);
       }
       return tweets;
   }
}