package frc.robot;

public final class RobotMap
{
  public static final int[][] SWERVE_MODULE_LUT = {
    /* clang-format off */

    /* driven spark */ /* steering spark */ /* steering encoder */ /* flip encoder? */
    {        1,                   2,                   0,                 1        }, /* BL */
    {        5,                   6,                   1,                 0        }, /* BR */
    {        3,                   4,                   3,                 0        }, /* FL */
    {        7,                   8,                   6,                 1        }  /* FR */

    /* clang-format on */
  };
}