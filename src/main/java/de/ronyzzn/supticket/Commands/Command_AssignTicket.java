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

public class Command_AssignTicket implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.RED + "The console is not able to assign tickets!");
			return true;
		}
		
		Player player = (Player) sender;
		if(!player.hasPermission("msupportticket.assignticket")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if(args.length == 1) {
			String ticketId = args[0].toString();
			
			//Check if ticket is already assigned and if the ticket exists..
			try {
				if(eSupportTicket.st.isAssigned(ticketId)) {
					player.sendMessage(ChatColor.GOLD + "This ticket is already assigned to " + eSupportTicket.st.getAssignedTo(ticketId) + ".");
					return true;
				}
				
				//Assign Ticket to player..
				eSupportTicket.st.assignTicket(ticketId, player.getName());
				
			} catch (NoTicketFoundException e) {
				player.sendMessage(ChatColor.GOLD + "Ticket " + ticketId + " can not be found.");
				return true;
			}
			
			player.sendMessage(ChatColor.GREEN + "You are now assigned to ticket " + ChatColor.AQUA + ticketId + ChatColor.GREEN+ ".");
			return true;
		}
		
		return false;
	}
}