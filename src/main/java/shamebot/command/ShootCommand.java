package shamebot.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import shamebot.rocket.Rocket;


public class ShootCommand extends LaunchCommand {

	private static final String NODE = "rocket.shoot";
	private static final String NODE_OTHER = "rocket.shoot.other";
	
	public ShootCommand(Rocket rocket) 
	{
		super(rocket);
	}

	@Override
	protected void execute(Entity entity, int duration, CommandSender sender)
	{
		if(sender instanceof Player)
		{
			rocket.launch(entity, 10, ((Player)sender).getLocation().getDirection().normalize().multiply(3), duration, false, 1);
		}
		else
		{
			super.execute(entity, duration, sender);
		}
	}
	
	@Override
	public String getNode() {
		return NODE;
	}

	@Override
	public String getNodeOther() {
		return NODE_OTHER;
	}
	
}
