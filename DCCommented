/**
 * @Author: Georgia Petroff
 * @Project: Comment walkthrough
 *           Used Shirley code as a base example
 * @Start: 11/11/20
 * @Last: 11/11/20
 */

//import all the necessary packages (if you have auto-import on you don't have to worry about typing these things in)
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//Tell the program it's going to be a TeleOp (driver controlled) program
@TeleOp
//This is just to help clear some errors that would otherwise just yell at you
@SuppressWarnings({"unused"})

//this is the class that will actually be running code
//make sure to put the extension otherwise it won't work/compile
public class DCCommented extends OpMode {
    //this is where you're going to be declaring your variables for whatever hardware you need
    //name everything private since you're not using it anywhere else
    //follow the access modifier with the name of the class of the piece of hardware you want
    //then finally give it a good name
    private DcMotor driveRF;//drive wheel located RIGHT FRONT
    private DcMotor driveRB;//drive wheel located RIGHT BACK
    private DcMotor driveLF;//drive wheel located LEFT FRONT
    private DcMotor driveLB;//drive wheel located LEFT BACK

    //don't let the code fight you
    @Override
    //this will run when you hit initialize on the driver station
    public void init()
    {
        //assign each variable to the class that you had declared it as above
        //the name that's in the green quotes is what needs to be put in the configuration otherwise it will throw an error
        //I suggest just making in the same in the code as it will be in the configuration so it's easier to remember
        driveRF = hardwareMap.get(DcMotor.class, "driveRF");
        driveRB = hardwareMap.get(DcMotor.class, "driveRB");
        driveLF = hardwareMap.get(DcMotor.class, "driveLF");
        driveLB = hardwareMap.get(DcMotor.class, "driveLB");

        //set the direction for each motor
        //it should default to clockwise if you don't
        //clockwise
        driveRF.setDirection(DcMotor.Direction.FORWARD);
        driveRB.setDirection(DcMotor.Direction.FORWARD);
        //counter-clockwise
        driveLF.setDirection(DcMotor.Direction.REVERSE);
        driveLB.setDirection(DcMotor.Direction.REVERSE);

        //this is just to reset any encoder values
        driveRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //prepare the encoders for use
        driveRF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //telemetry is just sort of an information and updating thing
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }//ends the series of commands that runs on initialization

    //still no code fighting you
    @Override
    //this will run when you hit the play button on the driver station (it's just an endless loop until you hit stop)
    public void loop()
    {
        //variables declared to act as power sources
        double /*x-axis power*/xPow, /*y-axis power*/yPow,/*strafing x-axis power*/rxPow;

        //if you do gamepad1. you should be able to see a bunch of options for each button/control on the gamepad
        //used gamepad2 for the other controller
        //fetch values for power sources depending on how the joysticks are moved
        xPow = -gamepad1.left_stick_x;
        yPow = -gamepad1.left_stick_y;
        rxPow = gamepad1.right_stick_x;

        //give the motors power based on the joystick input
        //math is math, just trust me
        //with the way we have the wheels, some of these are different than the source
        driveRF.setPower(yPow - xPow - rxPow);
        driveRB.setPower(yPow + xPow - rxPow);
        driveLF.setPower(yPow + xPow + rxPow);
        driveLB.setPower(yPow - xPow + rxPow);

        //again, just information and updates for it
        telemetry.addData("Status", "Running");
        telemetry.addData("ENCDR-RF", driveRF.getCurrentPosition());
        telemetry.addData("ENCDR-RB", driveRB.getCurrentPosition());
        telemetry.addData("ENCDR-LF", driveLF.getCurrentPosition());
        telemetry.addData("ENCDR-LB", driveLB.getCurrentPosition());
        telemetry.update();
    }//ends the loop that runs during play
}//ends the whole class
