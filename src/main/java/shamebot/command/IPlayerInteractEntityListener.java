package shamebot.command;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface IPlayerInteractEntityListener {
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e, int i);
}
