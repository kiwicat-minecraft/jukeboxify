package cat.kiwicat.jukeboxify.item;


import cat.kiwicat.jukeboxify.Jukeboxify;
import cat.kiwicat.jukeboxify.item.custom.RemoteItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static RemoteItem REMOTE = registerItem("remote",
            new RemoteItem(new Item.Settings()
                    .maxCount(1)));


    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(Jukeboxify.MOD_ID, name), item);
    }

    private static void addItemsToToolsItemGroup(FabricItemGroupEntries entries) {
        entries.add(REMOTE);
    }

    public static void RegisterModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsItemGroup);

    }





}

