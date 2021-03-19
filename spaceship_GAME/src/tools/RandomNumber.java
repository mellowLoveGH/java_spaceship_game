package tools;

import java.util.Random;

public class RandomNumber {
	
	public static Random ran = new Random();
	
	// this method is widely used in the project
	public static int getRandomNum(int from, int to) {
		return ran.nextInt(to - from) + from;
	}
	
}
