package useless.moonsteel;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import tosutosu.betterwithbackpacks.BetterWithBackpacks;

public class GuiStarBackpack extends GuiContainer {
	private int GUIx;
	private int GUIy;
	private int rows;
	private int slotsNum;
	private final ContainerStarBackpack backpack;
	public GuiStarBackpack(EntityPlayer player) {
		super(new ContainerStarBackpack(player));
		backpack = (ContainerStarBackpack) inventorySlots;
	}

	public void init() {
		this.GUIx = (this.width - this.xSize) / 2;
		this.GUIy = (this.height - this.ySize) / 2;
		this.slotsNum = this.backpack.backpackInventory.getSizeInventory();
		this.rows = (int)Math.ceil((double)this.slotsNum / 9.0);
		super.init();
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString(this.backpack.backpackInventory.getInvName(), 8, 6, BetterWithBackpacks.GUI_LABEL_COLOR);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, BetterWithBackpacks.GUI_LABEL_COLOR);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		GL11.glColor3d(1.0, 1.0, 1.0);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/assets/betterwithbackpacks/gui/backpack.png"));
		this.drawTexturedModalRect(this.GUIx, this.GUIy, 0, 0, this.xSize, this.ySize);

		for(int i = 0; i < this.rows; ++i) {
			if (i == this.rows - 1) {
				this.drawTexturedModalRect(this.GUIx + 7, this.GUIy + 17 + 18 * i, 0, 166, 18 * (this.slotsNum - 9 * i), 18);
			} else {
				this.drawTexturedModalRect(this.GUIx + 7, this.GUIy + 17 + 18 * i, 0, 166, 162, 18);
			}
		}
	}
}
