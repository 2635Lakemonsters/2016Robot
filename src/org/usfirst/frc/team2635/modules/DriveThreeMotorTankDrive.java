package org.usfirst.frc.team2635.modules;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Drive in a tank drive configuration
 * @author LakeM
 *
 */
public class DriveThreeMotorTankDrive extends DriveThreeMotor
{

	public DriveThreeMotorTankDrive(SpeedController rearRightMotor, SpeedController midRightMotor,
			SpeedController frontRightMotor, SpeedController rearLeftMotor, SpeedController midLeftMotor,
			SpeedController frontLeftMotor)
	{
		super(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean drive(double left, double right)
	{
		rearRightMotor.set(right);
		midRightMotor.set(right);
		frontRightMotor.set(right);
		
		rearLeftMotor.set(left);
		midLeftMotor.set(left);
		frontLeftMotor.set(left);
		return false;
	}

	@Override
	public boolean drive(double left, double right, double Z)
	{
		drive(left, right);
		return false;
	}

}
