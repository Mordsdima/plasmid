package xyz.nucleoid.plasmid.impl.portal.menu;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.nucleoid.plasmid.impl.portal.GamePortalBackend;
import xyz.nucleoid.plasmid.api.game.GameSpace;
import xyz.nucleoid.plasmid.impl.portal.GamePortal;

public record PortalEntry(
        GamePortal portal,
        Text name,
        List<Text> description,
        ItemStack icon
) implements MenuEntry {
    @Override
    public void click(ServerPlayerEntity player) {
        this.portal.requestJoin(player);
    }

    @Override
    public int getPlayerCount() {
        return this.portal.getPlayerCount();
    }

    @Override
    public void provideGameSpaces(Consumer<GameSpace> consumer) {
        portal.provideGameSpaces(consumer);
    }

    @Override
    public GamePortalBackend.ActionType getActionType() {
        return this.portal.getBackend().getActionType();
    }
}