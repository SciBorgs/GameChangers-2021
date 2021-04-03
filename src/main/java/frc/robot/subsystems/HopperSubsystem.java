package frc.robot.subsystems;

import frc.robot.hardware.SciSpark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class HopperSubsystem extends SubsystemBase{
    
    public SciSpark hopperSpark;
    private static final double MAX_SPEED = .3; // Change this value
    private boolean isMaxSpeed;

    public HopperSubsystem() {
        this.hopperSpark = new SciSpark(RobotMap.HOPPER_SPARK);
        this.isMaxSpeed = false;
    }

    public void toggleHopper() {
        if (this.isMaxSpeed) {
            this.hopperSpark.set(0);
        } else {
            this.hopperSpark.set(MAX_SPEED);
        }
        this.isMaxSpeed = !this.isMaxSpeed;
    }   
}