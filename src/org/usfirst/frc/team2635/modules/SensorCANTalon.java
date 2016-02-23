package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.sensor.interfaces.BaseSensor;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SensorCANTalon extends BaseSensor<Double>
{
	CANTalon talon;
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub
		
	}

	public SensorCANTalon(CANTalon talon)
	{
		super();
		this.talon = talon;
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		switch(talon.getControlMode())
		{
		case Voltage:
		case Disabled:
		case Follower:
		case MotionProfile:
		case PercentVbus:
		case Position:
			return PIDSourceType.kDisplacement;
		case Current:
		case Speed:
			return PIDSourceType.kRate;

		
		default:
			break;
		 
		}
		return null;
	}

	@Override
	public double pidGet()
	{
		return sense(null);
	}

	@Override
	public Double sense(Object argument)
	{
		return talon.get();
	}

}
