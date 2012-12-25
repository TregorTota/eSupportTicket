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
			<td>Close a ticket. (After that, it is not available anymore.</td>
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
</p>
