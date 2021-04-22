package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class LowerHoodAngleCommand extends CommandBase{
    public LowerHoodAngleCommand() {
        addRequirements(Robot.shooterSubsystem);
    }
    @Override public void execute() {
        Robot.shooterSubsystem.setHoodSpeed(-0.01);
    }
    @Override public boolean isFinished() {
        return true;
    }
}
