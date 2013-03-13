package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    // WIRING
    //Drive train
    public static final int DT_LEFT_JAG_CHAN = 2;
    public static final int DT_LEFT_JAG_CAR = 2;
    public static final int DT_RIGHT_JAG_CHAN = 3;
    public static final int DT_RIGHT_JAG_CAR = 1;
    //Pulley System
    // Top Pulley
    public static final int P_TOP_JAG_CHAN = 1;
    public static final int P_TOP_JAG_CAR = 1;
    public static final int P_TOP_SERVO_CHAN = 5;
    public static final int P_TOP_SERVO_CAR = 1;
    public static final int P_TOP_LOCK_CHAN = 7;    // Not actually used as of now
    public static final int P_TOP_LOCK_CAR = 1;    // Fix if they throw a lock on 
    public static final double P_TOP_LOCK_LOCKED = .9;
    public static final double P_TOP_LOCK_OPEN = .1;
    public static final double P_TOP_HOME = .30;
    public static final double P_TOP_MAX = .9;
    public static final double P_TOP_MIN = .2;
    public static final int P_TOP_MOTOR_DIR = -1;
    public static final int P_TOP_SERVO_DIR = -1;
    // Right Pulley
    public static final int P_RIGHT_JAG_CHAN = 2;
    public static final int P_RIGHT_JAG_CAR = 1;
    public static final int P_RIGHT_SERVO_CHAN = 6;
    public static final int P_RIGHT_SERVO_CAR = 1;
    public static final int P_RIGHT_LOCK_CHAN = 4;
    public static final int P_RIGHT_LOCK_CAR = 1;
    public static final double P_RIGHT_LOCK_LOCKED = .5;            //PAWL
    public static final double P_RIGHT_LOCK_OPEN = .8;
    public static final double P_RIGHT_HOME = .63;
    public static final double P_RIGHT_MAX = 1;
    public static final double P_RIGHT_MIN = .63;
    public static final int P_RIGHT_MOTOR_DIR = -1;
    public static final int P_RIGHT_SERVO_DIR = -1;
    // Left Pulley
    public static final int P_LEFT_JAG_CHAN = 1;
    public static final int P_LEFT_JAG_CAR = 2;
    public static final int P_LEFT_SERVO_CHAN = 4;
    public static final int P_LEFT_SERVO_CAR = 2;
    public static final int P_LEFT_LOCK_CHAN = 5;
    public static final int P_LEFT_LOCK_CAR = 2;
    public static final double P_LEFT_LOCK_LOCKED = .13;
    public static final double P_LEFT_LOCK_OPEN = .5;
    public static final double P_LEFT_HOME = .62;
    public static final double P_LEFT_MAX = .62;
    public static final double P_LEFT_MIN = .2;
    public static final int P_LEFT_MOTOR_DIR = 1;
    public static final int P_LEFT_SERVO_DIR = 1;
// Arm system
    // Arm
    public static final int A_MOTOR_CHAN = 8;                       // also bs
    public static final int A_MOTOR_CAR = 2;                        // just to stop a pwm error
    // Griper
    public static final int G_SERVO_CHAN = 9;
    public static final int G_SERVO_CAR = 1;
    public static final double G_SERVO_GRIP = .9;
    public static final double G_SERVO_OPEN = .1;
//Operator Input
    // Joysticks
    public static final int HUTCH_STICK = 1;        //top
    public static final int COOKER_STICK_1 = 2;      //left
    public static final int COOKER_STICK_2 = 3;      //right
    //Pot Constants
    public static final int TOP_PULLEY_CHAN = 4;
    public static final int LEFT_PULLEY_CHAN = 3;
    public static final int RIGHT_PULLEY_CHAN = 2;
    //Gyro
    public static final int GYRO_CHAN = 1;   // Made up for now... TODO make sure these channels match
/// ROBOT GEOMETRY
    // ARENA GEOMETRY
}
