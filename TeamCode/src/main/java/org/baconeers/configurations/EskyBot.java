package org.baconeers.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstglobal.FgCommon.RobotConfiguration;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * It is assumed that there is a configuration that is currently activated on the robot controller
 * (run menu / Configure Robot ) with the same name as this class.
 * It is also assumed that the device names in the 'init()' method below are the same as the devices
 * named on the activated configuration on the robot.
 */
public class EskyBot extends RobotConfiguration {

    //motors
    public DcMotor driveLeft;
    public DcMotor driveRight;


    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        driveLeft = (DcMotor) getHardwareOn("driveLeft", hardwareMap.dcMotor);
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        driveRight = (DcMotor) getHardwareOn("driveRight", hardwareMap.dcMotor);

    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static EskyBot newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        EskyBot config = new EskyBot();
        config.init(hardwareMap, telemetry);
        return config;

    }


}
