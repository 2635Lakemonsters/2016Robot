package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.Common;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorCommon extends Common
{
	public ElevatorCommon() 
	{
		super();
		rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
		leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);
		
		rightElevatorLimit = new DigitalInput(RIGHT_ELEVATOR_LIMIT_CHANNEL);
		leftElevatorLimit = new DigitalInput(LEFT_ELEVATOR_CHANNEL);
	}
	CANTalon rightElevatorMotor;
	CANTalon leftElevatorMotor;
	DigitalInput leftElevatorLimit;
	DigitalInput rightElevatorLimit;
	static double ELEVATION_MAX = 45000.0; 
	static double ELEVATION_ABOVE_CHASSIS = ELEVATION_MAX / 2; 
	
	static double ELEVATION_ERROR = 1000.0;
	static  int ELEVATE_UP_BUTTON = 11;
	static  int ELEVATE_DOWN_BUTTON = 10;
	static  int AIM_BUTTON = 3;
	static int LEFT_ELEVATOR_LIMIT_CHANNEL = 2;
	static int RIGHT_ELEVATOR_LIMIT_CHANNEL = 3;
	static int RIGHT_ELEVATOR_CHANNEL = 13;
	static int LEFT_ELEVATOR_CHANNEL = 12;
	static  String ELEVATOR_KEY_P = "Elevator P";
	static  String ELEVATOR_KEY_I = "Elevator I";
	static  String ELEVATOR_KEY_D = "Elevator D";
	
	static  double ELEVATOR_P_DEFAULT = 0.15;
	static  double ELEVATOR_I_DEFAULT = 0.0001;
	static  double ELEVATOR_D_DEFAULT = 0.0;

	

}
