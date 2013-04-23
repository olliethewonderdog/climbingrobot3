 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.LeftRodDoNothing;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.SI;
/**
 *
 * @author Brinton
 */
public class LeftRod extends Rod{
    /**
     * this is a constructor
     */
    public LeftRod() {
        super("LeftRod");
        servo = new Servo(RobotMap.P_LEFT_SERVO_CAR,
                RobotMap.P_LEFT_SERVO_CHAN);
        pulleyNumber=1;
        servo.setBounds(244,0,0,0,11);
    }
    public float getTapeLength() {
        float v =(float)SI.getLeft();
        float length= FrameMath.potParam [pulleyNumber][0]*v+
                FrameMath.potParam [pulleyNumber][1];
        return length; 
    }
    // set default joystick
    public void initDefaultCommand() {
        //Use joystick
        // Set the default command for a subsystem here.
        setDefaultCommand(new LeftRodDoNothing());
    }
}
