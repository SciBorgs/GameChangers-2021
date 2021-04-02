package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.Robot;

public class Localization {
  public static Point currentPos = new Point(0.0,0.0);
  public static Waypoint currentWaypoint;

  private static Timer timer;
  private static final double TRACK_LENGTH = 30;
  private static final double TRACK_WIDTH  = 29.579;
  private static final double WHEEL_RADIUS = 2.0;
  private static double prevTime;

  private static double getModuleSpeed(int index) {
    double speed = Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    return speed;
  }
  
  public Localization()
  {
    currentPos = new Point(0.0,0.0);
    currentWaypoint = new Waypoint(currentPos, 0.0);
    timer = new Timer();
    prevTime = 0;
    timer.start();
  }

  private static double getModuleAngle(int index) {
    double angle = Robot.swerveSubsystem.modules[index].steeringEncoder.getAngle();
    return angle;
  }

  public static void start() {
    timer = new Timer();
    prevTime = 0;
    timer.start();
  }

  public static void update()
  {
    var frontLeftState = new SwerveModuleState(getModuleSpeed(2), new Rotation2d(getModuleAngle(2)));
    var frontRightState = new SwerveModuleState(getModuleSpeed(3), new Rotation2d(getModuleAngle(3)));
    var backLeftState = new SwerveModuleState(getModuleSpeed(0), new Rotation2d(getModuleAngle(0)));
    var backRightState = new SwerveModuleState(getModuleSpeed(1), new Rotation2d(getModuleAngle(1)));
    //for (int i = 0; i < 4; i++) {
    //  if (getModuleSpeed(i) != 0) {
    //    System.out.println("Speed of Module #" + i + ": " + getModuleSpeed(i));
    //  }
    //  if (getModuleAngle(i) != 0) {
    //    System.out.println("Angle of Module #" + i + ": " + Math.toDegrees(getModuleAngle(i)));
    //  }
    //}
    // Convert to chassis speeds
    Translation2d m_frontLeftLocation = new Translation2d(TRACK_LENGTH/2, TRACK_WIDTH/2);
    Translation2d m_frontRightLocation = new Translation2d(TRACK_LENGTH/2, -TRACK_WIDTH/2);
    Translation2d m_backLeftLocation = new Translation2d(-TRACK_LENGTH/2, TRACK_WIDTH/2);
    Translation2d m_backRightLocation = new Translation2d(-TRACK_LENGTH/2, -TRACK_WIDTH/2);
    SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
      m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
    );
    ChassisSpeeds chassisSpeeds = kinematics.toChassisSpeeds(
  frontLeftState, frontRightState, backLeftState, backRightState);

    // Getting individual speeds
    double FWD = chassisSpeeds.vxMetersPerSecond;
    double STR = chassisSpeeds.vyMetersPerSecond;
    double ROT = chassisSpeeds.omegaRadiansPerSecond;
    //if (FWD != 0 || STR != 0 || ROT != 0) {
    //  System.out.println("FWD: " + FWD + "\tSTR: " + STR + "\tROT " + ROT);
    //}
    //Field centric modifications:
    double theta = Robot.swerveSubsystem.pigeon.getAngle();
    FWD = FWD * Math.cos(theta) - STR * Math.sin(theta);
    STR = FWD * Math.sin(theta) + STR * Math.cos(theta);
    
    //System.out.println("Theta: " + Math.toDegrees(theta) + "\tFWD: " + FWD + "\tSTR: " + STR);

    double deltaT = timer.get() - prevTime;
    double newX = currentPos.getX() + deltaT*STR;
    double newY = currentPos.getY() + deltaT*FWD;
    double newHeading = currentWaypoint.getHeading() + deltaT * ROT;
    currentPos.setX(newX);
    currentPos.setY(newY);
    prevTime = timer.get();
    currentWaypoint.setPoint(currentPos);
    currentWaypoint.setHeading(newHeading);
  }
}

