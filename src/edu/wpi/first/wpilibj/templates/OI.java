package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // need some buttons
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    //
    //  Following not used anymore. Needed a central place to get frameangle
    //Gyro gyro = new Gyro(RobotMap.GYRO_CHAN);  

    //public double getFrameAngle() {
    //double frameangle = gyro.getAngle();
    //return Math.toRadians(frameangle);
    protected Joystick driveStick;
    protected Joystick leftStick;
    protected Joystick rightStick;
    // Wills buttons
    protected Button driveMode;
    protected Button wAngle;
    protected Button wLength;
    // Left Buttons
    protected Button lAngle;
    protected Button lLength;
    protected Button lLock;
    protected Button lULock;
    protected Button lAdjust;
    //Right Buttons
    protected Button rAngle;
    protected Button rLength;
    protected Button rLock;
    protected Button rULock;
    protected Button rAdjust;

    public OI() {
        driveStick = new Joystick(RobotMap.DRIVESTICK);
        leftStick = new Joystick(RobotMap.LeftStick);
        rightStick = new Joystick(RobotMap.RightStick);
        initButtons();
    }

    private void initButtons() {
        // Wills buttons
        driveMode = new JoystickButton(driveStick, 1);
        wAngle = new JoystickButton(driveStick, 4);
        wLength = new JoystickButton(driveStick, 5);

        //Right Buttons
        rAngle = new JoystickButton(rightStick, 1);
        rLength = new JoystickButton(rightStick, 2);
        rLock = new JoystickButton(rightStick, 4);
        rULock = new JoystickButton(rightStick, 5);
        rAdjust = new JoystickButton(rightStick, 8);

        //Left Buttons
        lAngle = new JoystickButton(leftStick, 1);
        lLength = new JoystickButton(leftStick, 2);
        lLock = new JoystickButton(leftStick, 4);
        lULock = new JoystickButton(leftStick, 5);
        lAdjust = new JoystickButton(leftStick, 8);

        //Assign the buttons to their commands
        tieButtons();
    }

    private void tieButtons() {
        //Wills buttons
        //driveMode.whenPressed(new DriveMode());
        //wAngle.whenPressed(new WillAngle());
        //wLength.whenPressed(new WillLength());
        //Right buttons
        //rAngle.whenPressed(new RightPulleyAngle());
        //rLength.whenPressed(new RightPulleyExtend());
        //rLock.whenPressed(new RightPulleyLock(true));
        //rULock.whenPressed(new RightPulleyLock(false));
        //rAdjust.whenPressed(new RightPulleyAdjust());
        //Left buttons
        //lAngle.whenPressed(new LeftPulleyAngle());
        //lLength.whenPressed(new LeftPulleyExtend());
        //lLock.whenPressed(new LeftPulleyLock(true));
        //lULock.whenPressed(new LeftPulleyLock(false));
        //lAdjust.whenPressed(new LeftPulleyAdjust());
    }

    public Joystick getDriveStick() {
        return driveStick;
    }

    public Joystick getLeftStick() {
        return leftStick;
    }

    public Joystick getRightStick() {
        return rightStick;
    }
}
