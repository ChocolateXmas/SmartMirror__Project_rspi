/**
 * Created by chocolate_starfish on 08/11/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Weather extends JPanel implements Runnable
{

    private static ImageIcon weather_icon;
    private static Image     img;
    private static Image     newImg;

    private static JLabel    Temperature;
    private static JLabel    Summary;
//    private JLabel    HourlySummary;
    private static JTextArea    HourlySummary;

    private JPanel    Uppear_Weather_info;
    private JPanel    Summary_Panel;
    private JPanel    Hourly_Panel; // פאקינג שיט תן לי לסיים עם הפרויקט הזה כבר

    private Auto_Updater_Thread_Weather Updater = new Auto_Updater_Thread_Weather("Weather Auto-Updater -> Thread");

    public static Image     getIcon()        { return weather_icon.getImage(); }
    public static String    getTemperature() { return Temperature.getText(); }
    public static String    getSummary()     { return Summary.getText(); }
    public static String    getHourly()      { return HourlySummary.getText(); }


    public Weather()
    {
        __init(); /** Initiate the JPanel Window **/

        Temperature.setText( " " + ( ( !API_Connect.getTemperature().equals("-") ) ? API_Connect.getTemperature()+"°" : "אין חיבור" ) );

        // Setting the icon for the Temperature Label
        weather_icon = new ImageIcon("weather_icons/main_icons/" + API_Connect.getIcon() + ".png");
        img = weather_icon.getImage();
        newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        Temperature.setIcon(weather_icon);
        /////////////////////////////////////////////

        Summary.setText( API_Connect.getSummary() );

        Uppear_Weather_info.add(Temperature);
        Summary_Panel.add(Summary);

        HourlySummary.setText( API_Connect.getHourlySummary() );
        Hourly_Panel.add(HourlySummary);

        add(Uppear_Weather_info);
        add(Summary_Panel);
        add(Hourly_Panel);

        Updater.start();
    }

    private void __init()
    {
        setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
        setBackground( Color.BLACK );

//        api = new API_Connect();

        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
        /**              Declarations            **/
        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

        /**              Weather Icon            **/

        weather_icon = new ImageIcon();

        /**            END Weather Icon          **/

        /** ------------------------------------ **/

        /**            Temperature Label         **/

        Temperature = new JLabel("");
        Temperature.setFont(new Font("Times new Roman", Font.ITALIC, 48));
        Temperature.setForeground(Color.WHITE);

        /**          END Temperature Label       **/

        /** ------------------------------------ **/

        /**              Summary Label           **/

        Summary = new JLabel("");
        Summary.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        Summary.setForeground(Color.WHITE);

        /**            END Summary Label         **/

        /** ------------------------------------ **/

        /**           HourlySummary Label        **/

        HourlySummary = new JTextArea("");

        HourlySummary.setColumns(25);
        HourlySummary.setRows(4);

        HourlySummary.setFont(new Font("Times new Roman", Font.ITALIC, 24));
        HourlySummary.setForeground(Color.WHITE);
        HourlySummary.setBackground(Color.BLACK);
        HourlySummary.setLineWrap(true);
        HourlySummary.setWrapStyleWord(true);

        HourlySummary.setEditable(false);
        HourlySummary.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        /**            END HourlySummary Label          **/

        /** ------------------------------------ **/

        /**           Uppear_Weather_info        **/

        Uppear_Weather_info = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        Uppear_Weather_info.setBackground(Color.BLACK);

        /**             END Info Panel           **/

        /** ------------------------------------ **/

        /**             Summary_Panel            **/

        Summary_Panel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        Summary_Panel.setBackground(Color.BLACK);

        /**           END Summary_Panel          **/

        /** ------------------------------------ **/

        /**              Hourly_Panel            **/

        Hourly_Panel = new JPanel( new FlowLayout() );
        Hourly_Panel.setBackground(Color.BLACK);

        /**            END Hourly_Panel          **/

        // Make the connection, and set GUI info of the Connection DATA
        API_Connect.makeConnection( readURL(), readLatitude(), readLongitude() );
    }

    /** A Static function that will be activated to refresh and set all the data together when the internet comes back **/
    public static void refreshData()
    {
        // Set the text of the Celsius temperature
        Temperature.setText( " " + ( ( !API_Connect.getTemperature().equals("-") ) ? API_Connect.getTemperature()+"°" : "אין חיבור" ) );
        // Setting the icon for the Temperature Label
        weather_icon = new ImageIcon("weather_icons/main_icons/" + API_Connect.getIcon() + ".png");

        img = weather_icon.getImage();
        newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        Temperature.setIcon(weather_icon);
        /////////////////////////////////////////////

        // Set the text of the Summary
        Summary.setText( API_Connect.getSummary() );

        // Set the text of the Hourly Summary
        HourlySummary.setText( API_Connect.getHourlySummary() );
    }

    public static void showConnectionProblem()
    {
        // Set the text of the Celsius temperature
        Temperature.setText("בעית חיבור");
        // Setting the icon for the Temperature Label
        weather_icon = new ImageIcon("weather_icons/main_icons/null.png");

        img = weather_icon.getImage();
        newImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        Temperature.setIcon(weather_icon);
        /////////////////////////////////////////////

        // Set the text of the Summary
        Summary.setText( "-" );

        // Set the text of the Hourly Summary
        HourlySummary.setText( "-" );
    }

    /** Read URL from file _URL.dat **/
    public static String readURL()
    {
        String line = null, url = "";

        try
        {
            FileReader reader = new FileReader("weather_credentials/_URL.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                url += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return url;
    }

    /** Read Latitude from file _latitude.dat **/
    public static String readLatitude()
    {
        String line = null, latitude = "";

        try
        {
            FileReader reader = new FileReader("weather_credentials/_latitude.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                latitude += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return latitude;
    }

    /** Read Longitude from file _longitude.dat **/
    public static String readLongitude()
    {
        String line = null, longitude = "";

        try
        {
            FileReader reader = new FileReader("weather_credentials/_longitude.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                longitude += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return longitude;
    }

    @Override
    public void run()
    {

    }
}
