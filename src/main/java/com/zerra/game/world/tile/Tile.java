package com.zerra.game.world.tile;

import java.util.Map;

import org.joml.Vector2f;

import com.google.common.collect.Maps;
import com.zerra.game.map.TileMap;
import com.zerra.gfx.renderer.MasterRenderer;
import com.zerra.gfx.renderer.TileRenderer;
import com.zerra.util.AxisAlignedBB;
import com.zerra.util.I18n;
import com.zerra.util.ResourceLocation;

public abstract class Tile {

	public static final AxisAlignedBB FULL = new AxisAlignedBB(0, 0, 16, 16);

	private static final Map<String, Tile> REGISTRY = Maps.<String, Tile>newHashMap();

	public static final Tile VOID = new TileBase("void", "void", new Vector2f(0, 0));
	public static final Tile GRASS = new TileBase("grass", "grass", new Vector2f(1, 0));
	public static final Tile STONE = new TileBase("stone", "stone", new Vector2f(2, 0));
	public static final Tile COBBLESTONE = new TileBase("cobblestone", "cobblestone", new Vector2f(3, 0));
	public static final Tile SAND = new TileBase("sand", "sand", new Vector2f(4, 0));
	public static final Tile LAVA = new TileLava("lava", "lava", new Vector2f(2, 1));
	public static final Tile WATER = new TileBase("water", "water", new Vector2f(1, 1));
	public static final Tile OBSIDIAN = new TileBase("obsidian", "obsidian", new Vector2f(3, 1));
	public static final Tile PLANKS = new TileBase("planks", "planks", new Vector2f(5, 0));
	public static final Tile SNOW = new TileBase("snow", "snow", new Vector2f(6, 0));

	public static final Tile BOULDER = new TileBase("boulder", "boulder", new Vector2f(0, 1));

	private String registryName;
	private String unlocalizedName;

	protected boolean shouldRender;

	public Tile(String registryName, String unlocalizedName) {
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		if (!REGISTRY.containsKey(registryName)) {
			REGISTRY.put(registryName, this);
		} else {
			throw new RuntimeException("Attempted to register a tile over another. OLD: " + Tile.REGISTRY.get(registryName).getLocalizedName() + ", NEW: " + this.getLocalizedName());
		}
	}

	public void render(float x, float y, MasterRenderer renderer, TileRenderer tileRenderer) {
	}

	public abstract void update();

	public void onTilePlaced(TileMap map, float x, float y) {
	}

	public void onTileDestroyed(TileMap map, float x, float y) {
	}

	public abstract Vector2f getTextureCoords();

	public abstract ResourceLocation getTexture();

	public AxisAlignedBB getCollisionBox() {
		return FULL;
	}

	public String getRegistryName() {
		return registryName;
	}

	/**
	 * @return The name before it is localized
	 */
	public String getUnlocalizedName() {
		return "tile." + this.unlocalizedName + ".name";
	}

	/**
	 * @return The name of the tile converted to the proper language
	 */
	public String getLocalizedName() {
		return I18n.format(this.getUnlocalizedName());
	}

	public boolean shouldRender() {
		return shouldRender;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile) {
			Tile tile = (Tile) obj;
			return tile.getRegistryName().equalsIgnoreCase(tile.getRegistryName());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Tile[" + this.getLocalizedName() + "/" + this.getUnlocalizedName() + ":" + this.getRegistryName() + "]";
	}

	public static Tile byName(String registryName) {
		Tile tile = REGISTRY.get(registryName);
		return tile != null ? tile : Tile.VOID;
	}
}
