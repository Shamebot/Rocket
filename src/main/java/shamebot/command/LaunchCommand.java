package shamebot.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import shamebot.rocket.Rocket;


public class LaunchCommand extends StoppingType1Command {

	private static final String NODE = "rocket.launch";
	private static final String NODE_OTHER = "rocket.launch.other";
	
	public LaunchCommand(Rocket rocket)
	{
		super(rocket);
	}
	
	@Override
	protected void execute(Entity entity, int duration, CommandSender sender)
	{
    	rocket.launch(entity, 10, new Vector(0,3,0), duration, false, 1);
	}

	public String getNode() {
		return NODE;
	}

	public String getNodeOther() {
		return NODE_OTHER;
	}


}
