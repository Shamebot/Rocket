package shamebot.command;

import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import shamebot.rocket.Rocket;

public abstract class StoppingType1Command extends Type1Command implements IHasPermissionNode{

	public StoppingType1Command(Rocket rocket) {
		super(rocket);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
		if(args.length > 0)
		{
			if(args[0].equalsIgnoreCase("stop"))
	    	{
	    		return rocket.stop.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1,args.length));
	    	}
		}
		return decode(sender, args, getNode(), getNodeOther());
		
    }
}
