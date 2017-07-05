package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconComponent;
import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ButtonControl;
import org.baconeers.common.GamePadSafeMotor;
import org.baconeers.common.GamePadToggleMotor;
import org.baconeers.configurations.EskyBot;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(group = "Toggle Motor")
//@Disabled
public class ToggleMotor extends BaconOpMode {

    private EskyBot robot;
    private GamePadToggleMotor left;
    private GamePadSafeMotor right;
    private Telemetry.Item avgItem;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = EskyBot.newConfig(hardwareMap, telemetry);

        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
        avgItem.setRetained(true);

        left = new GamePadToggleMotor(this,gamepad1,robot.driveLeft, ButtonControl.A, 0.5f);
        right = new GamePadSafeMotor(this,gamepad1,robot.driveRight, ButtonControl.B, ButtonControl.Y, 0.5f,false);
    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        // Update the drive motors with the gamepad  values
        left.update();
        right.update();

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());
    }

}
