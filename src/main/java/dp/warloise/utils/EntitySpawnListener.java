package dp.warloise.utils;

import dp.warloise.DifficultyPlugin;
import static dp.warloise.DifficultyPlugin.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.lang.Math;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;



/**
 * Esta clase se usa de listener para la generación de entidades
 */
public class EntitySpawnListener implements Listener {

    private final DifficultyPlugin plugin;

    public EntitySpawnListener(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @param event Es el trigger de la generación de las entidades
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {

        //Codigo de elecciones
        //Guardmos la entidad que ha generado el evento
        Entity entity = event.getEntity();
        //Si la entidad es un objeto Enemy...
        if (entity instanceof Enemy enemigo) {
            //Este bloque reacciona para los enemigos en general
            if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.Trigger")) {
                int minCancel = plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMin");
                int maxCancel = plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ProbabilityMax");
                //Generamos un numero random entre el minCancel y el maxCancel
                int randomNum = (int) (minCancel + Math.random() * maxCancel);
                //si el numero generado es mas grande o igual al numero maximo...
                if (randomNum >= maxCancel) {
                    //Identificador del enemigo
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.SpeedEffect")) {
                        AttributeInstance velocidad = enemigo.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                        assert velocidad != null;
                        double nuevaVelocidad = velocidad.getBaseValue()+((double) plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL") /100);
                        velocidad.setBaseValue(nuevaVelocidad);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect")) {
                        AttributeInstance defensa = (enemigo.getAttribute(Attribute.GENERIC_ARMOR));
                        assert defensa != null;
                        double nuevadefensa = defensa.getBaseValue()+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL");
                        defensa.setBaseValue(nuevadefensa);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect")) {
                        AttributeInstance ataque = (enemigo.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE));
                        assert ataque != null;
                        double nuevoDano = ataque.getBaseValue()+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL");
                        ataque.setBaseValue(nuevoDano);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.GlowingEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.FireResistanceEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL"), true, true));
                    }
                }
            }
            
            if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.CreepersCharged")) {
            	if (entity.getType() == EntityType.CREEPER) {
                    Creeper creeper = (Creeper) entity;
                    // Hacer que el Creeper esté cargado
                    creeper.setPowered(true);
                }
            }
            if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.EndermansAgressives")) {
            	// Verificar si la entidad generada es un Enderman
                if (entity.getType() == EntityType.ENDERMAN) {
                    Enderman enderman = (Enderman) entity;
                    // Buscar un jugador cercano
                    Player nearestPlayer = null;
                    double nearestDistance = Double.MAX_VALUE;
                    for (Player player : entity.getWorld().getPlayers()) {
                        double distance = player.getLocation().distance(entity.getLocation());
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestPlayer = player;
                        }
                    }
                    // Si hay un jugador cerca, hacer que el Enderman se vuelva agresivo hacia él
                    if (nearestPlayer != null) {
                        enderman.setTarget(nearestPlayer);
                    }
                }
            }
            if(plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.PigsArePigmansAgressives")) {
            	// Verificar si la entidad generada es un cerdo
            	
                if (entity.getType() == EntityType.PIG) {
                    Pig pig = (Pig) entity;

                    // Convertir el cerdo en un Zombie Pigman
                    PigZombie pigman = (PigZombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIFIED_PIGLIN);
                    pigman.setAngry(true); // Hacer que el Zombie Pigman esté enfadado
                    pig.remove(); // Eliminar el cerdo original

                    // Buscar un jugador cercano y hacer que el Pigman sea agresivo hacia él
                    Player nearestPlayer = null;
                    double nearestDistance = Double.MAX_VALUE;

                    for (Player player : entity.getWorld().getPlayers()) {
                        double distance = player.getLocation().distance(entity.getLocation());
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestPlayer = player;
                        }
                    }

                    // Si hay un jugador cerca, hacer que el Pigman se vuelva agresivo hacia él
                    if (nearestPlayer != null) {
                        pigman.setTarget(nearestPlayer);
                    }
                }
            }
            
            //Este bloque reacciona para las entidades que llegan a ciertas distancias de render, al generarse estan mas fuertes.
            if (plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.Trigger")) {
                //Rename entity by maxRange?
                int entityX = (int) Math.abs(entity.getLocation().getX());
                int entityY = (int) Math.abs(entity.getLocation().getY());
                int entityZ = (int) Math.abs(entity.getLocation().getZ());
                int centerX = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.X"));
                int centerY = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Y"));
                int centerZ = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Z"));
                int period = plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Period");
                int divisionX = Math.abs(entityX - centerX) / period;
                int divisionY = Math.abs(entityY - centerY) / period;
                int divisionZ = Math.abs(entityZ - centerZ) / period;
                int maxRange = Math.max(divisionX, divisionY);
                maxRange = Math.max(maxRange, divisionZ);
                if (maxRange > 0) {
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.SpeedEffect")) {
                        AttributeInstance velocidad = enemigo.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                        assert velocidad != null;
                        double nuevaVelocidad = velocidad.getBaseValue() + ((double) maxRange /100) + ((double) plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL") /100) + ((double) plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start") /100);
                        velocidad.setBaseValue(nuevaVelocidad);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect")) {
                        AttributeInstance defensa = ((Enemy) entity).getAttribute(Attribute.GENERIC_ARMOR);
                        assert defensa != null;
                        double nuevadefensa = defensa.getBaseValue()+maxRange * plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start");
                        defensa.setBaseValue(nuevadefensa);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect")) {
                        AttributeInstance ataque = ((Enemy) entity).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                        assert ataque != null;
                        double nuevoDano = ataque.getBaseValue()+maxRange * plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start");
                        ataque.setBaseValue(nuevoDano);
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start"), true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.GlowingEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.FireResistanceEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));
                    }
                    if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect")) {
                        enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL") + plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.Start"), true, true));
                    }
                }
            }
            //Aqui se va el codigo de reacción al tiempo...
            //Intentar la configuracion usando segundos, minutos, horas, dias...
            int timeAmplifier = 0;
            if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")) {
                int realTimeDays=time_days;
                int realTimeHours=time_hours+(realTimeDays*24);
                int realTimeMinutes=time_min+(realTimeHours*60);
                int realTimeSeconds=time_sec+(realTimeMinutes*60);
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "D")) {
                    if (realTimeDays >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = realTimeDays / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        TimeControl(timeAmplifier,enemigo);
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "H")) {
                    if (realTimeHours >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = realTimeHours / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        TimeControl(timeAmplifier,enemigo);
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "M")) {
                    if (realTimeMinutes >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = realTimeMinutes / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        TimeControl(timeAmplifier,enemigo);
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "S")) {
                    if (realTimeSeconds >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = realTimeSeconds / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        TimeControl(timeAmplifier,enemigo);
                    }
                }
            }
        }
    }

    private void TimeControl(int timeAmplifier,Enemy enemigo){
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.SpeedEffect")) {
            AttributeInstance velocidad = enemigo.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            assert velocidad != null;
            double nuevaVelocidad = velocidad.getBaseValue()+((double) timeAmplifier /100)+((double) plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.SpeedEffectLvL") /100);
            velocidad.setBaseValue(nuevaVelocidad);
            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
        }
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect")) {
            AttributeInstance defensa = (enemigo.getAttribute(Attribute.GENERIC_ARMOR));
            assert defensa != null;
            double nuevadefensa = defensa.getBaseValue()+timeAmplifier+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL");
            defensa.setBaseValue(nuevadefensa);
            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
        }
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect")) {
            AttributeInstance ataque = (enemigo.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE));
            assert ataque != null;
            double nuevoDano = ataque.getBaseValue()+timeAmplifier+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL");
            ataque.setBaseValue(nuevoDano);
            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
        }
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.GlowingEffect")) {
            enemigo.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1, true, false));
        }
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.FireResistanceEffect")) {
            enemigo.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));
        }
        if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.DolphinsGraceEffect")) {
            enemigo.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.DolphinsGraceEffectLvL"), true, true));
        }
    }
}



