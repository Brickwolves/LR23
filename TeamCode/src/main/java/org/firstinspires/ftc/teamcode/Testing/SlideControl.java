package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Control.Hardware.V6Hardware;
import org.firstinspires.ftc.teamcode.Utilities.PID;

public class SlideControl {

    DcMotor liftLeft;
    DcMotor liftRight;
    PID pid;
    public SlideControl(V6Hardware hardware){
        liftLeft = hardware.liftLeft;
        liftRight = hardware.liftRight;
        pid = new PID(0, 0, 0)
    }
    public void setPosition(int target){
        int currentPosition = liftLeft.getCurrentPosition();
        double error = target - currentPosition;

        double speed = pid.update(error, false);
        liftLeft.setPower(speed);
        liftRight.setPower(speed);
    }
    public void setHeight(PoleHeight height){
        switch (height){
            case Short:

                break;
            case Med:

                break;
            case Tall:

                break;
        }
    }
    enum PoleHeight{
        Med, Tall, Short
    }
}

