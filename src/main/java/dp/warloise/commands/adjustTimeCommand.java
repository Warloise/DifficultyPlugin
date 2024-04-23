package dp.warloise.commands;

import dp.warloise.DifficultyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static dp.warloise.DifficultyPlugin.*;

public class adjustTimeCommand implements CommandExecutor, TabCompleter {

    private final DifficultyPlugin plugin;

    public adjustTimeCommand(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el jugador tiene permisos para usar el comando
        if (!sender.hasPermission("difftime.config")) {
            sender.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }
        //Modificar el tiempo segun los args
        if (args[0].equals("add")) {
            switch (args[1]) {
                case "D" -> {
                    time_days += Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Ahora llevas " + time_days + " Dias"));
                    return true;
                }
                case "H" -> {
                    time_hours += Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Ahora llevas " + time_hours + " Horas"));
                    return true;
                }
                case "M" -> {
                    time_min += Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Ahora llevas " + time_min + " Minutos"));
                    return true;
                }
                case "S" -> {
                    time_sec += Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Ahora llevas " + time_sec + " Segundos"));
                    return true;
                }
                default -> {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Elige un valor entre D,H,M,S"));
                    return true;
                }
            }
        }
        if (args[0].equals("remove")){
            switch (args[1]) {
                case "D" -> {
                    time_days -= Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_days + " Dias"));
                    return true;
                }
                case "H" -> {
                    time_hours -= Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_hours + " Horas"));
                    return true;
                }
                case "M" -> {
                    time_min -= Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_min + " Minutos"));
                    return true;
                }
                case "S" -> {
                    time_sec -= Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_sec + " Segundos"));
                    return true;
                }
                default -> {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Elige un valor entre D,H,M,S"));
                    return true;
                }
            }
        }
        if (args[0].equals("set")){
            switch (args[1]) {
                case "D" -> {
                    time_days = Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_days + " Dias"));
                    return true;
                }
                case "H" -> {
                    time_hours = Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_hours + " Horas"));
                    return true;
                }
                case "M" -> {
                    time_min = Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_min + " Minutos"));
                    return true;
                }
                case "S" -> {
                    time_sec = Integer.parseInt(args[2]);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Te quedan " + time_sec + " Segundos"));
                    return true;
                }
                default -> {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Elige un valor entre D,H,M,S"));
                    return true;
                }
            }
        }
        if (args[0].equals("info")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Llevas: "+time_days+":"+time_hours+":"+time_min+":"+time_sec));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" El tiempo esta: "+plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")));
            return true;
        }
//        if (args[0].equals("save")){
//            config.set("SpawnEntities.TimeEnemyDifficulty.DaysSaved",time_days);
//            config.set("SpawnEntities.TimeEnemyDifficulty.HoursSaved",time_hours);
//            config.set("SpawnEntities.TimeEnemyDifficulty.MinutesSaved",time_min);
//            config.set("SpawnEntities.TimeEnemyDifficulty.SecondsSaved",time_sec);
//            plugin.saveConfig();
//        }
        if (args[0].equals("trigger")){
            //time_sec=0;
            //time_min=0;
            //time_hours=0;
            //time_days=0;
            if(plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")){
                plugin.getConfig().set("SpawnEntities.TimeEnemyDifficulty.Trigger", false);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" Has desactivado el tiempo!"));
            }else{
                plugin.getConfig().set("SpawnEntities.TimeEnemyDifficulty.Trigger", true);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" Has activado el tiempo!"));
            }
            return true;
        }
        if (args[0].equals("help")){
            //Para enseñar la ayuda
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" Lista de comandos disponibles para ajustar el tiempo: "));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time help) para abrir esta ayuda "));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time info) Muestra el tiempo actual de la difficulty "));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time [D,H,M,S] add) Añade tiempo respectivamente (Solo un numero a la vez)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time [D,H,M,S] remove) Resta tiempo respectivamente (Solo un numero a la vez)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time [D,H,M,S] set) Fija un tiempo respectivamente (Solo un numero a la vez)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+" (/diff-time trigger) Activa o desactiva el tiempo y lo pone a 0"));
        }
        return true;
        }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("add");
            completions.add("remove");
            completions.add("set");
            completions.add("info");
            completions.add("trigger");
            completions.add("help");
        } else if (args.length == 2 && (args[0].equals("add") || args[0].equals("remove") || args[0].equals("set"))) {
            completions.add("D");
            completions.add("H");
            completions.add("M");
            completions.add("S");
        }
        // Return a list of completions based on the user's input
        return completions;
    }
}
