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

	private static final Vector2f TEXTURE_COORDS = new Vector2f(0, 0);

	private float dx;
	private float dy;

	private int exp = 0;
	private int level = 0;
	private PlayerInventory inventory;

	public EntityPlayer() {
		this(0, 0);
	}

	public EntityPlayer(float x, float y) {
		super(EntityType.PLAYER);
		this.setPosition(x, y);
		this.setLastPosition(x, y);
		this.setSpeed(2f);
		this.inventory = new PlayerInventory(45);
	}

	@Override
	public void update() {
		super.update();

		dx = 0;
		dy = 0;

		if (Display.isKeyPressed(GLFW.GLFW_KEY_W)) {
			dy -= this.getSpeed();
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_S)) {
			dy += this.getSpeed();
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_A)) {
			dx -= this.getSpeed();
		}

		if (Display.isKeyPressed(GLFW.GLFW_KEY_D)) {
			dx += this.getSpeed();
		}

		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);

		Zerra.getInstance().getCamera().move(dx, dy, 0);
		Zerra.getInstance().getCamera().setPosition(this.getX() - Display.getWidth() / MasterRenderer.scale / 2, this.getY() - Display.getHeight() / MasterRenderer.scale / 2, 0);
	}
	
	@Override
	public float getRenderX(float partialTicks) {
		return this.getX();
	}
	
	@Override
	public float getRenderY(float partialTicks) {
		return this.getY();
	}
	
	@Override
	public Vector2f getRenderOffset() {
		return new Vector2f(-16, -10);
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
	
	public int getExp() {
		return exp;
	}
	
	public int getLevel() {
		return level;
	}
	
	public PlayerInventory getInventory() {
		return inventory;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}