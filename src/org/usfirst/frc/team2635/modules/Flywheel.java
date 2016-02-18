package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;

/**
 * Class that represents a flywheel. 
 * @author LakeM
 *
 */
public class Flywheel
{

	BaseActuator<Double> wheel;
	
	BaseActuator<Double> launcherFeed;
	
	BaseActuator<Double> frontLoader;
	BaseActuator<Double> backLoader;
	BaseSensor<Boolean> canFireSensor;
	
	BaseActuator<Double> elevator;
	BaseActuator<Double> tilter;
	/**
	 * 
	 * @param wheel The thing(s) that accelerate the projectile
	 * @param launcherFeed The thing that feeds the projectile to the wheel
	 * @param frontLoader The thing that loads from the front
	 * @param backLoader The thing that loads from behind
	 * @param canFireSensor The thing that checks if the ball should be fed to the wheel or not
	 * @param elevator The thing that raises the shooter up and down
	 * @param tilter The thing that tilts the shooter up and down
	 */
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
	public void fire(double wheelMagnitude, double elevateMagnitude, double tiltMagnitude, double feedMagnitude) 
	{
		//Get the wheel spinning
		wheel.actuate(wheelMagnitude);

		//Get into firing position
		elevator.actuate(elevateMagnitude);
		tilter.actuate(tiltMagnitude);
		
		//Check if the launcher is ready to fire or not
		if(canFireSensor.sense(null))
		{
			//If it is, feed the ball to the wheel
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
