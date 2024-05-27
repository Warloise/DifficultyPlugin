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
            if (objetoEleccion.isEvent_highGravity()){
                objetoEleccion.setEvent_highGravity(false);
                SendAllPlayerMessage("La gravedad vuelve a la normalidad");
            }else{
                objetoEleccion.highGravityPassive();
                SendAllPlayerMessage("La gravedad ha aumentado");
            }
        }
        if (args[0].equals("acidRain")){
            if (objetoEleccion.event_acidRain){
                objetoEleccion.setEvent_acidRain(false);
                SendAllPlayerMessage("Ya no hay contaminación");
            }else{
                objetoEleccion.acidRainPassive();
                objetoEleccion.setEvent_acidRain(true);
                SendAllPlayerMessage("Empieza a caer lluvia ácida");
            }
        }
        if (args[0].equals("dangerJump")){
            if (objetoEleccion.event_dangerJump){
                objetoEleccion.setEvent_dangerJump(false);
                SendAllPlayerMessage("Ya no flotarás mas tranquilo/a");
            }else{
                objetoEleccion.dangerJumpPassive();
                objetoEleccion.setEvent_dangerJump(true);
                SendAllPlayerMessage("Si saltas flotarás, CUIDADO");
            }
        }
        if (args[0].equals("freezeNight")){
            if (objetoEleccion.isEvent_freezeNight()){
                objetoEleccion.setEvent_freezeNight(false);
                SendAllPlayerMessage("La temperatura ha vuelto a la normalidad");
            }else{
                objetoEleccion.setEvent_freezeNight(true);
                objetoEleccion.freezeNightPassive();
                SendAllPlayerMessage("Busca un punto de luz, en la noche empezara a hacer frio...");
            }
        }
        if (args[0].equals("heatDay")){
            if (objetoEleccion.isEvent_heatDay()){
                objetoEleccion.setEvent_heatDay(false);
                SendAllPlayerMessage("La temperatura ha vuelto a la normalidad");
            }else{
                objetoEleccion.setEvent_heatDay(true);
                objetoEleccion.heatDayPassive();
                SendAllPlayerMessage("La temperatura ha aumentado bastante, si hace calor sudas y hueles mal...");
            }
        }
        if (args[0].equals("esquizofrenia")) {
            if (objetoEleccion.event_esquizofrenia) {
                objetoEleccion.event_esquizofrenia = false;
                objetoEleccion.esquizoList.clear();
                sender.sendMessage("La gente ya no tiene esquizofrenia");
            }
        }
        if (args[0].equals("claustrofobia")) {
            if (objetoEleccion.event_claustrofobia) {
                objetoEleccion.event_claustrofobia = false;
                objetoEleccion.claustroList.clear();
                sender.sendMessage("La gente ya no tiene claustrofobia");
            }
        }
        if (args[0].equals("acrofobia")) {
            if (objetoEleccion.event_acrofobia) {
                objetoEleccion.event_acrofobia = false;
                objetoEleccion.acroList.clear();
                sender.sendMessage("La gente ya no tiene acrofobia");
            }
        }
        if (args[0].equals("movementPassive")) {
        	objetoEleccion.movementPassive.cancel();
        	objetoEleccion.event_movement_repair = false;
        	objetoEleccion.event_movement_hunger = false;
        	objetoEleccion.event_movement_xp = false;
        	SendAllPlayerMessage("Ya puedes moverte con normalidad...");
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

