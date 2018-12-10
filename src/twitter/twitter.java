package twitter;
import twitter4j.*;

import twitter4j.conf.ConfigurationBuilder;
import java.io.File;
import java.util.List;

//Authors: Jenil Thakker, Daanyal Saeed, Raghav Gupta

public class twitter {
	public static void main (String[] args) throws TwitterException{
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true)
				.setOAuthConsumerKey("oEuSJMZwmkoIQCyiGDhmGfCye")
				.setOAuthConsumerSecret("BEoz8SLhcjVt6Ow4bqu4YCJaGSOcUB8BUJ6i7pAHDqkgkkhxnt ")
				.setOAuthAccessToken("1410286200-LgsuaFi5plxhzmeXD43mS8aJp4hoK8NeLuieMCS")
				.setOAuthAccessTokenSecret("jevA2VYepNEYweigyzULJduwsif1ZpzCA1MWnbl6fo8nC");
				
		TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
		
		twitter4j.Twitter twitter = twitterFactory.getInstance();
		
		//First API: Getting all the tweets on the timeline. Authored by: Jenil Thakker
		List<Status> statuses = twitter.getHomeTimeline();
		for(Status status: statuses){
			System.out.println(status.getUser().getName()+" tweeted "+status.getText());
		}
		
		//Second API: Messaging another active peer. Authored by: Raghav Gupta
		DirectMessage dm = twitter.sendDirectMessage(twitter.getScreenName(), "Hello: This is just a test.");
		System.out.println("\""+dm.getText() +"\" was sent to @" + dm.getRecipientScreenName());
		
		//Third API: Posting a tweet on the twitter feed. Authored by: Daanyal Saeed
		Status tweet = twitter.updateStatus("This is just a test tweet.");
	    	System.out.println("The test tweet is: " + tweet.getText());
	    
	    //Fourth API: Using keywords to search for tweets such as  "Donald" and "women". Authored by Jenil Thakker
	  	Query query = new Query("Donald"+"women");
        QueryResult qr = twitter.search(query);
        System.out.println("Showing results for \"Donald\" and \"women\":");
        System.out.println();
        for (Status s : qr.getTweets()) {
            System.out.println("@" + s.getUser().getScreenName() + ":" + s.getText());
        }
		
        //Fifth API: How to get followers on twitter. Authored by: Daanyal Saeed.
		IDs id = twitter.getFollowersIDs(twitter.getScreenName(), -1);
        long[] list = id.getIDs();
        System.out.println(twitter.getScreenName()+" is followed by:");
        System.out.println();
        int counter = 1;
        for (long i : list) {
            twitter4j.User user = twitter.showUser(i);
            System.out.println( counter +". "+ user.getScreenName()+" follows you...");
            counter++;
        }

      //Sixth API: Crawling twitter followers of followers. Authored by: Raghav Gupta.
      for (long y : list) {
          twitter4j.User user = twitter.showUser(y);
          IDs ids = twitter.getFollowersIDs(user.getScreenName(), -1);
          System.out.println(user.getScreenName()+" is followed by:");
          long[] idList = ids.getIDs();
          int i=1;
          for (long a : idList) {
              System.out.println(i + ": " + twitter.showUser(a).getScreenName());
              i++;
          }
       }  
		
      //Seventh API: Geolocations. Authored by: Jenil Thakker
	   ResponseList<Location> location;
       location = twitter.getAvailableTrends();
       int count=1;
       	for (Location l : location) {
            System.out.println(count +". "+ l.getName() + "in the"+ l.getCountryName());
            count++;      
        }
       	
        //Eighth API: User timeleine. Authored by: Raghav Gupta.
        ResponseList<Status> tz = twitter.getUserTimeline(twitter.getScreenName());
        for(Status b: tz) {
            System.out.println(twitter.getScreenName() +" posted "+b.getText()+" on "+b.getCreatedAt()+ ". It was retweeted "+b.getRetweetCount()+" times!");
            }

		//Nineth API: Change profile picture. Authored by: Jenil Thakker.
       	File f = new File("/Users/jenilthakker/Desktop/Important_Docs/30629192_1646765335359206_4678008190447648768_n.jpg");
        twitter.updateProfileImage(f);
        System.out.println("Profile picture has been updated!");
  }

}
