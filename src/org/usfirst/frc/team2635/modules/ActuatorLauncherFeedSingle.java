package org.usfirst.frc.team2635.modules;

import com.lakemonsters2635.actuator.interfaces.BaseActuator;

import edu.wpi.first.wpilibj.SpeedController;

public class ActuatorLauncherFeedSingle extends BaseActuator<Double>
{
	SpeedController shooter;
	SpeedController feed;
	public ActuatorLauncherFeedSingle(SpeedController shooter, SpeedController feed)
	{
		super();
		this.shooter = shooter;
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
		shooter.set(magnitude);
		return false;
	}

}
