package tim03we.futureplots.generator;

/*
 * This software is distributed under "GNU General Public License v3.0".
 * This license allows you to use it and/or modify it but you are not at
 * all allowed to sell this plugin at any cost. If found doing so the
 * necessary action required would be taken.
 *
 * GunGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License v3.0 for more details.
 *
 * You should have received a copy of the GNU General Public License v3.0
 * along with this program. If not, see
 * <https://opensource.org/licenses/GPL-3.0>.
 */

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.chunk.IChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.GeneratorFactory;
import net.daporkchop.lib.random.PRandom;
import tim03we.futureplots.utils.PlotSettings;

import java.util.HashMap;

public class PlotGenerator implements Generator, GeneratorFactory {

    public final int PLOT = 0;
    public final int ROAD = 1;
    public final int WALL = 2;

    @Override
    public void generate(PRandom pRandom, IChunk iChunk, int chunkX, int chunkZ) {
        IChunk chunk = iChunk;
        HashMap<Integer, Integer> shape = getShape(chunkX << 4, chunkZ << 4, chunk);
        PlotSettings plotSettings = new PlotSettings(chunk.getLevel().getName());
        Block roadBlock = plotSettings.getRoadBlock();
        Block bottomBlock = plotSettings.getBottomBlock();
        Block plotFillBlock = plotSettings.getPlotFillBlock();
        Block plotFloorBlock = plotSettings.getPlotFloorBlock();
        Block wallBlock = plotSettings.getWallBlockUnClaimed();
        int groundHeight = plotSettings.getGroundHeight();
        for (int Z = 0; Z < 16; ++Z) {
            for (int X = 0; X < 16; ++X) {
                chunk.setBlock(X, 0, Z, bottomBlock);
                for (int y = 1; y < groundHeight; ++y) {
                    chunk.setBlock(X, y, Z, plotFillBlock);
                }
                int type = shape.get((Z << 4) | X);
                if (type == PLOT) {
                    chunk.setBlock(X, groundHeight, Z, plotFloorBlock);
                } else if (type == ROAD) {
                    chunk.setBlock(X, groundHeight, Z, roadBlock);
                } else {
                    chunk.setBlock(X, groundHeight, Z,roadBlock);
                    chunk.setBlock(X, groundHeight + 1, Z, wallBlock);
                }
            }
        }
    }

    @Override
    public void populate(PRandom pRandom, ChunkManager chunkManager, int i, int i1) { }

    public HashMap<Integer, Integer> getShape(int x, int z, IChunk chunk) {
        PlotSettings plotSettings = new PlotSettings(chunk.getLevel().getName());
        int roadWidth = plotSettings.getRoadWidth();
        int plotSize = plotSettings.getPlotSize();
        int totalSize = plotSize + roadWidth;
        int X;
        int Z;
        int typeZ;
        int typeX;
        int type;
        if (x >= 0) {
            X = x % totalSize;
        } else {
            X = totalSize - Math.abs(x % totalSize);
        }
        if (z >= 0) {
            Z = z % totalSize;
        } else {
            Z = totalSize - Math.abs(z % totalSize);
        }
        int startX = X;
        HashMap<Integer, Integer> shape = new HashMap<>();
        for (z = 0; z < 16; z++, Z++) {
            if (Z == totalSize) {
                Z = 0;
            }
            if (Z < plotSize) {
                typeZ = PLOT;
            } else if (Z == plotSize || Z == (totalSize - 1)) {
                typeZ = WALL;
            } else {
                typeZ = ROAD;
            }
            for (x = 0, X = startX; x < 16; x++, X++) {
                if (X == totalSize) {
                    X = 0;
                }
                if (X < plotSize) {
                    typeX = PLOT;
                } else if (X == plotSize || X == (totalSize - 1)) {
                    typeX = WALL;
                } else {
                    typeX = ROAD;
                }
                if (typeX == typeZ) {
                    type = typeX;
                } else if (typeX == PLOT) {
                    type = typeZ;
                } else if (typeZ == PLOT) {
                    type = typeX;
                } else {
                    type = ROAD;
                }
                shape.put((z << 4) | x, type);
            }
        }
        return shape;
    }

    @Override
    public void finish(PRandom pRandom, ChunkManager chunkManager, int i, int i1) {

    }

    @Override
    public Generator create(long l, String s) {
        return this;
    }
}
