import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chocolate_starfish on 17/02/2018.
 */

public class Settings extends JPanel
{

    // חדשות, מזג אוויר, עדכון ידני
    private static JPanel MainPanel;
    private static JPanel Weather, News, Manual;
    private static JPanel CenterPanel, GridContainer;
    private static JLabel LWeather, LNews, LManual;
    private static JPanel MainTitle_Panel;
    private static JLabel MainTitle = new JLabel("~ ∵∴ הגדרות ∴∵ ~");
    Border blackline = BorderFactory.createLineBorder(Color.WHITE);

    private static JPanel CenterContainer;

    private static WeatherSet P_WeatherSettings;

    public Settings()
    {
        __init();

//        MainTitle_Panel.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
        add(MainTitle_Panel, BorderLayout.PAGE_START);


//        Weather.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//        News.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//        Manual.setBorder( BorderFactory.createLineBorder(Color.WHITE) );

        GridContainer.add( Weather );
        GridContainer.add( News );
        GridContainer.add( Manual );

//        add( GridContainer, BorderLayout.CENTER );

        CenterContainer.add( GridContainer, BorderLayout.CENTER );

//        P_WeatherSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE, 5) );

        add( CenterContainer, BorderLayout.CENTER);

//        ShowWeatherSettings();
//        BackToSettings();

    }

    private void __init()
    {
        setLayout( new BorderLayout(0,20) );
        setBackground(Color.BLACK);
        setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) ); // Padding the border of the main JFrame

        // The Grid for the Options in Settings
        GridContainer = new JPanel( new GridLayout(2,2, 30, 30) );
        GridContainer.setBackground( Color.BLACK );

        // Container for each Option
        JPanel jPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        jPanel.setBackground(Color.BLACK);
        jPanel.setBorder( BorderFactory.createLineBorder(Color.WHITE) );

        // Applying the icon for the Weather Option
        JLabel icon;
        ImageIcon weather_icon = new ImageIcon( "SettingsIcons/Weather.png" );
        Image     img;
        Image     newImg;
        img = weather_icon.getImage();
        newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        icon = new JLabel(weather_icon);

        Weather = new JPanel( new BorderLayout() );
        Weather.setBackground( Color.BLACK );
        LWeather = new JLabel( "מזג אוויר" );
        LWeather.setFont(new Font("Times new Roman", Font.ITALIC, 40));
        LWeather.setForeground( Color.WHITE );
        jPanel.add(LWeather);
        Weather.add( jPanel, BorderLayout.NORTH );
        Weather.add( icon, BorderLayout.CENTER );

        //Appling the icon for the News Option
        weather_icon = new ImageIcon( "SettingsIcons/News.png" );
        img = weather_icon.getImage();
        newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        icon = new JLabel(weather_icon);

        News = new JPanel( new BorderLayout() );
        News.setBackground( Color.BLACK );
        LNews = new JLabel( "חדשות" );
        LNews.setFont(new Font("Times new Roman", Font.ITALIC, 40));
        LNews.setForeground( Color.WHITE );
        jPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        jPanel.setBackground(Color.BLACK);
        jPanel.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
        jPanel.add(LNews);
        News.add( jPanel, BorderLayout.NORTH );
        News.add( icon, BorderLayout.CENTER );


        // Applying the icon for the Manual Option
        weather_icon = new ImageIcon( "SettingsIcons/Manual.png" );
        img = weather_icon.getImage();
        newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);
        icon = new JLabel(weather_icon);

        Manual = new JPanel( new BorderLayout() );
        Manual.setBackground( Color.BLACK );
        LManual = new JLabel( "זמני עדכון" );
        LManual.setFont(new Font("Times new Roman", Font.ITALIC, 40));
        LManual.setForeground( Color.WHITE );
        jPanel = new JPanel( new FlowLayout(FlowLayout.CENTER));
        jPanel.setBackground(Color.BLACK);
        jPanel.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
        jPanel.add(LManual);
        Manual.add( jPanel, BorderLayout.NORTH );
        Manual.add( icon, BorderLayout.CENTER );


        MainTitle_Panel = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        MainTitle_Panel.setBackground( Color.BLACK );
        MainTitle.setForeground( Color.WHITE );
        MainTitle.setFont(new Font("Times new Roman", Font.ITALIC, 52));
        MainTitle_Panel.add( MainTitle );

        P_WeatherSettings = new WeatherSet();
//        add( P_WeatherSettings, BorderLayout.CENTER );

        CenterContainer = new JPanel( new BorderLayout() );
        CenterContainer.setBackground( Color.BLACK );
    }


    public static void setWeather()      { Weather.setBorder( BorderFactory.createLineBorder(Color.WHITE) ); } // Index - 11
    public static void disableWeather()  { Weather.setBorder( null ); }
    public static void setNews()         { News.setBorder( BorderFactory.createLineBorder(Color.WHITE) ); }    // Index - 12
    public static void disableNews()     { News.setBorder( null ); }
    public static void setManual()       { Manual.setBorder( BorderFactory.createLineBorder(Color.WHITE) ); }  // Index - 13
    public static void disableManual()   { Manual.setBorder( null ); }

    public static void setBorder(String option)
    {
        switch ( option )
        {
            case "News":
                setNews();
                disableManual();
                break;

            case "Weather":
                setWeather();
                break;

            case "Manual":
                setManual();
                break;
        }
    }

    /** Start of Border Handling **/
    /******************************/
    /******************************/
    private static int ActiveJPanel = 11;
    public static int getActiveJPanel() { return ActiveJPanel; }
    public static void setActiveJPanel(int x) { ActiveJPanel = x; }


    private static int         SecondsPassed = 0;
    private static Timer       timerBorder  = new Timer(1000, new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SecondsPassed++;
            if( SecondsPassed == 3 )
            {
                timerBorder.stop();
                MakeNullBorder();
                SecondsPassed = 0;
            }

            System.out.println("%%% Seconds Passed: " + SecondsPassed);
        }
    });

    public static Timer getTimerBorder() { return timerBorder; }

    public static void MakeNullBorder()
    {
        News.setBorder( null );
        Weather.setBorder( null );
        Manual.setBorder( null );
    }

    /** A Number that will assemble the index of the JPanels to know whom is the active one **/
    /** 11 - Weather Panel  ||| 22 - Weather Settings Panel
     *  12 - News Panel
     *  13 - Manual Panel
     **/


    public static void main(String args[])
    {
        JFrame jf = new JFrame("Settings Try");
        jf.setContentPane( new Settings() );
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(768, 1366);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

}