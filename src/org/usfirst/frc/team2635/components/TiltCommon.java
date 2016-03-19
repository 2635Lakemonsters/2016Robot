package org.usfirst.frc.team2635.components;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class TiltCommon extends Common
{
	public TiltCommon()
	{
		super();
		tiltMotor = new CANTalon(TILT_CHANNEL);
    	tiltEncoder = new Encoder(TILT_ENCODER_A, TILT_ENCODER_B);
		tiltPID = new PIDController(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT, tiltEncoder, tiltMotor);
	
		tiltLimit = new DigitalInput(TILT_LIMIT_CHANNEL);
	}

	protected static double TILT_MAX = -750.0; 

	protected CANTalon tiltMotor;

	protected Encoder tiltEncoder;
	protected PIDController tiltPID;
	protected DigitalInput tiltLimit;

	protected static int TILT_ENCODER_A = 1;
	protected static int TILT_ENCODER_B = 0;
	protected static String CAMERA_Y_KEY_P = "Camera Y P";
	protected static String CAMERA_Y_KEY_I = "Camera Y I";
	protected static String CAMERA_Y_KEY_D = "Camera Y D";
	protected static double CAMERA_Y_P_DEFAULT = 0.015;
	protected static double CAMERA_Y_I_DEFAULT = 0.001;
	protected static double CAMERA_Y_D_DEFAULT = 0.0;
	protected static int RIGHT_ELEVATOR_CHANNEL = 13;
	static int TILT_LIMIT_CHANNEL = 4;

	static int TILT_CHANNEL = 9;


}
