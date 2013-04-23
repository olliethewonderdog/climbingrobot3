/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.SI;
/**
 *
 * @author Brinton
 */
public class RightPulleySetLength extends CommandBase {
    private float tapeLength;
    private float error;
    private float speed;
    private int    extending;
    private float curLength;
    private boolean setLock;
    private float rspeed;
    private float curError;
    //
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
   public RightPulleySetLength(float T, float e,float s) {
        requires(rightpulley);                                
          tapeLength = T;
        speed=Math.abs(s);
        // error to target is always positive
        error=Math.abs(e);
        //rspeed may be reduced;
        rspeed=speed;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        tapeLength=Math.max(Math.min(tapeLength,rightpulley.tapeLenMax),rightpulley.tapeLenMin);
        curLength = rightpulley.getTapeLength();
        
        /* if there is an attempt to extend, unlock it.
         * We don't trust the state. We just unlock it.
         * We don't trust the sign of the speed.
         * The sign of speed is reset to match goal vs current length
        */
        extending =1;  
        if (curLength-tapeLength>0)  extending=-1;
        SmartDashboard.putNumber("RightPulleySetLength"
                + " direct", rightpulley.direct);
       SmartDashboard.putNumber("RightPulleySetLength"
                + " extending", extending);
        if (extending > 0) 
        setLock = rightpulley.setLock(false);
        // start it at rspeed
        SmartDashboard.putString("RightPulleySetLength"
                , " initialize setting tape velocity") ;
       rightpulley.setTape(extending * rspeed);
       
    }
    // Called repeatedly when this Command is scheduled to run
    
    protected void execute() {
         //SmartDashboard.putNumber("RightPulleySetLength"
               // + " Voltage", SI.getRight());
         SmartDashboard.putNumber("RightPulleySetLength"
                + " tapelength", rightpulley.getTapeLength());
         curLength = rightpulley.getTapeLength();
         SmartDashboard.putNumber("RightPulleySetLength"
                + " curerror", curLength - tapeLength);
         SmartDashboard.putNumber("RightPulleySetLength"
                + " speed", speed);
         SmartDashboard.putNumber("RightPulleySetLength"
                + " rspeed",rspeed);
         SmartDashboard.putNumber("RightPulleySetLength"
                + " setspeed",extending * rspeed );
         //
         // you get a negative error
        if ((-extending * (curLength - tapeLength)) < 1)
        {
           rspeed = .8f*speed;
        }
        if ((-extending * (curLength - tapeLength)) < .5)
        {
           rspeed = .5f*speed;
        }
         rightpulley.setTape(extending * rspeed );
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
        curLength = rightpulley.getTapeLength();
        SmartDashboard.putNumber("RightPulleySetLength"
                + "isFinished curLength ", curLength);
        // if I overshoot retracting, the current error will be negative,ie
        // plus times minus < 0
        // minus times plus < 0
        curError= -extending * (curLength - tapeLength);
        
       if (curError < 0)
       {SmartDashboard.putNumber("RightPulleySetLength"
                + "missed by ", curError);
       }
        // if I have exceeded min or max, stop
       SmartDashboard.putBoolean("RightPulleySetLength"
                + "isfinished out of bounds state ", 
                (curLength>=rightpulley.tapeLenMax || curLength<=rightpulley.tapeLenMin));
        if (curLength>=rightpulley.tapeLenMax || curLength<=rightpulley.tapeLenMin) 
            return true;
        // This condtion handles overshooting the target.
        // if I overshoot retracting, the current error will be negative,ie
        // plus times minus < a postive error,
        // and I stop
        // if I overshoot extending, the current error will be negative,
        // minus times plus < a postive error,
        // and I also stop
        SmartDashboard.putBoolean("RightPulleySetLength"
                + "is finished return state ", (curError) < error);
         return (curError < error);
    }
     protected void end() {
        rightpulley.setTape(0);
        SmartDashboard.putBoolean("RightPulleySetLength"
                + "is finished return state ", (curError) < error);
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        SmartDashboard.putString("RightPulleySetLength"
                , "interrupted ");
         rightpulley.setTape(0);
    }
}