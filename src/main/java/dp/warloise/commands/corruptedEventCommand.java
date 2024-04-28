package dp.warloise.commands;


import dp.warloise.DifficultyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static dp.warloise.DifficultyPlugin.*;

public class corruptedEventCommand implements CommandExecutor, TabCompleter {

    private final DifficultyPlugin plugin;

    public corruptedEventCommand(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el jugador tiene permisos para usar el comando
        if (!sender.hasPermission("corruptedevents.config")) {
            sender.sendMessage("No tienes permiso para usar este comando.");
            return true;
        }
        // Activar cada evento
        if (args[0].equals("highGravity")){
            if (event_highGravity){
                event_highGravity = false;
                SendAllPlayerMessage("La gravedad vuelve a la normalidad");
            }else{
                event_highGravity = true;
                SendAllPlayerMessage("La gravedad ha aumentado");
            }
        }
        if (args[0].equals("acidRain")){
            if (event_acidRain){
                event_acidRain = false;
                SendAllPlayerMessage("Ya no hay contaminación");
            }else{
                event_acidRain = true;
                SendAllPlayerMessage("Empieza a caer lluvia ácida");
            }
        }
        if (args[0].equals("dangerJump")){
            if (event_dangerJump){
                event_dangerJump = false;
                SendAllPlayerMessage("Ya no flotarás mas tranquilo/a");
            }else{
                event_dangerJump = true;
                SendAllPlayerMessage("Si saltas flotarás, CUIDADO");
            }
        }
        if (args[0].equals("freezeNight")){
            if (event_freezeNight){
                event_freezeNight = false;
                SendAllPlayerMessage("La temperatura ha vuelto a la normalidad");
            }else{
                event_freezeNight = true;
                SendAllPlayerMessage("Busca un punto de luz, en la noche empezara a hacer frio...");
            }
        }
        if (args[0].equals("heatDay")){
            if (event_heatDay){
                event_heatDay = false;
                SendAllPlayerMessage("La temperatura ha vuelto a la normalidad");
            }else{
                event_heatDay = true;
                SendAllPlayerMessage("La temperatura ha aumentado bastante, si hace calor sudas y hueles mal...");
            }
        }
        if (args[0].equals("MixingInventory")){
            if (event_MixingInventory){
                event_MixingInventory = false;
                SendAllPlayerMessage("Ya paro ya paro que no tiene gracia...");
            }else{
                event_MixingInventory = true;
                SendAllPlayerMessage("Cuidado se viene mezcladora");
            }
        }
        if (args[0].equals("simonDice")){
            if (event_roboInventarios){
                event_roboInventarios = false;
                SendAllPlayerMessage("Se te paso la peda...");
            }else{
                event_roboInventarios = true;
                SendAllPlayerMessage("Cuidado marioneta... Simon dice...");
            }
        }

        return true;

    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        completions.add("highGravity");
        completions.add("acidRain");
        completions.add("dangerJump");
        completions.add("freezeNight");
        completions.add("heatDay");
        completions.add("MixingInventory");
        completions.add("simonDice");

        // Return a list of completions based on the user's input
        return completions;
    }
}

