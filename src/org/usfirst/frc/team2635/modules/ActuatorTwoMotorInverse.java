package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Drive the left motor one way and the right motor the other way
 * @author LakeM
 *
 */
public class ActuatorTwoMotorInverse extends BaseActuator<Double>
{
	SpeedController leftMotor;
	SpeedController rightMotor;
	public ActuatorTwoMotorInverse(SpeedController leftMotor, SpeedController rightMotor)
	{
		super();
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	@Override
	public boolean actuate(Double magnitude)
	{
		leftMotor.set(magnitude);
		rightMotor.set(-magnitude);
		return false;
	}
}
