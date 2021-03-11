package frc.robot.util;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class AbsoluteEncoder extends DutyCycleEncoder{

    public int port;
    public double gearRatio = 1;
    public double initialAngle = 0;
    public boolean flip = false;
    public double offset;

    public static double ROTATIONS_TO_RADIANS_MULTIPLIER = 2 * Math.PI;


    public AbsoluteEncoder(int port, double gearRatio, double initialAngle, boolean flip) {
        super(port);
        this.port = port;
        this.gearRatio = gearRatio;
        this.initialAngle = initialAngle;
        this.flip = flip;

        super.setDistancePerRotation(gearRatio * ROTATIONS_TO_RADIANS_MULTIPLIER);
        
        for (;;) {
            if (super.getDistance() != 0) {
              break;
            }
        }
        
        this.offset = initialAngle - super.getDistance();
    }
    
    public double getAbsoluteEncoderAngle() {
        double angle = this.getDistance() + this.offset;
        return MathHelper.normalizeAngle(this.flip ? Math.PI - angle : angle);
    }
}
