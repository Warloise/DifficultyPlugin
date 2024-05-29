package dp.warloise.commands;

import dp.warloise.DifficultyPlugin;
import dp.warloise.utils.Eleccion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static dp.warloise.DifficultyPlugin.*;
import static dp.warloise.utils.menuEleccion.*;

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
            int randomNum = (int) (Math.random() * 38) + 1;
            while(electionBlackList.contains(randomNum) && lastElection!=randomNum){
                randomNum = (int) (Math.random() * 38) + 1;
            }
            lastElection = randomNum;
            switch (randomNum) {
                case 1:
                    objetoEleccion.StartElectionMainIndividual(createMenu1());
                    break;
                case 2:
                    objetoEleccion.StartElectionMainGeneral(createMenu2());
                    break;
                case 3:
                    objetoEleccion.StartElectionMainIndividual(createMenu3());
                    break;
                case 4:
                    objetoEleccion.StartElectionMainIndividual(createMenu4());
                    break;
                case 5:
                    objetoEleccion.StartElectionMainIndividual(createMenu5());
                    break;
                case 6:
                    objetoEleccion.StartElectionMainIndividual(createMenu6());
                    break;
                case 7:
                    objetoEleccion.StartElectionMainIndividual(createMenu7());
                    break;
                case 8:
                    objetoEleccion.StartElectionMainGeneral(createMenu8());
                    break;
                case 9:
                    objetoEleccion.StartElectionMainIndividual(createMenu9());
                    break;
                case 10:
                    objetoEleccion.StartElectionMainGeneral(createMenu10());
                    break;
                case 11:
                    if (Bukkit.getOnlinePlayers().size()>=2){
                        objetoEleccion.StartElectionMainGeneral(createMenu11());
                    }else{
                        objetoEleccion.StartElectionMainGeneral(createMenu10());
                    }
                    break;
                case 12:
                    objetoEleccion.StartElectionMainIndividual(createMenu12());
                    break;
                case 13:
                    objetoEleccion.StartElectionMainIndividual(createMenu13());
                    break;
                case 14:
                    objetoEleccion.StartElectionMainIndividual(createMenu14());
                    break;
                case 15:
                    objetoEleccion.StartElectionMainGeneral(createMenu15());
                    break;
                case 16:
                    objetoEleccion.StartElectionMainGeneral(createMenu16());
                    break;
                case 17:
                    objetoEleccion.StartElectionMainIndividual(createMenu17());
                    break;
                case 18:
                    objetoEleccion.StartElectionMainIndividual(createMenu18());
                    break;
                case 19:
                    objetoEleccion.StartElectionMainGeneral(createMenu19());
                	break;
                case 20:
                    objetoEleccion.StartElectionMainGeneral(createMenu20());
                	break;
                case 21:
                    objetoEleccion.StartElectionMainGeneral(createMenu21());
                	break;
                case 22:
                    objetoEleccion.StartElectionMainGeneral(createMenu22());
                    break;
                case 23:
                    objetoEleccion.StartElectionMainGeneral(createMenu23());
                    break;
                case 24:
                    objetoEleccion.StartElectionMainIndividual(createMenu24());
                    break;
                case 25:
                    objetoEleccion.StartElectionMainGeneral(createMenu25());
                    break;
                case 26:
                    objetoEleccion.StartElectionMainGeneral(createMenu26());
                    break;
                case 27:
                    objetoEleccion.StartElectionMainGeneral(createMenu27());
                    break;
                case 28:
                    objetoEleccion.StartElectionMainIndividual(createMenu28());
                    break;
                case 29:
                    objetoEleccion.StartElectionMainGeneral(createMenu29());
                    break;
                case 30:
                    objetoEleccion.StartElectionMainGeneral(createMenu30());
                    break;
                case 31:
                    objetoEleccion.StartElectionMainGeneral(createMenu31());
                    break;
                case 32:
                    objetoEleccion.StartElectionMainGeneral(createMenu32());
                    break;
                case 33:
                    objetoEleccion.StartElectionMainGeneral(createMenu33());
                    break;
                case 34:
                    objetoEleccion.StartElectionMainIndividual(createMenu34());
                    break;
                case 35:
                    objetoEleccion.StartElectionMainIndividual(createMenu35());
                    break;
                case 36:
                    objetoEleccion.StartElectionMainIndividual(createMenu36());
                    break;
                case 37:
                    objetoEleccion.StartElectionMainIndividual(createMenu37());
                    break;
                case 38:
                    objetoEleccion.StartElectionMainIndividual(createMenu38());
                    break;
            }

            if(args.length>=2 && args[1].equals("true")){
                randomElection_Trigger = true;
                config.set("Elections.RandomElections", true);
                plugin.saveConfig();
                plugin.reloadConfig();
                time_sec_Elections=0;
            }else if(args.length>=2 && args[1].equals("false")){
                randomElection_Trigger = false;
                config.set("Elections.RandomElections", false);
                plugin.saveConfig();
                plugin.reloadConfig();
                time_sec_Elections=0;
            }
        }
        if (args[0].equals("election1")){
            objetoEleccion.StartElectionMainIndividual(createMenu1());
        }
        if (args[0].equals("election2")){
            objetoEleccion.StartElectionMainGeneral(createMenu2());
        }
        if (args[0].equals("election3")){
            objetoEleccion.StartElectionMainIndividual(createMenu3());
        }
        if (args[0].equals("election4")){
            objetoEleccion.StartElectionMainIndividual(createMenu4());
        }
        if (args[0].equals("election5")){
            objetoEleccion.StartElectionMainIndividual(createMenu5());
        }
        if (args[0].equals("election6")){
            objetoEleccion.StartElectionMainIndividual(createMenu6());
        }
        if (args[0].equals("election7")){
            objetoEleccion.StartElectionMainIndividual(createMenu7());
        }
        if (args[0].equals("election8")){
            objetoEleccion.StartElectionMainGeneral(createMenu8());
        }
        if (args[0].equals("election9")){
            objetoEleccion.StartElectionMainIndividual(createMenu9());
        }
        if (args[0].equals("election10")){
            objetoEleccion.StartElectionMainGeneral(createMenu10());
        }
        if (args[0].equals("election11")){
            if (Bukkit.getOnlinePlayers().size()>=2){
                objetoEleccion.StartElectionMainGeneral(createMenu11());
            }else{
                objetoEleccion.StartElectionMainGeneral(createMenu10());
            }
        }
        if (args[0].equals("election12")){
            objetoEleccion.StartElectionMainIndividual(createMenu12());
        }
        if (args[0].equals("election13")){
            objetoEleccion.StartElectionMainIndividual(createMenu13());
        }
        if (args[0].equals("election14")){
            objetoEleccion.StartElectionMainIndividual(createMenu14());
        }
        if (args[0].equals("election15")){
            objetoEleccion.StartElectionMainGeneral(createMenu15());
        }
        if (args[0].equals("election16")){
            objetoEleccion.StartElectionMainGeneral(createMenu16());
        }
        if (args[0].equals("election17")){
            objetoEleccion.StartElectionMainIndividual(createMenu17());
        }
        if (args[0].equals("election18")){
            objetoEleccion.StartElectionMainIndividual(createMenu18());
        }
        if (args[0].equals("election19")){
            objetoEleccion.StartElectionMainGeneral(createMenu19());
        }
        if (args[0].equals("election20")){
            objetoEleccion.StartElectionMainGeneral(createMenu20());
        }
        if (args[0].equals("election21")){
            objetoEleccion.StartElectionMainGeneral(createMenu21());
        }
        if (args[0].equals("election22")){
            objetoEleccion.StartElectionMainGeneral(createMenu22());
        }
        if (args[0].equals("election23")){
            objetoEleccion.StartElectionMainGeneral(createMenu23());
        }
        if (args[0].equals("election24")){
            objetoEleccion.StartElectionMainIndividual(createMenu24());
        }
        if (args[0].equals("election25")){
            objetoEleccion.StartElectionMainGeneral(createMenu25());
        }
        if (args[0].equals("election26")){
            objetoEleccion.StartElectionMainGeneral(createMenu26());
        }
        if (args[0].equals("election27")){
            objetoEleccion.StartElectionMainGeneral(createMenu27());
        }
        if (args[0].equals("election28")){
            objetoEleccion.StartElectionMainIndividual(createMenu28());
        }
        if (args[0].equals("election29")){
            objetoEleccion.StartElectionMainGeneral(createMenu29());
        }
        if (args[0].equals("election30")){
            objetoEleccion.StartElectionMainGeneral(createMenu30());
        }
        if (args[0].equals("election31")){
            objetoEleccion.StartElectionMainGeneral(createMenu31());
        }
        if (args[0].equals("election32")){
            objetoEleccion.StartElectionMainGeneral(createMenu32());
        }
        if (args[0].equals("election33")){
            objetoEleccion.StartElectionMainGeneral(createMenu33());
        }
        if (args[0].equals("election34")){
            objetoEleccion.StartElectionMainIndividual(createMenu34());
        }
        if (args[0].equals("election35")){
            objetoEleccion.StartElectionMainIndividual(createMenu35());
        }
        if (args[0].equals("election36")){
            objetoEleccion.StartElectionMainIndividual(createMenu36());
        }
        if (args[0].equals("election37")){
            objetoEleccion.StartElectionMainIndividual(createMenu37());
        }
        if (args[0].equals("election38")){
            objetoEleccion.StartElectionMainIndividual(createMenu38());
        }

        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("random");
            for (int i=1;i<=38;i++){
                completions.add("election"+i);
            }
        }
        // Return a list of completions based on the user's input
        return completions;
    }
}
