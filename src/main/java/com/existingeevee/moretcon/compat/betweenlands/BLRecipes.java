package com.existingeevee.moretcon.compat.betweenlands;

import org.apache.commons.lang3.tuple.Pair;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.inits.recipes.UniqueToolpartRecipes;
import com.existingeevee.moretcon.other.RecipeHelper;
import com.existingeevee.moretcon.other.ingredient.TinkerPartIngredient;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
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
import thebetweenlands.common.recipe.misc.AnimatorRecipe;
import thebetweenlands.common.registries.BlockRegistry;
import thebetweenlands.common.registries.ItemRegistry;

public class BLRecipes {

	public static void init(Register<IRecipe> event) {
		IBetweenlandsAPI blAPI = BetweenlandsAPI.getInstance();
		if (ConfigHandler.registerBetweenTinkerTools) {
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.axeHead, ModTools.betweenAxeHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.swordBlade, ModTools.betweenSwordBlade));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.shovelHead, ModTools.betweenShovelHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.pickHead, ModTools.betweenPickHead));
			blAPI.registerAnimatorRecipe(new BLPatternRecipe(TinkerTools.bowLimb, ModTools.betweenBowLimb));
		}

		blAPI.registerPestleAndMortarRecipe(new BLCrushedShockwaveRecipe());
		if (UniqueToolpartRecipes.canRegisterUniqueRecipe(ModMaterials.materialShockwave)) {
			blAPI.registerAnimatorRecipe(new BLShockwaveBladeRecipe());
			blAPI.registerAnimatorRecipe(new BLSwampSteelRecipe());
		}
		if (UniqueToolpartRecipes.canRegisterUniqueRecipe(ModMaterials.materialWormed)) {
			event.getRegistry().register(
					RecipeHelper.createRecipe("wormed_recipe", ModMaterials.materialWormed.getUniqueToolPart(),
							new String[] {
									"WWW",
									"WAW",
									"WWW"
							},
							Pair.of('W', Ingredient.fromStacks(new ItemStack(ItemRegistry.SLUDGE_WORM_ARROW))),
							Pair.of('A', new TinkerPartIngredient(ModMaterials.materialSwampSteel, "tconstruct:arrow_head"))));
		}

		GameRegistry.addSmelting(new ItemStack(ModItems.sulfurBucketSyrmorite, (int) (1)), new ItemStack(BLItems.blFilledMoltenSulfur, 1, 1), 0f);
		GameRegistry.addSmelting(new ItemStack(ModItems.sulfurBucketIron, (int) (1)), FluidUtil.getFilledBucket(new FluidStack(ModFluids.liquidBurningSulfurFlow, 1000)), 0F);
		TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidRotiron, 1), new FluidStack(ModFluids.liquidRottenSludge, 1), new FluidStack(ModFluids.liquidSyrmorite, 3));
    	TinkerRegistry.registerEntityMelting(EntityDreadfulMummy.class, new FluidStack(ModFluids.liquidMummySludge, 16));
		TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidBetweenSludge, 1), new FluidStack(ModFluids.liquidRottenSludge, 1), new FluidStack(ModFluids.liquidMummySludge, 275));
		TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(ModItems.betweenifiedModifier, 1), RecipeMatch.of(new ItemStack(ModItems.betweenicCore)), ModFluids.liquidBetweenSludge, 16, true, true));

		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_BATTLE_AXE, 1), new ItemStack(ModItems.itemAncientSlag, 2), 0f);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_GREATSWORD, 1), new ItemStack(ModItems.itemAncientSlag, 2), 0f);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_HELMET, 1), new ItemStack(ModItems.itemAncientSlag, 3), 0f);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_CHESTPLATE, 1), new ItemStack(ModItems.itemAncientSlag, 4), 0f);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_LEGGINGS, 1), new ItemStack(ModItems.itemAncientSlag, 4), 0f);
		GameRegistry.addSmelting(new ItemStack(ItemRegistry.ANCIENT_BOOTS, 1), new ItemStack(ModItems.itemAncientSlag, 3), 0f);

		if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
			GameRegistry.addSmelting(new ItemStack(ModItems.dustSwampSteel, 1), new ItemStack(ModItems.ingotSwampSteel, 1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModItems.dustOctine, 1), new ItemStack(ItemRegistry.OCTINE_INGOT, 1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModItems.dustSyrmorite, 1), EnumItemMisc.SYRMORITE_INGOT.create(1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModItems.dustRotiron, 1), new ItemStack(ModItems.ingotRotiron, 1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModItems.dustAncientAlloy, 1), new ItemStack(ModItems.ingotAncientAlloy, 1), 0F);
		}
		if (ConfigHandler.unfracturedBedrockObtainable) {
			GameRegistry.addSmelting(new ItemStack(ModBlocks.blockCobbledBetweenBedrock, 1), new ItemStack(BlockRegistry.BETWEENLANDS_BEDROCK, 1), 0F);
		}
		blAPI.registerAnimatorRecipe(new AnimatorRecipe(new ItemStack(BlockRegistry.ANCIENT_REMNANT_BLOCK), 10, 30, new ItemStack(ModItems.itemAncientScrap)));
		ForgeRegistries.RECIPES.register(new ShapelessRecipes(ModInfo.MODID, new ItemStack(ModItems.itemAncientScrap, 3), 
				NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), 
						Ingredient.fromStacks(new ItemStack(ModItems.itemAncientSlag, 1)), 
						Ingredient.fromStacks(EnumItemMisc.ANCIENT_REMNANT.create(1)), 
						Ingredient.fromStacks(EnumItemMisc.ANCIENT_REMNANT.create(1))
						)).setRegistryName("slag_to_scrap"));
		TinkerRegistry.registerMelting(new ItemStack(ModItems.itemAncientScrap), ModFluids.liquidAncientAlloy, Material.VALUE_Ingot / 4);

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
		
		blAPI.registerPestleAndMortarRecipe(new BLCragravelRecipe());
	}
}
