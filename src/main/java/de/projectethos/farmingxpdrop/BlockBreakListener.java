package de.projectethos.farmingxpdrop;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {

    final Random rand = new Random();

    final List<Material> farmerBlocks = Arrays.asList(Material.MELON, Material.PUMPKIN, Material.SUGAR_CANE);
    final List<Material> logs = Arrays.asList(Material.OAK_LOG, Material.DARK_OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.ACACIA_LOG, Material.MANGROVE_LOG, Material.JUNGLE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM);
    final List<Material> leafs = Arrays.asList(Material.OAK_LEAVES, Material.DARK_OAK_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES, Material.ACACIA_LEAVES, Material.MANGROVE_LEAVES, Material.JUNGLE_LEAVES);
    final List<Material> lowArcheologyResources = Arrays.asList(Material.DIRT, Material.GRASS_BLOCK, Material.SAND, Material.GRAVEL, Material.MYCELIUM, Material.RED_SAND, Material.ROOTED_DIRT);
    final List<Material> highArcheologyResources = Arrays.asList(Material.CLAY, Material.SOUL_SAND, Material.COARSE_DIRT, Material.PODZOL, Material.SOUL_SOIL, Material.MUD, Material.MUDDY_MANGROVE_ROOTS);

    @EventHandler
    public void onFarmerResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        BlockData blockData = block.getBlockData();
        Material material = blockData.getMaterial();

        if (isFarmerBlock(material)) {
            if (isPlayerPlaced(block)) return;
            if (rand.nextInt(19) == 0) dropXP(block, rand.nextInt(2) + 1);
            return;
        }

        if (material.equals(Material.WHEAT) || material.equals(Material.CARROT) || material.equals(Material.POTATO)) {
            if (((Ageable) blockData).getAge() == 7 && rand.nextInt(29) == 0) dropXP(block, rand.nextInt(2) + 1);
            return;
        }

        if (material.equals(Material.BEETROOTS)) {
            if (((Ageable) blockData).getAge() == 3 && rand.nextInt(29) == 0) dropXP(block, rand.nextInt(2) + 1);
        }
    }

    @EventHandler
    public void onLogBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLog(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(29) == 0) dropXP(block, rand.nextInt(5) + 1 );
    }

    @EventHandler
    public void onLeafBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLeaf(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(29) == 0) dropXP(block, rand.nextInt(2) + 1 );
    }

    @EventHandler
    public void onLowArcheologyResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLowArcheologyResource(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(49) == 0) dropXP(block, rand.nextInt(2) + 1 );
    }

    @EventHandler
    public void onHighArcheologyResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isHighArcheologyResource(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(49) == 0) dropXP(block, rand.nextInt(4) + 1 );
    }

    private boolean isFarmerBlock(Material material) {
        for (Material farmerBlock : farmerBlocks) {
            if (material.equals(farmerBlock)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLog(Material material) {
        for (Material log : logs) {
            if (material.equals(log)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLeaf(Material material) {
        for (Material leaf : leafs) {
            if (material.equals(leaf)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLowArcheologyResource(Material material) {
        for (Material lowArcheologyResource : lowArcheologyResources) {
            if (material.equals(lowArcheologyResource)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHighArcheologyResource(Material material) {
        for (Material highArcheologyResource : highArcheologyResources) {
            if (material.equals(highArcheologyResource)) {
                return true;
            }
        }
        return false;
    }

    private void dropXP(Block block, int experienceAmount) {
        World world = block.getWorld();
        Location location = block.getLocation();
        world.spawn(location, ExperienceOrb.class, experienceOrb -> experienceOrb.setExperience(experienceAmount));
    }

    private boolean isPlayerPlaced(Block block) {
        CoreProtectAPI coreProtectAPI = FarmingXpDrop.getCoreProtectAPI();
        List<String[]> blockLookup = coreProtectAPI.blockLookup(block, 3600);
        for (String[] entry : blockLookup) {
            if ((entry[6] + entry[7] + entry[8] + entry[9]).equals("0101")) return true;
        }
        return false;
    }
}