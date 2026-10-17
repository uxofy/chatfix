package chatclickfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// priority = 1: runs before mixins of other mods at the same HEAD point.
@Mixin(value = ChatScreen.class, priority = 1)
public abstract class ChatClickFixMixin extends Screen {
    protected ChatClickFixMixin(Component title) { super(title); }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void chatclickfix$handleClick(MouseButtonEvent event, boolean doubleClick,
                                          CallbackInfoReturnable<Boolean> cir) {
        if (event.button() != 0) return;
        Style style = Minecraft.getInstance().gui.getChat()
                .getClickedComponentStyleAt(event.x(), event.y());
        if (style != null && style.getClickEvent() != null && this.handleComponentClicked(style)) {
            cir.setReturnValue(true);
        }
    }
}
