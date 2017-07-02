package org.baconeers.common;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.baconeers.utils.MovingAverageTimer;
import org.firstglobal.FgCommon.ErrorUtil;

/**
 * OpMode Abstract class that offers additional abstraction for opMode developers
 * including catch-all error handling
 */
public abstract class BaconOpMode extends LinearOpMode {


    private boolean operationsCompleted;
    protected MovingAverageTimer movingAverageTimer;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    abstract protected void onInit();


    /**
     * override to this method to perform one time operations after start is pressed
     */
    protected void onStart() throws InterruptedException {
        clearTelemetryData();
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle
     *
     * @throws InterruptedException
     */
    abstract protected void activeLoop() throws InterruptedException;


    /**
     * Override this method only if you need to do something outside of onInit() and activeLoop()
     *
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        try {
            setup();
            onInit();
        } catch (Throwable e) {
            ErrorUtil.handleCatchAllException(e, telemetry);
        }

        waitForStart();

        onStart();

        while (opModeIsActive() && !operationsCompleted) {

            try {
                activeLoop();
            } catch (Throwable e) {
                ErrorUtil.handleCatchAllException(e, telemetry);
            }

            movingAverageTimer.update();
            telemetry.update();
            idle();
        }

        //wait for user to hit stop
        while (opModeIsActive()) {
            idle();
        }
    }

    private void setup() {

        movingAverageTimer = new MovingAverageTimer(100);

    }

    /**
     * Clear data from the telemetry cache
     */
    public void clearTelemetryData() {
        if (telemetry.isAutoClear()) {
            telemetry.clear();
        } else {
            telemetry.clearAll();
        }
        if (opModeIsActive()) {
            idle();
        }
    }

    /**
     * call to prevent leave the loop calling activeLoop()
     */
    protected void setOperationsCompleted() {
        this.operationsCompleted = true;
        telemetry.addData("Opmode Status", "Operations completed");
    }

}