/*************************************************************************************************************************************************\
|****    _________                           __      _____   .__                                             ____   ____ ____     _______     ****|
|****   /   _____/  _____  _____   _______ _/  |_   /     \  |__|_______ _______   ____  _______             \   \ /   //_   |    \   _  \    ****|
|****   \_____  \  /     \ \__  \  \_  __ \\   __\ /  \ /  \ |  |\_  __ \\_  __ \ /  _ \ \_  __ \    ______   \   Y   /  |   |    /  /_\  \   ****|
|****   /        \|  Y Y  \ / __ \_ |  | \/ |  |  /    Y    \|  | |  | \/ |  | \/(  <_> ) |  | \/   /_____/    \     /   |   |    \  \_/   \  ****|
|****  /_______  /|__|_|  /(____  / |__|    |__|  \____|__  /|__| |__|    |__|    \____/  |__|                  \___/    |___| /\  \_____  /  ****|
|****          \/       \/      \/                        \/                                                                   \/        \/   ****|
|****                                                                                                                                         ****|
|****   < Java Edition - By Alex.Beigel   @ChocolateStarfish >                                                                     - 9.5.18 - ****|
|****   < T0t@l L!n3s Of C0de :                              >                                                                                ****|
|****   < tOT@l Classes:                                     >                                                                                ****|
|****   < Copyright © 2018 | Alex Beigel #ChocolateStarfish  >                                                                                ****|
\*************************************************************************************************************************************************/

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import smartmirror.RSS.News.RSSNews;
import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.*;
import java.net.SocketException;

/**
 * Created by chocolate_starfish on 06/11/2017.
 */


public class Main_GUI extends JFrame implements KeyListener
{

    private static JPanel           IconContainer;    // Icons Panel
    private static JPanel           contentPane;      // Main Control Panel
    private static JPanel           Main_contentPane; // Secondary Control Panel for Inner Panels
    private static JPanel           Clock_Panel;      // Clock Section
//    private static Weather          Weather_Panel;    // Weather Section
    private static Clock          Weather_Panel;    // Weather Section
    private static WeatherExtended  ExtendedWeather;  // Extended Weather Panel
    private static JPanel           WeatherSettings;  // Settings JPanel for Weather
    private        RSSNews          FeedNews_Panel;   // RSS Feed News Panel
    private static Settings         SettingsPanel;    // Settings for all Panels

    private        JPanel      _EastLeft, _EastRight; /** BorderLayout.PageStart(East) Panels **/
    private static JPanel      _PageEnd_Container; /** BorderLayout.PageEnd(Panel) **/

    //< Objects for applying icons for Settings + Refresh >\\
    private static ImageIcon weather_icon;
    private static Image     img;
    private static Image     newImg;
    /*******************************/
    private static JLabel    LSettings, LRefresh;
    /*******************************/
    private ImageIcon MakeIcon(String _IconName)
    {
        weather_icon = new ImageIcon("SettingsIcons/" + _IconName);
        // Setting the icon for the Temperature Label
        img = weather_icon.getImage();
        newImg = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        weather_icon.setImage(newImg);

        return weather_icon;
    }
    /*******************************/

    public static JPanel          getClockPanel()       { return Clock_Panel; }
//    public static Weather         getWeatherPanel()     { return Weather_Panel; }
    public static Clock         getWeatherPanel()     { return Weather_Panel; }
    public static WeatherExtended getWeatherXTNDPanel() { return ExtendedWeather; }
    public static JPanel          getFeedPanel()        { return _PageEnd_Container; }
    public static Settings        getSettingsPanel()    { return SettingsPanel; }
    public static JPanel          getWeatherSettings()  { return WeatherSettings; }

    public void SetContentPanel(JPanel jp)
    {
        Main_contentPane.removeAll();
        revalidate();
        repaint();
        Main_contentPane.add(jp);
        revalidate();
        repaint();
    }

    public void SetToDefault()
    {
        Main_contentPane.removeAll();
        revalidate();
        repaint();
        Main_contentPane.add(contentPane);
        revalidate();
        repaint();
    }

    public Main_GUI()
    {
        /** Main Panel **/
        contentPane = new JPanel( new BorderLayout() );
        Main_contentPane = new JPanel( new BorderLayout() );

        /** Icons for Uppear Panel **/
        LSettings = new JLabel( MakeIcon("GearSettingIcon.png") );
        LRefresh = new JLabel( MakeIcon("Refresh.png") );

        IconContainer = new JPanel();
        IconContainer.setLayout( new BoxLayout(IconContainer, BoxLayout.X_AXIS) );
        IconContainer.setBackground( Color.BLACK );
        IconContainer.add(LSettings);
        IconContainer.add(Box.createRigidArea(new Dimension(35,0)));
        IconContainer.add(LRefresh);
        /*****/

        /** Settings Panel **/
        SettingsPanel = new Settings();
        /********************/

        /** BorderLayout.PageStart(East) Panels **/
        _EastLeft   = new JPanel(new FlowLayout(FlowLayout.LEFT));
        _EastLeft.setBackground(Color.BLACK);
        _EastRight  = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        _EastRight.setBackground(Color.BLACK);
        JPanel Container_EAST = new JPanel();
        Container_EAST.setLayout( new BoxLayout(Container_EAST, BoxLayout.X_AXIS) );
        Container_EAST.setBackground(Color.BLACK);
        Container_EAST.add(_EastLeft);
        Container_EAST.add(_EastRight);

        /** (Clock + Weather) / PAGE_START Panel **/
        Clock_Panel = new Clock();
        _EastRight.add(Clock_Panel);
//        Weather_Panel = new Weather();
        Weather_Panel = new Clock();
        /** { **/
//        ExtendedWeather = new WeatherExtended( getWeatherPanel().getTemperature(), getWeatherPanel().getSummary(), getWeatherPanel().getIcon() );
        /** } **/
        _EastLeft.add(Weather_Panel);
        Weather_Panel.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
        JPanel UppearContainer = new JPanel();
        UppearContainer.setLayout( new BoxLayout( UppearContainer, BoxLayout.PAGE_AXIS ) );
        UppearContainer.setBackground( Color.BLACK );
        UppearContainer.add( IconContainer );
        UppearContainer.add( Container_EAST );
        contentPane.add( UppearContainer, BorderLayout.PAGE_START );

        /** Rss News Feed - Panel **/
        _PageEnd_Container = new JPanel( new BorderLayout() );
        _PageEnd_Container.setPreferredSize( new Dimension(getWidth(), 50) );
        JPanel leftRect, rightRect; // To fill White space at the end of the RSS Feed
        leftRect = new JPanel();
        leftRect.setBackground(Color.WHITE);
        rightRect = new JPanel();
        rightRect.setBackground(Color.WHITE);

        _PageEnd_Container.add(leftRect, BorderLayout.LINE_START);
        try
        {
            FeedNews_Panel = new RSSNews();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        _PageEnd_Container.setBackground(Color.BLACK);
        _PageEnd_Container.add(FeedNews_Panel, BorderLayout.CENTER);
        _PageEnd_Container.add(rightRect, BorderLayout.LINE_END);
        contentPane.add(_PageEnd_Container, BorderLayout.PAGE_END);

        Main_contentPane.add(contentPane);


        __init(); /** Initiate the JFrame Window **/
    }


    private void __init()
    {
        setContentPane(Main_contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(768, 1366);
        setLocationRelativeTo(null);
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding the border of the main JFrame


//        ExtendedWeather = new WeatherExtended( getWeatherPanel().getTemperature(), getWeatherPanel().getSummary(), getWeatherPanel().getIcon() );

        // Weather Settings init
//        WeatherSettings = new WeatherSet();
//        WeatherSet.getT_Key().addKeyListener(new KeyListener()
//        {
//            @Override
//            public void keyTyped(KeyEvent e) { }
//
//            @Override
//            public void keyPressed(KeyEvent e)
//            {
//                System.out.println( "@!> WeatherSet T_Key char: " + e.getKeyCode() );
//                switch ( e.getKeyCode() )
//                {
//                    case KeyEvent.VK_UP:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_DOWN:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_LEFT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_RIGHT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_ESCAPE:
//                        WeatherSet.getT_Key().setFocusable( false );
//                        setFocusable(true); // To add the focus on the Main Jframe
//                        requestFocusInWindow(); // Requesting focus from the main window
//                        WeatherSet.setActive( WeatherSet.get_Key(), 32 );
//                        System.gc();
//                        break;
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) { }
//        });
//
//        WeatherSet.getT_Longitude().addKeyListener(new KeyListener()
//        {
//            @Override
//            public void keyTyped(KeyEvent e) { }
//
//            @Override
//            public void keyPressed(KeyEvent e)
//            {
//                System.out.println( "@!> WeatherSet Longitude char: " + e.getKeyCode() );
//
//                switch ( e.getKeyCode() )
//                {
//                    case KeyEvent.VK_UP:
//                        WeatherSet.getT_Longitude().setFocusable( false );
//                        WeatherSet.getT_Latitude().setFocusable( true );
//                        WeatherSet.getT_Latitude().requestFocus();
//                        WeatherSet.setActive( WeatherSet.getT_Latitude(), 39 );
//                        System.gc();
//                        break;
//
//                    case KeyEvent.VK_DOWN:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_LEFT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_RIGHT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_ESCAPE:
//                        WeatherSet.getT_Longitude().setFocusable( false );
//                        setFocusable(true); // To add the focus on the Main Jframe
//                        requestFocusInWindow(); // Requesting focus from the main window
//                        WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 );
//                        System.gc();
//                        break;
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) { }
//        });
//
//        WeatherSet.getT_Latitude().addKeyListener(new KeyListener()
//        {
//            @Override
//            public void keyTyped(KeyEvent e) { }
//
//            @Override
//            public void keyPressed(KeyEvent e)
//            {
//                System.out.println( "@!> WeatherSet Latitude char: " + e.getKeyCode() );
//
//                switch ( e.getKeyCode() )
//                {
//                    case KeyEvent.VK_UP:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_DOWN:
//                        WeatherSet.getT_Latitude().setFocusable( false );
//                        WeatherSet.getT_Longitude().setFocusable( true );
//                        WeatherSet.getT_Longitude().requestFocus();
//                        WeatherSet.setActive( WeatherSet.getT_Longitude(), 38 );
//                        System.gc();
//                        break;
//
//                    case KeyEvent.VK_LEFT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_RIGHT:
//                        e.setKeyCode( KeyEvent.VK_RIGHT );
//                        break;
//
//                    case KeyEvent.VK_ESCAPE:
//                        WeatherSet.getT_Latitude().setFocusable( false );
//                        setFocusable(true); // To add the focus on the Main Jframe
//                        requestFocusInWindow(); // Requesting focus from the main window
//                        WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 );
//                        System.gc();
//                        break;
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) { }
//        });

        /** For full screen **/
//        setExtendedState(JFrame.MAXIMIZED_BOTH); / ** ORIGINAL ** /
        setExtendedState(JFrame.HEIGHT);
        setUndecorated(true);
//        pack();
        setVisible(true);

        setFocusable(true); // To add the focus on the Main Jframe
        requestFocusInWindow(); // Requesting focus from the main window
        addKeyListener(this); // Adding the key listener
        System.out.println("> Start Key Listener.");

        new Thread( FeedNews_Panel ).start(); // To start the scrolling text in the RSS Feed
        System.out.println("> Start Key Listener.");

        ActiveJPanel = 1; // Every Layout(JPanel) has a Index identification number
        timerBorder.start(); // Starts the timer for the Dimmed Border at the Main_Content layout
    }


    /** Start of Border Handling **/
    /******************************/
    /******************************/
    private int ActiveJPanel;

    private int         SecondsPassed = 0;
    private Timer       timerBorder  = new Timer(1000, new ActionListener()
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

            System.out.println("$$$ Seconds Passed: " + SecondsPassed);
        }
    });

    private void MakeNullBorder()
    {
        getWeatherPanel().setBorder( null );
        getClockPanel().setBorder( null );
        getFeedPanel().setBorder( null );
        LSettings.setBorder( null );
        LRefresh.setBorder( null );
    }

    /** A Number that will assemble the index of the JPanels to know whom is the active one **/
    /** 1 - Weather Panel  |||  4 (1-Extended) WeatherExtended Panel
     *  2 - Clock Panel    |||
     *  3 - News Panel     |||
     *  5 - Setting icon   |||  7 (5-Extended) Settings Panel
     *  6 - Refresh DATA   |||
     **/

    public void Move(KeyEvent e)
    {

        System.out.println(e + "\n\n->>> Pargin: " + e.getKeyCode() + " Another one: " + e.getKeyChar() + "\n\n");
//        System.out.println("");

        switch (ActiveJPanel) // Main_GUI active jpanel index
        {
//            /** Case 1 Layout - Weather **/
//            /*****************************/
//            // Weather Panel - 1
//            case 1:
//                switch (e.getKeyCode())
//                {
//                    case KeyEvent.VK_DOWN:
//                        getWeatherPanel().setBorder( null );
//                        getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                        ActiveJPanel = 3;
//                        if(!timerBorder.isRunning()) timerBorder.start();
//                        else timerBorder.restart();
//                        break;
//
//                    case KeyEvent.VK_UP:
//                        getWeatherPanel().setBorder( null );
//                        LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                        ActiveJPanel = 5;
//                        if(!timerBorder.isRunning()) timerBorder.start();
//                        else timerBorder.restart();
//                        break;
//
//                    case KeyEvent.VK_LEFT:
//                        getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                        if(!timerBorder.isRunning()) timerBorder.start();
//                        else timerBorder.restart();
//                        break;
//
//                    case KeyEvent.VK_RIGHT:
//                        getWeatherPanel().setBorder( null );
//                        getClockPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                        ActiveJPanel = 2;
//                        if(!timerBorder.isRunning()) timerBorder.start();
//                        else timerBorder.restart();
//                        break;
//
//                    case KeyEvent.VK_ENTER:
//                        if( !timerBorder.isRunning() )
//                        {
//                            // If the Borders are'nt shown, then show the CURRENT BORDER
//                            getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                            ActiveJPanel = 1;
//                            timerBorder.start();
//                        }
//                        else
//                        {
//                            System.out.println("><>>> Weather was Entered!!!");
//                            MakeNullBorder();
//                            System.gc();
////                            ExtendedWeather = new WeatherExtended( getWeatherPanel().getTemperature(), getWeatherPanel().getSummary(), getWeatherPanel().getIcon() );
////                            ExtendedWeather.Update();
//                            getWeatherXTNDPanel().Update();
////                            SetContentPanel( ExtendedWeather );
//                            SetContentPanel( getWeatherXTNDPanel() );
//                            timerBorder.stop();
//                            ActiveJPanel = 4;
//                        }
//                        System.out.println(">>> Enter Was Pressed!!!");
//                        break;
//
//                    case KeyEvent.VK_ESCAPE:
//                        SetToDefault();
//                        break;
//
//                    default:
//                        ActiveJPanel = 1;
//                        break;
//                }
//                break;


            /** Case 2 Layout - Clock **/
            /***************************/
            // Clock Panel - 2
            case 2:
                switch (e.getKeyCode())
                {
                    case KeyValue.(40):
                        getClockPanel().setBorder( null );
                        getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 3;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_DOWN:
                        getClockPanel().setBorder( null );
                        getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 3;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_UP:
                        getClockPanel().setBorder( null );
                        LRefresh.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 6;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_LEFT:
                        getClockPanel().setBorder( null );
                        getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 1;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_RIGHT:
                        getClockPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        timerBorder.start();
                        break;

                    case KeyEvent.VK_ENTER:
                        if(!timerBorder.isRunning())
                        {getClockPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                            ActiveJPanel = 2;
                            timerBorder.start();
                        }
                        else
                        {
                            System.out.println("><>>> Clock was Entered!!!");
                            timerBorder.restart();
                        }
                        System.out.println(">>> Enter Was Pressed!!!");
                        break;

                    case KeyEvent.VK_ESCAPE:
                        SetToDefault();
                        break;

                    default:
                        ActiveJPanel = 2;
                        break;
                }
                break;

            /** Case 3 Layout - News **/
            /**************************/
            // News Panel - 3
            case 3:
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                        getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_UP:
                        getFeedPanel().setBorder( null );
                        getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 1;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_LEFT:getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_RIGHT:getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_ENTER:
                        if(!timerBorder.isRunning())
                        {
                            getFeedPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                            ActiveJPanel = 3;
                            timerBorder.start();
                        }
                        else
                        {
                            System.out.println("><>>> News was Entered!!!");
                            timerBorder.restart();
                        }
                        System.out.println(">>> Enter Was Pressed!!!");
                        break;

                    case KeyEvent.VK_ESCAPE:
                        SetToDefault();
                        break;

                    default:
                        ActiveJPanel = 3;
                        break;
                }
                break;

            /** Case 4 Layout - Xtended Weather **/
            /*************************************/
            // Weather Extended
//            case 4:
//                switch (e.getKeyCode())
//                {
//                    case KeyEvent.VK_ESCAPE:
//                        getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                        timerBorder.restart();
////                        if(!timerBorder.isRunning()) timerBorder.start();
////                        else timerBorder.restart();
//
//                        SetToDefault();
//                        ActiveJPanel = 1;
//                        break;
//
//                    default:
//                        ActiveJPanel = 4;
//                        break;
//                }
//                break;

            /** Case 5 Layout - Settings **/
            /*******************/
            // Settings icon
            case 5:
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                        LSettings.setBorder( null );
                        getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 1;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_UP:
                        LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_LEFT:
                        LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_RIGHT:
                        LSettings.setBorder( null );
                        LRefresh.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 6;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_ENTER:
                        if( !timerBorder.isRunning() )
                        {
                            // If the Borders are'nt shown, then show the CURRENT BORDER
                            LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                            ActiveJPanel = 5;
                            timerBorder.start();
                        }
                        else
                        {
                            System.out.println("><>>> Settings was Entered!!!");
                            MakeNullBorder();
                            System.gc();
                            SettingsPanel.setActiveJPanel(11);
                            SettingsPanel.setWeather();
                            SettingsPanel.getTimerBorder().start();
                            SetContentPanel( SettingsPanel );
                            timerBorder.stop();
                            ActiveJPanel = 7;
                        }
                        System.out.println(">>> Enter Was Pressed!!!");
                        break;

                    case KeyEvent.VK_ESCAPE:
                        LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 5;
                        SetToDefault();
                        timerBorder.restart();
                        break;

                    default:
                        ActiveJPanel = 5;
                        break;
                }
                break;

            /** Case 6 Layout - Refresh **/
            /*****************************/
            // Refresh
            case 6:
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_DOWN:
                        LRefresh.setBorder( null );
                        getClockPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 2;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    /** Same as VK_RIGHT **/
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_RIGHT:
                        LRefresh.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_LEFT:
                        LRefresh.setBorder( null );
                        LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                        ActiveJPanel = 5;
                        if(!timerBorder.isRunning()) timerBorder.start();
                        else timerBorder.restart();
                        break;

                    case KeyEvent.VK_ENTER:
                        if( !timerBorder.isRunning() )
                        {
                            // If the Borders are'nt shown, then show the CURRENT BORDER
                            LRefresh.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                            ActiveJPanel = 6;
                            timerBorder.start();
                        }
                        else
                        {
                            /** Needs to refresh Weather Data **/
//                            Weather.refreshData();
                            System.out.println("><>>> Data Refreshed! ");
                            System.gc();
                            timerBorder.stop();
                            ActiveJPanel = 6;
                        }
                        System.out.println(">>> Enter Was Pressed!!!");
                        break;

                    case KeyEvent.VK_ESCAPE:
                        SetToDefault();
                        break;

                    default:
                        ActiveJPanel = 6;
                        break;
                }
                break;

            /** Case 7 Layout - Settings Panel Xtended **/
            /********************************************/
            // XTended Settings Panel
            case 7:
                switch ( SettingsPanel.getActiveJPanel() )
                {
                    /** Weather Panel - Settings **/
                    case 11:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                SettingsPanel.disableWeather(); // Make null border for Weather panel
                                SettingsPanel.setManual(); // set Border for Manual panel
                                SettingsPanel.setActiveJPanel(13);
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            /** Same as VK_LEFT **/
                            case KeyEvent.VK_UP:
                            case KeyEvent.VK_LEFT:
                                SettingsPanel.setWeather();
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            case KeyEvent.VK_RIGHT:
                                SettingsPanel.disableWeather();
                                SettingsPanel.setNews();
                                SettingsPanel.setActiveJPanel(12);
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            case KeyEvent.VK_ENTER:
                                if( !SettingsPanel.getTimerBorder().isRunning() )
                                {
                                    SettingsPanel.setWeather();
                                    SettingsPanel.setActiveJPanel(11);
                                    ActiveJPanel = 7;
                                    SettingsPanel.getTimerBorder().start();
                                }
                                else
                                {
                                    System.out.println("><>>> Weather was Entered!!!");
                                    System.gc();
                                    SettingsPanel.getTimerBorder().stop();
                                    SetContentPanel( getWeatherSettings() );
                                    ActiveJPanel = 8;
                                }

//                                if( !timerBorder.isRunning() )
//                                {
//                                    // If the Borders are'nt shown, then show the CURRENT BORDER
//                                    SettingsPanel.setWeather();
//                                    ActiveJPanel = 7;
//                                    timerBorder.start();
//                                }
//                                else
//                                {
//                                    System.out.println("><>>> Weather was Entered!!!");
//                                    ActiveJPanel = 8;
//                                    SetContentPanel( getWeatherSettings() );
//                                    timerBorder.stop();
//                                }
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                                timerBorder.restart();
                                ActiveJPanel = 5;
                                SettingsPanel.getTimerBorder().stop();
                                SettingsPanel.MakeNullBorder();
                                SetToDefault();
                                break;

                            default:
                                ActiveJPanel = 7;
                                break;
                        }
                    break;

                    /** News Panel - Settings **/
                    case 12:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                SettingsPanel.disableNews(); // Make null border for Weather panel
                                SettingsPanel.setManual(); // set Border for Manual panel
                                SettingsPanel.setActiveJPanel(13);
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            /** Same as VK_RIGHT **/
                            case KeyEvent.VK_UP:
                            case KeyEvent.VK_RIGHT:
                                SettingsPanel.setNews();
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            case KeyEvent.VK_LEFT:
                                SettingsPanel.disableNews();
                                SettingsPanel.setWeather();
                                SettingsPanel.setActiveJPanel(11);
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            case KeyEvent.VK_ENTER:
                                //TODO: Make a function to open the Weather Settings
//                        if( !timerBorder.isRunning() )
//                        {
//                            // If the Borders are'nt shown, then show the CURRENT BORDER
//                            getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                            ActiveJPanel = 1;
//                            timerBorder.start();
//                        }
//                        else
//                        {
//                            System.out.println("><>>> Weather was Entered!!!");
//                            MakeNullBorder();
//                            System.gc();
////                            ExtendedWeather = new WeatherExtended( getWeatherPanel().getTemperature(), getWeatherPanel().getSummary(), getWeatherPanel().getIcon() );
////                            ExtendedWeather.Update();
//                            getWeatherXTNDPanel().Update();
//                            SetContentPanel( ExtendedWeather );
//                            timerBorder.stop();
//                            ActiveJPanel = 4;
//                        }
//                        System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                                timerBorder.restart();
                                ActiveJPanel = 5;
                                SettingsPanel.getTimerBorder().stop();
                                SettingsPanel.MakeNullBorder();
                                SetToDefault();
                                break;

                            default:
                                ActiveJPanel = 7;
                                break;
                        }
                        break;

                    /** Manual Panel - Settings **/
                    case 13:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_UP:
                                SettingsPanel.disableManual();
                                SettingsPanel.setWeather();
                                SettingsPanel.setActiveJPanel(11);
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            /** Same as VK_DOWN **/
                            case KeyEvent.VK_LEFT:
                            case KeyEvent.VK_RIGHT:
                            case KeyEvent.VK_DOWN:
                                SettingsPanel.setManual(); // set Border for Manual panel
                                if(!SettingsPanel.getTimerBorder().isRunning()) SettingsPanel.getTimerBorder().start();
                                else SettingsPanel.getTimerBorder().restart();
                                break;

                            case KeyEvent.VK_ENTER:
                                //TODO: Make a function to open the Weather Settings
//                        if( !timerBorder.isRunning() )
//                        {
//                            // If the Borders are'nt shown, then show the CURRENT BORDER
//                            getWeatherPanel().setBorder( BorderFactory.createLineBorder(Color.WHITE) );
//                            ActiveJPanel = 1;
//                            timerBorder.start();
//                        }
//                        else
//                        {
//                            System.out.println("><>>> Weather was Entered!!!");
//                            MakeNullBorder();
//                            System.gc();
////                            ExtendedWeather = new WeatherExtended( getWeatherPanel().getTemperature(), getWeatherPanel().getSummary(), getWeatherPanel().getIcon() );
////                            ExtendedWeather.Update();
//                            getWeatherXTNDPanel().Update();
//                            SetContentPanel( ExtendedWeather );
//                            timerBorder.stop();
//                            ActiveJPanel = 4;
//                        }
//                        System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                LSettings.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
                                timerBorder.restart();
                                ActiveJPanel = 5;
                                SettingsPanel.getTimerBorder().stop();
                                SettingsPanel.MakeNullBorder();
                                SetToDefault();
                                break;

                            default:
                                ActiveJPanel = 7;
                                break;
                        }
                        break;
                }
                break;

            /** Case 8 Layout - Settings Panel Weather **/
            /********************************************/
            // XTended Settings Panel - Weather Settings
                case 8:
                switch ( WeatherSet.getActiveJPanel() )
                {
                    /************************************************************************/
                    /** ********************** Index of ActivePanel *************************/
                    /** ------------------------------------------------------------------ **/
                    /** 29 - Server Label   --->   30 - DarkSky Label                      **/
                    /**                     --->   31 - Google Label                       **/
                    /** ------------------------------------------------------------------ **/
                    /** 32 - Key authorization Label ---> 33 - Key JTextField              **/
                    /** ------------------------------------------------------------------ **/
                    /** 34 - Auto Location Label                                           **/
                    /** ------------------------------------------------------------------ **/
                    /** 35 - Manual Location Label ---> 36 - Manual Location JTextField    **/
                    /** 37 - Units Location Label  ---> 38 - Units Longitude JTXTField     **/
                    /**                            ---> 39 - Units Latitude JTXTField      **/
                    /** ------------------------------------------------------------------ **/
                    /** 40 - Save button Panel  ,  41 - Cancel button Panel                **/
                    /** ------------------------------------------------------------------ **/
                    /** ------------------------------------------------------------------ **/
                    /************************************************************************/

                    // DarkSky Server Label
                    case 30:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.get_Key(), 32 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.getDarkSky(), 30 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.getGoogle(), 31 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.getDarkSky(), 30 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                //TODO: Make a function to open the Weather Settings
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;

                    // Google Server Label
                    case 31:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.get_Key(), 32 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.getGoogle(), 31 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.getGoogle(), 31 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.getDarkSky(), 30 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                //TODO: Make a function to open the Weather Settings
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;

                    // Key JLabel
                    case 32:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.getGoogle(), 31 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.get_Key(), 32 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.get_Key(), 32 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                System.gc();
                                setFocusable( false );
                                WeatherSet.setActive( WeatherSet.getT_Key(), 33 );
                                WeatherSet.getT_Key().setFocusable( true );
                                WeatherSet.getT_Key().requestFocus();
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;

                    // Units Location Label
                    case 37:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.getP_Cancel(), 41 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.get_Key(), 32 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                //TODO: Make a function to open the Weather Settings
                                System.gc();
//                                WeatherSet.getT_Key().removeFocusListener( WeatherSet.getT_Key().getFocusListeners() );
                                setFocusable( false );
                                WeatherSet.setActive( WeatherSet.getT_Longitude(), 38 );
                                WeatherSet.getT_Longitude().setFocusable( true );
                                WeatherSet.getT_Longitude().requestFocus();
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;

                    // Save button Panel
                    case 40:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.getP_Save(), 40 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.getP_Cancel(), 41 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.getP_Save(), 40 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                WeatherSet.saveData();
                                WeatherSet.backToTheFuture();
                                Weather.refreshData();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;

                    // Cancel button Panel
                    case 41:
                        switch ( e.getKeyCode() )
                        {
                            case KeyEvent.VK_DOWN:
                                WeatherSet.setActive( WeatherSet.getP_Cancel(), 41 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_UP:
                                WeatherSet.setActive( WeatherSet.getUnitsLabel(), 37 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_LEFT:
                                WeatherSet.setActive( WeatherSet.getP_Cancel(), 41 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_RIGHT:
                                WeatherSet.setActive( WeatherSet.getP_Save(), 40 ); // Make null border for Weather panel
                                break;

                            case KeyEvent.VK_ENTER:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                System.out.println(">>> Enter Was Pressed!!!");
                                break;

                            case KeyEvent.VK_ESCAPE:
                                WeatherSet.backToTheFuture();
                                SettingsPanel.setWeather();
                                SetContentPanel( SettingsPanel );
                                timerBorder.restart();
                                ActiveJPanel = 7;
                                SettingsPanel.getTimerBorder().restart();
                                break;

                            default:
                                ActiveJPanel = 8;
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public void keyTyped(KeyEvent e)
    {
        System.out.println("KeyTyped: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e)
    {

        System.out.println("Key Pressed: " + e.getKeyChar());

        /** A Number that will assemble the index of the JPanels to know whom is the active one **/
        /** 1 - Weather Panel  |||  4 (1-Extended) WeatherExtended Panel
         *  2 - Clock Panel
         *  3 - News Panel
         *  5 - Setting Panel
         * **/
        Move(e);
    }

    public void keyReleased(KeyEvent e)
    {
        System.out.println("Key Released: " + e.getKeyChar());
    }

    public static void main(String args[]) throws SocketException
    {
        Main_GUI gui = new Main_GUI();
    }

}