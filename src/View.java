import javax.swing.*;
import java.awt.*;

/**
 * @ClassName View
 * @Description
 * @Author llj
 * @Date View 17:06
 **/


public class View extends JPanel {
    private static final long serialVersionUID = -525899676212660595L;
    private static final int GRID_SIZE = 42;
    private Field field;
    private static final int radius = 8;

    public View(Field field){
        this.field = field;
    }

    @Override
    public void paint(Graphics graphics){
        super.paint(graphics);
        for(int row = 0; row<field.getRow(); row++){
            for(int column = 0; column<field.getColumn(); column++){
                Sensor sensor = field.getSensor(row, column);
                sensor.draw(graphics, column*GRID_SIZE+GRID_SIZE/2, row*GRID_SIZE+GRID_SIZE/2, radius, GRID_SIZE);
            }
        }
    }
}
