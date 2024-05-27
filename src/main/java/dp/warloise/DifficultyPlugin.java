package dp.warloise;

import dp.warloise.commands.*;
import dp.warloise.utils.Eleccion;
import dp.warloise.utils.menuEleccion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
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
import java.util.*;

import static dp.warloise.utils.menuEleccion.*;

public class DifficultyPlugin extends JavaPlugin {

	DifficultyPlugin enNombreDelPlugin=this;

	//Try
	public static Eleccion objetoEleccion;

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
	public static boolean SpawnEntities_EnemyDifficulty_CreepersCharged=false;
	public static boolean SpawnEntities_EnemyDifficulty_EndermansAgressives=false;
	public static boolean SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives=false;
	public static boolean SpawnEntities_EnemyDifficulty_VillagerTrades=true;
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
		objetoEleccion=new Eleccion(this);
		huracanePassive();
		timePassive();
		//election1Passive();
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
				config.set("SpawnEntities.EnemyDifficulty.CreepersCharged", false);
				config.set("SpawnEntities.EnemyDifficulty.EndermansAgressives", false);
				config.set("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives", false);
				config.set("SpawnEntities.EnemyDifficulty.VillagerTrades", true);

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

		SpawnEntities_EnemyDifficulty_Trigger=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.Trigger");
		SpawnEntities_EnemyDifficulty_ProbabilityMin=getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMin");
		SpawnEntities_EnemyDifficulty_ProbabilityMax=getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMax");
		SpawnEntities_EnemyDifficulty_SpeedEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.SpeedEffect");
		SpawnEntities_EnemyDifficulty_SpeedEffectLvL=getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL");
		SpawnEntities_EnemyDifficulty_ResistanceEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect");
		SpawnEntities_EnemyDifficulty_ResistanceEffectLvL=getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL");
		SpawnEntities_EnemyDifficulty_StrengthEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect");
		SpawnEntities_EnemyDifficulty_StrengthEffectLvL=getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL");
		SpawnEntities_EnemyDifficulty_GlowingEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.GlowingEffect");
		SpawnEntities_EnemyDifficulty_FireResistanceEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.FireResistanceEffect");
		SpawnEntities_EnemyDifficulty_DolphinsGraceEffect=getConfig().getBoolean("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect");
		SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL=getConfig().getInt("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL");
		SpawnEntities_EnemyDifficulty_CreepersCharged = getConfig().getBoolean("SpawnEntities.EnemyDifficulty.CreepersCharged");
		SpawnEntities_EnemyDifficulty_EndermansAgressives = getConfig().getBoolean("SpawnEntities.EnemyDifficulty.EndermansAgressives");
		SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives = getConfig().getBoolean("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives");


		SpawnEntities_DistanceEnemyDifficulty_Trigger=getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.Trigger");
		SpawnEntities_DistanceEnemyDifficulty_Period=getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Period");
		SpawnEntities_DistanceEnemyDifficulty_Start=getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start");
		SpawnEntities_TimeEnemyDifficulty_Trigger=getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger");
		SpawnEntities_TimeEnemyDifficulty_TimePeriodScale=getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
		SpawnEntities_TimeEnemyDifficulty_TimeScale=getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale");
		Huracane_Trigger=getConfig().getBoolean("Huracane.Trigger");
		Huracane_DamageScale=getConfig().getInt("Huracane.DamageScale");
		Huracane_Period=getConfig().getInt("Huracane.Period");
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
	private void timePassive() {
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

	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&',prefix+"&eha sido desactivado! &fVersion: "+version));
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.translateAlternateColorCodes('&',prefix+"&eGracias por usar mi plugin"));

		config.set("SpawnEntities.EnemyDifficulty.Trigger", SpawnEntities_EnemyDifficulty_Trigger);
		config.set("SpawnEntities.EnemyDifficulty.ProbabilityMin", SpawnEntities_EnemyDifficulty_ProbabilityMin);
		config.set("SpawnEntities.EnemyDifficulty.ProbabilityMax", SpawnEntities_EnemyDifficulty_ProbabilityMax);
		config.set("SpawnEntities.EnemyDifficulty.SpeedEffect", SpawnEntities_EnemyDifficulty_SpeedEffect);
		config.set("SpawnEntities.EnemyDifficulty.SpeedEffectLvL", SpawnEntities_EnemyDifficulty_SpeedEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.ResistanceEffect", SpawnEntities_EnemyDifficulty_ResistanceEffect);
		config.set("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL", SpawnEntities_EnemyDifficulty_ResistanceEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.StrengthEffect", SpawnEntities_EnemyDifficulty_StrengthEffect);
		config.set("SpawnEntities.EnemyDifficulty.StrengthEffectLvL", SpawnEntities_EnemyDifficulty_StrengthEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.GlowingEffect", SpawnEntities_EnemyDifficulty_GlowingEffect);
		config.set("SpawnEntities.EnemyDifficulty.FireResistanceEffect", SpawnEntities_EnemyDifficulty_FireResistanceEffect);
		config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect", SpawnEntities_EnemyDifficulty_DolphinsGraceEffect);
		config.set("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL", SpawnEntities_EnemyDifficulty_DolphinsGraceEffectLvL);
		config.set("SpawnEntities.EnemyDifficulty.CreepersCharged", SpawnEntities_EnemyDifficulty_CreepersCharged);
		config.set("SpawnEntities.EnemyDifficulty.EndermansAgressives", SpawnEntities_EnemyDifficulty_EndermansAgressives);
		config.set("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives", SpawnEntities_EnemyDifficulty_PigsArePigmansAgressives);
		config.set("SpawnEntities.EnemyDifficulty.VillagerTrades", SpawnEntities_EnemyDifficulty_VillagerTrades);

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
}


