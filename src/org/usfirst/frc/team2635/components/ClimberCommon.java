package org.usfirst.frc.team2635.components;


import com.lakemonsters2635.sensor.modules.SensorOneShot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class ClimberCommon extends Common
{
	protected CANTalon climberMotor;
	protected static final int CLIMBER_CHANNEL = 14;
	
	protected static final int UP_BUTTON = 3;
	protected static final int DOWN_BUTTON = 2;
	
	public ClimberCommon()
	{
		climberMotor = new CANTalon(CLIMBER_CHANNEL);
	}
}