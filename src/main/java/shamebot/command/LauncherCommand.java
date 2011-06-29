package shamebot.command;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import shamebot.rocket.Rocket;


public class LauncherCommand extends ModeCommand implements IPlayerInteractEntityListener{

	private static final String NODE = "rocket.launcher";
	private static final String NODE_OTHER = "rocket.launcher.other";
	private static final String NAME = "launcher";
	
	public LauncherCommand(Rocket rocket) {
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
		rocket.launch(e.getRightClicked(), 10, new Vector(0,3,0), i, false, 1);
	}

}
