package org.usfirst.frc.team2635.robot;

import com.ni.vision.NIVision;

public final class Constants
{
	public static class Drive
	{
		public static final int REAR_RIGHT_CHANNEL = 1;
		public static final int MID_RIGHT_CHANNEL = 2;
		public static final int FRONT_RIGHT_CHANNEL = 3;
		
		public static final int REAR_LEFT_CHANNEL = 4;
		public static final int MID_LEFT_CHANNEL = 5;
		public static final int FRONT_LEFT_CHANNEL = 6;
		
		public static final int JOYSTICK_LEFT_CHANNEL = 1;
		public static final int JOYSTICK_RIGHT_CHANNEL = 0;
		
		public static final int RIGHT_Y_AXIS = 1;
		public static final int LEFT_Y_AXIS = 1;
		
		public static final String DRIVE_KEY_P = "Drive P";
		public static final String DRIVE_KEY_I = "Drive I";
		public static final String DRIVE_KEY_D = "Drive D";
		
		public static final double DRIVE_P_DEFAULT = 0.0;
		public static final double DRIVE_I_DEFAULT = 0.0;
		public static final double DRIVE_D_DEFAULT = 0.0;
		
		public double speedModeScaler = 1000.0;
	}
	public static class Camera
	{
		static final String CAMERA_X_KEY_P = "Camera X P";
		static final String CAMERA_X_KEY_I = "Camera X I";
		static final String CAMERA_X_KEY_D = "Camera X D";
		
		static final String CAMERA_Y_KEY_P = "Camera Y P";
		static final String CAMERA_Y_KEY_I = "Camera Y I";
		static final String CAMERA_Y_KEY_D = "Camera Y D";
		
	    static final double CAMERA_X_P_DEFAULT = 0.02;
	    static final double CAMERA_X_I_DEFAULT = 0.0006;
	    static final double CAMERA_X_D_DEFAULT = 0.0;

		static final double CAMERA_Y_P_DEFAULT = 0.0;
		static final double CAMERA_Y_I_DEFAULT = 0.0;
		static final double CAMERA_Y_D_DEFAULT = 0.0;
		
		static final double CAMERA_RESOLUTION_X = 680.0;
		static final double CAMERA_RESOLUTION_Y = 460.0;
		static final double CAMERA_VIEW_ANGLE = 64.0;
		static final double TARGET_ASPECT_RATIO = 14.0/20.0;
		static final NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
		static final NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
		static final NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
		static final double PARTICLE_AREA_MINIMUM = 0.5;
	}
	public static class Shooter
	{
		static final String ELEVATOR_KEY_P = "Elevator P";
		static final String ELEVATOR_KEY_I = "Elevator I";
		static final String ELEVATOR_KEY_D = "Elevator D";
		
		static final double ELEVATOR_P_DEFAULT = 0.0;
		static final double ELEVATOR_I_DEFAULT = 0.0;
		static final double ELEVATOR_D_DEFAULT = 0.0;

		static final String SHOOTER_KEY_P = "Shooter P";
		static final String SHOOTER_KEY_I = "Shooter I";
		static final String SHOOTER_KEY_D = "Shooter D";
		
		static final double SHOOTER_P_DEFAULT = 0.0;
		static final double SHOOTER_I_DEFAULT = 0.0;
		static final double SHOOTER_D_DEFAULT = 0.0;
	
		static final int RIGHT_FLYWHEEL_CHANNEL = 7;
		static final int LEFT_FLYWHEEL_CHANNEL = 8;
		//static final int FEED_CHANNEL = 9;
		static final int TILT_CHANNEL = 10;
		static final int ELEVATOR_RIGHT_CHANNEL = 11;
		static final int ELEVATOR_LEFT_CHANNEL = 14;
		
		static final double FIRE_SPEED = 1.0; //TODO: If speed mode implemented, multiply this by speed scaler
		static final double FEED_SPEED = 1.0;
		static final double LOAD_FRONT_SPEED = -0.5; //TODO: If speed mode implemented, multiply this by speed scaler
		//static final double LOAD_BACK_SPEED = 0.5;
		
		static final double ELEVATE_UP_SPEED = 0.5;
		static final double ELEVATE_DOWN_SPEED = -0.5;
		
		static final double TILT_SCALER = 1.0; //TODO: Find maximum tilt distance;
		static final double ELEVATION_DISTANCE = 0.0; //TODO: Find maximum elevation distance
		//Right hand joystick
		static final int TILT_AXIS = 2;
		static final int AIM_BUTTON = 3;
		static final int FIRE_BUTTON = 1;
		static final int LOAD_FRONT_BUTTON = 2;
		//static final int LOAD_BACK_BUTTON = 4;
		static final int ELEVATE_UP_BUTTON = 10;
		static final int ELEVATE_DOWN_BUTTON = 11;
	}
	public static class Climber
	{
		static final int LEFT_CLIMBER_CHANNEL = 12;
		static final int RIGHT_CLIMBER_CHANNEL = 13;
		
		static final String CLIMBER_KEY_P = "Climber P";
		static final String CLIMBER_KEY_I = "Climber I";
		static final String CLIMBER_KEY_D = "Climber D";
		
		static final double CLIMBER_P_DEFAULT = 0.0;
		static final double CLIMBER_I_DEFAULT = 0.0;
		static final double CLIMBER_D_DEFAULT = 0.0;

		//TODO: find maximum height of climber, make fractions of that height as climber positions
		static int climberIndex = 0;
		
		static final double CLIMBER_MAX_HEIGHT = 0.0;
		
		//TODO: figure out what fractions of CLIMBER_MAX_HEGIHT are a good idea
		static final double[] CLIMBER_POSITIONS = {0.0, CLIMBER_MAX_HEIGHT / 2, CLIMBER_MAX_HEIGHT};
		
		//Left hand joystick
		static final int CLIMB_UP_BUTTON = 3;
		static final int CLIMB_DOWN_BUTTON = 2;

	}
}
