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

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.ronyzzn.supticket.eSupportTicket;

public class SqlDatabase implements Storage {
	
	/**
	 * loginData format:  l[0] = host, l[1] = port, l[2] = user, l[3] = password, l[4] = database
	 * Table format: id = ticketId, sender = ticket owner, date = send date, content = message, assignedto = ticketStaff, closed = status
	 */	
	
	private String[] loginData = null;
	private Statement stat = null;
	boolean status = false;
	
	public SqlDatabase(String[] loginDatas) {
		this.loginData = loginDatas;
		this.connect();
		
		if(this.status) {
			try {
				stat.execute("CREATE TABLE IF NOT EXISTS esupportticket (" +
						"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
						"sender VARCHAR(50)," +
						"date VARCHAR(50)," +
						"content TEXT," +
						"assignedto VARCHAR(50)," +
						"closed VARCHAR(1)  )");
			} catch (SQLException e) {  e.printStackTrace();  }
		}
	}
	
	private void connect() {
			try {
				Class.forName("com.mysql.jdbc.Driver"); 	
			
				//Connect to Database
				this.stat = DriverManager.getConnection("jdbc:mysql://" + loginData[0] + ":"
					            + loginData[1] + "/" + loginData[4] + "?" + "user=" + loginData[2] + "&"
					            + "password=" + loginData[3]).createStatement();
				
				this.status = true;
			} catch (SQLException e) {	
				eSupportTicket.log.warning("Can not connect to mysql!");
				eSupportTicket.log.warning(" Changing storage to FlatFile..");
				eSupportTicket.st = new FlatFile(); 
				this.status = false;
			} 
			catch (ClassNotFoundException e) {	e.printStackTrace(); }
	}

	@Override
	public void addTicket(String sender, String date, String message) {
		this.runSql("INSERT INTO esupportticket SET `sender` = '"+ sender +"', `date` = '"+ date +"', `content` = '"+ message +"', `assignedto` = 'noone', `closed` = '0'");
	}

	@Override
	public void closeTicket(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		this.updateString(id, "closed", "1");
	}

	@Override
	public void assignTicket(String id, String ticketAdmin) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		this.updateString(id, "assignedto", ticketAdmin);
	}

	@Override
	public void reopenTicket(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		this.updateString(id, "closed", "0");		
	}

	@Override
	public boolean isAssigned(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		if(!this.getString(id, "assignedto").equalsIgnoreCase("noone"))
			return true;
		
		return false;
	}

	@Override
	public String getAssignedTo(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		return this.getString(id, "assignedto");
	}

	@Override
	public String getSender(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		return this.getString(id, "sender");
	}

	@Override
	public String getDate(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		return this.getString(id, "date");
	}

	@Override
	public String getTicketContent(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();

		return this.getString(id, "content");
	}

	@Override
	public boolean isClosed(String id) throws NoTicketFoundException {
		if(!this.containsId(id)) throw new NoTicketFoundException();
		
		if(this.getString(id, "closed").equalsIgnoreCase("1"))
			return true;
		
		return false;
	}

	@Override
	public List<String[]> getTicketsBySender(String sender) {
		ResultSet mySet = this.doSql("SELECT * FROM esupportticket WHERE `sender` = '"+ sender +"'");
		List<String[]> theTickets = new ArrayList<String[]>();
		
		try {
			while(mySet.next()) {
				theTickets.add(new String[] 
					{ String.valueOf(mySet.getInt("id")), mySet.getString("sender"), mySet.getString("date"), mySet.getString("content"), mySet.getString("assignedto"), mySet.getString("closed") }
				);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return theTickets;
	}

	@Override
	public String[] getTicketById(String id) throws NoTicketFoundException {
		ResultSet mySet = this.doSql("SELECT * FROM esupportticket WHERE `id` = '"+ id+"'");
		String[] theTicket= null;
		
		try {
			while(mySet.next()) {
				theTicket = 
					new String[]{ String.valueOf(mySet.getInt("id")), mySet.getString("sender"), mySet.getString("date"), mySet.getString("content"), mySet.getString("assignedto"), mySet.getString("closed") };
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return theTicket;
	}

	@Override
	public List<String[]> getAllTickets() {
		ResultSet mySet = this.doSql("SELECT * FROM esupportticket WHERE `closed` = '0' AND `assignedto` = 'noone'");
		List<String[]> theTickets = new ArrayList<String[]>();
		
		try {
			while(mySet.next()) {
				theTickets.add(new String[] 
					{ String.valueOf(mySet.getInt("id")), mySet.getString("sender"), mySet.getString("date"), mySet.getString("content"), mySet.getString("assignedto"), mySet.getString("closed") }
				);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return theTickets;
	}
	
	private void runSql(String sql) {
		try { stat.execute(sql); } catch(SQLException e) { e.printStackTrace(); }
	}
	
	private ResultSet doSql(String sql) {
		ResultSet sqlDone = null;
		try { sqlDone = stat.executeQuery(sql); } catch(SQLException e) { e.printStackTrace(); }
		
		return sqlDone;
	}
	
	private String getString(String id, String indx) {
		ResultSet row = this.doSql("SELECT * FROM esupportticket WHERE `id` = '"+ id +"'");
		
		String retString = "";
		try {
			while(row.next()) {
				retString = row.getString(indx);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return retString;		
	}
	
	private void updateString(String id, String indx, String newone) {
		this.runSql("UPDATE esupportticket SET `"+ indx +"` = '"+ newone +"' WHERE `id` = '"+ id +"'");
	}
	
	private boolean containsId(String id) {
		ResultSet row = this.doSql("SELECT * FROM esupportticket");
		int theId = 0;
		
		try {
			theId = Integer.parseInt(id);
			
			if(theId == 0) {
				return false;
			}
			
			while(row.next()) {
				if(row.getInt("id") == theId) 
					return true;
			}
		}
		catch (NumberFormatException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }		
		
		return false;
	}
}
