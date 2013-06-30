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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.ronyzzn.supticket.eSupportTicket;

public class TicketChatListener implements Listener {
	
	@EventHandler
	public void handleTicketChat(AsyncPlayerChatEvent event) {
		if(eSupportTicket.ticketChat.containsKey(event.getPlayer()) || eSupportTicket.ticketChat.containsValue(event.getPlayer())) {
			Player target = null;

			for(Entry<Player, Player> et : eSupportTicket.ticketChat.entrySet()) {
				if(et.getKey() == event.getPlayer()) {
					target = et.getValue();
				} else if(et.getValue() == event.getPlayer()){
					target = et.getKey();
				}
			}
			
			if(target != null) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "TicketChat" + ChatColor.GRAY + "] " + event.getPlayer().getName() + ": " + ChatColor.WHITE + event.getMessage());
				target.sendMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "TicketChat" + ChatColor.GRAY + "] " + event.getPlayer().getName() + " " + ChatColor.WHITE + event.getMessage());
			}			
		}
	}
}
