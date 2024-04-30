package me.secretlovers.bedwars.game.team;

import lombok.Getter;
import lombok.Setter;
import me.secretlovers.bedwars.game.BedWarsPlayer;
import me.secretlovers.bedwars.game.resourses.Generator;
import me.secretlovers.bedwars.game.resourses.ResourseType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Bed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Team {

    private final TeamColor teamColor;
    private final int maxPlayers;
    private ArrayList<BedWarsPlayer> players = new ArrayList<>();
    private final Location spawnLocation;
    private final Location bedLocation;
    private final Generator ironGenerator;
    private final Generator goldGenerator;
    private Map<UpgradeType, Integer> upgrades = new HashMap<>();
    @Setter
    private boolean hasBed;

    public Team(TeamColor teamColor, int maxPlayers, Location spawnLocation, Location bedLocation, Location generatorLocation) {
        this.teamColor      = teamColor;
        this.maxPlayers     = maxPlayers;
        this.spawnLocation  = spawnLocation;
        this.bedLocation    = bedLocation;
        this.ironGenerator  = new Generator(20L, ResourseType.IRON, generatorLocation);
        this.goldGenerator  = new Generator(80L, ResourseType.GOLD, generatorLocation);
        this.hasBed         = true;
        for(UpgradeType upgradeType : UpgradeType.values()) {
            upgrades.put(upgradeType, 0);
        }
    }

    public void placeBed() {
        BlockState blockFootState = bedLocation.getBlock().getState();
        blockFootState.setType(Material.BED_BLOCK);
        Bed bedFootData = new Bed(Material.BED_BLOCK);
        bedFootData.setHeadOfBed(false);
        bedFootData.setFacingDirection(BlockFace.EAST);
        blockFootState.setData(bedFootData);
        blockFootState.update(true, false);

        BlockState blockHeadState = bedLocation.getBlock().getRelative(BlockFace.EAST).getState();
        blockHeadState.setType(Material.BED_BLOCK);
        Bed bedHeadData = new Bed(Material.BED_BLOCK);
        bedHeadData.setHeadOfBed(true);
        bedHeadData.setFacingDirection(BlockFace.EAST);
        blockHeadState.setData(bedHeadData);
        blockHeadState.update(true, false);
    }

    public void upgradeTeam(UpgradeType upgradeType) {
        int nextLevel = upgrades.get(upgradeType);
        upgrades.remove(upgradeType);
        upgrades.put(upgradeType, nextLevel);
    }

}
