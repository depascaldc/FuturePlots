package tim03we.futureplots.tasks;

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
import cn.nukkit.block.BlockIds;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.scheduler.Task;
import com.nukkitx.math.vector.Vector3i;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Language;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotSettings;
import tim03we.futureplots.utils.PlotVector3i;

import java.util.concurrent.CompletableFuture;

public class PlotSetBorderTask extends Task {

    private Level level;
    private int height;
    private Block plotWallBlock;
    private PlotVector3i plotBeginPos;
    private double xMax, zMax;
    //private $plot, $level, $height, $plotWallBlock, $plotBeginPos, $xMax, $zMax;

    public PlotSetBorderTask (Plot plot, Block block) {
        Location plotPos = FuturePlots.getInstance().getPlotPosition(plot);
        this.level = plotPos.getLevel();
        this.plotBeginPos = new PlotVector3i((int) plotPos.getX(), (int) plotPos.getY(), (int) plotPos.getZ()).subtract(1, 0, 1);//$plotBeginPos.subtract(1,0,1);
        //$plotLevel = $plugin->getLevelSettings($plot->levelName);
        int plotSize = new PlotSettings(level.getName()).getPlotSize();
        this.xMax = plotBeginPos.x + plotSize + 1;
        this.zMax = plotBeginPos.z + plotSize + 1;
        this.height = new PlotSettings(level.getName()).getGroundHeight();
        this.plotWallBlock = block;
    }

    @Override
    public void onRun(int i) {
        CompletableFuture.runAsync(() -> {
            try {
                double x;
                double z;

                for (x = plotBeginPos.x; x <= xMax; x++) {
                    level.setBlock(Vector3i.from(x, height + 1, plotBeginPos.z), plotWallBlock);
                    level.setBlock(Vector3i.from(x, height + 1, zMax), plotWallBlock);
                }
                for (z = plotBeginPos.z; z <= zMax; z++) {
                    level.setBlock(Vector3i.from(plotBeginPos.x, height + 1, z), plotWallBlock);
                    level.setBlock(Vector3i.from(xMax, height + 1, z), plotWallBlock);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

}
