package com.kelmai.luma.blocks;

import com.kelmai.luma.BlockManager;
import com.kelmai.luma.Luma;
import com.kelmai.luma.client.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: KelMai
 * Date: 08.08.13
 * Time: 21:42
 */
public class BlockLumaLamp extends Block {

    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    private boolean powered;
    private boolean bars;
    private boolean inverted;

    public BlockLumaLamp(int id, boolean powered, boolean bars, boolean inverted) {
        super(id, Material.redstoneLight);

        this.powered = powered;
        this.bars = bars;
        this.inverted = inverted;

        if (this.bars) {
            this.setHardness(3f);
            this.setResistance(3f);
        } else {
            this.setHardness(.5f);
            this.setResistance(.5f);
        }

        this.setStepSound(Block.soundGlassFootstep);


        if (this.powered) {
            if (!this.inverted) {
                this.setLightValue(1.0F);
            }
        } else {
            if (this.inverted) {
                this.setLightValue(1.0F);
            }
        }
    }


    public int damageDropped (int metadata) {
        return metadata;
    }


    public boolean renderAsNormalBlock(){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType(){
        if ((this.inverted && !this.powered) || (!this.inverted && this.powered)  ) {
            return ClientProxy.lampRenderType;
        } else {
            return 0;
        }
    }

    public boolean isOpaqueCube() {
        return true;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote) {
            if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            } else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                int blockID;
                if (!this.inverted) {
                    blockID = this.bars ? BlockManager.blockLumaLampBarOn.blockID : BlockManager.blockLumaLampOn.blockID;
                } else {
                    blockID = this.bars ? BlockManager.blockLumaLampBarOffInv.blockID : BlockManager.blockLumaLampOffInv.blockID;
                }
                par1World.setBlock(par2, par3, par4, blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        if (!par1World.isRemote) {
            if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            } else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                int blockID;
                if (this.inverted) {
                    blockID = this.bars ? BlockManager.blockLumaLampBarOffInv.blockID : BlockManager.blockLumaLampOffInv.blockID;
                } else {
                    blockID = this.bars ? BlockManager.blockLumaLampBarOn.blockID : BlockManager.blockLumaLampOn.blockID;
                }
                par1World.setBlock(par2, par3, par4, blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
            }
        }

    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote && this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
            int blockID;
            if (this.inverted) {
                blockID = this.bars ? BlockManager.blockLumaLampBarOnInv.blockID : BlockManager.blockLumaLampOnInv.blockID;
            } else {
                blockID = this.bars ? BlockManager.blockLumaLampBarOff.blockID : BlockManager.blockLumaLampOff.blockID;
            }
            par1World.setBlock(par2, par3, par4, blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3) {
        int blockID;
        if (this.inverted) {
            blockID = this.bars ? BlockManager.blockLumaLampBarOnInv.blockID : BlockManager.blockLumaLampOnInv.blockID;
        } else {
            blockID = this.bars ? BlockManager.blockLumaLampBarOff.blockID : BlockManager.blockLumaLampOff.blockID;
        }
        return blockID;
    }


    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[16];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            String barsString = this.bars ? "Bars" : "";
            String textureString;
            if (this.inverted) {
                textureString = this.powered ? ":blockLumaLamp"+barsString+"Off_" : ":blockLumaLamp"+barsString+"On_";
            } else {
                textureString = this.powered ? ":blockLumaLamp"+barsString+"On_" : ":blockLumaLamp"+barsString+"Off_";
            }
            this.iconArray[i] = par1IconRegister.registerIcon(Luma.modID + textureString + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata) {
        return this.iconArray[metadata];
    }



    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
        for (int ix = 0; ix < 16; ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

}
