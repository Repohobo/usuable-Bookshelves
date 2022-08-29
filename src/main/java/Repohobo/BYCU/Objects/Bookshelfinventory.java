package Repohobo.BYCU.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

public class Bookshelfinventory {
    private int x;
    private int y;
    private int z;
    private String worldname;
    private Inventory inventory = Bukkit.createInventory(null,9,"Bookshelf");

    public Bookshelfinventory(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.worldname = location.getWorld().getName();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorldname() {
        return worldname;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
