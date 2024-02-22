package useless.moonsteel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.BlockOreCoal;
import net.minecraft.core.block.BlockOreDiamond;
import net.minecraft.core.block.BlockOreGold;
import net.minecraft.core.block.BlockOreIron;
import net.minecraft.core.block.BlockOreLapis;
import net.minecraft.core.block.BlockOreNetherCoal;
import net.minecraft.core.block.BlockOreRedstone;
import net.minecraft.core.block.BlockTallGrass;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.crafting.LookupFuelFurnaceBlast;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.sound.SoundType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.ArrayList;
import java.util.List;


public class MoonSteel implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientStartEntrypoint {
	// TODO enderchest backpack integration
	// TODO moon grav with full armorset
	// TODO fortune on tools | done
	// TODO looting on sword | done
	// TODO fallen star torches
	// TODO Recipes | done
	// TODO fuels | done
    public static final String MOD_ID = "moonsteel";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int blockId = 2000;
	public static int itemId = 17000;
	public static Block moonSteelBlock = new BlockBuilder(MOD_ID)
		.setTextures("moonsteel_block.png")
		.setHardness(5f)
		.setResistance(2000f)
		.addTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("block.moonsteel", blockId++, Material.metal));

	public static ToolMaterial moonSteelTool = new ToolMaterial().setDurability(1536).setEfficiency(7.0f, 14.0f).setMiningLevel(3);
	public static Item ingotMoonSteel = ItemHelper.createItem(MOD_ID, new Item("ingot.moonsteel", itemId++), "moonsteel_ingot.png");
	public static Item rawMoonSteel = ItemHelper.createItem(MOD_ID, new Item("raw.moonsteel", itemId++), "moonsteel_ingot.png");
	public static Item toolAxeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.moonsteel", itemId++, moonSteelTool), "moonsteel_axe.png");
	public static Item toolPickaxeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.moonsteel", itemId++, moonSteelTool), "moonsteel_pickaxe.png");
	public static Item toolShovelMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.moonsteel", itemId++, moonSteelTool), "moonsteel_shovel.png");
	public static Item toolHoeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolHoe("tool.hoe.moonsteel", itemId++, moonSteelTool), "moonsteel_hoe.png");
	public static Item toolSwordMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.moonsteel", itemId++, moonSteelTool), "moonsteel_sword.png");
	public static ArmorMaterial moonSteelArmor = ArmorHelper.createArmorMaterial(MOD_ID, "moonsteel", 800, 45f, 45f, 45f, 100f);
	public static Item helmetMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("helmet.moonsteel", itemId++, moonSteelArmor, 0), "moonsteel_helmet.png");
	public static Item chestplateMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("chestplate.moonsteel", itemId++, moonSteelArmor, 1), "moonsteel_chestplate.png");
	public static Item leggingsMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("leggings.moonsteel", itemId++, moonSteelArmor, 2), "moonsteel_leggings.png");
	public static Item bootsMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("boots.moonsteel", itemId++, moonSteelArmor, 3), "moonsteel_boots.png");
	public static Item fallenStar = ItemHelper.createItem(MOD_ID, new Item("fallenstar", itemId++), "fallen_star.png").withTags(ItemTags.renderFullbright);
	public static Tag<Block> forceFortune = Tag.of("moonsteel$force_enable_fortune");
	public static Tag<Block> forceNoFortune = Tag.of("moonsteel$force_disable_fortune");
	public static boolean canBeFortuned(Block block){
		if (block.hasTag(forceFortune)) return true;
		if (block.hasTag(forceNoFortune)) return false;
		if (block instanceof BlockLeavesBase) return true;
		if (block instanceof BlockOreCoal) return true;
		if (block instanceof BlockOreDiamond) return true;
		if (block instanceof BlockOreGold) return true;
		if (block instanceof BlockOreIron) return true;
		if (block instanceof BlockOreLapis) return true;
		if (block instanceof BlockOreNetherCoal) return true;
		if (block instanceof BlockOreRedstone) return true;
		if (block instanceof BlockTallGrass) return true;
		return false;
	}
    @Override
    public void onInitialize() {
        LOGGER.info("MoonSteel initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {
		LookupFuelFurnace.instance.addFuelEntry(fallenStar.id, 9600);
		LookupFuelFurnaceBlast.instance.addFuelEntry(fallenStar.id, 9600);

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				" F ",
				"FSF",
				" F ")
			.addInput('F', fallenStar)
			.addInput('S', Item.ingotSteelCrude)
			.create("raw_moonsteel", rawMoonSteel.getDefaultStack());

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(rawMoonSteel)
			.create("moonsteel", ingotMoonSteel.getDefaultStack());

		RecipeBuilder.Trommel(MOD_ID)
			.setInput(fallenStar)
			.addEntry(new WeightedRandomLootObject(Item.coal.getDefaultStack(), 1, 3), 14)
			.addEntry(new WeightedRandomLootObject(Item.oreRawIron.getDefaultStack(), 1), 5)
			.addEntry(new WeightedRandomLootObject(Item.oreRawGold.getDefaultStack(), 1), 1)
			.create("fallenstar");
	}

	@Override
	public void beforeClientStart() {
		SoundHelper.Client.addSound(MOD_ID, "starspawn.wav");
	}

	@Override
	public void afterClientStart() {

	}
	public static void playSound(String soundPath, SoundType soundType, float x, float y, float z, float volume, float pitch){
		if (Global.isServer) return;
		Minecraft.getMinecraft(Minecraft.class).sndManager.playSound(soundPath, soundType, x, y, z, volume, pitch);
	}
}