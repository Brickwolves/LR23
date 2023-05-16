package org.firstinspires.ftc.teamcode.Control.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public abstract class Hardware {

    public DcMotor leftFront, rightFront,
                    leftBack, rightBack,
            verticalEncoder, horizontalEncoder;

//    public abstract DcMotor getRightFront();
//    public abstract DcMotor getRightBack();
//    public abstract DcMotor getLeftFront();
//    public abstract DcMotor getLeftBack();

    public abstract double getLeftBackPowerScalar();
    public abstract double getRightBackPowerScalar();
    public abstract double getRightFrontPowerScalar();
    public abstract double getLeftFrontPowerScalar();

    public abstract double getVerticalEncoderTicksToCm();
    public abstract double getHorizontalEncoderTicksToCm();

//    public abstract DcMotor getVerticalEncoder();
//    public abstract DcMotor getHorizontalEncoder();

    public abstract double getImuHeading();
    public abstract void switchIMU();
    public abstract double getBatteryVoltage();

    public abstract void driveWithEncoders();
    public abstract void driveWithoutEncoders();

    public void floatDrive(){
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void lockDrive(){
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}
