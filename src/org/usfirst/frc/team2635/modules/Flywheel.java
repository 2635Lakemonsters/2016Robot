package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;

public class Flywheel
{

	BaseActuator<Double> leftWheel;
	BaseActuator<Double> rightWheel;
	
	BaseActuator<Double> launcherFeed;
	
	BaseActuator<Double> frontLoader;
	BaseActuator<Double> backLoader;
	
	BaseSensor<Boolean> canFireSensor;
	public void fire(double magnitude, double feedMagnitude) {
		leftWheel.actuate(magnitude);
		rightWheel.actuate(magnitude);
		if(canFireSensor.sense())
		{
			launcherFeed.actuate(feedMagnitude);
		}
		
	}
	public void loadBack(double magnitude)
	{
		backLoader.actuate(magnitude);
	}
	
	public void loadFront(double magnitude)
	{ 
		frontLoader.actuate(magnitude);
	}
	
}
