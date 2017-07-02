package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.GamePadSteerDrive;
import org.baconeers.configurations.EskyBot;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(group = "Steer Drive")
//@Disabled
public class SteerDrive extends BaconOpMode {

    private EskyBot robot;
    private GamePadSteerDrive drive;
    private Telemetry.Item avgItem;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = EskyBot.newConfig(hardwareMap, telemetry);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        drive = new GamePadSteerDrive(this, gamepad1, robot.driveLeft, robot.driveRight);
        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the drive motors with the gamepad  values
        drive.update();

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());

    }

}
