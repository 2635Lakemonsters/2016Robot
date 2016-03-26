package org.usfirst.frc.team2635.modules;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDOutputThreeMotorRotate implements PIDOutput
{
	DriveThreeMotor robotDrive;
	public PIDOutputThreeMotorRotate(DriveThreeMotor robotDrive)
	{
		super();
		this.robotDrive = robotDrive;
	}
	@Override
	public void pidWrite(double output)
	{
		//Output doesn't need to be inverted because the motor itself is inverted
		robotDrive.drive(output, output);
		
	}

}
