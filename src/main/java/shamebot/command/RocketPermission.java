package shamebot.command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class RocketPermission
{
	private PermissionHandler permissionHandler;
	private boolean op = true;
	private Logger log = Logger.getLogger("Minecraft");
	
	public RocketPermission()
	{
		Plugin permissionsPlugin = Bukkit.getServer().getPluginManager().getPlugin("Permissions");
		
	    if (permissionsPlugin == null) {
	        log.info("Rocket: Permission system not detected, defaulting to OP");
	        return;
	    }
	    
	    op = false;
	    setPermissionPlugin(permissionsPlugin);
	}
	
	private void setPermissionPlugin(Plugin plugin)
	{
	    permissionHandler = ((Permissions) plugin).getHandler();
	    log.info("Rocket: Found and will use plugin "+((Permissions)plugin).getDescription().getFullName());
	}
	
	public boolean hasPermission(CommandSender sender, String node)
	{
		if(op || !(sender instanceof Player))
		{
			return sender.isOp();
		}
		else
		{
			return permissionHandler.has((Player)sender, node);
		}
	}
	
}
