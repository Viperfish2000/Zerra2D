package com.zerra.game.entity;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.zerra.gfx.Animation;
import com.zerra.gfx.light.Light;
import com.zerra.gfx.renderer.EntityRenderer;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.ResourceLocation;

public class EntityFirefly extends EntityMob {

	private Random random;
	private float xa;
	private float ya;

	private Light buttLamp;

	private Animation<Vector2f> dayAnimation;
	private Animation<Vector2f> nightAnimation;

	public EntityFirefly() {
		this(0, 0);
	}

	public EntityFirefly(float x, float y) {
		super(x, y);
		this.random = new Random();

		this.buttLamp = new Light(new Vector2f(), new Vector4f(0.86f, 0.97f, 0.05f, 5.0f), 10);

		this.dayAnimation = new Animation<Vector2f>();
		this.dayAnimation.setDelay(75);
		this.dayAnimation.setFrames(new Vector2f(0, 3), new Vector2f(0, 4));
		this.nightAnimation = new Animation<Vector2f>();
		this.nightAnimation.setDelay(75);
		this.nightAnimation.setFrames(new Vector2f(0, 3), new Vector2f(0, 1), new Vector2f(0, 2), new Vector2f(0, 0), new Vector2f(0, 2), new Vector2f(0, 1));
		this.scale = 0.25f;
	}

	@Override
	public void update() {
		float speed = 0.25f;
		if (this.getTicksExisted() % (40 + random.nextInt(20)) == 0) {
			xa = random.nextInt(4) - 2;
			ya = random.nextInt(4) - 2;
			if (xa == 0)
				xa = 1;
			if (ya == 0)
				ya = 1;
		}
		this.setX(this.getX() + (xa * speed));
		this.setY(this.getY() + (ya * speed));
		super.update();
	}

	@Override
	public void render(MasterRenderer renderer, EntityRenderer entityRenderer, float partialTicks) {
		this.buttLamp.getPosition().x = this.getPartialRenderX(partialTicks) + 3.75f;
		this.buttLamp.getPosition().y = this.getPartialRenderY(partialTicks) + 6f;
		renderer.renderLights(buttLamp);

		this.dayAnimation.update();
		this.nightAnimation.update();
	}

	@Override
	public ResourceLocation getTexture() {
		return SMALL_ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		float time = world.getTime();
		return time < 0.75f ? nightAnimation.getObject() : dayAnimation.getObject();
	}

	@Override
	public int getTextureWidth() {
		return 16;
	}
}