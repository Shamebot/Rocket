package shamebot.command;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import shamebot.rocket.Rocket;
import shamebot.rocket.Rocketeer;


public class FlyCommand extends ModeCommand implements IPlayerInteractListener{

	private static final String NODE = "rocket.fly";
	private static final String NODE_OTHER = "rocket.fly.other";
	private static final String NAME = "fly";
	
	public FlyCommand(Rocket rocket) {
		super(rocket);
	}

	protected String getName() {
		return NAME;
	}
	
	public String getNode() {
		return NODE;
	}

	public String getNodeOther() {
		return NODE_OTHER;
	}

	public void onPlayerInteract(PlayerInteractEvent e, int i) {
		Rocketeer rocketeer = rocket.getRocketeer(e.getPlayer());
		if(rocketeer == null)
		{
			rocketeer = rocket.launch(e.getPlayer(), 10, e.getPlayer().getVelocity(), Integer.MAX_VALUE, false, 1);
		}
		Vector vec = e.getPlayer().getLocation().getDirection().normalize();
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			vec.multiply(i/100D);
		}
		else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			vec.multiply(-i/100D);
		}
		rocketeer.setVector(vec.setY(vec.getY() + 0.1));
		
	}
}
