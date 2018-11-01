package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ColorSensorThread;
import org.baconeers.configurations.EskyBot;
import org.baconeers.utils.MovingAverageTimer;
import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.GamePadMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Color Sensor Test", group = "Tests")
//@Disabled
public class ColorSensorTest extends BaconOpMode {

    private EskyBot robot;
    private MovingAverageTimer avg;
    private Telemetry.Item avgItem = null;
    private Telemetry.Item redItem = null;
    private Telemetry.Item greenItem = null;
    private Telemetry.Item blueItem = null;
    private Telemetry.Item alphaItem = null;
    private Telemetry.Item argbItem = null;
    private ColorSensorThread colorSensor = null;



    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = EskyBot.newConfig(hardwareMap, telemetry);
        telemetry.setAutoClear(false);
        robot.colorSensor.enableLed(false);

        colorSensor = new ColorSensorThread(this, robot.colorSensor, 20, 50, TimeUnit.MILLISECONDS);
    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        avg = new MovingAverageTimer(100);
        if (!telemetry.isAutoClear()) {
            avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
            redItem = telemetry.addData("Red", 0);
            greenItem = telemetry.addData("Green", 0);
            blueItem = telemetry.addData("Blue", 0);
            alphaItem = telemetry.addData("Intensity", 0);
            argbItem = telemetry.addData("Hue", 0);
        }

        colorSensor.enableLed(true);
        colorSensor.onStart();
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        avg.update();

        if (avgItem != null) {
            avgItem.setValue("%.3f ms", avg.movingAverage());
        } else {
            telemetry.addData("Avg", "%5.3f ms", avg.movingAverage());
        }

        if (redItem != null) {
            redItem.setValue(colorSensor.red);
            greenItem.setValue(colorSensor.green);
            blueItem.setValue(colorSensor.blue);
            alphaItem.setValue(colorSensor.intensity);
            argbItem.setValue(colorSensor.hue);
        } else {
            telemetry.addData("red", colorSensor.red);
            telemetry.addData("green", colorSensor.green);
            telemetry.addData("blue", colorSensor.blue);
            telemetry.addData("alpha", colorSensor.intensity);
            telemetry.addData("argb", colorSensor.hue);
        }
    }

    protected void onStop() throws InterruptedException {
        super.onStop();
        colorSensor.onStop();
        colorSensor.enableLed(false);
    }

}
