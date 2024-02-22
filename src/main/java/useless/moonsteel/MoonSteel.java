package useless.moonsteel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MoonSteel implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
	// TODO enderchest backpack integration
	// TODO moon grav with full armorset
	// TODO fortune on tools
	// TODO looting on sword
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
	public static Item toolAxeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.moonsteel", itemId++, moonSteelTool), "moonsteel_axe.png");
	public static Item toolPickaxeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.moonsteel", itemId++, moonSteelTool), "moonsteel_pickaxe.png");
	public static Item toolShovelMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.moonsteel", itemId++, moonSteelTool), "moonsteel_shovel.png");
	public static Item toolHoeMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolHoe("tool.hoe.moonsteel", itemId++, moonSteelTool), "moonsteel_hoe.png");
	public static Item toolSwordMoonSteel = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.moonsteel", itemId++, moonSteelTool), "moonsteel_sword.png");
	public static ArmorMaterial moonSteelArmor = ArmorHelper.createArmorMaterial(MOD_ID, "moonsteel", 800, 45f, 45f, 65f, 45f);
	public static Item helmetMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("helmet.moonsteel", itemId++, moonSteelArmor, 0), "moonsteel_helmet.png");
	public static Item chestplateMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("chestplate.moonsteel", itemId++, moonSteelArmor, 1), "moonsteel_chestplate.png");
	public static Item leggingsMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("leggins.moonsteel", itemId++, moonSteelArmor, 2), "moonsteel_leggins.png");
	public static Item bootsMoonSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("boots.moonsteel", itemId++, moonSteelArmor, 3), "moonsteel_boots.png");
	public static Item fallenStar = ItemHelper.createItem(MOD_ID, new Item("fallenstar", itemId++), "fallen_star.png");
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

	}
}
