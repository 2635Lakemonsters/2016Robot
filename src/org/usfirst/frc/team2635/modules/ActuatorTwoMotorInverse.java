package org.usfirst.frc.team2635.modules;

import edu.wpi.first.wpilibj.SpeedController;

public class ActuatorTwoMotorInverse extends ActuatorTwoMotor 
{

	public ActuatorTwoMotorInverse(SpeedController leftMotor, SpeedController rightMotor)
	{
		super(leftMotor, rightMotor);
	}
	@Override
	public boolean actuate(Double magnitude)
	{
		rightMotor.set(-magnitude);
		leftMotor.set(magnitude);
		return false;
		
	}
}
