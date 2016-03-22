package org.usfirst.frc.team2635.common;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Container class for all of the components and constants that go into a shooter.
 * @author Tristan
 *
 */
public class FlywheelCommon 
{
	
	public static void init()
	{
		rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
		leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
		leftFlywheelMotor.reverseSensor(true);
		feedMotor = new CANTalon(FEED_CHANNEL);

	}
	public static String REZERO_KEY = "Reset encoders";
	/**
	 * Left Joystick
	 */
	public static int REZERO_BUTTON = 8;
	/**
	 * Left Joystick
	 */
	public static int REZERO_INTERRUPT_BUTTON = 9;
	
	public static  String SHOOTER_KEY_P = "Shooter P";
	public static  String SHOOTER_KEY_I = "Shooter I";
	public static  String SHOOTER_KEY_D = "Shooter D";
	
	public static double SHOOTER_P_DEFAULT = 0.1;
	public static double SHOOTER_I_DEFAULT = 0.0001;
	public static double SHOOTER_D_DEFAULT = 0.0;

	public static String SHOOTER_SPEED_KEY = "ShooterSpeed";
		
	public static int RIGHT_FLYWHEEL_CHANNEL = 13;
	public static int LEFT_FLYWHEEL_CHANNEL = 7;		
	public static int FEED_CHANNEL = 11;
	public static double FIRE_SPEED; // 1.0; //TODO: If speed mode implemented, multiply this by speed scaler
	public static double FEED_SPEED; //0.5;
	public static double SHOOTER_ERROR = 3000.0;
	public static int FIRE_BUTTON = 1;
	public static int FEED_BUTTON = 2;
	public static CANTalon rightFlywheelMotor;
	public static CANTalon leftFlywheelMotor;
	
	public static CANTalon feedMotor;
	public static void spinFlywheels(double speed)
	{
		rightFlywheelMotor.set(speed);
		leftFlywheelMotor.set(-speed);
	}



}
