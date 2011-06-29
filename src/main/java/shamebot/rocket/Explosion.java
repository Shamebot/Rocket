package shamebot.rocket;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Explosion extends Exploding {

	private static final int PERIOD = 2;
	private static final int RADIUS = 2;
	
	private Entity entity;
	private Inventory inv;
	
	public Explosion(Entity entity, Inventory inv) {
		super(entity);
		this.entity = entity;
		this.inv = inv;
		explode();
	}

	private void explode()
	{
		if(inv != null)
		{
			for(ItemStack item : inv.getContents())
			{
				if(item == null)
					continue;
				Exploding exploding = new Exploding(entity.getWorld().dropItem(entity.getLocation(), item));
				Schedulable.scheduleSyncRepeatingTask(exploding, PERIOD);
			}
			inv.clear();
		}
		
		Smoke smoke = new Smoke(entity.getLocation());
		smoke.addSphere(entity.getLocation(), RADIUS);
		Schedulable.scheduleSyncRepeatingTask(smoke, PERIOD);
	}
}
