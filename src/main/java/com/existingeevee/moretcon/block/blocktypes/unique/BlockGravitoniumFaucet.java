package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;
import com.existingeevee.moretcon.block.tile.TileGravitoniumFaucet;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.block.BlockFaucet;

@SuppressWarnings("deprecation")
public class BlockGravitoniumFaucet extends BlockFaucet implements ISimpleBlockItemProvider {

	public BlockGravitoniumFaucet() {
		super();
	    this.setCreativeTab(ModTabs.moarTConMisc);
	    this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blockgravitoniumfaucet"));
	}

	@Nonnull
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TileGravitoniumFaucet() ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.translateToLocal("tile.moretcon.blockgravitoniumfaucet.desc"));
	}
}
