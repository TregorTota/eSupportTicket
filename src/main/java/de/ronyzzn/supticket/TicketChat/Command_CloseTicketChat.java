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

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.ronyzzn.supticket.eSupportTicket;

public class Command_CloseTicketChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.GOLD + "The console can not open a ticket chat.");
			return true;
		}

		Player player = (Player) sender;
		
		if(!player.hasPermission("msupportticket.controlticketchat")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if(args.length == 0) {
			if(!eSupportTicket.ticketChat.containsKey(player) && !eSupportTicket.ticketChat.containsValue(player)) {
				player.sendMessage(ChatColor.GOLD + "There is no ticket chat open!");
			}
			
			for(Entry<Player, Player> et : eSupportTicket.ticketChat.entrySet()) {
				if(et.getKey() == player) {
					eSupportTicket.ticketChat.remove(player);
					et.getValue().sendMessage(ChatColor.BLUE + "=== TicketChat Closed. ====");
				} else if(et.getValue() == player){
					eSupportTicket.ticketChat.remove(et.getKey());
					et.getKey().sendMessage(ChatColor.BLUE + "=== TicketChat Closed. ====");
				}
				
				player.sendMessage(ChatColor.BLUE + "=== TicketChat Closed. ====");
			}
			
			return true;			
		}		
		return false;
	}
}