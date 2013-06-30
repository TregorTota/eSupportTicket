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
package de.ronyzzn.supticket.Storage;

import java.util.ArrayList;
import java.util.List;

import com.xemsdoom.mexdb.MexDB;
import com.xemsdoom.mexdb.exception.EmptyIndexException;
import com.xemsdoom.mexdb.system.Entry;

import de.ronyzzn.supticket.eSupportTicket;

public class FlatFile implements Storage {
	
	private MexDB database;
	
	public FlatFile() {
		this.database = new MexDB("plugins/eSupportTicket", "tickets"); 
		eSupportTicket.ticketId = loadTicketId();
	}
	
	private int loadTicketId() {
		int highest = 0;
		for(String s : database.getIndices()) {
			if(Integer.valueOf(s) > highest)
				highest = Integer.valueOf(s);
		}		
		return highest; 
	}
	
	@Override
	public void addTicket(String sender, String date, String message) {
		try {
			Entry e = new Entry(String.valueOf((eSupportTicket.ticketId + 1)));
			eSupportTicket.ticketId++;
			e.addValue("sender", sender);
			e.addValue("date", date);
			e.addValue("content", message);
			e.addValue("assignedto", "noone");
			e.addValue("closed", "0");
			
			this.database.addEntry(e);
			this.database.push();
			
		} catch (EmptyIndexException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeTicket(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		this.database.setValue(id, "closed", "1");
		this.database.push();
	}

	@Override
	public void assignTicket(String id, String ticketAdmin) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		this.database.setValue(id, "assignedto", ticketAdmin);
		this.database.push();
	}

	@Override
	public void reopenTicket(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		this.database.setValue(id, "closed", "0");
		this.database.setValue(id, "assignedto", "noone");
		this.database.push();		
	}

	@Override
	public boolean isAssigned(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		if(!this.database.getString(id, "assignedto").equalsIgnoreCase("noone")) 
			return true;
		
		return false;
	}

	@Override
	public String getAssignedTo(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		return database.getString(id, "assignedto");
	}

	@Override
	public String getSender(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		return database.getString(id, "sender");
	}

	@Override
	public String getDate(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		return database.getString(id, "date");
	}

	@Override
	public String getTicketContent(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		return database.getString(id, "content");
	}

	@Override
	public boolean isClosed(String id) throws NoTicketFoundException {
		if(!database.hasIndex(id))
			throw new NoTicketFoundException();
		
		if(this.database.getString(id, "closed").equalsIgnoreCase("1")) 
			return true;
		
		return false;
	}

	@Override
	public List<String[]> getTicketsBySender(String sender) {
		List<String[]> tickets = new ArrayList<String[]>();
		for(String indx : this.database.getIndices()) {
			if(this.database.getString(indx, "sender").equalsIgnoreCase(sender)) {
				tickets.add(
				 new String[] {indx, getCont(indx, "sender"), getCont(indx, "date"), getCont(indx, "content"), getCont(indx, "assignedto"), getCont(indx, "closed")});
			}
		}
		
		return tickets;
	}

	@Override
	public String[] getTicketById(String id) throws NoTicketFoundException {
		String[] ticket = null;
		
		for(String indx : this.database.getIndices()) {
			if(indx.equalsIgnoreCase(id)) {
				ticket =  new String[] {indx, getCont(indx, "sender"), getCont(indx, "date"), getCont(indx, "content"), getCont(indx, "assignedto"), getCont(indx, "closed")};
			}
		}
		
		if(ticket == null) throw new NoTicketFoundException();
		
		return ticket;
	}
	
	private String getCont(String id, String indx) { return this.database.getString(id, indx); 	}

	@Override
	public List<String[]> getAllTickets() {
		List<String[]> tickets = new ArrayList<String[]>();
		for(String indx : this.database.getIndices()) {
			if(getCont(indx, "closed").equalsIgnoreCase("0") && getCont(indx, "assignedto").equalsIgnoreCase("noone")) {
				tickets.add(
				 new String[] {indx, getCont(indx, "sender"), getCont(indx, "date"), getCont(indx, "content"), getCont(indx, "assignedto"), getCont(indx, "closed")});
			}
		}
		
		return tickets;
	}
}
