package org.usfirst.frc.team2635.components;


import com.lakemonsters2635.sensor.modules.SensorOneShot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;

public class ClimberCommon extends Common
{
	public CANTalon climberMotor;
	public static final int CLIMBER_CHANNEL = 14;
	
	public static final int UP_BUTTON = 3;
	public static final int DOWN_BUTTON = 2;
	public ClimberCommon(Joystick rightJoystick, Joystick leftJoystick, CANTalon climberMotor)
	{
		super(rightJoystick, leftJoystick);
		this.climberMotor = climberMotor;
	}
	
	
}