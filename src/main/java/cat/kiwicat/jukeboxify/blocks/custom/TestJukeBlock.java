package cat.kiwicat.jukeboxify.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.block.jukebox.JukeboxManager;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.component.type.JukeboxPlayableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public class TestJukeBlock extends Block {
    public TestJukeBlock(Settings settings) {
        super(settings);
    }

    private JukeboxManager manager = new JukeboxManager(this::onManagerChange, new BlockPos(0,0,0));

    public void onManagerChange() {

    }


    private void onRecordStackChanged(boolean hasRecord, World world, BlockPos pos, BlockState state) {
        if (world != null) {
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
    }
    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack itemStack = player.getStackInHand(hand);
        ItemActionResult itemActionResult = JukeboxPlayableComponent.tryPlayStack(world, pos, itemStack, player);
        boolean bl = !stack.isEmpty();
        Optional<RegistryEntry<JukeboxSong>> optional = JukeboxSong.getSongEntryFromStack(world.getRegistryManager(), stack);
        this.onRecordStackChanged(bl, world, pos, state);
        this.manager = new JukeboxManager(this::onManagerChange, pos);
        if (bl && optional.isPresent()) {
            this.manager.startPlaying(world, (RegistryEntry<JukeboxSong>)optional.get());
        } else {
            this.manager.stopPlaying(world, state);
        }

        return !itemActionResult.isAccepted() ? ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION : itemActionResult;

    }
    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            manager.stopPlaying(world, state);

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
