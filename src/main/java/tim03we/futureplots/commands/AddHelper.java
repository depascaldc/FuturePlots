package tim03we.futureplots.commands;

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
import cn.nukkit.command.CommandSender;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotPlayer;

public class AddHelper extends BaseCommand {

    public AddHelper(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if(sender instanceof Player) {
            if(new PlotPlayer((Player) sender).onPlot()) {
                Plot plot = FuturePlots.getInstance().getPlotByPosition(((Player) sender).getLocation());
                if(FuturePlots.provider.isOwner(sender.getName(), plot)) {
                    if (args.length > 1) {
                        if (!FuturePlots.provider.isHelper(args[1], plot)) {
                            FuturePlots.provider.addHelper(args[1], plot);
                            sender.sendMessage(translate(true, "helper.added", args[1].toLowerCase()));
                        } else {
                            sender.sendMessage(translate(true, "helper.exists"));
                        }
                    } else {
                        sender.sendMessage(getUsage());
                    }
                } else {
                    sender.sendMessage(translate(true, "not.a.owner"));
                }
            } else {
                sender.sendMessage(translate(true, "not.in.plot"));
            }
        }
    }
}
