### Better Tekkit Classic Server
A remake of the TC server which focuses on optimization.

__Current status:__ BTCS, including Forge and CraftBukkit, is fully working except for loading Forge mods. See issue [#1](/../../issues/1)

### Pull Request conventions
* No trailing whitespaces.
* Follow the [Oracle Code Conventions](http://www.oracle.com/technetwork/java/codeconvtoc-136057.html)
* Mark your changes with:
    * 1 line; add a trailing: `// BTCS [: Optional reason]`
    * 2+ lines; add
        * Before: `// BTCS start [: Optional comment]`
        * After: `// BTCS end`

### TODO:
- ~~"Get it working"~~
- Get Forge Mods working (see [this issue](https://github.com/hypothermic/BTCS/issues/1))
- Remove Mojang Statistics
- Remove Bukkit's Updater
- Implement NBTReadLimiter into NBTTagList
- For all errors, dump stacktrace directly to console
- Split every world on it's own thread
- Include mods folder in package

### Changelog
- v1.30
    - Fixed world generation (see comment in Chunck.java line 150)
    - Added debugging mode
    - Fixed downcasting issues in WorldGenVillagePieces, WeightedRandom, StructurePiece and StructureGenerator
- v1.28
    - Added NBT max depth to fix vulnerability: http://blog.ammaraskar.com/minecraft-vulnerability-advisory/
        - Added to-do: implement NBTReadLimiter, priority medium.
    - Removed EE2 from resources folder.
    - Crunched a lot of bugs.
    - Moved file declaration to Launcher class.
    - Removed LuaJ dependency
- v1.27
    - Fixed mod loading issue with Balkon's Weapon Mod.
- v1.26
    - Fixed item init issues (see comment @ ItemStack.java:27).
    - Added ForgeExchanger.
    - Fixed issues with Forge not being able to load the core MC classes.
- v1.25
    - Added ConfigurationManager and moved the config stuff into there.
    - Updated Launcher to delete the temporary file after deploying the resources.
    - Fixed bug in ItemStack.setData()
- v1.24
    - Added ResourceManager and moved resource unpacking stuff to there.
- v1.21
    - Integrated CB's Main class into the Launcher
    - Fixed some JOptSimple stuff.
    
### Credits
__BTCS uses:__
- Bukkit API by the Bukkit Team
- Forge by LexManos, Eloraam, SpaceToad, FlowerChild, Hawkye, MALfunction84, Scokeev9
- ForgeModLoader by cpw

__BTCS contains the following mods:__
- Balkon's Weapon mod by BalkondeurAlpha  
- Equivalent Exchange 2 by x3n0ph0b3, MidnightLightning and maintainer Pahimar

__BTCS's maven dependencies:__
- Ebean by avaje
- Guava and Gson by Google
- MySql JDBC
- JLine
- Commons by Apache
- Collect by Google
- JOptSimple by Paul Holser
- Snakeyaml by asomov
- Jansi by fusesource
- Zip4j by Lingala

_Special thanks to LexManos for ignoring all my questions on the Forge forums. /s_