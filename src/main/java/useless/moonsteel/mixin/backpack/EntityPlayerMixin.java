package useless.moonsteel.mixin.backpack;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import useless.moonsteel.StarBackpackInventory;
import useless.moonsteel.interfaces.IStarBackpack;
@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IStarBackpack {
	@Unique
	public StarBackpackInventory starBackpackInventory;
	@Inject(method = "<init>(Lnet/minecraft/core/world/World;)V", at = @At("TAIL"))
	private void createBackpack(World world, CallbackInfo ci){
		this.starBackpackInventory = new StarBackpackInventory((EntityPlayer) (Object)this);
	}
	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void addData(CompoundTag tag, CallbackInfo ci){
		tag.put("moonsteel$InventoryStardust", this.starBackpackInventory.writeToNBT(new ListTag()));
	}
	@Inject(method = "readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void loadData(CompoundTag tag, CallbackInfo ci){
		this.starBackpackInventory.readFromNBT(tag.getList("moonsteel$InventoryStardust"));
	}
	@Override
	public StarBackpackInventory moonsteel$getStarBackpackInventory() {
		return starBackpackInventory;
	}

	@Override
	public void moonsteel$setStarBackpackInventory(StarBackpackInventory backpackInventory) {
		starBackpackInventory = backpackInventory;
	}

	@Override
	public void moonsteel$displayGuiStarBackpack() {

	}
}
