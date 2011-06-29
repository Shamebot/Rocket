package shamebot.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import shamebot.rocket.Rocket;


public abstract class ModeCommand extends Type1Command implements IHasPermissionNode{

	static protected HashMap<Entity,Pair<ModeCommand,Integer>> inMode = new HashMap<Entity,Pair<ModeCommand,Integer>>();
	
	public ModeCommand(Rocket rocket) {
		super(rocket);
	}

	static PlayerListener playerListener = new PlayerListener()
	{
		@Override
		public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
		{
			Pair<ModeCommand,Integer> pair = inMode.get(e.getPlayer());
			if(pair != null && pair.item1 instanceof IPlayerInteractEntityListener)
			{
				((IPlayerInteractEntityListener)pair.item1).onPlayerInteractEntity(e, pair.item2);
			}
		}
		
		@Override
		public void onPlayerInteract(PlayerInteractEvent e)
		{
			Pair<ModeCommand,Integer> pair = inMode.get(e.getPlayer());
			if(pair != null && pair.item1 instanceof IPlayerInteractListener)
			{
				((IPlayerInteractListener)pair.item1).onPlayerInteract(e, pair.item2);
			}
		}
	};

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
		if(args.length == 0)
		{
			if(sender instanceof Player)
			{
				inMode.remove(sender);
				sender.sendMessage("Left Mode");
				return true;
			}
			else
			{
				throw new NotImplementedException();
			}
		}
		else
		{
			return decode(sender, args, getNode(), getNodeOther());
		}
    }
	
	@Override
	protected void execute(Entity entity, int i, CommandSender sender)
	{
		inMode.put(entity, new Pair<ModeCommand,Integer>(this,i));
		sender.sendMessage("Entered mode " + getName());
	}
	
	public static PlayerListener getListener()
	{
		return playerListener;
	}

	protected abstract String getName();
	
	@SuppressWarnings("serial")
	private class NotImplementedException extends RuntimeException {}
	
	private class Pair<T1,T2>
	{
		public T1 item1;
		public T2 item2;
		public Pair(T1 t1,T2 t2)
		{
			item1 = t1;
			item2 = t2;
		}
	}
}
