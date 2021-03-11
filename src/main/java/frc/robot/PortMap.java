package frc.robot;

public class PortMap {

    public static final int[][] MODULE_LUT  = { 
        /* driven spark */ /* steering spark */ /* steering encoder */
        {        6,                   5,                   1        }, /* BL */
        {        2,                   1,                   0        }, /* BR */
        {        8,                   7,                   3        }, /* FL */
        {        4,                   3,                   2        }  /* FR */
    };

}