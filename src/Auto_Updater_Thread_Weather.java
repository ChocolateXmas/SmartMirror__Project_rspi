
/**
 * Created by chocolate_starfish on 01/02/2018.
 */
public class Auto_Updater_Thread_Weather extends Thread
{

    private int RefreshRate;

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

    public Auto_Updater_Thread_Weather(String name)
    {
        super(name);
    }

    public Auto_Updater_Thread_Weather(String name, int RefreshRate)
    {
        super(name);
        this.RefreshRate = RefreshRate;
    }

    public void run()
    {
        while( true )
        {

                try
                {
                    // if there is a connection
                    if( NetworkStatus.isNetAvailable() ) // If Network is available - Connect to API, and make new CONNECTION to the WeatherAPI
                    {
                        API_Connect.makeConnection( Weather.readURL(), Weather.readLatitude(), Weather.readLongitude() );
                        Weather.refreshData();
                        System.out.println("\n&| " + this.getName() + " has been started ! ");
                        System.out.println("&| The info has been updated.\n");
//                        this.sleep( this.RefreshRate );
                        this.sleep( MinutesToRefresh );
                    }
                    else // if there is no connection
                    {
                        Weather.refreshData();
                        System.out.println("\n& " + this.getName() + " has been started ! ");
                        System.out.println("&| The info has *NOT* been updated, NO CONNECTION !\n");
//                        Updater.sleep(1000 * 60 * 60);
                        this.sleep(2000 ); // 2 Minutes -> 2 * 1000(1 sec in millis) * 60
                        System.out.println("))>) - After two Seconds");
                        this.sleep(2000 ); // 2 Minutes -> 2 * 1000(1 sec in millis) * 60
                        System.out.println("))>) - After 2 two Seconds");
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

        }
    }
}
