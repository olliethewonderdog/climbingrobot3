package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.commands.TopRodDoNothing;
/**
 *
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
    }
    /**
     *
     * @return
     */
    public double getTapeLength() {
        double v =SI.getTop();
        double length= FrameMath.potParam [pulleyNumber][0]*v+
                FrameMath.potParam [pulleyNumber][1];
        return length; 
    }
    
    // set default joystick
    public void initDefaultCommand() {
        //Use joystick
        // Set the default command for a subsystem here.
        setDefaultCommand(new TopRodDoNothing());
    }

 
}

