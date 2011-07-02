package shamebot.rocket;

import java.util.HashSet;

import net.minecraft.server.ChunkPosition;
import net.minecraft.server.Packet60Explosion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Smoke extends Schedulable
{
	static final float F_START = 0.1F;//unknown value defines something like the speed of the particles/how they spread
	static final int DURATION = 20;
	
	private int ticks = 0, duration =  DURATION;
	private HashSet<ChunkPosition> blocks = new HashSet<ChunkPosition>();//the blocks to destroy/to spawn particles at
	Location loc;//the origin of the explosion, determines the direction of the particles, color(white/black)
	float f = F_START;
	
	public Smoke(Location loc)
	{
		this.loc = loc;
	}
	
	public void addSphere(Location loc, int radius)
	{
		int squRadius = radius * radius;
		for(int x = - radius; x <= radius; x++)
		{
			for(int y = - radius; y <= radius; y++)
			{
				for(int z = - radius; z <= radius; z++)
				{
					if(x*x+y*y+z*z+x+y+z+0.75 < squRadius)
					{
						blocks.add(new ChunkPosition(
								(int)(loc.getX()+x+0.5),
								(int)(loc.getY()+y+0.5),
								(int)(loc.getZ()+z+0.5)));
					}
				}
			}
		}
	}
	//adds a ray of blocks to the set
	public void addRay(Location loc, Vector vec, int length)
	{
		BlockIterator i = new BlockIterator(loc.getWorld(), loc.toVector(), vec, 0, length);
		Block block;
		while(i.hasNext())
		{
			block = i.next();
			if(block.getType() == Material.AIR)
			{
				blocks.add(new ChunkPosition(block.getX(),block.getY(),block.getZ()));
			}
		}
	}
	//sends the smoke packet to all players in radius 64 basically notch's explosion code, dunno why 64 since you can only see it in radius 8, 
	public void sendAll()
	{																			
		((CraftServer)Bukkit.getServer()).getServer().serverConfigurationManager.sendPacketNearby(loc.getX(),loc.getY(),loc.getZ(),64,((CraftWorld)loc.getWorld()).getHandle().dimension, new Packet60Explosion(loc.getX(),loc.getY(),loc.getZ(),f,blocks));
	}

	//is called from scheduler every X ticks
	@Override
	protected void tick()
	{
		if(ticks++ < duration)
		{
			sendAll();
		}
		else
		{
			stop();
		}
	}
}
