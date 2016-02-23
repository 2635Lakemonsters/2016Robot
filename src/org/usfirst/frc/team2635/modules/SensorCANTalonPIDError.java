package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.sensor.interfaces.BaseSensor;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SensorCANTalonPIDError extends BaseSensor<Double>
{
	CANTalon talon;
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double sense(Object argument)
	{
		return talon.getError();
	}

	public SensorCANTalonPIDError(CANTalon talon)
	{
		super();
		this.talon = talon;
	}

}
