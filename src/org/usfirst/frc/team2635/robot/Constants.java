package org.usfirst.frc.team2635.robot;

import com.ni.vision.NIVision;

/**
 * All constants are defaulted to Competition functionality mode
 * @author LakeM
 *
 */
public final class Constants
{
	
	public static class Drive
	{
		public static int REAR_RIGHT_CHANNEL = 1;
		public static int MID_RIGHT_CHANNEL = 2;
		public static int FRONT_RIGHT_CHANNEL = 3;
		
		public static int REAR_LEFT_CHANNEL = 4;
		public static int MID_LEFT_CHANNEL = 5;
		public static int FRONT_LEFT_CHANNEL = 6;
		
		public static int JOYSTICK_LEFT_CHANNEL = 1;
		public static int JOYSTICK_RIGHT_CHANNEL = 0;
		
		public static int RIGHT_Y_AXIS = 1;
		public static int LEFT_Y_AXIS = 1;
		
		public static String DRIVE_KEY_P = "Drive P";
		public static String DRIVE_KEY_I = "Drive I";
		public static String DRIVE_KEY_D = "Drive D";
		
		public static double DRIVE_P_DEFAULT = 0.2;
		public static double DRIVE_I_DEFAULT = 0.001;
		public static double DRIVE_D_DEFAULT = 0.0;
		public static int VOLTAGE_MODE_BUTTON = 8;
		public static int SPEED_MODE_BUTTON = 9;
		public static double speedModeScaler = 1000.0;
		
	}
	public static class Camera
	{
		static  String CAMERA_X_KEY_P = "Camera X P";
		static  String CAMERA_X_KEY_I = "Camera X I";
		static  String CAMERA_X_KEY_D = "Camera X D";
		
		static  String CAMERA_Y_KEY_P = "Camera Y P";
		static  String CAMERA_Y_KEY_I = "Camera Y I";
		static  String CAMERA_Y_KEY_D = "Camera Y D";
		
		static String CAMERA_MODE_KEY = "CameraMode";
		
		static String HUE_MAX_KEY = "HueMax";
		static String HUE_MIN_KEY = "HueMin";
		static String VAL_MAX_KEY = "ValMax";
		static String VAL_MIN_KEY = "ValMin";
		static String SAT_MIN_KEY = "SatMin";
		static String SAT_MAX_KEY = "SatMax";
		
	    static  double CAMERA_X_P_DEFAULT = 0.02;
	    static  double CAMERA_X_I_DEFAULT = 0.0006;
	    static  double CAMERA_X_D_DEFAULT = 0.0;

		static  double CAMERA_Y_P_DEFAULT = 0.015;
		static  double CAMERA_Y_I_DEFAULT = 0.001;
		static  double CAMERA_Y_D_DEFAULT = 0.0;
		
		static  double CAMERA_RESOLUTION_X = 640.0;
		static  double CAMERA_RESOLUTION_Y = 360.0;
		static  double CAMERA_VIEW_ANGLE = 64.0;
		static  double TARGET_ASPECT_RATIO = 14.0/20.0;
		static  NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
		static  NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
		static  NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
		
		static  double PARTICLE_AREA_MINIMUM = 0.5;
		static NIVision.PointDouble cameraSetpoints;
		
		static int TILT_PIXEL_SETPOINT = 250;
		static double PIXEL_TO_TILT = 1.149;
		
	}
	public static class Shooter
	{
		static String REZERO_KEY = "Reset encoders";
		/**
		 * Left Joystick
		 */
		static int REZERO_BUTTON = 8;
		/**
		 * Left Joystick
		 */
		static int REZERO_INTERRUPT_BUTTON = 9;
		
		static  String ELEVATOR_KEY_P = "Elevator P";
		static  String ELEVATOR_KEY_I = "Elevator I";
		static  String ELEVATOR_KEY_D = "Elevator D";
		
		static  double ELEVATOR_P_DEFAULT = 0.8;
		static  double ELEVATOR_I_DEFAULT = 0.0001;
		static  double ELEVATOR_D_DEFAULT = 0.0;

		static  String SHOOTER_KEY_P = "Shooter P";
		static  String SHOOTER_KEY_I = "Shooter I";
		static  String SHOOTER_KEY_D = "Shooter D";
		
		static double SHOOTER_P_DEFAULT = 0.01;
		static double SHOOTER_I_DEFAULT = 0.0001
				;
		static double SHOOTER_D_DEFAULT = 0.0;
	
		static String SHOOTER_SPEED_KEY = "ShooterSpeed";
		static String TILT_KEY = "Tilt";
		static String ELEVATION_KEY = "Elevation";
		
		static String DRIVE_MODE_KEY = "DriveMode";
		static String DRIVE_MODE_VBUS = "Vbus";
		static String DRIVE_MODE_SPEED = "Speed";
		
		static String TILT_FAULT_KEY = "TiltFault";
		static String ELEVATOR_FAULT_KEY = "ElevatorFault";
		static String SHOOTER_FAULT_KEY = "ShooterFault";
		
		static boolean tiltFault = false;
		static boolean elevatorFault = false;
		static boolean shooterFault = false;
		
		static int RIGHT_FLYWHEEL_CHANNEL = 13;
		static int LEFT_FLYWHEEL_CHANNEL = 7;
	
		static int TILT_CHANNEL = 9;
		static int TILT_ENCODER_A = 1;
		static int TILT_ENCODER_B = 0;
		
		static int RIGHT_ELEVATOR_CHANNEL = 10;
		static int LEFT_ELEVATOR_CHANNEL = 12;
		
		static int LEFT_ELEVATOR_LIMIT_CHANNEL = 2;
		static int RIGHT_ELEVATOR_LIMIT_CHANNEL = 3;
		
		static int TILT_LIMIT_CHANNEL = 4;
		
		static double REZERO_SPEED = 0.4;
		
		static int FEED_CHANNEL = 11;
		
		static double FIRE_SPEED; //= 1.0; //TODO: If speed mode implemented, multiply this by speed scaler
		static double FEED_SPEED; //0.5;
		
		static double LOAD_FRONT_SPEED = -0.5; //TODO: If speed mode implemented, multiply this by speed scaler
		//static  double LOAD_BACK_SPEED = 0.5;
		
		static double ELEVATE_UP_SPEED;// = 0.7;
		static double ELEVATE_DOWN_SPEED;// = -0.7;
		
		static double elevatorPosition = 0.0;
		static boolean elevatorState = false;
		static double debugTiltPosition = 0.0;
		
		static double TILT_MAX = -750.0; 
		static double TILT_RESTING = 0.0;
		
		static double ELEVATION_MAX = 45000.0; 
		static double ELEVATION_ABOVE_CHASSIS = ELEVATION_MAX / 2; 
		static double ELEVATION_START = 34000;
		static double ELEVATION_ERROR = 1000.0;
		static double SHOOTER_ERROR = 1000.0;
		static double TILT_ERROR = 10.0;
		//Right hand joystick
		static  int TILT_AXIS = 2;
		static  int AIM_BUTTON = 3;
		static  int FIRE_BUTTON = 1;
		static  int LOAD_FRONT_BUTTON = 2;
		static int AIM_CAMERA_BUTTON = 5;
		//static  int LOAD_BACK_BUTTON = 4;
		static  int ELEVATE_UP_BUTTON = 11;
		static  int ELEVATE_DOWN_BUTTON = 10;
		static int STARTING_BUTTON = 6;
		
		static int VBUS_FEED_BUTTON = 4;
	}
	public static class Climber
	{
		static  int CLIMBER_CHANNEL = 14;
		
		static  String CLIMBER_KEY_P = "Climber P";
		static  String CLIMBER_KEY_I = "Climber I";
		static  String CLIMBER_KEY_D = "Climber D";
		
		static  double CLIMBER_P_DEFAULT = 0.0;
		static  double CLIMBER_I_DEFAULT = 0.0;
		static  double CLIMBER_D_DEFAULT = 0.0;

		//TODO: find maximum height of climber, make fractions of that height as climber positions
		static int climberIndex = 0;
		
		static  double CLIMBER_MAX_HEIGHT = 0.0;
		
		//TODO: figure out what fractions of CLIMBER_MAX_HEGIHT are a good idea
		static  double[] CLIMBER_POSITIONS = {0.0, CLIMBER_MAX_HEIGHT / 2, CLIMBER_MAX_HEIGHT};
		
		//Left hand joystick
		static  int CLIMB_UP_BUTTON = 3;
		static  int CLIMB_DOWN_BUTTON = 2;

	}
	public static class Autonomous
	{
		/**
		 * These values should correspond with the values given by the labview smartdashboard.
		 * @author LakeM
		 *
		 */
		public enum AutoMode
		{
			NO_AUTO(0),
			SIMPLE_AUTO(1),
			SHOOT_AUTO(2);
			
			public int value;
			AutoMode(int number)
			{
				this.value = number;
			}
		}
		static String AUTO_KEY ="AutoMode";
		public static double countPerInch = 216.67;
		static double FORWARD_DISTANCE = 217.4 * countPerInch; //TODO: Figure out what this is
		static double ROTATION_DELTA = 60;
		static double ADJUSTMENT_DISTANCE = 0.0; //TODO Figure out what this is
	}
}
