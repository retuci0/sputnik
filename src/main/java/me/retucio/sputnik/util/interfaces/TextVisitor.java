/* code by meteorclient dev team */

package me.retucio.sputnik.util.interfaces;

import net.minecraft.text.PlainTextContent;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

// lo siento por robaros el código, meteor :(
// sorry for stealing your code, meteor dev team

@FunctionalInterface
public interface TextVisitor<T> {
    Optional<T> accept(Text text, Style style, String string);

    static <T> Optional<T> visit(Text text, TextVisitor<T> visitor, Style baseStyle) {
        Queue<Text> queue = collectSiblings(text);
        return text.visit((style, string) -> visitor.accept(queue.remove(), style, string), baseStyle);
    }

    static ArrayDeque<Text> collectSiblings(Text text) {
        ArrayDeque<Text> queue = new ArrayDeque<>();
        collectSiblings(text, queue);
        return queue;
    }

    private static void collectSiblings(Text text, Queue<Text> queue) {
        if (!(text.getContent() instanceof PlainTextContent ptc) || !ptc.string().isEmpty()) queue.add(text);
        for (Text sibling : text.getSiblings()) {
            collectSiblings(sibling, queue);
        }
    }
}