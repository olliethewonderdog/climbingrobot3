/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Has math functions
 * Has parameters for 
 *  pyramid geometry
 *  robot geometry
 *  linear equations for pots and servos
 * @author laptop
 */
public final class FrameMath {
    // droop =4inches at 45inches
    //=.07*57=4
    //leftservomax .453 back 181   delta 135
    //leftservommin  0  forward 46
    //rightservomax .744 forward 57 46  delta 100
    //rightservomin .385 back 157
    //midservomax  .78    -10   180 forward//.751= -10 degrees delta 190
    //midservomin  .1  back 18o
     public static float [] [] potParam =
    { // [row,column]
        //order of rows is
        //mid 0
        //left 1
        //right 2
        {16.1f,8.12f},
        {-15.2f,62.6f},
        {15.15f,3.25f}
    };

    /**
     * @param static RNGVERDIST s the height of the first pyramid rung above floor and
     * the higher pyramid rungs above the middle of the next lower rung
     * (formerly k2)
     */
    protected  static float RNGVERDIST = 30.75f;
    /**
     * @param static RNGHORIZDIST is horizontal distance between rungs
     *  (formerly k3)
     */
    protected  static float RNGHORIZDIST = 12.1f;
    /**
     * @param FLRHANGLENG tapelength at which the robot climbing from the floor
     * breaks free 
     */
    protected  static float FLRHANGLENG = 10.5f;
    /**
     * @param BUMPHITANG frame angle at which the rear bumper touches the floor
     * when climbing first rung
     */
    protected  static float BUMPHITANG = 25f;
    // 
    /**
     * @param RUNGHANGANG the frame angle condition at which robot breaks free
     * while climbing between rungs
     */
    protected static float RUNGHANGANG = 52f;
    /**
     * @param RUNGHANGLENG tapelength condition at which robot breaks free while
     * climbing between rungs
     */
    protected static float RUNGHANGLENG = 13f;
    /**
    
     * @param dMaxDeflec maximum deflection of tape at 0 angle and full
     * extension
     */
    protected static float dMaxDeflec = 4f;
    SmartDashboard smartdashboard;

    public FrameMath(){};
    
    /**
     * This method gets the angle of the tape relative to the frame, while the
     * the robot is climbing. It needs to know:
     *
     * @param floor --boolean Are We climbing from the floor?
     * @param T --float the tapelength, obtained thru a formula based on pot
     * value, reflecting how much the pulley has turned.
     * @param rFrAng -- float The angle of the frame to horizontal in radians
     * retrieved from the Gyro, The calculations are complex, involving a lot of
     * trig and the quadratic formulae, but are derived from the robot geometry
     * and the pyramid geometry. The crucial states are: Are we climbing from
     * floor or from rung? Are we hanging free, or constrained at the back of
     * the robot by the floor or lower rung
     *
     * @return angle of tape to frame in degrees
     */
    public static float getClimbTapeAngle(boolean floor, float T,
            int pulley) {
        float dFrAng =(float) SI.getdFrameAngle();
        float rFrAng =(float)SI.getrFrameAngle();
        float rTapeAngFloor;
        float dTapeAngle;
        float [] pulleyHeight;
        float [] [] servRodPosition;
        pulleyHeight = new float[]{9.5f, 1.125f, 1.125f};
        float heightPull=pulleyHeight[pulley];

        if (floor) // are we climbing from floor
        {
            if (T < FLRHANGLENG) //Are we hanging free
            {    // The break free condition check
                dTapeAngle = 90 - dFrAng;
                return dTapeAngle;
            } 
            else //We are climbing from floor, not hanging free
                
               if (dFrAng <= BUMPHITANG) // Rear wheel is touching
                {
                dTapeAngle =(float)Math.toDegrees(
                    MathUtils.asin((RNGVERDIST - 12.3 * Math.sin(rFrAng))/T));
                return dTapeAngle;
                } 
                else //Rear bumper is touching
                {
                dTapeAngle =(float)Math.toDegrees(
                    MathUtils.asin( (RNGVERDIST - 18.9 * Math.sin(rFrAng))/T));
                return dTapeAngle;
                 }

        } 
        else // Get here when climbing between rungs... not from floor
        {
            if (T < RUNGHANGLENG & dFrAng < RUNGHANGANG) // Breaking free angle plus length condition
            {
                dTapeAngle = 90 - dFrAng;
                return dTapeAngle;
            } 
            else 
                if (dFrAng < 91 && dFrAng > 89) //Are we near 90 degrees
                {
                rTapeAngFloor = (float)MathUtils.acos((RNGHORIZDIST + heightPull));
                } 
                else 
                    if (dFrAng < 1 && dFrAng > -1) //Are re we near 0 degrees?
                    {
                        if (T <= Math.sqrt(MathUtils.pow(RNGVERDIST - heightPull, 2)
                             + MathUtils.pow(RNGHORIZDIST + heightPull, 2)))
                        {
                           rTapeAngFloor = (float)MathUtils.asin((RNGVERDIST - heightPull) / T);
                        }
                        else 
                        
                        rTapeAngFloor = (float)MathUtils.acos((RNGHORIZDIST + heightPull) / T);
                        
                    } 
                    else
                    {    //Ugly quad stuff no need to do unless frame angle notnear 90 or 0 and
                        // near 90 or 0
                     float k5 = 1 / (float)Math.tan(rFrAng);
                     float a = (1 + (float)MathUtils.pow(k5, 2));
                     float k4 = heightPull / (float)Math.sin(rFrAng);
                     float b = -2 * (RNGVERDIST + k4 * k5 + RNGHORIZDIST * k5);
                     float c = (float)MathUtils.pow(RNGVERDIST, 2)
                        + (float)MathUtils.pow(RNGHORIZDIST, 2)
                        + (float)MathUtils.pow(k4, 2) + 2 * (RNGHORIZDIST * k4) 
                        - (float)MathUtils.pow(T, 2);
                     SmartDashboard.putNumber("getClimbTapeAng getClimbTapeAng "+String.valueOf(pulley)+"Quad k4", k4);
                     SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+" Quad k5", k5);
                    
                     SmartDashboard.putNumber(" "+String.valueOf(pulley)+"Quad a", a);
                     SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+" Quad b", b);
                     SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+" Quad c", c);
                     float x2 = (-b - (float)Math.sqrt(((b * b) - (4 * a * c)))) / (2 * a);
                     SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+" Quad x2", x2);
                      rTapeAngFloor = (float)MathUtils.atan((RNGVERDIST-x2)
                              / (RNGHORIZDIST + k4 - k5 * x2));
                      }
            SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+"dTapeAngFloor",
                               Math.toDegrees(rTapeAngFloor));
           dTapeAngle = (float)Math.toDegrees(rTapeAngFloor) - dFrAng;
           SmartDashboard.putNumber("getClimbTapeAng "+String.valueOf(pulley)+ 
                   "dTapeAngle to Frame", dTapeAngle);
           return dTapeAngle;
        }
    }
    /**
     * This method computes the servo value from 0 to 1 based on the desired angle of
     * the tape to the frame, using a formula based on robot geometry and
     * empirical measurements, The key ideas are: 
     * 1. As the tape length shortens, the rods are bent into a tighter
     * hoop, and the rods want to bend outward,away from each other. As
     * the tape lengthens the rods bend toward each other. At extreme
     *  extension, they would be parallel to the tape.  
     * 2. In order not to break  the rods or burn out the servo, when
     * climbing, because the angle is constrained, the servo needs to be set so
     * its angle is consistent with zero moment, ie zero torque, applied to the
     * end of the rods. This"relaxed angle" is a function only of the distance
     * between the two rod ends-- the servo and the hook tip.
     * 3 The relevant angle is the angle of the rod end at the servo to the axis
     *  connecting it to its other end at the hook. 
     * 4. This angle is computed from the position of the servo relative
     * to the end of the tape and the angle of the tape relative to the frame. 
     * 5. If the tape is hanging free, the servo needs to
     * account for the droop in tape which is at its maximum (about 4 degrees)
     * when the tape is fully extended to 45 inches
     *
     * @param rTapeAngToFrame tape angle relative to frame in radians
     * @param T is tape length in inches
     * @param droop true if hanging free, false if attached to rung when
     * climbing
     * @return value of servo to achieve that tape angle at that tape length
     */
    public static float calcServoFromAngle(boolean droop, 
            float rTapeAngToFrame, float T,int pulley) {
        /**
         * Adjustment for hanging free or freestanding When tape is hanging
         * constrained, the goal is to set the servo to put the tape so it
         * imposes no torque on the Servo. When setting the free standing tape
         * to an angle, it will droop so we have to aim it an a higher angle.
         * This droop is related to the square of the cosine of the angle of
         * tape relative to horizontal and cube of the length of the tape.
         *
         * @param  rTapeAngToFloor angle of the Tape relative to floor
         * @param droopAdjFact of Tape angle to account for tape droop It will
         * be a fraction between 0 and 1
         * @param droop if true make the adjustment to target tape angle
         */
     
       //  inititialize angTapHoriz
        float rTapeAngToFloor = 0;
        float rFramAng=(float)SI.getrFrameAngle();
        float dFramAng=(float)SI.getdFrameAngle();
        SmartDashboard.putNumber("calcServoFromAngle dFrameAngle "+String.valueOf(pulley),
               dFramAng);
        //if (dFramAng>10 || dFramAng<-10)rFramAng=0;
       // float dFramAng=SI.getdFrameAngle();
        if (droop) {
            rTapeAngToFloor = rTapeAngToFrame + rFramAng;
        }
       // SmartDashboard.putNumber("calcServoFromAngle dTapeAngToFloor "+String.valueOf(pulley),
        //        Math.toDegrees(rTapeAngToFloor));
       // SmartDashboard.putNumber("calcServoFromAngle dTapeAngToFrame "
       //         +String.valueOf(pulley),Math.toDegrees(rTapeAngToFrame));
       // SmartDashboard.putBoolean("calcServoFromAngle droop "+String.valueOf(pulley),droop);
        /**
         * @param droopAdjFact will be between 0 and 1. It is normalized
         * relative to an expected deflection of
         * @param dMaxDeflec the deflection in degrees at 45 inches of 
         * horizontal tape.
         *
         */
        float droopAdjFact = (T * T * T * (float)Math.cos(rTapeAngToFloor)
                *(float) Math.cos(rTapeAngToFloor)) / (45 * 45 * 45);
      //  SmartDashboard.putNumber("droopAdjFactor", droopAdjFact);
        //
        //Add the adjustment to the target angle
        //
        rTapeAngToFrame = rTapeAngToFrame + (float)Math.toRadians(droopAdjFact * dMaxDeflec);
         /**
         * Trig calculation which relates the distance between the rod ends to:
         * T is tape length, rTapeAngToFrame the angle of the tape to the frame, The
         * position of the servo relative to the bottom of the pulley
         * The next two parameters specify the distance of the servo relative 
         * to the pulley bottom. They are stored in an array.
         * @param servHeightPullBott 
         * @param servDistBehindPull 
         * Apply Pythagorean
         * theorem.
         */
         float [] [] servRodPosition;
        servRodPosition= new float [][]
        {
            //{servHeightPullBott,servDistBehindPull}
            {4.5f,0.f},
            {4.5f,2.5f},
            {4.5f,2.5f},
        };  
         //
        float servHeightPullBott=servRodPosition [pulley][0];
        
       // SmartDashboard.putNumber("CalcServo servHeightPullBott "+String.valueOf(pulley)
          //      + "height", servHeightPullBott);
        float servDistBehindPull=servRodPosition [pulley][1];
        
        //SmartDashboard.putNumber("CalcServo servDistBehind "+String.valueOf(pulley)
         //       + "behind dist", servDistBehindPull);
        /* 
         * now the trig equations
         */ 
        float j2 = (float)Math.sin(rTapeAngToFrame) * T - servHeightPullBott;
        SmartDashboard.putNumber("CalcServo rodhookend "+String.valueOf(pulley)
                + " vert dist j2 ", j2);
        float j3 = (float)Math.cos(rTapeAngToFrame) * T + servDistBehindPull;
        SmartDashboard.putNumber("CalcServo rodhookend "+String.valueOf(pulley)
                + " horz dist j3 ", j3);
        /**
         * rodAxisLeng is distance between rod ends, for a given tape length and
         * tape angle to the frame, based on relative position of pulley bottom
         * and servo
         */
        float rodAxisLeng = (float)Math.sqrt((j2 * j2) + (j3 * j3));
        SmartDashboard.putNumber("Calc Servo distance betw rod ends j4 "+
                String.valueOf(pulley),
                rodAxisLeng);
        //
        /**
         * dServAngRodAxis is the angle of the rod end at the servo,that is, the
         * servo angle, relative to the axis between rod ends, as a function of
         * the distance between the rod ends. The rods form a hoop. When the
         * tape is fully extended,say at 45 inches, this angle is about 45
         * degrees. When tape is fully contracted, rodAxisLeng approaches zero
         * and this angle is about 180 degrees.This linear equation, empirically
         * derived, is the relation between distance between the rod ends and
         * the angle formed by the rod end, at the servo, to the axis connecting
         * the rod ends.
         *  The slope and intercept for these linear equations are stored in the 
         * array. @param servRodParam, first two columns.
         *
         */
          float [] [] servRodParam;
         servRodParam = new float [] []
                //only left one is correct
                //mid
               // left
               //right is now correct
        {
          {-1.96f,172f,-.00325f,.803f},
          {-2.61f,+171f,.00325f,.276f},
          {-2.61f,171f,-.00793f,1.427f}
        };
        float mServAngRodAxis  = servRodParam [pulley][0];
        float kServAngRodAxis = servRodParam [pulley][1]; 
        //
        float dServAngRodAxis = mServAngRodAxis*rodAxisLeng + kServAngRodAxis; 
       
        SmartDashboard.putNumber("CalcServo Angle of servo j5"+String.valueOf(pulley)+ 
                " to rod axis deg", dServAngRodAxis);
        /**
         * j6 is the angle the rod axis makes to the tape.
         */
        float j6;
        if (j3 != 0) {
            j6 = (float)Math.toDegrees(MathUtils.atan(j2/j3));
        } else {
            j6 = 0;
        }
        SmartDashboard.putNumber("CalcServo Angle of rod "+String.valueOf(pulley)+ 
                " axis to frame deg", j6);
        /**
         * j7 is the angle that the rod end at the servo makes to the frame. It
         * therefore the servo angle to the frame.
         */
        float j7 = dServAngRodAxis + j6;
        SmartDashboard.putNumber("Calc Servo Angle of servo "+
                String.valueOf(pulley)+ "to frame deg", j7);
        /**
        * The empirically derived linear relationship between servo input value
        * and servo angle relative to the frame
        * The slope and intercept of this equation are stored in the 3d and 4th
        * columns of the servRodParam array above
        *
        */
         float mServAngToServVal=servRodParam[pulley] [2];
         float kServAngToServVal=servRodParam[pulley] [3];
        //
        float sVal = kServAngToServVal
                + mServAngToServVal * j7;
        SmartDashboard.putNumber("CalcServo ServoVal"+String.valueOf(pulley)+
                " value", sVal);
        return sVal;
    }
    /**
     * calculates linear regression using least squares method
     * @param n  number of data points in array
     * 
     * @param x x values independent
     * @param y y values dependents
     * @return   slope and intercept ina 2 dimensional array
     */
    public static float [] linearRegression(int n,float x [], float y [] ){
        float sumX=0;
        float sumY=0;  
        for (int i=0 ;i<n-1;i++){
        sumX=sumX+x[i];
        sumY=sumY+y[i];        
        }
        float meanX=sumX/n;
        float meanY=sumY/n;
        float productDelta=0;  
        float squareDeltaX=0;
        float deltaX;
        float deltaY;
        for (int i=0 ;i<n-1;i++){
        deltaX=(meanX-x[i]);
        deltaY=(meanY-y[i]);               
        productDelta=productDelta+(deltaX*deltaY);
        squareDeltaX=squareDeltaX+(deltaX*deltaX);
        }
       float [] mk = new float[2];
        mk[0]=productDelta/squareDeltaX;
        mk[1]=meanY-mk[0]*meanX;
        return mk;
           
    }
    
}