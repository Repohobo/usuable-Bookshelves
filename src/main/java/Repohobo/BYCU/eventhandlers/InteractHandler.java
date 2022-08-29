package Repohobo.BYCU.eventhandlers;

import Repohobo.BYCU.Bookshelvesyoucanuse;
import Repohobo.BYCU.Objects.Bookshelfinventory;
import Repohobo.BYCU.data.TemporaryData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.awt.print.Book;

public class InteractHandler implements Listener {
    private TemporaryData temporaryData;
    private Bookshelvesyoucanuse bookshelvesyoucanuse;

    public InteractHandler(TemporaryData temporaryData, Bookshelvesyoucanuse bookshelvesyoucanuse) {
        this.temporaryData = temporaryData;
        this.bookshelvesyoucanuse = bookshelvesyoucanuse;
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        if (temporaryData.isPlayeronCooldown(player)) {
            return;

        }

        if (block.getType() == Material.BOOKSHELF) {
            player.sendMessage("You Rummage through the bookshelf!");
            Bookshelfinventory inventory =new Bookshelfinventory(block.getLocation());
            player.openInventory(inventory.getInventory());
            temporaryData.addplayertoplayersoninteractcooldown(player);
            removeplayerfromcooldownlistwithdelay(player);


        }

    }
    private void removeplayerfromcooldownlistwithdelay(Player player){
        int seconds = 2;
        Bukkit.getServer().getScheduler().runTaskLater(bookshelvesyoucanuse, new Runnable() {
            @Override
            public void run() {
                temporaryData.removeplayerfromplayersoninteractcooldown(player);

            }
        }, seconds * 20);
    }
}
