/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
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
     * It holds the state of a couple of key control commands
     * These two variables allow operator control during autonomous climbing.
     * They signal when the middle pulley can stop stalling. and allow the 
     * command groups to pause at intervals to wait for the "nextCommand"
     * Arguably these last two should go in OI
     */
   
   protected static AnalogChannel top;
   protected static AnalogChannel left;
   protected static AnalogChannel right;
   protected static Gyro gyro;
    

    public SI() {
        top = new AnalogChannel(RobotMap.TOP_PULLEY_CHAN);
        left = new AnalogChannel(RobotMap.LEFT_PULLEY_CHAN);
        right = new AnalogChannel(RobotMap.RIGHT_PULLEY_CHAN);
        gyro = new Gyro (RobotMap.GYRO_CHAN);
       
    }
    public static double getTop () {
        double v;
                v=top.getVoltage();
           return v;
}
     public static double getLeft () {
        double v;
                v=left.getVoltage();
           return v;
}
      public static double getRight () {
        double v;
                v=right.getVoltage();
           return v;
}
   // Returns the angle of the frame in radians, because thats what we need them in most the time
    
    public static double getrFrameAngle() {
        return Math.toRadians(gyro.getAngle());
    }
    public static double getdFrameAngle() {
        return gyro.getAngle();

    }
    public static void reset() {
        gyro.reset();
    }
}
