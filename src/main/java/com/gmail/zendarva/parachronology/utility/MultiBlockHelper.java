package com.gmail.zendarva.parachronology.utility;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * Created by James on 9/13/2015.
 */
public class MultiBlockHelper {

	static int depth = 0;

	public static BlockVector findSouthWestCorner(World world, int x, int y, int z) {
		BlockVector corner = null;
		BlockVector current = new BlockVector(world, x, y, z);

		if (current.block() == Blocks.END_STONE) {
			corner = westUntilNot(current);
			if (corner != null && corner.North().block() == Blocks.END_STONE
					&& corner.East().block() == Blocks.END_STONE) {
				return corner;
			}
			corner = southUntilNot(corner);
			if (corner != null && corner.South().block() == Blocks.END_STONE
					&& corner.West().block() == Blocks.END_STONE) {
				return corner;
			}
			corner = westUntilNot(corner);
			if (corner != null && corner.North().block() == Blocks.END_STONE
					&& corner.East().block() == Blocks.END_STONE) {
				return corner;
			}
			corner = null;
		}

		return corner;
	}

	public static boolean checkMultiblock(BlockVector vector) {
		BlockVector start = vector;

		for (int i = 0; i < 3; i++) {
			vector = vector.North();
			if (vector.block() != Blocks.END_STONE)
				return false;
		}
		for (int i = 0; i < 3; i++) {
			if (vector.block() != Blocks.END_STONE)
				return false;
			vector = vector.East();
		}

		for (int i = 0; i < 3; i++) {
			if (vector.block() != Blocks.END_STONE)
				return false;
			vector = vector.South();
		}
		for (int i = 0; i < 3; i++) {
			if (vector.block() != Blocks.END_STONE)
				return false;
			vector = vector.West();
		}
		//Check for empty air in the center.
		BlockVector air = vector;
		air = air.East().North();
		if (!(air.block() == Blocks.AIR && air.North().block() == Blocks.AIR && air.East().block() == Blocks.AIR
				&& air.East().North().block() == Blocks.AIR)) {
			return false;
		}

		return start.equals(vector);
	}

	private static BlockVector westUntilNot(BlockVector start) {
		if (start == null)
			return null;
		depth++;
		if (depth > 5) {
			depth = 0;
			return null;
		}
		BlockVector Next = start.West();
		if (Next.block() == Blocks.END_STONE) {
			return westUntilNot(Next);
		}
		depth = 0;
		return start;

	}

	private static BlockVector southUntilNot(BlockVector start) {
		if (start == null)
			return null;
		depth++;
		if (depth > 5) {
			depth = 0;
			return null;
		}

		BlockVector Next = start.South();
		if (Next.block() == Blocks.END_STONE) {
			return southUntilNot(Next);
		}
		depth = 0;
		return start;

	}

}
