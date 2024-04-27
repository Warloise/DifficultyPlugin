package dp.warloise.commands;

import dp.warloise.DifficultyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static dp.warloise.DifficultyPlugin.*;

public class commandSelectionElection implements CommandExecutor, TabCompleter {

    private final DifficultyPlugin plugin;

    public commandSelectionElection(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el jugador tiene permisos para usar el comando
        if (!sender.hasPermission("selectionelection.config")) {
            sender.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }
        if (args[0].equals("random")){
            int randomNum = (int) (Math.random() * 22) + 1;
            switch (randomNum) {
                case 1:
                    b_election1 = true;
                    break;
                case 2:
                    b_election2 = true;
                    break;
                case 3:
                    b_election3 = true;
                    break;
                case 4:
                    b_election4 = true;
                    break;
                case 5:
                    b_election5 = true;
                    break;
                case 6:
                    b_election6 = true;
                    break;
                case 7:
                    b_election7 = true;
                    break;
                case 8:
                    b_election8 = true;
                    break;
                case 9:
                    b_election9 = true;
                    break;
                case 10:
                    b_election10 = true;
                    break;
                case 11:
                    if (Bukkit.getOnlinePlayers().size()>=2){
                        b_election11 = true;
                    }else{
                        b_election10 = true;
                    }
                    break;
                case 12:
                    b_election12 = true;
                    break;
                case 13:
                    b_election13 = true;
                    break;
                case 14:
                    b_election14 = true;
                    break;
                case 15:
                    b_election15 = true;
                    break;
                case 16:
                    b_election16 = true;
                    break;
                case 17:
                	b_election17 = true;
                    break;
                case 18:
                    b_election18 = true;
                    break;
                case 19:
                	b_election19 = true;
                	break;
                case 20:
                	b_election20 = true;
                	break;
                case 21:
                	b_election21 = true;
                	break;
                case 22:
                    b_election22 = true;
                    break;
            }
        }
        if (args[0].equals("election1")){
            b_election1=true;
        }
        if (args[0].equals("election2")){
            b_election2=true;
        }
        if (args[0].equals("election3")){
            b_election3=true;
        }
        if (args[0].equals("election4")){
            b_election4=true;
        }
        if (args[0].equals("election5")){
            b_election5=true;
        }
        if (args[0].equals("election6")){
            b_election6=true;
        }
        if (args[0].equals("election7")){
            b_election7=true;
        }
        if (args[0].equals("election8")){
            b_election8=true;
        }
        if (args[0].equals("election9")){
            b_election9=true;
        }
        if (args[0].equals("election10")){
            b_election10=true;
        }
        if (args[0].equals("election11")){
            if (Bukkit.getOnlinePlayers().size()>=2){
                b_election11 = true;
            }else{
                b_election10 = true;
            }
        }
        if (args[0].equals("election12")){
            b_election12=true;
        }
        if (args[0].equals("election13")){
            b_election13=true;
        }
        if (args[0].equals("election14")){
            b_election14=true;
        }
        if (args[0].equals("election15")){
            b_election15=true;
        }
        if (args[0].equals("election16")){
            b_election16=true;
        }
        if (args[0].equals("election17")){
            b_election17=true;
        }
        if (args[0].equals("election18")){
            b_election18 = true;
        }
        if (args[0].equals("election19")){
            b_election19 = true;
        }
        if (args[0].equals("election20")){
            b_election20 = true;
        }
        if (args[0].equals("election21")){
            b_election21 = true;
        }
        if (args[0].equals("election22")){
            b_election22 = true;
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("random");
            for (int i=1;i<=22;i++){
                completions.add("election"+i);
            }
        }
        // Return a list of completions based on the user's input
        return completions;
    }
}
