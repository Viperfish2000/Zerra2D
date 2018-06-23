package com.zerra.game.map;

import java.util.Random;

import com.zerra.util.Maths;

public class HeightGenerator {

	private Random random;
	private long randomSeed;
	private int seed;

	public HeightGenerator() {
		this.randomSeed = System.nanoTime();
		this.random = new Random(this.randomSeed);
		this.seed = random.nextInt(1000000000);
	}

	public HeightGenerator(long randomSeed, int seed) {
		this.random = new Random(randomSeed);
		this.seed = seed;
	}

	public float generateHeight(float x, float y, float amplitude, int octaves, float roughness) {
		float total = 0;
		float d = (float) Math.pow(2, octaves - 1);
		for (int i = 0; i < octaves; i++) {
			float freq = (float) (Math.pow(2, i) / d);
			float amp = (float) Math.pow(roughness, i) * amplitude;
			total += this.getInterpolatedNoise(x * freq, y * freq) * amp;
		}
		return total;
	}

	private float getInterpolatedNoise(float x, float y) {
		int intX = (int) x;
		int intY = (int) y;
		float fracX = x - intX;
		float fracY = y - intY;

		float v1 = getSmoothNoise(intX, intY);
		float v2 = getSmoothNoise(intX + 1, intY);
		float v3 = getSmoothNoise(intX, intY + 1);
		float v4 = getSmoothNoise(intX + 1, intY + 1);

		float i1 = Maths.interpolate(v1, v2, fracX);
		float i2 = Maths.interpolate(v3, v4, fracX);
		return Maths.interpolate(i1, i2, fracY);
	}

	private float getSmoothNoise(int x, int y) {
		float corners = (getNoise(x - 1, y - 1) + getNoise(x + 1, y - 1) + getNoise(x - 1, y + 1) + getNoise(x + 1, y + 1)) / 16f;
		float sides = (getNoise(x - 1, y) + getNoise(x + 1, y) + getNoise(x, y - 1) + getNoise(x, y + 1)) / 8f;
		float center = getNoise(x, y) / 4f;
		return corners + sides + center;
	}

	private float getNoise(int x, int y) {
		random.setSeed(x * 4956132 + y * 125176 + seed);
		return random.nextFloat();
	}

	public int getSeed() {
		return seed;
	}
	
	public long getRandomSeed() {
		return randomSeed;
	}
}