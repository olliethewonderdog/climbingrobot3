 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.FrameMath;

/**
 *
 * @author Brinton
 */
public abstract class Rod extends Subsystem {

     /** @param dTapeAngle angle of tape to frame in degrees while climbing
     * returned by getClimbTapeAngle
     */
    protected double dTapeAngle;
    
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
    protected double maxServoVelocity;
    protected double servoError;
    protected double servoErrorMulitplier;
    int pulleyNumber;
    Servo servo;
    SmartDashboard smartdashboard;

    // Initialize your subsystem here
    /**
     * this is a constructor
     */
    public Rod(String name) { 
        super(name);
        servo.setBounds(244,0,0,0,11);
        maxServoVelocity = .0037;
        servoError = .005;
        servoErrorMulitplier=1.2;
    }
    /**
     *
     * @param flr true if climbing from floor
     */
    public void adjustAngleClimbing(boolean flr) {
        //get tapelength from Pulley
        boolean floor = flr;
        double currTapeLen = this.getTapeLength();
        double dTpAn = FrameMath.getClimbTapeAngle(floor, currTapeLen, pulleyNumber);
        double rTpAn=Math.toRadians(dTpAn);
        double dServVal =FrameMath.calcServoFromAngle(false,rTpAn,currTapeLen,
                pulleyNumber);
        // Sets the servo by position given the current tape length
        this.setRodServoValue(dServVal);
    }
    /**
     *
     * @param serVeloc a number from zero to 1 representing fraction of maximum
     * servo velocity. Gives value of incremental change in servo value per cycle
     * @param dTaAn goal angle of tape to frame in degrees
     */
    public void setRodAngleFree(double serVeloc, double dTaAn) {
        //need current frameangle and tapelength for next calculations
        double currTapeLen =this.getTapeLength();
        // calculates servo value to achieve target angle at current tape length
        double dServVal = FrameMath.calcServoFromAngle(true, dTaAn,
                currTapeLen,pulleyNumber);
        // makes a small incremental change to servo value chasing goal angle
        setRodServoVelocity(serVeloc, dServVal, servoError);
    }
  /**
     * This function reports whether the servo has reached its target value.
     *
     * @param target
     * @return has servo hit target within error of 20% above servoError .005
     * about 1.5 degrees
     */
    public boolean isServoFinished(double target) {
        return (Math.abs(servo.get() - target) < servoErrorMulitplier * servoError);
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
    public abstract double getTapeLength() ;
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
     * degrees as servo input goes from 0 to 1 which translates into about
     * 0.0037 servo units per period. This is a maximum velocity of 50 degrees
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
         if (serVel > 1) 
         {
            serVel = 1.;
         }
         if (serVel < 0.0) 
         {
            serVel = 0.0;
         }
         double increVal = serVel * maxServoVelocity;
        // if we are geeting so close to the target value that the increment
        // exceeds the error, cut the increment in half.
         if (increVal > error) 
         {
            increVal = .5 * error;
         }
      
         if (Math.abs(curVal - goalVal) < error) 
         {
            servo.set(goalVal);
            return;
         }  
         if ((goalVal > curVal)
                & ((Math.abs(goalVal - (curVal + increVal))) > error)) 
         {
            servo.set(curVal + increVal);
            return;
         }  
         if (Math.abs(goalVal - (curVal + increVal)) <= error) 
         {
           servo.set(goalVal);
           return;
         }  
         if ((goalVal < curVal) & (Math.abs(goalVal - (curVal - increVal)) > error))
         {
            servo.set(curVal - increVal);
            return;
         }
         if (Math.abs(goalVal - (curVal - increVal)) <= error)
         {
            servo.set(goalVal);
         }
    } 
 
    // set default joystick
    public void initDefaultCommand() {
    }

 
}
