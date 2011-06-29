package shamebot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import shamebot.rocket.Rocket;


public abstract class Type1Command extends RocketCommand{
	
	public Type1Command(Rocket rocket) {
		super(rocket);
	}

	protected boolean decode(CommandSender sender, String[] args, String node, String nodeOther)
	{
		if(!hasPermissionOrError(sender, node))
		{
			return true;
		}
		if(args.length == 0)
		{
			sender.sendMessage("Duration argument expected");
			return false;
		}
		int duration = 0;
		Entity entity;
		if(args.length > 0)
		{
			try
			{
				duration = Integer.decode(args[0]);
			}
			catch (NumberFormatException e)
			{
				sender.sendMessage("Not a number: "+args[0]);
				return false;
			}
		}
		if(args.length > 1)
		{
			if(!hasPermissionOrError(sender, nodeOther))
	    	{
	    		return true;
	    	}
			entity = Bukkit.getServer().getPlayer(args[1]);
			if(entity == null)
			{
				try
				{
					int id = Integer.decode(args[1]);
					entity = rocket.getEntityById(id);
				}
				catch (NumberFormatException e)
				{
					
				}
			}
			if(entity == null)
			{
				sender.sendMessage("There is no such player or entity: " + args[1]);
				return false;
			}
		}
		else if(sender instanceof Player)
		{
			entity = (Player)sender;
		}
		else
		{
			sender.sendMessage("You must specify a playername or entityid from console");
			return false;
		}
		execute(entity, duration, sender);
		return true;
	}
	
	protected abstract void execute(Entity entity, int duration, CommandSender sender);
	
}
