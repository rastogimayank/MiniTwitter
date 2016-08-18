import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mayank.ra on 18/08/16.
 */

/**
// * Add mentions to username file and tweets, retweet, damaged tweets to respective files.
// * Add all usernames to the database(authentication )
 * Make connections randomly.(connection ) and build (user_details) table. --UserConnections.java
 * Add tweets to the database (tweets), completing (hashtags), (tweet_mentions).
 * Add retweets to database after searching the correct text. (Retweet)
 * (Media) will remain empty.
 */

public class ParseFile {
    BufferedReader inputReader;
    PrintWriter usernameWriter;
    PrintWriter tweetWriter;
    PrintWriter dumpedTweets;
    PrintWriter retweetWriter;

    public void initialize() throws IOException {
        inputReader = new BufferedReader(new FileReader("allTweets.txt"));
        usernameWriter = new PrintWriter(new BufferedWriter(new FileWriter("usernames.txt", true)));
        tweetWriter = new PrintWriter(new BufferedWriter(new FileWriter("tweets.txt", true)));
        retweetWriter = new PrintWriter(new BufferedWriter(new FileWriter("retweets.txt", true)));
        dumpedTweets = new PrintWriter(new BufferedWriter(new FileWriter("dumpedTweets.txt", true)));
    }

    public void parseSingleTweet(String tweet) {
        TweetParser tweetParser = new TweetParser(tweet);
        tweetParser.parseTweet();
        if (tweetParser.checkValidUser()) {
            String[] allWords = tweetParser.listToArrayOfWords();
            addUserNames(tweetParser, allWords);
            if (!tweetParser.isRetweeet()) {
                tweetWriter.println(tweet);
            } else {
                retweetWriter.println(tweet);
            }
        } else {
            dumpedTweets.println(tweet);
        }
    }

    public void addUserNames(TweetParser tweetParser, String[] allWords) {
        String user = tweetParser.getUserName();
        usernameWriter.println(user);

        List<Integer> mentions = tweetParser.getMentions();
        Iterator<Integer> iterator = mentions.iterator();
        while (iterator.hasNext()) {
            String mention = allWords[iterator.next()];
            String username = mention.substring(1, mention.length());
            usernameWriter.println(username);
        }
    }

    public void closeStreams() {
        usernameWriter.close();
        tweetWriter.close();
        retweetWriter.close();
        dumpedTweets.close();
    }

    public void parseAllTweets() {
        try {
            initialize();
            String input = inputReader.readLine();
            while (input != null) {
                parseSingleTweet(input);
                input = inputReader.readLine();
            }
            closeStreams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ParseFile parseFile = new ParseFile();
        parseFile.initialize();
        parseFile.parseAllTweets();
    }
}
