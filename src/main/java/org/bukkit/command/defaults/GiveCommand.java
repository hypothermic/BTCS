package org.bukkit.command.defaults;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends VanillaCommand {
    public GiveCommand() {
        super("give");
        this.description = "Gives the specified player a certain amount of items";
        this.usageMessage = "/give <player> <item> [amount [data]]";
        this.setPermission("bukkit.command.give");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        if ((args.length < 2) || (args.length > 4)) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        Player player = Bukkit.getPlayerExact(args[0]);

        if (player != null) {
        	// BTCS start: added support for :<data> within args[1] 
        	// (for example, you can do '/give player 215:1 32' instead of '/give player 215 32 1')
        	Material material;
        	// arr3 to prevent ArrayIndexOutOfBoundsException
        	String arr3 = null;
        	if (args.length >= 4) {
        		arr3 = args[3];
        	}
        	if (containsOnce(args[1], ":")) {
        		material = Material.matchMaterial(args[1].split(":")[0]);
        		arr3 = args[1].split(":")[1]; // don't parse short here, do it later in original code.
        	} else {
        		material = Material.matchMaterial(args[1]);
        	}
        	//Material material = Material.matchMaterial(args[1]);
        	// BTCS end
            
            if (material != null) {
                Command.broadcastCommandMessage(sender, "Giving " + player.getName() + " some " + material.getId() + " (" + material + ")");

                int amount = 1;
                short data = 0;

                if (args.length >= 3) {
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException ex) {}

                    if (amount < 1) amount = 1;
                    if (amount > 64) amount = 64;
                    // BTCS: moved if length>=4 to below
                }
                if (args.length >= 4 || arr3 != null) { // BTCS: added '|| arr3 != null'
                    try {
                    	// BTCS start
                        //data = Short.parseShort(args[3]);
                    	data = Short.parseShort(arr3);
                    	// BTCS end
                    } catch (NumberFormatException ex) {}
                }

                player.getInventory().addItem(new ItemStack(material, amount, data));
            } else {
                sender.sendMessage("There's no item called " + args[1]);
            }
        } else {
            sender.sendMessage("Can't find user " + args[0]);
        }

        return true;
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("give");
    }
    
    // BTCS start
    // from: https://codereview.stackexchange.com/questions/161386/checking-whether-a-string-contains-a-substring-only-once
    private boolean containsOnce(final String s, final CharSequence substring) {
    	Pattern pattern = Pattern.compile(substring.toString());
    	Matcher matcher = pattern.matcher(s);
    	return matcher.find() && !matcher.find();
    }
    // BTCS end
}
