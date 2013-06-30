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

import de.ronyzzn.supticket.eSupportTicket;
import de.ronyzzn.supticket.Storage.NoTicketFoundException;

public class Command_CloseTicket implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission("msupportticket.closeticket")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if(args.length == 1) {
			String ticketId = args[0];
			
			//Check if ticket is already assigned and if the ticket exists..
			try {
				if(eSupportTicket.st.isClosed(ticketId)) {
					sender.sendMessage(ChatColor.GOLD + "This ticket is already closed.");
					return true;
				}
				
				//Assign Ticket to player..
				eSupportTicket.st.closeTicket(ticketId);
				
			} catch (NoTicketFoundException e) {
				sender.sendMessage(ChatColor.GOLD + "Ticket " + ticketId + " can not be found.");
				return true;
			}
			
			sender.sendMessage(ChatColor.GREEN + "Ticket " + ticketId + " is now closed.");
			return true;
		}
		
		return false;
	}
}
