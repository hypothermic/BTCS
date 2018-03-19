package forge.packets;





public class PacketMissingMods
  extends PacketModList
{
  public PacketMissingMods(boolean server)
  {
    super(!server);
  }
  

  public int getID()
  {
    return 3;
  }
}
