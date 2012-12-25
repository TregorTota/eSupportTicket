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

import java.util.List;

public interface Storage {
	
	/**
	 * This method is used to add a ticket to the database.
	 * 
	 * @param sender - Name of the player who sends the ticket.
	 * @param date - When did the player send the ticket.
	 * @param message - Message from the player who sends the ticket.
	 * @param title - Title of the ticket.
	 */
	public void addTicket(String sender, String date, String message);
	
	/**
	 * This method is used to close an open ticket.
	 * 
	 * @param id - Ticket id of the ticket which should be closed.
	 * @throws NoTicketFoundException 
	 */
	public void closeTicket(String id) throws NoTicketFoundException;
	
	/**
	 * This method is used to assign a ticket to a ticketAdmin.
	 * 
	 * @param id - Ticket id of the ticket which should be assigned to somebody.
	 * @param ticketAdmin - Playername of the ticketAdmin, which should be assigned to the ticket.
	 */
	public void assignTicket(String id, String ticketAdmin) throws NoTicketFoundException;
	
	/**
	 * This method is used to reopen a closed ticket.
	 * 	
	 * @param id - Ticket id of the ticket which should be reopen.
	 */
	public void reopenTicket(String id) throws NoTicketFoundException;
	
	public boolean isAssigned(String id) throws NoTicketFoundException;
	
	public String getAssignedTo(String id) throws NoTicketFoundException;
	
	public String getSender(String id) throws NoTicketFoundException;
	
	public String getDate(String id) throws NoTicketFoundException;
	
	public String getTicketContent(String id) throws NoTicketFoundException;
	
	public boolean isClosed(String id) throws NoTicketFoundException;
	
	public List<String[]> getTicketsBySender(String sender);
	
	public String[] getTicketById(String id)  throws NoTicketFoundException;
	
	public List<String[]> getAllTickets();
}
