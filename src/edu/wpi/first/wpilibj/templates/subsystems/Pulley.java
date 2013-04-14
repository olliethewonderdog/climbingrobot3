package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.OI;
/**
 * We don't use the PID functionality at this time.
 *
 */
public abstract class Pulley extends PIDSubsystem {
    protected String name;
    /**
     * Length of hook is added to tape true for all hooks
     */
    protected double hooklen;
    // Maximum, minimum,and starting  tapelengths are particular to each pulley
    protected double tapeLenMax;
    protected  double tapeLenMin ;
    protected double initialTapeLen ;
    /**
     * Motor direction is +1 or -1 depending on its orientation in the robot
     */
    protected int direct ;
    // The PWM values for the open and locked postions of the pawl
    // May be able to set these in the servos themselves
    protected double pawlLock  ;
    protected double pawlOpen ;
    // Move the oulley at half velocity for now (Values from 0 to 1)
    // Error limits for the setTapelength method
    protected int pulleyErrorMax;
    protected  double pulleyErrorMin ;
    protected double pulleyErrorDef;
     
    /**
     * State of pawl lock
     */
    public boolean pawlLocked;
    Jaguar pulleyMotor;
    Servo pawl;
    Timer timer;
    OI oi;
    
    /**
     * Initialize the subsystem here
     */
    public Pulley(String name, double p, double i, double d) {
        super(name, p, i, d);  
        hooklen=4.5;
        pulleyErrorMax=1;
        pulleyErrorMin=.1 ;
        pulleyErrorDef=.15;
    }
         //Set the starting setpoint and have PID start running in the background
        // These are  commented out because it starts in starts in joystick mode.
        // when the command to 
        // These will  set in the command "execute" method
        // setSetpoint(initialTapeLen);                          //
        //enable(); // - Enables the PID controller.
    /**
     * Gets tape length in inches as a function of voltage
     *
     * @return tape length in inches
     * 
     * 
     */
    public abstract double getTapeLength() ;
    
    
      public void setTape(double velocity) {
        // speed is positive to extend,negative to retract
        // if there is an attempt to extend a locked pulley, Don't do it. stop motor
        // and return
        // Change outcome to unlock and extend?
        
        if (pawlLocked == true & velocity>0) {
            pulleyMotor.set(0);
            return;
        }
        // sign of velocity is adjusted 
        // by "direct" (+ or - 1)
        // for motor orientation
        // 
        pulleyMotor.set(velocity * direct);
    }
        public void stopPulley (){
     pulleyMotor.set(0);
      } 
    /**
     *This code is deprecated because we now allow the tape goal to be managed
     * in the command.
     * Simply extends or retracts tape at velocity until its
     * length is within "error" of "goalLength"
     *
     * @param goalLength is target length
     * @param error is positive number that sets how close you want to target
     * @param longer specifies direction and is positive if you are extending It
     * is 1 if you're extending and -1 if you are retracting We are trying to
     * avoid using the PID with this simple a
     */
    public void setTapeLength(double goalLength, double error,double velocity) {
        //Velocity is supposed to be positive if extending negative if retractin
        // but we just use the absolute value and the goal Length to determine
        //direction.
        // Check for out of bounds tapelength goals
         goalLength=Math.max(Math.min(goalLength,tapeLenMax),tapeLenMin);
        double curLength = getTapeLength();
        /* if there is an attempt to extend a locked pulley, Don't do it. stop motor
        * and return
        * Change outcome to unlock and extend?
        */
      double extending=1;  
        if (curLength-goalLength>0) extending=-1;
        if (pawlLocked = true & (extending > 0)) {
            pulleyMotor.set(0);
            return;
        }
      
        if (error > pulleyErrorMax || error < pulleyErrorMin) { //check for bad error value
            error = pulleyErrorMin;
        }
        // "extending" is either +1 or -1
        // If longer is positive, the current length will be <  the goal length
        // If longer is negative, the current length will be > the goal length
        // In either case above  the product is positive. If it overshoots 
        // the goal, the product will go negative and the motor will stop.
        // Will it overshoot? It might . The side pulleys turn at about 
        // 74 rpm or 7 to 8 inches per second no load, tho about half that
        // under load so say 3.5 to 8 inches per second or about .07 to .17
        // inches per cycle. The error is .1, Currently the velocity is pegged 
        // to 50% so the range per cycle is .035 to .085
        // Just in case I'll add a slow down whe we get within an inch
        //
        if ((-extending * (curLength - goalLength)) < 1) {
           velocity = .8* Math.abs(velocity);
        }
        if ((-extending * (curLength - goalLength)) > error) {
            // sign of velocity is adjusted 
            // by extending for 
            // by "direct" (+ or - 1)
            // for motor orientation
            pulleyMotor.set(extending * velocity * direct);

        } else {
            pulleyMotor.set(0);
        }
    }
    /**
     * Locks or unlocks the pawl depending setting of "locked"
     *
     * @param locked
     *
     * @return true if locked, false if open
     */
    public boolean setLock(boolean locked) {
        if (locked) { 
            pawl.set(pawlLock);
            // publish the state. Used in setTapeLength ()
            pawlLocked = true;
            return true;
        } 
        /** If trying to open  a locked tape, ie locked is false,
         * the pawl will not release until the force on it is relaxed and the 
         * pawl is not blocked by the gear tooth, so that it is free to lift.
         * Therefore this routine moves the tape slowly, several times, for very 
         * brief periods in an attempt to let the pawl find a spot relative to
         * the tooth that permits it to lift.
         * The top speed of the side pulleys, no load is 
         * 74 rpm = 1.2 revolutions/second
         * which is about 7.5 inches per second
         * 104 teeth per revolution= 125 teeth per second.
         * slow down by a factor of, say 7
         * about 1 inch per second
         * or 15 teeth per second.
         * our goal is about .5 teeth per try, means  about
         * 1/30 th of a second.
         * .033 seconds of travel.
         * Issue: is that enough time for the motor to respond?
         * So try a little more, say .05
         * The middle tape is about half as fast.
         * We then attempt to extend the tape, slowly, and test whether its 
         * length increases
         * Need to tune these parameters.
         */
        else
        {
            pawl.set(pawlOpen);
           double startlength=this.getTapeLength();
           // Do this loop until the tape has extended .5 inches
           // surrently stays in this loop until 
           while (this.getTapeLength()-.5 < startlength)
           {
            for(int i=1; i<7; i++)
            {
                this.setTape(-.15);
              Timer.delay(.05);
              this.setTape(0);
             }
            startlength=this.getTapeLength();
            // Try to extend the tape
            this.setTape(.15);
            //should move more than half an inch in 1 second
            Timer.delay(1);
            this.setTape(0);
           } 
            pawlLocked = false;
        }
        // Publish the state      
        return false;
    }
    
    public void initDefaultCommand() {
            
    }

    /**
     * @return if using PID system,The value of the rangefinder used as the PID
     * input device.
     */
    protected double returnPIDInput() {
        return this.getTapeLength();
    }

    /**
     * @param output The value to set the output to as determined by the PID
     * algorithm. This gets called each time through the PID loop to update the
     * output to the motor.
     */
    
    protected void usePIDOutput(double output) {
        pulleyMotor.set(output);

    }
}
