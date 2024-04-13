package me.secretlovers.bedwars.database;
import java.lang.*;

import io.vertx.core.json.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerData {

    final String id;
    final String nickname;
    int kills;
    int deaths;
    int finalKills;
    int bedsBroken;

    public PlayerData(JsonObject json) {
        id          = json.getString("_id");
        nickname    = json.getString("nickname");
        kills       = json.getInteger("kills");
        deaths      = json.getInteger("deaths");
        finalKills  = json.getInteger("finalKills");
        bedsBroken  = json.getInteger("bedsBroken");
    }

    public PlayerData(String nickname) {  //New player data
        id              = null;
        this.nickname   = nickname;
        kills           = 0;
        deaths          = 0;
        finalKills      = 0;
        bedsBroken      = 0;
    }

}
