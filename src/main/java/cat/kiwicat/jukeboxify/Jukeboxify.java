package cat.kiwicat.jukeboxify;

import cat.kiwicat.jukeboxify.blocks.ModBlocks;
import cat.kiwicat.jukeboxify.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.jukebox.JukeboxManager;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.component.type.JukeboxPlayableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SculkChargeParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public class Jukeboxify implements ModInitializer {
    public static String MOD_ID = "jukeboxify";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ArrayList<BlockPos> songs = new ArrayList<BlockPos>();

    private JukeboxManager manager = new JukeboxManager(this::onManagerChange, new BlockPos(0,0,0));

    public void onManagerChange() {
    }

    private void onRecordStackChanged(boolean hasRecord, World world, BlockPos pos, BlockState state) {
        if (world != null) {
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
    }

    private void stopSongs(World world, BlockState state){
        if(manager != null){
            //LOGGER.info("Stopping Songs!");
            manager.stopPlaying(world,state);
        }
    }

    @Override
    public void onInitialize() {
        ModBlocks.registerModBlocks();
        ModItems.RegisterModItems();


        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->{
            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemActionResult itemActionResult = JukeboxPlayableComponent.tryPlayStack(world, pos, stack, player);
            boolean bl = !stack.isEmpty();
            Optional<RegistryEntry<JukeboxSong>> optional = JukeboxSong.getSongEntryFromStack(world.getRegistryManager(), stack);
            this.onRecordStackChanged(bl, world, pos, state);
            if(itemActionResult.isAccepted()) {
                stack.decrement(1);
            }

            if(!world.isClient){
                //LOGGER.info("gay");
                ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                        pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5, 15, 0, 0, 0,1);
            }

            //stopSongs(world, state);
            this.manager = new JukeboxManager(this::onManagerChange, pos);

            if (bl && optional.isPresent()) {
                //this.manager.startPlaying(world, (RegistryEntry<JukeboxSong>)optional.get());
                int i = world.getRegistryManager().get(RegistryKeys.JUKEBOX_SONG).getRawId((JukeboxSong)optional.get().value());
                world.syncWorldEvent(null, WorldEvents.JUKEBOX_STARTS_PLAYING, pos, i);
                songs.add(pos);
            } else {
                this.manager.stopPlaying(world, state);
            }

            return (!itemActionResult.isAccepted() ? ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION : itemActionResult).toActionResult();
        });
    }
}
