/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.commands.LeftPulleyDoNothing;

/**
 *
 * @author laptop
 */
public class LeftPulley extends Pulley {
       private static final float Kp = (float) 0.0;
        private static final float Ki = (float)0.0;
        private static final float Kd = (float)0.0;  
    public LeftPulley() {
       super("leftpulley",Kp,Ki,Kd);
       name="left";
      /**
      * Length of hook is added to tape true for all hooks
      */
      // Maximum, minimum,and starting  tapelengths are particular to each pulley
       tapeLenMax = 38;
       tapeLenMin = 5.0f;
       initialTapeLen = 20;
       /**
       * Motor direction is +1 or -1 depending on its orientation in the robot
       */
       direct = RobotMap.P_LEFT_MOTOR_DIR;
          // The PWN values for the open and locked postions of the pawl
        // May be able to set these in the servos themselves
        pawlLock = (float)RobotMap.P_LEFT_LOCK_LOCKED;
        pawlOpen =(float) RobotMap.P_LEFT_LOCK_OPEN;
        pulleyMotor = new Jaguar(RobotMap.P_LEFT_JAG_CAR, RobotMap.P_LEFT_JAG_CHAN);
        LiveWindow.addActuator("leftPulley","pulley speed",pulleyMotor);
        pulleyNumber=1;
        pawl = new Servo(RobotMap.P_LEFT_LOCK_CAR, RobotMap.P_LEFT_LOCK_CHAN);
         LiveWindow.addActuator("leftPulley","pawl servo value",pawl);
    
        pawl.setBounds(244, 0, 0, 0, 11);
        //set pawl unlocked
        pawl.set(pawlOpen);
        pawlLocked = false;
    }
     public float getTapeLength() {
        float v =(float)SI.getLeft();
        float length= FrameMath.potParam [pulleyNumber][0]*v+ 
                FrameMath.potParam [pulleyNumber][1]
                +hooklen;
        return length; 
    } 
    public void initDefaultCommand() {
         setDefaultCommand(new LeftPulleyDoNothing()); 
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