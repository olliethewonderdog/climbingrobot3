/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
/**
 *
 * @author laptop
 */
public class SI {
    /**
     * This class serves a couple of functions.
     * 
     * It maps the potentiometers and gyros to their physical ports.
     * It contains functions which read the gyros and potentiometers.
     * It is never instantiated. Its variables and methods are static so they
     * can be called in static contexts
     */ 
   protected static AnalogChannel top;
   protected static AnalogChannel left;
   protected static AnalogChannel right;
   protected static Gyro gyro;
    public SI() {
        top = new AnalogChannel(RobotMap.TOP_PULLEY_CHAN);
         LiveWindow.addSensor("TopPulley", "pot voltage", top);
        left = new AnalogChannel(RobotMap.LEFT_PULLEY_CHAN);
         LiveWindow.addSensor("LeftPulley", "pot voltage", left);
        right = new AnalogChannel(RobotMap.RIGHT_PULLEY_CHAN);
         LiveWindow.addSensor("RightPulley", "pot voltage", right);
        gyro = new Gyro (RobotMap.GYRO_CHAN);   
        LiveWindow.addSensor("", "gyro", gyro);
    }
    public static float getTop () {
        float v;
                v=(float)top.getVoltage();
           return v;
}
     public static float getLeft () {
        float v;
                v=(float)left.getVoltage();
           return v;
}
      public static float getRight () {
        float v;
                v=(float)right.getVoltage();
           return v;
}
   // Returns the angle of the frame in radians, because thats what we need them in most the time
    
    public static float getrFrameAngle() {
        return (float)Math.toRadians(gyro.getAngle());
    }
    public static float getdFrameAngle() {
        return (float)gyro.getAngle();
    }
    public static void reset() {
        gyro.reset();
    }
}
