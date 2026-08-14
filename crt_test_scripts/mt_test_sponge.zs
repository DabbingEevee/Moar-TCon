#loader contenttweaker

import mods.moretcon.sponge.SpongeStep;
import mods.moretcon.MoreTConCoT;

import crafttweaker.item.IItemStack;
import crafttweaker.item.IIngredient;

MoreTConCoT.addGravitoniumSpongeAlloy("test", <ore:oreIron>, "oreGold", <item:minecraft:gold_ingot>, [
	SpongeStep.create("iron", 144),
	SpongeStep.create("ardite", 144)
]);
