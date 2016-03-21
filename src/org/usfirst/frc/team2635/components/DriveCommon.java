package org.usfirst.frc.team2635.components;

import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;

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

	public DriveCommon()
	{
		super();
		rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
		midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
		frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
		rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
		midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
		frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);

		robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
		speedModeScaler = 1000;
	}
	public static CANTalon rearRightMotor;
	public static CANTalon midRightMotor;
	public static CANTalon frontRightMotor;
	
	public static CANTalon rearLeftMotor;
	public static CANTalon midLeftMotor;
	public static CANTalon frontLeftMotor;
	
	/**
	 * The type of robotDrive can change between states, so it needs to be initialized in the state, not DriveComponents
	 */
	public static BaseDrive robotDrive;

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
	public static double speedModeScaler = 1000.0;

}
