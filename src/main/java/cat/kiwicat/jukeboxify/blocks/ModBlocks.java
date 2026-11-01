package cat.kiwicat.jukeboxify.blocks;

import cat.kiwicat.jukeboxify.Jukeboxify;
import cat.kiwicat.jukeboxify.blocks.custom.TestJukeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

public class ModBlocks {

    public static final Block TEST_JUKE = registerBlock("test_juke",
            new TestJukeBlock(AbstractBlock.Settings.create()
                    .breakInstantly()
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)) {
            });









    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(Jukeboxify.MOD_ID, name), block);
    }



    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Jukeboxify.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(Jukeboxify.MOD_ID, name ),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        Jukeboxify.LOGGER.info("Registering Mod Blocks for " + Jukeboxify.MOD_ID);


    }
}
