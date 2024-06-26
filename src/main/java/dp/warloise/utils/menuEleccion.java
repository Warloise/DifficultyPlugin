package dp.warloise.utils;

import dp.warloise.DifficultyPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
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
import org.bukkit.util.Vector;

import java.util.*;

import static dp.warloise.DifficultyPlugin.*;
import static org.bukkit.Bukkit.getLogger;

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
    static ItemStack electionItem1 = createItem(Material.DIAMOND_SWORD, "Un guerrero siempre mancha su espada",1,null, "¡Recibirás una espada!", "De diamante y sin encantar... no te flipes");
    static ItemStack electionItem2 = createItem(Material.GOLDEN_APPLE, "¿Y la enfermera pa cuando?",1,null, "Recibirás dos manzanas de oro", "Pa que el nene no llore");
    static ItemStack electionItem3 = createItem(Material.DIAMOND_PICKAXE, "¿Un piquito?",1,null, "Recibirás un pico", "De diamante y sin encantar... no te flipes");

    static ItemStack electionItem4 = createItem(Material.POTION, "Recibir Daño Instantaneo",1,null, "Recibirás 10 ", "de daño instantaneo");
    static ItemStack electionItem5 = createItem(Material.SPLASH_POTION, "Recibir Daño Progresivo",1,null, "Recibirás 5 corazones de daño", "a lo largo de 15 segundos");
    static ItemStack electionItem6 = createItem(Material.WITHER_SKELETON_SKULL, "Descomposición",1,null, "Recibirás el efecto de Wither II", "durante 15 segundos");

    static ItemStack electionItem7 = createItem(Material.APPLE, "Recibir Vida Instantánea",1,Enchantment.PROTECTION_ENVIRONMENTAL, "Te curaras toda la vida ", "y ya esta. Weno si ¿y un poco de absorción te parece?");
    static ItemStack electionItem8 = createItem(Material.GHAST_TEAR, "Recibir Regeneración",1,null, "Recibirás regeneración II", "durante 5 minutos");
    static ItemStack electionItem9 = createItem(Material.TOTEM_OF_UNDYING, "Recibir 1 Tótem y algo más",1,null, "Recibirás un totem", "perate que lo nerfeo, recibirás 5 de daño también.");

    static ItemStack electionItem10 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Irrompibilidad III",3,Enchantment.DURABILITY, "Un libro para que dures mas...", "en el juego malpensade");
    static ItemStack electionItem11 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Filo III",3,Enchantment.DAMAGE_ALL, "Un libro para que seas mas...", "Filo da p*ta");
    static ItemStack electionItem12 = createItem(Material.ENCHANTED_BOOK, "Recibir Libro con Eficiencia III",3,Enchantment.DIG_SPEED, "Con este libro serás más...", "Eficiente, o eso espero");

    static ItemStack electionItem13 = createItem(Material.COD, "Recibir 32 Pescaos y un libro random",1,Enchantment.CHANNELING, "Recibirás 16 pescaos i un libro", "va a pareces que has pescado y todo");
    static ItemStack electionItem14 = createItem(Material.FISHING_ROD, "Recibir 1 caña de pescar fuerte",1,Enchantment.LUCK, "Recibirás 1 caña de pescar fuerte", "el trabajo duro es bien recompensado");
    static ItemStack electionItem15 = createItem(Material.LEATHER, "Recibir cosas para craftear",1,Enchantment.LUCK, "Recibirás hilo, cuero, plumas,polvora...", "que pareza da farmear...");

    static ItemStack electionItem16 = createItem(Material.REDSTONE_BLOCK, "Recibir Kit de redstone",1,null, "Recibirás cosas 100tificas porque eres 100tifico", "no?");
    static ItemStack electionItem17 = createItem(Material.MINECART, "Recibir Kit Transporte",1,null, "Recibirás cosas para hacerte un scalextric", "¡Passajeros al tren! Chu chuu");
    static ItemStack electionItem18 = createItem(Material.JUKEBOX, "Recibir Kit DJ",1,null, "Recibirás un conjunto de objetos", "para montarte una fiesta to guapa :)");

    static ItemStack electionItem19 = createItem(Material.DIORITE, "Recibir Diorita",1,Enchantment.LUCK, "Recibirás 64 de fabulosa diorita", "para que puedas hacerte el chulo delante","los pocos amigos que tengas :)");
    static ItemStack electionItem20 = createItem(Material.ANDESITE, "Recibir Andesita",1,null, "Recibirás 64 asquerosa andesita", "de verdad que da asco no la elijas");
    static ItemStack electionItem21 = createItem(Material.GRANITE, "Recibir Andesita",1,null, "Recibirás 64 pestilente granito", "¡no mires mas elije la diorita, que es mejor!");

    static ItemStack electionItem22 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem23 = createItem(Material.LAPIS_LAZULI, "Pacto Mágico",1,null, "Perderás la mitad de tu vida hasta que mueras", "a cambio recibiras:", "-> 100 nvls de XP","-> 32 LapisLazuli");
    static ItemStack electionItem24 = createItem(Material.CHAINMAIL_CHESTPLATE, "Pacto Físico",1,Enchantment.DURABILITY, "Perderás la mitad de tu vida hasta que mueras", "a cambio recibiras:", "-> 10 de armadura base","-> 5 de daño base");

    static ItemStack electionItem25 = createItem(Material.LAVA_BUCKET, "Ayuda del fuego",1,null, "Recibirás un conjunto de cosas de fuego:", "-> 1 cubito de lava","-> 1 libro de protección contra el fuego IV", "-> 1 Arco + Fuego", "-> 2 baras de blaze");
    static ItemStack electionItem26 = createItem(Material.AXOLOTL_BUCKET, "Ayuda del agua",1,null, "Recibirás un conjunto de cosas de agua:", "-> 1 cubito de agua con ajolote","-> 1 libro de paso helado II", "-> 10 bolas de nieve", "-> 1 tridente");
    static ItemStack electionItem27 = createItem(Material.PHANTOM_MEMBRANE, "Ayuda del viento",1,null, "Recibirás un conjunto de cosas de viento:", "-> 1 Elytros","-> 1 libro de Caida de pluma IV", "-> 16 ender pearls", "-> 32 cohetes");

    static ItemStack electionItem28 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem29 = createItem(Material.END_PORTAL_FRAME, "Un viaje al final de la clase",1,null, "Recibirás lo necesario para ir al end:", "-> 12 end portal frames", "-> 12 ojos del end");
    static ItemStack electionItem30 = createItem(Material.OBSIDIAN, "Un viaje al infierno",1,null, "Recibirás lo necesario para ir al nether:", "-> 10 obsidiana", "-> 1 carga de fuego");

    static ItemStack electionItem31 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem32 = createItem(Material.STICK, "Cambio de Inventario random",1,null, "Se cambiará el inventario a todos los jugadores", "será aleatorio si.");
    static ItemStack electionItem33 = createItem(Material.MAP, "Cambio de posición entre jugadores",1,null, "Se cambiará la posición entre jugadores", "No vas a saber donde acabarás...");

    static ItemStack electionItem34 = createItem(Material.BOW, "El Arco del Poder",4,Enchantment.ARROW_DAMAGE, "Este solo tendrá Poder IV", "Si solo eso...");
    static ItemStack electionItem35 = createItem(Material.BOW, "El Arco de la infinidad",1,Enchantment.ARROW_INFINITE, "Este solo tendrá Infinidad", "Si solo eso...");
    static ItemStack electionItem36 = createItem(Material.BOW, "El Arco de la infinidad parte2",1,Enchantment.MENDING, "Este tendrá reparación y irrompibilidad III", "Si solo eso...");

    static ItemStack electionItem37 = createItem(Material.PARROT_SPAWN_EGG, "El loro de Javier",1,Enchantment.DAMAGE_ALL, "Recibirás:", "-> Huevo de loro","-> Caja de musica","-> Semillas","-> 10 diamantes");
    static ItemStack electionItem38 = createItem(Material.WOLF_SPAWN_EGG, "Los perros de tu tia Katalina",1,Enchantment.DAMAGE_ALL, "Recibirás:", "-> 2 Huevos de lobo","-> Huesos");
    static ItemStack electionItem39 = createItem(Material.HORSE_SPAWN_EGG, "El caballo del abuelo Manolo",1,Enchantment.DAMAGE_ALL, "Recibirás:", "-> Huevo de caballo","-> Silla de montar","-> 2 manzanas doradas");

    static ItemStack electionItem40 = createItem(Material.SHIELD, "Voy tarde, quiero un escudo",1,null, "Recibirás un escudo cualquiera");
    static ItemStack electionItem41 = createItem(Material.CROSSBOW, "Soy fan de la ballesta",1,null, "Recibirás una ballesta cualquiera");
    static ItemStack electionItem42 = createItem(Material.TNT, "Quiero bum bum",1,null, "Recibirás 10 de TNT");

    static ItemStack electionItem43 = createItem(Material.LIGHTNING_ROD, "/weather thunder",1,null, "Literalmente eso.");
    static ItemStack electionItem44 = createItem(Material.CLOCK, "/time set night",1,null, "Literalmente eso.");
    static ItemStack electionItem45 = createItem(Material.WHITE_BANNER, "/effect give @a minecraft:bad_omen 60 4",1,null, "Literalmente eso.");

    static ItemStack electionItem46 = createItem(Material.DEAD_BRAIN_CORAL_BLOCK, "Reroll random",1,null, "Se hace otra elección");
    static ItemStack electionItem47 = createItem(Material.DEAD_BUBBLE_CORAL_BLOCK, "Reroll de daño",1,null, "Se hará una elección de daño");
    static ItemStack electionItem48 = createItem(Material.DEAD_TUBE_CORAL_BLOCK, "Reroll de curación",1,null, "Se hará una elección de curaciones");

    static ItemStack electionItem49 = createItem(Material.COBBLESTONE, "Piedra",1,null, "Gana a las tijeras, recibirás: ", "-> 32 de roca normal", "-> +1 armadura hasta morir");
    static ItemStack electionItem50 = createItem(Material.PAPER, "Papel",1,null, "Gana a la piedra, recibirás: ","-> 16 de papel","+0.02 velocidad hasta morir");
    static ItemStack electionItem51 = createItem(Material.SHEARS, "Tijeras",1,null, "Gana al papel,recibirás: ","-> 1 Tijeras","-> +1 ataque base hasta morir");

    static ItemStack electionItem52 = createItem(Material.CHEST, "Cofresitos",1,null, "Recibirás 8 cofres para guardar tus cositas ");
    static ItemStack electionItem53 = createItem(Material.ENDER_CHEST, "Cofre del Fin",1,null, "Recibirás un cofre del end ","¡RECUERDA!","una vez colocado en el suelo", "solo se puede recoger con un pico","con toque de seda");
    static ItemStack electionItem54 = createItem(Material.SHULKER_BOX, "Coraza de Merodeador",1,null, "Recibirás una caja de shulker ","El color lo elijo yo jajajajaja");

    static ItemStack electionItem55 = createItem(Material.BARRIER, "/gamerule keepInventory false",1,null,"Literalmente eso", "Explotar como palomita", "al morir...");
    static ItemStack electionItem56 = createItem(Material.CHEST, "/gamerule keepInventory true",1,Enchantment.PROTECTION_ENVIRONMENTAL, "Literalmente eso ","Weno lo explico... Al morir","mantendrás tus items en el inventario");

    static ItemStack electionItem57 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada","EN CASO DE TENER UN ESTADO PERMANENTE","VOLVERA TODO A LA NORMALIDAD");
    static ItemStack electionItem58 = createItem(Material.CLOCK, "Day everyday",1,null, "¡Cuidado!", "Se hará de dia de manera permanente,","Con todo lo que eso conlleva...");
    static ItemStack electionItem59 = createItem(Material.CLOCK, "Night everynight",1,null, "¡Cuidado!", "Se hará de noche de manera permanente,","Con todo lo que eso conlleva...");

    static ItemStack electionItem60 = createItem(Material.SAND, "High Gravity",1,null, "¡Cuidado!", "¡Durante 5 minutos la gravedad ha incrementado","por lo que irás agachado todo el rato!");
    static ItemStack electionItem61 = createItem(Material.SLIME_BLOCK, "Acid Rain",1,null, "¡Cuidado!", "¡Durante 5 minutos la lluvia que caiga","te hará daño poco a poco...!");
    static ItemStack electionItem62 = createItem(Material.RABBIT_FOOT, "Salto elevado peligroso",1,null, "¡Cuidado!", "¡Durante 5 minutos saltarás más...!","PERO EL DAÑO DE CAÍDA NO SERÁ MITIGADO...");

    static ItemStack electionItem63 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem64 = createItem(Material.ICE, "Fría noche",1,null, "Si estás en un nivel de luz", "INFERIOR a 5 te empezarás a congelar");
    static ItemStack electionItem65 = createItem(Material.MAGMA_BLOCK, "Dia caluroso",1,null, "Si estás en un nivel de luz", "SUPERIOR a 10 te empezarás a quemar");

    static ItemStack electionItem66 = createItem(Material.ANVIL, "Si te mueves...",1,null, "Se te rompe la armadura,", "Si te mueves se te reparará la armadura");
    static ItemStack electionItem67 = createItem(Material.ROTTEN_FLESH, "Si te mueves...",1,null, "Te baja la comida", "Si te mueves ganas comida");
    static ItemStack electionItem68 = createItem(Material.EXPERIENCE_BOTTLE, "Si te mueves...",1,null, "Te baja la xp", "Si te mueves ganas xp");

    static ItemStack electionItem69 = createItem(Material.NOTE_BLOCK, "Esquizofrenia",1,null, "Durante 2 minutos", "pueden aparecer sonidos de mobs...","o MOBS...");
    static ItemStack electionItem70 = createItem(Material.BLACKSTONE, "Claustrofobia",1,null, "Obtendrás oscuridad", "y te ahogarás poco a poco...");
    static ItemStack electionItem71 = createItem(Material.FEATHER, "Acrofobia",1,null, "Empezarás a flotar con levitación...", "La caída puede ser mortal...","Recibirás un cubo de agua tranquile...");

    static ItemStack electionItem72 = createItem(Material.CREEPER_SPAWN_EGG, "¡A cargar las pilas!",1,null, "Todos los creepers que spawneen", "serán CARGADOS","¡bum bum bum!","Activar");
    static ItemStack electionItem73 = createItem(Material.ENDERMAN_SPAWN_EGG, "Ni que los mires...",1,null, "Todos los endermans que spawneen", "serán AGRESIVOS","¿tendrán problemas de ira o algo?","Activar");
    static ItemStack electionItem74 = createItem(Material.PIGLIN_SPAWN_EGG, "Yo no los recordaba así...",1,null, "Todos los cerdos que spawneen", "Se convertiran en brutos","¿Quién puso esto en el guion?","Activar");
    static ItemStack electionItem72_e = createItem(Material.CREEPER_SPAWN_EGG, "¡A cargar las pilas!",1,Enchantment.CHANNELING, "Todos los creepers que spawneen", "serán CARGADOS","¡bum bum bum!","Desactivar");
    static ItemStack electionItem73_e = createItem(Material.ENDERMAN_SPAWN_EGG, "Ni que los mires...",1,Enchantment.DAMAGE_ALL, "Todos los endermans que spawneen", "serán AGRESIVOS","¿tendrán problemas de ira o algo?","Desactivar");
    static ItemStack electionItem74_e = createItem(Material.PIGLIN_SPAWN_EGG, "Yo no los recordaba así...",1,Enchantment.DAMAGE_ALL, "Todos los cerdos que spawneen", "Se convertirán en brutos","¿Quién puso esto en el guion?","Desactivar");

    static ItemStack electionItem75 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem76 = createItem(Material.VILLAGER_SPAWN_EGG, "¿Aldeanos?",1,null, "¿Poder tradear com aldeanos?", "Desactivado");
    static ItemStack electionItem76_e = createItem(Material.VILLAGER_SPAWN_EGG, "¿Aldeanos?",1,Enchantment.MENDING, "¿Poder tradear com aldeanos?", "Activado");

    static ItemStack electionItem77 = createItem(Material.SPYGLASS, "¿Aumento de dificultad?",1,null, "Uish parece que NO va a augmentar la dificultad...", "Has tenido suerte...");
    static ItemStack electionItem77_e = createItem(Material.SPYGLASS, "¿Aumento de dificultad?",1,Enchantment.THORNS, "Preparate va a aumentar la dificultad", "Prepara el gluteo jejejejeje");

    static ItemStack electionItem78 = createItem(Material.TURTLE_HELMET, "Maestro Tortuga",1,null, "Obtendrás:", "- Resistencia 5","-Lentitud 5","Durante 2 minutos");
    static ItemStack electionItem79 = createItem(Material.BLACKSTONE, "Pajaro loco",1,null, "Obtendrás:", "- Velocidad 5","-Debilidad 5","Durante 2 minutos");
    static ItemStack electionItem80 = createItem(Material.FEATHER, "Manos de dulce, pero del duro",1,null, "Obtendrás:", "- Fuerza 5","-Fatiga Minera 5","Durante 2 minutos");

    static ItemStack electionItem81 = createItem(Material.BARRIER, "No hacer nada",1,null, "/do ¿pasaría algo?", "/do 99,93% que no pasaría nada");
    static ItemStack electionItem82 = createItem(Material.GOLDEN_APPLE, "Gamerule naturalRegeneration OFF",1,null, "Gamerule natural regeneration", "Ta en false","Como si fuera un UHC, pero sin serlo");
    static ItemStack electionItem82_e = createItem(Material.GOLDEN_APPLE, "Gamerule naturalRegeneration ON",1,Enchantment.MENDING, "Gamerule natural regeneratión", "Ta en true","Como de normal");

    static ItemStack electionItem83 = createItem(Material.CAMPFIRE, "Fogata=Faro=Daño",1,null, "La fogata actuará como un faro magico,", "En este caso te otorga daño cerca de ella");
    static ItemStack electionItem84 = createItem(Material.CAMPFIRE, "Fogata=Faro=Regeneración",1,null, "La fogata actuarà como un faro mágico,", "En este caso te otorga regeneración cerca de ella");
    static ItemStack electionItem85 = createItem(Material.CAMPFIRE, "Fogata=Faro=Prisa Minera",1,null, "La fogata actuarà como un faro mágico,", "En este caso te otorga prisa minera cerca de ella");

    static ItemStack electionItem86 = createItem(Material.CLOCK, "RandomTickSpeed LOW",1,null, "Determina la frecuencia de un tick,", "como por ejemplo el crecimiento vegetal, la caída de hojas, etc.","Tendrá el valor de 1");
    static ItemStack electionItem87 = createItem(Material.CLOCK, "RandomTickSpeed DEFAULT",1,null, "Determina la frecuencia de un tick,", "como por ejemplo el crecimiento vegetal, la caída de hojas, etc.","Tendrá el valor de 3");
    static ItemStack electionItem88 = createItem(Material.CLOCK, "RandomTickSpeed HIGH",1,null, "Determina la frecuencia de un tick,", "como por ejemplo el crecimiento vegetal, la caída de hojas, etc.","Tendrá el valor de 40");

    static ItemStack electionItem89 = createItem(Material.LIME_BED, "PlayersSleepingPercentage 1%",1,null, "Determina la cantidad de jugadores", "para saltar una noche.");
    static ItemStack electionItem90 = createItem(Material.YELLOW_BED, "PlayersSleepingPercentage 50%",1,null, "Determina la cantidad de jugadores", "para saltar una noche.");
    static ItemStack electionItem91 = createItem(Material.RED_BED, "PlayersSleepingPercentage 100%",1,null, "Determina la cantidad de jugadores", "para saltar una noche.");

    static ItemStack electionItem92 = createItem(Material.BARRIER, "No ya está bien como está",1,null, "No activaremos el pvp ahora", "Más tarde quizás...");
    static ItemStack electionItem93 = createItem(Material.NETHERITE_SWORD, "¡¡ACTIVA EL PVP!!",9,Enchantment.DAMAGE_ALL, "Siiiiii porfavor,", "quiero matar al niño","Quiero piliar");

    static ItemStack electionItem94 = createItem(Material.BAKED_POTATO, "Menu de Silence",1,null, "Recibirás 16 Patatas Cocinadas");
    static ItemStack electionItem95 = createItem(Material.COOKED_BEEF, "Menu Filetaco",1,null, "Recibirás 8 Filetes Cocinados");
    static ItemStack electionItem96 = createItem(Material.DRIED_KELP, "Menu il mare",1,null, "Recibirás 32 Algas Secas");

    static ItemStack electionItem97 = createItem(Material.CARROT, "Planta algo de comer",1,null, "Recibirás 5 Zanahorias para plantarlas");
    static ItemStack electionItem98 = createItem(Material.POTATO, "Planta algo de comer",1,null, "Recibirás 5 Patatas para plantar");
    static ItemStack electionItem99 = createItem(Material.SWEET_BERRIES, "Planta algo de comer",1,null, "Recibirás 5 Bayas dulces para plantar");

    static ItemStack electionItem100 = createItem(Material.PUMPKIN, "Hoy es halloween, halloween",1,null, "Recibirás 1 Calabaza");
    static ItemStack electionItem101 = createItem(Material.MELON, "Dulce y fresquito para el veranito",1,null, "Recibirás 1 Melón");
    static ItemStack electionItem102 = createItem(Material.BEEHIVE, "Apicultor, aBeecultor",1,null, "Recibirás 1 Colmena para guardar abejas");

    static ItemStack electionItem103 = createItem(Material.COOKED_BEEF, "Comida++",1,null, "Recibirás 10 Filetes Cocinados");
    static ItemStack electionItem104 = createItem(Material.COOKED_MUTTON, "Comida++",1,null, "Recibirás 10 Corderos Cocinados");
    static ItemStack electionItem105 = createItem(Material.COOKED_PORKCHOP, "Comida++",1,null, "Recibirás 10 Cerdos Cocinados");

    static ItemStack electionItem106 = createItem(Material.RECOVERY_COMPASS, "Vuelta al pasado...",1,null, "Recibirás 1 brújula de recuperación");
    static ItemStack electionItem107 = createItem(Material.ENCHANTED_BOOK, "Reparamela",1,Enchantment.MENDING, "Recibirás 1 libro con reparación");
    static ItemStack electionItem108 = createItem(Material.NETHER_STAR, "Una luz al final del juego",1,null, "Recibirás 1 estrella del nether");


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
            case 29:
                if (clickedItem.equals(electionItem81)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem82)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem82_e)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                break;
            case 30:
                if (clickedItem.equals(electionItem83)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem84)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem85)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 31:
                if (clickedItem.equals(electionItem86)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem87)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem88)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 32:
                if (clickedItem.equals(electionItem89)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem90)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem91)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 33:
                if (clickedItem.equals(electionItem92)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem93)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                break;
            case 34:
                if (clickedItem.equals(electionItem94)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem95)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem96)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 35:
                if (clickedItem.equals(electionItem97)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem98)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem99)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 36:
                if (clickedItem.equals(electionItem100)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem101)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem102)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 37:
                if (clickedItem.equals(electionItem103)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem104)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem105)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote3List().contains(player)){
                        objetoEleccion.addPlayer(3,player);
                    }
                    player.closeInventory();
                }
                break;
            case 38:
                if (clickedItem.equals(electionItem106)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote1List().contains(player)){
                        objetoEleccion.addPlayer(1,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem107)) {
                    //player.sendMessage("¡Has seleccionado el Pico de Diamante!");
                    //player.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Pico de Diamante", "¡Rompe cualquier cosa!", "¡El favorito de los mineros!"));
                    if (!objetoEleccion.getVote2List().contains(player)){
                        objetoEleccion.addPlayer(2,player);
                    }
                    player.closeInventory();
                }
                if (clickedItem.equals(electionItem108)) {
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
    public static Inventory createMenu29(){
        objetoEleccion.setEleccionNum(29);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        World world = Bukkit.getWorlds().get(0); // Obtener el mundo por defecto
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem81);
        if (world != null) {
            String gameRuleValue = world.getGameRuleValue("naturalRegeneration");
            if (gameRuleValue != null && gameRuleValue.equalsIgnoreCase("true")) {
                menu.setItem(6, electionItem82_e);
            } else {
                menu.setItem(6, electionItem82);
            }
        }
        return menu;
    }
    public static Inventory createMenu30(){
        objetoEleccion.setEleccionNum(30);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem83);
        menu.setItem(4, electionItem84);
        menu.setItem(6, electionItem85);
        return menu;
    }
    public static Inventory createMenu31(){
        objetoEleccion.setEleccionNum(31);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem86);
        menu.setItem(4, electionItem87);
        menu.setItem(6, electionItem88);
        return menu;
    }
    public static Inventory createMenu32(){
        objetoEleccion.setEleccionNum(32);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem89);
        menu.setItem(4, electionItem90);
        menu.setItem(6, electionItem91);
        return menu;
    }
    public static Inventory createMenu33(){
        objetoEleccion.setEleccionNum(33);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem92);
        menu.setItem(6, electionItem93);
        return menu;
    }
    public static Inventory createMenu34(){
        objetoEleccion.setEleccionNum(34);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem94);
        menu.setItem(4, electionItem95);
        menu.setItem(6, electionItem96);
        return menu;
    }
    public static Inventory createMenu35(){
        objetoEleccion.setEleccionNum(35);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem97);
        menu.setItem(4, electionItem98);
        menu.setItem(6, electionItem99);
        return menu;
    }
    public static Inventory createMenu36(){
        objetoEleccion.setEleccionNum(36);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem100);
        menu.setItem(4, electionItem101);
        menu.setItem(6, electionItem102);
        return menu;
    }
    public static Inventory createMenu37(){
        objetoEleccion.setEleccionNum(37);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem103);
        menu.setItem(4, electionItem104);
        menu.setItem(6, electionItem105);
        return menu;
    }
    public static Inventory createMenu38(){
        objetoEleccion.setEleccionNum(38);
        menu = Bukkit.createInventory(null, 9, "Selecciona un item");
        // Centrar los ítems en el menú
        menu.setItem(2, electionItem106);
        menu.setItem(4, electionItem107);
        menu.setItem(6, electionItem108);
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
        if(objetoEleccion.fogataPassiveNum!=0){
            // Obtener la posición del jugador
            Vector playerLocation = event.getPlayer().getLocation().toVector();
            // Definir el radio de búsqueda
            int radius = 10;
            // Recorrer los bloques dentro del radio
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block block = event.getPlayer().getLocation().add(x, y, z).getBlock();
                        if (block.getType() == Material.CAMPFIRE || block.getType() == Material.SOUL_CAMPFIRE) {
                            // Verificar la distancia exacta
                            if (playerLocation.distance(block.getLocation().toVector()) <= radius) {
                                if(objetoEleccion.fogataPassiveNum==1){
                                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1, true, true));
                                    return; // Salir del método si se encuentra una fogata cercana
                                }
                                if(objetoEleccion.fogataPassiveNum==2){
                                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1, true, true));
                                    return; // Salir del método si se encuentra una fogata cercana
                                }
                                if(objetoEleccion.fogataPassiveNum==3){
                                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 1, true, true));
                                    return; // Salir del método si se encuentra una fogata cercana
                                }
                                return; // Salir del método si se encuentra una fogata cercana
                            }
                        }
                    }
                }
            }
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
