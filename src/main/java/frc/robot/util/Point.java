package frc.robot.util;
import java.lang.Math;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceToPoint(Point P2) {
        return Math.sqrt(Math.pow((P2.x - this.x),2) + Math.pow((P2.y - this.y),2));
    }
}
