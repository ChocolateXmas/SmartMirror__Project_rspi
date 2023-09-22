/**
 * Created by chocolate_starfish on 07/11/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Clock extends JPanel
{
    private DateFormat  ClockFormat;
    private DateFormat  DateFormat;

    private JLabel      clock_lbl;
    private JLabel      day_lbl;
    private JLabel      date_lbl;

    private JPanel      Bottom_Time;
    private JPanel      Center_Day;
    private JPanel      Uppear_Date;


    public Clock()
    {
        __init(); /** Initiate the JPanel Window **/


        Bottom_Time.setBackground(Color.BLACK);
        Center_Day.setBackground(Color.BLACK);
        Uppear_Date.setBackground(Color.BLACK);

        /** Clock Label Setup **/
        clock_lbl.setFont(new Font("Times new Roman", Font.ITALIC, 48));
        clock_lbl.setForeground(Color.WHITE);

        /** Day In Week Setup **/
        day_lbl.setFont(new Font("Times new Roman", Font.ITALIC, 26));
        day_lbl.setForeground(Color.WHITE);

        /** Date In Calendar Setup **/
        date_lbl.setFont(new Font("Times new Roman", Font.ITALIC, 44));
        date_lbl.setForeground(Color.WHITE);

        Uppear_Date.add(date_lbl);
        Center_Day.add(day_lbl);
        Bottom_Time.add(clock_lbl);

        add(Uppear_Date);
        add(Center_Day);
        add(Bottom_Time);

//        dateFormat = new SimpleDateFormat("HH:mm - dd/MMM/yyyy");
        ClockFormat = new SimpleDateFormat("HH:mm");
        DateFormat = new SimpleDateFormat("MMM  dd , yyyy");
        Thread clock = new Thread()
        {
            public void run()
            {

                try
                {
                    while (true)
                    {
                        //get current date time with Date()
                        Date date = new Date();

//                        System.out.println(ClockFormat.format(date)); /** For logging **/
                        clock_lbl.setText( ClockFormat.format(date) );
                        switch(date.getDay())
                        {
                            case 0:
                                day_lbl.setText("ראשון");
                                break;

                            case 1:
                                day_lbl.setText("שני");
                                break;

                            case 2:
                                day_lbl.setText("שלישי");
                                break;

                            case 3:
                                day_lbl.setText("רביעי");
                                break;

                            case 4:
                                day_lbl.setText("חמישי");
                                break;

                            case 5:
                                day_lbl.setText("שישי");
                                break;

                            case 6:
                                day_lbl.setText("שבת");
                                break;
                        }

                        date_lbl.setText( DateFormat.format(date) );

                        Thread.sleep(0b1111101000); // 1 sec in binary
                    }
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

        };
        clock.start();
    }

    private void __init()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.BLACK);

        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
        /**         Declarations - JPanels       **/
        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

        /**            Bottom_Time Panel         **/

        Bottom_Time = new JPanel( new FlowLayout(FlowLayout.RIGHT) );

        /**            END Bottom_Time           **/
        /** ------------------------------------ **/

        /**            Center_Day Panel         **/

        Center_Day = new JPanel( new FlowLayout(FlowLayout.RIGHT) );

        /**            END Center_Day           **/
        /** ------------------------------------ **/

        /**            Center_Day Panel         **/

        Uppear_Date = new JPanel( new FlowLayout(FlowLayout.RIGHT) );

        /**            END Center_Day           **/
        /** ------------------------------------ **/


        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/
        /**         Declarations - JPanels       **/
        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **/

        /**            clock_lbl JLabel          **/

        clock_lbl = new JLabel("-Clock time-");

        /**             END clock_lbl            **/
        /** ------------------------------------ **/

        /**            day_lbl JLabel            **/

        day_lbl = new JLabel("-Week day-");

        /**              END day_lbl             **/
        /** ------------------------------------ **/

        /**            date_lbl JLabel           **/

        date_lbl = new JLabel("-Date-");

        /**             END date_lbl             **/
        /** ------------------------------------ **/
    }

}
