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

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.ronyzzn.supticket.eSupportTicket;
import de.ronyzzn.supticket.Storage.NoTicketFoundException;

public class Command_TicketList implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§6The console can not view tickets.");
			return true;
		}
		
		Player player = (Player) sender;
		List<String[]> tickets = eSupportTicket.st.getAllTickets();
		
		if(!player.hasPermission("msupportticket.ticketlist")) {
			sender.sendMessage("§cYou don't have permission!");
			return true;
		}
		
		if(args.length == 0) {		
			player.sendMessage("§9=== -§cAdmin§9 Tickets- ===");
			player.sendMessage("§b#Here are all tickets listed, which are open and not assigned to someone.");
			player.sendMessage("§eTickets 1-" + eSupportTicket.config.getString("tickets_per_page") + " from " + tickets.size());
			
			try {
			showOwnTickets(0, Integer.parseInt(eSupportTicket.config.getString("tickets_per_page")), player, tickets);
			} catch(NumberFormatException e) {
				player.sendMessage("§cError! §6tickets_per_page must be a number! Please contact a admin!");
				return true;
			}
			
			return true;
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("1")) {
				player.sendMessage("§9=== -§cAdmin§9 Tickets- ===");
				player.sendMessage("§b#Here are all tickets listed, which are open and not assigned to someone.");
				player.sendMessage("§eTickets 1-" + eSupportTicket.config.getString("tickets_per_page") + " from " + tickets.size());
				
				try {
				showOwnTickets(0, Integer.parseInt(eSupportTicket.config.getString("tickets_per_page")), player, tickets);
				} catch(NumberFormatException e) {
					player.sendMessage("§cError! §6tickets_per_page must be a number! Please contact a admin!");
					return true;
				}
				
				return true;
			}
			
			try {
				int startValue = (Integer.parseInt(args[0]) - 1) * Integer.parseInt(eSupportTicket.config.getString("tickets_per_page"));
				player.sendMessage("§9=== -§cAdmin§9 Tickets- ===");
				player.sendMessage("§b#Here are all tickets listed, which are open and not assigned to someone.");
				player.sendMessage("§eTickets " + (startValue + 1) + " - " + (startValue + Integer.parseInt(eSupportTicket.config.getString("tickets_per_page"))) + " from " + tickets.size());
				showOwnTickets(startValue, startValue + 5, player, tickets);	
			} catch(NumberFormatException e) {
				player.sendMessage("§cError! §6tickets_per_page must be a number! Please contact a admin!");
				return true;
			}
			
			return true;
		}
		return false;
	}
	
	private void showOwnTickets(int start, int end, Player player, List<String[]> tickets) {
		int count = 0;
		for(String[] ticket : tickets) {
			if(count >= start && count < end) {
				String status = "§aOPEN";
				try {
					if(eSupportTicket.st.isClosed(ticket[0]))
						status = "§cCLOSED";				
				} catch (NoTicketFoundException e) { e.printStackTrace(); }
				
				player.sendMessage("§7Ticket §f" + ticket[0] + "§7, Assigned to: §f" + ticket[4]);
				player.sendMessage("     §7Status: " + status);	
				player.sendMessage("§9--------");
			}
			count++;
		}
	}
}