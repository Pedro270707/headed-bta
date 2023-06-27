//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.pedroricardo.headed.block;

import net.minecraft.src.*;

import java.util.Random;

public class HeadedSkullBlock extends BlockContainer {
    private Class skullEntityClass;
    private boolean isOnFloor;

    public HeadedSkullBlock(int id, Class skullEntityClass, boolean isOnFloor) {
        super(id, Material.rock);
        this.isOnFloor = isOnFloor;
        this.skullEntityClass = skullEntityClass;
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return this.getSelectedBoundingBoxFromPool(world, i, j, k);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }

    public void setBlockBoundsBasedOnState(World world, int i, int j, int k) {
        if (!this.isOnFloor) {
            int l = world.getBlockMetadata(i, j, k);
            float four = 0.25F;
            float eight = 0.5F;
            float twelve = 0.75F;
            float sixteen = 1.0F;
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (l == 2) {
                this.setBlockBounds(four, four, eight, twelve, twelve, sixteen);
            }

            if (l == 3) {
                this.setBlockBounds(four, four, 0.0F, twelve, twelve, eight);
            }

            if (l == 4) {
                this.setBlockBounds(eight, four, four, sixteen, twelve, twelve);
            }

            if (l == 5) {
                this.setBlockBounds(0.0F, four, four, eight, twelve, twelve);
            }

        }
    }

    public int getRenderType() {
        return -1;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected TileEntity getBlockEntity() {
        try {
            return (TileEntity)this.skullEntityClass.newInstance();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public int idDropped(int i, Random random) {
        int type = 1;
        if (((HeadedSkullBlockEntity)this.getBlockEntity()).getSkullType().equals("skeleton")) {
            type = 2;
        } else if (((HeadedSkullBlockEntity)this.getBlockEntity()).getSkullType().equals("creeper")) {
            type = 3;
        }
        return 17287 + type;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        super.onNeighborBlockChange(world, i, j, k, l);
    }
}
