package com.sa;

import java.util.concurrent.ThreadLocalRandom;

public class SensorHelper {

	public static double getValueSensor(double min_bound, double max_bound) {
		double random = ThreadLocalRandom.current().nextDouble(min_bound,
				max_bound);
		return round(random);
	}

	public static double getSleepTime(long min, long max) {
		return round(ThreadLocalRandom.current().nextLong(min, max));
	}

	public static double changeValuesSensor(double value, double min_bound,
			double max_bound, double delta) {
		double newValue;
		if (ThreadLocalRandom.current().nextBoolean() && value != min_bound) {
			// value decrease
			double min = value - delta;
			if (min < min_bound) {
				min = min_bound;
			}
			newValue = ThreadLocalRandom.current().nextDouble(min, value);
		} else {
			// value increase
			double max = value + delta;
			if (max > max_bound) {
				max = max_bound;
			}
			newValue = ThreadLocalRandom.current().nextDouble(value, max);
		}
		return round(newValue);
	}

	public static double round(double d) {
		return Math.floor(d * 100) / 100;
	}

	public static void main(String[] args) {
		double res = changeValuesSensor(-40.0, -40.0, 100.0, 5.0);
		System.out.println(res);
	}
}
