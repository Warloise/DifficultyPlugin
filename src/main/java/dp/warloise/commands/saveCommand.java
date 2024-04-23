package dp.warloise.commands;

import dp.warloise.DifficultyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dp.warloise.DifficultyPlugin.*;

public class saveCommand implements CommandExecutor, TabCompleter {

    private final DifficultyPlugin plugin;

    public saveCommand(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el jugador tiene permisos para usar el comando
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por jugadores.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("difficultysave.param")) {
            player.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Por favor, proporciona un argumento.");
            return false;
        }

        switch (args[0]) {
            case "time":
                config.set("SpawnEntities.TimeEnemyDifficulty.DaysSaved", time_days);
                config.set("SpawnEntities.TimeEnemyDifficulty.HoursSaved", time_hours);
                config.set("SpawnEntities.TimeEnemyDifficulty.MinutesSaved", time_min);
                config.set("SpawnEntities.TimeEnemyDifficulty.SecondsSaved", time_sec);
                time_days=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.DaysSaved");
                time_hours=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.HoursSaved");
                time_min=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.MinutesSaved");
                time_sec=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.SecondsSaved");
                plugin.saveConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La configuración de Time se ha guardado correctamente."));
                return true;
            case "centerDistance":
                config.set("SpawnEntities.DistanceEnemyDifficulty.Center.X",Integer.parseInt(args[1]));
                config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Y",Integer.parseInt(args[2]));
                config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Z",Integer.parseInt(args[3]));
                DistanceEnemyDifficulty_CenterX=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.X");
                DistanceEnemyDifficulty_CenterY=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Y");
                DistanceEnemyDifficulty_CenterZ=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Z");
                plugin.saveConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La configuración de Time se ha guardado correctamente."));
                return true;
            case "centerHuracane":
                config.set("Huracane.Center.X",Integer.parseInt(args[1]));
                config.set("Huracane.Center.Y",Integer.parseInt(args[2]));
                config.set("Huracane.Center.Z",Integer.parseInt(args[3]));
                Huracane_CenterX=plugin.getConfig().getInt("Huracane.Center.X");
                Huracane_CenterY=plugin.getConfig().getInt("Huracane.Center.Y");
                Huracane_CenterZ=plugin.getConfig().getInt("Huracane.Center.Z");
                plugin.saveConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La configuración de Time se ha guardado correctamente."));
                return true;
            default:
                player.sendMessage(ChatColor.RED + "Argumento no válido. Los argumentos válidos son: time, centerDistance, centerHuracane.");
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        Block targetBlock = player.getTargetBlockExact(10);
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("time");
            completions.add("centerDistance");
            completions.add("centerHuracane");
        } else if (args.length == 2 && (args[0].equals("centerDistance") || args[0].equals("centerHuracane"))) {
            player = (Player) sender;
            targetBlock = player.getTargetBlockExact(10);
            if (targetBlock != null) {
                completions.add(String.valueOf(targetBlock.getX()));
            }

        } else if (args.length == 3 && (args[0].equals("centerDistance") || args[0].equals("centerHuracane"))) {
            player = (Player) sender;
            targetBlock = player.getTargetBlockExact(10);
            if (targetBlock != null) {
                completions.add(String.valueOf(targetBlock.getY()));
            }
        } else if (args.length == 4 && (args[0].equals("centerDistance") || args[0].equals("centerHuracane"))) {
            player = (Player) sender;
            targetBlock = player.getTargetBlockExact(10);
            if (targetBlock != null) {
                completions.add(String.valueOf(targetBlock.getZ()));
            }
        }
        return completions;
    }
}
