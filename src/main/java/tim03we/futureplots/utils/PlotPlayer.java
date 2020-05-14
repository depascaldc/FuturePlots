package tim03we.futureplots.utils;

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

import cn.nukkit.player.Player;
import tim03we.futureplots.FuturePlots;

public class PlotPlayer {

    private Player player;
    private String username;

    public PlotPlayer(Player player) {
        this.player = player;
    }

    public PlotPlayer(String username) {
        this.username = username;
    }

    public boolean onPlot() {
        return FuturePlots.getInstance().getPlotByPosition(player.getLocation()) != null;
    }

    public Plot getPlot() {
        return FuturePlots.getInstance().getPlotByPosition(player.getLocation());
    }

    public boolean isOwner() {
        return FuturePlots.provider.isOwner(player.getName(), getPlot());
    }
}
