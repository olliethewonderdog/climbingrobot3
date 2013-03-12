/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Brinton
 */
public class DigitalServo extends Servo {

    protected double lastSet;
    public boolean freed;
    private static final double kMaxServoAngle = 270.0;
    private static final double kMinServoAngle = 0.0;

    public DigitalServo(int channel) {
        super(channel);
        freed = false;
        setBounds(184, 0, 0, 0, 67);
    }

    //private void initServo() {
    //setBounds(184, 0, 0, 0, 67);
    //setPeriodMultiplier(PeriodMultiplier.k4X);
    public DigitalServo(int slot, int channel) {
        super(slot, channel);
        freed = false;
        setBounds(184, 0, 0, 0, 67);
    }

    public void set(double set) {	//??
        lastSet = set;
        super.set(set);
    }

    public void toLastSet() {
        super.set(lastSet);
    }

    public void free() {
        freed = true;
        super.free();
    }
}
