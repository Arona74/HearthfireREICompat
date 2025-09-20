package io.arona74.hearthfirereicompat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

// Woodcutting Category
public class HearthfireWoodcuttingCategory implements DisplayCategory<HearthfireWoodcuttingDisplay> {

    @Override
    public CategoryIdentifier<? extends HearthfireWoodcuttingDisplay> getCategoryIdentifier() {
        return HearthfireREIPlugin.WOODCUTTING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.hearthfirereicompat.woodcutting");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Registries.ITEM.get(new Identifier("hearthfire", "sawhorse")));
    }

    @Override
    public List<Widget> setupDisplay(HearthfireWoodcuttingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = new ArrayList<>();
        
        // Background
        widgets.add(Widgets.createRecipeBase(bounds));
        
        // Input slot
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)).markInput());
        
        // Arrow
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        
        //Result Slot Background
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));

        // Output slot
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }
}