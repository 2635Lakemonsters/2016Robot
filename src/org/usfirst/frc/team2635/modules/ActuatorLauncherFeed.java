package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;

import edu.wpi.first.wpilibj.SpeedController;

public class ActuatorLauncherFeed extends BaseActuator<Double>
{
	SpeedController leftShooter;
	SpeedController rightShooter;
	SpeedController feed;
	public ActuatorLauncherFeed(SpeedController leftShooter, SpeedController rightShooter, SpeedController feed)
	{
		super();
		this.leftShooter = leftShooter;
		this.rightShooter = rightShooter;
		this.feed = feed;
	}
	@Override
	public boolean actuate(Double magnitude)
	{
		if(magnitude == 0.0)
		{
			feed.set(0.0);
		}
		else
		{
			if(magnitude < 0.0)
			{
				feed.set(1.0);
			}
			else 
			{
				feed.set(-1.0);
			}
		}
		leftShooter.set(magnitude);
		rightShooter.set(magnitude);
		return false;
	}
	
	
}
