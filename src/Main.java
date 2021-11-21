import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * @ClassName Main
 * @Description
 * @Author llj
 * @Date 2021/11/17 15:37
 **/

public class Main {
    private static double distance; //默认间隔
    private static double totalDistance = 0.0;
    private static JFrame frame= new JFrame("传感器遍历最佳路径");
    private static Field field = new Field(8,12);
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        System.out.print("请设置传感器节点的间隔: ");
        distance = in.nextDouble();
        System.out.print("请设置速度: ");
        double speed = in.nextDouble();

        View view = new View(field);
        view.setSize(520,520);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(545,520);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel characterPanel = new JPanel();
        characterPanel.setSize(20,520);
        JTextArea textArea = new JTextArea(8,1);
        textArea.setText("A B C D E F G H");
        textArea.setEditable(false);
        textArea.setSize(19,520);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("宋体",Font.BOLD, 19));
        characterPanel.add(textArea, BorderLayout.CENTER);

        JLabel ColumnLabel = new JLabel(" 1  2  3  4  5  6  7  8  9  10 11 12");
        ColumnLabel.setSize(520,40);
        ColumnLabel.setFont(new Font("宋体",Font.BOLD,25));

        frame.add(ColumnLabel, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.add(characterPanel, BorderLayout.EAST);
        frame.setVisible(true);

        int count = 0;
        Sensor current;
        Sensor target;

        System.out.print("请输入需要遍历的传感器总个数：");
        count = in.nextInt();
        System.out.println("请输入需要遍历的传感器：");
        for(int i=0; i<count; i++){
            System.out.print("传感器" + (i+1) + " 的位置坐标（列号行号）："); // H12
            String location = in.next();
            String row = location.substring(0,1); // H
            String column = location.substring(1);// 12
            Sensor sensor = field.getSensor(transformToInt(row)-1, Integer.parseInt(column)-1);
            sensor.toBeVisited();
            sensor.setPosition_x(distance*Integer.parseInt(column)+distance/2.0);
            sensor.setPosition_y(distance*transformToInt(row)+distance/2.0);
            sensor.setRow_index(transformToInt(row)-1);
            sensor.setColumn_index(Integer.parseInt(column)-1);
            field.getToBeVisitedSensor().add(sensor);
            frame.repaint();
        }

        {
            System.out.print("请输入起点坐标: ");
            String location = in.next();
            String row = location.substring(0,1);
            String column = location.substring(1);
            current = field.getSensor(transformToInt(row)-1, Integer.parseInt(column)-1);
            current.Visited();
            field.getToBeVisitedSensor().remove(current);
            frame.repaint(); // 起点变绿
        }

        while(field.getToBeVisitedSensor().size() > 0){
            // 确定目标节点
            target = field.getToBeVisitedSensor().get(0);
            // 找最近的待遍历节点，然后赋值给target
            for(Sensor sensor: field.getToBeVisitedSensor()){
                if(getDistance(current, sensor) < getDistance(current, target)){
                    target = sensor;
                }
            }

            // 找到了下一个目标节点
            // 寻找路径
            findPath(current, target);
            current = target;
            target.Visited();
            field.getToBeVisitedSensor().remove(target);
            frame.repaint(); // 旧的目标节点变绿
        }

        System.out.println("END");
        System.out.println("总路程：" + totalDistance);
        double totalTime = totalDistance / speed;
        System.out.println("总时间：" + totalTime);
    }


    public static void findPath(Sensor current, Sensor target){
        double x_distance = Math.abs(current.getPosition_x() - target.getPosition_x());
        double y_distance = Math.abs(current.getPosition_y() - target.getPosition_y());
        if(x_distance==0 && y_distance==0){
            return;
        }
        // 对角线
        if(x_distance == y_distance){
            // 目标节点在左上方
            if(target.getPosition_x()<current.getPosition_x() && target.getPosition_y()<current.getPosition_y()){
                // current Go LeftUp (totalDistance+g2)
                // 划线
                while(target.getPosition_x()<current.getPosition_x()){
                    System.out.println("你要走下一步吗");
                    in.next();
                    field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(1);
                    frame.repaint(); // 划线
                    goLeftUp(current);
                }
                // until current.getPosition_x()==target.getPosition_x()
            }
            // 目标节点在右上方
            else if(target.getPosition_x()>current.getPosition_x() && target.getPosition_y()<current.getPosition_y()){
                // current Go RightUp (totalDistance+g2)
                // 划线
                while(target.getPosition_x()>current.getPosition_x()){
                    System.out.println("你要走下一步吗");
                    in.next();
                    field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(3);
                    frame.repaint();
                    goRightUp(current);
                }
                // until current.getPosition_x()==target.getPosition_x()
            }
            // 目标节点在左下方
            else if(target.getPosition_x()<current.getPosition_x() && target.getPosition_y()>current.getPosition_y()){
                // current Go LeftDown (totalDistance+g2)
                // 划线
                while(target.getPosition_x()<current.getPosition_x()){
                    System.out.println("你要走下一步吗");
                    in.next();
                    field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(7);
                    frame.repaint();
                    goLeftDown(current);
                }
                // until current.getPosition_x()==target.getPosition_x()
            }
            // 目标节点在右下方
            else{
                // current Go RightDown (totalDistance+g2)
                // 划线
                while(target.getPosition_x()>current.getPosition_x()){
                    System.out.println("你要走下一步吗");
                    in.next();
                    field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(9);
                    frame.repaint();
                    goRightDown(current);
                }
                // until current.getPosition_x()==target.getPosition_x()
            }
        }
        // 宽度差大于高度差
        else if(x_distance > y_distance){
            // 目标节点在左边
            if(target.getPosition_x() < current.getPosition_x()){
                // current Go Left (totalDistance+1)
                // 划线
                System.out.println("你要走下一步吗");
                in.next();
                field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(4);
                frame.repaint();
                goLeft(current);
                // findPath
                findPath(current, target);
            }
            // 目标节点在右边
            else{
                // current Go Right (totalDistance+1)
                // 划线
                System.out.println("你要走下一步吗");
                in.next();
                field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(6);
                frame.repaint();
                goRight(current);
//                 findPath
                findPath(current, target);
            }
        }
        // 宽度差小于高度差
        // x_distance < y_distance
        else{
            // 目标节点在上边
            if(target.getPosition_y() < current.getPosition_y()){
                // current Go Up (totalDistance+1)
                // 划线
                System.out.println("你要走下一步吗");
                in.next();
                field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(2);
                frame.repaint();
                goUp(current);
                // findPath
                findPath(current, target);
            }
            // 目标节点在下边
            else{
                // current Go Down (totalDistance+1)
                // 划线
                System.out.println("你要走下一步吗");
                in.next();
                field.getSensor(current.getRow_index(), current.getColumn_index()).getDirections().add(8);
                frame.repaint();
                goDown(current);
                // findPath
                findPath(current, target);
            }
        }
    }

    public static Sensor goLeftUp(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()-distance*1);
        sensor.setPosition_y(sensor.getPosition_y()-distance*1);

        sensor.setRow_index(sensor.getRow_index()-1);
        sensor.setColumn_index(sensor.getColumn_index()-1);

        totalDistance += distance*Math.sqrt(2);
        return sensor;
    }

    public static Sensor goUp(Sensor sensor){
        sensor.setPosition_y(sensor.getPosition_y()-distance*1);

        sensor.setRow_index(sensor.getRow_index()-1);

        totalDistance += distance;

        return sensor;
    }

    public static Sensor goRightUp(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()+distance*1);
        sensor.setPosition_y(sensor.getPosition_y()-distance*1);

        sensor.setRow_index(sensor.getRow_index()-1);
        sensor.setColumn_index(sensor.getColumn_index()+1);

        totalDistance += distance*Math.sqrt(2);

        return sensor;
    }

    public static Sensor goLeft(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()-distance*1);

        sensor.setColumn_index(sensor.getColumn_index()-1);

        totalDistance += distance;

        return sensor;
    }

    public static Sensor goRight(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()+distance*1);

        sensor.setColumn_index(sensor.getColumn_index()+1);

        totalDistance += distance;

        return sensor;
    }

    public static Sensor goLeftDown(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()-distance*1);
        sensor.setPosition_y(sensor.getPosition_y()+distance*1);

        sensor.setRow_index(sensor.getRow_index()+1);
        sensor.setColumn_index(sensor.getColumn_index()-1);

        totalDistance += distance*Math.sqrt(2);

        return sensor;
    }

    public static Sensor goDown(Sensor sensor){
        sensor.setPosition_y(sensor.getPosition_y()+distance*1);

        sensor.setRow_index(sensor.getRow_index()+1);

        totalDistance += distance;
        return sensor;
    }

    public static Sensor goRightDown(Sensor sensor){
        sensor.setPosition_x(sensor.getPosition_x()+distance*1);
        sensor.setPosition_y(sensor.getPosition_y()+distance*1);

        sensor.setRow_index(sensor.getRow_index()+1);
        sensor.setColumn_index(sensor.getColumn_index()+1);

        totalDistance += distance*Math.sqrt(2);
        return sensor;
    }


    public static double getDistance(Sensor current, Sensor target){
        int a = (current.getRow_index()-target.getRow_index());
        int b = (current.getColumn_index()-target.getColumn_index());
        return Math.sqrt(a*a + b*b);
    }

    public static int transformToInt(String s){
        switch(s){
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            default:
                return -1;
        }
    }
}
