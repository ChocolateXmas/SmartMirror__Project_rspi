package smartmirror.RSS.News; /**
 * Created by chocolate_starfish on 18/01/2018.
 */
import smartmirror.RSS.News.RSSNews;

import java.awt.font.FontRenderContext;

public class Auto_Updater_Thread_RSS extends Thread
{
    private RSSNews RSS_Jpanel;

    private static final int MINUTES_5  = 300000;
    private static final int MINUTES_10 = 600000;
    private static final int MINUTES_15 = 900000;
    private static final int MINUTES_30 = 1800000;

    private static int MinutesToRefresh = MINUTES_5;
    public static void setRefreshRate(int num)
    {
        switch ( num )
        {
            case 5:
                MinutesToRefresh = MINUTES_5;
                break;

            case 10:
                MinutesToRefresh = MINUTES_10;
                break;

            case 15:
                MinutesToRefresh = MINUTES_15;
                break;

            case 30:
                MinutesToRefresh = MINUTES_30;
                break;
        }
    }

    public Auto_Updater_Thread_RSS(String name, RSSNews jpanel)
    {
        super(name);
        this.RSS_Jpanel = jpanel;
    }

    public void run()
    {
        while( true )
        {
            try
            {
                RSSNews.setFeed( RSSNews.getParser().readFeed() ); // feed = parser.readFeed();

                if( RSSNews.getFeed() != null ) // feed != null
                {
                    RSSNews.setEmptyStatus(false);
                    RSSNews.setNews_txt(""); // news_txt = "";
                    for (FeedMessage message : RSSNews.getFeed().getMessages())
                        RSSNews.setNews_txt( RSSNews.getNews_txt() + message ); // news_txt += message;

                    System.out.println( RSSNews.getNews_txt() );

                    RSSNews.setWidthPixel( (int) (RSSNews.getDefaultFont().getStringBounds( RSSNews.getNews_txt(), new FontRenderContext( RSSNews.getDefaultFont().getTransform(), false, false)).getBounds().getWidth())  );
//                          System.out.println("Width: " + width);
                    RSSNews.setX_Coord( 0 - RSSNews.getWidthPixel() ); // x = 0 - width;
                    RSSNews.setY_Coord( 150 ); // y = 150;
                    RSS_Jpanel.repaint(); // Activates the *paint* function to ReDraw the string of the news
                    System.out.println("\n> " + this.getName() + " has been started ! ");
                    System.out.println("> The info has been updated.\n");
//                        Updater.sleep(1000 * 60 * 60);
                    this.sleep( MinutesToRefresh );
                }
                else
                {
                    RSSNews.setEmptyStatus(true);
                    RSSNews.setNews_txt("- אין חיבור לרשת -"); // news_txt = "- אין חיבור לרשת -";

                    System.out.println( RSSNews.getNews_txt() );
                    System.out.println( RSS_Jpanel.getHeight() );

                    RSSNews.setWidthPixel( (int) (RSSNews.getDefaultFont().getStringBounds( RSSNews.getNews_txt(), new FontRenderContext( RSSNews.getDefaultFont().getTransform(), false, false)).getBounds().getWidth())  ); // width = (int) defaultFont.getStringBounds(news_txt, new FontRenderContext(defaultFont.getTransform(), false, false)).getBounds().getWidth();
                    RSSNews.setX_Coord( RSS_Jpanel.getWidth() / 2 ); // x = getWidth() / 2;
                    RSSNews.setY_Coord( 30 ); // y = 150;
//                    RSSNews.setY_Coord( RSS_Jpanel.getHeight() ); // y = 150;
                    RSS_Jpanel.repaint(); // Activates the *paint* function to ReDraw the string of the news
                    System.out.println("\n> " + this.getName() + " has been started ! ");
                    System.out.println("> The info has *NOT* been updated, NO CONNECTION !\n");
//                        Updater.sleep(1000 * 60 * 60);
                    this.sleep(5000 );
                }

            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


}
