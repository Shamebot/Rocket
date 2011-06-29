package shamebot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import shamebot.rocket.Rocket;


public class StopCommand extends RocketCommand {

	private static final String NODE = "rocket.stop";
	private static final String NODE_OTHER = "rocket.stop.other";
	
	public StopCommand(Rocket rocket)
	{
		super(rocket);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
		if(!hasPermissionOrError(sender, NODE))
    	{
    		return true;
    	}
    	Entity entity;
    	if(args.length > 0)
    	{
    		if(!hasPermissionOrError(sender, NODE_OTHER))
        	{
        		return true;
        	}
    		entity = Bukkit.getServer().getPlayer(args[0]);
    		if(entity == null)
    		{
    			try
    			{
    				int id = Integer.decode(args[0]);
    				entity = rocket.getEntityById(id);
    			}
    			catch (NumberFormatException e)
    			{
    				//handled below
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
    	rocket.stop(entity);
    	sender.sendMessage("Stopping");
    	return true;
    }

}
