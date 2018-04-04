### Better Tekkit Classic Server ++
ForgeBukkit is not availible anymore, and the default TC server's performance is horrible. That's the reason why Project BTCS was created.

BTCS++ is a remake of the Tekkit Classic server which focuses on optimization. It is not to be confused with the [original BTCS](https://github.com/hypothermic/BTCS/tree/old_btcs) or [plus](https://github.com/hypothermic/BTCS/tree/plus) (which were both discontinued due to huge performance issues)

__Current status:__ BTCS++ is currently in beta. Please report any issues. If you know how to fix them, send a pull request with your fix.

### Pull Request conventions
* No trailing whitespaces.
* Follow the [Oracle Code Conventions](http://www.oracle.com/technetwork/java/codeconvtoc-136057.html)
* Mark your changes with:
    * 1 line; add a trailing: `// BTCS [: Optional reason]`
    * 2+ lines; add
        * Before: `// BTCS start [: Optional comment]`
        * After: `// BTCS end`

### TODO:
- Solve WM bullet entities not registered error
- BlockVine forge patch (failed last time)
- ConsoleCommandHandler forge patch
- Remove Mojang Statistics
- Remove Bukkit's Updater
- Limit NBT depth
- Implement NBTReadLimiter into NBTTagList
- Better error reporting
- Split every world on it's own thread

### Changelog
- v1.07
    - Added this readme
    - Implemented forge patches for all blocks and entities
    - 'Fixed' IC2 tile entity issues
    - Fixed log file not being created
    - Added ServerController API
- v1.06
    - Scrapped BTCS+ code and rebuilt from the ground up.
    
### Credits
__BTCS++ uses:__
- Bukkit API by the Bukkit Team
- Forge by LexManos, Eloraam, SpaceToad, FlowerChild, Hawkye, MALfunction84, Scokeev9
- ForgeModLoader by cpw
- ForgePlugin by Paul Buonopane

__BTCS++ includes the following mods:__
- Balkon's Weapon mod by BalkondeurAlpha  
- Buildcraft by SpaceToad
- Buildcraft Additional Pipes by SpaceToad, Krapht, SirSengir
- ComputerCraft by dan200
    - ccSensors by yoskaz01
- Ender Storage by Ecu & chicken_bones
- Equivalent Exchange 2 by x3n0ph0b3, MidnightLightning and maintainer Pahimar (FP improved version)
- IndustrialCraft 2 by Albaka & IC2 Team
    - IC2 Advanced Machines by AtomicStryker
    - IC2 Compact Solars by cpw
    - IC2 Nuclear Control by Shedar
    - IC2 Charging Bench by Drashian
- Immibis Core by immibis
    - Dimensional Anchors by immibis
    - TubeStuff by immibis (FP improved version)
- Iron Chests by cpw
- Nether Ores by Powercrystals
- Not Enough Items by chicken_bones
- Modular Force Field Systems by ThunderDark and immibis
- PowerConverters by Powercrystals
- RailCraft 5 by CovertJaguar (FP improved version)
- RedPower 2 by Eloraam (FP improved version)
- WRCBE by chicken_bones

__BTCS++'s maven dependencies:__
- Ebean by avaje
- Guava and Gson by Google
- MySQL JDBC
- JLine
- Commons by Apache
- Collect by Google
- JOptSimple by Paul Holser
- Snakeyaml by asomov
- Jansi by fusesource
- Zip4j by Lingala

(licenses are included in the "licenses" folder)

_Special thanks to LexManos for ignoring all my questions on the Forge forums. /s_