package net.namekdev.net.growing_love_garden.enums;

/**
 * Game Constants.
 * 
 * @author Namek
 */
public interface C {
	public interface Player {
		public static final float NormalMoveSpeed = 8f;
		
		public static final float BucketLeft = 25;
		public static final float BucketTop = 98;
	}
	
	public interface Tree {
		public static final float PulseUpDuration = 1f;
		public static final float PulseDownDuration = 0.5f;
	}
	
	public interface Leaf {
		public static final float GrowTempoMin = 0.009f;
		public static final float GrowTempoMax = 0.012f;
		
		public interface Stadium {
			public static final float Bigger = 0.4f;
			public static final float GettingYellow = 0.8f;
			public static final float GettingSmaller = 0.9f;
		}
	}
}
