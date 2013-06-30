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
package de.ronyzzn.supticket;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.ronyzzn.supticket.Commands.Command_AddTicket;
import de.ronyzzn.supticket.Commands.Command_AssignTicket;
import de.ronyzzn.supticket.Commands.Command_CloseTicket;
import de.ronyzzn.supticket.Commands.Command_MyTickets;
import de.ronyzzn.supticket.Commands.Command_ShowTicket;
import de.ronyzzn.supticket.Commands.Command_TicketList;
import de.ronyzzn.supticket.Storage.FlatFile;
import de.ronyzzn.supticket.Storage.SqlDatabase;
import de.ronyzzn.supticket.Storage.Storage;
import de.ronyzzn.supticket.TicketChat.Command_CloseTicketChat;
import de.ronyzzn.supticket.TicketChat.Command_OpenTicketChat;
import de.ronyzzn.supticket.TicketChat.TicketChatBreakListener;
import de.ronyzzn.supticket.TicketChat.TicketChatListener;

public class eSupportTicket extends JavaPlugin {
	
	public static Logger log;
	public static FileConfiguration config;
	public static Storage st;
	public static int ticketId = 0;
	public static HashMap<Player, Player> ticketChat = new HashMap<Player, Player>();
	
	@Override
	public void onDisable() {
		//Final Message
		log.info("Plugin disabled!");
	}
	
	@Override
	public void onEnable() {
		//Initialize
		this.initializeStuff();
		
		//Final Message
		log.info("Plugin enabled successfully!");
	}	
	
	private void initializeStuff() {
		//Logger for console messageing
		log = this.getLogger();
		//Config for Datas
		config = this.getConfig();
		if(!new File("plugins/eSupportTicket/config.yml").exists()) {
			this.saveDefaultConfig();
			log.info("Created default configuration!");
		}
		//Loading Storage
		st = this.loadStorage(config.getString("storage"));
		
		//Loading COmmands
		this.getCommand("addticket").setExecutor(new Command_AddTicket());
		this.getCommand("closeticket").setExecutor(new Command_CloseTicket());
		this.getCommand("mytickets").setExecutor(new Command_MyTickets());
		this.getCommand("assignticket").setExecutor(new Command_AssignTicket());
		this.getCommand("viewticket").setExecutor(new Command_ShowTicket());
		this.getCommand("opentc").setExecutor(new Command_OpenTicketChat());
		this.getCommand("closetc").setExecutor(new Command_CloseTicketChat());
		this.getCommand("ticketlist").setExecutor(new Command_TicketList());
		
		//Loading Events
		this.getServer().getPluginManager().registerEvents(new TicketChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new TicketChatBreakListener(), this);
	}
	
	private Storage loadStorage(String storageType) {
		if(storageType.equalsIgnoreCase("FlatFile")) {
			return new FlatFile();
		} else if(storageType.equalsIgnoreCase("MySQL")) {
			return new SqlDatabase(new String[] {config.getString("mysql_host"), config.getString("mysql_port"),
					config.getString("mysql_user"), config.getString("mysql_password"),
					config.getString("mysql_database")});
		}
		
		return new FlatFile();
	}
}