import java.util.ArrayList;

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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Sensor getSensor(int row, int column){
        return field[row][column];
    }

    public ArrayList<Sensor> getToBeVisitedSensor() {
        return toBeVisitedSensor;
    }
}
