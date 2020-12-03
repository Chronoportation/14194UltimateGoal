/**
 * @Author: Lanielle
*/

package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import static android.os.SystemClock.sleep;

@TeleOp
@SuppressWarnings({"unused"})
public class FancyShirley extends OpMode
{
   //prepares needed hardware
   private DcMotor driveRF;//drive wheel located RIGHT FRONT
   private DcMotor driveRB;//drive wheel located RIGHT BACK
   private DcMotor driveLF;//drive wheel located LEFT FRONT
   private DcMotor driveLB;//drive wheel located LEFT BACK
   private BNO055IMU imu;//IMU for direction

   //runs once when the user hits the INIT button
   @Override
   public void init()
   {
       {//assigning each variable to its class
       driveRF = hardwareMap.get(DcMotor.class, "driveRF");
       driveRB = hardwareMap.get(DcMotor.class, "driveRB");
       driveLF = hardwareMap.get(DcMotor.class, "driveLF");
       driveLB = hardwareMap.get(DcMotor.class, "driveLB");
       imu = hardwareMap.get(BNO055IMU.class, "imu");}

       {//set all motors to forward, despite orientation
       driveRF.setDirection(DcMotor.Direction.FORWARD);
       driveRB.setDirection(DcMotor.Direction.FORWARD);
       driveLF.setDirection(DcMotor.Direction.REVERSE);
       driveLB.setDirection(DcMotor.Direction.REVERSE);}

       {//reset any encoder values
       driveRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       driveRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       driveLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       driveLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}

       {//give each motor an encoder
       driveRF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       driveRB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       driveLF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       driveLB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);}

       IMUInit();

       telemetry.addData("Status", "Initialized");
       telemetry.update();
   }//ends init method

   @Override//runs repeatedly when the user hits PLAY, until hitting STOP
   public void loop()
   {
       //variables to act as power sources
       double /*x-axis power*/xPow, /*y-axis power*/yPow,/*strafing x-axis power*/rxPow;
       //imu = hardwareMap.get(BNO055IMU.class, "imu");

       //fetch values for power sources depending on how the joysticks are moved
       xPow = -gamepad1.left_stick_x;
       yPow = -gamepad1.left_stick_y;
       rxPow = gamepad1.right_stick_x;

       double orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;;
       double magnitude = Math.sqrt( Math.pow(xPow,2) + Math.pow(yPow,2) );
       double ang = Math.toDegrees(Math.atan( xPow/yPow ));

       ang = 90 - ang; //conversion from normal angles to robot angles
       if(ang<-180)ang+=360;//angle must be>=-180
       if(ang>180)ang-=360;//angle must be<=180

       if(xPow>=0 && yPow<0)ang+=90;//move angle to second quadrant
       else if(xPow<0 && yPow<0)ang+=180;//move angle to third quadrant
       else if(xPow<0 && yPow<=0 )ang+=270;//move angle to fourth quadrant
       ang = ang - orientation;//likely to be an issue

       if(ang<-180)ang+=360;//angle must be>=-180
       if(ang>180)ang-=360;//angle must be<=180

       ang = Math.toRadians(ang);

       xPow = Math.sin(ang)*magnitude;
       yPow = Math.cos(ang)*magnitude;

       driveRF.setPower(yPow + xPow - rxPow);//might be yPow - xPow - rxPow
       driveRB.setPower(yPow - xPow - rxPow);//might be yPow + xPow - rxPow
       driveLF.setPower(yPow - xPow + rxPow);//might be yPow + xPow - rxPow
       driveLB.setPower(yPow + xPow + rxPow);//might be yPow - xPow + rxPow

       telemetry.addData("Status", "Running");
       telemetry.addData("ENCDR-RF", driveRF.getCurrentPosition());
       telemetry.addData("ENCDR-RB", driveRB.getCurrentPosition());
       telemetry.addData("ENCDR-LF", driveLF.getCurrentPosition());
       telemetry.addData("ENCDR-LB", driveLB.getCurrentPosition());
       telemetry.update();
   }//ends loop method

   private boolean IMU_Calibrated() {
       telemetry.addData("IMU Calibration Status", imu.getCalibrationStatus());
       telemetry.addData("Gyro Calibrated", imu.isGyroCalibrated() ? "True" : "False");
       telemetry.addData("System Status", imu.getSystemStatus().toString());
       return imu.isGyroCalibrated();
   }//ends IMU_Calibrated

   private void IMUInit() {//DEFINITELY not borrowed from github
       BNO055IMU.Parameters IMU_Parameters;
       IMU_Parameters = new BNO055IMU.Parameters();
       IMU_Parameters.mode = BNO055IMU.SensorMode.IMU;//Set IMU sensor mode to IMU (uses gyroscope to calculate the orientation of hub)
       IMU_Parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;//may need removal
       imu.initialize(IMU_Parameters);// Initialize the IMU using parameters object.
       telemetry.addData("Status", "IMU initialized, calibration started.");// Report initialization
       telemetry.update();
       sleep(1000);// Wait one second to ensure the IMU is ready.
       while (!IMU_Calibrated()) {// Loop until IMU has been calibrated.
           telemetry.addData("If calibration ", "doesn't complete after 3 seconds, move through 90 degree pitch, roll and yaw motions until calibration complete ");
           telemetry.update();
           sleep(1000);// Wait one second before checking calibration status again.
       }//ends while loop
       telemetry.addData("Status", "Calibration Complete");// Report calibration complete to Driver Station.
       telemetry.addData("Action needed:", "Please press the start triangle");
       telemetry.update();
   }//ends IMUInit
}//ends FancyShirley class
