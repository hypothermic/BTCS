package cpw.mods.fml.common;

public abstract interface IConsoleHandler
{
  public abstract boolean handleCommand(String paramString, Object... paramVarArgs);
}
