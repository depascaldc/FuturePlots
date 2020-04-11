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

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import tim03we.futureplots.provider.Provider;

public class HomesCommand extends BaseCommand {

    public HomesCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if(sender instanceof Player) {
                if(new Provider().getHomes(sender.getName(), ((Player) sender).getLevel().getName()).size() != 0) {
                    sender.sendMessage(translate(true, "homes-title", null));
                    for (String home : new Provider().getHomes(sender.getName(), ((Player) sender).getLevel().getName())) {
                        sender.sendMessage(translate(false, "homes-text", home.split(";")[0], home.split(";")[1]+ ";" + home.split(";")[2]));
                    }
                }
        }
    }
}
