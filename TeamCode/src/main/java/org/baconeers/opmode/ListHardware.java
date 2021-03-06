/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.baconeers.utils.MovingAverageTimer;

import java.util.Iterator;
import java.util.Set;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just displays a timer on the Driver Station recording loop count,
 * a moving average loop time, average loop time and total time.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add/remove this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "List Hardware", group = "Tests")
//@Disabled
public class ListHardware extends LinearOpMode {

    @Override
    public void runOpMode() {
        // (driver presses INIT)
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Test", "Loop timer using MovingAverageTimer.averageString()");

        Iterator<HardwareDevice> iter = hardwareMap.iterator();

        while (iter.hasNext())
        {
            HardwareDevice dev = iter.next();
            Set<String> names = hardwareMap.getNamesOf(dev);
            if (names.size() > 0)
            {
                for (String name:names) {
                    String nameStr = String.format("%-20s",name);
                    telemetry.addData(nameStr,dev.getDeviceName());
                }
            }
        }

        telemetry.update();

        // (driver presses PLAY)
        waitForStart();

        // Create a MovingAverageTimer object so that we can time each iteration of the loop
        MovingAverageTimer avg = new MovingAverageTimer();

        // loop until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Update and recalculate the average
            avg.update();

            telemetry.addData("Status", avg.averageString());
            telemetry.update();

            // Allow other threads their fair share of the CPU
            idle();
        }
    }
}
