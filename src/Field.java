import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName Field
 * @Description
 * @Author llj
 * @Date 2021/11/17 16:18
 **/


public class Field {
    private int row;
    private int column;
    private Sensor[][] field;
    private ArrayList<Sensor> toBeVisitedSensor = new ArrayList<>();

    public Field(int row, int column) {
        this.row = row;
        this.column = column;

        field = new Sensor[row][column];
        for(int i = 0; i< row; i++){
            for(int j = 0; j< column; j++){
                Sensor sensor = new Sensor();
                sensor.setRow_index(i);
                sensor.setColumn_index(j);
                field[i][j] = sensor;
            }
        }
    }

    public Sensor getNeighbour(Sensor sensor, int direction){
        switch (direction){
            case 1:
                return field[sensor.getRow_index()-1][sensor.getColumn_index()-1];
            case 2:
                return field[sensor.getRow_index()-1][sensor.getColumn_index()];
            case 3:
                return field[sensor.getRow_index()-1][sensor.getColumn_index()+1];
            case 4:
                return field[sensor.getRow_index()][sensor.getColumn_index()-1];
            case 6:
                return field[sensor.getRow_index()][sensor.getColumn_index()+1];
            case 7:
                return field[sensor.getRow_index()+1][sensor.getColumn_index()-1];
            case 8:
                return field[sensor.getRow_index()+1][sensor.getColumn_index()];
            case 9:
                return field[sensor.getRow_index()+1][sensor.getColumn_index()+1];
            default:
                return null;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setSensor(int row, int column, Sensor sensor){
        field[row][column] = sensor;
    }

    public Sensor getSensor(int row, int column){
        return field[row][column];
    }

    public ArrayList<Sensor> getToBeVisitedSensor() {
        return toBeVisitedSensor;
    }
}
