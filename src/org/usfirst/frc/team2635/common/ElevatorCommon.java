package org.usfirst.frc.team2635.common;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.AllocationException;

public  class ElevatorCommon 
{
	public static void init()
	{
		rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
		rightElevatorMotor.reverseSensor(true);
		rightElevatorMotor.reverseOutput(true);
		rightElevatorMotor.setPosition(0.0);
		
		leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);
		leftElevatorMotor.reverseSensor(true);
		leftElevatorMotor.reverseOutput(true);
		leftElevatorMotor.setPosition(0.0);
		
		leftElevatorLimit = new DigitalInput(LEFT_ELEVATOR_LIMIT_CHANNEL);
		rightElevatorLimit = new DigitalInput(RIGHT_ELEVATOR_LIMIT_CHANNEL);
	

	}
	public static double ELEVATION_MAX = 45000.0; 
	public static double ELEVATION_ABOVE_CHASSIS = ELEVATION_MAX / 2; 
	
	public static double ELEVATION_ERROR = 1000.0;
	public static int ELEVATE_UP_BUTTON = 11;
	public static int ELEVATE_DOWN_BUTTON = 10;
	public static int AIM_BUTTON = 3;
	public static int RIGHT_ELEVATOR_CHANNEL = 10;
	public static int LEFT_ELEVATOR_CHANNEL = 12;
	static int LEFT_ELEVATOR_LIMIT_CHANNEL = 2;
	static int RIGHT_ELEVATOR_LIMIT_CHANNEL = 3;
	
	public static CANTalon rightElevatorMotor;
	public static CANTalon leftElevatorMotor;
	public static DigitalInput leftElevatorLimit;
	public static DigitalInput rightElevatorLimit;
	public static  String ELEVATOR_KEY_P = "Elevator P";
	public static  String ELEVATOR_KEY_I = "Elevator I";
	public static  String ELEVATOR_KEY_D = "Elevator D";
	
	public static  double ELEVATOR_P_DEFAULT = 0.13;
	public static  double ELEVATOR_I_DEFAULT = 0.0001;
	public static  double ELEVATOR_D_DEFAULT = 0.0;

	

}
