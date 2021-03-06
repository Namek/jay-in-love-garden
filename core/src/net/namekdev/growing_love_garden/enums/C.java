package net.namekdev.growing_love_garden.enums;

/**
 * Game Constants.
 * 
 * @author Namek
 */
public interface C {
	public interface Player {
		public static final float NormalMoveSpeed = 8f;
		public static final float WalkStepDuration = 0.1f;
		public static final float WalkRotation = 3f;
		public static final float StompDuration = 0.1f;
		public static final float StompCooldown = 0.7f;
		
		public static final float ColliderBottomWidth = 80;
		public static final float ColliderBottomHeight = 50;
	}
	
	public interface Camera {
		public static final float MoveThresholdScreenVertSizePercent = 0.49f;
		public static final float MoveThresholdScreenHorzSizePercent = 0.49f;
	}
	
	public interface Bucket {
		public static final float PosLeft = 25;
		public static final float PosTop = 98;
	}
	
	public interface Tree {
		public static final float PulseUpDuration = 1f;
		public static final float PulseDownDuration = 0.5f;
	}
	
	public interface Leaf {
		public static final float GrowTempoMin = 0.04f;
		public static final float GrowTempoMax = 0.08f;
		public static final float ScaleYMin = 0.1f;
		public static final float ScaleYMax = 1.2f;
		public static final float NaturalFallSpeed = 50f;
		public static final float StompFallSpeed = 80f;
		public static final float LyingDuration = 5f;
		public static final float LifeProgressStartMin = 0;
		public static final float LifeProgressStartMax = 0.1f;
		public static final float VacuumSpeedFactor = 0.2f;
		public static final float VacuumSpeedMin = 3f;
		public static final float VacuumSpeedMax = 7.40f;
		public static final float VacuumDisappearDistance = 25;
		
		public interface Stadium {
			public static final float Bigger = 0.5f;
			public static final float GettingYellow = 0.8f;
			public static final float GettingSmaller = 0.9f;
		}
		
		public interface Value {
			public static final int Bigger = 10;
			public static final int GettingYellow = 4;
		}
	}
	
	public interface World {
		public static final float Width = 1280;
		public static final float Height = 768;
		public static final float TopHorizonGraphicBottom = 385;
		public static final float TopHorizonCollisionBottom = TopHorizonGraphicBottom + 100;
		public static final float LowerHorizonGraphicBottom = 150;

		public static final float ShakeWidthMin = 4;
		public static final float ShakeWidthMax = 10;
		public static final float ShakeHeightMin = 1;
		public static final float ShakeHeightMax = 3;
		public static final float TimeToDetachLeafs = C.Player.StompDuration/2;
	}
	
	public interface Levels {
		public static final int[] Goal = new int[] { 1000, 10000, 23000, 35000 };
		public static final float[] YearProgressingSpeed = new float[] { 0.09f, 0.006f, 0.0025f, 0.0017f };
	}
	
	public static final float ThanksMinDuration = 5f;//secs
}
