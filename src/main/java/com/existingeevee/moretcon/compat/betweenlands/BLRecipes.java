package com.existingeevee.moretcon.compat.betweenlands;

import java.util.Arrays;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.inits.ModTools;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.tools.TinkerTools;
import thebetweenlands.api.IBetweenlandsAPI;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.entity.mobs.EntityDreadfulMummy;
import thebetweenlands.common.item.misc.ItemMisc.EnumItemMisc;
import thebetweenlands.common.registries.BlockRegistry;
import thebetweenlands.common.registries.ItemRegistry;

public class BLRecipes {

	public static void init() {
		IBetweenlandsAPI blAPI = BetweenlandsAPI.getInstance();
		if (ConfigHandler.registerBetweenTinkerTools) {
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.axeHead, ModTools.betweenAxeHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.swordBlade, ModTools.betweenSwordBlade));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.shovelHead, ModTools.betweenShovelHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.pickHead, ModTools.betweenPickHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.bowLimb, ModTools.betweenBowLimb));
		}

		blAPI.registerPestleAndMortarRecipe(new BLCrushedShockwaveRecipe());
		if (ModMaterials.materialShockwave != null && ModMaterials.materialShockwave.getUniqueToolPart() != null && !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes).contains(ModMaterials.materialShockwave.getIdentifier())) {
			blAPI.registerAnimatorRecipe(new BLShockwaveBladeRecipe());
			blAPI.registerAnimatorRecipe(new BLSwampSteelRecipe());
		}
		GameRegistry.addSmelting(new ItemStack(ModItems.sulfurBucketSyrmorite, (int) (1)), new ItemStack(BLItems.blFilledMoltenSulfur, 1, 1), 0f);
		GameRegistry.addSmelting(new ItemStack(ModItems.sulfurBucketIron, (int) (1)), FluidUtil.getFilledBucket(new FluidStack(ModFluids.liquidBurningSulfurFlow, 1000)), 0F);
		TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidRotiron, 1), new FluidStack(ModFluids.liquidRottenSludge, 1), new FluidStack(ModFluids.liquidSyrmorite, 3));
    	TinkerRegistry.registerEntityMelting(EntityDreadfulMummy.class, new FluidStack(ModFluids.liquidMummySludge, 16));
		TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidBetweenSludge, 1), new FluidStack(ModFluids.liquidRottenSludge, 1), new FluidStack(ModFluids.liquidMummySludge, 275));
		TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(ModItems.betweenifiedModifier, 1), RecipeMatch.of(new ItemStack(ModItems.betweenicCore)), ModFluids.liquidBetweenSludge, 16, true, true));

    	
		ForgeRegistries.RECIPES.register(new ShapelessRecipes(ModInfo.MODID, new ItemStack(ModItems.sulfurBucketSyrmorite, 1), NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), Ingredient.fromStacks(new ItemStack(ItemRegistry.BL_BUCKET, 1, 1)), Ingredient.fromStacks(new ItemStack(BlockRegistry.SULFUR_BLOCK, 1)))) {
			@Override
		    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		        boolean foundBucket = false;
		        for (int i = 0; i < nonnulllist.size(); ++i) {
		            ItemStack itemstack = inv.getStackInSlot(i);
		            if (!foundBucket && itemstack != null && !itemstack.isEmpty() && itemstack.getItem().equals(ItemRegistry.BL_BUCKET) && itemstack.getMetadata() == 1) {
			            nonnulllist.set(i, ItemStack.EMPTY);
			            foundBucket = true;
			            continue;
		            }
		            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
		        }
		        return nonnulllist;
		    }
		    @Override
			public boolean matches(InventoryCrafting inv, World worldIn) {
		        for (int i = 0; i < inv.getHeight(); ++i) {
		            for (int j = 0; j < inv.getWidth(); ++j) {
		                ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
		                if (!itemstack.isEmpty()) {
				            if (itemstack != null && !itemstack.isEmpty() && itemstack.getItem().equals(ItemRegistry.BL_BUCKET) && itemstack.getMetadata() == 1) {
				            	try {
				            		FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemstack.serializeNBT().getCompoundTag("tag").getCompoundTag("Fluid"));
					            	if (fluidStack == null) continue;
					            	return false;
				            	} catch (ClassCastException | NullPointerException e) {
				            		continue;
				            	}
				            }
		                }
		            }
		        }
		        return super.matches(inv, worldIn);
		    }
		}.setRegistryName("syrmoritesulfur"));
		TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(ItemRegistry.ROTTEN_FOOD, Material.VALUE_Ingot / 2), ModFluids.liquidRottenSludge));

		TinkerRegistry.registerMelting(new MeltingRecipe(new RecipeMatch.ItemCombination(Material.VALUE_Ingot / 8, EnumItemMisc.UNDYING_EMBER.create(1)), ModFluids.liquidEmber, 0));
		
		if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
			GameRegistry.addSmelting(new ItemStack(ModItems.dustOctine, 1), new ItemStack(ItemRegistry.OCTINE_INGOT, 1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModItems.dustSyrmorite, 1), EnumItemMisc.SYRMORITE_INGOT.create(1), 0F);
		}
		blAPI.registerPestleAndMortarRecipe(new BLCragravelRecipe());
	}
}
