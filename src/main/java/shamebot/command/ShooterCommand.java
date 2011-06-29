package shamebot.command;

import org.bukkit.event.player.PlayerInteractEntityEvent;

import shamebot.rocket.Rocket;


public class ShooterCommand extends ModeCommand  implements IPlayerInteractEntityListener{

	private static final String NODE = "rocket.shooter";
	private static final String NODE_OTHER = "rocket.shooter.other";
	private static final String NAME = "shooter";
	
	public ShooterCommand(Rocket rocket) {
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

	public void onPlayerInteractEntity(PlayerInteractEntityEvent e, int i) {
		rocket.launch(e.getRightClicked(), 10, e.getPlayer().getLocation().getDirection().normalize().multiply(3), i, false, 1);
	}

}
