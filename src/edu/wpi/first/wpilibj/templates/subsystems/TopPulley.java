/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.commands.TopPulleyDoNothing;
/**
 *
 * 
 */
public class TopPulley extends Pulley {
       private static final double Kp = 0.0;
        private static final double Ki = 0.0;
        private static final double Kd = 0.0;  
    // Initialize your subsystem here
    public TopPulley() {
       super("toppulley",Kp,Ki,Kd);
       name="top";
      /**
      * Length of hook is added to tape true for all hooks
      */
        hooklen = 4.5;
      // Maximum, minimum,and starting  tapelengths are particular to each pulley
       tapeLenMax = 47;
       tapeLenMin = 5.0;
       initialTapeLen = 20;
       /**
       * Motor direction is +1 or -1 depending on its orientation in the robot
       */
       direct = RobotMap.P_TOP_MOTOR_DIR;
       pulleyMotor = new Jaguar(RobotMap.P_TOP_JAG_CAR, RobotMap.P_TOP_JAG_CHAN);
       pulleyNumber=0;
    }
    public double getTapeLength() {
        double v =SI.getTop();
        double length= FrameMath.potParam [pulleyNumber][0]*v+ FrameMath.potParam [pulleyNumber][1]
                +hooklen;
        return length; 
    } 
    //No pawl reference in this toppulley version.
      public void setTape(double velocity) {
        // speed is positive to extend,negative to retract
        // sign of velocity is adjusted 
        // by "direct" (+ or - 1)
        // for motor orientation
        // 
        pulleyMotor.set(velocity * direct);
    }
       /**
     * There is no pawl check in toppulley version.
     * This code is deprecated because we now allow the tape goal to be managed
     * in the command.
     * Simply extends or retracts tape at velocity until its
     * length is within "error" of "goalLength"
     *
     * @param goalLength is target length
     * @param error is positive number that sets how close you want to target
     * @param longer specifies direction and is positive if you are extending It
     * is 1 if you're extending and -1 if you are retracting We are trying to
     * avoid using the PID with this simple a
     */
    public void setTapeLength(double goalLength, double error,double velocity) {
        // Velocity is supposed to be positive if extending negative if retractin
        // but we just use the absolute value and the goal Length to determine
        // direction.
        // Check for out of bounds tapelength goals
        goalLength=Math.max(Math.min(goalLength,tapeLenMax),tapeLenMin);
        double curLength = getTapeLength();
        /* if there is an attempt to extend a locked pulley, Don't do it. stop motor
        * and return
        * Change outcome to unlock and extend?
        */
      double extending=1;  
        if (curLength-goalLength>0) extending=-1;
        if (error > pulleyErrorMax || error < pulleyErrorMin)
        { //check for bad error value
            error = pulleyErrorMin;
        }
        // "extending" is either +1 or -1
        // If longer is positive, the current length will be <  the goal length
        // If longer is negative, the current length will be > the goal length
        // In either case above  the product is positive. If it overshoots 
        // the goal, the product will go negative and the motor will stop.
        // Will it overshoot? It might . The side pulleys turn at about 
        // 74 rpm or 7 to 8 inches per second no load, tho about half that
        // under load so say 3.5 to 8 inches per second or about .07 to .17
        // inches per cycle. The error is .1, Currently the velocity is pegged 
        // to 50% so the range per cycle is .035 to .085
        // Just in case I'll add a slow down whe we get within an inch
        //
        if ((-extending * (curLength - goalLength)) < 1) 
        {
           velocity = .8* Math.abs(velocity);
        }
        if ((-extending * (curLength - goalLength)) > error)
        {
            // sign of velocity is adjusted 
            // by extending for 
            // by "direct" (+ or - 1)
            // for motor orientation
            pulleyMotor.set(extending * velocity * direct);
        } 
        else 
        {
            pulleyMotor.set(0);
        }
    }
    public void initDefaultCommand() {
         setDefaultCommand(new TopPulleyDoNothing()); 
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