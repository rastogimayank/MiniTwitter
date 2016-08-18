package database.dummy.dump;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mayank.ra on 17/08/16.
 */
public class TweetParser {
    public static final Pattern mentionPattern = Pattern.compile("@[a-zA-Z0-9_]+");
    public static final Pattern hashtagPattern = Pattern.compile("#[0-9_]*[a-zA-Z]+[a-zA-Z0-9_]*");
    public static final Pattern urlPattern = Pattern.compile("((http(s)?:\\/\\/)|(www\\.)).+");
    public static final Pattern usernamePattern = Pattern.compile("[0-9]*[a-zA-Z_]+[a-zA-Z0-9_]*");

    private String tweet;

    private String userName;
    private boolean retweet;
    private String[] spaceSeparatedWords;

    private List<String> allWordsInTweet = new ArrayList<>();
    private List<Integer> mentions = new ArrayList<>();
    private List<Integer> hashtags = new ArrayList<>();
    private List<Integer> urls = new ArrayList<>();
    private List<Integer> withoutSpaceWords = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public List<String> getAllWordsInTweet() {
        return allWordsInTweet;
    }

    public List<Integer> getMentions() {
        return mentions;
    }

    public List<Integer> getHashtags() {
        return hashtags;
    }

    TweetParser(String s) {
        tweet = s;
    }

    public void parseTweet() {
        if (tweet != "") {
            spaceSeparatedWords = tweet.split(" ");
            markIfRetweet();
            extractAll();
        }
    }

    private void markIfRetweet() {
        if (spaceSeparatedWords.length > 2 && spaceSeparatedWords[1].equals("RT")) {
            retweet = true;
            return;
        }
        retweet = false;
    }

    public boolean isRetweeet() {
        return retweet;
    }

    public void displayWordsInTweet() {
        Integer index = 0;
        for (String word : allWordsInTweet) {
            System.out.println(index + "  : " + word);
            index++;
        }

        System.out.println("To be printed without spaces :");
        for (Integer i : withoutSpaceWords) {
            System.out.println(i);
        }

        System.out.println("Mentions :");
        for (Integer i : mentions) {
            System.out.println(i);
        }

        System.out.println("Hashtags :");
        for (Integer i : hashtags) {
            System.out.println(i);
        }

        System.out.println("URLs :");
        for (Integer i : urls) {
            System.out.println(i);
        }
    }

    public void extractAll() {
        Integer urlStart, mentionStart, hashtagStart, index = 0;
        for(int i = 0; i < spaceSeparatedWords.length; i++) {
            urlStart = getStartIndexOfURL(spaceSeparatedWords[i]);
            mentionStart = getStartIndexOfMention(spaceSeparatedWords[i]);
            hashtagStart = getStartIndexOfHashtag(spaceSeparatedWords[i]);

            Integer minStart = Integer.min(urlStart, Integer.min(mentionStart, hashtagStart));
            if (minStart == urlStart) {
                if (urlStart > 0) {
                    allWordsInTweet.add(spaceSeparatedWords[i].substring(0, urlStart));
                    index++;
                    withoutSpaceWords.add(index);
                }
                allWordsInTweet.add(spaceSeparatedWords[i].substring(urlStart));
                urls.add(index);
                index++;
            } else if (minStart == mentionStart) {
                Integer mentionEnd = getEndIndexOfMention(spaceSeparatedWords[i]);
                String before, mention, after;
                if (mentionStart > 0) {
                    allWordsInTweet.add(spaceSeparatedWords[i].substring(0, mentionStart));
                    index++;
                    withoutSpaceWords.add(index);
                }
                mention = spaceSeparatedWords[i].substring(mentionStart, mentionEnd);
                if (spaceSeparatedWords[i].length() > mentionEnd) {
                    withoutSpaceWords.add(index + 1);
                    spaceSeparatedWords[i] = spaceSeparatedWords[i].substring(mentionEnd, spaceSeparatedWords[i].length());
                    i--;
                }
                allWordsInTweet.add(mention);
                mentions.add(index);
                index++;
            } else if (minStart == hashtagStart) {
                Integer hashtagEnd = getEndIndexOfHashtag(spaceSeparatedWords[i]);
                if (hashtagStart > 0) {
                    allWordsInTweet.add(spaceSeparatedWords[i].substring(0, hashtagStart));
                    index++;
                    withoutSpaceWords.add(index);
                }
                String hashtag = spaceSeparatedWords[i].substring(hashtagStart, hashtagEnd);
                if (spaceSeparatedWords[i].length() > hashtagEnd) {
                    withoutSpaceWords.add(index + 1);
                    spaceSeparatedWords[i] = spaceSeparatedWords[i].substring(hashtagEnd, spaceSeparatedWords[i].length());
                    i--;
                }
                allWordsInTweet.add(hashtag);
                hashtags.add(index);
                index++;
            } else if (!spaceSeparatedWords[i].equals("")) {
                allWordsInTweet.add(spaceSeparatedWords[i]);
                index++;
            }
        }
        Iterator<String> iter = allWordsInTweet.iterator();
        userName = iter.next();
        userName = userName.substring(0, userName.length() - 1);
    }

    public Integer getStartIndexOfMention(String s) {
        Matcher match = mentionPattern.matcher(s);
        if (match.find())
            return match.start();
        return Integer.MAX_VALUE;
    }

    public Integer getEndIndexOfMention(String s) {
        Matcher match = mentionPattern.matcher(s);
        if (match.find())
            return match.end();
        return null;
    }

    public Integer getStartIndexOfHashtag(String s) {
        Matcher matcher = hashtagPattern.matcher(s);
        if (matcher.find())
            return matcher.start();
        return Integer.MAX_VALUE;
    }

    public Integer getEndIndexOfHashtag(String s) {
        Matcher matcher = hashtagPattern.matcher(s);
        if (matcher.find())
            return matcher.end();
        return null;
    }

    public Integer getStartIndexOfURL(String s) {
        Matcher matcher = urlPattern.matcher(s);
        if (matcher.find())
            return matcher.start();
        return Integer.MAX_VALUE;
    }

    public boolean checkValidUser() {
        Matcher matcher = usernamePattern.matcher(userName);
        if (matcher.find())
            return true;
        return false;
    }

    public String[] listToArrayOfWords() {
        String[] words = new String[100];
        Iterator<String> iter = allWordsInTweet.iterator();
        int index = 0;
        while(iter.hasNext()) {
            words[index] = iter.next();
            index++;
        }
        return words;
    }

    public static void main(String[] args) {
        String tweet = "rondavies: RT @znmdthemovie: WATCH AND SHARE...GET A " +
                "CHECK @khnhthemovie-https://www.google.com #greatMovie UP...MAKE SURE THE WOMEN #awesome" +
                " AROUND YOU www.amazon.in/@media.net ARE CANCER FREE http://t.co/4xkitJIa";
        String tweet2 = "rondavies: RT @RealRonHoward: “@jeca332: @RealRonHoward  I adore Hans Zimmers music.  He is amazing!”find his music @ #Rushmovie.com";
        TweetParser tp = new TweetParser(tweet);

        System.out.println(tweet);
        tp.parseTweet();
        if (tp.isRetweeet())
            System.out.println("yes, it is a retweet");
        tp.displayWordsInTweet();
    }
   
}

