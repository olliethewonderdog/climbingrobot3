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
public class LeftPulleySetLength extends CommandBase {
    private float tapeLength;
    private float error;
    private float speed;
    private int  extending;
    private float curLength;
    private boolean setLock;
    private float rspeed;
    //
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
   public LeftPulleySetLength(float T, float e,float s) {
        requires(leftpulley);                                
           tapeLength = T;
        speed=Math.abs(s);
        // error to target is always positive
        error=Math.abs(e);
        //rspeed may be reduced;
        rspeed=speed;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        tapeLength=Math.max(Math.min(tapeLength,leftpulley.tapeLenMax),leftpulley.tapeLenMin);
        curLength = leftpulley.getTapeLength();
        
        /* if there is an attempt to extend, unlock it.
         * We don't trust the state. We just unlock it.
         * We don't trust the sign of the speed.
         * The sign of speed is reset to match goal vs current length
        */
        extending =1;  
        if (curLength-tapeLength>0)  extending=-1;
        SmartDashboard.putNumber("LeftPulleySetLength"
                + " direct", leftpulley.direct);
       SmartDashboard.putNumber("LeftPulleySetLength"
                + " extending", extending);
        if (extending > 0) 
        setLock = leftpulley.setLock(false);
        // start it at rspeed
        SmartDashboard.putString("LeftPulleySetLength"
                , " initialize setting tape velocity") ;
       leftpulley.setTape(extending * rspeed);
       
    }
    // Called repeatedly when this Command is scheduled to run
    
    protected void execute() {
         //SmartDashboard.putNumber("LeftPulleySetLength"
               // + " Voltage", SI.getLeft());
         SmartDashboard.putNumber("LeftPulleySetLength"
                + " tapelength", leftpulley.getTapeLength());
         curLength = leftpulley.getTapeLength();
         SmartDashboard.putNumber("LeftPulleySetLength"
                + " curerror", curLength - tapeLength);
         SmartDashboard.putNumber("LeftPulleySetLength"
                + " speed", speed);
         SmartDashboard.putNumber("LeftPulleySetLength"
                + " rspeed",rspeed);
         SmartDashboard.putNumber("LeftPulleySetLength"
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
         leftpulley.setTape(extending * rspeed );
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
        curLength = leftpulley.getTapeLength();
        SmartDashboard.putNumber("LeftPulleySetLength"
                + "isFinished curLength ", curLength);
        // if I overshoot retracting, the current error will be negative,ie
        // plus times minus < 0
        // minus times plus < 0
        float curError= -extending * (curLength - tapeLength);
        
       if (curError < 0)
       {SmartDashboard.putNumber("LeftPulleySetLength"
                + "missed by ", curError);
       }
        // if I have exceeded min or max, stop
       SmartDashboard.putBoolean("LeftPulleySetLength"
                + "isfinished out of bounds state ", 
                (curLength>=leftpulley.tapeLenMax || curLength<=leftpulley.tapeLenMin));
        if (curLength>=leftpulley.tapeLenMax || curLength<=leftpulley.tapeLenMin) 
            return true;
        // This condtion handles overshooting the target.
        // if I overshoot retracting, the current error will be negative,ie
        // plus times minus < a postive error,
        // and I stop
        // if I overshoot extending, the current error will be negative,
        // minus times plus < a postive error,
        // and I also stop
        SmartDashboard.putBoolean("LeftPulleySetLength"
                + "isfinished return state ", (curError) < error);
         return (curError < error);
    }
     protected void end() {
        leftpulley.setTape(0);
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        SmartDashboard.putString("LeftPulleySetLength"
                , "interrupted ");
         leftpulley.setTape(0);
    }
}