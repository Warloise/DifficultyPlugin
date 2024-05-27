package dp.warloise.commands;

import dp.warloise.DifficultyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static dp.warloise.DifficultyPlugin.*;

public class reloadCommand implements CommandExecutor {

    private final DifficultyPlugin plugin;

    public reloadCommand(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            // Verificar si el jugador tiene permisos para usar el comando
            if (!sender.hasPermission("difficultyreload.reloadconfig")) {
                sender.sendMessage("No tienes permiso para usar este comando.");
                return true;
            }
                // Recargar la configuración
        SpawnEntities_EnemyDifficulty_Trigger=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.Trigger");
        SpawnEntities_EnemyDifficulty_ProbabilityMin=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMin");
        SpawnEntities_EnemyDifficulty_ProbabilityMax=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMax");
        SpawnEntities_EnemyDifficulty_SpeedEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.SpeedEffect");
        SpawnEntities_EnemyDifficulty_SpeedEffectLvL=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL");
        SpawnEntities_EnemyDifficulty_ResistanceEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect");
        SpawnEntities_EnemyDifficulty_ResistanceEffectLvL=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL");
        SpawnEntities_EnemyDifficulty_StrengthEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect");
        SpawnEntities_EnemyDifficulty_StrengthEffectLvL=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL");
        SpawnEntities_EnemyDifficulty_GlowingEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.GlowingEffect");
        SpawnEntities_EnemyDifficulty_FireResistanceEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.FireResistanceEffect");
        SpawnEntities_EnemyDifficulty_DolphinsGraceEffect=plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect");
        SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL=plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL");
        SpawnEntities_EnemyDifficulty_CreepersCharged = plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.CreepersCharged");
        SpawnEntities_EnemyDifficulty_EndermansAgressives = plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.EndermansAgressives");
        SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives = plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives");
        
        
        SpawnEntities_DistanceEnemyDifficulty_Trigger=plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.Trigger");
        SpawnEntities_DistanceEnemyDifficulty_Period=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Period");
        SpawnEntities_DistanceEnemyDifficulty_Start=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start");
        SpawnEntities_TimeEnemyDifficulty_Trigger=plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger");
        SpawnEntities_TimeEnemyDifficulty_TimePeriodScale=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
        SpawnEntities_TimeEnemyDifficulty_TimeScale=plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale");
        Huracane_Trigger=plugin.getConfig().getBoolean("Huracane.Trigger");
        Huracane_DamageScale=plugin.getConfig().getInt("Huracane.DamageScale");
        Huracane_Period=plugin.getConfig().getInt("Huracane.Period");
        DistanceEnemyDifficulty_CenterX=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.X");
        DistanceEnemyDifficulty_CenterY=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Y");
        DistanceEnemyDifficulty_CenterZ=plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Z");
        Huracane_CenterX=plugin.getConfig().getInt("Huracane.Center.X");
        Huracane_CenterY=plugin.getConfig().getInt("Huracane.Center.Y");
        Huracane_CenterZ=plugin.getConfig().getInt("Huracane.Center.Z");
        time_days=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.DaysSaved");
        time_hours=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.HoursSaved");
        time_min=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.MinutesSaved");
        time_sec=plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.SecondsSaved");
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La configuración se ha recargado correctamente."));
        return true;



    }
}
