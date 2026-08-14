#loader contenttweaker

import mods.contenttweaker.tconstruct.MaterialBuilder;
import mods.moretcon.MoreTConCoT;
import mods.contenttweaker.VanillaFactory;

val mat = MaterialBuilder.create("test_boomer");
mat.localizedName = "Test Boomer";
mat.color = 0xAA3333;
mat.addExplosiveMaterialStats(7, 140);
mat.register();


val mat2 = MoreTConCoT.createUniqueMaterial("test_slicer", "tconstruct:sword_blade", "tconstruct:rapier");
mat2.localizedName = "Test Slicer";
mat2.color = 0xffff00;
mat2.addHeadMaterialStats(500, 7.0, 3.0, 2);
mat2.register();

MoreTConCoT.createCatalyst("cata_test");

VanillaFactory.createItem("zs_item").register();
