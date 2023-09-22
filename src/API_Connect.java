/**
 * Created by chocolate_starfish on 12/11/2017.
 */

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;


public class API_Connect
{
    private static URL           url_string;
    private static URLConnection url_2connect;

    public API_Connect()
    {
    }

    public API_Connect(String Connection_URL)
    {
        /*******************************************************************************************/
        /** - Making a connection to the api, Writing the data to .JSON file for later analysis - **/
        /*******************************************************************************************/
        try
        {
            url_string     = new URL(Connection_URL);
            url_2connect   = url_string.openConnection();
            InputStream is = url_2connect.getInputStream();

            FileWriter fileWriter = new FileWriter("api.json");
            PrintWriter pwriter = new PrintWriter(fileWriter);

            int i;
            char ch;
            while( (i = is.read()) != -1 ) /** ( -1) = EOF / End Of File**/
            {
//                System.out.println( (char) i ); /** For logging and checking **/
                ch = (char) i;
                pwriter.printf( String.valueOf(ch) );
            }

            pwriter.close();

        }
        catch (Exception ex) { System.err.print(ex); }
    }

    public static void makeConnection(String url, String latitude, String longitude)
    {
        /*******************************************************************************************/
        /** - Making a connection to the api, Writing the data to .JSON file for later analysis - **/
        /*******************************************************************************************/
        try
        {
            // To clean the file content //
            File file = new File("api.json");
            System.out.println( "> API_CONNECT api.json Length: " + file.length() );
            ///////////////////////////////
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            System.out.println( "> API_CONNECT api.json Length <AFTER>: " + file.length() );
            ///////////////////////////////

            FileWriter fileWriter = new FileWriter("api.json");
            PrintWriter pwriter = new PrintWriter(fileWriter);

            if( NetworkStatus.isNetAvailable() )
            {
                url_string   = new URL(url + getKey() + "/" + latitude + "," + longitude);
                url_2connect = url_string.openConnection();
                InputStream is = url_2connect.getInputStream();

                int i;
                char ch;
                while( (i = is.read()) != -1 ) /** ( -1) = EOF / End Of File **/
                {
//                System.out.println( (char) i ); /** For logging and checking **/
                    ch = (char) i;
                    pwriter.printf( String.valueOf(ch) );
                }
                is.close();
            }


            pwriter.close();
        }
        catch (IOException ioe)
        {
            System.err.print(ioe);
            Weather.showConnectionProblem();
        }
        catch (Exception ex) { System.err.print(ex); }
    }

    public static String getKey()
    {
        String line = null, key = "";

        try
        {
            FileReader reader = new FileReader("weather_credentials/key.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                key += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return key;
    }

    public static String getSummary()
    {
        File file;
        if( (file = new File("api.json")).length() != 0 && NetworkStatus.isNetAvailable() )
        {
            JSONObject structure = new JSONObject();
            try
            {
                // read the json file
                FileReader reader = new FileReader("api.json");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                // handle a structure into the json object
                structure = (JSONObject) jsonObject.get("currently");
            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
            catch (IOException ex) { ex.printStackTrace(); }
            catch (NullPointerException ex) { ex.printStackTrace(); }
            catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }

            return structure.get("summary").toString();
        }

        return "      -";
    }

    public static String getIcon()
    {
        File file;
        if( (file = new File("api.json")).length() != 0 && NetworkStatus.isNetAvailable() )
        {
            JSONObject structure = new JSONObject();
            try
            {
                // read the json file
                FileReader reader = new FileReader("api.json");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                // handle a structure into the json object
                structure = (JSONObject) jsonObject.get("currently");
            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
            catch (IOException ex)
            {
                ex.printStackTrace();
                return "null";
            }
            catch (NullPointerException ex) { ex.printStackTrace(); }
            catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
            return structure.get("icon").toString();
        }

        return "no-wifi";

//        return (NetworkStatus.isNetAvailable()) ? structure.get("icon").toString() : "no-wifi"; // If( isNetUp = true ) -> return Icon's name, else -> return "no-wifi"
    }

    public static String getTemperature()
    {
        /**************************************************/
        /** - Get the Temperature and return in Celsius- **/
        /**************************************************/
        File file;
        if( (file = new File("api.json")).length() != 0 && NetworkStatus.isNetAvailable() )
        {
            JSONObject structure = new JSONObject();
            try
            {
                // read the json file
                FileReader reader = new FileReader("api.json");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                // handle a structure into the json object
                structure = (JSONObject) jsonObject.get("currently");
            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
            catch (IOException ex) { ex.printStackTrace(); }
            catch (NullPointerException ex) { ex.printStackTrace(); }
            catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }

            String temp = structure.get("temperature").toString(); // Temperature in F°
//            double final_Temp = Double.parseDouble(temp);
//            final_Temp -= 32.0;
//            final_Temp *= (5.0 / 9.0) ; // Temperature in C°
//            return String.format("%.1f", final_Temp);
            return F2C_Temperature( temp );
        }

        return "-";
    }

    public static String F2C_Temperature(String Original)
    {
        double final_Temp = Double.parseDouble( Original );
        final_Temp -= 32.0;
        final_Temp *= (5.0 / 9.0) ; // Temperature in C°
        return String.format("%.1f", final_Temp);
    }

    public static JSONArray getDailyTemperature()
    {
        /**************************************************/
        /** - Get the Temperature and return in Celsius- **/
        /**************************************************/

        File file;
        JSONArray DailyArray = new JSONArray();
        if( (file = new File("api.json")).length() != 0 && NetworkStatus.isNetAvailable() )
        {
            JSONObject structure = new JSONObject();
            try
            {
                // read the json file
                FileReader reader = new FileReader("api.json");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                // handle a structure into the json object
                structure = (JSONObject) jsonObject.get("daily");


            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
            catch (IOException ex) { ex.printStackTrace(); }
            catch (NullPointerException ex) { ex.printStackTrace(); }
            catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }
            DailyArray = (JSONArray) structure.get("data");
//            return DailyArray;
        }
        return DailyArray;
    }

    public static String getHumidity()
    {
        /*****************************************************/
        /** - לחות האוויר - **/
        /*****************************************************/
        JSONObject structure = new JSONObject();
        try
        {
            // read the json file
            FileReader reader = new FileReader("api.json");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            // handle a structure into the json object
            structure = (JSONObject) jsonObject.get("currently");
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex) { ex.printStackTrace(); }
        catch (NullPointerException ex) { ex.printStackTrace(); }
        catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }

        double humidity_Percentage = (double) structure.get("humidity");
        DecimalFormat df = new DecimalFormat("#%");
        return df.format( humidity_Percentage );
    }

    public static String getHourlySummary()
    {
        File file;
        if( (file = new File("api.json")).length() != 0 && NetworkStatus.isNetAvailable() )
        {
            JSONObject structure = new JSONObject();
            try
            {
                // read the json file
                FileReader reader = new FileReader("api.json");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                // handle a structure into the json object
                structure = (JSONObject) jsonObject.get("hourly");
            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
            catch (IOException ex) { ex.printStackTrace(); }
            catch (NullPointerException ex) { ex.printStackTrace(); }
            catch (org.json.simple.parser.ParseException e) { e.printStackTrace(); }

            return structure.get("summary").toString();
        }

        return "      -";
    }

    /** Saves the data of the selected Saved Weather Server Setting, and brings it to the GUI
     * 0 - DarkSky API
     * 1 - Google API
     * **/
    public static int getSelectedWeatherServ()
    {

        String line = null, key = "";

        try
        {
            FileReader reader = new FileReader("weather_settings/_Selected_Server.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                key += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return Integer.parseInt( key );
    }

    /** Saves the data of the selected Saved Location Setting, and brings it to the GUI
     * 0 - Automated Location
     * 1 - Manual Location
     * 2 - Units Location
     * **/
    public static int getSelectedWeatherLoc()
    {

        String line = null, key = "";

        try
        {
            FileReader reader = new FileReader("weather_settings/_Location.dat");
            BufferedReader bufferedReader = new BufferedReader( reader );

            while( ( line = bufferedReader.readLine() ) != null )
                key += line;

            // Always close files.
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) { ex.printStackTrace(); }
        catch (IOException ex)           { ex.printStackTrace(); }
        catch (NullPointerException ex)  { ex.printStackTrace(); }

        return Integer.parseInt( key );
    }

    // " Seize The Day "
    /** Saves the data of the selected Saved Weather Server Setting, and brings it to the GUI
     * 0 - DarkSky API
     * 1 - Google API
     *
     * Saves the data of the selected Autho' Key, and brings it to the GUI
     * - String Key
     *
     *
     * Saves the data of the selected Saved Location Setting, and brings it to the GUI
     * - int Longitude
     * - int Latitude
     **/
    public static void SaveSettings(int Server_option, String Autho_Key, String Longitude, String Latitude)
    {
        // File handling for Selected Server
        File file = new File("weather_settings/_Selected_Server.dat");
        ///////////////////////////////
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter( file );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        writer.print("");
        writer.print( Integer.toString(Server_option) );
        writer.close();

        writer.flush(); // Flush the toilet

        //////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////

        // File handling for Selected Autho' Key
        file = new File("weather_credentials/key.dat");
        ///////////////////////////////
        writer = null;
        try
        {
            writer = new PrintWriter( file );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        writer.print("");
        writer.print( Autho_Key );
        writer.close();

        writer.flush(); // Flush the toilet

        //////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////

        /** File handling for Location **/
        // Latitude Handling
        file = new File("weather_credentials/_latitude.dat");
        ///////////////////////////////
        writer = null;
        try
        {
            writer = new PrintWriter( file );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        writer.print("");
        writer.print( Latitude );
        writer.close();

        writer.flush(); // Flush the toilet


        // Longitude Handling
        file = new File("weather_credentials/_longitude.dat");
        ///////////////////////////////
        writer = null;
        try
        {
            writer = new PrintWriter( file );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        writer.print("");
        writer.print( Longitude );
        writer.close();

        writer.flush(); // Flush the toilet
    }

}
