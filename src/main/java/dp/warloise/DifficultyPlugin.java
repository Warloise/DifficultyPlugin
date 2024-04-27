package dp.warloise;

import dp.warloise.commands.*;
import dp.warloise.commands.commandSelectionElection.*;
import dp.warloise.utils.menuEleccion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;
import dp.warloise.utils.EntitySpawnListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static dp.warloise.utils.menuEleccion.*;

public class DifficultyPlugin extends JavaPlugin {

	DifficultyPlugin enNombreDelPlugin=this;

	public static boolean b_election1=false;
	public static boolean b_election2=false;
	public static boolean b_election3=false;
	public static boolean b_election4=false;
	public static boolean b_election5=false;
	public static boolean b_election6=false;
	public static boolean b_election7=false;
	public static boolean b_election8=false;
	public static boolean b_election9=false;
	public static boolean b_election10=false;
	public static boolean b_election11=false;
	public static boolean b_election12=false;
	public static boolean b_election13=false;
	public static boolean b_election14=false;
	public static boolean b_election15=false;
	public static boolean b_election16=false;
	public static boolean b_election17=false;
	public static boolean b_election18=false;
	public static boolean b_election19=false;
	public static boolean b_election20=false;
	public static boolean b_election21=false;
	public static boolean b_election22=false;


	//CorruptedEvents
	public static boolean event_highGravity = false;
	public static int event_highGravity_time = 0;
	public static boolean event_acidRain = false;
	public static int event_acidRain_time = 0;
	public static boolean event_dangerJump = false;
	public static int event_dangerJump_time = 0;
	public static boolean event_freezeNight = false;
	public static int event_freezeNight_time = 0;
	public static boolean event_heatDay = false;
	public static int event_heatDay_time = 0;



	public static int estadoEleccion=0;
	public static int votoGanador=0;
	public static int contador=10;
	public static int contadorSecundario=0;
	public static int tiempoCooldown=30;
	public static int vote1=0;
	public static int vote2=0;
	public static int vote3=0;
	public static int maxVotes=0;

	public static Vector<Player> vote1List = new Vector<>();
	public static Vector<Player> vote2List = new Vector<>();
	public static Vector<Player> vote3List = new Vector<>();

	public static int time_days=0;
	public static int time_hours=0;
	public static int time_min=0;
	public static int time_sec=0;


	public static boolean SpawnEntities_EnemyDifficulty_Trigger=false;
	public static int SpawnEntities_EnemyDifficulty_ProbabilityMin=0;
	public static int SpawnEntities_EnemyDifficulty_ProbabilityMax=10;
	public static boolean SpawnEntities_EnemyDifficulty_SpeedEffect=false;
	public static int SpawnEntities_EnemyDifficulty_SpeedEffectLvL=1;
	public static boolean SpawnEntities_EnemyDifficulty_ResistanceEffect=false;
	public static int SpawnEntities_EnemyDifficulty_ResistanceEffectLvL=1;
	public static boolean SpawnEntities_EnemyDifficulty_StrengthEffect=false;
	public static int SpawnEntities_EnemyDifficulty_StrengthEffectLvL=1;
	public static boolean SpawnEntities_EnemyDifficulty_GlowingEffect=false;
	public static boolean SpawnEntities_EnemyDifficulty_FireResistanceEffect=false;
	public static boolean SpawnEntities_EnemyDifficulty_DolphinsGraceEffect=false;
	public static int SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL=1;
	public static boolean SpawnEntities_DistanceEnemyDifficulty_Trigger=false;
	public static int SpawnEntities_DistanceEnemyDifficulty_Period=500;
	public static int SpawnEntities_DistanceEnemyDifficulty_Start=0;
	public static boolean SpawnEntities_TimeEnemyDifficulty_Trigger=false;
	public static int SpawnEntities_TimeEnemyDifficulty_TimePeriodScale=1;
	public static String SpawnEntities_TimeEnemyDifficulty_TimeScale="H";
	public static boolean Huracane_Trigger=false;
	public static int Huracane_DamageScale=1;
	public static int Huracane_Period=500;

	public static int DistanceEnemyDifficulty_CenterX=0;
	public static int DistanceEnemyDifficulty_CenterY=0;
	public static int DistanceEnemyDifficulty_CenterZ=0;
	public static int Huracane_CenterX=0;
	public static int Huracane_CenterY=0;
	public static int Huracane_CenterZ=0;

	public static String prefix="&8[&c&lDifficultyPlugin&8] ";
	private String version = getDescription().getVersion();

	public static File configFile;
	public static FileConfiguration config;

	public void onEnable() {
		huracanePassive();
		timePassive();
		election1Passive();
		corruptedElections();
		getServer().getPluginManager().registerEvents(new menuEleccion(this),this);
		getServer().getPluginManager().registerEvents(new EntitySpawnListener(this), this);
		//getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
		registerCommands();
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', prefix + "&eha sido activado! &fVersion: " + version));
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&', prefix + "&eGracias por usar mi plugin"));
		// Obtener la ruta de la carpeta del plugin
		File dataFolder = getDataFolder();
		// Verificar si la carpeta del plugin existe, si no, crearla
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
		}

		// Crear el archivo config.yml si no existe
		configFile = new File(dataFolder, "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				// Cargar la configuración predeterminada
				config = YamlConfiguration.loadConfiguration(configFile);
				config.set("SpawnEntities.EnemyDifficulty.Trigger", false);
				config.set("SpawnEntities.EnemyDifficulty.ProbabilityMin", 0);
				config.set("SpawnEntities.EnemyDifficulty.ProbabilityMax", 10);
				config.set("SpawnEntities.EnemyDifficulty.SpeedEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.SpeedEffectLvL", 1);
				config.set("SpawnEntities.EnemyDifficulty.ResistanceEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL", 1);
				config.set("SpawnEntities.EnemyDifficulty.StrengthEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.StrengthEffectLvL", 1);
				config.set("SpawnEntities.EnemyDifficulty.GlowingEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.FireResistanceEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect", false);
				config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL", 1);

				config.set("SpawnEntities.DistanceEnemyDifficulty.Trigger", false);
				config.set("SpawnEntities.DistanceEnemyDifficulty.Center.X", 0);
				config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Y", 0);
				config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Z", 0);
				config.set("SpawnEntities.DistanceEnemyDifficulty.Period", 500);
				config.set("SpawnEntities.DistanceEnemyDifficulty.Start", 0);

				config.set("SpawnEntities.TimeEnemyDifficulty.Trigger", false);
				config.set("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale", 1);
				config.set("SpawnEntities.TimeEnemyDifficulty.TimeScale", "H");//D,H,M,S
				config.set("SpawnEntities.TimeEnemyDifficulty.DaysSaved",0);
				config.set("SpawnEntities.TimeEnemyDifficulty.HoursSaved",0);
				config.set("SpawnEntities.TimeEnemyDifficulty.MinutesSaved",0);
				config.set("SpawnEntities.TimeEnemyDifficulty.SecondsSaved",0);

				config.set("Huracane.Trigger",false);
				config.set("Huracane.Center.X",0);
				config.set("Huracane.Center.Y",0);
				config.set("Huracane.Center.Z",0);
				config.set("Huracane.DamageScale",1);
				config.set("Huracane.Period",500);


			} catch (IOException e) {
				e.printStackTrace();
			}

			saveConfig();
		}

		// Ejemplo de cómo obtener variables de configuración
		//opcion1 = getConfig().getBoolean("opciones.opcion1");
		//opcion2 = getConfig().getInt("opciones.opcion2");
		//opcion3 = getConfig().getString("opciones.opcion3");

		// Ejemplo de cómo modificar variables de configuración
		//getConfig().set("opciones.opcion1", false);
		//getConfig().set("opciones.opcion2", 10);
		//getConfig().set("opciones.opcion3", "¡Hola, Mundo!");

		// Guardar los cambios en el archivo de configuración
		config = YamlConfiguration.loadConfiguration(configFile);

		DistanceEnemyDifficulty_CenterX=getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.X");
		DistanceEnemyDifficulty_CenterY=getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Y");
		DistanceEnemyDifficulty_CenterZ=getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Z");
		Huracane_CenterX=getConfig().getInt("Huracane.Center.X");
		Huracane_CenterY=getConfig().getInt("Huracane.Center.Y");
		Huracane_CenterZ=getConfig().getInt("Huracane.Center.Z");
		time_days=getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.DaysSaved");
		time_hours=getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.HoursSaved");
		time_min=getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.MinutesSaved");
		time_sec=getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.SecondsSaved");
		this.saveConfig();
	}

	public void saveConfig(){
		if (config == null || configFile == null) {
			return;
		}
		try {
			config.save(configFile);
		} catch (IOException ex) {
			Bukkit.getConsoleSender().sendMessage("No se pudo guardar la configuración en: " + configFile.getName());
		}
	}


	private void huracanePassive(){
		// Ejecutar una tarea cada 20 ticks (1 segundo en el juego)
		new BukkitRunnable() {
			public void run() {
				if (getConfig().getBoolean("Huracane.Trigger")) {
					for (Player jugador : Bukkit.getOnlinePlayers()) {
						Location ubicacionJugador = jugador.getLocation();
						Location posicionObjetivo = new Location(jugador.getWorld(), getConfig().getInt("Huracane.Center.X"), getConfig().getInt("Huracane.Center.Y"), getConfig().getInt("Huracane.Center.Z")); // Reemplaza X, Y, Z con las coordenadas de la posición objetivo
						double distancia = calcularDistancia(ubicacionJugador, posicionObjetivo);
						if (Math.abs(distancia)<=getConfig().getInt("Huracane.Period")){
							jugador.damage(getConfig().getInt("Huracane.DamageScale"));
							String mensaje = ChatColor.RED + "¡Alejate o morirás! Centro: " + (int)distancia + "/"+getConfig().getInt("Huracane.Period");
							jugador.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(mensaje));
							//jugador.spawnParticle(Particle.FLAME, jugador.getLocation(), 5);
							// Calcular la posición de las partículas alrededor del jugador
							double radius = 0.5;
							for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 8) {
								double x = jugador.getLocation().getX() + radius * Math.cos(theta);
								double y = jugador.getLocation().getY() + 2; // Altura deseada de las partículas
								double z = jugador.getLocation().getZ() + radius * Math.sin(theta);
								jugador.getWorld().spawnParticle(Particle.FLAME, x, y, z, 1, 0, 0, 0, 0); // Agrega 0 para la velocidad en todas las direcciones
							}
							for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 8) {
								double x = jugador.getLocation().getX() + radius * Math.cos(theta);
								double y = jugador.getLocation().getY() + 1; // Altura deseada de las partículas
								double z = jugador.getLocation().getZ() + radius * Math.sin(theta);
								jugador.getWorld().spawnParticle(Particle.FLAME, x, y, z, 1, 0, 0, 0, 0); // Agrega 0 para la velocidad en todas las direcciones
							}
						}
					}
				}
				//Bukkit.getConsoleSender().sendMessage("Time--> D: "+time_days+" H: "+time_hours+" M: "+time_min+" S: "+time_sec);
			}
		}.runTaskTimer(this, 0, 20); // 0 indica que la tarea comenzará en el próximo tick
		// 20 indica que la tarea se repetirá cada 1 segundo
	}
	private void timePassive(){
		// Ejecutar una tarea cada 20 ticks (1 segundo en el juego)
		new BukkitRunnable() {
			public void run() {
				if (getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")) {
					time_sec++;
					if (time_sec >= 60) {
						time_sec = 0;
						time_min++;
					}
					if (time_min >= 60) {
						time_hours++;
						time_min = 0;
					}
					if (time_hours >= 24) {
						time_days++;
						time_hours = 0;
					}
				}
				//Bukkit.getConsoleSender().sendMessage("Time--> D: "+time_days+" H: "+time_hours+" M: "+time_min+" S: "+time_sec);
			}
		}.runTaskTimer(this, 0, 20); // 0 indica que la tarea comenzará en el próximo tick
		// 20 indica que la tarea se repetirá cada 1 segundo
	}
	private void election1Passive() {
		// Ejecutar una tarea cada 20 ticks (1 segundo en el juego)
		new BukkitRunnable() {
			public void run() {
				//Election 1 Individual
				if (b_election1) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(
									ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu1());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						for (Player votador1 : vote1List){
							votador1.getInventory().addItem(createItem(Material.DIAMOND_SWORD, "Espada de Diamante de Votación",1, null, "¡La mejor espada!", "¡Para los niños que no saben jugar!"));
						}
						for (Player votador2 : vote2List){
							votador2.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Espada de Diamante de Votación",1, null, "¡La mejor espada!", "¡Para los niños que no saben jugar!"));
							votador2.getInventory().addItem(createItem(Material.GOLDEN_APPLE, "Manzana Dorada de Votación",1, null, "¡Recuperate!", "¡Hasta de las almorranas!"));
						}
						for (Player votador3 : vote3List){
							votador3.getInventory().addItem(createItem(Material.DIAMOND_PICKAXE, "Espada de Diamante de Votación",1, null, "¡La mejor espada!", "¡Para los niños que no saben jugar!"));
						}
						estadoEleccion = 0;
						b_election1 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 2 General
				if (b_election2) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu2());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.damage(10);
								estadoEleccion = 0;
								b_election2 = false;
								votoGanador = 0;
								vote1 = 0;
								vote2 = 0;
								vote3 = 0;
								contador = 10;
							}
						} else if (votoGanador == 2) {
							contadorSecundario++;
							if (contadorSecundario==5 || contadorSecundario==10 ||contadorSecundario==15 ){
								for (Player jugador : Bukkit.getOnlinePlayers()) {
									jugador.damage(3.3);
								}
							}
							if (contadorSecundario>=15){
								estadoEleccion = 0;
								b_election2 = false;
								votoGanador = 0;
								vote1 = 0;
								vote2 = 0;
								vote3 = 0;
								contador = 10;
								contadorSecundario=0;
							}

						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*15, 1, true, true));
								estadoEleccion = 0;
								b_election2 = false;
								votoGanador = 0;
								vote1 = 0;
								vote2 = 0;
								vote3 = 0;
								contador = 10;
							}
						}
					}
				}

				//Election 3 Individual
				if (b_election3) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu3());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
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
						estadoEleccion = 0;
						b_election3 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 4 Individual
				if (b_election4) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu4());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						for (Player votador1 : vote1List){
							votador1.getInventory().addItem(createEnchantmentBook(3,Enchantment.DURABILITY));
						}
						for (Player votador2 : vote2List){
							votador2.getInventory().addItem(createEnchantmentBook(3,Enchantment.DAMAGE_ALL));
						}
						for (Player votador3 : vote3List){
							votador3.getInventory().addItem(createEnchantmentBook(3,Enchantment.DIG_SPEED));
						}
						estadoEleccion = 0;
						b_election4 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 5 Individual
				if (b_election5) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu5());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
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
						estadoEleccion = 0;
						b_election5 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 6 Individual
				if (b_election6) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu6());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
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
						estadoEleccion = 0;
						b_election6 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 7 Individual
				if (b_election7) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu7());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {

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
						estadoEleccion = 0;
						b_election7 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 8 General
				if (b_election8) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu8());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							SendAllPlayerMessage("Te he dicho que no passaria nada...");
							estadoEleccion = 0;
							b_election8 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;

						} else if (votoGanador == 2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								int xpDelPlayer=jugador.getLevel();
								jugador.setLevel(xpDelPlayer+100);
								jugador.getInventory().addItem(createItem(Material.LAPIS_LAZULI,"Usarla sabiamente",1,null,"Encantado de ayudarte. jijijiji"));
								Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2);
							}
							estadoEleccion = 0;
							b_election8 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2);                                
								Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(jugador.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue()+5);                                
								Objects.requireNonNull(jugador.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(jugador.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue()+10);
							}
							estadoEleccion = 0;
							b_election8 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}
				}

				//Election 9 Individual
				if (b_election9) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu9());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {

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
						estadoEleccion = 0;
						b_election9 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 10 General
				if (b_election10) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu10());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							SendAllPlayerMessage("Te he dicho que no passaria nada...");
							estadoEleccion = 0;
							b_election10 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;

						} else if (votoGanador == 2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								for (int i = 0; i < 12; i++) {
									jugador.getInventory().addItem(createItem(Material.END_PORTAL_FRAME,"End Portal Frame",1,null,"Para ir al end"));
								}
								for (int i = 0; i < 12; i++) {
									jugador.getInventory().addItem(createItem(Material.ENDER_EYE,"Ojete de enderman",1,null,"Para ir al end"));
								}
							}
							estadoEleccion = 0;
							b_election10 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								for (int i = 0; i < 10; i++) {
									jugador.getInventory().addItem(createItem(Material.OBSIDIAN,"Para ir al nether recuerda",1,null,"uWu"));
								}
								jugador.getInventory().addItem(createItem(Material.FIRE_CHARGE,"Para ir al nether recuerda",1,null,"uWu"));
							}
							estadoEleccion = 0;
							b_election10 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}
				}

				//Election 11 General
				if (b_election11) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu11());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							SendAllPlayerMessage("Te he dicho que no passaria nada...");
							estadoEleccion = 0;
							b_election11 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;

						} else if (votoGanador == 2) {

							Player[] jugadores = Bukkit.getOnlinePlayers().toArray(new Player[0]);

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
							estadoEleccion = 0;
							b_election11 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							Player[] jugadores = Bukkit.getOnlinePlayers().toArray(new Player[0]);
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
							estadoEleccion = 0;
							b_election11 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}
				}

				//Election 12 Individual
				if (b_election12) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu12());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
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
						estadoEleccion = 0;
						b_election12 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 13 Individual
				if (b_election13) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu13());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
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
						estadoEleccion = 0;
						b_election13 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 14 Individual
				if (b_election14) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu14());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {

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
						estadoEleccion = 0;
						b_election14 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 15 General
				if (b_election15) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu15());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Activar una tormenta en el mundo principal
							world.setStorm(true);
							world.setThundering(true); // Opcional: activar truenos junto con la tormenta
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else if (votoGanador == 2) {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Establecer la hora del día a noche
							world.setTime(13000); // 13000 es la hora de Minecraft correspondiente a la noche
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, Integer.MAX_VALUE, 4, true, false));
							}
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}
				}

				//Election 16 General
				if (b_election16) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu16());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
							ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
							Bukkit.getServer().dispatchCommand(console,"selectionelection random");
						} else if (votoGanador == 2) {
							b_election2 = true;
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							b_election3 = true;
							estadoEleccion = 0;
							b_election15 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}
				}

				//Election 17 Individual
				if (b_election17) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu17());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {

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
						estadoEleccion = 0;
						b_election17 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 18 Individual
				if (b_election18) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación Individual empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu18());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {

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
						estadoEleccion = 0;
						b_election18 = false;
						votoGanador = 0;
						vote1 = 0;
						vote2 = 0;
						vote3 = 0;
						contador = 10;
						vote1List.clear();
						vote2List.clear();
						vote3List.clear();
					}
				}

				//Election 19 General
				if (b_election19) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu19());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Establecer el doDaylightCycle en false
							world.setGameRule(GameRule.KEEP_INVENTORY, false);
							SendAllPlayerMessage("Se ha decidido que al morir explotes como palomita...");
							estadoEleccion = 0;
							b_election19 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else if (votoGanador == 2) {

							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Establecer el doDaylightCycle en false
							world.setGameRule(GameRule.KEEP_INVENTORY, true);
							SendAllPlayerMessage("A partir de ahora no soltaras los objetos al morir...");
							estadoEleccion = 0;
							b_election19 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							SendAllPlayerMessage("Oh...");
							estadoEleccion = 0;
							b_election19 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}

				}

				//Election 20 General
				if (b_election20) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu20());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							if (world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)) {
								SendAllPlayerMessage("Pues no pasa nada");
								world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
							}else {
								SendAllPlayerMessage("Volvemos a la normalidad...");
								// Establecer el doDaylightCycle en false
								world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
							}
							estadoEleccion = 0;
							b_election20 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else if (votoGanador == 2) {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Establecer el doDaylightCycle en false
							world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
							// Establecer la hora del día a noche
							world.setTime(1000); // 13000 es la hora de Minecraft correspondiente al dia
							SendAllPlayerMessage("Ahora es de DIA -PERMANENTEMENTE-");
							estadoEleccion = 0;
							b_election20 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Establecer el doDaylightCycle en false
							world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
							// Establecer la hora del día a noche
							world.setTime(16000); // 13000 es la hora de Minecraft correspondiente a la noche
							SendAllPlayerMessage("Ahora es de NOCHE -PERMANENTEMENTE-");
							estadoEleccion = 0;
							b_election20 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}

				}

				//Election 21 General
				if (b_election21) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu21());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							event_highGravity = true;
							estadoEleccion = 0;
							b_election21 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else if (votoGanador == 2) {
							event_acidRain = true;
							// Obtener el mundo principal del servidor
							World world = Bukkit.getWorlds().get(0);
							// Activar una tormenta en el mundo principal
							world.setStorm(true);
							world.setThundering(true); // Opcional: activar truenos junto con la tormenta
							estadoEleccion = 0;
							b_election21 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							event_dangerJump = true;

							estadoEleccion = 0;
							b_election21 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}

				}

				//Election 22 General
				if (b_election22) {
					if (estadoEleccion == 0) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"La votación General empieza en..." + contador));
						}
						contador--;
						if (contador == 0) {
							estadoEleccion = 1;
						}
					}
					if (estadoEleccion == 1) {
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.openInventory(createMenu22());
						}
						estadoEleccion = 2;
					}
					if (estadoEleccion == 2) {
						tiempoCooldown--;
						for (Player jugador : Bukkit.getOnlinePlayers()) {
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "La votación termina en: " + tiempoCooldown));
						}
						if (tiempoCooldown <= 0) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.closeInventory();
							}
							tiempoCooldown = 30;
							estadoEleccion = 3;
						}
					}
					if (estadoEleccion == 3) {
						maxVotes = Math.max(vote1, vote2);
						maxVotes = Math.max(maxVotes, vote3);
						if (maxVotes == vote1) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto1"));
								votoGanador = 1;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else if (maxVotes == vote2) {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto2"));
								votoGanador = 2;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						} else {
							for (Player jugador : Bukkit.getOnlinePlayers()) {
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+"Ha ganado el voto3"));
								votoGanador = 3;
							}
							estadoEleccion = 4;
							maxVotes = 0;
						}
					}
					if (estadoEleccion == 4) {
						if (votoGanador == 1) {
							SendAllPlayerMessage("Pues no pasa nada");
							estadoEleccion = 0;
							b_election22 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else if (votoGanador == 2) {
							event_freezeNight = true;
							SendAllPlayerMessage("Va a empezar a hacer frio");
							estadoEleccion = 0;
							b_election22 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						} else {
							event_heatDay = true;
							SendAllPlayerMessage("Mmmm te pusiste caliente...");
							estadoEleccion = 0;
							b_election22 = false;
							votoGanador = 0;
							vote1 = 0;
							vote2 = 0;
							vote3 = 0;
							contador = 10;
						}
					}

				}


			}
		}.runTaskTimer(this,0,20); // 0 indica que la tarea comenzará en el próximo tick
		// 20 indica que la tarea se repetirá cada 1 segundo
	}
	
	
	
	private void corruptedElections(){
		// Ejecutar una tarea cada 20 ticks (1 segundo en el juego)
		new BukkitRunnable() {
			public void run() {
				//Aqui se añaden los eventos corruptos y sus respectivas consequecias
				if (event_highGravity){
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
						event_highGravity = false;
						event_highGravity_time = 0;
						SendAllPlayerMessage("La gravedad ha vuelto a la normalidad");
					}
				}

				if (event_acidRain){
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
						event_acidRain = false;
						event_acidRain_time = 0;
						SendAllPlayerMessage("Ya ha passado la tormenta");
					}
				}

				if(event_dangerJump){
					event_dangerJump_time++;
					if (event_dangerJump_time>=(60*5)){
						event_dangerJump = false;
						event_dangerJump_time = 0;
					}
				}

				if(event_freezeNight){
					event_freezeNight_time++;

					for (Player jugador : Bukkit.getOnlinePlayers()){
						if (jugador.getLocation().getBlock().getLightLevel()<=5){
							playerFreeze(jugador);
							SendAllPlayerMessage("¡Busca un punto de luz para entrar en calor!");
						}
					}

					if (event_freezeNight_time>=(60*10)){
						event_freezeNight = false;
						event_freezeNight_time = 0;
						SendAllPlayerMessage("Ya ha passado el frio...");
					}
				}

				if(event_heatDay){
					event_heatDay_time++;

					for (Player jugador : Bukkit.getOnlinePlayers()){
						if (jugador.getLocation().getBlock().getLightLevel()>=10){
							playerHeat(jugador);
							SendAllPlayerMessage("¡Alejate de la luz que hace mucho calor!");
						}
					}

					if (event_heatDay_time>=(60*10)){
						event_heatDay = false;
						event_heatDay_time = 0;
						SendAllPlayerMessage("Ya no hace tanto calor...");
					}
				}
			}
		}.runTaskTimer(this, 0, 20); // 0 indica que la tarea comenzará en el próximo tick
		// 20 indica que la tarea se repetirá cada 1 segundo
	}
	
	
	
	
	
	

	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&',prefix+"&eha sido desactivado! &fVersion: "+version));
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&',prefix+"&eGracias por usar mi plugin"));

		config.set("SpawnEntities.EnemyDifficulty.Trigger", SpawnEntities_DistanceEnemyDifficulty_Trigger);
		config.set("SpawnEntities.EnemyDifficulty.ProbabilityMin", SpawnEntities_EnemyDifficulty_ProbabilityMin);
		config.set("SpawnEntities.EnemyDifficulty.ProbabilityMax", SpawnEntities_EnemyDifficulty_ProbabilityMax);
		config.set("SpawnEntities.EnemyDifficulty.SpeedEffect", SpawnEntities_EnemyDifficulty_SpeedEffect);
		config.set("SpawnEntities.EnemyDifficulty.SpeedEffectLvL", SpawnEntities_EnemyDifficulty_ResistanceEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.ResistanceEffect", SpawnEntities_EnemyDifficulty_ResistanceEffect);
		config.set("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL", SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.StrengthEffect", SpawnEntities_EnemyDifficulty_StrengthEffect);
		config.set("SpawnEntities.EnemyDifficulty.StrengthEffectLvL", SpawnEntities_EnemyDifficulty_StrengthEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.GlowingEffect", SpawnEntities_EnemyDifficulty_GlowingEffect);
		config.set("SpawnEntities.EnemyDifficulty.FireResistanceEffect", SpawnEntities_EnemyDifficulty_FireResistanceEffect);
		config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect", SpawnEntities_EnemyDifficulty_DolphinsGraceEffect);
		config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL", SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL);

		config.set("SpawnEntities.DistanceEnemyDifficulty.Trigger", SpawnEntities_DistanceEnemyDifficulty_Trigger);
		config.set("SpawnEntities.DistanceEnemyDifficulty.Period", SpawnEntities_DistanceEnemyDifficulty_Period);
		config.set("SpawnEntities.DistanceEnemyDifficulty.Start", SpawnEntities_DistanceEnemyDifficulty_Start);

		config.set("SpawnEntities.TimeEnemyDifficulty.Trigger", SpawnEntities_TimeEnemyDifficulty_Trigger);
		config.set("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale", SpawnEntities_TimeEnemyDifficulty_TimePeriodScale);
		config.set("SpawnEntities.TimeEnemyDifficulty.TimeScale", SpawnEntities_TimeEnemyDifficulty_TimeScale);//D,H,M,S

		config.set("Huracane.Trigger",Huracane_Trigger);
		config.set("Huracane.DamageScale",Huracane_DamageScale);
		config.set("Huracane.Period",Huracane_Period);

		config.set("SpawnEntities.TimeEnemyDifficulty.DaysSaved",time_days);
		config.set("SpawnEntities.TimeEnemyDifficulty.HoursSaved",time_hours);
		config.set("SpawnEntities.TimeEnemyDifficulty.MinutesSaved",time_min);
		config.set("SpawnEntities.TimeEnemyDifficulty.SecondsSaved",time_sec);
		config.set("SpawnEntities.DistanceEnemyDifficulty.Center.X",DistanceEnemyDifficulty_CenterX);
		config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Y",DistanceEnemyDifficulty_CenterY);
		config.set("SpawnEntities.DistanceEnemyDifficulty.Center.Z",DistanceEnemyDifficulty_CenterZ);
		config.set("Huracane.Center.X",Huracane_CenterX);
		config.set("Huracane.Center.Y",Huracane_CenterY);
		config.set("Huracane.Center.Z",Huracane_CenterZ);
		this.saveConfig();

	}
	public void registerCommands(){
		this.getCommand("difficulty-reload").setExecutor(new reloadCommand(this));
		this.getCommand("diff-time").setExecutor(new adjustTimeCommand(this));
		this.getCommand("difficulty-save").setExecutor(new saveCommand(this));
		this.getCommand("menueleccion").setExecutor(new menuEleccion(this));
		this.getCommand("selectionElection").setExecutor(new commandSelectionElection(this));
		this.getCommand("corruptedEvents").setExecutor(new corruptedEventCommand(this));

	}

	public double calcularDistancia(Location loc1, Location loc2){
		return loc1.distance(loc2);
	}
	private static void intercambiarInventarios(Player jugador1, Player jugador2) {
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
	private static void intercambiarPosiciones(Player jugador1, Player jugador2) {
		// Guardar los inventarios en variables temporales
		Location PosicionTemporalJugador1 = jugador1.getLocation();
		Location PosicionTemporalJugador2 = jugador2.getLocation();
		// Borrar los inventarios de ambos jugadores
		jugador1.teleport(PosicionTemporalJugador2);
		jugador2.teleport(PosicionTemporalJugador1);

	}
	public static void SendAllPlayerMessage(String message) {
		for (Player jugador : Bukkit.getOnlinePlayers()) {
			jugador.sendMessage(message);
		}
	}

	public static boolean acidRainHelp(Player jugador){
		for (int i = 1; i <= 200; i++) {
			Block bloqueEncima = jugador.getLocation().add(0, i, 0).getBlock();
			if (bloqueEncima.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}

	public static void playerFreeze(Player jugador){
		jugador.setFreezeTicks(jugador.getMaxFreezeTicks()+100);
	}

	public static void playerHeat(Player jugador){
		jugador.setFireTicks(100);
		//jugador.setVisualFire(true);
	}


}


