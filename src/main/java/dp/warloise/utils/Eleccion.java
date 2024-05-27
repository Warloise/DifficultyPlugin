package dp.warloise.utils;

import dp.warloise.DifficultyPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;
import java.util.Vector;
import static dp.warloise.DifficultyPlugin.*;
import static dp.warloise.utils.menuEleccion.*;

public class Eleccion {

    private final DifficultyPlugin plugin;
    private Vector<Player> vote1List = new Vector<>();
    private Vector<Player> vote2List = new Vector<>();
    private Vector<Player> vote3List = new Vector<>();
    public Vector<Player> esquizoList = new Vector<>();
    public Vector<Player> claustroList = new Vector<>();
    public Vector<Player> acroList = new Vector<>();
    private int contador = 10;
    private int contadorSecundario = 0;
    private int eleccionNum = 0;
    private int state = 0;
    private Inventory menu;
    private int maxVotes = 0;
    private int generalWinner = 0;

    public boolean event_movement_repair=false;
    public boolean event_movement_hunger=false;
    public boolean event_movement_xp=false;

    //Corrupted events
    public boolean event_highGravity = false;
    public int event_highGravity_time = 0;
    public boolean event_acidRain = false;
    public int event_acidRain_time = 0;
    public boolean event_dangerJump = false;
    public int event_dangerJump_time = 0;
    public boolean event_freezeNight = false;
    public int event_freezeNight_time = 0;
    public boolean event_heatDay = false;
    public int event_heatDay_time = 0;
    public boolean event_esquizofrenia = false;
    public int event_esquizofrenia_time = 0;
    public boolean event_claustrofobia = false;
    public int event_claustrofobia_time = 0;
    public boolean event_acrofobia = false;
    public int event_acrofobia_time = 0;

    
    //Passives
    public BukkitRunnable movementPassive; 


    private int event_movement_time=0;
    private final Sound[] sonidos = {
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            Sound.ENTITY_FIREWORK_ROCKET_BLAST,
            Sound.ENTITY_PLAYER_LEVELUP,
            Sound.ENTITY_CAT_PURR,
            Sound.ENTITY_ZOMBIE_AMBIENT,
            Sound.ENTITY_SKELETON_AMBIENT,
            Sound.ENTITY_CREEPER_PRIMED,
            Sound.ENTITY_ENDERMAN_AMBIENT,
            Sound.ENTITY_BLAZE_AMBIENT,
            Sound.ENTITY_WARDEN_AGITATED,
            Sound.ENTITY_SPIDER_AMBIENT,
            Sound.ENTITY_PARROT_IMITATE_ENDER_DRAGON,
            Sound.ENTITY_TNT_PRIMED,
            Sound.ENTITY_GENERIC_EXPLODE,
            Sound.ENTITY_WITHER_SPAWN,
            Sound.ENTITY_WITCH_CELEBRATE
            // Agrega más sonidos aquí si quieres mi rey
    };

    public Eleccion(DifficultyPlugin plugin) {
        this.plugin=plugin;
    }


    public Vector<Player> getVote1List() {
        return vote1List;
    }

    public void setVote1List(Vector<Player> vote1List) {
        this.vote1List = vote1List;
    }

    public Vector<Player> getVote2List() {
        return vote2List;
    }

    public void setVote2List(Vector<Player> vote2List) {
        this.vote2List = vote2List;
    }

    public Vector<Player> getVote3List() {
        return vote3List;
    }

    public void setVote3List(Vector<Player> vote3List) {
        this.vote3List = vote3List;
    }

    public void addPlayer(int numList, Player player) {
        switch (numList) {
            case 1:
                vote1List.add(player);
                break;
            case 2:
                vote2List.add(player);
                break;
            case 3:
                vote3List.add(player);
                break;
        }
    }

    public void removePlayer(int numList, Player player) {
        switch (numList) {
            case 1:
                vote1List.remove(player);
                break;
            case 2:
                vote2List.remove(player);
                break;
            case 3:
                vote3List.remove(player);
                break;
        }
    }

    public void clearList(int numList) {
        switch (numList) {
            case 1:
                vote1List.clear();
                break;
            case 2:
                vote2List.clear();
                break;
            case 3:
                vote3List.clear();
                break;
        }
    }


    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getEleccionNum() {
        return eleccionNum;
    }

    public void setEleccionNum(int eleccionNum) {
        this.eleccionNum = eleccionNum;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public boolean isEvent_movement_repair() {
        return event_movement_repair;
    }

    public void setEvent_movement_repair(boolean event_movement_repair) {
        this.event_movement_repair = event_movement_repair;
    }
    public boolean isEvent_movement_hunger() {
        return event_movement_hunger;
    }

    public void setEvent_movement_hunger(boolean event_movement_hunger) {
        this.event_movement_hunger = event_movement_hunger;
    }

    public boolean isEvent_movement_xp() {
        return event_movement_xp;
    }

    public void setEvent_movement_xp(boolean event_movement_xp) {
        this.event_movement_xp = event_movement_xp;
    }
    public boolean isEvent_highGravity() {
        return event_highGravity;
    }

    public void setEvent_highGravity(boolean event_highGravity) {
        this.event_highGravity = event_highGravity;
    }

    public boolean isEvent_acidRain() {
        return event_acidRain;
    }

    public void setEvent_acidRain(boolean event_acidRain) {
        this.event_acidRain = event_acidRain;
    }

    public boolean isEvent_dangerJump() {
        return event_dangerJump;
    }

    public void setEvent_dangerJump(boolean event_dangerJump) {
        this.event_dangerJump = event_dangerJump;
    }

    public boolean isEvent_freezeNight() {
        return event_freezeNight;
    }

    public void setEvent_freezeNight(boolean event_freezeNight) {
        this.event_freezeNight = event_freezeNight;
    }

    public boolean isEvent_heatDay() {
        return event_heatDay;
    }

    public void setEvent_heatDay(boolean event_heatDay) {
        this.event_heatDay = event_heatDay;
    }

    public boolean isEvent_esquizofrenia() {
        return event_esquizofrenia;
    }

    public void setEvent_esquizofrenia(boolean event_esquizofrenia) {
        this.event_esquizofrenia = event_esquizofrenia;
    }

    public boolean isEvent_claustrofobia() {
        return event_claustrofobia;
    }

    public void setEvent_claustrofobia(boolean event_claustrofobia) {
        this.event_claustrofobia = event_claustrofobia;
    }

    public boolean isEvent_acrofobia() {
        return event_acrofobia;
    }

    public void setEvent_acrofobia(boolean event_acrofobia) {
        this.event_acrofobia = event_acrofobia;
    }




    public void ElectionMainWinners() {
        switch (eleccionNum){
            case 2:
                switch (generalWinner){
                    case 1:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            jugador.damage(10);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Todo el mundo recibira 10 de daño"));

                        }
                        break;
                    case 2:
                        DanoProlongadoPassive();
                        break;
                    case 3:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            jugador.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 15, 1, true, true));
                        }
                        break;
                }
                break;
            case 8:
                switch (generalWinner){
                    case 1:
                        SendAllPlayerMessage("Te he dicho que no passaria nada...");
                        break;
                    case 2:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            int xpDelPlayer=jugador.getLevel();
                            jugador.setLevel(xpDelPlayer+100);
                            jugador.getInventory().addItem(createItem(Material.LAPIS_LAZULI,"Usarla sabiamente",1,null,"Encantado de ayudarte. jijijiji"));
                            Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2);
                        }
                        break;
                    case 3:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue()/2);
                            Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).getBaseValue()+5);
                            Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ARMOR)).getBaseValue()+10);
                        }
                        break;
                }
                break;
            case 10:
                switch (generalWinner){
                    case 1:
                        SendAllPlayerMessage("Te he dicho que no passaria nada...");
                        break;
                    case 2:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            for (int i = 0; i < 12; i++) {
                                jugador.getInventory().addItem(createItem(Material.END_PORTAL_FRAME,"End Portal Frame",1,null,"Para ir al end"));
                            }
                            for (int i = 0; i < 12; i++) {
                                jugador.getInventory().addItem(createItem(Material.ENDER_EYE,"Ojete de enderman",1,null,"Para ir al end"));
                            }
                        }
                        break;
                    case 3:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            for (int i = 0; i < 10; i++) {
                                jugador.getInventory().addItem(createItem(Material.OBSIDIAN,"Para ir al nether recuerda",1,null,"uWu"));
                            }
                            jugador.getInventory().addItem(createItem(Material.FIRE_CHARGE,"Para ir al nether recuerda",1,null,"uWu"));
                        }
                        break;
                }
                break;
            case 11:
                Player[] jugadores = Bukkit.getOnlinePlayers().toArray(new Player[0]);
                switch (generalWinner){
                    case 1:
                        SendAllPlayerMessage("Te he dicho que no passaria nada...");
                        break;
                    case 2:
                        for (int i = 0; i < jugadores.length; i++){
                            Player jugador1 = jugadores[i];
                            if (jugador1.getGameMode() == GameMode.SPECTATOR){
                                continue;
                            }
                            for (int j = i+1; j<jugadores.length; j++){
                                Player jugador2 = jugadores[j];
                                if (jugador2.getGameMode() == GameMode.SPECTATOR){
                                    continue;
                                }
                                intercambiarInventarios(jugador1,jugador2);
                            }
                        }
                        break;
                    case 3:
                        for (int i = 0; i < jugadores.length; i++){
                            Player jugador1 = jugadores[i];
                            if (jugador1.getGameMode() == GameMode.SPECTATOR){
                                continue;
                            }
                            for (int j = i+1; j<jugadores.length; j++){
                                Player jugador2 = jugadores[j];
                                if (jugador2.getGameMode() == GameMode.SPECTATOR){
                                    continue;
                                }
                                intercambiarPosiciones(jugador1,jugador2);
                            }
                        }
                        break;
                }
                break;
            case 15:
                switch (generalWinner){
                    case 1:
                        // Obtener el mundo principal del servidor
                        // Activar una tormenta en el mundo principal
                        World world = Bukkit.getWorlds().get(0);
                        world.setStorm(true);
                        world.setThundering(true); // Opcional: activar truenos junto con la tormenta
                        break;
                    case 2:
                        // Obtener el mundo principal del servidor
                        // Establecer la hora del día a noche
                        World worlda = Bukkit.getWorlds().get(0);
                        worlda.setTime(13000); // 13000 es la hora de Minecraft correspondiente a la noche
                        break;
                    case 3:
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            jugador.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, Integer.MAX_VALUE, 4, true, false));
                        }
                        break;
                }
                break;
            case 16:
                switch (generalWinner){
                    case 1:
                        ConsoleCommandSender consolea = Bukkit.getServer().getConsoleSender();
                        Bukkit.getServer().dispatchCommand(consolea,"selectionelection random");
                        break;
                    case 2:
                        ConsoleCommandSender consoleb = Bukkit.getServer().getConsoleSender();
                        Bukkit.getServer().dispatchCommand(consoleb,"selectionelection election2");
                        break;
                    case 3:
                        ConsoleCommandSender consolec = Bukkit.getServer().getConsoleSender();
                        Bukkit.getServer().dispatchCommand(consolec,"selectionelection election3");
                        break;
                }
                break;
            case 19:
                switch (generalWinner){
                    case 1:
                        // Obtener el mundo principal del servidor
                        World world = Bukkit.getWorlds().get(0);
                        // Establecer el doDaylightCycle en false
                        world.setGameRule(GameRule.KEEP_INVENTORY, false);
                        SendAllPlayerMessage("Se ha decidido que al morir explotes como palomita...");
                        break;
                    case 2:
                        // Obtener el mundo principal del servidor
                        World worldb = Bukkit.getWorlds().get(0);
                        // Establecer el doDaylightCycle en false
                        worldb.setGameRule(GameRule.KEEP_INVENTORY, true);
                        SendAllPlayerMessage("A partir de ahora no soltaras los objetos al morir...");
                        break;
                    case 3:
                        SendAllPlayerMessage("Oh...");
                        break;
                }
                break;
            case 20:
                switch (generalWinner){
                    case 1:
                        // Obtener el mundo principal del servidor
                        World world = Bukkit.getWorlds().get(0);
                        if (Boolean.TRUE.equals(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE))) {
                            SendAllPlayerMessage("Pues no pasa nada");
                            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                        }else {
                            SendAllPlayerMessage("Volvemos a la normalidad...");
                            // Establecer el doDaylightCycle en false
                            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                        }
                        break;
                    case 2:
                        // Obtener el mundo principal del servidor
                        World worldb = Bukkit.getWorlds().get(0);
                        // Establecer el doDaylightCycle en false
                        worldb.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                        // Establecer la hora del día a noche
                        worldb.setTime(1000); // 13000 es la hora de Minecraft correspondiente al dia
                        SendAllPlayerMessage("Ahora es de DIA -PERMANENTEMENTE-");
                        break;
                    case 3:
                        // Obtener el mundo principal del servidor
                        World worldc = Bukkit.getWorlds().get(0);
                        // Establecer el doDaylightCycle en false
                        worldc.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                        // Establecer la hora del día a noche
                        worldc.setTime(16000); // 13000 es la hora de Minecraft correspondiente a la noche
                        SendAllPlayerMessage("Ahora es de NOCHE -PERMANENTEMENTE-");
                        break;
                }
                break;
            case 21:
                switch (generalWinner){
                    case 1:
                        highGravityPassive();
                        event_highGravity = true;
                        break;
                    case 2:
                        acidRainPassive();
                        event_acidRain = true;
                        break;
                    case 3:
                        dangerJumpPassive();
                        event_dangerJump = true;
                        break;
                }
                break;
            case 22:
                switch (generalWinner){
                    case 1:
                        SendAllPlayerMessage("Te he dicho que no passaria nada...");
                        break;
                    case 2:
                        freezeNightPassive();
                        event_freezeNight = true;
                        break;
                    case 3:
                        heatDayPassive();
                        event_heatDay = true;
                        break;
                }
                break;
            case 23:
                switch (generalWinner){
                    case 1:
                        event_movement_repair = true;
                        movementPassive();
                        break;
                    case 2:
                        event_movement_hunger = true;
                        movementPassive();
                        break;
                    case 3:
                        event_movement_xp = true;
                        movementPassive();
                        break;
                }
                break;
            case 25:
                switch (generalWinner){
                    case 1:
                        if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.CreepersCharged")){
                            config.set("SpawnEntities.EnemyDifficulty.CreepersCharged", false);
                            SpawnEntities_EnemyDifficulty_CreepersCharged=false;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }else{
                            config.set("SpawnEntities.EnemyDifficulty.CreepersCharged", true);
                            SpawnEntities_EnemyDifficulty_CreepersCharged=true;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }
                        break;
                    case 2:
                        if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.EndermansAgressives")){
                            config.set("SpawnEntities.EnemyDifficulty.EndermansAgressives", false);
                            SpawnEntities_EnemyDifficulty_EndermansAgressives=false;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }else{
                            SpawnEntities_EnemyDifficulty_EndermansAgressives=true;
                            config.set("SpawnEntities.EnemyDifficulty.EndermansAgressives", true);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }
                        break;
                    case 3:
                        if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives")){
                            config.set("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives", false);
                            SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives=false;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }else{
                            config.set("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives", true);
                            SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives=true;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }
                        break;
                }
                break;
            case 26:
                switch (generalWinner){
                    case 1:
                        SendAllPlayerMessage("Te he dicho que no passaria nada...");
                    case 2:
                        if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.VillagerTrades")){
                            config.set("SpawnEntities.EnemyDifficulty.VillagerTrades", false);
                            SpawnEntities_EnemyDifficulty_VillagerTrades=false;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }else{
                            config.set("SpawnEntities.EnemyDifficulty.VillagerTrades", true);
                            SpawnEntities_EnemyDifficulty_VillagerTrades=true;
                            plugin.saveConfig();
                            plugin.reloadConfig();
                        }
                        break;
                }
                break;
            case 27:
                if(SpawnEntities_EnemyDifficulty_Trigger){
                    SendAllPlayerMessage("Se ha augmentado la dificultad");
                    SpawnEntities_EnemyDifficulty_SpeedEffectLvL++;
                    config.set("SpawnEntities.EnemyDifficulty.SpeedEffectLvL", SpawnEntities_EnemyDifficulty_SpeedEffectLvL);
                    SpawnEntities_EnemyDifficulty_ResistanceEffectLvL++;
                    config.set("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL", SpawnEntities_EnemyDifficulty_ResistanceEffectLvL);
                    SpawnEntities_EnemyDifficulty_StrengthEffectLvL++;
                    config.set("SpawnEntities.EnemyDifficulty.StrengthEffectLvL", SpawnEntities_EnemyDifficulty_StrengthEffectLvL);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                }
                break;
        }
        eleccionNum=0;
        generalWinner=0;

    }
    public void ElectionMainWinnersPerVote(){
        switch (eleccionNum){
            case 1:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante de Votación",1, null, "¡La mejor espada!", "¡Para los niños que no saben jugar!"));
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada de Votación",1, null, "¡Recuperate!", "¡Hasta de las almorranas!"));
                    votador2.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada de Votación",1, null, "¡Recuperate!", "¡Hasta de las almorranas!"));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Espada de Diamante de Votación",1, null, "¡La mejor espada!", "¡Para los niños que no saben jugar!"));
                }
                break;
            case 3:
                for (Player votador1 : vote1List){
                    votador1.setHealth(20.0); // Establecer la salud del jugador a 20 (salud máxima)
                    votador1.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*(60*2), 10, true, true));                        }
                for (Player votador2 : vote2List){
                    votador2.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*(60*5), 2, true, true));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.TOTEM_OF_UNDYING, "metoT",1,null, "Na es un totem nomás", "¿te esperabas algo mas? pues no"));
                    votador3.damage(5);
                }
                break;
            case 4:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createEnchantmentBook(3, Enchantment.DURABILITY));
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createEnchantmentBook(3,Enchantment.DAMAGE_ALL));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createEnchantmentBook(3,Enchantment.DIG_SPEED));
                }
                break;
            case 5:
                //Generar un libro random
                ItemStack libroEncantado = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta metaLibro = (EnchantmentStorageMeta) libroEncantado.getItemMeta();
                //Aqui declaro el libro pero no randomizo
                Enchantment encantamiento;
                int nivelEncantamiento;
                //Declaro la caña de pescar
                ItemStack itemCanya=createItem(Material.FISHING_ROD,"Cañatoguapa",1,null,"Esta caña es para que lo passes bien mi rey :)");
                addEnchantments(itemCanya, Enchantment.DURABILITY, 3);
                addEnchantments(itemCanya,Enchantment.MENDING,1);
                addEnchantments(itemCanya,Enchantment.LURE,3);
                addEnchantments(itemCanya,Enchantment.LUCK,3);

                int Numrandom;

                for (Player votador1 : vote1List){
                    encantamiento = Enchantment.values()[(int) (Math.random() * Enchantment.values().length)];
                    nivelEncantamiento = (int) (Math.random() * (encantamiento.getMaxLevel() - 1)) + 1;
                    assert metaLibro != null;
                    metaLibro.addStoredEnchant(encantamiento, nivelEncantamiento, true);
                    libroEncantado.setItemMeta(metaLibro);
                    votador1.getInventory().addItem(libroEncantado);
                    for (int i = 0; i < 24; i++) {
                        votador1.getInventory().addItem(createItem(Material.COD,"Pesaco",1,null,"Pescaito no frito"));
                    }
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(itemCanya);
                }
                for (Player votador3 : vote3List){
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.STRING,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.LEATHER,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.GUNPOWDER,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.INK_SAC,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.SUGAR,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.FLINT,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.FEATHER,"basura",1,null,"Con amor tu amigo vago"));
                    }
                    Numrandom = (int) (Math.random() * 16) + 1;
                    for (int i = 0; i < Numrandom; i++) {
                        votador3.getInventory().addItem(createItem(Material.CLAY_BALL,"basura",1,null,"Con amor tu amigo vago"));
                    }
                }
                break;
            case 6:
                for (Player votador1 : vote1List){
                    for (int i = 0; i < 16; i++) {
                        votador1.getInventory().addItem(createItem(Material.REDSTONE,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.REDSTONE,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.REDSTONE,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.REDSTONE,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.STRING,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.PISTON,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.HOPPER,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.SLIME_BALL,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.REPEATER,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                        votador1.getInventory().addItem(createItem(Material.QUARTZ,"Kit Redstone",1,null,"Soy ingeniero siiii"));
                    }
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.MINECART,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                    votador2.getInventory().addItem(createItem(Material.TNT_MINECART,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                    for (int i = 0; i < 32; i++) {
                        votador2.getInventory().addItem(createItem(Material.RAIL,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                        votador2.getInventory().addItem(createItem(Material.POWERED_RAIL,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                        votador2.getInventory().addItem(createItem(Material.DETECTOR_RAIL,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                        votador2.getInventory().addItem(createItem(Material.ACTIVATOR_RAIL,"Kit Transporte",1,null,"Pa que no te duelan las piernas"));
                    }
                }
                for (Player votador3 : vote3List){
                    for (int i = 0; i < 32; i++) {
                        votador3.getInventory().addItem(createItem(Material.NOTE_BLOCK,"Kit Musica",1,null,"Para que muevas el culo"));
                    }
                    votador3.getInventory().addItem(createItem(Material.MUSIC_DISC_5,"Kit Musica",1,null,"Para que muevas el culo"));
                    votador3.getInventory().addItem(createItem(Material.MUSIC_DISC_11,"Kit Musica",1,null,"Para que muevas el culo"));
                    votador3.getInventory().addItem(createItem(Material.MUSIC_DISC_CAT,"Kit Musica",1,null,"Para que muevas el culo"));
                    votador3.getInventory().addItem(createItem(Material.MUSIC_DISC_RELIC,"Kit Musica",1,null,"Para que muevas el culo"));
                    votador3.getInventory().addItem(createItem(Material.JUKEBOX,"Kit Musica",1,null,"Para que muevas el culo"));
                }
                break;
            case 7:
                for (Player votador1 : vote1List){
                    for (int i = 0; i < 64; i++) {
                        votador1.getInventory().addItem(createItem(Material.DIORITE,"Diorita de los dioses",1,null,"Tu si eres una buena persona <3"));
                    }
                }
                for (Player votador2 : vote2List){
                    for (int i = 0; i < 64; i++) {
                        votador2.getInventory().addItem(createItem(Material.ANDESITE,"Andesita que huele mal",1,null,"Tirala al suelo, no vale nada"));
                    }
                }
                for (Player votador3 : vote3List){
                    for (int i = 0; i < 64; i++) {
                        votador3.getInventory().addItem(createItem(Material.GRANITE,"No hace falta ni mencionarlo",1,null,"Que asco de verdad quemalo."));
                    }
                }
                break;
            case 9:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createItem(Material.LAVA_BUCKET,"Parte del conjunto de fuego",1,null,"Cuidado que quema"));
                    votador1.getInventory().addItem(createEnchantmentBook(4,Enchantment.PROTECTION_FIRE));
                    votador1.getInventory().addItem(createItem(Material.BOW,"Parte del conjunto de fuego",1,Enchantment.ARROW_FIRE,"Cuidado que quema"));
                    votador1.getInventory().addItem(createItem(Material.BLAZE_ROD,"Parte del conjunto de fuego",1,null,"Cuidado que quema"));
                    votador1.getInventory().addItem(createItem(Material.BLAZE_ROD,"Parte del conjunto de fuego",1,null,"Cuidado que quema"));
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.AXOLOTL_BUCKET,"Antonio",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createEnchantmentBook(2,Enchantment.FROST_WALKER));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.SNOWBALL,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                    votador2.getInventory().addItem(createItem(Material.TRIDENT,"Parte del conjunto de agua",1,null,"Cuidado que moja"));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.ELYTRA,"Parte del conjunto de viento",1,null,"Vuela pajarito"));
                    votador3.getInventory().addItem(createEnchantmentBook(4,Enchantment.PROTECTION_FALL));
                    for (int i = 0; i < 16; i++) {
                        votador3.getInventory().addItem(createItem(Material.ENDER_PEARL,"Parte del conjunto de viento",1,null,"Vuela pajarito"));
                    }
                    for (int i = 0; i < 32; i++) {
                        votador3.getInventory().addItem(createItem(Material.FIREWORK_ROCKET,"Parte del conjunto de viento",1,null,"Vuela pajarito"));
                    }
                }
                break;
            case 12:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createItem(Material.BOW,"Arco de triunfo",4,Enchantment.ARROW_DAMAGE,"Ponle mas cosas anda..."));
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.BOW,"Arco de triunfo",1,Enchantment.ARROW_INFINITE,"Ponle mas cosas anda..."));

                }
                for (Player votador3 : vote3List){
                    ItemStack item = createItem(Material.BOW,"Arco de triunfo",4,Enchantment.DURABILITY,"Ponle mas cosas anda...");
                    addEnchantments(item,Enchantment.MENDING,1);
                    votador3.getInventory().addItem(item);
                }
                break;
            case 13:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createItem(Material.PARROT_SPAWN_EGG,"Ataulfo",1,null,"Cuidado que vuela..."));
                    votador1.getInventory().addItem(createItem(Material.JUKEBOX,"Pa que bailen las nenas",1,null,"Cuidado que vuela..."));
                    for (int i = 0; i < 24;i++){
                        votador1.getInventory().addItem(createItem(Material.WHEAT_SEEDS,"Plantalas o pa pal pajero",1,null,"Cuidado que vuela..."));
                    }
                    for (int i = 0; i < 10;i++){
                        votador1.getInventory().addItem(createItem(Material.DIAMOND,"¿Ui de donde han salido?",1,null,"Cuidado que vuela..."));
                    }
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.WOLF_SPAWN_EGG,"Adolfo",1,null,"Cuidado que muerde..."));
                    votador2.getInventory().addItem(createItem(Material.WOLF_SPAWN_EGG,"Rodolfo",1,null,"Cuidado que muerde..."));
                    for (int i = 0; i < 32;i++){
                        votador2.getInventory().addItem(createItem(Material.BONE,"¿Ui de donde han salido?",1,null,"Cuidado que muerde..."));
                    }
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.HORSE_SPAWN_EGG,"Alonso",1,null,"Cuidado que... no se"));
                    votador3.getInventory().addItem(createItem(Material.SADDLE,"¡Montalo!",1,null,"Cuidado que... no se"));
                    votador3.getInventory().addItem(createItem(Material.GOLDEN_APPLE,"Mmmmm...",1,null,"Cuidado que... no se"));
                    votador3.getInventory().addItem(createItem(Material.GOLDEN_APPLE,"Mmmmm...",1,null,"Cuidado que... no se"));
                }
                break;
            case 14:
                for (Player votador1 : vote1List){
                    votador1.getInventory().addItem(createItem(Material.SHIELD,"esQdo",1,null,"Jajajaja esta mal escrito"));

                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.CROSSBOW,"va ahi esta",1,null,"Jajajaja esta mal escrito"));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                    votador3.getInventory().addItem(createItem(Material.TNT,"Tik tok ace vum",1,null,"Jajajaja esta mal escrito"));
                }
                break;
            case 17:
                for (Player votador1 : vote1List){
                    for (int i=0;i<32;i++) {
                        votador1.getInventory().addItem(createItem(Material.COBBLESTONE,"Roca normal",1,null,"Gana a las tijeras,","Weno las aplasta vaya"));
                    }
                    Objects.requireNonNull(votador1.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(votador1.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue()+1);
                }
                for (Player votador2 : vote2List){
                    for (int i=0;i<16;i++) {
                        votador2.getInventory().addItem(createItem(Material.PAPER,"Papel normal",1,null,"Gana a la piedra,","Weno las envuelve en amor."));
                    }
                    Objects.requireNonNull(votador2.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(votador2.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()+0.02);

                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.SHEARS,"Tijeras normales",1,null,"Ganan al papel,","Weno lo corta sin mas"));
                    Objects.requireNonNull(votador3.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(votador3.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()+1);
                }
                break;
            case 18:
                for (Player votador1 : vote1List){
                    for (int i=0;i<8;i++) {
                        votador1.getInventory().addItem(createItem(Material.CHEST,"Cofre normal",1,null,"Sisi es normal lo juro"));
                    }
                }
                for (Player votador2 : vote2List){
                    votador2.getInventory().addItem(createItem(Material.ENDER_CHEST,"Cofre del fin",1,null,"¡Picalo con toque de seda melón!","Weno no me hago responsable si lo pierdes..."));
                }
                for (Player votador3 : vote3List){
                    votador3.getInventory().addItem(createItem(Material.SHULKER_BOX,"Un caparazón del Merodeador",1,null,"¿Habia dicho un color?","Mmmm... no lo recuerdo..."));
                }
                break;
            case 24:
                for (Player votador1 : vote1List){
                    esquizoList.add(votador1);
                    esquizoPassive();
                }
                for (Player votador2 : vote2List){
                    claustroList.add(votador2);
                    claustroPassive();
                }
                for (Player votador3 : vote3List){
                    acroList.add(votador3);
                    acroPassive();
                    votador3.getInventory().addItem(createItem(Material.WATER_BUCKET,"Pa que no te caigas",1,null,"¡¡No te conviertas en tortilla!!"));
                }
                break;
        }
        eleccionNum=0;
        clearList(1);
        clearList(2);
        clearList(3);
    }
    public void StartElectionMainIndividual(Inventory menu) {
        new BukkitRunnable() {
            public void run() {
                if (state == 0) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La votación Individual empieza en..." + contador));
                    }
                    contador--;
                    if (contador <= 0) {
                        state = 1;
                        contador=30;
                    }
                }
                if (state == 1) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.openInventory(menu);
                    }
                    state = 2;
                }
                if (state == 2) {
                    contador--;
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La votación termina en: " + contador));
                    }
                    if (contador <= 0) {
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            jugador.closeInventory();
                        }
                        contador = 10;
                        state = 3;
                    }
                }
                if (state == 3) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Aqui tienes tu premio"));
                        //Ganadores votelist
                        ElectionMainWinnersPerVote();
                    }
                    state = 4;
                }
                if (state == 4){
                    contador = 10;
                    //clearList(1);
                    //clearList(2);
                    //clearList(3);
                    state = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20); // 0 indica que la tarea comenzará en el próximo tick
    }
    public void StartElectionMainGeneral(Inventory menu) {
        new BukkitRunnable() {
            public void run() {
                if (state == 0) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La votación General empieza en..." + contador));
                    }
                    contador--;
                    if (contador <= 0) {
                        state = 1;
                        contador=30;
                    }
                }
                if (state == 1) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.openInventory(menu);
                    }
                    state = 2;
                }
                if (state == 2) {
                    contador--;
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "La votación termina en: " + contador));
                    }
                    if (contador <= 0) {
                        for (Player jugador : Bukkit.getOnlinePlayers()) {
                            jugador.closeInventory();
                        }
                        contador = 10;
                        state = 3;
                    }
                }
                if (state == 3) {
                    maxVotes = Math.max(vote1List.size(), vote2List.size());
                    maxVotes = Math.max(maxVotes, vote3List.size());
                    if (maxVotes == vote1List.size()) {
                        generalWinner = 1;
                        state = 4;
                    } else if (maxVotes == vote2List.size()) {
                        generalWinner = 2;
                        state = 4;
                    } else {
                        generalWinner = 3;
                        state = 4;
                    }
                }
                if (state == 4){
                    ElectionMainWinners();
                    maxVotes = 0;
                    clearList(1);
                    clearList(2);
                    clearList(3);
                    state = 0;
                    contador=10;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20); // 0 indica que la tarea comenzará en el próximo tick
    }

    //Efectos passivos

    public void movementPassive(){
        movementPassive = new BukkitRunnable() {
            public void run() {
                event_movement_time++;
                if (event_movement_time>=(60*5)){
                    event_movement_time = 0;
                    event_movement_repair = false;
                    event_movement_hunger = false;
                    event_movement_xp = false;
                    SendAllPlayerMessage("Ya puedes moverte con normalidad...");
                    this.cancel();
                }
            }
        };
        movementPassive.runTaskTimer(plugin,0,20);
    }
    public void DanoProlongadoPassive(){
        new BukkitRunnable() {
            public void run() {
                contadorSecundario++;
                if (contadorSecundario==5 || contadorSecundario==10 || contadorSecundario==15){
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                        jugador.damage(3.3);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Auch me da dolido"));
                    }
                }
                if (contadorSecundario==15) {
                    contadorSecundario = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20); // 0 indica que la tarea comenzará en el próximo tick
    }


    //Corrupted Events
    public void esquizoPassive() {
        new BukkitRunnable() {
            public void run() {
                event_esquizofrenia_time++;
                if ((event_esquizofrenia_time%10)==0){
                    for (Player esquizofrenico : esquizoList){
                        reproducirSonidoAleatorio(esquizofrenico);
                    }
                }
                if(event_esquizofrenia_time>=(60*2)){
                    event_esquizofrenia_time = 0;
                    esquizoList.clear();
                    this.cancel();
                }
                if(!event_esquizofrenia){
                    event_esquizofrenia_time = 0;
                    esquizoList.clear();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
    public void claustroPassive(){
        new BukkitRunnable() {
            public void run() {
                event_claustrofobia_time++;
                for (Player claustrofobico : claustroList){
                    claustrofobico.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 40, 1, true, false));
                    if (claustrofobico.getRemainingAir()<=40) {
                        claustrofobico.setRemainingAir(claustrofobico.getRemainingAir());
                    }else{
                        claustrofobico.setRemainingAir(claustrofobico.getRemainingAir() - 100);
                    }
                }
                if(event_claustrofobia_time>=(60*2)){
                    event_claustrofobia_time = 0;
                    claustroList.clear();
                    this.cancel();
                }
                if(!event_claustrofobia){
                    event_claustrofobia_time = 0;
                    claustroList.clear();
                    this.cancel();
                }

            }
        }.runTaskTimer(plugin,0,20);
    }
    public void acroPassive(){
        new BukkitRunnable() {
            public void run() {
                event_acrofobia_time++;
                if ((event_acrofobia_time%10)==0){
                    for (Player acrofobico : acroList) {
                        acrofobico.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 160, 1, true, false));
                    }
                }
                if(event_acrofobia_time>=(60*2)){
                    event_acrofobia_time = 0;
                    acroList.clear();
                    this.cancel();
                }
                if(!event_acrofobia){
                    event_acrofobia_time = 0;
                    acroList.clear();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
    public void highGravityPassive(){
        new BukkitRunnable() {
            public void run() {
                event_highGravity_time++;
                for (Player jugador : Bukkit.getOnlinePlayers()){
                    if (!jugador.getGameMode().equals(GameMode.SPECTATOR)) {
                        if (!jugador.isSneaking()) {
                            jugador.sendMessage("¡¡Agachate!!");
                            jugador.setSneaking(false);
                            jugador.damage(1);
                        }
                    }
                }
                if (event_highGravity_time>=(60*5)){
                    event_highGravity_time = 0;
                    SendAllPlayerMessage("La gravedad ha vuelto a la normalidad");
                    this.cancel();
                }
                if(!event_highGravity){
                    event_highGravity_time = 0;
                    this.cancel();
                }

            }
        }.runTaskTimer(plugin,0,20);
    }
    public void acidRainPassive(){
        new BukkitRunnable() {
            public void run() {
                event_acidRain_time++;
                for (Player jugador : Bukkit.getOnlinePlayers()){
                    if (acidRainHelp(jugador)){
                        jugador.damage(1);
                        // Calcular la posición de las partículas alrededor del jugador
                        double radius = 0.5;
                        for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 8) {
                            double x = jugador.getLocation().getX() + radius * Math.cos(theta);
                            double y = jugador.getLocation().getY() + 2; // Altura deseada de las partículas
                            double z = jugador.getLocation().getZ() + radius * Math.sin(theta);
                            jugador.getWorld().spawnParticle(Particle.SLIME, x, y, z, 1, 0, 0, 0, 0); // Agrega 0 para la velocidad en todas las direcciones
                        }
                        if (!jugador.getGameMode().equals(GameMode.SPECTATOR)){
                            jugador.sendMessage("¡Cuidado te quemas con el acido!");
                        }
                    }
                }
                if (event_acidRain_time>=(60*5)){
                    event_acidRain_time = 0;
                    SendAllPlayerMessage("Ya ha passado la tormenta");
                    this.cancel();
                }
                if(!event_acidRain){
                    event_acidRain_time = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
    public void dangerJumpPassive(){
        new BukkitRunnable() {
            public void run() {
                event_dangerJump_time++;
                if (event_dangerJump_time>=(60*5)){
                    event_dangerJump_time = 0;
                    event_dangerJump=false;
                    this.cancel();
                }
                if(!event_dangerJump){
                    event_dangerJump_time = 0;
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
    public void freezeNightPassive(){
        new BukkitRunnable() {
            public void run() {
                event_freezeNight_time++;
                for (Player jugador : Bukkit.getOnlinePlayers()){
                    if (jugador.getLocation().getBlock().getLightLevel()<=5){
                        playerFreeze(jugador);
                        SendAllPlayerMessage("¡Busca un punto de luz para entrar en calor!");
                    }
                }
                if (event_freezeNight_time>=(60*10)){
                    event_freezeNight_time = 0;
                    SendAllPlayerMessage("Ya ha passado el frio...");
                    this.cancel();
                }
                if(!event_freezeNight){
                    event_freezeNight_time = 0;
                    this.cancel();
                }


            }
        }.runTaskTimer(plugin,0,20);
    }
    public void heatDayPassive(){
        new BukkitRunnable() {
            public void run() {
                event_heatDay_time++;
                for (Player jugador : Bukkit.getOnlinePlayers()){
                    if (jugador.getLocation().getBlock().getLightLevel()>=10){
                        playerHeat(jugador);
                        SendAllPlayerMessage("¡Alejate de la luz que hace mucho calor!");
                    }
                }
                if (event_heatDay_time>=(60*10)){
                    event_heatDay_time = 0;
                    SendAllPlayerMessage("Ya no hace tanto calor...");
                    this.cancel();
                }
                if(!event_heatDay){
                    event_heatDay_time = 0;
                    this.cancel();
                }


            }
        }.runTaskTimer(plugin,0,20);
    }




    public static void playerFreeze(Player jugador){
        jugador.setFreezeTicks(jugador.getMaxFreezeTicks()+100);
    }
    public static void playerHeat(Player jugador){
        jugador.setFireTicks(100);
        //jugador.setVisualFire(true);
    }
    public void reproducirSonidoAleatorio(Player jugador) {
        Random rand = new Random();
        int indiceSonido = rand.nextInt(sonidos.length);
        Sound sonidoAleatorio = sonidos[indiceSonido];
        // Reproducir el sonido para el jugador
        jugador.playSound(jugador.getLocation(), sonidoAleatorio, 1.0f, 1.0f);
    }
    private void intercambiarInventarios(Player jugador1, Player jugador2) {
        // Guardar los inventarios en variables temporales
        ItemStack[] inventarioTemporalJugador1 = jugador1.getInventory().getContents().clone();
        ItemStack[] inventarioTemporalJugador2 = jugador2.getInventory().getContents().clone();
        // Borrar los inventarios de ambos jugadores
        jugador1.getInventory().clear();
        jugador2.getInventory().clear();
        // Asignar los inventarios guardados a los jugadores opuestos
        jugador1.getInventory().setContents(inventarioTemporalJugador2);
        jugador2.getInventory().setContents(inventarioTemporalJugador1);
    }
    private void intercambiarPosiciones(Player jugador1, Player jugador2) {
        // Guardar los inventarios en variables temporales
        Location PosicionTemporalJugador1 = jugador1.getLocation();
        Location PosicionTemporalJugador2 = jugador2.getLocation();
        // Borrar los inventarios de ambos jugadores
        jugador1.teleport(PosicionTemporalJugador2);
        jugador2.teleport(PosicionTemporalJugador1);

    }

}

