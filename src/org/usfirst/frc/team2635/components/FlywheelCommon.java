package org.usfirst.frc.team2635.components;

import org.usfirst.frc.team2635.modules.ActuatorLauncherFeedInvertRight;
import org.usfirst.frc.team2635.modules.Flywheel;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

/**
 * Container class for all of the components and constants that go into a shooter.
 * @author Tristan
 *
 */
public class FlywheelCommon extends Common
{
	
	
	public FlywheelCommon()
	{
		super();
		this.rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
		this.leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
		this.feedMotor = new CANTalon(FEED_CHANNEL);
		flywheelRoutine = new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor);
		feedRoutine = new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor);
	}





	protected CANTalon rightFlywheelMotor;
	protected CANTalon leftFlywheelMotor;
	
	protected CANTalon feedMotor;
	protected BaseActuator<Double> flywheelRoutine;
	protected BaseActuator<Double> feedRoutine;
	protected static String REZERO_KEY = "Reset encoders";
	/**
	 * Left Joystick
	 */
	protected static int REZERO_BUTTON = 8;
	/**
	 * Left Joystick
	 */
	protected static int REZERO_INTERRUPT_BUTTON = 9;
	
	protected static  String SHOOTER_KEY_P = "Shooter P";
	protected static  String SHOOTER_KEY_I = "Shooter I";
	protected static  String SHOOTER_KEY_D = "Shooter D";
	
	protected static double SHOOTER_P_DEFAULT = 0.1;
	protected static double SHOOTER_I_DEFAULT = 0.0001;
	protected static double SHOOTER_D_DEFAULT = 0.0;

	protected static String SHOOTER_SPEED_KEY = "ShooterSpeed";
	protected static String TILT_KEY = "Tilt";
	protected static String ELEVATION_KEY = "Elevation";
	
	protected static String DRIVE_MODE_KEY = "DriveMode";
	protected static String DRIVE_MODE_VBUS = "Vbus";
	protected static String DRIVE_MODE_SPEED = "Speed";
		
	protected static int RIGHT_FLYWHEEL_CHANNEL = 10;
	protected static int LEFT_FLYWHEEL_CHANNEL = 7;		
	protected static int FEED_CHANNEL = 11;
	protected static double FIRE_SPEED; // 1.0; //TODO: If speed mode implemented, multiply this by speed scaler
	protected static double FEED_SPEED; //0.5;
	protected static double SHOOTER_ERROR = 3000.0;
	protected static int FIRE_BUTTON = 1;
	protected static int FEED_BUTTON = 2;



}
