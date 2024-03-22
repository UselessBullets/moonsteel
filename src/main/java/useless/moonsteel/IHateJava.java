package useless.moonsteel;

import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemHelper;
import useless.moonsteel.item.ItemStarBackpack;

import static useless.moonsteel.MoonSteel.MOD_ID;

public class IHateJava {
	public static Item makeTheFuckingBackpack(){
		return ItemHelper.createItem(MOD_ID, new ItemStarBackpack("backpack.cosmic", MoonSteel.itemId++), "starpack.png").setMaxStackSize(1);
	}
}
