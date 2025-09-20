package io.arona74.hearthfirereicompat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HearthfireWoodworkingCategory implements DisplayCategory<HearthfireWoodworkingDisplay> {

    @Override
    public CategoryIdentifier<? extends HearthfireWoodworkingDisplay> getCategoryIdentifier() {
        return HearthfireREIPlugin.WOODWORKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.hearthfirereicompat.woodworking");
    }

    @Override
    public Renderer getIcon() {
        try {
            return EntryStacks.of(Registries.ITEM.get(new Identifier("hearthfire", "carpentry_table")));
        } catch (Exception e) {
            return EntryStacks.of(Items.CRAFTING_TABLE);
        }
    }

    @Override
    public List<Widget> setupDisplay(HearthfireWoodworkingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = new ArrayList<>();

        // Background
        widgets.add(Widgets.createRecipeBase(bounds));

        // Main ingredient slot (top left)
        if (display.getInputEntries().size() > 0) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x - 16, startPoint.y - 5))
                .entries(display.getInputEntries().get(0))
                .markInput());
        }

        // Supplement slot (top right)
        if (display.getInputEntries().size() > 1) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y - 5))
                .entries(display.getInputEntries().get(1))
                .markInput());
        }

        // Dye slot (bottom center)
        if (display.getInputEntries().size() > 2) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x - 6, startPoint.y + 15))
                .entries(display.getInputEntries().get(2))
                .markInput());
        }

        // Arrow pointing to output
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));

        // Result slot background
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));

        // Output slot
        if (!display.getOutputEntries().isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 46;
    }
}