package useless.moonsteel.mixin;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.moonsteel.MoonSteel;

import java.util.Random;

@Mixin(value = WorldFeatureLabyrinth.class, remap = false)
public class WorldFeatureLabyrinthMixin {
	@Shadow
	public WeightedRandomBag<WeightedRandomLootObject> chestLoot;

	@Inject(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/WeightedRandomBag;addEntry(Ljava/lang/Object;D)V", ordinal = 0))
	private void addLoot(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
		chestLoot.addEntry(new WeightedRandomLootObject(MoonSteel.crudeMoonSteel.getDefaultStack(), 1, 4), 5d);
	}
}
