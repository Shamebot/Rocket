package shamebot.rocket;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

public class Rocketeer extends Schedulable{

	static private final int SMOKE_PERIOD = 2;
	static private final int EXPLOSION_TICKS = 20;
	
	private Entity entity;
	private Vector vec;
	private int ticks, duration, tail;
	private boolean explode;
	
	public Rocketeer(Entity entity, int tail, Vector vec, int duration)
	{
		this.tail = tail;
		this.duration = duration;
		this.vec = vec;
		this.entity = entity;
	}
	
	public Rocketeer(Entity entity, int tail, Vector vec, int duration, boolean explode)
	{
		this(entity, tail, vec, duration);
		if(explode)
		{
			this.explode = true;
		}
		else
		{
			this.explode = new Random().nextInt(1000) == 0;
		}
	}

	@Override
	protected void tick()
	{
		if(ticks++ > duration)
		{
			stop();
			return;
		}
		if(explode && ticks > EXPLOSION_TICKS)
		{
			Inventory inv = null;
			if(entity instanceof HumanEntity)
			{
				inv = ((HumanEntity)entity).getInventory();
			}
			else if (entity instanceof StorageMinecart)
			{
				inv = ((StorageMinecart)entity).getInventory();
			}
			Schedulable.scheduleSyncRepeatingTask(new Explosion(entity, inv), 1);
		}
		entity.setVelocity(vec);
		Location loc = entity.getLocation().add(0, 1, 0);
		Smoke smoke = new Smoke(loc);
		try
		{
		smoke.addRay(entity.getLocation(), entity.getVelocity().multiply(-1), tail);
		Schedulable.scheduleSyncRepeatingTask(smoke, SMOKE_PERIOD);
		}
		catch (IllegalStateException e)
		{
			Bukkit.getServer().broadcastMessage("failed to create smoke");
			System.out.println("failed to create smoke");
		}
	}

	public Entity getEntity()
	{
		return entity;
	}
	
	public Vector getVector()
	{
		return vec.clone();
	}
	
	public void setVector(Vector vector)
	{
		vec = vector;
	}
}
