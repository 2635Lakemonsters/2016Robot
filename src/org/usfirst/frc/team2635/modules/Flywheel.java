package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;

public class Flywheel
{

	BaseActuator<Double> wheel;
	
	BaseActuator<Double> launcherFeed;
	
	BaseActuator<Double> frontLoader;
	BaseActuator<Double> backLoader;
	BaseSensor<Boolean> canFireSensor;
	
	BaseActuator<Double> elevator;
	BaseActuator<Double> tilter;
	public Flywheel(BaseActuator<Double> wheel, BaseActuator<Double> launcherFeed, BaseActuator<Double> frontLoader,
			BaseActuator<Double> backLoader, BaseSensor<Boolean> canFireSensor, BaseActuator<Double> elevator,
			BaseActuator<Double> tilter)
	{
		super();
		this.wheel = wheel;
		this.launcherFeed = launcherFeed;
		this.frontLoader = frontLoader;
		this.backLoader = backLoader;
		this.canFireSensor = canFireSensor;
		this.elevator = elevator;
		this.tilter = tilter;
	}
	public void fire(double wheelMagnitude, double elevateMagnitude, double tiltMagnitude, double feedMagnitude) {
		tilter.actuate(tiltMagnitude);
		elevator.actuate(elevateMagnitude);
		wheel.actuate(wheelMagnitude);
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
