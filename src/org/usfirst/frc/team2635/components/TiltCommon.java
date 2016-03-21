package org.usfirst.frc.team2635.components;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
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

	public static double TILT_MAX = -750.0; 

	public static CANTalon tiltMotor;

	public static Encoder tiltEncoder;
	public static PIDController tiltPID;
	public static DigitalInput tiltLimit;

	public static int TILT_ENCODER_A = 1;
	public static int TILT_ENCODER_B = 0;
	public static String CAMERA_Y_KEY_P = "Camera Y P";
	public static String CAMERA_Y_KEY_I = "Camera Y I";
	public static String CAMERA_Y_KEY_D = "Camera Y D";
	public static double CAMERA_Y_P_DEFAULT = 0.015;
	public static double CAMERA_Y_I_DEFAULT = 0.001;
	public static double CAMERA_Y_D_DEFAULT = 0.0;
	public static int RIGHT_ELEVATOR_CHANNEL = 13;
	public static int TILT_LIMIT_CHANNEL = 4;

	public static int TILT_CHANNEL = 9;


}
