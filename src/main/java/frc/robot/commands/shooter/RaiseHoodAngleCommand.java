package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RaiseHoodAngleCommand extends CommandBase{
    public RaiseHoodAngleCommand() {
        addRequirements(Robot.shooterSubsystem);
    }
    @Override public void execute() {
        Robot.shooterSubsystem.setHoodSpeed(0.1);
    }
    @Override public boolean isFinished() {                              
               return false;
    }                                                                                                                                  
    @Override public void end(boolean interrupted) {                  
        Robot.shooterSubsystem.setHoodSpeed(0.0);
    }
}
