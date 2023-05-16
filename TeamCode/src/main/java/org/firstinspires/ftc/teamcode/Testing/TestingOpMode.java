package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Control.Hardware.V6Hardware;
import org.firstinspires.ftc.teamcode.Utilities.PID;

@TeleOp (name = "Testing")
public class TestingOpMode extends OpMode {

    V6Hardware hardware;
    PID pid;

    @Override
    public void init() {
        hardware = new V6Hardware(hardwareMap);
        pid = new PID(.001, 0, 0);
        System.out.println("Initialized");
        telemetry.addLine("Initialized");
        telemetry.update();

    }

    @Override
    public void init_loop() {

    }

    @Override
    public void loop() {
        double horizontalEncoderPosition = hardware.horizontalEncoder.getCurrentPosition();

        double motorPower = pid.update(horizontalEncoderPosition, false);

        hardware.rightFront.setPower(motorPower);
        telemetry.addData("Horizontal Odometry Encoder", horizontalEncoderPosition);
        telemetry.update();
        System.out.println("Horizontal Odometry Encoder" + horizontalEncoderPosition);
    }
}
