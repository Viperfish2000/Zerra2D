package com.zerra.game.entity;

public abstract class EntityLiving extends Entity {

	private int health = 0;
	private int maxHealth = 0;
	private int stamina = 0;
	private int maxStamina = 0;
	private int mana = 0;
	private int maxMana = 0;
	private int armor = 0;
	private int speed = 0;

	public EntityLiving() {
		super(0, 0, EntityType.NEUTRAL);
	}

	public EntityLiving(int x, int y, EntityType type) {
		super(x, y, type);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxHealth = maxhealth;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
