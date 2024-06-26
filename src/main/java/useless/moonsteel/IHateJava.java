package useless.moonsteel;

import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import useless.moonsteel.item.ItemStarBackpack;

import static useless.moonsteel.MoonSteel.MOD_ID;

public class IHateJava {
	public static Item makeTheFuckingBackpack(){
		return new ItemBuilder(MOD_ID)
			.setIcon(MOD_ID + ":item/starpack")
			.setStackSize(1)
			.build(new ItemStarBackpack("backpack.cosmic", MoonSteel.itemId++));
	}
}
