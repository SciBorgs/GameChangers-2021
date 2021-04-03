package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SwerveResetModulesCommand extends CommandBase {
    public SwerveResetModulesCommand()
    {
        addRequirements(Robot.swerveSubsystem);
    }

    @Override public void execute()
    {
        Robot.swerveSubsystem.resetModulePosition();
    }

    @Override
    public boolean isFinished() {
        return Robot.swerveSubsystem.areModulesReset();
    }
}