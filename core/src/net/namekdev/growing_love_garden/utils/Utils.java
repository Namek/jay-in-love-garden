package net.namekdev.growing_love_garden.utils;

import com.artemis.utils.IntBag;

public class Utils {
	public static IntBag cloneBag(IntBag bag) {
		int n = bag.size();
		IntBag newBag = new IntBag(n);
		int data[] = bag.getData();
		int newData[] = newBag.getData();
		
		for (int i = 0; i < n; ++i) {
			newData[i] = data[i]; 
		}
		
		return newBag;
	}
	
	public static IntBag filterBag(IntBag bag, IntBagPredicate predicate) {
		int n = bag.size();
		int data[] = bag.getData();
		IntBag newBag = new IntBag(n);

		for (int i = 0; i < n; ++i) {
			int e = data[i];
			if (predicate.apply(e)) {
				newBag.add(e);
			}
		}
		
		return newBag;
	}


	public static interface IntBagPredicate {
		boolean apply(int e);
	}
}
