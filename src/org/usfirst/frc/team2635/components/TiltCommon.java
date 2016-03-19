package org.usfirst.frc.team2635.components;

import edu.wpi.first.wpilibj.CANTalon;

public class TiltCommon extends Common
{
	public TiltCommon()
	{
		super();
		tiltMotor = new CANTalon(TILT_CHANNEL);
	}


	protected CANTalon tiltMotor;


	static int TILT_CHANNEL = 9;


}
