package com.existingeevee.moretcon.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.materials.UniqueMaterial;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModTabs {

	public static void init() {
		moarTConMaterials = (new CreativeTabs("moarTConMaterials") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.ingotFusionite, 1);
			}
			
		    @SideOnly(Side.CLIENT)
		    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		        for (Object item : Item.REGISTRY) {
		            ((Item) item).getSubItems(this, items);
		        }
		        items.sort(new Comparator<ItemStack>() {
					//                    5         4        3        2         1      0
 		        	String[] priority = {"block", "ore",  "gem", "ingot", "nugget", "dust"};		        	
		        	@Override
					public int compare(ItemStack o1, ItemStack o2) {
						List<String> one = new ArrayList<>(Arrays.stream(OreDictionary.getOreIDs(o1)).mapToObj(i -> OreDictionary.getOreName(i)).collect(Collectors.toList()));
						List<String> two = new ArrayList<>(Arrays.stream(OreDictionary.getOreIDs(o2)).mapToObj(i -> OreDictionary.getOreName(i)).collect(Collectors.toList()));
						int oneVal = Integer.MAX_VALUE;
						int twoVal = Integer.MAX_VALUE;
						for (final AtomicInteger i = new AtomicInteger(5); i.get() >= 0; i.decrementAndGet()) {
							if (one.removeIf(s -> s.startsWith(priority[i.get()]))) {
								oneVal = i.get();
								break;
							}
						}
						for (final AtomicInteger i = new AtomicInteger(5); i.get() >= 0; i.decrementAndGet()) {
							if (two.removeIf(s -> s.startsWith(priority[i.get()]))) {
								twoVal = i.get();
								break;
							}	
						}			
						
						return Integer.compare(oneVal, twoVal);
					}  	
		        });
		    }
		});

		moarTConMisc = (new CreativeTabs("moarTConMisc") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.matterReconstructionGel, 1);
			}
		});
		
		uniqueToolParts = (new CreativeTabs("uniqueToolParts") {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.ingotGravitonium, 1);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void displayAllRelevantItems(NonNullList<ItemStack> list) {
				for (Entry<String, BiValue<UniqueMaterial, String>> matObj : UniqueMaterial.uniqueMaterials.entrySet()) {
					if (matObj.getValue().getA() != null) {
						ItemStack toolpart = matObj.getValue().getA().getUniqueToolPart();
						if (toolpart != null && !toolpart.isEmpty()) {
							list.add(toolpart);
						}
					}
				}
			}
		});
	}

	public static CreativeTabs moarTConMaterials;
	public static CreativeTabs moarTConMisc;
	public static CreativeTabs uniqueToolParts;
}
