package net.namekdev.net.growing_love_garden.enums;

/**
 * Game Constants.
 * 
 * @author Namek
 */
public interface C {
	public interface Player {
		public static final float NormalMoveSpeed = 8f;
		public static final float StompDuration = 0.1f;
		
		public static final float ColliderBottomWidth = 80;
		public static final float ColliderBottomHeight = 50;
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
		public static final float GrowTempoMax = 0.06f;
		public static final float ScaleYMin = 0.1f;
		public static final float ScaleYMax = 1f;
		public static final float NaturalFallSpeed = 50f;
		public static final float StompFallSpeed = 80f;
		public static final float LyingDuration = 5f;
		public static final float LifeProgressStartMin = 0;
		public static final float LifeProgressStartMax = 0.1f;
		
		public interface Stadium {
			public static final float Bigger = 0.4f;
			public static final float GettingYellow = 0.8f;
			public static final float GettingSmaller = 0.9f;
		}
	}
	
	public interface World {
		public static final float ShakeWidthMin = 4;
		public static final float ShakeWidthMax = 10;
		public static final float ShakeHeightMin = 1;
		public static final float ShakeHeightMax = 3;
		public static final float TimeToDetachLeafs = C.Player.StompDuration/2;
	}
	
	public interface Levels {
		public static final int Goal1 = 10000; 
		public static final int Goal2 = 20000; 
	}
}
