package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;

import edu.wpi.first.wpilibj.SpeedController;

public class ActuatorBlockingMotor extends BaseActuator<Double>
{
	BaseSensor<Boolean> canActuateChecker;
	SpeedController motor;
	public ActuatorBlockingMotor(SpeedController motor, BaseSensor<Boolean> canActuateChecker)
	{
		super();
		this.canActuateChecker = canActuateChecker;
		this.motor = motor;
	}
	@Override
	public boolean actuate(Double magnitude)
	{
		if(canActuateChecker.sense(null))
		{
			motor.set(magnitude);
		}
		else
		{
			motor.set(0);
		}
		return true;
	}
}
