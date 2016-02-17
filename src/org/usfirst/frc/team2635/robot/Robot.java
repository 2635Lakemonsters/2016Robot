
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.composites.PIDDrive;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;
import org.usfirst.team2635.util.AngleUnwrapper;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.actuator.modules.DriveArcade;
import com.lakemonsters2635.actuator.modules.DriveTank;
import com.lakemonsters2635.sensor.modules.SensorRawButton;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	final int REAR_RIGHT_CHANNEL = 3;
	final int FRONT_RIGHT_CHANNEL = 4;
	final int REAR_LEFT_CHANNEL = 1;
	final int FRONT_LEFT_CHANNEL = 2;
	
	final int JOYSTICK_LEFT_CHANNEL = 1;
	final int JOYSTICK_RIGHT_CHANNEL = 0;
	final int RIGHT_Y_AXIS = 1;
	final int LEFT_Y_AXIS = 1;
	final int FIRE_BUTTON = 0;
	
	CANTalon rearRight;
	CANTalon frontRight;
	CANTalon rearLeft;
	CANTalon frontLeft;
	RobotDrive robotDrive;
	
	BaseDrive driveMethod;
	
	Joystick rightJoystick;
	Joystick leftJoystick;
	
	AHRS navx;
	SensorUnwrapper testUnwrapper;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	rearRight = new CANTalon(REAR_RIGHT_CHANNEL);
    	frontRight = new CANTalon(FRONT_RIGHT_CHANNEL);
    	rearLeft = new CANTalon(REAR_LEFT_CHANNEL);
    	frontLeft = new CANTalon(FRONT_LEFT_CHANNEL);
    	
    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
        	
    	robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
      	driveMethod = new DriveArcade(robotDrive);//new PIDDrive(new DriveTank(robotDrive), , new SensorRawButton(FIRE_BUTTON, rightJoystick), pid)
      	navx = new AHRS(SerialPort.Port.kMXP);
      	testUnwrapper = new SensorUnwrapper(360.0, new SensorNavxAngle(navx));

      	
    	
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
    public void teleopPeriodic() 
    {
        double X = -rightJoystick.getRawAxis(0);
        double Y = -rightJoystick.getRawAxis(1);
        
        driveMethod.drive(X, Y);
        SmartDashboard.putNumber("Raw angle", navx.getAngle());
        SmartDashboard.putNumber("Unwrapped angle", testUnwrapper.sense(null));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    
    }
    
}
