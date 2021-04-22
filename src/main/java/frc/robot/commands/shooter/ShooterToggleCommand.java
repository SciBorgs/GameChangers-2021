package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ShooterToggleCommand extends CommandBase{
    public ShooterToggleCommand() {
        addRequirements(Robot.shooterSubsystem);
    }
    @Override public void execute() {
        Robot.shooterSubsystem.toggleShooter();
    }
    @Override public boolean isFinished() {
        return true;
    }
}
