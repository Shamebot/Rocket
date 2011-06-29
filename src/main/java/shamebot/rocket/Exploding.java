package shamebot.rocket;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class Exploding extends Schedulable{

	static final int FIRE_TICKS = 1000;
	//static final float STOP = 0.1F;
	static final int PROBABILITY = 5;
	static final int SMOKE_PERIOD = 2;
	static final int TAIL = 7;
	static final int DURATION = 10;
	
	private Entity entity;
	private Random random;
	private int ticks;
	
	public Exploding(Entity entity) {
		entity.setFireTicks(FIRE_TICKS);
		this.entity = entity;
		random = new Random();
	}

	@Override
	protected void tick()
	{
		/*if(-STOP < entity.getVelocity().getY() && entity.getVelocity().getY() < STOP)
		{
			stop();
			return;
		}*/
		if(ticks++ > DURATION)
		{
			stop();
			return;
		}
		
		if(random.nextInt(PROBABILITY) == 0)
		{
			float x = random.nextFloat();
			float y = random.nextFloat();
			float z = random.nextFloat();
			
			Location loc = entity.getLocation();
			Smoke smoke = new Smoke(loc);
			smoke.addSphere(entity.getLocation(), (int)((x+y+z)*2+0.5F));
			Schedulable.scheduleSyncRepeatingTask(smoke, SMOKE_PERIOD);
			
			entity.setVelocity(new Vector(x - 0.5, y - 0.5, z - 0.5).multiply(2));
			
			//Bukkit.getServer().broadcastMessage("random kick: "+(int)((x+y+z)*2+0.5F));
		}
		

		Smoke smoke = new Smoke(entity.getLocation());
		smoke.addRay(entity.getLocation(), entity.getVelocity().multiply(-1), TAIL);
		Schedulable.scheduleSyncRepeatingTask(smoke, SMOKE_PERIOD);
	}

}
