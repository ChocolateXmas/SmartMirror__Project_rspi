/**
 * Created by chocolate_starfish on 01/02/2018.
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkStatus
{

    static boolean isNetAvailable()
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
}
