package org.usfirst.frc.team2635.components;

import com.lakemonsters2635.actuator.interfaces.BaseDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Container class containing all of the things that make up the drive system.
 * @author Tristan
 *
 */
public class DriveCommon extends Common 
{

	public DriveCommon(Joystick rightJoystick, Joystick leftJoystick, CANTalon rearRightMotor, CANTalon midRightMotor,
			CANTalon frontRightMotor, CANTalon rearLeftMotor, CANTalon midLeftMotor, CANTalon frontLeftMotor,
			BaseDrive robotDrive, double speedModeScaler)
	{
		super(rightJoystick, leftJoystick);
		this.rearRightMotor = rearRightMotor;
		this.midRightMotor = midRightMotor;
		this.frontRightMotor = frontRightMotor;
		this.rearLeftMotor = rearLeftMotor;
		this.midLeftMotor = midLeftMotor;
		this.frontLeftMotor = frontLeftMotor;
		this.robotDrive = robotDrive;
		this.speedModeScaler = speedModeScaler;
	}
	public CANTalon rearRightMotor;
	public CANTalon midRightMotor;
	public CANTalon frontRightMotor;
	
	public CANTalon rearLeftMotor;
	public CANTalon midLeftMotor;
	public CANTalon frontLeftMotor;
	
	/**
	 * The type of robotDrive can change between states, so it needs to be initialized in the state, not DriveComponents
	 */
	public BaseDrive robotDrive;

	public static int REAR_RIGHT_CHANNEL = 1;
	public static int MID_RIGHT_CHANNEL = 2;
	public static int FRONT_RIGHT_CHANNEL = 3;
	
	public static int REAR_LEFT_CHANNEL = 4;
	public static int MID_LEFT_CHANNEL = 5;
	public static int FRONT_LEFT_CHANNEL = 6;
	
	
	public static String DRIVE_KEY_P = "Drive P";
	public static String DRIVE_KEY_I = "Drive I";
	public static String DRIVE_KEY_D = "Drive D";
	
	public static double DRIVE_P_DEFAULT = 0.2;
	public static double DRIVE_I_DEFAULT = 0.001;
	public static double DRIVE_D_DEFAULT = 0.0;
	public double speedModeScaler = 1000.0;

}
