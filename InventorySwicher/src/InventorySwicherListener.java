import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class InventorySwicherListener extends PluginListener{
	 static Logger log = Logger.getLogger("Minecraft");
	
	 
	 public void onLogin(Player player){
		 File f = new File("plugins/config/InventorySwicher/"+player.getName()+".properties");
		 if (!f.exists()){
		 PropertiesFile f1 = new PropertiesFile("plugins/config/InventorySwicher/"+player.getName()+".properties");
		 f1.save();
		 }
	 }
	public boolean onTeleport(Player player,Location from,Location to){
			PropertiesFile file = new PropertiesFile("plugins/config/InventorySwicher/"+player.getName()+".properties");
			saveinv(player,from.getWorld().getName());
			log.info("looping to inventory saver..");
			if (file.containsKey(to.getWorld().getName())){
				setinv(player,to.getWorld().getName());
		}
		return false;
}
	public void setinv(Player player,String world){
		PropertiesFile f = new PropertiesFile("plugins/config/InventorySwicher/"+player.getName()+".properties");
		if (f.containsKey(world)){
		String inventory = f.getString(world);
		if (inventory.equals("")){return;}
		if (inventory.contains(":")){
			String[] inv = inventory.split(":");
			List<String> list = Arrays.asList(inv);
			int length = list.size();
			int i = 0;
			
			while (length < i){
				if (!list.get(i).equals("")){
					String[] ia = list.get(i).split(",");
					Inventory invs = player.getInventory();
					Item item = new Item(a(ia[0]), a(ia[1]),a(ia[2]));
					invs.setSlot(item, a(ia[3]));
					i++;
				}
				i++;
			}
		}
		String[] inv = inventory.split(",");
		player.getInventory().getEmptySlot();
		Item item = new Item(a(inv[0]),a(inv[1]),a(inv[2]));
		Inventory in = player.getInventory();
		in.setSlot(item, a(inv[3]));
		}
	}
	
	public void saveinv(Player player,String world){
		log.info("[InventorySwicher] - "+player.getName()+" Inventory saving...");
		 PropertiesFile f = new PropertiesFile("plugins/config/InventorySwicher/"+player.getName()+".properties");
		 int i = 0;
		 String s = "";
		 Inventory inv = player.getInventory();
		 while (i != 35){
			 Item item = inv.getItemFromSlot(i);
			 if (item != null){
				 String add = item.getItemId()+","+item.getAmount()+","+item.getDamage()+","+i;
				 s = s+":"+add;
				 inv.removeItem(i);
				 i++;
			 }
			 i++;
		 }
		 Item ar1 = inv.getItemFromSlot(100);
		 if (ar1 != null){
			 String add = ar1.getItemId()+","+ar1.getAmount()+","+ar1.getDamage()+","+100;
			 s = s+":"+add;
			 inv.removeItem(100);
		 }
		 Item ar2 = inv.getItemFromSlot(101);
		 if (ar2 != null){
			 String add = ar2.getItemId()+","+ar2.getAmount()+","+ar2.getDamage()+","+101;
			 s = s+":"+add;
			 inv.removeItem(101);
		 }
		 Item ar3 = inv.getItemFromSlot(102);
		 if (ar3 != null){
			 String add = ar3.getItemId()+","+ar3.getAmount()+","+ar3.getDamage()+","+102;
			 s = s+":"+add;
			 inv.removeItem(102);
		 }
		 Item ar4 = inv.getItemFromSlot(102);
		 if (ar4 != null){
			 String add = ar4.getItemId()+","+ar4.getAmount()+","+ar4.getDamage()+","+103;
			 s = s+":"+add;
			 inv.removeItem(103);
		 }
		 f.setString(world, s);
		 log.info("[InventorySwicher] - inventory saved...");
	}
	
	public int a(String s){
		int toret = -1;
		try{toret = Integer.parseInt(s);}catch(NumberFormatException nfe){log.info("[InventorySwicher] - error curing convert int -> item");}
		return toret;
	}
}