package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.sensor.interfaces.BaseSensor;

import edu.wpi.first.wpilibj.PIDSourceType;
/**
 * Process a number of sensors and return true if they all return true. No sensors can have input.
 * <br>
 * I wanted to have this be an array of sensors to process, but Java doesn't handle generically typed arrays well, so damnit.
 * @author LakeM
 *
 */
public class SensorThreeAND extends BaseSensor<Boolean>
{
	BaseSensor<Boolean> sensor1;
	BaseSensor<Boolean> sensor2;
	BaseSensor<Boolean> sensor3;
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub
		
	}


	public SensorThreeAND(BaseSensor<Boolean> sensor1, BaseSensor<Boolean> sensor2, BaseSensor<Boolean> sensor3)
	{
		super();
		this.sensor1 = sensor1;
		this.sensor2 = sensor2;
		this.sensor3 = sensor3;
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
	public Boolean sense(Object argument)
	{
		
		return sensor1.sense(null) && sensor2.sense(null) && sensor3.sense(null);
	}
	
}
