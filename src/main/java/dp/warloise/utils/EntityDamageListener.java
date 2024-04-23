package dp.warloise.utils;
import dp.warloise.DifficultyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static dp.warloise.DifficultyPlugin.*;
public class EntityDamageListener implements Listener { //Detecta quien recibe el daño

    private final DifficultyPlugin plugin;

    public EntityDamageListener(DifficultyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntityDamageEvent event) {
        double normalDamage = event.getDamage();
        Entity entityRecibe = event.getEntity();
        if (entityRecibe instanceof Player){
            if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.Trigger")) {
                if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.StrengthEffect")) {
                    event.setDamage(normalDamage+plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.StrengthEffectLvL"));
                }
            } else if (plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.Trigger")) {
                int entityX = (int) Math.abs(entityRecibe.getLocation().getX());
                int entityY = (int) Math.abs(entityRecibe.getLocation().getY());
                int entityZ = (int) Math.abs(entityRecibe.getLocation().getZ());
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
                    if (plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.StrengthEffect")) {
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start"), true, true));
                        event.setDamage(normalDamage+maxRange*plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier"));
                    }
                }
            }else if(plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")){
                int timeAmplifier = 0;
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "D")) {
                    if (time_days >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_days / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.StrengthEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage+timeAmplifier*plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.StrengthEffectLvL"));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "H")) {
                    if (time_hours >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_hours / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.StrengthEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage+timeAmplifier*plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "M")) {
                    if (time_min >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_min / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.StrengthEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage+timeAmplifier*plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "S")) {
                    if (time_sec >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_sec / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.StrengthEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage+timeAmplifier*plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"));
                        }
                    }
                }
            }
        }

        //Fragmento de codigo para la resistencia de los enemigos
        if (entityRecibe instanceof Enemy){
            if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.Trigger")) {
                if (plugin.getConfig().getBoolean("SpawnEntities.EnemyDifficulty.ResistanceEffect")) {
                    event.setDamage(plugin.getConfig().getInt("SpawnEntities.EnemyDifficulty.ResistanceEffectLvL")-normalDamage);
                }
            } else if (plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.Trigger")) {
                int entityX = (int) Math.abs(entityRecibe.getLocation().getX());
                int entityY = (int) Math.abs(entityRecibe.getLocation().getY());
                int entityZ = (int) Math.abs(entityRecibe.getLocation().getZ());
                int centerX = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.X"));
                int centerY = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Y"));
                int centerZ = Math.abs(plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Center.Z"));
                int period = plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Period");
                int divisionX = Math.abs(entityX - centerX) / period;
                int divisionY = Math.abs(entityY - centerY) / period;
                int divisionZ = Math.abs(entityZ - centerZ) / period;
                int maxRange = Math.max(divisionX, divisionY);
                maxRange = Math.max(maxRange, divisionZ);
                Bukkit.getConsoleSender().sendMessage("El rango maximo esta en: "+maxRange);
                Bukkit.getConsoleSender().sendMessage("Ademas el aplifier esta en: "+plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier"));
                Bukkit.getConsoleSender().sendMessage("Y el daño normal es de: "+normalDamage);
                if (maxRange > 0) {
                    if (plugin.getConfig().getBoolean("SpawnEntities.DistanceEnemyDifficulty.ResistanceEffect")) {
                        //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, maxRange + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier") + plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Start"), true, true));
                        event.setDamage(normalDamage-(maxRange+plugin.getConfig().getInt("SpawnEntities.DistanceEnemyDifficulty.Amplifier")));
                    }
                }
            }else if(plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.Trigger")){
                int timeAmplifier = 0;
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "D")) {
                    if (time_days >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_days / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.ResistanceEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage-(timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier")));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "H")) {
                    if (time_hours >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_hours / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.ResistanceEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage-(timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier")));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "M")) {
                    if (time_min >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_min / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.ResistanceEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage-(timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier")));
                        }
                    }
                }
                if (Objects.equals(plugin.getConfig().getString("SpawnEntities.TimeEnemyDifficulty.TimeScale"), "S")) {
                    if (time_sec >= plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale")) {
                        timeAmplifier = time_sec / plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.TimePeriodScale");
                        if (plugin.getConfig().getBoolean("SpawnEntities.TimeEnemyDifficulty.ResistanceEffect")) {
                            //enemigo.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier"), true, true));
                            event.setDamage(normalDamage-(timeAmplifier+plugin.getConfig().getInt("SpawnEntities.TimeEnemyDifficulty.EffectAmplifier")));
                        }
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("El daño que has recibido es: "+event.getFinalDamage());
    }
}
