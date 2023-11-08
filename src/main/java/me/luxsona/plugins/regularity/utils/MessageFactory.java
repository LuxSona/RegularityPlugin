package me.luxsona.plugins.regularity.utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;

public class MessageFactory {

    public static TextComponent createMessage(String message, int color){
        TextColor textColor = TextColor.color(color);

        //Create a style.
        Style style = Style.style(textColor, TextDecoration.BOLD);
        TextComponent text = Component.text().append(Component.text(message, style)).build();
        return text;
    }
}
