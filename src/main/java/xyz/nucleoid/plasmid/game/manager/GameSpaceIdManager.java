package xyz.nucleoid.plasmid.game.manager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.RandomStringUtils;
import xyz.nucleoid.plasmid.game.config.GameConfig;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

final class GameSpaceIdManager {
    private final Multimap<GameConfig<?>, Identifier> configToIds = HashMultimap.create();
    private final Map<Identifier, GameConfig<?>> idToConfig = new Object2ObjectOpenHashMap<>();

    public Identifier acquire(GameConfig<?> config) {
        var ids = this.configToIds.get(config);

        var uniqueId = this.generateUniqueId(config, ids);
        ids.add(uniqueId);

        this.idToConfig.put(uniqueId, config);

        return uniqueId;
    }

    public void release(Identifier id) {
        var config = this.idToConfig.remove(id);
        if (config != null) {
            this.configToIds.remove(config, id);
        }
    }

    private Identifier generateUniqueId(GameConfig<?> config, Collection<Identifier> ids) {
        var configId = this.getIdForConfig(config);
        if (ids.isEmpty()) {
            return configId;
        }

        Identifier uniqueId;
        do {
            uniqueId = this.generateRandomId(configId);
        } while (ids.contains(uniqueId));

        return uniqueId;
    }

    private Identifier generateRandomId(Identifier configId) {
        var random = RandomStringUtils.randomAlphabetic(4).toLowerCase(Locale.ROOT);
        return new Identifier(configId.getNamespace(), configId.getPath() + "/" + random);
    }

    private Identifier getIdForConfig(GameConfig<?> config) {
        return config.getType().getIdentifier();
    }
}
