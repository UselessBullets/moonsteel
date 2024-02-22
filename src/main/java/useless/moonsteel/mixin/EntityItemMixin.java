package useless.moonsteel.mixin;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.IFallenStar;
import useless.moonsteel.MoonSteel;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixin extends Entity implements IFallenStar {
	@Shadow
	public ItemStack item;
	@Unique
	public boolean despawnInDay = false;

	public EntityItemMixin(World world) {
		super(world);
	}
	@Inject(method = "<init>(Lnet/minecraft/core/world/World;DDDLnet/minecraft/core/item/ItemStack;)V", at = @At("TAIL"))
	private void constuct1(World world, double d, double d1, double d2, ItemStack itemstack, CallbackInfo ci){
		if (itemstack.getItem() == MoonSteel.fallenStar){
			viewScale = 5;
		}
	}
	@Inject(method = "tick()V", at = @At("TAIL"))
	private void tick(CallbackInfo ci){
		if (despawnInDay && world.isDaytime()){
			this.remove();
		}
	}

	@Override
	public void moonsteel$setDaylightSensitive(boolean flag) {
		this.despawnInDay = flag;
	}
	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void saveData(CompoundTag tag, CallbackInfo ci){
		tag.putBoolean("moonsteel$daydespawn", despawnInDay);
	}
	@Inject(method = "readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void loadData(CompoundTag tag, CallbackInfo ci){
		despawnInDay = tag.getBoolean("moonsteel$daydespawn");
		if (item.getItem() == MoonSteel.fallenStar){
			viewScale = 5;
		}
	}
}
