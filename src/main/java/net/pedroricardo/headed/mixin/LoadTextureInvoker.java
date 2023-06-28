package net.pedroricardo.headed.mixin;

import net.minecraft.src.Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Render.class, remap = false)
public interface LoadTextureInvoker {
    @Invoker("loadTexture")
    public void setLoadTexture(String s);
}
