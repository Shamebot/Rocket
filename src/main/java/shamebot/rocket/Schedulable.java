package shamebot.rocket;

import org.bukkit.Bukkit;

public abstract class Schedulable implements Runnable{

	protected static Rocket rocket;
	private int id = -1;
	private boolean finished = false;
	public static void setRocket(Rocket rocket)
	{
		Schedulable.rocket =rocket;
	}
	
	public static void scheduleSyncRepeatingTask(Schedulable task, int period) throws IllegalStateException
	{
		if(rocket != null)
		{
			task.setId(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(rocket, task, 0, period));
		}
		else
		{
			throw new java.lang.IllegalStateException("Could not schedule task, plugin uninitialized");
		}
	}
	
	private void setId(int id)
	{
		this.id = id;
	}
	
	private void cancel()
	{
		if(id != -1)
		{
			Bukkit.getServer().getScheduler().cancelTask(id);
		}
	}
	
	public void stop()
	{
		finished = true;
	}
	
	public  void run()
	{
		if(!finished)
			tick();
		if(finished)
			cancel();
	}
	protected abstract void tick();
}
