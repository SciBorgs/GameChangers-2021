package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleIntakePositionCommand extends CommandBase {
    private Timer timer;

    /* kinda jank
     * the intake starts at the upright position and
     * when this command runs the motor runs for .1 sec
     * which jerks the intake and it falls flat
     */
    
    public ToggleIntakePositionCommand() {
        addRequirements(Robot.intakeSubsystem);
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override public void execute() {
        Robot.intakeSubsystem.liftSpark.set(0.3);
    }

    @Override
    public boolean isFinished() {        
        return timer.get() >= 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intakeSubsystem.stopIntakeLift();
    }
}
