package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.configurations.EskyBot;
import org.baconeers.utils.MovingAverageTimer;
import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.GamePadMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp (group = "Tank Drive")
//@Disabled
public class TankDrive extends FGOpMode {

    private EskyBot robot;
    private GamePadMotor  leftWheel;
    private GamePadMotor  rightWheel;
    private MovingAverageTimer avg;
    private Telemetry.Item avgItem = null;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = EskyBot.newConfig(hardwareMap, telemetry);
        telemetry.setAutoClear(false);
    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        leftWheel = new GamePadMotor(this,  gamepad1, robot.driveLeft, GamePadMotor.Control.LEFT_STICK_Y);
        rightWheel = new GamePadMotor(this,  gamepad1, robot.driveRight, GamePadMotor.Control.RIGHT_STICK_Y);

        avg = new MovingAverageTimer(100);
        if (!telemetry.isAutoClear()) {
            avgItem = telemetry.addData("Avg","%.3f ms",0.0);
        }
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motors with the gamepad joystick values
        leftWheel.update();
        rightWheel.update();

        avg.update();

        if (avgItem != null) {
            avgItem.setValue("%.3f ms",avg.movingAverage());
        }
        else {
            telemetry.addData("Avg", "%5.3f ms", avg.movingAverage());
        }
    }

}
