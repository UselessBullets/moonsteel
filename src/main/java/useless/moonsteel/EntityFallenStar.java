package useless.moonsteel;

import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.world.World;

public class EntityFallenStar extends EntityItem {
	public EntityFallenStar(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, MoonSteel.fallenStar.getDefaultStack());
		viewScale = 5;
	}
	@Override
	public void tick(){
		super.tick();
		if (world.isDaytime()){
			this.remove();
		}
	}
	public EntityFallenStar(World world){
		super(world);
		viewScale = 5;
	}
}
