package smartmirror.RSS.News; /**
 * Created by chocolate_starfish on 29/11/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class RSSNews extends JPanel implements Runnable
{

    /** The url that has the RSS News Feed channel **/
    /** ------------------------------------------ **/
    private static final String news_url = "https://www.ynet.co.il/Integration/StoryRss2.xml";
    /** ------------------------------------------ **/
    /**       Getters and Setters for News_Url     **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static String getNews_url() { return news_url; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**                 End of Story               **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/


    /** X and Y coordinates of the News JLabel **/
    /** -------------------------------------- **/
    private static int           x,y;
    /** -------------------------------------- **/
    /**     Getters and Setters for X and Y    **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static int  getX_Coord(           ) { return x;    }
    public static void setX_Coord(int x_coord) { x = x_coord; }
    public static int  getY_Coord(           ) { return y;    }
    public static void setY_Coord(int y_coord) { y = y_coord; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**              End of Story              **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/


    /** The string that adds to itself the info from the xml rss feed **/
    /** ------------------------------------------------------------- **/
    private static String        news_txt = "";
    /** ------------------------------------------------------------- **/
    /**               Getters and Setters for String news             **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static String getNews_txt(             ) { return news_txt;   }
    public static void   setNews_txt(String newStr) { news_txt = newStr; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**                          End of Story                         **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

    /** Default Font for the News JLabel **/
    /** -------------------------------- **/
    private static Font          defaultFont = new Font("Arial", Font.PLAIN, 16);
    /** -------------------------------- **/
    /** Getters and Setters for defaultFont **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static Font getDefaultFont() { return defaultFont; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**           End of Story              **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

    /** Calculates the width of the News JLabel in pixels **/
    /** ------------------------------------------------- **/
    private static int           width;
    /** ------------------------------------------------- **/
    /**           Getters and Setters for width           **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static int  getWidthPixel(            ) { return width;     }
    public static void setWidthPixel(int newWidth) { width = newWidth; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**                    End of Story                   **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/


    /** Feed Object - reads the String from the xml news server **/
    /** ------------------------------------------------------- **/
    private static Feed          feed; //
    /** ------------------------------------------------------- **/
    /**               Getters and Setters for feed              **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static Feed getFeed(            ) { return feed;    }
    public static void setFeed(Feed newFeed) { feed = newFeed; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**                        End of Story                     **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/


    /** Makes the connection to the Internet RSS Link **/
    /** --------------------------------------------- **/
    private static RSSFeedParser parser; // Makes the connection to the Internet RSS Link
    /** --------------------------------------------- **/
    /**           Getters and Setters for parser      **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    public static RSSFeedParser getParser(                       ) { return parser;      }
    public static void          setParser(RSSFeedParser newParser) { parser = newParser; }
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
    /**                   End of Story                **/
    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

    private static boolean isNewsEmpty; // true -> string = "אין חיבור לרשת" \\ false - > string = newsFeed.Messages
    public  static boolean getEmptyStatus() { return isNewsEmpty; }
    public  static void    setEmptyStatus(boolean newStatus) { isNewsEmpty = newStatus; }

    private Thread  thread /** thread - self class thread for moving JLabel from side to side **/;
    private Auto_Updater_Thread_RSS Updater = new Auto_Updater_Thread_RSS("RSS Auto-Updater -> Thread", this) /** Updater - every one hour it connects to the News RSS Server and updates the info. **/;

    // Constructor
    public RSSNews() throws InterruptedException
    {
        __init();

        parser = new RSSFeedParser( news_url );

//

        if( netIsAvailable() )
        {
            System.out.println(">> No Problem with the smartmirror.RSS.News.RSSNews JPanel.");

        }
        else
            System.out.println(">> Problem with the smartmirror.RSS.News.RSSNews JPanel.");

        Updater.start();
    }

    public void __init()
    {
        setLayout( null );
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }

    public void StringRead()
    {
        parser = new RSSFeedParser( news_url );
        feed = parser.readFeed();

        if( feed != null )
        {
//                            news_txt = "";
            for (FeedMessage message : feed.getMessages())
                news_txt += message;

            System.out.println(news_txt);

//                            isNewsEmpty = false;

            width = (int) defaultFont.getStringBounds(news_txt, new FontRenderContext(defaultFont.getTransform(), false, false)).getBounds().getWidth();
//                          System.out.println("Widht: " + width);
            x = 0 - width;
            y = 150;
            repaint(); // Activates the *paint* function to ReDraw the string of the news
//            System.out.println("\n> " + Updater.getName() + " has been started ! ");
            System.out.println("> The info has been updated.\n");
        }
    }

//    public void horizonScroll() throws InterruptedException
//    {
//        while(true)
//        {
//            x = 0 - width;
//            while( x <= getWidth() )
//            {
//                x++;
//                y  = getHeight() / 2;
//                repaint();
//                Thread.sleep(17);
//            }
//            x = 0 - width;
//            while( x <= 0 )
//            {
//                x++;
//                y  = getHeight() / 2;
//                repaint();
//                Thread.sleep(17);
//            }
//            while( x >= 0 && x != getWidth() )
//            {
//                x++;
//                y  = getHeight() / 2;
//                repaint();
//                Thread.sleep(17);
//            }
//        }
//    }

    private static boolean netIsAvailable()
    {
        try
        {
            final URL url_test = new URL("http://www.google.com");
            final URLConnection conn = url_test.openConnection();
            conn.connect();
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            if( isNewsEmpty )
            {
               x = getWidth() / 2;
               y = 30;
               repaint();
               try
               {
                   Thread.sleep(17);
               }
               catch (InterruptedException e)
               {
                   e.printStackTrace();
               }
            }
            else
            {
                x = 0 - width;

                while( x <= getWidth() )
                {
                    x++;
                    y  = getHeight() / 2;
                    repaint();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                x = 0 - width;

                while( x <= 0 )
                {
                    x++;
                    y  = getHeight() / 2;
                    repaint();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while( x >= 0 && x != getWidth() )
                {
                    x++;
                    y  = getHeight() / 2;
                    repaint();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    public static void paintAgain()
//    {
//        repaint();
//    }

    @Override
    public void paint(Graphics g)
    {

        g.setColor(Color.black); // The Background color
        g.fillRect( 0, 0, getWidth(), getHeight() ); // Drawing the Rectangle (BLACK COLOR)
        g.setColor(Color.white); // Font color
        g.setFont(defaultFont); // Font setup
        g.drawString(news_txt, x, y); // to draw the String
//        System.out.println("X: " + x + ",  Y: " + y); /** For Logging **/
    }
}
