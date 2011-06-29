package shamebot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import shamebot.rocket.Rocket;


public abstract class RocketCommand implements CommandExecutor {
	
	protected Rocket rocket;
	protected static final String NO_PERMISSION = "You have insufficent permissions";
	
	public RocketCommand(Rocket rocket)
	{
		this.rocket = rocket;
	}

	public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

	protected boolean hasPermissionOrError(CommandSender sender, String node)
	{
		boolean has = rocket.hasPermission(sender, node);
		if(!has)
    	{
    		sender.sendMessage(NO_PERMISSION);
    	}
		return has;
	}
}
