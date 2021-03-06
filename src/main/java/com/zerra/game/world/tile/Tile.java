package com.zerra.game.world.tile;

import java.util.Map;

import javax.annotation.Nullable;

import org.joml.Vector2f;

import com.google.common.collect.Maps;
import com.zerra.game.world.map.TileMap;
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
	public static final Tile WATER = new TileLayeredBase("water", "water", new Vector2f(4, 0), new Vector2f(1, 1));
	public static final Tile SNOW = new TileBase("snow", "snow", new Vector2f(6, 0));

	public static final Tile PLANKS = new TileBase("planks", "planks", new Vector2f(5, 0));

	public static final Tile OBSIDIAN = new TileBase("obsidian", "obsidian", new Vector2f(3, 1));
	public static final Tile GOLD_ORE = new TileBase("gold_ore", "gold_ore", new Vector2f(7, 0));

	public static final Tile BOULDER = new TileBase("boulder", "boulder", new Vector2f(0, 1));
	public static final Tile FOREST_BUSH = new TileBase("forest_bush", "forest_bush", new Vector2f(0, 2));
	public static final Tile DEAD_BUSH = new TileBase("dead_bush", "dead_bush", new Vector2f(1, 2));
	public static final Tile SNOW_BUSH = new TileBase("snow_bush", "snow_bush", new Vector2f(2, 2));

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
		this.shouldRender = true;
	}

	/**
	 * Called when the tile is rendered. Please Note, this does <b><i>NOT</i></b> render the tile! This is only called when the tile is rendered.
	 * 
	 * @param x
	 *            The x position of the tile instance
	 * 
	 * @param y
	 *            The y position of the tile instance
	 * 
	 * @param renderer
	 *            the main rendering handler
	 * 
	 * @param tileRenderer
	 *            the renderer that actually renders the tile
	 */
	public void render(float x, float y, MasterRenderer renderer, TileRenderer tileRenderer) {
	}

	/**
	 * Updates the tile.
	 */
	public abstract void update();

	/**
	 * Called when the tile is added into the world.
	 * 
	 * @param map
	 *            The world the tile is being placed in.
	 * 
	 * @param x
	 *            The x position of where the tile is placed at.
	 * 
	 * @param y
	 *            The y position of where the tile is placed at.
	 */
	public void onTilePlaced(TileMap map, float x, float y) {
	}

	/**
	 * Called when the tile is removed from the world.
	 * 
	 * @param map
	 *            The world the tile is being removed from.
	 * 
	 * @param x
	 *            The x position of where the tile is removed from.
	 * 
	 * @param y
	 *            The y position of where the tile is removed from.
	 */
	public void onTileDestroyed(TileMap map, float x, float y) {
	}

	/**
	 * If the texture layer is zero, null is not valid. If the texture layer is greater than zero, then you may pass in null.
	 * 
	 * @param textureLayer
	 *            The layer that is being requested
	 * @return The texture coordinates the tile uses
	 */
	@Nullable
	public abstract Vector2f getTextureCoords(int textureLayer);

	/**
	 * @return The resource location for the texture the tile uses
	 */
	public abstract ResourceLocation getTexture();

	/**
	 * @return The width (in tiles) of the texture atlas. This will <b><i>ONLY</i></b> work properly if you use a different texture than another tile with the same texture
	 */
	public int getTextureWidth() {
		return 1;
	}

	/**
	 * @return The collision box of the tile
	 */
	public AxisAlignedBB getCollisionBox() {
		return FULL;
	}

	/**
	 * @return The registry name of the tile
	 */
	public String getRegistryName() {
		return registryName;
	}

	/**
	 * @return The name of the tile before it is localized
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

	/**
	 * @return Whether or not this tile should render
	 */
	public boolean shouldRender() {
		return shouldRender;
	}

	/**
	 * Gets this tile object as a String object.
	 */
	@Override
	public String toString() {
		return "Tile[" + this.getLocalizedName() + "/" + this.getUnlocalizedName() + ":" + this.getRegistryName() + "]";
	}

	/**
	 * Gets the tile type based on the registry name.
	 * 
	 * @param registryName
	 *            The registry name used to search the registry with.
	 * 
	 * @return The tile retrieved from the registry
	 */
	public static Tile byName(String registryName) {
		Tile tile = REGISTRY.get(registryName);
		return tile != null ? tile : Tile.VOID;
	}
}
