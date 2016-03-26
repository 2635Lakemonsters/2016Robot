
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorTwoMotor;
import org.usfirst.frc.team2635.modules.ActuatorTwoMotorInverse;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.actuator.modules.ActuatorSimple;
import com.lakemonsters2635.sensor.modules.SensorOneShot;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.lakemonsters2635.util.ImageGrabber.ImageMode;
import com.ni.vision.NIVision;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import static org.usfirst.frc.team2635.robot.Constants.Drive.*;
import static org.usfirst.frc.team2635.robot.Constants.Camera.*;
import static org.usfirst.frc.team2635.robot.Constants.Climber.*;
import static org.usfirst.frc.team2635.robot.Constants.Shooter.*;

import java.util.TimerTask;

import static org.usfirst.frc.team2635.robot.Constants.Autonomous.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	//TODO: Make everything fit FunctionalityMode structure
	enum FunctionalityMode
	{
		Encoder, //More position
		VBus, //More vbus
		Autonomous
	}
	FunctionalityMode flywheelMode;
	FunctionalityMode elevatorMode;
	FunctionalityMode tiltMode;
	FunctionalityMode driveMode = FunctionalityMode.VBus;
	
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
	//END CLIMBER VARIABLES
	
	//SHOOTER VARIABLES
		
		DigitalInput leftElevatorLimit;
		DigitalInput rightElevatorLimit;
		BaseActuator<Double> elevateMethod;
		
		DigitalInput tiltLimit;
		
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		BaseActuator<Double> flywheelMethod;
		
		CANTalon feedMotor;
		
		CANTalon rightElevatorMotor;
		CANTalon leftElevatorMotor;
		
		SensorOneShot elevateOneShot;
		
		Encoder tiltEncoder;
		PIDController tiltPID;
		CANTalon tiltMotor;
		
	//END SHOOTER VARIABLES
	//CAMERA VARIABLES
		AHRS navx;
		SensorTargetAngleFromImage angleToTargetGrabber;
		SensorUnwrapper angleUnwrapper;
		ImageGrabber camera;
		PIDController cameraXPID;
		USBCamera cam0;
		boolean cameraExists = false;
		
	//END CAMERA VARIABLES
		
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void driveConfigEncoder()
	{
		//TODO: Middle motor is driver
		//TODO: Uses RobotDrive instead of threeMotor
		driveMode = FunctionalityMode.Encoder;
    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	midRightMotor.changeControlMode(TalonControlMode.Speed);
    	midRightMotor.reverseSensor(true);
    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	frontRightMotor.changeControlMode(TalonControlMode.Follower);
    	frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
    	
    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	midLeftMotor.changeControlMode(TalonControlMode.Speed);
    	midLeftMotor.reverseSensor(true);
    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	frontLeftMotor.changeControlMode(TalonControlMode.Follower);
    	frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);

	}
	public void driveConfigPosition()
	{
		driveMode = FunctionalityMode.Autonomous;
    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	midRightMotor.changeControlMode(TalonControlMode.Position);
    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	frontRightMotor.changeControlMode(TalonControlMode.Follower);
    	frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
    	
    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	midLeftMotor.changeControlMode(TalonControlMode.Position);
    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	frontLeftMotor.changeControlMode(TalonControlMode.Follower);
    	frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);

	}
	public void driveConfigVbus()
	{
		driveMode = FunctionalityMode.VBus;
    	rearRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	midRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	rearLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	midLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);

	}
	public void driveInit(FunctionalityMode setupMode)
	{
    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);	    	
    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
    	
    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
		frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);	
		
		robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
		
		rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
		
    	//Encoder functionality hasn't been set up for drive, so only have it be set to vbus.
    	driveConfigVbus();

	}
	public void flywheelConfigVbus()
	{
		flywheelMode = FunctionalityMode.VBus;
		
		FIRE_SPEED = 1.0;
		FEED_SPEED = -FIRE_SPEED/ 2.5;
		
	  	feedMotor.changeControlMode(TalonControlMode.PercentVbus);
    	rightFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
    	leftFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	public void elevatorConfigVbus()
	{
		elevatorMode = FunctionalityMode.VBus;
		
		//Elevator is - going up, ONLY ON PRACTICE ROBOT
		ELEVATE_UP_SPEED = -1.0;
		ELEVATE_DOWN_SPEED = 0.7;
    	
		rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
    	leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	public void tiltConfigVbus()
	{
		tiltMode = FunctionalityMode.VBus;
    	tiltPID.disable();
	}
	public void shooterAssemblyConfigVbus()
	{
    	flywheelConfigVbus();
    	elevatorConfigVbus();
    	tiltConfigVbus();
	}
	
	public void flywheelConfigEncoder()
	{
		flywheelMode = FunctionalityMode.Encoder;
		FIRE_SPEED = -55000.0; //Competition: -50000.0, Practice: 50000.0
		FEED_SPEED = -FIRE_SPEED / 1.5;

		//On practice bot, leftFlywheelMotor has its sensor reversed. On competition, both are reversed.
		rightFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
    	rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
    	rightFlywheelMotor.enableBrakeMode(false);
    	//rightFlywheelMotor.reverseSensor(true);
	
    	leftFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
    	leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
    	leftFlywheelMotor.enableBrakeMode(false);
    	leftFlywheelMotor.reverseSensor(true);

	}
	public void elevatorConfigEncoder(boolean zero)
	{
		elevatorMode = FunctionalityMode.Encoder;
		//1 encoder tick per 20ms
		ELEVATE_DOWN_SPEED = -1000.0;
		ELEVATE_UP_SPEED = 1000.0;
		
		//Output is inversed for elevator motors ONLY ON PRACTICE ROBOT
		
    	rightElevatorMotor.changeControlMode(TalonControlMode.Position);
    	rightElevatorMotor.reverseSensor(true);
    	rightElevatorMotor.reverseOutput(true);
    	rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
    	
    	leftElevatorMotor.changeControlMode(TalonControlMode.Position);
    	leftElevatorMotor.reverseSensor(true);
    	leftElevatorMotor.reverseOutput(true);
    	leftElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
    	if(zero)
    	{
			rightElevatorMotor.setPosition(0.0);
    		leftElevatorMotor.setPosition(0.0);
    	}
	}
	public void tiltConfigEncoder(boolean zero)
	{
		tiltMode = FunctionalityMode.Encoder;
		if(zero)
		{
			tiltEncoder.reset();
		}
		tiltPID.enable();
	}
	public void shooterAssemblyConfigEncoder(boolean rezeroEncoders)
	{  		
    		flywheelConfigEncoder();
    		
    		
    		elevatorConfigEncoder(rezeroEncoders);
    		tiltConfigEncoder(rezeroEncoders);
	}
	
    public void shooterInit(FunctionalityMode setupMode)
    {
    	//NOTE: Right and left limit are true when open
    	leftElevatorLimit = new DigitalInput(LEFT_ELEVATOR_LIMIT_CHANNEL);
    	rightElevatorLimit = new DigitalInput(RIGHT_ELEVATOR_LIMIT_CHANNEL);
    	tiltLimit = new DigitalInput(TILT_LIMIT_CHANNEL);
    	
    		
    	elevateOneShot = new SensorOneShot(false);
    	
		rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);		
    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
    	flywheelMethod = new ActuatorTwoMotorInverse(leftFlywheelMotor, rightFlywheelMotor);
    	
    	rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
    	leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);
    	elevateMethod = new ActuatorTwoMotor(leftElevatorMotor, rightElevatorMotor);
    	
    	tiltMotor = new CANTalon(TILT_CHANNEL);
    	tiltEncoder = new Encoder(TILT_ENCODER_A, TILT_ENCODER_B);
		tiltPID = new PIDController(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT, tiltEncoder, tiltMotor);
    	feedMotor = new CANTalon(FEED_CHANNEL);
    	if(setupMode == FunctionalityMode.Encoder)
    	{
    		shooterAssemblyConfigEncoder(true);
    	}   
    	else if(setupMode == FunctionalityMode.VBus)
    	{
    		shooterAssemblyConfigVbus();
    	}

    }
	public void climberInit()
	{		
		climberMotor = new CANTalon(CLIMBER_CHANNEL);
		climber = new ActuatorSimple(climberMotor);    	
	}
	//This should be run periodically
	public class TryCameraInit extends TimerTask
	{

		@Override
		public void run()
		{
			try
	      	{
	      		//use cam0 to change properties of the camera such as FPS, exposure, etc.
	    		USBCamera cam0 = new USBCamera();
	    		cam0.setExposureManual(50);
	    		cam0.closeCamera();
	      		int session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	      		camera = new ImageGrabber(session, true, true, ImageMode.NORMAL, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE);
	//TODO: may need to add argument and logic to represent the camera view angle in the Y direction, if the camera's veiw angle isn't square. Edit the file in the LakeLib project, re export LakeLib to a jar (select built outputs and compression, write to LakeLib.jar) and restart eclipse if this ends up being true
	      		angleToTargetGrabber = new SensorTargetAngleFromImage(CAMERA_RESOLUTION_X, CAMERA_RESOLUTION_Y, CAMERA_VIEW_ANGLE, TARGET_ASPECT_RATIO, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE, PARTICLE_AREA_MINIMUM);
	      	
	        	
	      		cameraExists = true;
	      	}
	      	catch(Exception ex)
	      	{
	      		cameraExists = false;
	      		System.err.println("Camera does not exist!");
	      		ex.printStackTrace();
	      	}
		}
	}
	public class CameraInitChecker extends Thread
	{
		java.util.Timer initTimer = new java.util.Timer();
		@Override
		public void run()
		{
			initTimer.scheduleAtFixedRate(new TryCameraInit(), 0, 500);

			while(cameraExists != true)
			{
			}
			initTimer.cancel();
		}
	}
	public void cameraInit()
	{
    	navx = new AHRS(SerialPort.Port.kMXP);
    	CameraInitChecker initThread = new CameraInitChecker();
    	initThread.start();
    	//TODO: The navx is mounted vertically rather than horizontally, so this may not get the angle correctly
      	angleUnwrapper = new SensorUnwrapper(180.0, new SensorNavxAngle(navx));
      	
  		cameraXPID = new PIDController(CAMERA_X_P_DEFAULT, CAMERA_X_I_DEFAULT, CAMERA_X_D_DEFAULT, angleUnwrapper, new PIDOutputThreeMotorRotate(robotDrive));

	}
	public void smartDashboardInit()
	{
		
		NetworkTable.flush();
		SmartDashboard.putBoolean(AUTO_KEY, true);
		SmartDashboard.putBoolean(REZERO_KEY, false);
		
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
    	
    	SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_SPEED);
	

	}
    @Override
	public void robotInit() 
    {
    	
	    //if the robot throws a null pointer exception, its probably because something didn't get initialized in here! Check the error thrown (RioLog should have it if the driver station doesn't) to get more info
	    smartDashboardInit();
    	driveInit(driveMode);	
	    shooterInit(FunctionalityMode.Encoder);
	    climberInit();
	    cameraInit();
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
    
    Timer autoTimer = new Timer();
    double autoStartTime;
    boolean doAuto = true;
    @Override
    
	public void autonomousInit() 
    {
    	
    	doAuto  &= rezeroTilt();
		Timer timer = new Timer();
		timer.start();
		double startTime = timer.get();
		while(timer.get() - startTime < 0.3){}
		doAuto &= rezeroElevator();
		
		autoTimer.reset();
		autoTimer.start();
		autoStartTime = autoTimer.get();
		robotDrive.drive(1.0, -1.0);
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() 
    {
    	if(autoTimer.get() > 4.0 || !doAuto)
    	{
    		robotDrive.drive(0, 0);
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	midRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    	leftFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));

	    
    }
    /**
     * 
     * @return Angle for tilter
     */
    @Deprecated
    public double cameraTeleop(FunctionalityMode teleopMode)
    {
    	if(teleopMode == FunctionalityMode.Encoder)
    	{
	    	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
	    	//TODO: Tilt angle is currently set to just always be manually set
	    	//Transform [-1,1] to [0,1]
	    	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
	    	if(findTargetAngle && cameraExists)
	    	{
	    		//Need to find angle based upon a fully elevated shooter.
	    		//TODO: May want to wrap this logic into a couple of modules and put them in the PIDDrive class. Do this after basic functionality is established
	    		//TODO: Will probably want to add some logic to block untill the flywheel is elevated
	    		if(!cameraXPID.isEnabled())
	    		{
	    			
	    			NIVision.PointDouble angleToTarget = angleToTargetGrabber.sense(camera.getImage());
	    			
	    			SmartDashboard.putNumber("Angle to target X", angleToTarget.x);
	    			SmartDashboard.putNumber("Angle to target Y", angleToTarget.y);
	    			//tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
	    			cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
	    			cameraXPID.setSetpoint(angleToTarget.x);
	    			//PID will drive for us
	    			cameraXPID.enable();
	    			//Tilt angle is ratio between the angle to the target and the maximum angle the camera can see, scaled by the maximum tilt
	    			//TODO: Need to find TILT_MAX
	    			tiltAngle = (angleToTarget.y / CAMERA_VIEW_ANGLE) * TILT_MAX;
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
    	else
    	{
	    	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
	    	return tiltAngle;
    	}
    }
    /**
     * 
     * @param tiltAngle Angle to tilt the shooter to. This should be calculated in cameraTeleop()
     * @param teleopMode
     */
    public boolean rezeroTilt()
	{
		
		tiltConfigVbus();
		tiltMotor.set(REZERO_SPEED);
		Timer timer = new Timer();
		timer.reset();
		timer.start();

		while(true)
		{
			
			if(leftJoystick.getRawButton(REZERO_INTERRUPT_BUTTON) || timer.hasPeriodPassed(2.0))
			{
				//tilt will be in vbus
				tiltMotor.set(0.0);
				return false;
			}
			boolean limitHit = !tiltLimit.get();
			if(limitHit)
			{
				tiltMotor.set(0.0);
				tiltEncoder.reset();
				tiltConfigEncoder(true);
				tiltPID.setSetpoint(-100.0); //Competition = -100, Practice = 100
				return true;
			}
		}
	}
	public boolean rezeroElevator()
	{
		
		elevatorConfigVbus();
		Timer timer = new Timer();		
		timer.reset();
		timer.start();
		rightElevatorMotor.set(REZERO_SPEED);
		leftElevatorMotor.set(REZERO_SPEED);

		while(true)
		{
			//TODO: add in time limit for autonomous
			//If the limit switches break, the robot could enter a broken state, so be able to exit rezeroing.
			if(leftJoystick.getRawButton(REZERO_INTERRUPT_BUTTON)|| timer.hasPeriodPassed(2.0))
			{
				rightElevatorMotor.set(0.0);
				leftElevatorMotor.set(0.0);
				//Robot is in Vbus mode now.
				return false;
			}
			boolean leftLimitHit = !leftElevatorLimit.get();
			boolean rightLimitHit = !rightElevatorLimit.get();
			if(rightLimitHit || leftLimitHit)
			{
				rightElevatorMotor.set(0.0);
				rightElevatorMotor.setPosition(0.0);
				
				leftElevatorMotor.set(0.0);
				leftElevatorMotor.setPosition(0.0);
				elevatorPosition = 0.0;
				elevatorState = false;
				break;
			}
			
		}
		elevatorConfigEncoder(true);
		return true;
	}
    public void rezeroTiltAndElevator()
    {
    	rezeroTilt();
		Timer timer = new Timer();
		timer.start();
		double startTime = timer.get();
		while(timer.get() - startTime < 0.3){}
		rezeroElevator();
    }
    Timer fireTimeout = new Timer();
    boolean timerSet = false;
    public void checkFlywheelFault()
    {
    	//The flywheel takes a moment to rev up, so give it a second before declaring a fault
		if((Math.abs(rightFlywheelMotor.getSpeed()) < 0.5 || Math.abs(leftFlywheelMotor.getSpeed()) < 0.5) && timerSet)
		{
			if(fireTimeout.hasPeriodPassed(1.0))
			{
				shooterFault = true;
				timerSet = false;
			}
		}
		else
		{
			
			timerSet = false;
			fireTimeout.stop();
		}
		if((Math.abs(rightFlywheelMotor.getSpeed()) < 0.5 || Math.abs(leftFlywheelMotor.getSpeed()) < 0.5) && !timerSet)
		{
			fireTimeout.reset();
			fireTimeout.start();
			timerSet = true;
		}
		else
		{
			timerSet = false;
			fireTimeout.stop();
		}
    }
    boolean feedMotorSet = false;
    public void flywheelTeleop(FunctionalityMode mode)
    {
    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
		boolean loadFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
		
    	if(fire)
    	{
    		
    		flywheelMethod.actuate(FIRE_SPEED);
    		if(mode == FunctionalityMode.Encoder)
    		{
    		
    			checkFlywheelFault();
    			
    			double averageError = (Math.abs(rightFlywheelMotor.getError()) + Math.abs(leftFlywheelMotor.getError())) / 2.0;
    			double averageSpeed = (Math.abs(rightFlywheelMotor.getSpeed()) + Math.abs(leftFlywheelMotor.getSpeed())) / 2.0;
    			boolean readyToFire = averageError < SHOOTER_ERROR && averageSpeed > Math.abs(FIRE_SPEED) - SHOOTER_ERROR;

    			if(readyToFire || feedMotorSet)
    			{
    				feedMotor.set(1.0);
    				feedMotorSet = true;
    			}
    			else
    			{
    				feedMotor.set(0.0);
    			}
    		}
    		else if(mode == FunctionalityMode.VBus)
    		{
    			feedMotor.set(1.0);
    		}
    	}
    	else if(loadFront)
    	{
    		
    		if(mode == FunctionalityMode.Encoder)
    		{
    			checkFlywheelFault();
    		}
    		flywheelMethod.actuate(FEED_SPEED);
    		feedMotor.set(-1.0);
    	}
    	else
    	{	
    		rightFlywheelMotor.ClearIaccum();
    		leftFlywheelMotor.ClearIaccum();
    		feedMotorSet = false;
    		
    		flywheelMethod.actuate(0.0);
    		feedMotor.set(0.0);
    	}
    	
    }
    public void elevatorTeleop(FunctionalityMode mode)
    {
    	boolean rightElevatorLimitHit = !rightElevatorLimit.get();
    	boolean leftElevatorLimitHit = !leftElevatorLimit.get();
    	if (rightElevatorLimitHit || leftElevatorLimitHit)
    	{
    		leftElevatorMotor.setPosition(0.0);
    		rightElevatorMotor.setPosition(0.0);
    		
    	}
    	
    	
    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
    	boolean aim = (boolean) elevateOneShot.sense(rightJoystick.getRawButton(AIM_BUTTON));
    	boolean start = leftJoystick.getRawButton(STARTING_BUTTON);
    	boolean climbing = leftJoystick.getRawButton(CLIMB_UP_BUTTON) || leftJoystick.getRawButton(CLIMB_DOWN_BUTTON);
    	
    	if(mode == FunctionalityMode.Encoder)
    	{
			if(elevatorPosition > 0)
			{
				//If the left or right elevator isn't returning the correct distance
				if(!(rightElevatorMotor.getPosition() > 0.5)|| !(leftElevatorMotor.getPosition() > 0.5))
				{
					elevatorFault = true;
				}
				else
				{
					elevatorFault = false;
				}
			}
			if(elevateUp && elevatorPosition < ELEVATION_MAX)
			{
				elevatorPosition += ELEVATE_UP_SPEED;
			}
			else if(elevateDown && elevatorPosition > 0)
			{
				elevatorPosition += ELEVATE_DOWN_SPEED;
			}
			else if(start || climbing)
			{
				elevatorPosition = ELEVATION_START;
			}
			if(aim)
			{
				if(elevatorState == true)
				{
					elevatorPosition = 0;
					elevatorState = false;
					rezeroTiltAndElevator();
				}
				else
				{
	
					elevatorPosition = ELEVATION_MAX;
					elevatorState = true;
				}
			}
			elevateMethod.actuate(elevatorPosition);
    	}
    	else if(mode == FunctionalityMode.VBus)
    	{
    		if(elevateUp)
	    	{
    			elevateMethod.actuate(ELEVATE_UP_SPEED);
	    	}
    		else if(elevateDown)
	    	{
	    		elevateMethod.actuate(ELEVATE_DOWN_SPEED);
	    	}
	    	else
	    	{
	    		elevateMethod.actuate(0.0);
	    	}
    	}
    }
    boolean aimed = false;
    double angleToTargetYSetpoint;
    public void tiltTeleop(FunctionalityMode mode)
    {
    	//boolean leftElevatorLimitHit = !leftElevatorLimit.get();
    	boolean tiltLimitHit = !tiltLimit.get();
    
    	if(tiltLimitHit)
    	{
    		tiltEncoder.reset();
    	}
    	if(mode == FunctionalityMode.Encoder)
    	{
    		//TODO: Camera aiming
    		boolean aimCamera = rightJoystick.getRawButton(AIM_CAMERA_BUTTON);
    	 	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
    	 	if(aimCamera && cameraSetpoints != null)
    	 	{
    	 		if(!aimed)
    	 		{
	    	 		angleToTargetYSetpoint = tiltEncoder.getDistance() + (cameraSetpoints.y - TILT_PIXEL_SETPOINT) * PIXEL_TO_TILT;     	 		
	    	 		aimed = true;
    	 		}
    	 		else
    	 		{
    	 			tiltPID.setSetpoint(angleToTargetYSetpoint);
    	 		}
    	 	}
    	 	else
    	 	{
    	 		aimed = false;
	    	 	if(rightElevatorMotor.getPosition() > ELEVATION_ABOVE_CHASSIS || leftElevatorMotor.getPosition() > ELEVATION_ABOVE_CHASSIS)
	    	 	{
	    	 		tiltPID.setSetpoint(tiltAngle);
	    	 	}
	    	 	else
	    	 	{
	    	 		tiltPID.setSetpoint(0.0);
	    	 	}
    	 	}
    	}
    	else if(mode == FunctionalityMode.VBus)
    	{
	    	boolean tiltUp = rightJoystick.getRawButton(6);
	    	boolean tiltDown = rightJoystick.getRawButton(7);
	    	//Inverse these numbers for competition bot
	    	if(tiltUp)
	    	{
	    		tiltMotor.set(-1.0);
	    	}
	    	else if(tiltDown)
	    	{
	    		tiltMotor.set(0.5);
	    	}
	    	else
	    	{
	    		tiltMotor.set(0.0);
	    	}
    	}
    }

    public void shooterTeleop()
    {
    	if(leftJoystick.getRawButton(REZERO_BUTTON))
    	{
    		rezeroTiltAndElevator();
    	}
    	flywheelTeleop(flywheelMode);
    	elevatorTeleop(elevatorMode);
    	tiltTeleop(tiltMode);
		
    }
    boolean prevValue = false;
    public void driveTeleop(FunctionalityMode mode)
    {
    	double RY = rightJoystick.getRawAxis(RIGHT_Y_AXIS);
    	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);
    	boolean aimCamera = rightJoystick.getRawButton(AIM_CAMERA_BUTTON);
    	
    	if(aimCamera && cameraSetpoints != null)
    	{
    		if(!cameraXPID.isEnabled())
    		{
    			double setPoint =  angleUnwrapper.sense(null) + cameraSetpoints.x; 
				SmartDashboard.putNumber("X setpoint", setPoint);
				cameraXPID.enable();
				cameraXPID.setSetpoint(setPoint);
    		}
    	}
    	else
    	{
    		if(cameraXPID.isEnabled())
    		{
    			cameraXPID.disable();
    		}
    	}
    
    	robotDrive.drive(LY, RY);
    }
    public void climberTeleop()
    {	
		boolean climbUp = leftJoystick.getRawButton(CLIMB_UP_BUTTON);
		boolean climbDown = leftJoystick.getRawButton(CLIMB_DOWN_BUTTON);
		double speed = ((-leftJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
		if(climbUp)
		{
			climber.actuate(speed);
		}
		else if(climbDown)
		{
			climber.actuate(-speed);
		}
		else
		{
			climber.actuate(0.0);
		}
	
    }
    boolean previousCameraMode = false;
    @Override
	public void teleopPeriodic() 
    {
    	//DEBUG
    	
    		SmartDashboard.putNumber("Tilt Encoder", tiltEncoder.getDistance()); //TODO: Max Tilt:
    		SmartDashboard.putNumber("Elevator Encoder Right", rightElevatorMotor.getPosition()); //TODO: Max Elevation:
    		SmartDashboard.putNumber("Elevator encoder left", leftElevatorMotor.getPosition());
    		SmartDashboard.putNumber("Right shooter speed", rightFlywheelMotor.getSpeed());
    		SmartDashboard.putNumber("Left shooter speed", leftFlywheelMotor.getSpeed());    		
    		
    		SmartDashboard.putNumber("Right drive speed", midRightMotor.getSpeed());
    		SmartDashboard.putNumber("Left drive speed", midLeftMotor.getSpeed());
    		
    		SmartDashboard.putBoolean("Left limit", leftElevatorLimit.get());
    		SmartDashboard.putBoolean("Right limit", rightElevatorLimit.get());
    		SmartDashboard.putBoolean("Tilt limit", tiltLimit.get());

    		SmartDashboard.putNumber("Angle", angleUnwrapper.sense(null));
    		SmartDashboard.putNumber("Raw Angle", navx.getAngle());
    		SmartDashboard.putNumber(ELEVATION_KEY, (rightElevatorMotor.getPosition() + leftElevatorMotor.getPosition()) / 2);
	    	SmartDashboard.putNumber(TILT_KEY, tiltEncoder.getDistance());
	    	SmartDashboard.putNumber(SHOOTER_SPEED_KEY, (Math.abs(rightFlywheelMotor.getSpeed()) + Math.abs(leftFlywheelMotor.getSpeed())) / 2);
	    
    	//END DEBUG
	    	
	    	//Prevent lag issues (multithreading?)
	    	if(SmartDashboard.getBoolean("Push"))
	    	{
		    	boolean currentCameraMode = SmartDashboard.getBoolean(CAMERA_MODE_KEY);
		    	
	    		if(currentCameraMode)
	    		{
	    			camera.setMode(ImageMode.COLOR_DETECT);
	    		}
	    		else
	    		{
	    			camera.setMode(ImageMode.NORMAL);
	    		}
	    	
		    	previousCameraMode = currentCameraMode;
		    	TARGET_HUE_RANGE.maxValue =(int) SmartDashboard.getNumber(HUE_MAX_KEY);
		    	TARGET_HUE_RANGE.minValue = (int) SmartDashboard.getNumber(HUE_MIN_KEY);
		    	TARGET_SATURATION_RANGE.maxValue = (int) SmartDashboard.getNumber(SAT_MAX_KEY);
		    	TARGET_SATURATION_RANGE.minValue = (int) SmartDashboard.getNumber(SAT_MIN_KEY);
		    	TARGET_VALUE_RANGE.maxValue = (int) SmartDashboard.getNumber(VAL_MAX_KEY);
		    	TARGET_VALUE_RANGE.minValue = (int) SmartDashboard.getNumber(VAL_MIN_KEY);
		    		
		    	camera.setHueRange(TARGET_HUE_RANGE);
		    	camera.setSatRange(TARGET_SATURATION_RANGE);
		    	camera.setValRange(TARGET_VALUE_RANGE);
		    	
		    	angleToTargetGrabber.HUE_RANGE = TARGET_HUE_RANGE;
		    	angleToTargetGrabber.SAT_RANGE = TARGET_SATURATION_RANGE;
		    	angleToTargetGrabber.VAL_RANGE = TARGET_VALUE_RANGE;
	    	}	
    		if(rightJoystick.getRawButton(VOLTAGE_MODE_BUTTON))
    		{
    			shooterAssemblyConfigVbus();
    			SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_VBUS);
    			
    		}
    		else if(rightJoystick.getRawButton(SPEED_MODE_BUTTON))
    		{
    			
    			shooterAssemblyConfigEncoder(true);
    			SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_SPEED);
    		}
    		
    		//Get a common set of setpoints for drive and tilt
    		if(rightJoystick.getRawButton(AIM_CAMERA_BUTTON))
    		{
    			cameraSetpoints = angleToTargetGrabber.sense(camera.getImage());
    		}
    		else
    		{
    			//Functions should null check to make sure the cameraSetpoints are calculated before attempting to caculate setpoints
    			cameraSetpoints = null;
    		}
    		//If aiming isn't enabled, the tilt angle will be the rightJoystick's Z axis
    		//As a result, cameraTeleop must always be run before shooterTeleop
    		
    		shooterTeleop();
    		SmartDashboard.putBoolean(SHOOTER_FAULT_KEY, shooterFault);
    		SmartDashboard.putBoolean(ELEVATOR_FAULT_KEY, elevatorFault);
    		SmartDashboard.putBoolean(TILT_FAULT_KEY, tiltFault);
    		driveTeleop(driveMode);
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
