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
	
	BaseActuator<Double> elevator;
	BaseActuator<Double> tilter;
	public void fire(double wheelMagnitude, double elevateMagnitude, double tiltMagnitude, double feedMagnitude) {
		tilter.actuate(tiltMagnitude);
		elevator.actuate(elevateMagnitude);
		leftWheel.actuate(wheelMagnitude);
		rightWheel.actuate(wheelMagnitude);
		if(canFireSensor.sense(null))
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
