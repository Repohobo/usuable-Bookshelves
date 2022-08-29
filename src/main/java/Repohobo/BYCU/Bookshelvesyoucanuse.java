package Repohobo.BYCU;

import Repohobo.BYCU.Objects.Bookshelfinventory;
import Repohobo.BYCU.commands.DefaultCommand;
import Repohobo.BYCU.commands.HelpCommand;
import Repohobo.BYCU.data.TemporaryData;
import Repohobo.BYCU.eventhandlers.InteractHandler;
import Repohobo.BYCU.exception.Bookshelfinventorynotfoundexception;
import Repohobo.BYCU.services.ConfigService;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import preponderous.ponder.minecraft.bukkit.abs.AbstractPluginCommand;
import preponderous.ponder.minecraft.bukkit.abs.PonderBukkitPlugin;
import preponderous.ponder.minecraft.bukkit.services.CommandService;
import preponderous.ponder.minecraft.bukkit.tools.EventHandlerRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daniel McCoy Stephenson
 */
public final class Bookshelvesyoucanuse extends PonderBukkitPlugin {
    private final String pluginVersion = "v" + getDescription().getVersion();
    private final CommandService commandService = new CommandService(getPonder());
    private final ConfigService configService = new ConfigService(this);
    private TemporaryData temporaryData = new TemporaryData();
    private ArrayList<Bookshelfinventory> bookshelfinventories = new ArrayList<>();

    /**
     * This runs when the server starts.
     */
    @Override
    public void onEnable() {
        initializeConfig();
        registerEventHandlers();
        initializeCommandService();
        System.out.println("BYCU has enabled");
    }

    /**
     * This runs when the server stops.
     */
    @Override
    public void onDisable() {

    }

    /**
     * This method handles commands sent to the minecraft server and interprets them if the label matches one of the core commands.
     * @param sender The sender of the command.
     * @param cmd The command that was sent. This is unused.
     * @param label The core command that has been invoked.
     * @param args Arguments of the core command. Often sub-commands.
     * @return A boolean indicating whether the execution of the command was successful.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            DefaultCommand defaultCommand = new DefaultCommand(this);
            return defaultCommand.execute(sender);
        }

        return commandService.interpretAndExecuteCommand(sender, label, args);
    }

    /**
     * This can be used to get the version of the plugin.
     * @return A string containing the version preceded by 'v'
     */
    public String getVersion() {
        return pluginVersion;
    }

    /**
     * Checks if the version is mismatched.
     * @return A boolean indicating if the version is mismatched.
     */
    public boolean isVersionMismatched() {
        String configVersion = this.getConfig().getString("version");
        if (configVersion == null || this.getVersion() == null) {
            return false;
        } else {
            return !configVersion.equalsIgnoreCase(this.getVersion());
        }
    }

    /**
     * Checks if debug is enabled.
     * @return Whether debug is enabled.
     */
    public boolean isDebugEnabled() {
        return configService.getBoolean("debugMode");
    }

    public ArrayList<Bookshelfinventory> getBookshelfinventories(){
        return bookshelfinventories;
    }

    public Bookshelfinventory getBookshelfInventory(Location location) throws Bookshelfinventorynotfoundexception {
        for (Bookshelfinventory bookshelf : bookshelfinventories) {
            if (bookshelf.getX() == location.getBlockX() && bookshelf.getY() == location.getBlockY() && bookshelf.getZ() == location.getBlockZ() && bookshelf.getWorldname() .equals(location.getWorld().getName())) {
                return bookshelf;

            }
        }
        throw new Bookshelfinventorynotfoundexception();
    }

    private void initializeConfig() {
        if (configFileExists()) {
            performCompatibilityChecks();
        }
        else {
            configService.saveMissingConfigDefaultsIfNotPresent();
        }
    }

    private boolean configFileExists() {
        return new File("./plugins/" + getName() + "/config.yml").exists();
    }

    private void performCompatibilityChecks() {
        if (isVersionMismatched()) {
            configService.saveMissingConfigDefaultsIfNotPresent();
        }
        reloadConfig();
    }

    /**
     * Registers the event handlers of the plugin using Ponder.
     */
    private void registerEventHandlers() {
        EventHandlerRegistry eventHandlerRegistry = new EventHandlerRegistry();
        ArrayList<Listener> listeners = new ArrayList<>(Arrays.asList(

                new InteractHandler(temporaryData, this)
        ));
        eventHandlerRegistry.registerEventHandlers(listeners, this);
    }

    /**
     * Initializes Ponder's command service with the plugin's commands.
     */
    private void initializeCommandService() {
        ArrayList<AbstractPluginCommand> commands = new ArrayList<>(Arrays.asList(
                new HelpCommand()
        ));
        commandService.initialize(commands, "That command wasn't found.");
    }
}