package org.firstinspires.ftc.teamcode.Testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Control.Hardware.V6Hardware;
import org.firstinspires.ftc.teamcode.Utilities.PID;

public class ArmControl {

    DcMotor arm;

    PID pid;

    int armhomedthreshold = 20;
    private int targetposition = 0;

    private int max = 900;
    @Config
    public static class ArmDash {
        public static double proportional = 0.01, integral = 0, derivative = 0.001, settargetPosition = 0;
    }
    public ArmControl(V6Hardware hardware){
        arm = hardware.arm;
        pid = new PID(0, 0, 0);
    }
    public void setposition(int target){
        targetposition = target;
    }
    public void update(){
        pid.setWeights(ArmControl.ArmDash.proportional, ArmControl.ArmDash.integral, ArmControl.ArmDash.derivative);
        int currentPosition = arm.getCurrentPosition();
        targetposition = Range.clip(targetposition, 0, max);
        double error = targetposition - currentPosition;

        double power = pid.update(error, false);
        if (SlideControl.slidehome && currentPosition <= armhomedthreshold && error <= 0) {
            power = 0;
        }
        System.out.println("Arm Error " + error);
        arm.setPower(power);

    }

    public void setHeight(SlideControl.PoleHeight height) {
        switch (height) {
            case Short:
                setposition(850);
                break;
            case Med:
            case Tall:
                setposition(750);

                break;
        }
    }

}
