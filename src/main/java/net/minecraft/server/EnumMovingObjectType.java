package net.minecraft.server;

public enum EnumMovingObjectType {
	
	// BTCS start
	TILE("TILE", 0), ENTITY("ENTITY", 1);

    private static final EnumMovingObjectType[] c = new EnumMovingObjectType[] { TILE, ENTITY};

    private EnumMovingObjectType(String s, int i) {}
	// BTCS end
}
