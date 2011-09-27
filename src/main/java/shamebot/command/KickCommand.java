package shamebot.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import shamebot.rocket.Rocket;

public class KickCommand extends LaunchCommand {

    private static final String NODE = "rocket.kick";
    private static final String NODE_OTHER = "rocket.kick.other";
    
    public KickCommand(Rocket rocket) {
        super(rocket);
    }

    @Override
    protected void execute(final Entity entity, int duration, CommandSender sender)
    {
        super.execute(entity, duration, sender);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(rocket, new Runnable()
        {
            public void run()
            {
                if(entity instanceof Player)
                {
                    Player player = (Player)entity;
                    Location loc = player.getLocation();
                    loc.setY(loc.getWorld().getHighestBlockYAt(loc)+1);
                    player.teleport(loc);
                    player.kickPlayer("");
                }
            }
        }, duration+1);
    }
    
    public String getNode() {
        return NODE;
    }

    public String getNodeOther() {
        return NODE_OTHER;
    }
}
