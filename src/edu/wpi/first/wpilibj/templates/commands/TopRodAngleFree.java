/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.SI;
/**
 *
 * @author Brinton
 *
 *
 */
public class TopRodAngleFree extends CommandBase {
    private double goalTapeLength;
    private double error;
    private double dTapeAngToFloor;
       private int pulley;
    private double dTapeAngToFrame;
    private double servoSpeed; 
    public TopRodAngleFree(double dTAng, double T) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(toprod);
         goalTapeLength = T;
        // angle of the tape relative to the frame in degrees
        dTapeAngToFloor = dTAng;
        error=.5;
        pulley=0;
        servoSpeed=1;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
         SmartDashboard.putNumber("top RodAnglefree"
                + " Frame Angle", SI.getdFrameAngle());
    }

    /**
     * Called repeatedly when this Command is scheduled to run This moves the
     * servo slowly toward a given target angle. The servo speed is reduced to
     * .33 of its maximum, about 50 degrees per second This has two advantages.
     * It does not jerk the rods around. It causes the tape and rod movements to
     * occur at similar rates. The risk with allowing the rod to move quickly to
     * position while the tape is moving slowly is that the servo may think it
     * is finished while the tape is still moving
     */
    protected void execute() {
        // 
         SmartDashboard.putNumber("top RodAnglefree"
                + " dTapeAngToFrame",dTapeAngToFrame);
         
            dTapeAngToFrame=-SI.getdFrameAngle()+dTapeAngToFloor;

        SmartDashboard.putNumber("top RodAnglefree"
                + " Top Tape Length", toprod.getTapeLength());
        
        SmartDashboard.putNumber("top RodAnglefree"
                + " Frame Angle", SI.getdFrameAngle());
        
        SmartDashboard.putNumber("top RodAnglefree Rod Servo",
                FrameMath.calcServoFromAngle(
                true, Math.toRadians(dTapeAngToFrame),toprod.getTapeLength(),
                pulley)
                );
        
       toprod.setRodAngleFree(servoSpeed, dTapeAngToFrame);
    }

    // Make this return true when this Command no longer needs to run execute()
    // tapelength has reached goal and also
    // servo has reached goal
    protected boolean isFinished() {
        //calulates final servo position for 
        double t;
        t = toprod.getTapeLength();
        if (Math.abs(t - goalTapeLength) > error)
        {
            return false;
        }
         else
         {  
         double servValfinal =
         FrameMath.calcServoFromAngle(true, Math.toRadians(dTapeAngToFrame),
                 goalTapeLength,pulley);
         
         boolean servDone=toprod.isServoFinished(servValfinal);
         SmartDashboard.putBoolean("top RodAnglefree"
                + " servDone", servDone);
         return servDone;
         }
    }
    
    // Called once after isFinished returns true
    
    protected void end() {
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}