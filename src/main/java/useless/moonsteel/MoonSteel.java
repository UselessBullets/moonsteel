package useless.moonsteel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.render.block.model.BlockModelTorch;
import net.minecraft.client.render.item.model.ItemModelStandard;
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
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tosutosu.betterwithbackpacks.ModItems;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.CreativeHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import useless.moonsteel.block.BlockModelStellarRewinder;
import useless.moonsteel.block.BlockStellarRewinder;
import useless.moonsteel.block.BlockTorchStar;
import useless.moonsteel.block.TileEntityStellarRewinder;
import useless.moonsteel.item.ItemConnectedStar;
import useless.moonsteel.item.ItemModelConnectStar;

import java.util.Properties;


public class MoonSteel implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "moonsteel";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean backpackPresent = ModVersionHelper.isModPresent("betterwithbackpacks");
	public static int blockId;
	public static int itemId;
	public static int GUI_ID;
	public static int FORTUNE_AMOUNT;
	public static int LOOTING_AMOUNT;
	public static int STAR_SPAWN_RATE;
	static {
		Properties prop = new Properties();
		prop.setProperty("starting_block_id","6700");
		prop.setProperty("starting_item_id","21400");
		prop.setProperty("gui_backpack_id","20");
		prop.setProperty("fortune_amount","3");
		prop.setProperty("looting_amount","3");
		prop.setProperty("star_spawn_rate","7500");
		ConfigHandler config = new ConfigHandler(MOD_ID,prop);

		blockId = config.getInt("starting_block_id");
		itemId = config.getInt("starting_item_id");
		GUI_ID = config.getInt("gui_backpack_id");
		FORTUNE_AMOUNT = config.getInt("fortune_amount");
		LOOTING_AMOUNT = config.getInt("looting_amount");
		STAR_SPAWN_RATE=config.getInt("star_spawn_rate");

		config.updateConfig();
	}
	public static Block moonSteelBlock = new BlockBuilder(MOD_ID)
		.setSideTextures(MOD_ID + ":block/moonsteel_block_side")
		.setTopTexture(MOD_ID + ":block/moonsteel_block_top")
		.setBottomTexture(MOD_ID + ":block/moonsteel_block_bottom")
		.setHardness(5f)
		.setResistance(2000f)
		.addTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("block.moonsteel", blockId++, Material.metal));
	public static Block torchStar = new BlockBuilder(MOD_ID)
		.setTextures(MOD_ID + ":block/startorch")
		.setBlockModel(BlockModelTorch::new)
		.setLuminance(15)
		.build(new BlockTorchStar("torch.star", blockId++))
		.withDisabledNeighborNotifyOnMetadataChange();
	public static Block stellarRewinder = new BlockBuilder(MOD_ID)
		.setHardness(3.5f)
		.setSideTextures(MOD_ID + ":block/stellarrewinder_side")
		.setNorthTexture(MOD_ID + ":block/stellarrewinder_front_active")
		.setNorthTexture(MOD_ID + ":block/stellarrewinder_front")
		.setBlockModel(BlockModelStellarRewinder::new)
		.setTopBottomTextures(MOD_ID + ":block/stellarrewinder_top")
		.addTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new BlockStellarRewinder("stellar.rewinder", blockId++, Material.metal))
		.withImmovableFlagSet();

	public static ToolMaterial moonSteelTool = new ToolMaterial().setDurability(1536).setEfficiency(7.0f, 14.0f).setMiningLevel(3).setDamage(2);
	public static Item ingotMoonSteel = new ItemBuilder(MOD_ID)
	.setIcon(MOD_ID + ":item/moonsteel_ingot")
	.build(new Item("ingot.moonsteel", itemId++));
	public static Item crudeMoonSteel = new ItemBuilder(MOD_ID)
	.setIcon(MOD_ID + ":item/moonsteel_crude")
	.build(new Item("crude.moonsteel", itemId++));
	public static Item toolPickaxeMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_pickaxe")
		.setItemModel(item -> new ItemModelStandard(item, null).setFull3D())
		.build(new ItemToolPickaxe("tool.pickaxe.moonsteel", itemId++, moonSteelTool));
	public static Item toolAxeMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_axe")
		.setItemModel(item -> new ItemModelStandard(item, null).setFull3D())
		.build(new ItemToolAxe("tool.axe.moonsteel", itemId++, moonSteelTool));
	public static Item toolShovelMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_shovel")
		.setItemModel(item -> new ItemModelStandard(item, null).setFull3D())
		.build(new ItemToolShovel("tool.shovel.moonsteel", itemId++, moonSteelTool));
	public static Item toolHoeMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_hoe")
		.setItemModel(item -> new ItemModelStandard(item, null).setFull3D())
		.build(new ItemToolHoe("tool.hoe.moonsteel", itemId++, moonSteelTool));
	public static Item toolSwordMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_sword")
		.setItemModel(item -> new ItemModelStandard(item, null).setFull3D())
		.build(new ItemToolSword("tool.sword.moonsteel", itemId++, moonSteelTool));
	public static ArmorMaterial moonSteelArmor = ArmorHelper.createArmorMaterial(MOD_ID, "moonsteel", 800, 51f, 45f, 45f, 100f);
	public static Item helmetMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_helmet")
		.build(new ItemArmor("helmet.moonsteel", itemId++, moonSteelArmor, 0));
	public static Item chestplateMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_chestplate")
		.build(new ItemArmor("chestplate.moonsteel", itemId++, moonSteelArmor, 1));
	public static Item leggingsMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_leggings")
		.build(new ItemArmor("leggings.moonsteel", itemId++, moonSteelArmor, 2));
	public static Item bootsMoonSteel = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/moonsteel_boots")
		.build(new ItemArmor("boots.moonsteel", itemId++, moonSteelArmor, 3));
	public static Item fallenStar = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/fallen_star")
		.setItemModel((i) -> new ItemModelStandard(i, null).setFullBright())
		.build(new Item("star.fallen", itemId++));
	public static Item connectedStar = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/connected_star_off")
		.setItemModel((i) -> new ItemModelConnectStar(i, null).setFullBright())
		.setStackSize(1)
		.build(new ItemConnectedStar("star.connected", itemId++));
	public static Item cosmicBackpack;
	public static Tag<Block> FORCE_FORTUNE = Tag.of("moonsteel$force_enable_fortune");
	public static Tag<Block> FORCE_NO_FORTUNE = Tag.of("moonsteel$force_disable_fortune");
	public static boolean canBeFortuned(Block block){
		if (block.hasTag(FORCE_FORTUNE)) return true;
		if (block.hasTag(FORCE_NO_FORTUNE)) return false;
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

	public static ItemStack starZombieSword = toolSwordMoonSteel.getDefaultStack();
	public static boolean forceChunkLoads = false;
    @Override
    public void onInitialize() {
		LOGGER.info("Backpacks present: " + backpackPresent);
		if (backpackPresent){
			cosmicBackpack = IHateJava.makeTheFuckingBackpack();
		} else {
			cosmicBackpack = new ItemBuilder(MOD_ID)
				.setIcon(MOD_ID + ":block/starpack")
				.setStackSize(1)
				.setTags(ItemTags.NOT_IN_CREATIVE_MENU)
				.build(new Item("backpack.cosmic.missing", itemId++));
		}
        LOGGER.info("MoonSteel initialized.");
    }

	@Override
	public void beforeGameStart() {
		EntityHelper.createTileEntity(TileEntityStellarRewinder.class, "moonsteel$stellar_rewinder");
		if (backpackPresent){
			CreativeHelper.setParent(cosmicBackpack.getDefaultStack(), ModItems.DiamondBackpack.getDefaultStack());
		}
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
			.create("raw_moonsteel", crudeMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M",
				"M",
				"S")
			.addInput('M', ingotMoonSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_sword", toolSwordMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				" S ",
				" S ")
			.addInput('M', ingotMoonSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_pickaxe", toolPickaxeMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MM",
				"MS",
				" S")
			.addInput('M', ingotMoonSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_axe", toolAxeMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M",
				"S",
				"S")
			.addInput('M', ingotMoonSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_shovel", toolShovelMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MM",
				" S",
				" S")
			.addInput('M', ingotMoonSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_hoe", toolHoeMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				"M M",
				"   ")
			.addInput('M', ingotMoonSteel)
			.create("moonsteel_helmet", helmetMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M M",
				"MMM",
				"MMM")
			.addInput('M', ingotMoonSteel)
			.create("moonsteel_chestplate", chestplateMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				"M M",
				"M M")
			.addInput('M', ingotMoonSteel)
			.create("moonsteel_leggings", leggingsMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M M",
				"M M",
				"   ")
			.addInput('M', ingotMoonSteel)
			.create("moonsteel_boots", bootsMoonSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape(
					"MMM",
					"MMM",
					"MMM")
			.addInput('M', ingotMoonSteel)
			.create("block_of_moonsteel", moonSteelBlock.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"F",
				"S")
			.addInput('F', fallenStar)
			.addInput('S', Item.stick)
			.create("stardust_torches", new ItemStack(torchStar, 8));

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"DLD",
				"LFL",
				"DLD")
			.addInput('F', fallenStar)
			.addInput('L', Item.dye, 4)
			.addInput('D', Item.diamond)
			.create("connected_star", connectedStar.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"SSS",
				"ICI",
				"SSS")
			.addInput('S', Item.ingotSteel)
			.addInput('I', Item.ingotIron)
			.addInput('C', connectedStar)
			.create("stellar_rewinder", stellarRewinder.getDefaultStack());

		if (backpackPresent){
			RecipeBuilder.Shaped(MOD_ID)
				.setShape(
					"MMM",
					"MSM",
					"MMM")
				.addInput('M', ingotMoonSteel)
				.addInput('S', ModItems.GoldBackpack)
				.create("cosmic_backpack", cosmicBackpack.getDefaultStack());
		}

		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(crudeMoonSteel)
			.create("moonsteel", ingotMoonSteel.getDefaultStack());

		RecipeBuilder.Trommel(MOD_ID)
			.setInput(fallenStar)
			.addEntry(new WeightedRandomLootObject(Item.coal.getDefaultStack(), 1, 3), 14)
			.addEntry(new WeightedRandomLootObject(Item.oreRawIron.getDefaultStack(), 1), 5)
			.addEntry(new WeightedRandomLootObject(Item.oreRawGold.getDefaultStack(), 1), 1)
			.create("fallenstar");
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	@Override
	public void beforeClientStart() {
		SoundHelper.addSound(MOD_ID, "starspawn.wav");
	}

	@Override
	public void afterClientStart() {

	}
	public static void playSound(String soundPath, SoundCategory soundType, float x, float y, float z, float volume, float pitch){
		if (Global.isServer) return;
		Minecraft.getMinecraft(Minecraft.class).sndManager.playSound(soundPath, soundType, x, y, z, volume, pitch);
	}
	public static boolean isStarTime(World world){
		if (world.worldType.hasCeiling()) return false;
		if (world.isDaytime()) return false;
		if (world.getWorldTime() % 2000 > 200) return false;
		return true;
	}
	public static void teleport(double x, double y, double z, EntityPlayer player){
		if (player instanceof EntityPlayerMP){
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			playerMP.playerNetServerHandler.teleport(x, y, z);
		} else if (player instanceof EntityPlayerSP) {
			EntityPlayerSP playerSP = (EntityPlayerSP)player;
			playerSP.setPos(x, y + playerSP.bbHeight, z);
		}
	}
}
