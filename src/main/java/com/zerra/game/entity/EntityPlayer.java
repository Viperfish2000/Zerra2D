package com.zerra.game.entity;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import com.zerra.Zerra;
import com.zerra.game.inventory.PlayerInventory;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.Display;
import com.zerra.util.ResourceLocation;

public class EntityPlayer extends EntityLiving {

	private float dx;
	private float dy;

	private int exp = 0;
	private int level = 0;
	private PlayerInventory inventory;

	private static final Vector2f TEXTURE_COORDS = new Vector2f(0, 0);

	public EntityPlayer() {
		this(0, 0);
	}

	public EntityPlayer(float x, float y) {
		this.speed = 2f;
		this.setX(x);
		this.setY(y);
		this.setType(EntityType.PLAYER);
		this.inventory = new PlayerInventory(45);
	}

	@Override
	public void update() {
		super.update();

		dx = 0;
		dy = 0;

		if (Display.isKeyPressed(GLFW.GLFW_KEY_W)) {
			dy -= speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_S)) {
			dy += speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_A)) {
			dx -= speed;
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_D)) {
			dx += speed;
		}

		x += dx;
		y += dy;

		Zerra.getInstance().getCamera().move(dx, dy, 0);
		Zerra.getInstance().getCamera().setPosition(x - Display.getWidth() / MasterRenderer.SCALE / 2, y - Display.getHeight() / MasterRenderer.SCALE / 2, 0);
	}
	
	@Override
	public float getPartialRenderX(float partialTicks) {
		return x;
	}
	
	@Override
	public float getPartialRenderY(float partialTicks) {
		return y;
	}
	
	@Override
	public Vector2f getRenderOffset() {
		return new Vector2f(-8, -8);
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	@Override
	public AxisAlignedBB getCollisionBox() {
		return new AxisAlignedBB(this.getX(), this.getY(), 32, 32);
	}

	@Override
	public ResourceLocation getTexture() {
		return ENTITY_TEXTURE_LOCATION;
	}

	@Override
	public Vector2f getTextureOffset() {
		return TEXTURE_COORDS;
	}

	@Override
	public int getTextureWidth() {
		return 8;
	}
}