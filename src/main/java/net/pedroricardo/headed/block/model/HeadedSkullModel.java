package net.pedroricardo.headed.block.model;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class HeadedSkullModel extends ModelBase {
    public ModelRenderer skull = new ModelRenderer(0, 0);
    public ModelRenderer skullLayer = new ModelRenderer(32, 0);
    public ModelRenderer leftEar = new ModelRenderer(24, 0);
    public ModelRenderer rightEar = new ModelRenderer(24, 0);

    public HeadedSkullModel() {
        this.skull.addBox(-4.0F, 4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.skullLayer.addBox(-4.0F, 4.0F, -4.0F, 8, 8, 8, 0.25F);
        this.leftEar.addBox(2.0F, 0.0F, -1.0F, 6, 6, 1, 0.0F);
        this.rightEar.addBox(-8.0F, 0.0F, -1.0F, 6, 6, 1, 0.0F);
    }

    public void render() {
        this.skull.render(0.0625F);
        this.skullLayer.render(0.0625F);
        this.leftEar.render(0.0625F);
        this.rightEar.render(0.0625F);
    }
}
