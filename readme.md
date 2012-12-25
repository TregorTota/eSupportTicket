<h1>eSupportTicket - Easy ticket system for Bukkit.</h1><hr />
<h3>What is eSupportTicket?</h3>
<p>
	<i>eSupportTicket</i> is a simple ticket system for the Bukkit API. It allows
	to create tickes in an easy way. You can control all commands with some
	permissions and there are already some special permissions for ranks like
	<i>Moderator</i>. You can find more in the documentation.
</p>
<h3>Commands</h3>
<p>
	Here you can read all commands and their use.
	<br />
	<table width="80%" style="border-color: black;">
		<tr>
			<th width="25%">Command</th>
			<th width="20%">Args</th>
			<th width="55%">Use</th>
		</tr>
		<tr><td colspan="3"><i><b>User Commands</b></i></td></tr>
		<tr style="align: left;">
			<td>/addticket</td>
			<td>[Text] [..] [..]</td>
			<td>Create a new ticket.</td>
		</tr>
		<tr style="align: left;">
			<td>/mytickets</td>
			<td>[Page]</td>
			<td>A view of all your tickets (Open and Closed).</td>
		</tr>
		<tr style="align: left;">
			<td>/viewticket</td>
			<td>[Id]</td>
			<td>Get the content and status of a ticket.</td>
		</tr>
		<tr><td colspan="3"><i><b>Admin Commands</b></i></td></tr>
		<tr style="align: left;">
			<td>/assignticket</td>
			<td>[Id]</td>
			<td>Assign a ticket as yours.</td>
		</tr>
		<tr style="align: left;">
			<td>/closeticket</td>
			<td>[Id]</td>
			<td>Close a ticket. (After that, it is not available anymore.)</td>
		</tr>
		<tr style="align: left;">
			<td>/ticketlist</td>
			<td>[Page]</td>
			<td>List all tickets which are open and not assigned to someone.</td>
		</tr>		
		<tr style="align: left;">
			<td>/opentc</td>
			<td>[Id]</td>
			<td>Open a <i>TicketChat</i> on the given ticket.</td>
		</tr>
		<tr style="align: left;">
			<td>/closetc</td>
			<td>[Id]</td>
			<td>Close a <i>TicketChat</i>.</td>
		</tr>
	</table>
</p><p>
<h3>Permissions</h3>
	Here you can read all permission entries and their use.
	<table width="80%" style="border-color: black;">
		<tr>
			<th width="25%">Permission</th>
			<th width="20%">Command</th>
			<th width="65%">Use</th>
		</tr>
		<tr><td colspan="3"><i><b>Command Permissions</b></i></td></tr>
		<tr style="align: left;">
			<td>msupportticket.addticket</td>
			<td>/addticket</td>
			<td>Permissions to use the given Command.</td>
		</tr>
		<tr style="align: left;">
			<td>No Permission</td>
			<td>/mytickets</td>
			<td>A permission is not necessary.</td>
		</tr>
		<tr style="align: left;">
			<td>
				<i>Admin: </i>msupportticket.showticket.all<br />
				<i>User: </i>msupportticket.showticket.own
			</td>
			<td>/viewticket</td>
			<td>Permissions to view all tickets or to view only all own tickets.</td>
		</tr>
		<tr style="align: left;">
			<td>msupportticket.assignticket</td>
			<td>/assignticket</td>
			<td>Permissions to assign a ticket as yours.</td>
		</tr>
		<tr style="align: left;">
			<td>msupportticket.closeticket</td>
			<td>/closeticket]</td>
			<td>Permissions to close a ticket finally.</td>
		</tr>
		<tr style="align: left;">
			<td></td>
			<td>/ticketlist</td>
			<td>Permissions to see a list of all tickets which are not closed and not assigned to someone.</td>
		</tr>		
		<tr style="align: left;">
			<td>msupportticket.controlticketchat</td>
			<td>/opentc <i>and</i> /closetc</td>
			<td>Permissions to open and close a <i>TicketChat</i>.</td>
		</tr>
		<tr><td colspan="3"><i><b>Bundled Permissions</b></i></td></tr>
		<tr style="align: left;">
			<td>msupportticket.user</td>
			<td colspan="2"><i>Contains:</i> <br />msupportticket.addticket <br />
      msupportticket.shwoticket.own</td>
		</tr>
		<tr style="align: left;">
			<td>msupportticket.moderative</td>
			<td colspan="2"><i>Contains:</i> <br />msupportticket.assignticket <br />
      msupportticket.closeticket<br />
      msupportticket.showticket.all<br />
      msupportticket.controlticketchat<br />
      msupportticket.addticket<br />
      msupportticket.ticketlist<br /></td>
		</tr>
		
		
	</table>
</p>