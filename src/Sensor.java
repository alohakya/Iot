import java.awt.*;
import java.util.ArrayList;

public class Sensor {

    private double position_x;
    private double position_y;
    private int row_index;
    private int column_index;

    private String state;
    // 普通, 白色/灰色
    // 待遍历, 红色
    // 已遍历, 绿色

    private ArrayList<Integer> directions = new ArrayList<>();

    private int direction;
    // LeftUp = 1;
    // Up = 2;
    // RightUp = 3;
    // Left = 4;
    // Right = 6;
    // LeftDown = 7;
    // Down = 8;
    // RightDown = 9;

    public Sensor(){
        state = "Normal";
        direction = 5;

    }

    public void toBeVisited(){
        state = "ToBeVisited";
    }

    public void Visited(){
        state = "Visited";
    }

    public void addLine(Graphics graphics, int x, int y, int radius, int grid_size, int direction){
        switch (direction){
            case 1:
                graphics.drawLine(x-radius,y-radius,x-grid_size+radius,y-grid_size+radius);
                break;
            case 2:
                graphics.drawLine(x,y-radius,x,y-grid_size+radius);
                break;
            case 3:
                graphics.drawLine(x,y,x + grid_size,y-grid_size);
                break;
            case 4:
                graphics.drawLine(x-radius,y,x - grid_size + radius,y);
                break;
            case 6:
                graphics.drawLine(x+radius,y,x + grid_size-radius,y);
                break;
            case 7:
                graphics.drawLine(x,y,x - grid_size,y+grid_size);
                break;
            case 8:
                graphics.drawLine(x,y+radius,x,y+grid_size-radius);
                break;
            case 9:
                graphics.drawLine(x,y,x + grid_size,y+grid_size);
                break;
            default:
                break;
        }
    }

    // 圆心坐标为(x,y), 半径为radius
    public void draw(Graphics graphics, int x, int y, int radius, int grid_size){
        graphics.drawOval(x-radius, y-radius, radius*2, radius*2);
        switch(state){
            case "Normal":
                graphics.setColor(Color.lightGray);
                break;
            case "ToBeVisited":
                graphics.setColor(Color.RED);
                break;
            default:
                graphics.setColor(Color.GREEN);
                break;
        }
        graphics.fillOval(x-radius, y-radius, radius*2, radius*2);
        for(Integer i : directions){
            addLine(graphics, x, y, radius, grid_size, i);
        }
    }

    public double getPosition_x() {
        return position_x;
    }

    public void setPosition_x(double position_x) {
        this.position_x = position_x;
    }

    public double getPosition_y() {
        return position_y;
    }

    public void setPosition_y(double position_y) {
        this.position_y = position_y;
    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getColumn_index() {
        return column_index;
    }

    public void setColumn_index(int column_index) {
        this.column_index = column_index;
    }

    public ArrayList<Integer> getDirections() {
        return directions;
    }
}
