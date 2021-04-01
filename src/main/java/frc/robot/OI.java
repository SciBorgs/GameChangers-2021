package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    public XboxController xboxController;
    
    public OI() {
        xboxController = new XboxController(RobotMap.XBOX_CONTROLLER);
    }

}
