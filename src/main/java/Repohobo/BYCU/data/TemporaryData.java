package Repohobo.BYCU.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TemporaryData {
    private List<UUID> playersoninteractCooldown = new ArrayList<>();

    public boolean isPlayeronCooldown(Player player) {
        return playersoninteractCooldown.contains(player.getUniqueId());
    }

    public void addplayertoplayersoninteractcooldown(Player player) {
        playersoninteractCooldown.add(player.getUniqueId());
    }

    public void removeplayerfromplayersoninteractcooldown(Player player) {
        playersoninteractCooldown.remove(player.getUniqueId());
    }

}
