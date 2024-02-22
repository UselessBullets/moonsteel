package useless.moonsteel.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.MoonSteel;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class EntityLivingMixin extends Entity {
	public EntityLivingMixin(World world) {
		super(world);
	}

	@Shadow
	protected abstract void dropFewItems();

	@Inject(method = "onDeath(Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityLiving;dropFewItems()V"))
	private void multiplyDrop(Entity entity, CallbackInfo ci){
		if (entity instanceof EntityPlayer){
			ItemStack heldStack = ((EntityPlayer) entity).getHeldItem();
			if (heldStack != null && heldStack.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) heldStack.getItem()).getMaterial() == MoonSteel.moonSteelTool){
				for (int i = 0; i < random.nextInt(3); i++) {
					dropFewItems();
				}
			}
		}
	}
}
