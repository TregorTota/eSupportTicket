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
package de.ronyzzn.supticket.TicketChat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.ronyzzn.supticket.eSupportTicket;
import de.ronyzzn.supticket.Storage.NoTicketFoundException;

public class Command_OpenTicketChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§6The console can not open a ticket chat.");
			return true;
		}

		Player player = (Player) sender;
		
		if(!player.hasPermission("msupportticket.controlticketchat")) {
			sender.sendMessage("§cYou don't have permission!");
			return true;
		}
		
		if(args.length == 1) {
			String ticketId = args[0];
			Player target;
			
			try {
				target = Bukkit.getPlayer(eSupportTicket.st.getSender(ticketId));
				
				if(eSupportTicket.st.isClosed(ticketId)) {
					sender.sendMessage("§6This ticket is closed!");
					return true;
				}
				
			} catch (NoTicketFoundException e) { 
				sender.sendMessage("§6This ticket can not be found!");
				return true;
			}
			
			if(target == null) {
				sender.sendMessage("§6The sender of this ticket is not online!");
				return true;
			}
			
			if(eSupportTicket.ticketChat.containsKey(player) || eSupportTicket.ticketChat.containsValue(player)) {
				player.sendMessage("§6You are already in a ticket chat!");
				return true;
			}
			
			target.sendMessage("§9==== -New TicketChat opened- ====");
			target.sendMessage("§7-- " + player.getName() + " opened this TicketChat on ticket §a" + ticketId + "§7. --");
			target.sendMessage("§6Everything you are writing now, belongs to this TicketChat!!");
			target.sendMessage("§9--------");
			
			player.sendMessage("§9==== -New TicketChat opened- ====");
			player.sendMessage("§7-- You have opened this TicketChat on ticket §a" + ticketId + "§7. --");
			player.sendMessage("§6Everything you are writing now, belongs to this TicketChat!!");
			player.sendMessage("§9--------");
			
			eSupportTicket.ticketChat.put(player, target);
			return true;
		}
		
		return false;
	}
}