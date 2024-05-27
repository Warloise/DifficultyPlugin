package dp.warloise.utils;

import dp.warloise.DifficultyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static dp.warloise.DifficultyPlugin.*;

public class menuEleccion implements CommandExecutor, TabCompleter, Listener {
    private final DifficultyPlugin plugin;
    private static Inventory menu;
    private Inventory menu1;
    private BukkitTask task;
    private final double jumpThreshold = 0.3; // Umbral de distancia para considerar un salto
    private final Map<Player, Long> playerLastMoveTime = new HashMap<>();

    // Agregar los ítems al menú1
    ItemStack diamondSword = createItem(Material.DIAMOND_SWORD, "Espada de Diamante",1,null, "Todos los jugadores", "recibirán una espada de diamente" );
    ItemStack goldenApple = createItem(Material.GOLDEN_APPLE, "Manzana Dorada",1,null, "Todos los Jugadores", "recibirán 2 manzanas doradas");
    ItemStack diamondPickaxe = createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante",1,null, "Todos los Jugadores", "recibirán un pico de diamante");

    //Items del menuStandard
    static ItemStack electionItem1 = createItem(Material.DIAMOND_SWORD, "Un guerrero siempre mancha su espada",1,null, "¡Recibiras una espada!", "De diamante y sin encantar... no te flipes");
    static ItemStack electionItem2 = createItem(Material.GOLDEN_APPLE, "¿Y la enfermera pa cuando?",1,null, "Recibiras dos manzanas de oro", "Pa que el nene no llore");
    static ItemStack electionItem3 = createItem(Material.DIAMOND_PICKAXE, "¿Un piquito?",1,null, "Recibiras un pico", "De diamante y sin encantar... no te flipes");

    static ItemStack electionItem4 = createItem(Material.POTION, "Recibir Daño Instantaneo",1,null, "Recibiras 10 ", "de daño instantaneo");
    static ItemStack electionItem5 = createItem(Material.SPLASH_POTION, "Recibir Daño Progresivo",1,null, "Recibiras 5 corazones de daño", "a lo largo de 15 segundos");
    static ItemStack electionItem6 = createItem(Material.WITHER_SKELETON_SKULL, "Recibir 1 Pico de diamante",1,null, "Recibiras el efecto de Wither II", "durante 15 segundos");

    static ItemStack electionItem7 = createItem(Material.APPLE, "Recibir Vida Instantanea",1,Enchantment.PROTECTION_ENVIRONMENTAL, "Te curaras toda la vida ", "y ya esta. Weno si ¿y un poco de absorción te parece?");
    static ItemStack electionItem8 = createItem(Material.GHAST_TEAR, "Recibir Regeneración",1,null, "Recibiras regeneración II", "durante 5 minutos");
    static ItemStack electionItem9 = createItem(Material.TOTEM_OF_UNDYING, "Recibir 1 Totem y algo mas",1,null, "Recibiras un totem", "perate que lo nerfeo, recibiras 5 de daño también.");

    static ItemStack electionItem10 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Irrompibilidad III",3,Enchantment.DURABILITY, "Un libro para que dures mas...", "en el juego malpensade");
    static ItemStack electionItem11 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Filo III",3,Enchantment.DAMAGE_ALL, "Un libro para que seas mas", "Filo da p*ta");
    static ItemStack electionItem12 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Eficiencia III",3,Enchantment.DIG_SPEED, "Con este libro seras mas...", "Eficiente, o eso espero");

    static ItemStack electionItem13 = createItem(Material.COD, "Recibir 32 Pescaos y un libro random",1,Enchantment.CHANNELING, "Recibiras 16 pescaos i un libro", "va a pareces que has pescado y todo");
    static ItemStack electionItem14 = createItem(Material.FISHING_ROD, "Recibir 1 caña de pescar fuerte",1,Enchantment.LUCK, "Recibiras 1 caña de pescar fuerte", "el trabajo duro es bien recompensado");
    static ItemStack electionItem15 = createItem(Material.LEATHER, "Recibir cosas para craftear",1,Enchantment.LUCK, "Recibiras hilo, cuero, plumas,polvora...", "que pareza da farmear...");

    static ItemStack electionItem16 = createItem(Material.REDSTONE_BLOCK, "Recibir Kit de redstone",1,null, "Recibiras cosas 100tificas porque eres 100tifico", "no?");
    static ItemStack electionItem17 = createItem(Material.MINECART, "Recibir Kit Transporte",1,null, "Recibiras cosas para hacerte un scalextric", "¡Passajeros al tren! Chu chuu");
    static ItemStack electionItem18 = createItem(Material.JUKEBOX, "Recibir Kit DJ",1,null, "Recibiras un conjunto de objetos", "para montarte una fiesta to guapa :)");

    static ItemStack electionItem19 = createItem(Material.DIORITE, "Recibir Diorita",1,Enchantment.LUCK, "Recibiras 64 de fabulosa diorita", "para que puedas hacerte el chulo delante","los pocos amigos que tengas :)");
    static ItemStack electionItem20 = createItem(Material.ANDESITE, "Recibir Andesita",1,null, "Recibiras 64 asquerosa andesita", "de verdad que da asco no la elijas");
    static ItemStack electionItem21 = createItem(Material.GRANITE, "Recibir Andesita",1,null, "Recibiras 64 pestilente granito", "¡no mires mas elije la diorita, que es mejor!");

    static ItemStack electionItem22 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada");
    static ItemStack electionItem23 = createItem(Material.LAPIS_LAZULI, "No hacer nada",1,null, "Perderas la mitad de tu vida hasta que mueras", "a cambio recibiras:", "-> 100 nvls de XP","-> 32 LapisLazuli");
    static ItemStack electionItem24 = createItem(Material.CHAINMAIL_CHESTPLATE, "No hacer nada",1,Enchantment.DURABILITY, "Perderas la mitad de tu vida hasta que mueras", "a cambio recibiras:", "-> 10 de armadura base","-> 5 de daño base");

    static ItemStack electionItem25 = createItem(Material.LAVA_BUCKET, "Recibiras la ayuda del fuego",1,null, "Recibiras un conjunto de cosas de fuego:", "-> 1 cubito de lava","-> 1 libro de protección contra el fuego IV", "-> 1 Arco + Fuego", "-> 2 baras de blaze");
    static ItemStack electionItem26 = createItem(Material.AXOLOTL_BUCKET, "Recibiras la ayuda del agua",1,null, "Recibiras un conjunto de cosas de agua:", "-> 1 cubito de agua con ajolote","-> 1 libro de paso helado II", "-> 10 bolas de nieve", "-> 1 tridente");
    static ItemStack electionItem27 = createItem(Material.PHANTOM_MEMBRANE, "Recibiras la ayuda del bosque",1,null, "Recibiras un conjunto de cosas de viento:", "-> 1 Elytros","-> 1 libro de Caida de pluma IV", "-> 16 ender pearls", "-> 32 cohetes");

    static ItemStack electionItem28 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada");
    static ItemStack electionItem29 = createItem(Material.END_PORTAL_FRAME, "Un viaje al final de la clase",1,null, "Recibiras lo necesario para ir al end:", "-> 12 end portal frames", "-> 12 ojos del end");
    static ItemStack electionItem30 = createItem(Material.OBSIDIAN, "Un viaje al infierno",1,null, "Recibiras lo necesario para ir al nether:", "-> 10 obsidiana", "-> 1 carga de fuego");

    static ItemStack electionItem31 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada");
    static ItemStack electionItem32 = createItem(Material.STICK, "Cambio de Inventario random",1,null, "Se cambiara el inventario a todos los jugadores", "sera aleatorio si.");
    static ItemStack electionItem33 = createItem(Material.MAP, "Cambio de posición entre jugadores",1,null, "Se cambiara la posición entre jugadores", "No vas a saber donde acabaras...");

    static ItemStack electionItem34 = createItem(Material.BOW, "Recibiras un arco con cosas",4,Enchantment.ARROW_DAMAGE, "Este solo tendra Poder IV", "Si solo eso...");
    static ItemStack electionItem35 = createItem(Material.BOW, "Recibiras un arco con cosas",1,Enchantment.ARROW_INFINITE, "Este solo tendra Infinidad", "Si solo eso...");
    static ItemStack electionItem36 = createItem(Material.BOW, "Recibiras un arco con cosas",1,Enchantment.MENDING, "Este tendra reparación y irrompibilidad III", "Si solo eso...");

    static ItemStack electionItem37 = createItem(Material.PARROT_SPAWN_EGG, "Recibiras un lorito y mas",1,Enchantment.DAMAGE_ALL, "Recibiras:", "-> Huevo de loro","-> Caja de musica","-> Semillas","-> 10 diamantes");
    static ItemStack electionItem38 = createItem(Material.WOLF_SPAWN_EGG, "Recibiras dos perretes",1,Enchantment.DAMAGE_ALL, "Recibiras:", "-> 2 Huevos de lobo","-> Huesos");
    static ItemStack electionItem39 = createItem(Material.HORSE_SPAWN_EGG, "Recibiras un caballo",1,Enchantment.DAMAGE_ALL, "Recibiras:", "-> Huevo de caballo","-> Silla","-> 2 manzanas doradas");

    static ItemStack electionItem40 = createItem(Material.SHIELD, "Recibiras un escudo",1,null, "Recibiras un escudo cualquiera");
    static ItemStack electionItem41 = createItem(Material.CROSSBOW, "Recibiras una ballesta",1,null, "Recibiras una ballesta cualquiera");
    static ItemStack electionItem42 = createItem(Material.TNT, "Recibiras tnt",1,null, "Recibiras 10 de TNT");

    static ItemStack electionItem43 = createItem(Material.LIGHTNING_ROD, "/weather thunder",1,null, "Literalmente eso.");
    static ItemStack electionItem44 = createItem(Material.CLOCK, "/time set night",1,null, "Literalmente eso.");
    static ItemStack electionItem45 = createItem(Material.WHITE_BANNER, "/effect give @a minecraft:bad_omen 60 4",1,null, "Literalmente eso.");

    static ItemStack electionItem46 = createItem(Material.DEAD_BRAIN_CORAL_BLOCK, "Reroll random",1,null, "Se hace otra elección");
    static ItemStack electionItem47 = createItem(Material.DEAD_BUBBLE_CORAL_BLOCK, "Reroll de daño",1,null, "Se hara una eleccion de daño");
    static ItemStack electionItem48 = createItem(Material.DEAD_TUBE_CORAL_BLOCK, "Reroll de curación",1,null, "Se hara una eleccion de curaciones");

    static ItemStack electionItem49 = createItem(Material.COBBLESTONE, "Piedra",1,null, "Gana a las tijeras, recibiras: ", "-> 32 de roca normal", "-> +1 armadura hasta morir");
    static ItemStack electionItem50 = createItem(Material.PAPER, "Papel",1,null, "Gana a la piedra, recibiras: ","-> 16 de papel","+0.02 velocidad hasta morir");
    static ItemStack electionItem51 = createItem(Material.SHEARS, "Tijeras",1,null, "Gana al papel,recibiras: ","-> 1 Tijeras","-> +1 ataque base hasta morir");

    static ItemStack electionItem52 = createItem(Material.CHEST, "Cofresitos",1,null, "Recibiras 8 cofres para guardar tus cositas ");
    static ItemStack electionItem53 = createItem(Material.ENDER_CHEST, "Cofre del Fin",1,null, "Recibiras un cofre del end ","¡RECUERDA!","una vez colocado en el suelo", "solo se puede recoger con un pico","con toque de seda");
    static ItemStack electionItem54 = createItem(Material.SHULKER_BOX, "Coraza de Merodeador",1,null, "Recibiras una caja de shulker ","El color lo elijo yo jajajajaja");

    static ItemStack electionItem55 = createItem(Material.BARRIER, "/gamerule keepInventory false",1,null,"Literalmente eso", "Explotar como palomita", "al morir...");
    static ItemStack electionItem56 = createItem(Material.CHEST, "/gamerule keepInventory true",1,Enchantment.PROTECTION_ENVIRONMENTAL, "Literalmente eso ","Weno lo explico... Al morir","mantendras tus items en el inventario");

    static ItemStack electionItem57 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada","EN CASO DE TENER UN ESTADO PERMANENTE","VOLVERA TODO A LA NORMALIDAD");
    static ItemStack electionItem58 = createItem(Material.CLOCK, "Day everyday",1,null, "¡Cuidado!", "Se hara de dia de manera permanente,","Con todo lo que eso conlleva...");
    static ItemStack electionItem59 = createItem(Material.CLOCK, "Night everynight",1,null, "¡Cuidado!", "Se hara de noche de manera permanente,","Con todo lo que eso conlleva...");

    static ItemStack electionItem60 = createItem(Material.SAND, "High Gravity",1,null, "¡Cuidado!", "¡Durante 5 minutos la gravedad ha incrementado","por lo que iras agachado todo el rato!");
    static ItemStack electionItem61 = createItem(Material.SLIME_BLOCK, "Acid Rain",1,null, "¡Cuidado!", "¡Durante 5 minutos la lluvia que caiga","te hara daño poco a poco...!");
    static ItemStack electionItem62 = createItem(Material.RABBIT_FOOT, "Salto elevado peligroso",1,null, "¡Cuidado!", "¡Durante 5 minutos saltaras mas...!","PERO EL DAÑO DE CAIDA NO SERA MITIGADO...");

    static ItemStack electionItem63 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada");
    static ItemStack electionItem64 = createItem(Material.ICE, "Fria noche",1,null, "Si estas en un nivel de luz", "INFERIOR a 5 te empezaras a congelar");
    static ItemStack electionItem65 = createItem(Material.MAGMA_BLOCK, "Dia caluroso",1,null, "Si estas en un nivel de luz", "SUPERIOR a 10 te empezaras a quemar");

    static ItemStack electionItem66 = createItem(Material.ANVIL, "Si te mueves...",1,null, "Se te rompe la armadura,", "Si te mueves se te repara la armadura");
    static ItemStack electionItem67 = createItem(Material.ROTTEN_FLESH, "Si te mueves...",1,null, "Te baja la comida", "Si te mueves ganas comida");
    static ItemStack electionItem68 = createItem(Material.EXPERIENCE_BOTTLE, "Si te mueves...",1,null, "Te baja la xp", "Si te mueves ganas xp");

    static ItemStack electionItem69 = createItem(Material.NOTE_BLOCK, "Esquizofrenia",1,null, "Durante 2 minutos", "pueden aparecer sonidos de mobs...","o MOBS...");
    static ItemStack electionItem70 = createItem(Material.BLACKSTONE, "Claustrofobia",1,null, "Obtendras oscuridad", "y te ahogaras poco a poco...");
    static ItemStack electionItem71 = createItem(Material.FEATHER, "Acrofobia",1,null, "Empezaras a flotar con levitación...", "La caida puede ser mortal...","Recibiras un cubo de agua tranquilo/a...");

    static ItemStack electionItem72 = createItem(Material.CREEPER_SPAWN_EGG, "¡A cargar las pilas!",1,null, "Todos los creepers que spawneen", "seran CARGADOS","¡bum bum bum!","Activar");
    static ItemStack electionItem73 = createItem(Material.ENDERMAN_SPAWN_EGG, "Ni que los mires...",1,null, "Todos los endermans que spawneen", "seran AGRESIVOS","¿tendran problemas de ira o algo?","Activar");
    static ItemStack electionItem74 = createItem(Material.PIGLIN_SPAWN_EGG, "Yo no los recordaba asi...",1,null, "Todos los cerdos que spawneen", "Se convertiran en brutos","¿Quien puso esto en el guion?","Activar");
    static ItemStack electionItem72_e = createItem(Material.CREEPER_SPAWN_EGG, "¡A cargar las pilas!",1,Enchantment.CHANNELING, "Todos los creepers que spawneen", "seran CARGADOS","¡bum bum bum!","Desactivar");
    static ItemStack electionItem73_e = createItem(Material.ENDERMAN_SPAWN_EGG, "Ni que los mires...",1,Enchantment.DAMAGE_ALL, "Todos los endermans que spawneen", "seran AGRESIVOS","¿tendran problemas de ira o algo?","Desactivar");
    static ItemStack electionItem74_e = createItem(Material.PIGLIN_SPAWN_EGG, "Yo no los recordaba asi...",1,Enchantment.DAMAGE_ALL, "Todos los cerdos que spawneen", "Se convertiran en brutos","¿Quien puso esto en el guion?","Desactivar");

    static ItemStack electionItem75 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿passaria algo?", "/do 99,93% que no passaria nada");
    static ItemStack electionItem76 = createItem(Material.VILLAGER_SPAWN_EGG, "¿Aldeanos?",1,null, "¿Poder tradear com aldeanos?", "Desactivado");
    static ItemStack electionItem76_e = createItem(Material.VILLAGER_SPAWN_EGG, "¿Aldeanos?",1,Enchantment.MENDING, "¿Poder tradear com aldeanos?", "Activado");

    static ItemStack electionItem77 = createItem(Material.SPYGLASS, "¿Aumento de dificultad?",1,null, "Uish parece que NO va a augmentar la dificultad...", "Has tenido suerte...");
    static ItemStack electionItem77_e = createItem(Material.SPYGLASS, "¿Aumento de dificultad?",1,Enchantment.THORNS, "Preparate va a aumentar la dificultad", "Prepara el gluteo jejejejeje");

    static ItemStack electionItem78 = createItem(Material.TURTLE_HELMET, "Maestro Tortuga",1,null, "Obtendras:", "- Resistencia 5","-Lentitud 5","Durante 2 minutos");
    static ItemStack electionItem79 = createItem(Material.BLACKSTONE, "Pajaro loco",1,null, "Obtendras:", "- Velocidad 5","-Debilidad 5","Durante 2 minutos");
    static ItemStack electionItem80 = createItem(Material.FEATHER, "Manos de dulce, pero del duro",1,null, "Obtendras:", "- Fuerza 5","-Fatiga Minera 5","Durante 2 minutos");


    public menuEleccion(DifficultyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        createMenu();
        startArmorCheckTask();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("dp.menu")) {
            player.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
            return true;
        }

        player.openInventory(menu);

        // Programar el cierre del menú después de 30 segundos
        task=Bukkit.getScheduler().runTaskLater(plugin, () -> player.closeInventory(), 20 * 30);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        // Verificar si el inventario que se abre es del tipo MERCHANT (comercio de aldeano)
        if(!config.getBoolean("SpawnEntities.EnemyDifficulty.VillagerTrades")){
            if (event.getInventory().getType() == InventoryType.MERCHANT) {
                Player player = (Player) event.getPlayer();
                player.sendMessage("No puedes comerciar con aldeanos.");
                event.setCancelled(true); // Cancelar la apertura del inventario
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Selecciona un item")) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        //if(task !=null){
        //task.cancel();
        //}
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;


        //Election 1 V
        switch (objetoEleccion.getEleccionNum()){
            case 1:
                if (clickedItem.equals(electionItem1)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem2)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem3)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 2:
                if (clickedItem.equals(electionItem4)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem5)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem6)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 3:
                if (clickedItem.equals(electionItem7)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem8)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem9)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 4:
                if (clickedItem.equals(electionItem10)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem11)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem12)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 5:
                if (clickedItem.equals(electionItem13)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem14)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem15)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 6:
                if (clickedItem.equals(electionItem16)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem17)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem18)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 7:
                if (clickedItem.equals(electionItem19)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem20)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem21)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 8:
                if (clickedItem.equals(electionItem22)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem23)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem24)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 9:
                if (clickedItem.equals(electionItem25)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem26)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem27)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 10:
                if (clickedItem.equals(electionItem28)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem29)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem30)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 11:
                if (clickedItem.equals(electionItem31)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem32)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem33)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 12:
                if (clickedItem.equals(electionItem34)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem35)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem36)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 13:
                if (clickedItem.equals(electionItem37)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem38)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem39)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 14:
                if (clickedItem.equals(electionItem40)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem41)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem42)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 15:
                if (clickedItem.equals(electionItem43)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem44)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem45)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 16:
                if (clickedItem.equals(electionItem46)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem47)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem48)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 17:
                if (clickedItem.equals(electionItem49)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem50)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem51)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 18:
                if (clickedItem.equals(electionItem52)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem53)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem54)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 19:
                if (clickedItem.equals(electionItem55)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem56)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                break;
            case 20:
                if (clickedItem.equals(electionItem57)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem58)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem59)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 21:
                if (clickedItem.equals(electionItem60)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem61)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem62)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 22:
                if (clickedItem.equals(electionItem63)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem64)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem65)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 23:
                if (clickedItem.equals(electionItem66)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem67)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem68)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 24:
                if (clickedItem.equals(electionItem69)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem70)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem71)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 25:
                if (clickedItem.equals(electionItem72)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem73)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem74)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem72_e)) {
                    //player.sendMessage("¡Has seleccionado la Espada de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante", "¡La mejor espada!", "¡Para los guerreros más valientes!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem73_e)) {
                    //player.sendMessage("¡Has seleccionado la Manzana Dorada!");
                    //player.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada", "¡Recupera tu salud!", "¡Una delicia dorada!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem74_e)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 26:
                if (clickedItem.equals(electionItem75)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem76)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem76_e)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                break;
            case 27:
                if (clickedItem.equals(electionItem77)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem77_e)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                break;
            case 28:
                if (clickedItem.equals(electionItem78)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem79)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem80)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
        }
        event.setCancelled(true);
    }

    private void createMenu() {
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, diamondSword);
        menu.setItem(4, goldenApple);
        menu.setItem(6, diamondPickaxe);
    }
    public static Inventory createMenu1(){
        objetoEleccion.setEleccionNum(1);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem1);
        menu.setItem(4, electionItem2);
        menu.setItem(6, electionItem3);
        return menu;
    }
    public static Inventory createMenu2(){
        objetoEleccion.setEleccionNum(2);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem4);
        menu.setItem(4, electionItem5);
        menu.setItem(6, electionItem6);
        return menu;
    }
    public static Inventory createMenu3(){
        objetoEleccion.setEleccionNum(3);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem7);
        menu.setItem(4, electionItem8);
        menu.setItem(6, electionItem9);
        return menu;
    }
    public static Inventory createMenu4(){
        objetoEleccion.setEleccionNum(4);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem10);
        menu.setItem(4, electionItem11);
        menu.setItem(6, electionItem12);
        return menu;
    }
    public static Inventory createMenu5(){
        objetoEleccion.setEleccionNum(5);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem13);
        menu.setItem(4, electionItem14);
        menu.setItem(6, electionItem15);
        return menu;
    }
    public static Inventory createMenu6(){
        objetoEleccion.setEleccionNum(6);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem16);
        menu.setItem(4, electionItem17);
        menu.setItem(6, electionItem18);
        return menu;
    }
    public static Inventory createMenu7(){
        objetoEleccion.setEleccionNum(7);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem19);
        menu.setItem(4, electionItem20);
        menu.setItem(6, electionItem21);
        return menu;
    }
    public static Inventory createMenu8(){
        objetoEleccion.setEleccionNum(8);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem22);
        menu.setItem(4, electionItem23);
        menu.setItem(6, electionItem24);
        return menu;
    }
    public static Inventory createMenu9(){
        objetoEleccion.setEleccionNum(9);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem25);
        menu.setItem(4, electionItem26);
        menu.setItem(6, electionItem27);
        return menu;
    }
    public static Inventory createMenu10(){
        objetoEleccion.setEleccionNum(10);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem28);
        menu.setItem(4, electionItem29);
        menu.setItem(6, electionItem30);
        return menu;
    }
    public static Inventory createMenu11(){
        objetoEleccion.setEleccionNum(11);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem31);
        menu.setItem(4, electionItem32);
        menu.setItem(6, electionItem33);
        return menu;
    }
    public static Inventory createMenu12(){
        objetoEleccion.setEleccionNum(12);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem34);
        menu.setItem(4, electionItem35);
        menu.setItem(6, electionItem36);
        return menu;
    }
    public static Inventory createMenu13(){
        objetoEleccion.setEleccionNum(13);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem37);
        menu.setItem(4, electionItem38);
        menu.setItem(6, electionItem39);
        return menu;
    }
    public static Inventory createMenu14(){
        objetoEleccion.setEleccionNum(14);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem40);
        menu.setItem(4, electionItem41);
        menu.setItem(6, electionItem42);
        return menu;
    }
    public static Inventory createMenu15(){
        objetoEleccion.setEleccionNum(15);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem43);
        menu.setItem(4, electionItem44);
        menu.setItem(6, electionItem45);
        return menu;
    }
    public static Inventory createMenu16(){
        objetoEleccion.setEleccionNum(16);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem46);
        menu.setItem(4, electionItem47);
        menu.setItem(6, electionItem48);
        return menu;
    }
    public static Inventory createMenu17(){
        objetoEleccion.setEleccionNum(17);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem49);
        menu.setItem(4, electionItem50);
        menu.setItem(6, electionItem51);
        return menu;
    }
    public static Inventory createMenu18(){
        objetoEleccion.setEleccionNum(18);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem52);
        menu.setItem(4, electionItem53);
        menu.setItem(6, electionItem54);
        return menu;
    }
    public static Inventory createMenu19(){
        objetoEleccion.setEleccionNum(19);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem55);

        menu.setItem(6, electionItem56);
        return menu;
    }
    public static Inventory createMenu20(){
        objetoEleccion.setEleccionNum(20);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem57);
        menu.setItem(4, electionItem58);
        menu.setItem(6, electionItem59);
        return menu;
    }
    public static Inventory createMenu21(){
        objetoEleccion.setEleccionNum(21);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem60);
        menu.setItem(4, electionItem61);
        menu.setItem(6, electionItem62);
        return menu;
    }
    public static Inventory createMenu22(){
        objetoEleccion.setEleccionNum(22);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem63);
        menu.setItem(4, electionItem64);
        menu.setItem(6, electionItem65);
        return menu;
    }
    public static Inventory createMenu23(){
        objetoEleccion.setEleccionNum(23);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem66);
        menu.setItem(4, electionItem67);
        menu.setItem(6, electionItem68);
        return menu;
    }
    public static Inventory createMenu24(){
        objetoEleccion.setEleccionNum(24);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem69);
        menu.setItem(4, electionItem70);
        menu.setItem(6, electionItem71);
        return menu;
    }
    public static Inventory createMenu25(){
        objetoEleccion.setEleccionNum(25);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        if(config.getBoolean("SpawnEntities.EnemyDifficulty.CreepersCharged")){
            menu.setItem(2, electionItem72_e);
        }else{
            menu.setItem(2, electionItem72);
        }
        // Centrar los ítems en el menú
        if(config.getBoolean("SpawnEntities.EnemyDifficulty.EndermansAgressives")){
            menu.setItem(4, electionItem73_e);
        }else{
            menu.setItem(4, electionItem73);
        }
        if(config.getBoolean("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives")){
            menu.setItem(6, electionItem74_e);
        }else{
            menu.setItem(6, electionItem74);
        }
        return menu;
    }
    public static Inventory createMenu26(){
        objetoEleccion.setEleccionNum(26);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem75);
        if(config.getBoolean("SpawnEntities.EnemyDifficulty.VillagerTrades")){
            menu.setItem(6, electionItem76_e);
        }else{
            menu.setItem(6, electionItem76);
        }
        return menu;
    }
    public static Inventory createMenu27(){
        objetoEleccion.setEleccionNum(27);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        if(SpawnEntities_EnemyDifficulty_Trigger){
            menu.setItem(4, electionItem77_e);
        }else{
            menu.setItem(4, electionItem77);
        }
        return menu;
    }
    public static Inventory createMenu28(){
        objetoEleccion.setEleccionNum(28);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem78);
        menu.setItem(4, electionItem79);
        menu.setItem(6, electionItem80);
        return menu;
    }

    public static ItemStack createItem(Material material, String name,int level, Enchantment enchant, String... loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        // Crear una lista para almacenar la lore
        List<String> lore = new ArrayList<>();
        // Añadir cada línea de lore al listado
        for (String line : loreText) {
            lore.add(ChatColor.GRAY + line); // Añadir color y formato si es necesario
        }
        // Establecer la lore en el objeto ItemMeta
        meta.setLore(lore);

        // Añadir encantamiento al objeto si el encantamiento no es nulo
        if (enchant != null) {
            meta.addEnchant(enchant, level, true); // Aquí 'true' indica si el encantamiento es forzado (true: se aplicará incluso si el encantamiento no es válido para el objeto)
        }

        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createEnchantmentBook(int level, Enchantment enchant) {
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK); // Crear un ItemStack para un libro encantado
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta(); // Obtener el ItemMeta como EnchantmentStorageMeta
        meta.addStoredEnchant(enchant, level, true);
        enchantedBook.setItemMeta(meta);
        return enchantedBook;
    }

    public static void addEnchantments(ItemStack item, Enchantment enchant, int level) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(enchant, level, true); // Aquí 'true' indica si el encantamiento es forzado (true: se aplicará incluso si el encantamiento no es válido para el objeto)
        item.setItemMeta(meta);
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player jugador = event.getEntity();
        Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
        Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(1);
        Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(0);
        Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (objetoEleccion.event_dangerJump){
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            // Verificar si el jugador ha saltado
            if (to != null && to.getY() > from.getY() && to.getY() - from.getY() > jumpThreshold) {
                // Acciones a realizar cuando un jugador salta
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 30, 1, true, true));
                // Puedes realizar otras acciones aquí, como ejecutar comandos, modificar el jugador, etc.
            }
        }
        if (objetoEleccion.isEvent_movement_repair()){
            Player player = event.getPlayer();
            playerLastMoveTime.put(player, System.currentTimeMillis());
            repairArmor(player);
        }
        if (objetoEleccion.isEvent_movement_hunger()){
            Player player = event.getPlayer();
            playerLastMoveTime.put(player, System.currentTimeMillis());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2, 5, true, true));

        }
        if (objetoEleccion.isEvent_movement_xp()){
            Player player = event.getPlayer();
            playerLastMoveTime.put(player, System.currentTimeMillis());
            increaseXP(player);
        }


    }
    private void repairArmor(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) (item.getDurability() - 0.01));
            }
        }
    }
    private void damageArmor(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) (item.getDurability() + 1));
            }
        }
    }

    private void increaseXP(Player player) {
        float xp = player.getExp();
        try{
            if (xp <= 1){
                player.setExp(xp + 0.0005f);
            }
        }catch(IllegalArgumentException e){
            player.setLevel(player.getLevel()+1);
            player.setExp(0);
        }
    }

    private void decreaseXP(Player player) {
        float xp = player.getExp();
        try{
            if (xp >= 0){
                player.setExp(xp - 0.05f);
            }
        }catch(IllegalArgumentException e){
            if (player.getExp() > 0 && player.getLevel()>0){
                player.setLevel(player.getLevel()-1);
                player.setExp(1);
            }

        }
    }

    private void startArmorCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    long lastMoveTime = playerLastMoveTime.getOrDefault(player, currentTime);
                    if (currentTime - lastMoveTime > 250) { // 0.25 seconds
                        //SendAllPlayerMessage("¡No te quedes quieto que se te lia!");
                        if (objetoEleccion.isEvent_movement_repair()){
                            damageArmor(player);
                        }
                        if (objetoEleccion.isEvent_movement_hunger()){
                            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20, 250, true, true));
                        }
                        if (objetoEleccion.isEvent_movement_xp()){
                            decreaseXP(player);
                        }
                        if (!objetoEleccion.isEvent_movement_repair() && !objetoEleccion.isEvent_movement_hunger() && !objetoEleccion.isEvent_movement_xp()){
                            this.cancel();
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Run every second (20 ticks)
    }


}
