package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 */
public class LeftPulley extends PIDSubsystem {

    /**
     * Values of a, b and c, the coefficients of a quadratic equation
     * y=ax*x+bx+c, which gives tapelength as a function of pot value, which is
     * voltage between 0 and 5 This equation is particular to each pulley.
     *
     */
    private static final double tapea = -.6296;
    private static final double tapeb = -12.2;
    private static final double tapec = +69.62;
    /**
     * Length of hook is added to tape true for all hooks
     */
    private static final double hooklen = 4.5;
    //add retact tape amount
    ///ad retract tape error
    // Maximum, minimum,and starting  tapelengths are particular to each pulley
    private static final double tapeLenMax = 42;
    private static final double tapeLenMin = .5;
    private static final double initialTapeLen = 20;
    // Motor direction is +1 or -1 depending on its orientation in the robot
    private static final int direct = RobotMap.P_LEFT_MOTOR_DIR;
    // The PWN values for the open and locked postions of the pawl
    private static final double pawlLock = RobotMap.P_LEFT_LOCK_LOCKED;
    private static final double pawlOpen = RobotMap.P_LEFT_LOCK_OPEN;
    // Move the oulley at half velocity for now (Values from 0 to 1
    private static final double pulleyVelocity = .5;
    private static final double stop = 0.;
    // Error limits for the setTapelength method
    private static final double pulleyErrorMax = 1;
    private static final double pulleyErrorMin = .1;
    private static final double pulleyErrorDef = .1;
    // 
    /**
     * State of pawl lock
     *
     */
    public boolean leftPawlLocked;
    // The constants for the P, I and D portion of PID
    // We do not use PID, but it's there waiting for us
    private static final double Kp = 3;
    private static final double Ki = .2;
    private static final double Kd = 0.0;
    //
    Jaguar pulleyMotor;
    Servo pawl;
    AnalogChannel pot = new AnalogChannel(RobotMap.LEFT_PULLEY_CHAN);

    /**
     * Initialize the subsystem here
     */
    public LeftPulley() {
        super("Pulley", Kp, Ki, Kd);
        pulleyMotor = new Jaguar(RobotMap.P_LEFT_JAG_CAR, RobotMap.P_LEFT_JAG_CHAN);
        pot = new AnalogChannel(RobotMap.LEFT_PULLEY_CHAN);
        pawl = new Servo(RobotMap.P_LEFT_LOCK_CAR, RobotMap.P_LEFT_LOCK_CHAN);
        //set pawl unlocked
        pawl.set(pawlOpen);
        leftPawlLocked = false;
        //Set the starting setpoint and have PID start running in the background
        // These are  commented out because it starts in starts in joystick mode.
        // when the command to 
        // These will  set in the command "execute" method
        // setSetpoint(initialTapeLen);                          //
        //enable(); // - Enables the PID controller.
    }

    /**
     * Gets tape length in inches as a function of voltage
     *
     * @return tape length in inches
     */
    public double getTapeLength() {
        double v;
        v = pot.getVoltage();
        double length = tapea * v * v - tapeb * v + +tapec + hooklen;
        return length;

    }
    

    /**
     * 
     * Simply extends or retracts tape,depending on the sign of
     * longer until its length is within "error" of "goalLength"
     * @param goalLength is target length
     * @param error is positive number that sets how close you want to target
     * @param longer specifies direction and is positive if you are extending
     */
    public void setTapelength(double goalLength, double error, int longer) {
        double curLength = getTapeLength();
        // if there is an attempt to extend a locked pulley, Don't do it. stop motor
        // and return
        // Change outcome to unlock and extend?
        if (leftPawlLocked = true & (longer > 0)) {
            pulleyMotor.set(stop);
            return;
        }
        //
        // Check for out of bounds tapelength goals

        if (goalLength > tapeLenMax) {
            goalLength = tapeLenMax;
        }
        if (goalLength < tapeLenMin) {
            goalLength = tapeLenMin;
        }
        if (error > pulleyErrorMax || error < pulleyErrorMin) {   //check for bad error value
            error = pulleyErrorMin;
        }
        if ((-longer * (curLength - goalLength)) > error) {
            pulleyMotor.set(longer * pulleyVelocity * direct);         // sign of velocity is adjusted 
            //by "direct" (+ or - 1)
            //for motor orientation
        } else {
            pulleyMotor.set(stop);
        }
    }

    /**
     * Locks or unlocks the pawl depending setting of "locked"
     * @param locked
     * 
     * @return true if locked, false if open
     */
    public boolean setLock(boolean locked) {
        if (locked) {
            pawl.set(pawlLock);
            // publish the state. Used in setTapeLength ()
            leftPawlLocked = true;
            return true;
        } // If extending a locked tape, the pawl will not release until force on it 
        // is relaxed, therefore
        // Retract tape a little bit to releive pressure before unlocking it;
        else {
            this.setTapelength(.3, .1, -1);
        }
        pawl.set(pawlOpen);
        // Publish the state
        leftPawlLocked = false;
        return false;

    }

    //Set the default command to drive with joysticks
    public void initDefaultCommand() {
        //setDefaultCommand(new JoystickleftPulley()); 
        // TO DO need to get joystick settings
    }

    /**
     * @return if using PID system,The value of the rangefinder used as the PID
     * input device.
     */
    protected double returnPIDInput() {
        return this.getTapeLength();
    }

    /**
     *
     *
     *
     * @param output The value to set the output to as determined by the PID
     * algorithm. This gets called each time through the PID loop to update the
     * output to the motor.
     */
    protected void usePIDOutput(double output) {
        pulleyMotor.set(output);

    }
}
