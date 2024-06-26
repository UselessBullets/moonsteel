package useless.moonsteel.item;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import useless.moonsteel.MoonSteel;

public class ItemModelConnectStar extends ItemModelStandard {
	private static final IconCoordinate star_connected = TextureRegistry.getTexture(MoonSteel.MOD_ID + ":item/connected_star");
	public ItemModelConnectStar(Item item, String namespace) {
		super(item, namespace);
	}
	@NotNull
	@Override
	public IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		if (itemStack.getData().getBoolean("moonsteel$has_location")){
			return star_connected;
		}
		return super.getIcon(entity, itemStack);
	}
}
