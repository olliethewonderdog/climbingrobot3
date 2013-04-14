/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.RightRodDoNothing;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.SI;

/**
 *
 * @author Brinton
 */
public class RightRod extends Rod {
    
    // Initialize your subsystem here
    /**
     * this is a constructor
     */
    public RightRod() {
        super("RightRod");
        servo = new Servo(RobotMap.P_RIGHT_SERVO_CAR,
                RobotMap.P_RIGHT_SERVO_CHAN);
        pulleyNumber=2;
    }
    public double getTapeLength() {
        double v =SI.getRight();
        double length= FrameMath.potParam [pulleyNumber][0]*v+
                FrameMath.potParam [pulleyNumber][1];
        return length; 
    }

    // set default joystick
    public void initDefaultCommand() {
        //Use joystick
        // Set the default command for a subsystem here.
        setDefaultCommand(new RightRodDoNothing());
    }

 
}
