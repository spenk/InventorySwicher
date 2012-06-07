import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;


public class InventorySwicherListener extends PluginListener{
	String dir = "plugins/config/InventorySwicher/";
    Logger log = Logger.getLogger("Minecraft");
	HashMap<String,String> worlds = new HashMap<String,String>();
    
	
	 public void onLogin(Player player){
		 File f = new File(dir+player.getName()+".properties");
		 if (!f.exists()){
			 try {
				f.createNewFile();
			} catch (IOException e) {
				log.info("[InventorySwicher] - could not create file for "+player.getName());
			}
		 }
		 worlds.put(player.getName(), player.getWorld().getName());
	 }
	 
	 
	 public void onPlayerMove(Player player,Location from,Location to){
		 if (worlds.containsKey(player.getName())){
		 if (!to.getWorld().getName().equals(worlds.get(player.getName()))){
			 PropertiesFile f = new PropertiesFile(dir+player.getName()+".properties");
			 if (f.containsKey(to.getWorld().getName())){
				 saveinv(player,worlds.get(player.getName()));
				 clearinv(player);
				 restoreinv(to.getWorld().getName(),player);
				 worlds.remove(player.getName()); worlds.put(player.getName(), player.getWorld().getName());
			 }else{
			 saveinv(player,worlds.get(player.getName()));
			 clearinv(player);
			 worlds.remove(player.getName()); worlds.put(player.getName(), player.getWorld().getName());
			 }
		 }
		 }
	 }
	 
		public boolean onCommand(Player player,String[] split){
			if (split[0].equalsIgnoreCase("/sinv")){
			saveinv(player,player.getWorld().getName());	
			player.sendMessage("saved!");
			return true;
			}
			if (split[0].equalsIgnoreCase("/cinv")){
				clearinv(player);
				player.sendMessage("cleared");
				return true;
			}
			if (split[0].equalsIgnoreCase("/rinv")){
				restoreinv(player.getWorld().getName(),player);
				player.sendMessage("restored");
				return true;
			}
			return false;
			}
		
		public void restoreinv(String world, Player player){
			PropertiesFile f = new PropertiesFile(dir+player.getName()+".properties");
			String toret = f.getProperty(world);
			if (toret == null){return;}
			String[] itemarray = toret.split("/");
			for(String i : itemarray){
			String[] id = i.split(",");
			int itemid = Integer.parseInt(id[0]);
			int amount = Integer.parseInt(id[1]);
			int damage = Integer.parseInt(id[2]);
			int slot = Integer.parseInt(id[3]);
			if (id[4].equals("null")){
				player.getInventory().setSlot(itemid, amount, damage, slot);
			}else{
			String[] enchantments = id[4].split(";");
			Item item = new Item(itemid, amount, damage, slot);
			addenchantments(enchantments,item);
			player.getInventory().setSlot(item, slot);
			}
			}
		}
	 
		public void clearinv(Player player){
			Item[] inv = player.getInventory().getContents();
			if (inv == null){return;}
			for(Item item : inv){
				if (item != null){
					player.getInventory().removeItem(item);
				}
			}
			return;
		}
		
		public void saveinv(Player player,String worldname){
			PropertiesFile f = new PropertiesFile(dir+player.getName()+".properties");
			StringBuilder tosave = new StringBuilder();
			Item[] inv = player.getInventory().getContents();
			String s;
			for (Item item : inv){
				if (item != null){
					if (item.getEnchantments() != null){
						s = item.getItemId()+","+item.getAmount()+","+item.getDamage()+","+item.getSlot()+","+getenchantments(item);
					}else{
						s = item.getItemId()+","+item.getAmount()+","+item.getDamage()+","+item.getSlot()+","+"null";
					}
					if (item.getSlot() != inv[getsize(player)].getSlot()){
					tosave.append(s+"/");
					}
					else{
					tosave.append(s);
					}
					log.info(s);
					log.info(tosave.toString());
			}
			}
			f.setString(worldname, tosave.toString());
		}
	 
		public String getenchantments(Item item){
			StringBuilder sb = new StringBuilder();
			Enchantment[] enchantments = item.getEnchantments();
			int i=0;
			while (i < enchantments.length){
				sb.append(enchantments[i].getType().toString()+ "-");
				if (i == enchantments.length-1){
					sb.append(enchantments[i].getLevel());}else{sb.append(enchantments[i].getLevel()+";");
					}
				i++; 
			}
			return sb.toString();
		}
		public void addenchantments(String[] enchantments,Item item){
			for (String Enchantments : enchantments){
				String[] iel = Enchantments.split("-");
				int level = Integer.parseInt(iel[1]);
			item.addEnchantment(Enchantment.Type.valueOf(iel[0]), level);
			}
		}
		public int getsize(Player player){
			int i =40;
			while (player.getInventory().getItemFromSlot(i) == null){i--;}
			return i;
		}
}