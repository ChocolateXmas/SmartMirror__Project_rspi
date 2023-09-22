package smartmirror.RSS.News;
/**
 * Created by chocolate_starfish on 18/11/2017.
 */

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser implements Runnable
{
    static final String TITLE       = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL     = "channel";
    static final String LANGUAGE    = "language";
    static final String COPYRIGHT   = "copyright";
    static final String LINK        = "link";
    static final String AUTHOR      = "author";
    static final String ITEM        = "item";
    static final String PUB_DATE    = "pubDate";
    static final String GUID        = "guid";

    URL url;

    private Thread internalThread;

    public RSSFeedParser(String feedUrl)
    {
        if( isNetUp() )
        {
            try
            {
                this.url = new URL(feedUrl);
                System.out.println("> YAY There's is connection to the internet.");
//            throw new Exception("> No connection...");
            }
            catch (MalformedURLException e)
            {
//            System.out.println("> No connection to the internet ! Please make a connection.");

                throw new RuntimeException("> No connection...");
//            throw new RuntimeException(e);
            }
        }
        else
            System.out.println("> No Connection to the internet, Retrying in 1 min . . .");


        internalThread = new Thread(this);
        System.out.println("> RSS Thread has been started!");
        internalThread.start();
    }

    private boolean netIsAvailable()
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

    private boolean isNetUp()
    {
        boolean indicator;
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com", 80);
        try
        {
            sock.connect(addr, 3000);
            indicator = true;
        }
        catch (Exception e) { indicator = false; }
        finally
        {
            try
            {
                sock.close();
            }
            catch (Exception e) {}
        }
        return indicator;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            System.out.println("@ RSSFeedParser Thread > in run() - still going . . .");

            try
            {
                if( isNetUp() )
                {
                    System.out.println("@                      > Connection has been found! checking again in 1 min . . .");
                    Thread.sleep(1000 );
                }
                else
                {
                    System.out.println("@                      > Connection has **NOT*** been found! checking again in 1 min . . .");
                    Thread.sleep(1000 );
                }
            }
            catch ( InterruptedException x )
            {

                Thread.currentThread().interrupt();
            }
        }
    }

    public Feed readFeed()
    {
        Feed feed = null;

        if( isNetUp() )
        {
            try
            {
                boolean isFeedHeader = true;
                // Set header values intial to the empty string
                String description = "";
                String title       = "";
                String link        = "";
                String language    = "";
                String copyright   = "";
                String author      = "";
                String pubdate     = "";
                String guid        = "";

                // First create a new XMLInputFactory
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();

                // Setup a new eventReader
                InputStream in = read();
                if( in == null )
                    return null;

//                in.reset();
//                in = read();
                XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

                // read the XML document
                while ( eventReader.hasNext() )
                {
                    XMLEvent event = eventReader.nextEvent();
                    if( event.isStartElement() )
                    {
                        String localPart = event.asStartElement().getName().getLocalPart();
                        switch( localPart )
                        {
                            case ITEM:
                                if( isFeedHeader )
                                {
                                    isFeedHeader = false;
                                    feed = new Feed(title, link, description, language, copyright, pubdate);
                                }
                                event = eventReader.nextEvent();
                                break;

                            case TITLE:
                                title = getCharacterData(event, eventReader);
                                break;

                            case DESCRIPTION:
                                description = getCharacterData(event, eventReader);
                                break;

                            case LINK:
                                link = getCharacterData(event, eventReader);
                                break;

                            case GUID:
                                guid = getCharacterData(event, eventReader);
                                break;

                            case LANGUAGE:
                                language = getCharacterData(event, eventReader);
                                break;

                            case AUTHOR:
                                author = getCharacterData(event, eventReader);
                                break;

                            case PUB_DATE:
                                pubdate = getCharacterData(event, eventReader);
                                break;

                            case COPYRIGHT:
                                copyright = getCharacterData(event, eventReader);
                                break;
                        }
                    }
                    else if( event.isEndElement() )
                    {
                        if( event.asEndElement().getName().getLocalPart() == (ITEM) )
                        {
                            FeedMessage message = new FeedMessage();
                            message.setAuthor(author);
                            message.setDescription(description);
                            message.setGuid(guid);
                            message.setLink(link);
                            message.setTitle(title);
                            feed.getMessages().add(message);
                            event = eventReader.nextEvent();
                            continue;
                        }
                    }
                }
            }
            catch (XMLStreamException e)
            {
//                throw new RuntimeException(e);
            }
        }

        return feed;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException
    {
        String result = "";
        event = eventReader.nextEvent();
        if( event instanceof Characters )
        {
            result = event.asCharacters().getData();
        }
        return result;
    }

    @Nullable
    private InputStream read()
    {
        if(  isNetUp() )
        {
            System.out.println("> Connection is out there . . .");
            try
            {
                url = new URL(RSSNews.getNews_url());
                return url.openStream();
            } catch (SocketException e) { e.printStackTrace(); }
              catch (IOException e)
              {
//                throw new RuntimeException(e);
                  return null;
              }
        }
        else
            System.out.println("> Nah hell naw");
        return null;
    }

}
































