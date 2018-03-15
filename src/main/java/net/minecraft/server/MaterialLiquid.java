package net.minecraft.server;

public class MaterialLiquid extends Material {
  public MaterialLiquid(MaterialMapColor paramMaterialMapColor) {
    super(paramMaterialMapColor);
    h();
    m();
  }
  
  public boolean isLiquid() {
    return true;
  }
  
  public boolean isSolid() {
    return false;
  }
  
  public boolean isBuildable() {
    return false;
  }
}
