package cat.kiwicat.jukeboxify.item.custom;

import cat.kiwicat.jukeboxify.Jukeboxify;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class RemoteItem extends Item {
    public RemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Jukeboxify.songs.forEach((blockPos -> {
            world.syncWorldEvent(WorldEvents.JUKEBOX_STOPS_PLAYING, blockPos, 0);
        }));
        return super.use(world, user, hand);
    }
}
