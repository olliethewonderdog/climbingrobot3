/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
        LiveWindow.addActuator("rightRod","servo Value",servo);
        pulleyNumber=2;
        servo.setBounds(244,0,0,0,11);
    }
    public float getTapeLength() {
        float v =(float)SI.getRight();
        float length= FrameMath.potParam [pulleyNumber][0]*v+
                FrameMath.potParam [pulleyNumber][1];
        return length; 
    }
    public void initDefaultCommand() {
        setDefaultCommand(new RightRodDoNothing());
    }
}
