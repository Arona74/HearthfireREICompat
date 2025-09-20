package io.arona74.hearthfirereicompat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;

import java.util.List;

// Woodcutting Display
public class HearthfireWoodcuttingDisplay implements Display {
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public HearthfireWoodcuttingDisplay(Recipe<?> recipe) {
        // Inputs
        List<Ingredient> ing = recipe.getIngredients();
        if (!ing.isEmpty()) {
            this.inputs = List.of(EntryIngredients.ofIngredient(ing.get(0)));
        } else {
            this.inputs = List.of();
        }

        // Output (stack with count)
        ItemStack output = recipe.getOutput(null);
        this.outputs = List.of(EntryIngredients.of(output));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return HearthfireREIPlugin.WOODCUTTING;
    }
}
