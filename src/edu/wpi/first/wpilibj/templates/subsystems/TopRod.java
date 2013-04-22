package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.commands.TopRodDoNothing;
/**
 * Most of the methods used are in the parent class.
 * @author Brinton
 */
public class TopRod extends Rod {
    /**
     * this is a constructor
     */
    public TopRod() {
        super("topRod");
        servo = new Servo(RobotMap.P_TOP_SERVO_CAR,
                RobotMap.P_TOP_SERVO_CHAN); 
        pulleyNumber=0;
        servo.setBounds(244,0,0,0,11);
    }
    /**
     * Gets potentiometer voltage from SI and applies the calibrated parameters.
     */ 
    public double getTapeLength() {
        double v=SI.getTop();
        double length=FrameMath.potParam [pulleyNumber][0]*v+
                FrameMath.potParam [pulleyNumber][1];
        return length; 
    }
    public void initDefaultCommand() {
        setDefaultCommand(new TopRodDoNothing());
    }
}

