package net.minecraft.server;

public class MaterialPortal extends Material {
  public MaterialPortal(MaterialMapColor paramMaterialMapColor) {
    super(paramMaterialMapColor);
  }
  
  public boolean isBuildable() {
    return false;
  }
  
  public boolean blocksLight() {
    return false;
  }
  
  public boolean isSolid() {
    return false;
  }
}
