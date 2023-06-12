package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Controller {
	
	private final Gamepad gamepad;
	
	public Thumbstick rightStick, leftStick;
	public Button cross, circle, triangle, square, dpad_up, dpad_down, dpad_left, dpad_right, rightBumper, leftBumper, rightTrigger, leftTrigger, share, touchpad, options;
	private Gamepad.RumbleEffect rumble2m, rumble1m30s, rumble1m, rumble40s, rumble30s, rumble20s, rumble10s, rumbleCountdown, rumbleConstant, rumbleStop;
	private boolean time2m = false, time1m30s = false, time1m = false, time40s = false, time30s = false, time20s = false, timeCountdown = false;

	
	public Controller(Gamepad gamepad) {
		this.gamepad = gamepad;
		rightStick = new Thumbstick(); leftStick = new Thumbstick();
		
		cross = new Button(); circle = new Button(); triangle = new Button(); square = new Button();
		dpad_up = new Button(); dpad_down = new Button(); dpad_left = new Button(); dpad_right = new Button();
		rightBumper = new Button(); leftBumper = new Button();
		rightTrigger = new Button(); leftTrigger = new Button(); options = new Button();
		share = new Button(); touchpad = new Button();

		initializeRumble();
	}
	
	
	public void update(){
		rightStick.update(gamepad.right_stick_x, gamepad.right_stick_y, gamepad.right_stick_button); leftStick.update(gamepad.left_stick_x, gamepad.left_stick_y, gamepad.left_stick_button);
		
		cross.update(gamepad.cross); circle.update(gamepad.circle); triangle.update(gamepad.triangle); square.update(gamepad.square);
		dpad_up.update(gamepad.dpad_up); dpad_down.update(gamepad.dpad_down); dpad_left.update(gamepad.dpad_left); dpad_right.update(gamepad.dpad_right);
		rightBumper.update(gamepad.right_bumper); leftBumper.update(gamepad.left_bumper);
		share.update(gamepad.share); touchpad.update(gamepad.touchpad); options.update(gamepad.options);
		rightTrigger.update(gamepad.right_trigger); leftTrigger.update(gamepad.left_trigger);

	}

	/**
	 * Straight up just better button
	 */
	public static class Button {
		private boolean hold = false; private boolean tap = false; private boolean toggle = false; private float rawVal = 0;
		private final ElapsedTime timeHeld = new ElapsedTime();

		private void update(float trigger){
			rawVal = trigger;
			update(rawVal > .5);
		}

		public void update(boolean button) {
			boolean wasHeld = hold;
			tap = (hold = button) && !wasHeld;
			if(tap) toggle = !toggle;
			if(!button) timeHeld.reset();
		}

		public float rawValue() { return rawVal; }

		public boolean isPressed() { return hold; }
		
		public boolean isTapped() { return tap; }
		
		public boolean isToggled() { return toggle; }

		public void setToggle(boolean newValue){
			toggle = newValue;
		}

		public void toggle(){ toggle = !toggle; }

		public boolean isHeldFor(double seconds){
			return timeHeld.seconds() >= seconds;
		}

	}

	/**
	 * Fixed Joystick
	 */
	public class Thumbstick extends Button{
		private float rawX = 0, rawY = 0;
		private void update(float x, float y, boolean pressed) {
			rawX = x;
			rawY = y;
			update(pressed);
		}
		
		public double X() { return rawX; }
		
		public double Y() { return rawY * -1; }

	}



	public void stopRumble(){
		gamepad.stopRumble();
	}

	public void rumble(){
		gamepad.rumble(1, 1, 100);
	}

	public void rumbleTimer(double time){
		double remainingTime = 120 - time;
		if(remainingTime < 120 && !time2m) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble2m); time2m = true; }
		if(remainingTime < 90 && !time1m30s) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble1m30s); time1m30s = true; }
		if(remainingTime < 60 && !time1m) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble1m); time1m = true; }
		if(remainingTime < 40 && !time40s) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble40s); time40s = true; }
		if(remainingTime < 35 && !time30s) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble30s); time30s = true; }
		if(remainingTime < 20 && !time20s) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumble20s); time20s = true; }
		if(remainingTime < 10 && !timeCountdown) { gamepad.stopRumble(); gamepad.runRumbleEffect(rumbleCountdown); timeCountdown = true; }
	}



	private void initializeRumble() {
		final int rumbleLong = 700;
		final int rumbleShort = 200;
		final int pauseLong = 300;
		final int pauseShort = 100;

		rumble2m = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleLong)
				.addStep(0,0, pauseLong)
				.addStep(1,1, rumbleLong)
				.build();

		rumble1m30s = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleLong)
				.addStep(0,0, pauseLong)

				.addStep(1,1, rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1, rumbleShort)
				.build();

		rumble1m = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleLong)
				.build();

		rumble40s = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1, rumbleShort)
				.build();

		rumble30s = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)

				.addStep(1,1,rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1,rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1,rumbleShort)
				.build();

		rumble20s = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1,rumbleShort)
				.addStep(0,0, pauseShort)
				.addStep(1,1,rumbleShort)
				.build();

		rumbleCountdown = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1, rumbleShort)
				.addStep(0,0,1000 - rumbleShort)
				.addStep(1,1,2000)
				.build();

		rumbleConstant = new Gamepad.RumbleEffect.Builder()
				.addStep(1,1,1000)
				.build();

		rumbleStop = new Gamepad.RumbleEffect.Builder()
				.addStep(0,0,1)
				.build();
	}
}
