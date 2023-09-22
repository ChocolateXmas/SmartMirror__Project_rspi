import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chocolate_starfish on 06/05/2018.
 */
public class WeatherSet extends JPanel implements ActionListener
{

    /** Class Jpanel for the Weather Settings **/

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
    private static int ActivePanel = 30;
    /** ------------------------------------------------------------------ **/
    /************************************************************************/

    // Elements
    private static JPanel      MainContent_Container;
    private static JPanel      MainTitle_Panel;
    private static JLabel      MainTitle = new JLabel("~ ∵∴ הגדרות מזג אוויר ∴∵ ~ ");
    private static JLabel      Server, Server2Display, KeyAutho, Location, Location_Auto, Location_Units, Location_Manual, Longitude, Latitude; // JLabels for information
    private static JTextField  T_Key, T_Location; // JTextFields for information
    private static JPlaceField T_Longitude, T_Latitude; // JTextFields for units info with place holder
    private static JPanel      P_Server, P_Key, P_Location, P_UnitsLocJLbl, P_UnitsContainer; // JPanel for containing the information JTXT + JLBL
    private static JPanel      P_Cancel, P_Save, Destiny_Container;
    private static JLabel      Cancel, Save;

    private final String SAVE   = "שמור";
    private final String CANCEL = "בטל";

    private static JLabel[] WeatherServers = { new JLabel("DarkSky"), new JLabel("Google") };

    private static JLabel Slash = new JLabel(" / ");
    private static JLabel ServerSlash = new JLabel(" / ");

    private static final String BASE    = " | ";
    private static       JLabel Base    = new JLabel( BASE );
    private static final String SLASHER = " - ";

    private static String SelectedKey  = "";
    private static String SelectedLong = "";
    private static String SelectedLat  = "";

    private static int SelectedServer = 0;

    protected void createServerLabel()
    {
        for( int i = 0; i < WeatherServers.length; i++ )
        {
            WeatherServers[i].setFont( new Font("Times new Roman", Font.ITALIC, 28) );
            WeatherServers[i].setForeground( Color.WHITE );
        }
    }

    // Constructor for This Class
    public WeatherSet()
    {
        _init();

        // Initializing the Container JPanels
        P_Server = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
        P_Server.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
        P_Key = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
        P_Key.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
        P_Location = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
        P_Location.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
        P_UnitsLocJLbl = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
        P_UnitsLocJLbl.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );

        // Color Settings for The Container JPanels
        P_Server.setBackground( Color.BLACK );
        P_Key.setBackground( Color.BLACK );
        P_Location.setBackground( Color.BLACK );
        P_UnitsLocJLbl.setBackground( Color.BLACK );

//        P_Server.add( C_Server );
        createServerLabel();
        P_Server.add( Server );
        P_Server.add( Server2Display );
        P_Server.add( Base );
//        WeatherServers[0].setBorder( compound );
        P_Server.add( WeatherServers[0] );
        P_Server.add( ServerSlash );
        P_Server.add( WeatherServers[1] );
        MainContent_Container.add( P_Server );

        P_Key.add( KeyAutho );
        P_Key.add( T_Key );
        MainContent_Container.add( P_Key );

        P_Location.add( Location );
        P_Location.add( Location_Auto );
        P_Location.add( Slash );
        P_Location.add( Location_Manual );
        P_Location.add( T_Location );
        P_UnitsLocJLbl.add( Location_Units );
        P_UnitsLocJLbl.add( P_UnitsContainer );
        MainContent_Container.add( P_UnitsLocJLbl );
        MainContent_Container.setBorder( BorderFactory.createLineBorder(Color.WHITE, 5) );
        MainContent_Container.add( Destiny_Container );

        MainContent_Container.add(Box.createRigidArea(new Dimension(0,30)));

        add( MainContent_Container, BorderLayout.CENTER);

        /** Setting the first option as selected **/
        setSelectedBorder( WeatherServers[0] );
        ActivePanel = 30;

        setSavedSettings(); // to set the info that already exist
        SelectedKey = T_Key.getText();
        SelectedLong = T_Longitude.getText();
        SelectedLat = T_Latitude.getText();
//        hideSave(); // To hide the save button, will appear back when any data will be changed

    }

    // _Init Function to initialize all of the objects
    private void _init()
    {
        setLayout( new BorderLayout(0,20) );
        setBackground( Color.BLACK );
        setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) ); // Padding the border of the main JFrame


        MainContent_Container = new JPanel();
        MainContent_Container.setLayout( new BoxLayout(MainContent_Container, BoxLayout.Y_AXIS) );
        MainContent_Container.setBackground( Color.BLACK );
        MainContent_Container.setBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15) ); // Padding the border of the main JFrame


        MainTitle_Panel = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        MainTitle_Panel.setBackground( Color.BLACK );
        MainTitle.setForeground( Color.WHITE );
        MainTitle.setFont(new Font("Times new Roman", Font.ITALIC, 49));
        MainTitle_Panel.add( MainTitle );

        add(MainTitle_Panel, BorderLayout.PAGE_START);

          /******************************************/
         /** Initializing Server Option - JLabels **/
        /******************************************/
        SelectedServer = API_Connect.getSelectedWeatherServ();
        Server = new JLabel("  שרת:   "); // JLabel to DISPLAY info about Option
        Server.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        Server.setForeground( Color.WHITE );
        Server2Display = new JLabel( SLASHER + WeatherServers[ SelectedServer ].getText() + SLASHER ); // JLabel to DISPLAY info about the chosen Option
        Server2Display.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        Server2Display.setForeground( Color.WHITE );
        setBottomBorder( Server2Display ); // Setting a border to differ it from others SO SPECIAL KAWAII (づ｡◕‿‿◕｡)づ
        Base.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        Base.setForeground( Color.WHITE );


          /*****************************************************/
         /** Initializing Key Authorization Option - JLabels **/
        /*****************************************************/
        KeyAutho = new JLabel("  מפתח אימות:   "); // JLabel to DISPLAY info about Option
        KeyAutho.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        KeyAutho.setForeground( Color.WHITE );
        T_Key = new JTextField(22); // JTextField to allow the user to ENTER the <Key String>
        T_Key.setHorizontalAlignment(SwingConstants.CENTER); // To align the Text to Center
        T_Key.setBorder( BorderFactory.createLineBorder(Color.WHITE) );
        T_Key.setBackground(Color.BLACK);
        T_Key.setForeground(Color.WHITE);
        T_Key.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        setBottomBorder( T_Key );


          /************************************************/
         /** Initializing Key Location Option - JLabels **/
        /************************************************/
        Location = new JLabel("  מיקום ");
        Location.setForeground( Color.WHITE );
        Location_Auto = new JLabel(" אוטו' ");
        Location_Auto.setForeground( Color.WHITE );
        Location_Manual = new JLabel(" ידני:  ");
        Location_Manual.setForeground( Color.WHITE );
        Location.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        Location_Auto.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        Location_Manual.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        T_Location = new JTextField(16);
        T_Location.setHorizontalAlignment(SwingConstants.CENTER);
        T_Location.setBackground(Color.BLACK);
        T_Location.setForeground(Color.WHITE);
        T_Location.setFont(new Font("Times new Roman", Font.ITALIC, 28));
        setBottomBorder( T_Location );

        Location_Units = new JLabel("  יחידות ציון: ");
        Location_Units.setForeground( Color.WHITE );
        Location_Units.setFont(new Font("Times new Roman", Font.ITALIC, 28) );

        Longitude = new JLabel("קו אורך:");
        Longitude.setForeground( Color.WHITE );
        Longitude.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        T_Longitude = new JPlaceField(20);
        T_Longitude.setPlaceholder( "- קו אורך -" );
        T_Longitude.setHorizontalAlignment(SwingConstants.CENTER);
        T_Longitude.setBackground(Color.BLACK);
        T_Longitude.setForeground( Color.WHITE );
        T_Longitude.setSelectionColor( Color.BLACK );
        T_Longitude.setSelectedTextColor( Color.WHITE );
        setBottomBorder( T_Longitude );
//        T_Longitude.setBorder( BorderFactory.createLineBorder(Color.RED) );
        T_Longitude.setForeground(Color.WHITE);
        T_Longitude.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        Latitude = new JLabel("קו רוחב:");
        Latitude.setForeground( Color.WHITE );
        Latitude.setFont( new Font("Times new Roman", Font.ITALIC, 28) );
        T_Latitude = new JPlaceField( 20);
        T_Latitude.setPlaceholder( "- קו רוחב -" );
        T_Latitude.setHorizontalAlignment( SwingConstants.CENTER );
        T_Latitude.setSelectionColor( Color.BLACK );
        T_Latitude.setSelectedTextColor( Color.WHITE );
        T_Latitude.setBackground(Color.BLACK);
        T_Latitude.setForeground(Color.WHITE);
        setBottomBorder( T_Latitude );
        T_Latitude.setFont( new Font("Times new Roman", Font.ITALIC, 28) );

        P_UnitsContainer = new JPanel();
        P_UnitsContainer.setLayout( new BoxLayout( P_UnitsContainer, BoxLayout.PAGE_AXIS ) );
        P_UnitsContainer.setBackground( Color.BLACK );
        P_UnitsContainer.add( T_Latitude );
        JLabel lbltest = new JLabel("Latitude");
        lbltest.setForeground( Color.WHITE );
//        P_UnitsContainer.add( lbltest );
        P_UnitsContainer.add( Box.createRigidArea(new Dimension(0,10) ) );
        P_UnitsContainer.add( T_Longitude );
        lbltest.setText("Longitude");
//        P_UnitsContainer.add( lbltest );



        /***************************************************/
         /** Initializing Server Divider Graphic - JLabels **/
        /***************************************************/
        Slash.setFont(new Font("Times new Roman", Font.ITALIC, 34));
        Slash.setForeground( Color.WHITE );

        ServerSlash.setFont(new Font("Times new Roman", Font.ITALIC, 34));
        ServerSlash.setForeground( Color.WHITE );


          /**************************************************************/
         /** Initializing Save and Cancel information field - JLabels **/
        /**************************************************************/
        // Save Area
        P_Save = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
        P_Save.setBackground( Color.BLACK );
        Save = new JLabel( SAVE );
        Save.setForeground( Color.WHITE );
        Save.setFont( new Font("Times new Roman", Font.ITALIC, 42) );
        Save.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
        P_Save.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) );
        P_Save.add( Save );
        // Cancel Area
        P_Cancel = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
        P_Cancel.setBackground( Color.BLACK );
        Cancel = new JLabel( CANCEL );
        Cancel.setForeground( Color.WHITE );
        Cancel.setFont( new Font("Times new Roman", Font.ITALIC, 42) );
        Cancel.setBorder( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
        P_Cancel.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) );
        P_Cancel.add( Cancel );
        // Creating the Container for these 2 buttons in a X_AXIS
        Destiny_Container = new JPanel();
        Destiny_Container.setLayout( new BoxLayout( Destiny_Container, BoxLayout.X_AXIS ) );
        Destiny_Container.setBackground( Color.BLACK );
        Destiny_Container.add(Box.createRigidArea(new Dimension(30,0)));
        Destiny_Container.add( P_Cancel );
        Destiny_Container.add(Box.createRigidArea(new Dimension(30,0)));
        Destiny_Container.add( P_Save );
        Destiny_Container.add(Box.createRigidArea(new Dimension(30,0)));
    }

    /** Start of << Components Border >> **/
    /**************************************/
    public static void setBottomBorder( JLabel comp )       { comp.setBorder(BorderFactory.createMatteBorder( 0, 0, 4, 0, Color.WHITE )); }
    public static void setSelectedBorder( JLabel comp )     { comp.setBorder(BorderFactory.createMatteBorder( 1, 1, 1, 1, Color.WHITE )); }
    public static void setSelectedBorder( JPanel comp )
    {
        comp.setBorder(BorderFactory.createMatteBorder( 1, 1, 8, 1, Color.WHITE ));
//        if( getP_CS_OptionIndex() == 40 ) // Save button Panel
//            P_Save.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) ); // Return to original Border
//        else
//            P_Cancel.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) ); // Return to original Border
    }
    public static void setBottomBorder( JTextField comp )   { comp.setBorder(BorderFactory.createMatteBorder( 0, 0, 4, 0, Color.WHITE )); }
    public static void setSelectedBorder( JTextField comp ) { comp.setBorder(BorderFactory.createMatteBorder( 1, 1, 4, 1, Color.WHITE )); }
    /************************************/
    /** END of << Components Border >> **/


//    /** Functions to Show/Hide Save Button **/
//    /****************************************/
//    public static void showSave()
//    {
//        Save.setForeground( Color.WHITE );
//        P_Save.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) );
//    }
//    public static void hideSave()
//    {
//        Save.setForeground( Color.BLACK );
//        P_Save.setBorder( BorderFactory.createLineBorder( Color.BLACK, 3, true ) );
//    }
//    /****************************************/
//    /** - END - Functions to Show/Hide Save Button **/


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
    /** 35 - Manual Location Label ---> 36 - Manual Location JTXTField     **/
    /** 37 - Units Location Label  ---> 38 - Units Longitude JTXTField     **/
    /**                            ---> 39 - Units Latitude JTXTField      **/
    /** ------------------------------------------------------------------ **/
    /** 40 - Save button Panel  ,  41 - Cancel button Panel                **/
    /** ------------------------------------------------------------------ **/
    /** ------------------------------------------------------------------ **/
    /************************************************************************/
    public static JLabel       getDarkSky()          { return WeatherServers[0]; }
    public static JLabel       getGoogle()           { return WeatherServers[1]; }
    public static JLabel       get_Key()             { return KeyAutho; }
    public static JTextField   getT_Key()            { return T_Key; }
    public static JLabel       getLocation_Auto()    { return Location_Auto; }
    public static JLabel       getLocation_Manual()  { return Location_Manual; }
    public static JTextField   getT_Location()       { return T_Location; }
    public static JLabel       getUnitsLabel()       { return Location_Units; }
    public static JTextField   getT_Longitude()      { return T_Longitude; }
    public static JPlaceField  getT_Latitude()       { return T_Latitude; }
    public static JPanel       getP_Cancel()         { return P_Cancel; }
    public static JPanel       getP_Save()           { return P_Save; }
//    public static JPanel       getP_CS_Option() // Returns the 1 Button from the 2 (Save, Cancel) to show it as a selected component
//    {
//        if( Save.getBackground() == Color.WHITE )
//            return  getP_Save();
//        else
//            return  getP_Cancel();
//    }
//    public static int          getP_CS_OptionIndex() // Rerturns the index id of the Panel (Save, Cancel)
//    {
//        if( Save.getBackground() == Color.WHITE )
//            return  40;
//        else
//            return  41;
//    }

    public static void setSavedSettings()
    {
        // The selected Server foe Weather info
        Server2Display.setText( SLASHER + WeatherServers[ API_Connect.getSelectedWeatherServ() ].getText() + SLASHER );

        // the Key for the DarkSky API Connection
        T_Key.setText( API_Connect.getKey() );
        T_Key.setSelectedTextColor( Color.WHITE );
        T_Key.setSelectionColor( Color.BLACK );

        // The selected Location option
        switch ( API_Connect.getSelectedWeatherLoc() )
        {
            case 0:
                setBottomBorder( Location_Auto );
                break;

            case 1:
                setBottomBorder( Location_Manual );
                break;

            case 2:
                setBottomBorder( Location_Units );
                T_Latitude.setText( Weather.readLatitude() );
                T_Longitude.setText( Weather.readLongitude() );
                break;
        }
    }

    public static void saveData()
    {
        API_Connect.SaveSettings( 0, T_Key.getText(), T_Longitude.getText(), T_Latitude.getText() );

    }

    /** Start of Border Handling **/
    /******************************/
    /******************************/
//    private static int ActiveJPanel = 11;
    // Active JPanel index is already DECLARED at top of this document
    public static int  getActiveJPanel()      { return ActivePanel; }
    public static void setActiveJPanel(int x) { ActivePanel = x; }

    public static void MakeNullBorder()
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
        switch ( ActivePanel )
        {
            /** Server Label **/
            case 29:
                Server.setBorder( null );
                break;

            /** DarkSky Label **/
            case 30:
                WeatherServers[0].setBorder( null );
                break;

            /** Google Label **/
            case 31:
                WeatherServers[1].setBorder( null );
                break;

            /** Key authorization Label **/
            case 32:
                KeyAutho.setBorder( null );
                break;

            /** Key JTextField **/
            case 33:
                T_Key.setBorder( null );
                setBottomBorder( T_Key );
                break;

            /** Auto Location Label **/
            case 34:
                Location_Auto.setBorder( null );
                break;

            /** Manual Location Label **/
            case 35:
                Location_Manual.setBorder( null );
                break;

            /** Manual Location JTextField **/
            case 36:
                T_Location.setBorder( null );
                setBottomBorder( T_Location );
                break;

            /** Units Location Label **/
            case 37:
                Location_Units.setBorder( null );
                break;

            /** Units Longitude JTXTField **/
            case 38:
                T_Longitude.setBorder( null );
                setBottomBorder( T_Longitude );
                break;

            /** Units Latitude JTXTField **/
            case 39:
                T_Latitude.setBorder( null );
                setBottomBorder( T_Latitude );
                break;

            /** P_Save JPanel **/
            case 40 :
                P_Save.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) );
                break;

            /** P_Cancel JPanel **/
            case 41 :
                P_Cancel.setBorder( BorderFactory.createLineBorder( Color.WHITE, 3, true ) );
                break;
        }
    }

    public static void setActive(JLabel comp, int ActivePanelIndex)
    {
        MakeNullBorder();
        setSelectedBorder( comp );
        setActiveJPanel( ActivePanelIndex );
    }
    public static void setActive(JTextField comp, int ActivePanelIndex)
    {
        MakeNullBorder();
        setSelectedBorder( comp );
        setActiveJPanel( ActivePanelIndex );
    }
    public static void setActive(JPlaceField comp, int ActivePanelIndex)
    {
        MakeNullBorder();
        setSelectedBorder( comp );
        setActiveJPanel( ActivePanelIndex );
    }
    public static void setActive(JPanel comp, int ActivePanelIndex)
    {
        MakeNullBorder();
        setSelectedBorder( comp );
        setActiveJPanel( ActivePanelIndex );
    }

    // When exiting this Weather settings panel, all borders and ActiveJPanels get back to ORIGINAL
    public static void backToTheFuture()
    {
        MakeNullBorder();
        setActive( WeatherSet.getDarkSky(), 30 );
        setSavedSettings();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    public static void main(String args[])
    {
        JFrame jf = new JFrame("Settings Try");
        jf.setContentPane( new WeatherSet() );
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(768, 1366);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}