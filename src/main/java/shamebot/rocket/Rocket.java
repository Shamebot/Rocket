package shamebot.rocket;

import java.util.ArrayList;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import shamebot.command.FlyCommand;
import shamebot.command.LaunchCommand;
import shamebot.command.LauncherCommand;
import shamebot.command.ModeCommand;
import shamebot.command.RocketPermission;
import shamebot.command.ShootCommand;
import shamebot.command.ShooterCommand;
import shamebot.command.StopCommand;



public class Rocket extends JavaPlugin {

	private ArrayList<Rocketeer> flying = new ArrayList<Rocketeer>();
	private Logger log = Logger.getLogger("Minecraft");
	private RocketPermission rocketPermission;
	
	LaunchCommand launch;
	LauncherCommand launcher;
	public StopCommand stop;
	ShootCommand shoot;
	ShooterCommand shooter;
	FlyCommand fly;
	
    public void onDisable() {
    	PluginDescriptionFile pdFile = getDescription();
        log.info(pdFile.getName() + " version " + pdFile.getVersion() + " disabled!");
    }

    public void onEnable() {
    	EntityListener entityListener = new EntityListener()
    	{
    		@Override
    		public void onEntityDamage(EntityDamageEvent e)
    		{
    			if(e.getCause() == DamageCause.FALL)
    			{
        			Rocketeer rocketeer = getRocketeer(e.getEntity());
        			if(rocketeer != null)
        			{
	    				e.setCancelled(true);
	    				flying.remove(rocketeer);
        			}
    			}
    		}
    	};
    	
        PlayerListener playerListener = new PlayerListener()
        {
        	@Override
        	public void onPlayerKick(PlayerKickEvent e)
        	{
        		if(e.getReason().startsWith("You moved too") || e.getReason().startsWith("Flying is not"))
        		{
        			Rocketeer rocketeer = getRocketeer(e.getPlayer());
        			if(rocketeer != null)
        			{
        				e.setCancelled(true);
        			}
        		}
        	}
        	
        	@Override
        	public void onPlayerInteract(PlayerInteractEvent e)
        	{
        		Player player = e.getPlayer();
        		Vector vec1 = player.getVelocity();
        		Vector vec2 = player.getLocation().getDirection();
        		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        		{
        			player.setVelocity(vec1.add(vec2.multiply(3)));
        		}
        		else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        		{
        			player.setVelocity(vec1.add(vec2.multiply(-3)));
        		}
        	}
        };
        
        log.setFilter(new Filter()
        {
        	public boolean isLoggable(LogRecord record) {
        		if(record.getMessage() != null)
        		{
        			if(record.getMessage().endsWith("was kicked for floating too long!"))
        			{
        				return false;
        			}
        		}
        		return true;
			}
        	
        });
        
        Schedulable.setRocket(this);
        
        launch =  new LaunchCommand(this);
        launcher = new LauncherCommand(this);
        stop = new StopCommand(this);
        shoot = new ShootCommand(this);
        shooter = new ShooterCommand(this);
        //fly = new FlyCommand(this);
        
        rocketPermission = new RocketPermission();
        
        getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.High, this);
        getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT_ENTITY, ModeCommand.getListener(), Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, ModeCommand.getListener(), Priority.Normal, this);
        //getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Type.PLAYER_KICK, playerListener, Priority.Normal, this);
        
        getCommand("launch").setExecutor(launch);
        getCommand("launcher").setExecutor(launcher);
        getCommand("halt").setExecutor(stop);
        getCommand("shoot").setExecutor(shoot);
        getCommand("shooter").setExecutor(shooter);
        //getCommand("fly").setExecutor(fly);
        
    	PluginDescriptionFile pdFile = getDescription();
        log.info(pdFile.getName() + " version " + pdFile.getVersion() + " enabled!" );
    }
    
    public void stop(Entity entity)
    {
    	Rocketeer rocketeer = getRocketeer(entity);
		if(rocketeer != null)
		{
			rocketeer.stop();
		}
    }
    
    public Rocketeer launch(Entity entity, int tail, Vector vec, int duration, boolean explode, int period)
    {
    	Rocketeer rocketeer = new Rocketeer(entity,tail,vec,duration,explode);
    	Schedulable.scheduleSyncRepeatingTask(rocketeer,period);
    	flying.add(rocketeer);
    	return rocketeer;
    }
    
    public Rocketeer getRocketeer(Entity entity)
    {
    	for(Rocketeer rocketeer : flying)
    	{
    		if(rocketeer.getEntity() == entity)
    		{
    			return rocketeer;
    		}
    	}
    	return null;
    }
    
    public Entity getEntityById(int id)
    {
    	for(World w : Bukkit.getServer().getWorlds())
    	{
    		for(Entity e : w.getLivingEntities())
    		{
    			if(e.getEntityId() == id)
    			{
    				return e;
    			}
    		}
    	}
    	return null;
    }
    
    public boolean hasPermission(CommandSender sender, String node)
    {
    	return rocketPermission.hasPermission(sender, node);
    }
}