package net.pedroricardo.headed.mixin;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySkeleton.class)
public abstract class SkeletonSkullDropMixin extends EntityLiving {

    public SkeletonSkullDropMixin(World world) {
        super(world);
    }

    @Override
    public void onDeath(Entity entity) {
        if (entity instanceof EntityCreeper) {
            if (((EntityCreeper)entity).getPowered()) {
                entity.dropItem(Item.itemsList[Headed.IDs.SKELETON_SKULL].itemID, 1);
            }
        }
    }
}
