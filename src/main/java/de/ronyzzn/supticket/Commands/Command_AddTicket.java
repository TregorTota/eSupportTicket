/*
 * eSupportTicket
 * Copyright (C) 2012-2013 Rony Tesch <chillupx@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package de.ronyzzn.supticket.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.ronyzzn.supticket.eSupportTicket;

public class Command_AddTicket implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.RED + "The console is not able to create tickets!");
			return true;
		}
		
		Player player = (Player) sender;
		if(!player.hasPermission("msupportticket.addticket")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if(args.length > 2) {
			String send = player.getName();
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
			
			StringBuilder message = new StringBuilder();
			for(int i = 0; i < args.length; i++) {
				message.append(args[i] + " ");
			}
			
			eSupportTicket.st.addTicket(send, date, message.toString());
			
			player.sendMessage(ChatColor.GREEN + "Your ticket was saved.");
			return true;
		}
		
		return false;
	}
}
