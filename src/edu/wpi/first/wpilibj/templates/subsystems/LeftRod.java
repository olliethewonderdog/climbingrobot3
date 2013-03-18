/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.DigitalServo;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Brinton
 */
public class LeftRod extends PIDSubsystem {
// We don't use pid values at this time
    // droo p =3inches at 42inches
    //=.07*57=4
    //leftservomax .453 back 181   delta 135
    //leftservommin  0  forward 46
    //rightservomax .744 forward 57 46  delta 100
    //rightservomin .385 back 157
    //midservomax  .78    -10   180 forward//.751= -10 degrees delta 190
    //midservomin  .1  back 180

    private static final double Kp = 3;
    private static final double Ki = .2;
    private static final double Kd = 0.0;
    /**
     * @param k2 s the height of the first pyramid rung above floor and the
     * higher pyramid rungs above the middle of the next lower rung
     */
    protected double k2 = 30.75;
    /**
     * @param k1 is the height of the bottom of the side pulleys above frame
     */
    protected double k1 = 1.125;
    /**
     * @param k3 is horizontal distance between rungs
     */
    protected double k3 = 12.1;
    /**
     * @param floorbreakfree tapelength at which the robot climbing from the
     * floor breaks free
     */
    protected double floorbreakfree = 10.5;
    /**
     * @param bumperTouchAngle frame angle at which the rear bumper touches the
     * floor when climbing first rung
     */
    protected double bumperTouchAngle = 25;
    // 
    /**
     * @param rungbreakfreeangle the frame angle condition at which robot breaks
     * free while climbing between rungs
     */
    protected double rungbreakfreeangle = 52;
    /**
     * @param rungbreakfree tapelength condition at which robot breaks free
     * while climbing between rungs
     */
    protected double rungbreakfree = 13;
    /**
     * The following parameters are intermediate values along the way to
     * calculate the expected tape angle at different positions while climbing.
     * These calculations are lengthy, involving lots of trig and a quadratic
     * equation. Intermediate steps help debugging.
     *
     * @param m2 intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle. the calculated tape angle to horizontal
     */
    protected double m2;
    /**
     * @param k4 intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle
     */
    protected double k4;
    /**
     * @param k5 intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle
     */
    protected double k5;
    /**
     * @param a intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle (related to quadratic equation ax^2+bx+c)
     */
    protected double a;
    /**
     * @param b intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle (related to quadratic equation ax^2+bx+c)
     */
    protected double b;
    /**
     * @param c intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle (related to quadratic equation ax^2+bx+c)
     */
    protected double c;
    /**
     * @param x2 intermediate value in long quadratic trig equation for method
     * getClimbTapeAngle
     */
    protected double x2;
    /**
     * @param j2 intermediate value in trig equation for method
     * calcServoFromAngle
     */
    protected double j2;
    /**
     * @param j3 intermediate value in trig equation for method
     * calcServoFromAngle
     */
    protected double j3;
    /**
     * @param j4 intermediate value in trig equation for method
     * calcServoFromAngle. It is distance between rod ends, for a given
     * tapelength and tape angle to the frame, based on relative position of
     * pulley bottom and servo
     *
     */
    protected double j4;
    /**
     * @param j5 intermediate value in trig equation for method
     * calcServoFromAngle. j5 is the angle of the rod end at the servo as a
     * function of the distance between the rod ends* j5 is the angle of the rod
     * end at the servo as a function of the distance between the rod ends
     *
     */
    protected double j5;
    /**
     * @param j6 intermediate value in trig equation for method
     * calcServoFromAngle
     */
    protected double j6;
    /**
     * @param j7 intermediate value in trig equation for method
     * calcServoFromAngle
     */
    protected double j7;
    /**
     * @param dTapeAngle angle of tape to frame in degrees while climging
     * returned by getClimbTapeAngle
     */
    protected double dTapeAngle;
    /**
     * @param dMaxDeflec maximum deflection of tape at 0 angle and full
     * extension
     */
    protected double dMaxDeflec = 4;
    /**
     * @param servoDistanceAbovePulleyBottom in inches for side servo
     */
    protected double servoDistanceAbovePulleyBottom = 4.5;
    /**
     * @param servoDistanceBehindPulleyBottom in inches for side servos
     */
    protected double servoDistanceBehindPulleyBottom = 2.5; // Based on the following
    /**
     * Servo travels 360 degrees in 72 seconds 50 degrees per second or 1 degree
     * per minute The angular range is about 270 degrees as servo input goes
     * from 0 to 1 which translates into about 0.0037 servo units per period.
     * This is a maximum velocity of 50 degrees per second, way too fast.
     * Propose slowing it down by a factor of 4, so the incremental adjustment
     * is about .0009. A reasonable error value is 1 degree say about 0.005 PID
     * is possible, if the setpoint is not dynamic, but unnecessary, I think
     *
     * @param maxServoVelocity is maximum servo velocity per above
     */
    private double maxServoVelocity = .0037;
    private double servoError = .005;
    LeftPulley pulley;
    DigitalServo servo;
    Gyro gyro;
    SmartDashboard smartdashboard;

    // Initialize your subsystem here
    /**
     * this is a constructor
     */
    public LeftRod() {
        super("ServoLeft", Kp, Ki, Kd);
        servo = new DigitalServo(RobotMap.P_LEFT_SERVO_CAR, RobotMap.P_LEFT_SERVO_CHAN);
        gyro = new Gyro(RobotMap.GYRO_CHAN);
        // Use these to get going: Will be set in command call
        // Commented out because we won't use PID system'
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    /**
     *
     * @return frame angle in radians
     */
    public double getFrameAngle() {
        double frameangle = gyro.getAngle();
        return Math.toRadians(frameangle);
    }

    /**
     *
     * @param flr true if climbing from floor
     */
    public void adjustAngleClimbing(boolean flr) {
        //get tapelength from Pulley
        boolean floor = flr;
        double currTapeLen = pulley.getTapeLength();
        double rFrameAngle = this.getFrameAngle();  //inradians
        double dTpAn = this.getClimbTapeAngle(floor, currTapeLen, rFrameAngle);
        double rTpAn;
        rTpAn=Math.toRadians(dTpAn);
        double dServVal = this.calcServoFromAngle(false,rTpAn,currTapeLen);
        // Sets the servo by position given the current tape length
        this.setRodServoValue(dServVal);
    }

    /**
     *
     * @param serVeloc a number from zero to 1 representing fraction of maximum
     * servo velocity. Gives valu of incremental change in servo value per cycle
     * @param dTaAn goal angle of tape to frame in degrees
     */
    public void setRodAngleFree(double serVeloc, double dTaAn) {
        //need current frameangle and tapelength for next calculations
        double dFrameAngle = Math.toDegrees(this.getFrameAngle());
        double currTapeLen = pulley.getTapeLength();
        // calculates servo value to achieve target angle at current tape length
        double dServVal = this.calcServoFromAngle(true, dTaAn, currTapeLen);
        // makes a small incremental change to servo value chasing goal angle
        setRodServoVelocity(serVeloc, dServVal, servoError);
    }

    /**
     * This method gets the angle of the tape relative to the frame, while the
     * the robot is climbing. It needs to know:
     *
     * @param floor --boolean Are We climbing from the floor?
     * @param T --double the tapelength, obtained thru a formula based on pot
     * value, reflecting how much the pulley has turned.
     * @param rFrAng -- double The angle of the frame to horizontal in radians
     * retrieved from the Gyro, The calculations are complex, involving a lot of
     * trig and the quadratic formulae, but are derived from the robot geometry
     * and the pyramid geometry. The crucial states are: Are we climbing from
     * floor or from rung? Are we hanging free, or constrained at the back of
     * the robot by the floor or lower rung
     *
     * @return angle of tape to frame in degrees
     */
    public double getClimbTapeAngle(boolean floor, double T, double rFrAng) {
        double dFrAng = Math.toDegrees(rFrAng);
        if (floor) {                      // are we climbing from floor
            if (T < floorbreakfree) {    // The break free condition check
                dTapeAngle = 90 - dFrAng;
                return dTapeAngle;
            } else {                                  //If you haven't broken free
                if (dFrAng <= bumperTouchAngle) {
                    dTapeAngle = Math.toDegrees(MathUtils.asin((k2 - 12.3 * Math.sin(rFrAng)) / T));// Rear wheel touching
                    return dTapeAngle;
                } else {
                    return Math.toDegrees(MathUtils.asin((k2 - 18.9 * Math.sin(rFrAng)) / T));  // rear bumper
                }
            }
        } else {   // Get here if are between rungs... not climbing from floor

            if (T < 13.1 & dFrAng < 60) { // Breaking free angle plus length condition
                dTapeAngle = 90 - dFrAng;
                return dTapeAngle;

            } else if (dFrAng < 92 && dFrAng > 88) {  //Checks for conditions near 90 degrees
                m2 = MathUtils.acos((k3 + k1) / T);
            } else if (dFrAng < 2 && dFrAng > -2) {
                if (T <= Math.sqrt(MathUtils.pow(k2 - k1, 2) + MathUtils.pow(k3 + k1, 2))) {
                    m2 = MathUtils.asin((k2 - k1) / T);
                } else {
                    m2 = MathUtils.acos((k3 + k1) / T);
                }
            } else {    //Ugly quad stuff no need to do unless frame angle not 90 or 0 
                k5 = 1 / Math.tan(rFrAng);
                a = (1 + MathUtils.pow(k5, 2));
                k4 = k1 / Math.sin(rFrAng);
                b = -2 * (k2 + k4 * k5 + k3 * k5);
                c = MathUtils.pow(k2, 2) + MathUtils.pow(k3, 2) + MathUtils.pow(k4, 2) + 2 * (k3 * k4) - MathUtils.pow(T, 2);
                SmartDashboard.putDouble("Quad a", a);
                SmartDashboard.putDouble("Quad b", b);
               SmartDashboard.putDouble("Quad c", c);
                x2 = (-b - Math.sqrt(((b * b) - (4 * a * c)))) / (2 * a);
            }
            dTapeAngle = Math.toDegrees(m2) - dFrAng;
            return dTapeAngle;
        }
    }

    /**
     * This sets the servo value from 0 to 1 based on the desired angle of the
     * tape, using a formula based on robot geometry and empirical measurements,
     * The key ideas are: 1. As the tap elength shortens, the rods are bent into
     * a tighter hoop, and the rods want to bend outward, away from each other.
     * As the tape lengthens the rods bend toward each other. At extreme
     * extension, they would be parallel to the tape. 2. In order not to breakt
     * he rods or burn out the servo, when climbing, because the angle is
     * constrained, the servo needs to be set so its angle is consistent with
     * zero moment, ie zero torque, applied to the endo of the rods.
     * This"relaxed angle" is a function of the distance between the two rod
     * ends-- the servo and the hook tip. 3 The relevant angle is the angle of
     * the rod end at the servo to the axis connecting it to its other end at
     * the hook. 4. This angle is computed from the position of the servo
     * relative to the end of the tape and the angle of the tape relative to the
     * frame. 5. If the tape is hanging free, the servo needs to account for the
     * droop in tape which is at its maximum (about 10 degrees) when the tape is
     * fully extended
     *
     * @param rTaAng tape angle relative to frame is in radians
     * @param T is tape length in inches
     * @param droop true if hanging free, false if climbing
     * @return value of servo to achieve that tape angle at that tape length
     */
    public double calcServoFromAngle(boolean droop, double rTaAng, double T) {
        /**
         * Adjustment for hanging free or freestanding When tape is hanging
         * constrained, the goal is to set the servo to put the tape so it
         * imposes no torque on the Servo. When setting the free standing tape
         * to an angle, it will droop so we have to aim it an a higher angle.
         * This droop is related to the square of the cosine of the angle of
         * tape relative to horizontal and cube of the length of the tape.
         *
         * @param angTapHoriz angle of the Tape relative to horizontal
         * @param adjTargetAnglefactor of Tape angle to account for tape droop
         * It will be a fraction between 0 and 1
         * @param droop if true make the adjustment to target tape angle
         */
        // inititialize angTapHoriz
        double angTapHoriz;
        angTapHoriz = 0;
        if (droop) {

            angTapHoriz = rTaAng + this.getFrameAngle();
        }
        /**
         * @param adjTargetAnglefactor will be between 0 and 1. It is normalized
         * relative to an expected deflection of
         * @param dMaxDeflec degrees at 45 inches
         *
         */
        double adjTargetAnglefactor = (T * T * T * Math.cos(angTapHoriz)
                * Math.cos(angTapHoriz)) / (45 * 45 * 45);
        //
        //Add the adjustment to the target angle
        //
        rTaAng = rTaAng + Math.toRadians(adjTargetAnglefactor * dMaxDeflec);

        /**
         * Trig calculation which relates the distance between the rod ends to:
         * T is tapelength, rTaAng the angle of the tape to the frame, The
         * position of the servo relative to the bottom of the pulley
         *
         * @param servoDistanceAbovePulleyBottom servoDistanceBehindPulleyBottom
         * Apply Pythagorean theorem.
         */
        j2 = Math.sin(rTaAng) * T - servoDistanceAbovePulleyBottom;
        j3 = Math.cos(rTaAng) * T + servoDistanceBehindPulleyBottom;
        /**
         * j4 is distance between rod ends, for a given tapelength and tape
         * angle to the frame, based on relative position of pulley bottom and
         * servo
         */
        j4 = Math.sqrt((j2 * j2) + (j3 * j3));
        //
        /**
         * j5 is the angle of the rod end at the servo,that is, the servo angle,
         * relative to the axis between rod ends as a function of the distance
         * between the rod ends. The rods form a hoop. When the tape is fully
         * extended,say at 45 inches, the angle is about 45 degrees. When tape
         * is fully contracted, j4 approaches zero and this angle is 134
         * degrees.This linear equation, empirically derived, is the relation
         * between distance between the rod ends and the angle formed by the rod
         * end, at the servo, to the axis connecting the rod ends.
         */
        j5 = 134 - 2.046 * j4;
        /**
         * j6 is the angle the rod axis makes to the frame.
         */
        j6 = Math.toDegrees(MathUtils.atan(j2 / j3));
        /**
         * j7 is the angle that the rod end at the servo makes to the frame. It
         * therefore the servo angle to the frame.
         */
        j7 = j5 + j6;
        // The empirically derived linear relationship between servo input value
        // and servo angle relative to the frame
        // Particular to a pulley
        // old double sVal = -.0248 + .0033 * j7;
        // just changed with new servo
        double sVal = +.2665 + .0033 * j7;
        //double right sVal = .9486 - .0036 * j7;
        //double middle sVal = .7442 - .0036 * j7;
        SmartDashboard.putDouble("j2", j2);
        SmartDashboard.putDouble("j3", j3);
        SmartDashboard.putDouble("j4 distance rod ends", j4);
        SmartDashboard.putDouble("j5 servo angle to rod axis", j5);
        SmartDashboard.putDouble("j6 rod axis to frame", j6);
        SmartDashboard.putDouble("j7 servo angle to frame", j7);
        //servo.set(sVal);
        return sVal;
    }

    /**
     * This function reports whether the servo has reached its target value.
     *
     * @param target
     * @return has servo hit target within error of 20% above servoError .005
     * about 1.5 degrees
     */
    public boolean isServoFinished(double target) {
        return (Math.abs(servo.get() - target) < 1.2 * servoError);
    }

    // To DO add other rods
    // servo angle controlled by joystick
    /**
     *
     * @return
     */
    public double getRodServo() {
        return servo.get();
    }

    /**
     *
     * @return
     */
    public double getTapeLength() {
        return pulley.getTapeLength();
    }

    /**
     *
     * @param val
     */
    public void setRodServoValue(double val) {
        servo.set(val);
    }

    /**
     * This resets servo value in small increments which effectively slow it
     * down. It deals with two problems in setting the servos that control the
     * rod angle. 1.Fast jerky servo movements- it slows the servo down 2.The
     * servo goes to its target position immediately and would therefore pass
     * any test I can think of for it being finished. In a parallel that
     * involved the tape length, the rod angle command would be "finished" based
     * on a tape length that was either not final or not current. If we pick the
     * final tape length as the input, the initial angle will be very different
     * from the final and movements will be very jerky. If we pick the current
     * value of the tape length as the input, the outcome will be even worse,
     * since the tape angle command will be finished at a tape length that is
     * not the final target. Meanwhile, the pulley would continue for several
     * more seconds command to its final tape length, This approach is to
     * increment the servo value by a small amount each cycle, chasing the
     * current tape length. The Servo travels 360 degrees in 72 seconds 50
     * degrees per second or 1 degree per minute The angular range is about 270
     * degrees as servo inpute goes from 0 to 1 which translates into about
     * 0.0037 servo units per period. This is ath maximum velocity of 50 degrees
     * per second, way too fast. Propose slowing it down by a factor of 4, so
     * the incremental adjustment is about .0009. A reasonable error value is
     * 1.5 degree say about 0.005 PID is possible, if the setpoint is not
     * dynamic, but unnecessary, I think
     *
     * @param serVel
     * @param goalVal
     * @param error
     * @return new value for the rod servo that is a small increment to its
     * previous value
     */
    public void setRodServoVelocity(double serVel, double goalVal, double error) {
        double curVal = servo.get();
        double increVal = serVel * maxServoVelocity;
        // if we are geeting so close to the target value that the increment
        // exceeds the error, cut the increment in half.
        if (increVal > error) 
            increVal = .5 * error;
       if (serVel > 1) {
               serVel = 1.;
         }
        if (serVel < 0.0) {
            serVel = 0.0;
        }
        if (Math.abs(curVal - goalVal) < error) {
            servo.set(goalVal);
        return;
        }  if ((goalVal > curVal)
                & ((Math.abs(goalVal - (curVal + increVal))) > error)) {
            servo.set(curVal + increVal);
            return;
        }  if (Math.abs(goalVal - (curVal + increVal)) <= error) {
           servo.set(goalVal);
           return;
        }  
        if ((goalVal < curVal) & (Math.abs(goalVal - (curVal - increVal)) > error)) {
            servo.set(curVal - increVal);
            return;
        }
        if (Math.abs(goalVal - (curVal - increVal)) <= error) {
            servo.set(goalVal);
            return;
        }
        } 
    
    

    // set default joystick
    public void initDefaultCommand() {
        //Use joystick
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
