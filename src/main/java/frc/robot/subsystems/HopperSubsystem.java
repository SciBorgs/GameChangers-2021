package frc.robot.subsystems;

import frc.robot.hardware.SciSpark;

import frc.robot.RobotMap;

public class HopperSubsystem extends SubsystemBase{
    
    public SciSpark hopperSpark;
    private static final double MAX_SPEED = 10; // Change this value
    private boolean isMaxSpeed;

    public HopperSubsystem() {
        this.hopperSpark = new SciSpark(RobotMap.hopperSpark);
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