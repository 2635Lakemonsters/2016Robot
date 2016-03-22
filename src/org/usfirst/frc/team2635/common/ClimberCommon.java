package org.usfirst.frc.team2635.common;


import com.lakemonsters2635.sensor.modules.SensorOneShot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;

public class ClimberCommon 
{
	public static void init()
	{
		climberMotor = new CANTalon(CLIMBER_CHANNEL);

	}
	public static final int CLIMBER_CHANNEL = 14;
	public static CANTalon climberMotor;
	
	public static final int UP_BUTTON = 3;
	public static final int DOWN_BUTTON = 2;
	
	
	
}