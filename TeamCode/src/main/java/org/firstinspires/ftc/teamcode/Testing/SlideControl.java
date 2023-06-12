package org.firstinspires.ftc.teamcode.Testing;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Control.Hardware.V6Hardware;
import org.firstinspires.ftc.teamcode.Utilities.PID;

public class SlideControl {

    DcMotor liftLeft;
    DcMotor liftRight;
    PID pid;
    PoleHeight height = PoleHeight.Home;
    ElapsedTime runtime;
    Servo brace;
    private int max = 660;
    int slideshomedthreshold = 20;
    private double targetPosition = 0;
    @Config
    public static class SlidesDash {
        public static double targetPosition = 0;
        public static double proportional = 0.02, integral = 0, derivative = 0.0002, constant = 0.2, testposition = 0, braceHome = 0.57, bracedeployed = 0.98;

    }
    public static boolean slidehome;


    public SlideControl(V6Hardware hardware){
        liftLeft = hardware.liftLeft;
        liftRight = hardware.liftRight;
        pid = new PID(0, 0, 0);
        runtime = new ElapsedTime();
        slidehome = true;
        brace = hardwareMap.servo.get("brace");
    }
    public void setPosition(int target){
        targetPosition = target;
    }
    public void setHeight(PoleHeight height) {
        if(height != this.height){
            runtime.reset();
            this.height = height;
        }
    }

    public void testPostition(){
        brace.setPosition(SlidesDash.testposition);
    }
    private void stateMachine(){
        switch (height){
            case Home:
            case Short:
                setPosition(0);
                break;
            case Med:
                brace.setPosition(SlidesDash.bracedeployed);
                if (runtime.milliseconds() > 200){
                    setPosition(250);
                }
                break;
            case Tall:
                brace.setPosition(SlidesDash.bracedeployed);
                if (runtime.milliseconds() > 200) {
                    setPosition(600);
                }
                break;
        }
    }

    public void update(){
        stateMachine();
        pid.setWeights(SlidesDash.proportional, SlidesDash.integral, SlidesDash.derivative);
        int currentPosition = liftLeft.getCurrentPosition();
        targetPosition = Range.clip(targetPosition, 0, max);
        double error = targetPosition - currentPosition;

        double power = pid.update(error, false) + SlidesDash.constant;
        //if homed
        if (currentPosition <= slideshomedthreshold && error <= 0){
            power = 0;
            brace.setPosition(SlidesDash.braceHome);
            slidehome = true;
        }
        else{
            slidehome = false;
        }
//        System.out.println("Slides Error " + error);
        liftLeft.setPower(power);
        liftRight.setPower(power);
    }
    enum PoleHeight{
        Med, Tall, Short, Home
    }
}

