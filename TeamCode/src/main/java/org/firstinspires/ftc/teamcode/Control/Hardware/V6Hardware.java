package org.firstinspires.ftc.teamcode.Control.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//hardware variables followed by hardware objects

//DCMotorEx: https://ftctechnh.github.io/ftc_app/doc/javadoc/index.html?com/qualcomm/robotcore/hardware/DcMotorEx.html

public class V6Hardware extends Hardware{

    public VoltageSensor batteryVoltage;

    public BNO055IMU imu;

    public DcMotor liftLeft, liftRight, arm;

    private HardwareMap hardwareMap;

    private double imuOffset = 0;
    private int imuNumber = 0;

    @Override
    public double getLeftBackPowerScalar() {
        return -1;
    }

    @Override
    public double getRightBackPowerScalar() {
        return 1;
    }

    @Override
    public double getRightFrontPowerScalar() {
        return -1;
    }

    @Override
    public double getLeftFrontPowerScalar() {
        return 1;
    }

    @Override
    public double getVerticalEncoderTicksToCm() {
        return 0.00132968; //should be different than one another (*60/48)
    }

    @Override
    public double getHorizontalEncoderTicksToCm() {
        return -0.00132968; // -0.001927 everything *243.84/126.5
    }
    @Override
    public double getBatteryVoltage(){return batteryVoltage.getVoltage();}

    @Override
    public void driveWithEncoders() {
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void driveWithoutEncoders() {
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public V6Hardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        batteryVoltage = hardwareMap.voltageSensor.iterator().next();

        //DRIVE
        rightFront = hardwareMap.dcMotor.get("fr");
        leftFront = hardwareMap.dcMotor.get("fl");
        leftBack = hardwareMap.dcMotor.get("bl");
        rightBack = hardwareMap.dcMotor.get("br");

        verticalEncoder = leftFront;
        horizontalEncoder = hardwareMap.dcMotor.get("bl");
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft = hardwareMap.dcMotor.get("spool2");
        liftRight = hardwareMap.dcMotor.get("spool");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        arm = hardwareMap.dcMotor.get("v4b");

        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        driveWithoutEncoders();

        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        imu =  hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters Params = new BNO055IMU.Parameters();
        Params.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        Params.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        Params.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opMode
        Params.loggingEnabled      = true;
        Params.loggingTag          = "IMU";
        Params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(Params);

    }
    @Override
    public double getImuHeading(){
        Orientation angles = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
        double heading = (angles.firstAngle + 360 + imuOffset) % 360;
        return Math.toRadians(heading);
    }

    public void switchIMU(){
        imuOffset = Math.toDegrees(getImuHeading());
        imuNumber = (imuNumber + 1) % 2;
        switch (imuNumber){
            case 0:
                imu =  hardwareMap.get(BNO055IMU.class, "imu1");
                break;
            case 1:
                imu =  hardwareMap.get(BNO055IMU.class, "imu2");
                break;
        }
        BNO055IMU.Parameters Params = new BNO055IMU.Parameters();
        Params.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        Params.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        Params.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opMode
        Params.loggingEnabled      = true;
        Params.loggingTag          = "IMU";
        Params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(Params);
    }

}
