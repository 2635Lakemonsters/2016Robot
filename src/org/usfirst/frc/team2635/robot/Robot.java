
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorTwoMotorInverse;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.Flywheel;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.actuator.modules.ActuatorSimple;
import com.lakemonsters2635.actuator.modules.DriveArcade;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.lakemonsters2635.sensor.modules.SensorDummy;
import com.lakemonsters2635.sensor.modules.SensorOneShot;
import com.lakemonsters2635.sensor.modules.SensorRawButton;
import com.lakemonsters2635.sensor.modules.SensorRawJoystickAxis;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
//CONSTANTS
	//DRIVE CONSTANTS
		final int REAR_RIGHT_CHANNEL = 1;
		final int MID_RIGHT_CHANNEL = 2;
		final int FRONT_RIGHT_CHANNEL = 3;
		
		final int REAR_LEFT_CHANNEL = 4;
		final int MID_LEFT_CHANNEL = 5;
		final int FRONT_LEFT_CHANNEL = 6;
		
		final int JOYSTICK_LEFT_CHANNEL = 1;
		final int JOYSTICK_RIGHT_CHANNEL = 0;
		
		final int RIGHT_Y_AXIS = 1;
		final int LEFT_Y_AXIS = 1;
		
		final String DRIVE_KEY_P = "Drive P";
		final String DRIVE_KEY_I = "Drive I";
		final String DRIVE_KEY_D = "Drive D";
		
		final double DRIVE_P_DEFAULT = 0.0;
		final double DRIVE_I_DEFAULT = 0.0;
		final double DRIVE_D_DEFAULT = 0.0;
		
		double speedModeScaler = 1000.0;
	//END DRIVE CONSTANTS
	
	//CAMERA CONSTANTS
		final String CAMERA_X_KEY_P = "Camera X P";
		final String CAMERA_X_KEY_I = "Camera X I";
		final String CAMERA_X_KEY_D = "Camera X D";
		
		final String CAMERA_Y_KEY_P = "Camera Y P";
		final String CAMERA_Y_KEY_I = "Camera Y I";
		final String CAMERA_Y_KEY_D = "Camera Y D";
		
		double CAMERA_X_P_DEFAULT = 0.02;
		double CAMERA_X_I_DEFAULT = 0.0006;
		double CAMERA_X_D_DEFAULT = 0.0;

		final double CAMERA_Y_P_DEFAULT = 0.0;
		final double CAMERA_Y_I_DEFAULT = 0.0;
		final double CAMERA_Y_D_DEFAULT = 0.0;
		
		final double CAMERA_RESOLUTION_X = 680.0;
		final double CAMERA_RESOLUTION_Y = 460.0;
		final double CAMERA_VIEW_ANGLE = 64.0;
		final double TARGET_ASPECT_RATIO = 14.0/20.0;
		final NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
		final NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
		final NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
		final double PARTICLE_AREA_MINIMUM = 0.5;
	//END CAMERA CONSTANTS
	
	//SHOOTER CONSTANTS
		final String ELEVATOR_KEY_P = "Elevator P";
		final String ELEVATOR_KEY_I = "Elevator I";
		final String ELEVATOR_KEY_D = "Elevator D";
		
		final double ELEVATOR_P_DEFAULT = 0.0;
		final double ELEVATOR_I_DEFAULT = 0.0;
		final double ELEVATOR_D_DEFAULT = 0.0;

		final String SHOOTER_KEY_P = "Shooter P";
		final String SHOOTER_KEY_I = "Shooter I";
		final String SHOOTER_KEY_D = "Shooter D";
		
		final double SHOOTER_P_DEFAULT = 0.0;
		final double SHOOTER_I_DEFAULT = 0.0;
		final double SHOOTER_D_DEFAULT = 0.0;

		
		
		final int RIGHT_FLYWHEEL_CHANNEL = 7;
		final int LEFT_FLYWHEEL_CHANNEL = 8;
		final int FEED_CHANNEL = 9;
		final int TILT_CHANNEL = 10;
		final int ELEVATOR_CHANNEL = 11;
		
		final double FIRE_SPEED = 1.0;
		final double FEED_SPEED = 1.0;
		
		final double TILT_SCALER = 1.0; //TODO: Find maximum tilt distance;
		final double ELEVATION_DISTANCE = 0.0; //TODO: Find maximum elevation distance
		//Right hand joystick
		final int TILT_AXIS = 2;
		final int AIM_BUTTON = 3;
		final int FIRE_BUTTON = 1;
		final int FEED_FORWARD_BUTTON = 2;
		
	
	//END SHOOTER CONSTANTS
	
	//CLIMBER CONSTANTS
		final int LEFT_CLIMBER_CHANNEL = 12;
		final int RIGHT_CLIMBER_CHANNEL = 13;
		
		final String CLIMBER_KEY_P = "Climber P";
		final String CLIMBER_KEY_I = "Climber I";
		final String CLIMBER_KEY_D = "Climber D";
		
		final double CLIMBER_P_DEFAULT = 0.0;
		final double CLIMBER_I_DEFAULT = 0.0;
		final double CLIMBER_D_DEFAULT = 0.0;

		//TODO: find maximum height of climber, make fractions of that height as climber positions
		int climberIndex = 0;
		
		final double CLIMBER_MAX_HEIGHT = 0.0;
		
		//TODO: figure out what fractions of CLIMBER_MAX_HEGIHT are a good idea
		final double[] CLIMBER_POSITIONS = {0.0, CLIMBER_MAX_HEIGHT / 2, CLIMBER_MAX_HEIGHT};
		
		//Left hand joystick
		final int CLIMB_UP_BUTTON = 3;
		final int CLIMB_DOWN_BUTTON = 2;
	//END CLIMBER CONSTANTS
//END CONSTANTS

//VARIABLES
	//DRIVE VARIABLES
		CANTalon rearRightMotor;
		CANTalon midRightMotor;
		CANTalon frontRightMotor;
		
		CANTalon rearLeftMotor;
		CANTalon midLeftMotor;
		CANTalon frontLeftMotor;
		
		DriveThreeMotor robotDrive;
		
		Joystick rightJoystick; //Shooter controls
		Joystick leftJoystick; //Climber controls

	//END DRIVE VARIABLES

	//CLIMBER VARIABLES
		CANTalon rightClimberMotor;
		CANTalon leftClimberMotor;
		BaseActuator<Double> climber;
		SensorOneShot climbUpOneShot;
		SensorOneShot climbDownOneShot;
	//END CLIMBER VARIABLES
	
	//SHOOTER VARIABLES
		Flywheel flywheel;
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		CANTalon feedMotor;
		CANTalon elevatorMotor;
		CANTalon tiltMotor;
		
	//END SHOOTER VARIABLES
	
	//CAMERA VARIABLES
		AHRS navx;
		BaseSensor<NIVision.PointDouble> angleToTargetGrabber;
		SensorUnwrapper angleUnwrapper;
		ImageGrabber camera;
		PIDController cameraXPID;
		boolean cameraExists = false;
	//END CAMERA VARIABLES
		
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	//SMART DASHBOARD INIT
    		//PIDPIDPIDPIDPIDPIDPID
//	    	SmartDashboard.putNumber(DRIVE_KEY_P, DRIVE_P_DEFAULT);
//	    	SmartDashboard.putNumber(DRIVE_KEY_I, DRIVE_I_DEFAULT);
//	    	SmartDashboard.putNumber(DRIVE_KEY_D, DRIVE_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_X_KEY_P, CAMERA_X_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_I, CAMERA_X_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_D, CAMERA_X_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_P, CAMERA_Y_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_I, CAMERA_Y_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_D, CAMERA_Y_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CLIMBER_KEY_P, CLIMBER_P_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_I, CLIMBER_I_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_D, CLIMBER_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(ELEVATOR_KEY_P, ELEVATOR_P_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_I, ELEVATOR_I_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_D, ELEVATOR_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(SHOOTER_KEY_P, SHOOTER_P_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_I, SHOOTER_I_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_D, SHOOTER_D_DEFAULT);
	    //END SMART DASHBOARD INIT
	    //TODO: if the robot throws a null pointer exception, its probably because something didn't get initialized in here! Check the error thrown (RioLog should have it if the driver station doesn't) to get more info

	    //DRIVE INIT
	    	//TODO: might have to invert the output for some of these. There is a function to do that in CANTalon
	    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
	    	//rearRightMotor.changeControlMode(TalonControlMode.Follower);
	    	//rearRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
	    	//midRightMotor.changeControlMode(TalonControlMode.Follower);
	    	//midRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
	    	//frontRightMotor.changeControlMode(TalonControlMode.Speed);
	    	//frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
	    	
	    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
	    	//rearLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	//rearLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
	    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
	    	//midLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	//midLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
			frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
	    	//frontLeftMotor.changeControlMode(TalonControlMode.Speed);
	    	//frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
			robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
	      	
	    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
	    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
	    //END DRIVE INIT
	    	
    	//SHOOTER INIT
	    	rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
	    	//rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	//leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	feedMotor = new CANTalon(FEED_CHANNEL);
	    	
	    	elevatorMotor = new CANTalon(ELEVATOR_CHANNEL);
	    	elevatorMotor.changeControlMode(TalonControlMode.Position);
	    	elevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	tiltMotor.changeControlMode(TalonControlMode.Position);
	    	tiltMotor.setPID(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT);
	    	
	    	flywheel = new Flywheel(
	    			new ActuatorTwoMotorInverse(leftFlywheelMotor, rightFlywheelMotor), 
	    			new ActuatorSimple(feedMotor), 
	    			new ActuatorTwoMotorInverse(leftFlywheelMotor, rightFlywheelMotor), 
	    			new ActuatorSimple(feedMotor),
	    			new SensorRawButton(FEED_FORWARD_BUTTON, rightJoystick), /**
	    			TODO: Figure out if there are encoders to read to determine whether to feed or not
	    			If there are you can make a class that implements BaseSensor that returns the speed of the flywheels and 
	    			then containing that class in a SensorHitTest, like so:
	    			new SensorHitTest(new SensorCANTalonSpeed(...))
	    			SensorHitTest hasn't been tested yet so if there are issues check that it is working like intended.
	    			If you are able to debug it you will have to make the change in the LakeLib project,
	    			export it to the LakeLib jar, and then restart eclipse so intellisense can catch up.
	    			
	    			**/
	    			new ActuatorSimple(elevatorMotor),
	    			new ActuatorSimple(tiltMotor));
	    //END SHOOTER INIT
	    	
	    
	    //CLIMBER INIT
	    	//TODO: Might have to invert the sensor or output of one of these. There are member functions for both in CANTalon
	    	rightClimberMotor = new CANTalon(RIGHT_CLIMBER_CHANNEL);
	    	rightClimberMotor.changeControlMode(TalonControlMode.Position);
	    	rightClimberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
	    	
	    	leftClimberMotor = new CANTalon(LEFT_CLIMBER_CHANNEL);
	    	leftClimberMotor.changeControlMode(TalonControlMode.Position);
	    	leftClimberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
	    	
	    	climbUpOneShot = new SensorOneShot(false);
	    	climbDownOneShot = new SensorOneShot(false);
	    	
	    	climber = new ActuatorTwoMotorInverse(leftClimberMotor, rightClimberMotor);
	    //END CLIMBER INIT
	    
	    //CAMERA INIT
	    	navx = new AHRS(SerialPort.Port.kMXP);
	    	//TODO: The navx is mounted vertically rather than horizontally, so this may not get the angle correctly
	      	angleUnwrapper = new SensorUnwrapper(180.0, new SensorNavxAngle(navx));
	      	try
	      	{
	      		int session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	      		camera = new ImageGrabber(session, true);
	      	
	      		//TODO: may need to add argument and logic to represent the camera view angle in the Y direction, if the camera's veiw angle isn't square. Edit the file in the LakeLib project, re export LakeLib to a jar (select built outputs and compression, write to LakeLib.jar) and restart eclipse if this ends up being true
	      		angleToTargetGrabber = new SensorTargetAngleFromImage(CAMERA_RESOLUTION_X, CAMERA_RESOLUTION_Y, CAMERA_VIEW_ANGLE, TARGET_ASPECT_RATIO, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE, PARTICLE_AREA_MINIMUM);
	      	
	      		cameraExists = true;
	      	}
	      	catch(Exception ex)
	      	{
	      		cameraExists = false;
	      		System.err.println("Camera does not exist!");
	      	}
      		cameraXPID = new PIDController(CAMERA_X_P_DEFAULT, CAMERA_X_I_DEFAULT, CAMERA_X_D_DEFAULT, angleUnwrapper, new PIDOutputThreeMotorRotate(robotDrive));

      	//END CAMERA INIT
    }
    
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() 
    {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	//frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    	//frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
		tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
		cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
		elevatorMotor.setPID(SmartDashboard.getNumber(ELEVATOR_KEY_P), SmartDashboard.getNumber(ELEVATOR_KEY_I), SmartDashboard.getNumber(ELEVATOR_KEY_D));
		//leftClimberMotor.setPID(SmartDashboard.getNumber(CLIMBER_KEY_P),SmartDashboard.getNumber(CLIMBER_KEY_I), SmartDashboard.getNumber(CLIMBER_KEY_D));
		//rightClimberMotor.setPID(SmartDashboard.getNumber(CLIMBER_KEY_P),SmartDashboard.getNumber(CLIMBER_KEY_I), SmartDashboard.getNumber(CLIMBER_KEY_D));
		
		//TODO: not the most important thing to calibrate, would still be nice
		//rightFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
		//leftFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
	    
    }
    public void teleopPeriodic() 
    {
    	//DEBUG
    		boolean climbUp = (Boolean) climbUpOneShot.sense(leftJoystick.getRawButton(CLIMB_UP_BUTTON));
    		boolean climbDown = (Boolean) climbDownOneShot.sense(leftJoystick.getRawButton(CLIMB_DOWN_BUTTON));
    		SmartDashboard.putBoolean("climbUp", climbUp);
    		SmartDashboard.putBoolean("climbDown", climbDown);
    		SmartDashboard.putNumber("Tilt Encoder", tiltMotor.getPosition());
    		SmartDashboard.putNumber("Elevator Encoder", elevatorMotor.getPosition());
    		//SmartDashboard.putNumber("Climber left encoder", leftClimberMotor.getPosition());
    		SmartDashboard.putNumber("Unwrapped navx angle", angleUnwrapper.sense(null));
    	//END DEBUG
        //TELEOP SHOOTER AND CAMERA AND DRIVE
        	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
        	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
        	//TODO: Tilt angle is currently set to just always be manually set. Change this to 0.0 when Y axis PID is figured out
        	//Transform [-1,1] to [0,1]
        	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_SCALER ;
        	if(findTargetAngle && cameraExists)
        	{
        		//May want to wrap this logic into a couple of modules and put them in the PIDDrive class, but not necessary for right now
        		if(!cameraXPID.isEnabled())
        		{
        			
        			NIVision.PointDouble angleToTarget = angleToTargetGrabber.sense(camera.getImage());
        			
        			SmartDashboard.putNumber("Angle to target X", angleToTarget.x);
        			SmartDashboard.putNumber("Angle to target Y", angleToTarget.y);
        			tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
        			cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
        			cameraXPID.setSetpoint(angleToTarget.x);
        			//PID will drive for us
        			cameraXPID.enable();
        			//TODO: need to set tilt to some distance that is probably the ratio between the angle to the target and the veiw angle, multiplied by the maximum tilt distance
        		
        		}	
        		
	        	
        	}
        	else
        	{
        		if(cameraXPID.isEnabled())
        		{
        			//Prevent PID from screwing with normal driving
        			cameraXPID.disable();
        		}
        		tiltAngle = rightJoystick.getRawAxis(TILT_AXIS) * TILT_SCALER; 
        		//Drive normally 
            	//Depending on how the robot is wired, the axes values may have to be inverted
        		//If the joysticks are swapped, swap the order of the argumens in robotDrive.drive . We want to keep the right joystick on the right hand side

            	double RY = rightJoystick.getRawAxis(RIGHT_Y_AXIS);
            	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);
            	robotDrive.drive(LY, RY);

        	}
        	if(fire)
        	{
        		//TODO: integrate vision processing, figure out elevateMagnitude
        		flywheel.fire(FIRE_SPEED, ELEVATION_DISTANCE, tiltAngle, FEED_SPEED);
        	}
        //END TELEOP SHOOTER AND CAMERA AND DRIVE
        
        //TELEOP CLIMBER
        	//TODO:The climber wont go anywhere until the max height of the climber is filled in in the constants section
        
        	if(climbUp && climberIndex < CLIMBER_POSITIONS.length - 1)
        	{
        		climberIndex++;
        	}
        	else if(climbDown && climberIndex > 0)
        	{
        		climberIndex--;
        	}
        	//climber.actuate(CLIMBER_POSITIONS[climberIndex]);
        //END TELEOP CLIMBER

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    	//Add motors and sensors using the LiveWindow class to test them. ex:
    	//LiveWindow.addActuator("Rear right motor", REAR_RIGHT_CHANNEL, rearRightMotor);
    	//I've never actually tried using the LiveWindow so some fiddling may be required
    }
    
}
