package frc.robot.util;

public class MathHelper {
    
    
    public static double normalizeAngle(double angle) {
        return normalize(angle, 0, 2 * Math.PI);
    }

    public static double normalize(double val, double begin, double end) {
        double offset = val - begin;
        double delta = end - begin;
        return (offset - (Math.floor(offset / delta) * delta)) + begin;
    }

    public static double degreesToRadians(double deg) {
        return deg * Math.PI / 180;
    }

    public static double radiansToDegrees(double rad) {
        return rad * 180 / Math.PI;
    }
}
