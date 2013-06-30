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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.ronyzzn.supticket.eSupportTicket;
import de.ronyzzn.supticket.Storage.NoTicketFoundException;

public class Command_ShowTicket implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.GOLD + "The console can not view tickets.");
			return true;
		}
		
		boolean adm = false;
		Player player = (Player) sender;
		
		if(player.hasPermission("msupportticket.showticket.all")) {
			adm = true;
		}
		
		if(!adm && !player.hasPermission("msupportticket.showticket.own")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if(args.length == 1) {
			String ticketId = args[0];
			String[] ticket = null;
			
			//Check if ticket is closed and if the ticket exists..
			try {
				if(!adm && !eSupportTicket.st.getSender(ticketId).equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(ChatColor.GOLD + "You are only allowed to see your own tickets!");
                    return true;
				}					
					
				ticket = eSupportTicket.st.getTicketById(ticketId);
				
				if(eSupportTicket.st.isClosed(ticketId)) {
					sender.sendMessage(ChatColor.GOLD + "This ticket is closed.");
					return true;
				}		
				
			} catch (NoTicketFoundException e) {
				sender.sendMessage(ChatColor.GOLD + "Ticket " + ticketId + " can not be found.");
				return true;
			}
			
			
			
			player.sendMessage(ChatColor.BLUE + "=== -Ticket " + ticketId + "- ====");
			if(adm) player.sendMessage(ChatColor.GRAY + "Sender: " + ChatColor.WHITE + ticket[1]);
			player.sendMessage(ChatColor.GRAY + "Assigned to: " + ChatColor.WHITE + ticket[4]);
			player.sendMessage(ChatColor.GRAY + "Creation date: " + ChatColor.WHITE + ticket[2]);
			player.sendMessage(ChatColor.GRAY + "Message: " + ChatColor.WHITE + ticket[3]);
			player.sendMessage(ChatColor.BLUE + "--------");
			
			return true;
		}
		
		return false;
	}

}
