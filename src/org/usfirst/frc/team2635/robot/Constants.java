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
		
		public static double DRIVE_P_DEFAULT = 0.0;
		public static double DRIVE_I_DEFAULT = 0.0;
		public static double DRIVE_D_DEFAULT = 0.0;
		
		public double speedModeScaler = 1000.0;
	}
	public static class Camera
	{
		static  String CAMERA_X_KEY_P = "Camera X P";
		static  String CAMERA_X_KEY_I = "Camera X I";
		static  String CAMERA_X_KEY_D = "Camera X D";
		
		static  String CAMERA_Y_KEY_P = "Camera Y P";
		static  String CAMERA_Y_KEY_I = "Camera Y I";
		static  String CAMERA_Y_KEY_D = "Camera Y D";
		
	    static  double CAMERA_X_P_DEFAULT = 0.02;
	    static  double CAMERA_X_I_DEFAULT = 0.0006;
	    static  double CAMERA_X_D_DEFAULT = 0.0;

		static  double CAMERA_Y_P_DEFAULT = 0.0;
		static  double CAMERA_Y_I_DEFAULT = 0.0;
		static  double CAMERA_Y_D_DEFAULT = 0.0;
		
		static  double CAMERA_RESOLUTION_X = 680.0;
		static  double CAMERA_RESOLUTION_Y = 460.0;
		static  double CAMERA_VIEW_ANGLE = 64.0;
		static  double TARGET_ASPECT_RATIO = 14.0/20.0;
		static  NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
		static  NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
		static  NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
		static  double PARTICLE_AREA_MINIMUM = 0.5;
	}
	public static class Shooter
	{
		static  String ELEVATOR_KEY_P = "Elevator P";
		static  String ELEVATOR_KEY_I = "Elevator I";
		static  String ELEVATOR_KEY_D = "Elevator D";
		
		static  double ELEVATOR_P_DEFAULT = 0.0;
		static  double ELEVATOR_I_DEFAULT = 0.0;
		static  double ELEVATOR_D_DEFAULT = 0.0;

		static  String SHOOTER_KEY_P = "Shooter P";
		static  String SHOOTER_KEY_I = "Shooter I";
		static  String SHOOTER_KEY_D = "Shooter D";
		
		static  double SHOOTER_P_DEFAULT = 0.0;
		static  double SHOOTER_I_DEFAULT = 0.0;
		static  double SHOOTER_D_DEFAULT = 0.0;
	
		static  int RIGHT_FLYWHEEL_CHANNEL = 10;
		static  int LEFT_FLYWHEEL_CHANNEL = 7;
	
		static  int TILT_CHANNEL = 9;
		
		static  int RIGHT_ELEVATOR_CHANNEL = 13;
		static  int LEFT_ELEVATOR_CHANNEL = 12;
		
		static  int FEED_CHANNEL = 11;
		
		static  double FIRE_SPEED = 1.0; //TODO: If speed mode implemented, multiply this by speed scaler
		static  double FEED_SPEED = 0.5;
		
		static  double LOAD_FRONT_SPEED = -0.5; //TODO: If speed mode implemented, multiply this by speed scaler
		//static  double LOAD_BACK_SPEED = 0.5;
		
		static  double ELEVATE_UP_SPEED = 0.7;
		static  double ELEVATE_DOWN_SPEED = -0.7;
		
		static double elevatorPosition = 0.0;
		
		static  double TILT_MAX = 1.0; //TODO: Find maximum tilt distance;
		static  double ELEVATION_MAX = 0.0; //TODO: Find maximum elevation distance
		static double ELEVATION_ERROR = 5.0;
		static double SHOOTER_ERROR = 10.0;
		static double TILT_ERROR = 3.0;
		//Right hand joystick
		static  int TILT_AXIS = 2;
		static  int AIM_BUTTON = 3;
		static  int FIRE_BUTTON = 1;
		static  int LOAD_FRONT_BUTTON = 2;
		//static  int LOAD_BACK_BUTTON = 4;
		static  int ELEVATE_UP_BUTTON = 10;
		static  int ELEVATE_DOWN_BUTTON = 11;
	}
	public static class Climber
	{
		static  int LEFT_CLIMBER_CHANNEL = 14;
		static  int RIGHT_CLIMBER_CHANNEL = 15;
		
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
		static String AUTO_KEY ="Auto enabled";
		static double FORWARD_DISTANCE;
	}
}
