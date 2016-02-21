
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorTwoMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.Flywheel;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.actuator.modules.ActuatorSimple;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.lakemonsters2635.sensor.modules.SensorOneShot;
import com.lakemonsters2635.sensor.modules.SensorRawButton;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static org.usfirst.frc.team2635.robot.Constants.Drive.*;
import static org.usfirst.frc.team2635.robot.Constants.Camera.*;
import static org.usfirst.frc.team2635.robot.Constants.Climber.*;
import static org.usfirst.frc.team2635.robot.Constants.Shooter.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
//See Constants.java for constants
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
		CANTalon climberMotor;
		BaseActuator<Double> climber;
		SensorOneShot climbUpOneShot;
		SensorOneShot climbDownOneShot;
	//END CLIMBER VARIABLES
	
	//SHOOTER VARIABLES
		Flywheel flywheel;
		
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		
		CANTalon feedMotor;
		
		CANTalon rightElevatorMotor;
		CANTalon leftElevatorMotor;
		
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
    @Override
	public void robotInit() 
    {
    	//SMART DASHBOARD INIT
    		//PIDPIDPIDPIDPIDPIDPID
	    	SmartDashboard.putNumber(DRIVE_KEY_P, DRIVE_P_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_I, DRIVE_I_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_D, DRIVE_D_DEFAULT);
	    	
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
	    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
	    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
	    	midRightMotor.changeControlMode(TalonControlMode.Follower);
	    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
	    	frontRightMotor.changeControlMode(TalonControlMode.Speed);
	    	frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
	    	
	    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
	    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
	    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
	    	midLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
			frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
	    	frontLeftMotor.changeControlMode(TalonControlMode.Speed);
	    	frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
			robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
	      	
	    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
	    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
	    //END DRIVE INIT
	    	
    	//SHOOTER INIT
	    	rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
	    	//rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	//leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	//feedMotor = new CANTalon(FEED_CHANNEL);
	    	
	    	rightElevatorMotor = new CANTalon(ELEVATOR_RIGHT_CHANNEL);
	    	rightElevatorMotor.changeControlMode(TalonControlMode.Position);
	    	rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
	    	
	    	leftElevatorMotor = new CANTalon(ELEVATOR_LEFT_CHANNEL);
	    	leftElevatorMotor.changeControlMode(TalonControlMode.Follower);
	    	leftElevatorMotor.set(ELEVATOR_RIGHT_CHANNEL);
	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	tiltMotor.changeControlMode(TalonControlMode.Position);
	    	tiltMotor.setPID(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT);
	    	
	    	flywheel = new Flywheel(
	    			new ActuatorTwoMotor(leftFlywheelMotor, rightFlywheelMotor), 
	    			/*new ActuatorSimple(feedMotor), */
	    			new ActuatorTwoMotor(leftFlywheelMotor, rightFlywheelMotor), 
	    			new ActuatorSimple(feedMotor),
	    			new SensorRawButton(LOAD_FRONT_BUTTON, rightJoystick), /**
	    			TODO: Figure out if there are encoders to read to determine whether to feed or not
	    			If there are you can make a class that implements BaseSensor that returns the speed of the flywheels and 
	    			then containing that class in a SensorHitTest, like so:
	    			new SensorHitTest(new SensorCANTalonSpeed(...))
	    			SensorHitTest hasn't been tested yet so if there are issues check that it is working like intended.
	    			If you are able to debug it you will have to make the change in the LakeLib project,
	    			export it to the LakeLib jar, and then restart eclipse so intellisense can catch up.
	    			
	    			**/
	    			new ActuatorSimple(rightElevatorMotor),
	    			new ActuatorSimple(tiltMotor));
	    //END SHOOTER INIT
	    	
	    
	    //CLIMBER INIT
	    	//TODO: Might have to invert the sensor or output of one of these. There are member functions for both in CANTalon
	    	climberMotor = new CANTalon(RIGHT_CLIMBER_CHANNEL);
	    	climberMotor.changeControlMode(TalonControlMode.Position);
	    	climberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
	    	
	    	
	    	climbUpOneShot = new SensorOneShot(false);
	    	climbDownOneShot = new SensorOneShot(false);
	    	
	    	climber = new ActuatorSimple(climberMotor);
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
    @Override
	public void autonomousInit() 
    {
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() 
    {
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    	frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
		tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
		cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
		rightElevatorMotor.setPID(SmartDashboard.getNumber(ELEVATOR_KEY_P), SmartDashboard.getNumber(ELEVATOR_KEY_I), SmartDashboard.getNumber(ELEVATOR_KEY_D));
		climberMotor.setPID(SmartDashboard.getNumber(CLIMBER_KEY_P),SmartDashboard.getNumber(CLIMBER_KEY_I), SmartDashboard.getNumber(CLIMBER_KEY_D));
		
		//TODO: not tfhe most important thing to calibrate, would still be nice
		rightFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
		//leftFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
	    
    }
    //Return y axis angle
    public double cameraTeleop()
    {
    	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
    	//TODO: Tilt angle is currently set to just always be manually set. Change this to 0.0 when Y axis PID is figured out
    	//Transform [-1,1] to [0,1]
    	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_SCALER ;
    	if(findTargetAngle && cameraExists)
    	{
    		flywheel.elevate(ELEVATION_DISTANCE);
    		//May want to wrap this logic into a couple of modules and put them in the PIDDrive class, but not necessary for right now
    		//TODO: Will probably want to add some logic to block untill the flywheel is elevated
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
    			//TODO: Need to find tilt scaler
    			tiltAngle = (angleToTarget.y / CAMERA_VIEW_ANGLE) * TILT_SCALER;
    		}	
    		
        	
    	}
    	else
    	{
    		if(cameraXPID.isEnabled())
    		{
    			//Prevent PID from screwing with normal driving
    			cameraXPID.disable();
    		}

    	}
    	return tiltAngle;
    	
	
    }
    public void shooterTeleop(double tiltAngle)
    {
    	boolean feedFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
    	//boolean feedBack = rightJoystick.getRawButton(LOAD_BACK_BUTTON);
    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
    	
    	if(fire)
    	{

    		//TODO: integrate vision processing, figure out elevateMagnitude
    		flywheel.fire(FIRE_SPEED, ELEVATION_DISTANCE, tiltAngle, FEED_SPEED);
    	}
    	else
    	{
    		flywheel.fire(0.0, 0.0, 0.0, 0.0);
    	}

    	if(feedFront)
    	{
    		flywheel.wheel(LOAD_FRONT_SPEED);
    	}
//    	if(feedBack)
//    	{
//    		flywheel.loadBack(LOAD_BACK_SPEED);
//    	}
    	//
    	if(elevateUp)
    	{
    		flywheel.elevate(ELEVATE_UP_SPEED);
    	}
    	if(elevateDown)
    	{
    		flywheel.elevate(ELEVATE_DOWN_SPEED);
    	}

    }
    public void driveTeleop()
    {
    	double RY = rightJoystick.getRawAxis(RIGHT_Y_AXIS);
    	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);
    	robotDrive.drive(LY, RY);

    }
    public void climberTeleop()
    {
		boolean climbUp = (Boolean) climbUpOneShot.sense(leftJoystick.getRawButton(CLIMB_UP_BUTTON));
		boolean climbDown = (Boolean) climbDownOneShot.sense(leftJoystick.getRawButton(CLIMB_DOWN_BUTTON));

    	//DEBUG
		SmartDashboard.putBoolean("climbUp", climbUp);
		SmartDashboard.putBoolean("climbDown", climbDown);
		//END DEBUG
		//TODO: need to find max climber height
    	if(climbUp && climberIndex < CLIMBER_POSITIONS.length - 1)
    	{
    		climberIndex++;
    	}
    	else if(climbDown && climberIndex > 0)
    	{
    		climberIndex--;
    	}
    }
    @Override
	public void teleopPeriodic() 
    {
    	//DEBUG
    	
    		SmartDashboard.putNumber("Tilt Encoder", tiltMotor.getPosition());
    		SmartDashboard.putNumber("Elevator Encoder", rightElevatorMotor.getPosition());
    		SmartDashboard.putNumber("Unwrapped navx angle", angleUnwrapper.sense(null));
    	//END DEBUG
    		
    		//If aiming isn't enabled, the tilt angle will be the rightJoystick's Z axis
    		//As a result, cameraTeleop must always be run before shooterTeleop
    		double tiltAngle = cameraTeleop();
    		shooterTeleop(tiltAngle);
    		
    		driveTeleop();
    		climberTeleop();

    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() 
    {
    	//Add motors and sensors using the LiveWindow class to test them. ex:
    	//LiveWindow.addActuator("Rear right motor", REAR_RIGHT_CHANNEL, rearRightMotor);
    	//I've never actually tried using the LiveWindow so some fiddling may be required
    }
    
}
