package org.usfirst.frc.team2635.components;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.AllocationException;

public class ElevatorCommon extends Common
{
	public ElevatorCommon(Joystick rightJoystick, Joystick leftJoystick, CANTalon rightElevatorMotor,
			CANTalon leftElevatorMotor, DigitalInput leftElevatorLimit, DigitalInput rightElevatorLimit)
	{
		super(rightJoystick, leftJoystick);
		this.rightElevatorMotor = rightElevatorMotor;
		this.leftElevatorMotor = leftElevatorMotor;
		this.leftElevatorLimit = leftElevatorLimit;
		this.rightElevatorLimit = rightElevatorLimit;
	}
	public CANTalon rightElevatorMotor;
	public CANTalon leftElevatorMotor;
	public DigitalInput leftElevatorLimit;
	public DigitalInput rightElevatorLimit;
	public static double ELEVATION_MAX = 45000.0; 
	public static double ELEVATION_ABOVE_CHASSIS = ELEVATION_MAX / 2; 
	
	public static double ELEVATION_ERROR = 1000.0;
	public static int ELEVATE_UP_BUTTON = 11;
	public static int ELEVATE_DOWN_BUTTON = 10;
	public static int AIM_BUTTON = 3;
	public static int RIGHT_ELEVATOR_CHANNEL = 10;
	public static int LEFT_ELEVATOR_CHANNEL = 12;
	public static  String ELEVATOR_KEY_P = "Elevator P";
	public static  String ELEVATOR_KEY_I = "Elevator I";
	public static  String ELEVATOR_KEY_D = "Elevator D";
	
	public static  double ELEVATOR_P_DEFAULT = 0.15;
	public static  double ELEVATOR_I_DEFAULT = 0.0001;
	public static  double ELEVATOR_D_DEFAULT = 0.0;

	

}
