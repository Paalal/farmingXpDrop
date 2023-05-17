package de.projectethos.farmingxpdrop;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {
    final Random rand = new Random();

    final List<Material> farmerResources = Arrays.asList(Material.WHEAT, Material.CARROT, Material.BEETROOT, Material.POTATO, Material.MELON, Material.PUMPKIN, Material.SUGAR_CANE);
    final List<Material> logs = Arrays.asList(Material.OAK_LOG, Material.DARK_OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.ACACIA_LOG, Material.MANGROVE_LOG, Material.JUNGLE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM);
    final List<Material> leafs = Arrays.asList(Material.OAK_LEAVES, Material.DARK_OAK_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES, Material.ACACIA_LEAVES, Material.MANGROVE_LEAVES, Material.JUNGLE_LEAVES);
    final List<Material> lowArcheologyResources = Arrays.asList(Material.DIRT, Material.GRASS_BLOCK, Material.SAND, Material.GRAVEL, Material.MYCELIUM, Material.RED_SAND, Material.ROOTED_DIRT);
    final List<Material> highArcheologyResources = Arrays.asList(Material.CLAY, Material.SOUL_SAND, Material.COARSE_DIRT, Material.PODZOL, Material.SOUL_SOIL, Material.MUD, Material.MUDDY_MANGROVE_ROOTS);

    @EventHandler
    public void onFarmerResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isFarmerResource(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(89) == 0) dropXP(block, rand.nextInt(2) + 1 );
    }

    @EventHandler
    public void onLogBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLog(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(59) == 0) dropXP(block, rand.nextInt(7) + 1 );
    }

    @EventHandler
    public void onLeafBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLeaf(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(79) == 0) dropXP(block, rand.nextInt(2) + 1 );
    }

    @EventHandler
    public void onLowArcheologyResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isLowArcheologyResource(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(89) == 0) dropXP(block, rand.nextInt(2) + 1 );
    }

    @EventHandler
    public void onHighArcheologyResourceBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!isHighArcheologyResource(block.getState().getBlockData().getMaterial())) return;
        if (isPlayerPlaced(block)) return;
        if (rand.nextInt(89) == 0) dropXP(block, rand.nextInt(4) + 1 );
    }

    private boolean isFarmerResource(Material material) {
        for (Material resource : farmerResources) {
            if (material.equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLog(Material material) {
        for (Material resource : logs) {
            if (material.equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLeaf(Material material) {
        for (Material resource : leafs) {
            if (material.equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLowArcheologyResource(Material material) {
        for (Material resource : lowArcheologyResources) {
            if (material.equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHighArcheologyResource(Material material) {
        for (Material resource : highArcheologyResources) {
            if (material.equals(resource)) {
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
        return blockLookup.size() > 0;
    }
}