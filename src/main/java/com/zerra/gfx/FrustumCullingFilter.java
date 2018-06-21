package com.zerra.gfx;

import java.util.List;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;

import com.zerra.game.world.tile.Tile;
import com.zerra.util.AxisAlignedBB;

public class FrustumCullingFilter {

	private Matrix4f projectionViewMatrix;
	private FrustumIntersection frustumIntersection;

	public FrustumCullingFilter() {
		this.projectionViewMatrix = new Matrix4f();
		this.frustumIntersection = new FrustumIntersection();
	}

	public void updateFrustum(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		this.projectionViewMatrix.set(projectionMatrix);
		this.projectionViewMatrix.mul(viewMatrix);
		this.frustumIntersection.set(projectionViewMatrix);
	}

	public boolean insideFrustum(float x, float y, float z, float boundingRadius) {
		return frustumIntersection.testSphere(x, y, z, boundingRadius);
	}

	public boolean insideFrustum(float x, float y, AxisAlignedBB box) {
		return frustumIntersection.testAab((float) (x + box.getX()), (float) (y + box.getY()), 0, (float) (x + box.getXMax()), (float) (y + box.getYMax()), 1);
	}

	public void filterTiles(List<Tile> tiles) {
		for (Tile tile : tiles) {
			tile.setRemoved(!insideFrustum(tile.getX() + 8, tile.getY() + 8, 0, 16));
		}
	}

	// public void filterObjects(List<? extends GameObject> objects, float meshBoundingRadius) {
	// float boundingRadius;
	// Vector3f pos;
	// for (GameObject object : objects) {
	// boundingRadius = object.getScale().y * meshBoundingRadius;
	// pos = object.getPosition();
	// object.setInsideFrustum(insideFrustum(pos.x, pos.y, pos.z, boundingRadius));
	// }
	// }
	//
	// public void filter(Map<? extends ICullableObjectModel, List<GameObject>> objects) {
	// for (Map.Entry<? extends ICullableObjectModel, List<GameObject>> entry : objects.entrySet()) {
	// List<? extends GameObject> gameItems = entry.getValue();
	// filterObjects(gameItems, entry.getKey().getBoundingRadius());
	// }
	// }
}