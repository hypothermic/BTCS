package org.bukkit.plugin;

import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.FileUtil;

public class SimplePluginManager implements PluginManager
{
  private final Server server;
  private final Map<Pattern, PluginLoader> fileAssociations = new HashMap();
  protected final List<Plugin> plugins = new ArrayList();
  protected final Map<String, Plugin> lookupNames = new HashMap();
  private static File updateDirectory = null;
  private final SimpleCommandMap commandMap;
  private final Map<String, Permission> permissions = new HashMap();
  private final Map<Boolean, Set<Permission>> defaultPerms = new LinkedHashMap();
  private final Map<String, Map<Permissible, Boolean>> permSubs = new HashMap();
  private final Map<Boolean, Map<Permissible, Boolean>> defSubs = new HashMap();
  private boolean useTimings = false;
  
  public SimplePluginManager(Server instance, SimpleCommandMap commandMap) {
    this.server = instance;
    this.commandMap = commandMap;
    
    this.defaultPerms.put(Boolean.valueOf(true), new HashSet());
    this.defaultPerms.put(Boolean.valueOf(false), new HashSet());
  }
  public void registerInterface(Class<? extends PluginLoader> loader)
    throws IllegalArgumentException
  {
	  PluginLoader instance; // BTCS;
    if (PluginLoader.class.isAssignableFrom(loader))
    {
      try
      {
        Constructor<? extends PluginLoader> constructor = loader.getConstructor(new Class[] { Server.class });
        instance = (PluginLoader)constructor.newInstance(new Object[] { this.server });
      } catch (NoSuchMethodException ex) {
        String className = loader.getName();
        
        throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", new Object[] { className, className }), ex);
      } catch (Exception ex) {
        throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", new Object[] { ex.getClass().getName(), loader.getName() }), ex);
      }
    } else {
      throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", new Object[] { loader.getName() }));
    }
    Pattern[] patterns = instance.getPluginFileFilters();
    
    synchronized (this) {
      for (Pattern pattern : patterns) {
        this.fileAssociations.put(pattern, instance);
      }
    }
  }
  





  public Plugin[] loadPlugins(File directory)
  {
    Validate.notNull(directory, "Directory cannot be null");
    Validate.isTrue(directory.isDirectory(), "Directory must be a directory");
    
    List<Plugin> result = new ArrayList();
    Set<Pattern> filters = this.fileAssociations.keySet();
    
    if (!this.server.getUpdateFolder().equals("")) {
      updateDirectory = new File(directory, this.server.getUpdateFolder());
    }
    
    Map<String, File> plugins = prePopulatePluginList();
    Set<String> loadedPlugins = new HashSet();
    loadedPlugins.addAll(plugins.keySet());
    Map<String, Collection<String>> dependencies = new HashMap();
    Map<String, Collection<String>> softDependencies = new HashMap();
    
    PluginDescriptionFile description;
    for (File file : directory.listFiles()) {
      PluginLoader loader = null;
      for (Pattern filter : filters) {
        Matcher match = filter.matcher(file.getName());
        if (match.find()) {
          loader = (PluginLoader)this.fileAssociations.get(filter);
        }
      }
      
      if (loader != null)
      {
        description = null;
        try {
          description = loader.getPluginDescription(file);
        } catch (InvalidDescriptionException ex) {
          this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
          continue;
        }
        
        plugins.put(description.getName(), file);
        
        Collection<String> softDependencySet = description.getSoftDepend();
        if (softDependencySet != null) {
          if (softDependencies.containsKey(description.getName()))
          {
            ((Collection)softDependencies.get(description.getName())).addAll(softDependencySet);
          } else {
            softDependencies.put(description.getName(), new LinkedList(softDependencySet));
          }
        }
        
        Collection<String> dependencySet = description.getDepend();
        if (dependencySet != null) {
          dependencies.put(description.getName(), new LinkedList(dependencySet));
        }
        
        Collection<String> loadBeforeSet = description.getLoadBefore();
        if (loadBeforeSet != null) {
          for (String loadBeforeTarget : loadBeforeSet) {
            if (softDependencies.containsKey(loadBeforeTarget)) {
              ((Collection)softDependencies.get(loadBeforeTarget)).add(description.getName());
            }
            else {
              Collection<String> shortSoftDependency = new LinkedList();
              shortSoftDependency.add(description.getName());
              softDependencies.put(loadBeforeTarget, shortSoftDependency);
            }
          }
        }
      }
    }
    while (!plugins.isEmpty()) {
      boolean missingDependency = true;
      Iterator<String> pluginIterator = plugins.keySet().iterator();
      
      while (pluginIterator.hasNext()) {
        String plugin = (String)pluginIterator.next();
        
        if (dependencies.containsKey(plugin)) {
          Iterator<String> dependencyIterator = ((Collection)dependencies.get(plugin)).iterator();
          
          while (dependencyIterator.hasNext()) {
            String dependency = (String)dependencyIterator.next();
            

            if (loadedPlugins.contains(dependency)) {
              dependencyIterator.remove();

            }
            else if (!plugins.containsKey(dependency)) {
              missingDependency = false;
              File file = (File)plugins.get(plugin);
              pluginIterator.remove();
              softDependencies.remove(plugin);
              dependencies.remove(plugin);
              
              this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", new UnknownDependencyException(dependency));
              


              break;
            }
          }
          
          if ((dependencies.containsKey(plugin)) && (((Collection)dependencies.get(plugin)).isEmpty())) {
            dependencies.remove(plugin);
          }
        }
        if (softDependencies.containsKey(plugin)) {
          Iterator<String> softDependencyIterator = ((Collection)softDependencies.get(plugin)).iterator();
          
          while (softDependencyIterator.hasNext()) {
            String softDependency = (String)softDependencyIterator.next();
            

            if (!plugins.containsKey(softDependency)) {
              softDependencyIterator.remove();
            }
          }
          
          if (((Collection)softDependencies.get(plugin)).isEmpty()) {
            softDependencies.remove(plugin);
          }
        }
        if ((!dependencies.containsKey(plugin)) && (!softDependencies.containsKey(plugin)) && (plugins.containsKey(plugin)))
        {
          File file = (File)plugins.get(plugin);
          pluginIterator.remove();
          missingDependency = false;
          try
          {
            result.add(loadPlugin(file));
            loadedPlugins.add(plugin);
          }
          catch (InvalidPluginException ex) {
            this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
          }
        }
      }
      
      if (missingDependency)
      {

        pluginIterator = plugins.keySet().iterator();
        
        while (pluginIterator.hasNext()) {
          String plugin = (String)pluginIterator.next();
          
          if (!dependencies.containsKey(plugin)) {
            softDependencies.remove(plugin);
            missingDependency = false;
            File file = (File)plugins.get(plugin);
            pluginIterator.remove();
            try
            {
              result.add(loadPlugin(file));
              loadedPlugins.add(plugin);
            }
            catch (InvalidPluginException ex) {
              this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
            }
          }
        }
        
        if (missingDependency) {
          softDependencies.clear();
          dependencies.clear();
          Iterator<File> failedPluginIterator = plugins.values().iterator();
          
          while (failedPluginIterator.hasNext()) {
            File file = (File)failedPluginIterator.next();
            failedPluginIterator.remove();
            this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': circular dependency detected");
          }
        }
      }
    }
    
    return (Plugin[])result.toArray(new Plugin[result.size()]);
  }
  
  protected Map<String, File> prePopulatePluginList() {
    return new HashMap();
  }
  








  public synchronized Plugin loadPlugin(File file)
    throws InvalidPluginException, UnknownDependencyException
  {
    Validate.notNull(file, "File cannot be null");
    
    checkUpdate(file);
    
    Set<Pattern> filters = this.fileAssociations.keySet();
    Plugin result = null;
    
    for (Pattern filter : filters) {
      String name = file.getName();
      Matcher match = filter.matcher(name);
      
      if (match.find()) {
        PluginLoader loader = (PluginLoader)this.fileAssociations.get(filter);
        
        result = loader.loadPlugin(file);
      }
    }
    
    if (result != null) {
      this.plugins.add(result);
      this.lookupNames.put(result.getDescription().getName(), result);
    }
    
    return result;
  }
  
  private void checkUpdate(File file) {
    if ((updateDirectory == null) || (!updateDirectory.isDirectory())) {
      return;
    }
    
    File updateFile = new File(updateDirectory, file.getName());
    if ((updateFile.isFile()) && (FileUtil.copy(updateFile, file))) {
      updateFile.delete();
    }
  }
  







  public synchronized Plugin getPlugin(String name)
  {
    return (Plugin)this.lookupNames.get(name);
  }
  
  public synchronized Plugin[] getPlugins() {
    return (Plugin[])this.plugins.toArray(new Plugin[0]);
  }
  







  public boolean isPluginEnabled(String name)
  {
    Plugin plugin = getPlugin(name);
    
    return isPluginEnabled(plugin);
  }
  





  public boolean isPluginEnabled(Plugin plugin)
  {
    if ((plugin != null) && (this.plugins.contains(plugin))) {
      return plugin.isEnabled();
    }
    return false;
  }
  
  public void enablePlugin(Plugin plugin)
  {
    if (!plugin.isEnabled()) {
      List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
      
      if (!pluginCommands.isEmpty()) {
        this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
      }
      try
      {
        plugin.getPluginLoader().enablePlugin(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
      
      HandlerList.bakeAll();
    }
  }
  
  public void disablePlugins() {
    Plugin[] plugins = getPlugins();
    for (int i = plugins.length - 1; i >= 0; i--) {
      disablePlugin(plugins[i]);
    }
  }
  
  public void disablePlugin(Plugin plugin) {
    if (plugin.isEnabled()) {
      try {
        plugin.getPluginLoader().disablePlugin(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
      try
      {
        this.server.getScheduler().cancelTasks(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
      try
      {
        this.server.getServicesManager().unregisterAll(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
      try
      {
        HandlerList.unregisterAll(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering events for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
      try
      {
        this.server.getMessenger().unregisterIncomingPluginChannel(plugin);
        this.server.getMessenger().unregisterOutgoingPluginChannel(plugin);
      } catch (Throwable ex) {
        this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering plugin channels for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
      }
    }
  }
  
  public void clearPlugins() {
    synchronized (this) {
      disablePlugins();
      this.plugins.clear();
      this.lookupNames.clear();
      HandlerList.unregisterAll();
      this.fileAssociations.clear();
      this.permissions.clear();
      ((Set)this.defaultPerms.get(Boolean.valueOf(true))).clear();
      ((Set)this.defaultPerms.get(Boolean.valueOf(false))).clear();
    }
  }
  





  public void callEvent(Event event)
  {
    if (event.isAsynchronous()) {
      if (Thread.holdsLock(this)) {
        throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from inside synchronized code.");
      }
      if (this.server.isPrimaryThread()) {
        throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from primary server thread.");
      }
      fireEvent(event);
    } else {
      synchronized (this) {
        fireEvent(event);
      }
    }
  }
  
  private void fireEvent(Event event) {
    HandlerList handlers = event.getHandlers();
    RegisteredListener[] listeners = handlers.getRegisteredListeners();
    
    for (RegisteredListener registration : listeners) {
      if (registration.getPlugin().isEnabled())
      {
        try
        {

          registration.callEvent(event);
        } catch (AuthorNagException ex) {
          Plugin plugin = registration.getPlugin();
          
          if (plugin.isNaggable()) {
            plugin.setNaggable(false);
            
            String author = "<NoAuthorGiven>";
            
            if (plugin.getDescription().getAuthors().size() > 0) {
              author = (String)plugin.getDescription().getAuthors().get(0);
            }
            this.server.getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", new Object[] { author, plugin.getDescription().getName(), ex.getMessage() }));
          }
          

        }
        catch (Throwable ex)
        {

          this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getName(), ex);
        } }
    }
  }
  
  public void registerEvents(Listener listener, Plugin plugin) {
    if (!plugin.isEnabled()) {
      throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
    }
    
    for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
      getEventListeners(getRegistrationClass((Class)entry.getKey())).registerAll((Collection)entry.getValue());
    }
  }
  
  public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin)
  {
    registerEvent(event, listener, priority, executor, plugin, false);
  }
  









  public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled)
  {
    Validate.notNull(listener, "Listener cannot be null");
    Validate.notNull(priority, "Priority cannot be null");
    Validate.notNull(executor, "Executor cannot be null");
    Validate.notNull(plugin, "Plugin cannot be null");
    
    if (!plugin.isEnabled()) {
      throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
    }
    
    if (this.useTimings) {
      getEventListeners(event).register(new TimedRegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
    } else {
      getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
    }
  }
  
  private HandlerList getEventListeners(Class<? extends Event> type) {
    try {
      Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList", new Class[0]);
      method.setAccessible(true);
      return (HandlerList)method.invoke(null, new Object[0]);
    } catch (Exception e) {
      throw new IllegalPluginAccessException(e.toString());
    }
  }
  
  private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
    try {
      clazz.getDeclaredMethod("getHandlerList", new Class[0]);
      return clazz;
    } catch (NoSuchMethodException e) {
      if ((clazz.getSuperclass() != null) && (!clazz.getSuperclass().equals(Event.class)) && (Event.class.isAssignableFrom(clazz.getSuperclass())))
      {

        return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
      }
      throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName());
    }
  }
  
  public Permission getPermission(String name)
  {
    return (Permission)this.permissions.get(name.toLowerCase());
  }
  
  public void addPermission(Permission perm) {
    String name = perm.getName().toLowerCase();
    
    if (this.permissions.containsKey(name)) {
      throw new IllegalArgumentException("The permission " + name + " is already defined!");
    }
    
    this.permissions.put(name, perm);
    calculatePermissionDefault(perm);
  }
  
  public Set<Permission> getDefaultPermissions(boolean op) {
    return ImmutableSet.copyOf((Collection)this.defaultPerms.get(Boolean.valueOf(op)));
  }
  
  public void removePermission(Permission perm) {
    removePermission(perm.getName().toLowerCase());
  }
  
  public void removePermission(String name) {
    this.permissions.remove(name);
  }
  
  public void recalculatePermissionDefaults(Permission perm) {
    if (this.permissions.containsValue(perm)) {
      ((Set)this.defaultPerms.get(Boolean.valueOf(true))).remove(perm);
      ((Set)this.defaultPerms.get(Boolean.valueOf(false))).remove(perm);
      
      calculatePermissionDefault(perm);
    }
  }
  
  private void calculatePermissionDefault(Permission perm) {
    if ((perm.getDefault() == PermissionDefault.OP) || (perm.getDefault() == PermissionDefault.TRUE)) {
      ((Set)this.defaultPerms.get(Boolean.valueOf(true))).add(perm);
      dirtyPermissibles(true);
    }
    if ((perm.getDefault() == PermissionDefault.NOT_OP) || (perm.getDefault() == PermissionDefault.TRUE)) {
      ((Set)this.defaultPerms.get(Boolean.valueOf(false))).add(perm);
      dirtyPermissibles(false);
    }
  }
  
  private void dirtyPermissibles(boolean op) {
    Set<Permissible> permissibles = getDefaultPermSubscriptions(op);
    
    for (Permissible p : permissibles) {
      p.recalculatePermissions();
    }
  }
  
  public void subscribeToPermission(String permission, Permissible permissible) {
    String name = permission.toLowerCase();
    Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
    
    if (map == null) {
      map = new WeakHashMap();
      this.permSubs.put(name, map);
    }
    
    map.put(permissible, Boolean.valueOf(true));
  }
  
  public void unsubscribeFromPermission(String permission, Permissible permissible) {
    String name = permission.toLowerCase();
    Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
    
    if (map != null) {
      map.remove(permissible);
      
      if (map.isEmpty()) {
        this.permSubs.remove(name);
      }
    }
  }
  
  public Set<Permissible> getPermissionSubscriptions(String permission) {
    String name = permission.toLowerCase();
    Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
    
    if (map == null) {
      return ImmutableSet.of();
    }
    return ImmutableSet.copyOf(map.keySet());
  }
  
  public void subscribeToDefaultPerms(boolean op, Permissible permissible)
  {
    Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
    
    if (map == null) {
      map = new WeakHashMap();
      this.defSubs.put(Boolean.valueOf(op), map);
    }
    
    map.put(permissible, Boolean.valueOf(true));
  }
  
  public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
    Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
    
    if (map != null) {
      map.remove(permissible);
      
      if (map.isEmpty()) {
        this.defSubs.remove(Boolean.valueOf(op));
      }
    }
  }
  
  public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
    Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
    
    if (map == null) {
      return ImmutableSet.of();
    }
    return ImmutableSet.copyOf(map.keySet());
  }
  
  public Set<Permission> getPermissions()
  {
    return new HashSet(this.permissions.values());
  }
  
  public boolean useTimings() {
    return this.useTimings;
  }
  




  public void useTimings(boolean use)
  {
    this.useTimings = use;
  }
}
