import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Date;
import java.util.*;

/**
 * Created by chocolate_starfish on 18/03/2018.
 */

/**
 *
 * This panel will use for the enter screen in Main_GUI
 *
 */
public class WeatherExtended extends JPanel
{

    private static Date EpochDate; // Translates Epoch Unix Timestamp to Humam date readable
    private JPanel TopCurrent, Top_Container, LeftTop, RightTop, DownContainr; /** MigLayout **/
    private JPanel BottomDaily;

    // Current Dada
    private JLabel    Temperature;   /** Current Tepmerature in the day **/
    private JTextPane Summary;       /** Current Summary in the day **/
    private JLabel    CurrentDay;    /** Current Day in the week **/

    // Daily Data
    private static JLabel Weekly[] = new JLabel[8];
    private static JPanel Weekly_Panel[] = new JPanel[8];
    private static JLabel [] MaxTemp = new JLabel[8], MinTemp = new JLabel[8];
    private static JLabel [] Before = new JLabel[8], After = new JLabel[8];
    private static JLabel [] RiseTime = new JLabel[8], DawnTime = new JLabel[8];
    private static ImageIcon weather_icon_Rise;
    private static Image     img_Rise;
    private static Image     newImg_Rise;
    private static ImageIcon weather_icon_Dawn;
    private static Image     img_Dawn;
    private static Image     newImg_Dawn;
    private static JLabel Rain;
    private static JSONObject DailyArray_Cell; // To absorb/catch one cell from the JSON Array (Daily Data)
    private static JPanel Daily_FlowContainer[] = new JPanel[8];

    private static String[] DaysOfWeek = {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};


    public WeatherExtended(String Temperature, String Summary, Image icon)
    {
        weather_icon_Rise = new ImageIcon("weather_icons/Sunrise_Sun.png" );
        weather_icon_Dawn = new ImageIcon("weather_icons/Dawn_Sun.png" );

        __init(); /** Initiate the JPanel Window **/

//        System.out.println("^_> Extended Weather was opened !");
//        System.out.println("^_> EpochDate: " + EpochDate.getDay());
//        System.out.println("^_> EpochDate: " + EpochDate.toString() );

        // Setting the Temperature Section//
        this.Temperature.setText( Temperature );
        ImageIcon weather_icon = new ImageIcon();
        weather_icon.setImage( icon );
        this.Temperature.setIcon( weather_icon );
        ////////////////////////////////////////////////////////////


        // Setting the Summary + HourlySummary section
        this.Summary.setText( Summary );
        //////////////////////////////////////////////

        // Setting the Current Day in week //
        Date date = new Date();
        CurrentDay.setText( "יום " + DaysOfWeek[date.getDay()] );//
        /////////////////////////////////////

        /** Drop the Topl'e **/
        LeftTop.add( this.Temperature );
        RightTop.add( CurrentDay );
        Top_Container.add( LeftTop );
        Top_Container.add( RightTop );

        DownContainr.add( this.Summary );

        TopCurrent.add( Top_Container, BorderLayout.PAGE_START);
        TopCurrent.add( DownContainr, BorderLayout.CENTER );
        /** End of the Drop the Topl'e **/

        add(TopCurrent, BorderLayout.PAGE_START);

        /** Start of Daily Skeleton dance **/
        ////////////////////////////////////////////////////////////////////
        ////                        - Elements -                          //
        /**/  JSONArray Daily_Array = API_Connect.getDailyTemperature();  //
        /**/  String    epochTime;                                        //
        ////                                                              //
        ////////////////////////////////////////////////////////////////////
        /** Flow-1 **/
        while( Daily_Array.size() == 0 ) // if the Daily Json Array is null
        {
            Daily_Array = API_Connect.getDailyTemperature();
        }
        DailyArray_Cell = (JSONObject) Daily_Array.get(0);
        epochTime = DailyArray_Cell.get("sunriseTime").toString();
        EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
        RiseTime[0].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) + " / ");
        Daily_FlowContainer[0].add( RiseTime[0] );
        epochTime = DailyArray_Cell.get("sunsetTime").toString();
        EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
        DawnTime[0].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) );
        Daily_FlowContainer[0].add( DawnTime[0] );
        MinTemp[0].setText( " ❖ " + API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMin" ).toString() ) + "°" );
        Daily_FlowContainer[0].add( MinTemp[0] );
        Daily_FlowContainer[0].add( After[0] );
        MaxTemp[0].setText( API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMax" ).toString() ) + "°" );
        Daily_FlowContainer[0].add( MaxTemp[0] );
        Daily_FlowContainer[0].add( Before[0] );
        Weekly[0].setText( "היום " + "❖ " );
        Weekly_Panel[0].add( Weekly[0] );
        Daily_FlowContainer[0].add( Weekly_Panel[0] );
//        Daily_FlowContainer[0].add( Weekly[0] );
        System.out.println( "Daily Skeleton: " + Weekly[0].getText() + ": " + MaxTemp[0].getText() + " , " + MinTemp[0].getText() );
        BottomDaily.add( Daily_FlowContainer[0] );

        /** Flow-All the rest **/
        for(int i = 1; i <= 7; i++)
        {
            DailyArray_Cell = (JSONObject) Daily_Array.get(i); // Gets the cell from the JSON File

            epochTime = DailyArray_Cell.get("sunriseTime").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            RiseTime[i].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) + " / ");
            Daily_FlowContainer[i].add( RiseTime[i] );
            epochTime = DailyArray_Cell.get("sunsetTime").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            DawnTime[i].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) );
            Daily_FlowContainer[i].add( DawnTime[i] );
            MinTemp[i].setText( " ❖ " +  API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMin" ).toString() ) + "°" ); // gets the cell under "temperatureMin" title - JSON
            Daily_FlowContainer[i].add( MinTemp[i] ); // Adds to JPanels array
            Daily_FlowContainer[i].add( After[i] ); // Adds to JPanels array
            MaxTemp[i].setText( API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMax" ).toString() ) + "°" ); // Cell under "temperatureMax" title - JSON
            Daily_FlowContainer[i].add(MaxTemp[i]);
            Daily_FlowContainer[i].add(Before[i]);
            epochTime = DailyArray_Cell.get("time").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            Weekly[i].setText( DaysOfWeek[ EpochDate.getDay() ] + " ❖ " );
            Weekly_Panel[i].add( Weekly[i] );
            Daily_FlowContainer[i].add( Weekly_Panel[i] );
//            Daily_FlowContainer[i].add( Weekly[i] );
            System.out.println( "Daily Skeleton: " + Weekly[i].getText() + ": " + MaxTemp[i].getText() + " , " + MinTemp[i].getText() );
            BottomDaily.add( Daily_FlowContainer[i] );
        }

        add( BottomDaily, BorderLayout.CENTER );

        Update();
    }

    private void __init()
    {
        setLayout(new BorderLayout() );
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding the border of the main JFrame

        TopCurrent = new JPanel();
        TopCurrent.setLayout( new BorderLayout() );
        TopCurrent.setBackground(Color.BLACK);
        // -----------------------------------------
        Top_Container = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        Top_Container.setBackground(Color.BLACK);
        // -----------------------------------------
        LeftTop = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
        LeftTop.setBackground(Color.BLACK);
        // -----------------------------------------
        RightTop = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        RightTop.setBackground(Color.BLACK);
        // -----------------------------------------
        DownContainr = new JPanel( );
        DownContainr.setLayout( new GridLayout(1,0) );
        DownContainr.setBackground(Color.BLACK);
        // -----------------------------------------
        BottomDaily = new JPanel( ); // Puts the layout in vertical flow mode. This means that the next cell is normally below and the next component will be put there instead of to the right.
//        BottomDaily.setLayout( new BoxLayout(BottomDaily, BoxLayout.PAGE_AXIS) );
//        BottomDaily.setLayout( new GridLayout(0,1) ); // Puts the layout in vertical flow mode. This means that the next cell is normally below and the next component will be put there instead of to the right.
        BottomDaily.setLayout( new BoxLayout( BottomDaily, BoxLayout.Y_AXIS) ); // Puts the layout in vertical flow mode. This means that the next cell is normally below and the next component will be put there instead of to the right.
        BottomDaily.setBackground(Color.BLACK);


        /**            Temperature Label         **/

        Temperature = new JLabel("");
        Temperature.setFont(new Font("Times new Roman", Font.ITALIC, 48));
        Temperature.setForeground(Color.WHITE);

        /**          END Temperature Label       **/

        /** ------------------------------------ **/

        /**              Summary Label      w     **/

        Summary = new JTextPane();
        Summary.setFont( new Font("Times new Roman", Font.ITALIC, 38));
        Summary.setForeground(Color.WHITE);
        Summary.setBackground(Color.BLACK);
        Summary.setEditable(false);
        // Centering the Text in the JTextPane //
        StyledDocument doc1 = Summary.getStyledDocument();
        SimpleAttributeSet center1 = new SimpleAttributeSet();
        StyleConstants.setAlignment( center1, StyleConstants.ALIGN_CENTER );
        doc1.setParagraphAttributes(0, doc1.getLength(), center1, false );
        ////////////////////////////////////////

        /**            END Summary Label         **/

        /** ------------------------------------ **/

        /**            CurrentDay Label          **/

        CurrentDay = new JLabel("");
        CurrentDay.setFont(new Font("Times new Roman", Font.ITALIC, 48));
        CurrentDay.setForeground(Color.WHITE);

        /**          END CurrentDay Label        **/

        /** ------------------------------------ **/

        /**              Daily Labels            **/

        // Weekly
        for(int i = 0; i <= 7; i++)
        {
            Daily_FlowContainer[i] = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
            Daily_FlowContainer[i].setBackground( Color.BLACK );
            Daily_FlowContainer[i].setForeground( Color.WHITE );

            Weekly_Panel[i] = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
            Weekly_Panel[i].setBackground( Color.BLACK );
            Weekly_Panel[i].setForeground( Color.WHITE );


            // Weekly
            Weekly[i] = new JLabel("");
            Weekly[i].setFont(new Font("Times new Roman", Font.ITALIC, 25 ));
            Weekly[i].setForeground(Color.WHITE);

            // Temperaturres
            MaxTemp[i] = new JLabel("");
            MaxTemp[i].setFont(new Font("Times new Roman", Font.ITALIC, 25));
            MaxTemp[i].setForeground(Color.WHITE);

            MinTemp[i] = new JLabel("");
            MinTemp[i].setFont(new Font("Times new Roman", Font.ITALIC, 25));
            MinTemp[i].setForeground(Color.WHITE);

            Before[i] = new JLabel("מקסימום:" );
            After[i] = new JLabel(" מינימום: " );
            Before[i].setFont(new Font("Times new Roman", Font.ITALIC, 25)); // "טמפרטורה - מקסימום:"
            Before[i].setForeground(Color.WHITE);
            After[i].setFont(new Font("Times new Roman", Font.ITALIC, 25)); // " מינימום: "
            After[i].setForeground(Color.WHITE);

            // Rise and Dawn
            RiseTime[i] = new JLabel("");
            RiseTime[i].setFont(new Font("Times new Roman", Font.ITALIC, 25));
            RiseTime[i].setForeground(Color.WHITE);

            DawnTime[i] = new JLabel("");
            DawnTime[i].setFont(new Font("Times new Roman", Font.ITALIC, 25));
            DawnTime[i].setForeground(Color.WHITE);

            // Setting the icon for the Temperature Label
            img_Rise = weather_icon_Rise.getImage();
            newImg_Rise = img_Rise.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            weather_icon_Rise.setImage( newImg_Rise );
            RiseTime[i].setIcon(weather_icon_Rise);

            img_Dawn = weather_icon_Dawn.getImage();
            newImg_Dawn= img_Dawn.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            weather_icon_Dawn.setImage( newImg_Dawn );
            DawnTime[i].setIcon(weather_icon_Dawn);
            /////////////////////////////////////////////
        }

        // Rain
        Rain = new JLabel("");
        Rain.setFont(new Font("Times new Roman", Font.ITALIC, 22));
        Rain.setForeground(Color.WHITE);

        /**            END Daily Labels          **/
    }


    public static void Update()
    {
        System.gc();

        /** Start of Daily Skeleton dance **/
        ////////////////////////////////////////////////////////////////////
        ////                        - Elements -                          //
        /**/  JSONArray Daily_Array = API_Connect.getDailyTemperature();  //
        /**/  String    epochTime;                                        //
        ////                                                              //
        ////////////////////////////////////////////////////////////////////
        /** Flow-1 **/
        while( Daily_Array.size() == 0 ) // if the Daily Json Array is null
        {
            Daily_Array = API_Connect.getDailyTemperature();
        }
        DailyArray_Cell = (JSONObject) Daily_Array.get(0);
        epochTime = DailyArray_Cell.get("sunriseTime").toString();
        EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
        RiseTime[0].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) + " / ");
//        Daily_FlowContainer[0].add( RiseTime[0] );
        epochTime = DailyArray_Cell.get("sunsetTime").toString();
        EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
        DawnTime[0].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) );
//        Daily_FlowContainer[0].add( DawnTime[0] );
        MinTemp[0].setText( " ❖ " + API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMin" ).toString() ) + "°" );
//        Daily_FlowContainer[0].add( MinTemp[0] );
//        Daily_FlowContainer[0].add( After[0] );
        MaxTemp[0].setText( API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMax" ).toString() ) + "°" );
//        Daily_FlowContainer[0].add( MaxTemp[0] );
//        Daily_FlowContainer[0].add( Before[0] );
        Weekly[0].setText( "היום " + "❖ " );
//        Weekly_Panel[0].add( Weekly[0] );
//        Daily_FlowContainer[0].add( Weekly_Panel[0] );
//        Daily_FlowContainer[0].add( Weekly[0] );
        System.out.println( "Daily Skeleton: " + Weekly[0].getText() + ": " + MaxTemp[0].getText() + " , " + MinTemp[0].getText() );
//        BottomDaily.add( Daily_FlowContainer[0] );

        /** Flow-All the rest **/
        for(int i = 1; i <= 7; i++)
        {
            DailyArray_Cell = (JSONObject) Daily_Array.get(i); // Gets the cell from the JSON File

            epochTime = DailyArray_Cell.get("sunriseTime").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            RiseTime[i].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) + " / ");
//            Daily_FlowContainer[i].add( RiseTime[i] );
            epochTime = DailyArray_Cell.get("sunsetTime").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            DawnTime[i].setText( Integer.toString(EpochDate.getHours()) + ":" + Integer.toString(EpochDate.getMinutes()) );
//            Daily_FlowContainer[i].add( DawnTime[i] );
            MinTemp[i].setText( " ❖ " +  API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMin" ).toString() ) + "°" ); // gets the cell under "temperatureMin" title - JSON
//            Daily_FlowContainer[i].add( MinTemp[i] ); // Adds to JPanels array
//            Daily_FlowContainer[i].add( After[i] ); // Adds to JPanels array
            MaxTemp[i].setText( API_Connect.F2C_Temperature( DailyArray_Cell.get( "temperatureMax" ).toString() ) + "°" ); // Cell under "temperatureMax" title - JSON
//            Daily_FlowContainer[i].add(MaxTemp[i]);
//            Daily_FlowContainer[i].add(Before[i]);
            epochTime = DailyArray_Cell.get("time").toString();
            EpochDate = new Date(Long.parseLong( epochTime ) * 1000);
            Weekly[i].setText( DaysOfWeek[ EpochDate.getDay() ] + " ❖ " );
//            Weekly_Panel[i].add( Weekly[i] );
//            Daily_FlowContainer[i].add( Weekly_Panel[i] );
//            Daily_FlowContainer[i].add( Weekly[i] );
            System.out.println( "Daily Skeleton: " + Weekly[i].getText() + ": " + MaxTemp[i].getText() + " , " + MinTemp[i].getText() );
//            BottomDaily.add( Daily_FlowContainer[i] );
        }

//        repaint();
    }

}