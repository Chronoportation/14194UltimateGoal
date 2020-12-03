/**
 * @Author: Georgia Petroff
 * @Project: Basic autonomous to move forward
 * @Start: 11/20/20
 * @Last: 12/03/20
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class LaunchLine_AUTO extends OpMode {
    //prepares needed hardware
    private DcMotor driveRF;//drive wheel located RIGHT FRONT
    private DcMotor driveRB;//drive wheel located RIGHT BACK
    private DcMotor driveLF;//drive wheel located LEFT FRONT
    private DcMotor driveLB;//drive wheel located LEFT BACK

    private DcMotor Shooter;
    private DcMotor mainTreads;
    private DcMotor backTreads;
    private Servo bandHolder;
    private Servo extendContinuous;
    private Servo rotateArm;
    private Servo clampArm;

    @Override
    public void init() {
        //assigning each variable to its class
        driveRF = hardwareMap.get(DcMotor.class, "driveRF");
        driveRB = hardwareMap.get(DcMotor.class, "driveRB");
        driveLF = hardwareMap.get(DcMotor.class, "driveLF");
        driveLB = hardwareMap.get(DcMotor.class, "driveLB");

        //set the direction for each motor
        //clockwise
        driveRF.setDirection(DcMotor.Direction.FORWARD);
        driveRB.setDirection(DcMotor.Direction.FORWARD);

        //cataDoor.setDirection(Servo.Direction.FORWARD);
        //counter-clockwise
        driveLF.setDirection(DcMotor.Direction.REVERSE);
        driveLB.setDirection(DcMotor.Direction.REVERSE);

        //reset any encoder values
        driveRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //give each motor an encoder
        driveRF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Shooter = hardwareMap.get(DcMotor.class, "Shooter");
        mainTreads = hardwareMap.get(DcMotor.class, "mainTreads");
        backTreads = hardwareMap.get(DcMotor.class, "backTreads");
        bandHolder = hardwareMap.get(Servo.class, "bandHolder");
        extendContinuous = hardwareMap.get(Servo.class, "extendContinuous");
        rotateArm = hardwareMap.get(Servo.class, "rotateArm");
        clampArm = hardwareMap.get(Servo.class, "clampArm");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        move(9400.4, 1.0, 0);
    }

    /**
     * Pre: all four drive wheels have been initialized and declared
     * Param: int ticks - the absolute value of amount of ticks that the axel will be spinning per motor
     *        double power - range -1.0 to 1.0 (positive for forward, negative for backward)
     *        int direction - range 0 to 7 (use the directions established below)
     * Post: encoder positions of drive motors
     * Return: none (void)
     *
     *
     * Here's how the directions work according to a clock:
     * 1: 1:30
     * 2: 3:00
     * 3: 4:30
     * 4: 6:00
     * 5: 7:30
     * 6: 9:00
     * 7: 10:30
     * (0) default: 12:00
     *
     * For explanation of code workings, look at the comments on the default case
     */
    private void move(int ticks, double power, int direction)
    {
        switch (direction)
        {
            case 1:
                driveRF.setTargetPosition(ticks);
                driveLB.setTargetPosition(ticks);

                while (driveRF.getCurrentPosition() <= ticks)
                {
                    driveRF.setPower(power);
                    driveRB.setPower(0.0);
                    driveLF.setPower(0.0);
                    driveLB.setPower(power);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 2:
                driveRF.setTargetPosition(ticks);
                driveRB.setTargetPosition(-ticks);
                driveLF.setTargetPosition(-ticks);
                driveLB.setTargetPosition(ticks);

                while (driveRF.getCurrentPosition() <= ticks)
                {
                    driveRF.setPower(power);
                    driveRB.setPower(-power);
                    driveLF.setPower(-power);
                    driveLB.setPower(power);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 3:
                ticks = -ticks;
                driveLF.setTargetPosition(ticks);
                driveRB.setTargetPosition(ticks);

                while (driveLF.getCurrentPosition() >= ticks)
                {
                    driveRF.setPower(0.0);
                    driveRB.setPower(power);
                    driveLF.setPower(power);
                    driveLB.setPower(0.0);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 4:
                ticks = -ticks;

                driveRF.setTargetPosition(ticks);
                driveRB.setTargetPosition(ticks);
                driveLF.setTargetPosition(ticks);
                driveLB.setTargetPosition(ticks);

                while (driveRF.getCurrentPosition() >= ticks)
                {
                    driveRF.setPower(power);
                    driveRB.setPower(power);
                    driveLF.setPower(power);
                    driveLB.setPower(power);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 5:
                ticks = -ticks;
                driveRF.setTargetPosition(ticks);
                driveLB.setTargetPosition(ticks);

                while (driveRF.getCurrentPosition() >= ticks)
                {
                    driveRF.setPower(power);
                    driveRB.setPower(0.0);
                    driveLF.setPower(0.0);
                    driveLB.setPower(power);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 6:
                driveRF.setTargetPosition(-ticks);
                driveRB.setTargetPosition(ticks);
                driveLF.setTargetPosition(ticks);
                driveLB.setTargetPosition(-ticks);

                while (driveRF.getCurrentPosition() >= ticks)
                {
                    driveRF.setPower(-power);
                    driveRB.setPower(power);
                    driveLF.setPower(power);
                    driveLB.setPower(-power);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            case 7:
                driveLF.setTargetPosition(ticks);
                driveRB.setTargetPosition(ticks);

                while (driveLF.getCurrentPosition() <= ticks)
                {
                    driveRF.setPower(0.0);
                    driveRB.setPower(power);
                    driveLF.setPower(power);
                    driveLB.setPower(0.0);
                }

                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
            default:
                //set the position of each motor to the desired amount
                //use math from paper in engineering notebook
                driveRF.setTargetPosition(ticks);
                driveRB.setTargetPosition(ticks);
                driveLF.setTargetPosition(ticks);
                driveLB.setTargetPosition(ticks);

                //run the motors until the correct position is reached
                while (driveRF.getCurrentPosition() <= ticks)
                {
                    driveRF.setPower(power);
                    driveRB.setPower(power);
                    driveLF.setPower(power);
                    driveLB.setPower(power);
                }

                //turn off the motors
                driveRF.setPower(0.0);
                driveRB.setPower(0.0);
                driveLF.setPower(0.0);
                driveLB.setPower(0.0);
                break;
        }
    }
}
