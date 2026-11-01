package cat.kiwicat.jukeboxify.mixins;

import cat.kiwicat.jukeboxify.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.JukeboxPlayableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxPlayableComponent.class)
public class JukeboxPlayableComponentMixin {
    @Inject(at = @At("HEAD"), method = "tryPlayStack", cancellable = true)
    private static void init(World world, BlockPos pos, ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemActionResult> cir) {
        JukeboxPlayableComponent jukeboxPlayableComponent = (JukeboxPlayableComponent)stack.get(DataComponentTypes.JUKEBOX_PLAYABLE);
        if (jukeboxPlayableComponent == null) {
            cir.setReturnValue(ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        } else {
            BlockState blockState = world.getBlockState(pos);
            if (true) {
                if (!world.isClient) {
                    ItemStack itemStack = stack.splitUnlessCreative(1, player);
                    BlockEntity var8 = world.getBlockEntity(pos);

                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));


                    player.incrementStat(Stats.PLAY_RECORD);
                }

                cir.setReturnValue(ItemActionResult.success(world.isClient));
            } else {
                cir.setReturnValue(ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
            }
        }
    }
}
