package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.commands.RightPulleyDoNothing;

/**
 *
 * @author laptop
 */
public class RightPulley extends Pulley {
       private static final double Kp = 0.0;
        private static final double Ki = 0.0;
        private static final double Kd = 0.0;  

    // Initialize your subsystem here
    public RightPulley() {
       super("rightpulley",Kp,Ki,Kd);
       name="left";
      // Maximum, minimum,and starting  tapelengths are particular to each pulley
       tapeLenMax = 42;
       tapeLenMin = 5.0;
       initialTapeLen = 20;
       /**
       * Motor direction is +1 or -1 depending on its orientation in the robot
       */
       direct = RobotMap.P_RIGHT_MOTOR_DIR;
          // The PWN values for the open and locked postions of the pawl
        // May be able to set these in the servos themselves
        pawlLock = RobotMap.P_RIGHT_LOCK_LOCKED;
        pawlOpen = RobotMap.P_RIGHT_LOCK_OPEN;
        pulleyMotor = new Jaguar(RobotMap.P_RIGHT_JAG_CAR, RobotMap.P_RIGHT_JAG_CHAN);
        pawl = new Servo(RobotMap.P_RIGHT_LOCK_CAR, RobotMap.P_RIGHT_LOCK_CHAN);
        pawl.setBounds(244, 0, 0, 0, 11);
        //set pawl unlocked
        pawl.set(pawlOpen);
        pawlLocked = false;
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
     public double getTapeLength() {
        double v =SI.getRight();
        double length= FrameMath.potParam [2][0]*v+ FrameMath.potParam [2][1]
                +hooklen;
        return length; 
    } 
    
    public void initDefaultCommand() {
         setDefaultCommand(new RightPulleyDoNothing()); 
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