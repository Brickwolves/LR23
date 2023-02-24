package org.firstinspires.ftc.teamcode.DashConstants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PositionsAndSpeeds {

    @Config
    public static class SlidePositions {
        public static int slidesHighJunction = 700;
        public static int slidesHighFront = 650;//9000
        public static int slidesHighFunny = 490;
        public static int slidesMidJunction = 400;  //6500
        public static int slidesMidFunny = 150;
        public static int slidesMidFront = 310;
        public static int slidesDepositDrop = 20;  //1000
        public static int slidesTippedHeight = 140;
        public static int slidesStackIncrease = 45;
    }
    @Config
    public static class V4BPositions {
        //works in angles
        public static int v4bDown = -60;
        public static int v4bHide = 0;
        public static int v4bDownFunny = -30;
        public static int v4bScoreBack = 0;
        public static int v4bScoreBackLow = 0;
        public static int v4bScoreFront = 0;
        public static int v4bScoreFrontLow = 0;
        public static int v4bStartAuto = 0;
        public static int v4b0 = -226;
        public static int v4b90 = -520;
        public static double v4bSpeed = 0.6;
    }

    @Config
    public static class grabberPositions {
        public static double grabberDown = 0.89;
        public static double grabberScore = 0.91;
        public static double grabberScoreFront = 0.89;
        public static double grabberScoreFunnyFront = 1;
        public static double grabberScoreFunny = 0.81;
        public static double grabberTip = 0.95;
        public static double grabberHide = 0.86;
        public static double grabberStartAuto = 0.81;
        public static double grabber0 = 0.5;
        public static double grabber90 = 0.6;
    }


    public static double rateOfChange = 100;


    public static double clawOpen = 0.36;
    public static double clawOpenScore = 0.28;
    public static double clawTipped = 0.3;
    public static double clawClose = 0;

    public static double tipAngle = 3;

    public static double driveSpeed = 0.7;

    public static int stackedHeight = 1;



}

