import java.io.File;
import java.util.logging.Logger;


public class InventorySwicher extends Plugin{
	 String name = "InventorySwicher";
	  String version = "1.0";
	  String author = "by spenk";
	  static Logger log = Logger.getLogger("Minecraft");

	  public void initialize()
	  {
		  InventorySwicherListener listener = new InventorySwicherListener();
	    log.info(this.name + " version " + this.version + " by " + this.author + " is initialized!");
	    etc.getLoader().addListener(PluginLoader.Hook.TELEPORT, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.LOGIN, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.PLAYER_MOVE, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);

	    File f1 = new File("plugins/config");
	    f1.mkdir();
	    File f2 = new File("plugins/config/InventorySwicher");
	    f2.mkdir();
	  }

	  public void enable() {
	    log.info(this.name + " version " + this.version + " by " + this.author + " is enabled!");
	  }

	  public void disable() {
	    log.info(this.name + " version " + this.version + " is disabled!");
}
}
