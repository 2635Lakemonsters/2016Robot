package org.usfirst.frc.team2635.components;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A container class that contains all of the things that everything has, such as input devices (Joysticks)
 * @author Tristan
 *
 */
public  class Common  
{
	public Common()
	{
		super();
		rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
		leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
	}
	public static Joystick rightJoystick;
	public static Joystick leftJoystick;
	
	public static int JOYSTICK_LEFT_CHANNEL = 1;
	public static int JOYSTICK_RIGHT_CHANNEL = 0;
	
	public static int RIGHT_Y_AXIS = 1;
	public static int LEFT_Y_AXIS = 1;

	public static int ELEVATE_UP_BUTTON = 11;
	public static int ELEVATE_DOWN_BUTTON = 10;
	
	public static  int CLIMB_UP_BUTTON = 3;
	public static  int CLIMB_DOWN_BUTTON = 2;
	
	public static  int TILT_AXIS = 2;
	public static  int AIM_BUTTON = 3;
	public static  int FIRE_BUTTON = 1;
	public static  int LOAD_FRONT_BUTTON = 2;
	public static int REZERO_BUTTON = 8;	
	public static int REZERO_INTERRUPT_BUTTON = 9;


}
