/**
 * Created by chocolate_starfish on 09/05/2018.
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class JPlaceField extends JTextField
{

//    public static void main(final String[] args) {
//        final JPlaceField tf = new JPlaceField("");
//        tf.setColumns(20);
//        tf.setPlaceholder("All your base are belong to us!");
//        final Font f = tf.getFont();
//        tf.setFont(new Font(f.getName(), f.getStyle(), 30));
//        JOptionPane.showMessageDialog(null, tf);
//    }

    private String placeholder;

    public JPlaceField() {
    }

    public JPlaceField( final Document pDoc, final String pText, final int pColumns )
    {
        super(pDoc, pText, pColumns);
    }

    public JPlaceField( final int pColumns )
    {
        super(pColumns);
    }

    public JPlaceField( final String pText )
    {
        super(pText);
    }

    public JPlaceField( final String pText, final int pColumns )
    {
        super(pText, pColumns);
    }

    public String getPlaceholder() { return placeholder; }

    @Override
    protected void paintComponent(final Graphics pG)
    {
        super.paintComponent(pG);

        if (placeholder.length() == 0 || getText().length() > 0)
        {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor( getDisabledTextColor() );
        g.drawString( placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top );
    }

    public void setPlaceholder(final String s) { placeholder = s; }

}
