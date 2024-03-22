package useless.moonsteel.mixin;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.MoonSteel;

import java.util.Random;

@Mixin(value = WorldFeatureLabyrinth.class, remap = false)
public class WorldFeatureLabyrinthMixin {
	@Inject(method = "pickCheckLootItem(Ljava/util/Random;)Lnet/minecraft/core/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"), cancellable = true)
	private void addLoot(Random random, CallbackInfoReturnable<ItemStack> cir){
		if (random.nextInt(16) == 0 && random.nextInt(10) == 0){
			cir.setReturnValue(new ItemStack(MoonSteel.crudeMoonSteel, random.nextInt(3) + 1));
		}
	}
}
