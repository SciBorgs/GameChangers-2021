package frc.robot;

public final class RobotMap
{

  //*******************DRIVE TRAIN******************//

  public static final int[][] SWERVE_MODULE_LUT = {
    /* clang-format off */

    /* driven spark */ /* steering spark */ /* steering encoder */ /* flip encoder? */
    {        1,                   2,                   0,                 1        }, /* BL */
    {        5,                   6,                   1,                 0        }, /* BR */
    {        3,                   4,                   3,                 0        }, /* FL */
    {        7,                   8,                   6,                 1        }  /* FR */

    /* clang-format on */
  };

  // *******************INTAKE******************//

  public static final int INTAKE_FLYWHEEL_SPARK = 16;
  public static final int INTAKE_LIFT_SPARK = 11;

  // *******************HOPPER******************//

  public static final int HOPPER_SPARK = 9;

  // *******************SHOOTER******************//

    public static final int SHOOTER_LEFT_SPARK = 15;
    public static final int SHOOTER_RIGHT_SPARK = 10;
    public static final int HOOD_SPARK = -1;
    public static final int HOOD_ENCODER = -1;
    
    // *****************XBOX*****************//

    public static final int XBOX_CONTROLLER = 0;
  
    public static final int XBOX_A = 2;
    public static final int XBOX_B = 3;
    public static final int XBOX_X = 1;
    public static final int XBOX_Y = 4;
  
    public static final int XBOX_BUMPER_LEFT  = -1;
    public static final int XBOX_BUMPER_RIGHT = -1;
  
    public static final int XBOX_BACK  = -1;
    public static final int XBOX_START = -1;
  
    public static final int XBOX_STICK_LEFT_BUTTON  = -1;
    public static final int XBOX_STICK_RIGHT_BUTTON = -1;
  
    public static final int XBOX_TRIGGER_LEFT  = -1;
    public static final int XBOX_TRIGGER_RIGHT = -1;
  
    public static final int XBOX_LEFT_JOY_X = 0;
    public static final int XBOX_LEFT_JOY_Y = 1;
  
    public static final int XBOX_RIGHT_JOY_X = 2;
    public static final int XBOX_RIGHT_JOY_Y = 3;
}