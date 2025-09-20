package io.arona74.hearthfirereicompat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HearthfireWoodworkingDisplay implements Display {
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public HearthfireWoodworkingDisplay(Recipe<?> recipe) {
        List<EntryIngredient> tempInputs = new ArrayList<>();
        List<EntryIngredient> tempOutputs = new ArrayList<>();

        try {
            // Get the basic ingredients first
            List<Ingredient> ingredients = recipe.getIngredients();
            
            // Get the counts using the methods we found
            int ingredientCount = 1;
            int supplementCount = 1;
            int dyeCount = 1;
            
            ingredientCount = (Integer) recipe.getClass().getMethod("getIngredientCount").invoke(recipe);
            supplementCount = (Integer) recipe.getClass().getMethod("getSupplementCount").invoke(recipe);
            dyeCount = (Integer) recipe.getClass().getMethod("getDyeCount").invoke(recipe);
            
            // Add ingredients with proper counts
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                if (!ingredient.isEmpty()) {
                    // Get the first matching stack and adjust its count
                    ItemStack[] stacks = ingredient.getMatchingStacks();
                    if (stacks.length > 0) {
                        ItemStack adjustedStack = stacks[0].copy();
                        
                        // Apply the correct count based on ingredient index
                        if (i == 0) {
                            adjustedStack.setCount(ingredientCount);
                        } else if (i == 1) {
                            adjustedStack.setCount(supplementCount);
                        } else if (i == 2) {
                            adjustedStack.setCount(dyeCount);
                        }
                        
                        tempInputs.add(EntryIngredients.of(adjustedStack));
                    } else {
                        // Fallback to original ingredient if no stacks
                        tempInputs.add(EntryIngredients.ofIngredient(ingredient));
                    }
                }
            }
            
            // Get output
            ItemStack result = recipe.getOutput(net.minecraft.registry.DynamicRegistryManager.EMPTY);
            if (!result.isEmpty()) {
                tempOutputs.add(EntryIngredients.of(result));
            }

        } catch (Exception e) {
            // Fallback placeholders
            tempInputs.add(EntryIngredients.of(Items.OAK_PLANKS, 4));
            tempInputs.add(EntryIngredients.of(Items.PAPER, 1));
            tempInputs.add(EntryIngredients.of(Items.BLACK_DYE, 4));
            tempOutputs.add(EntryIngredients.of(Items.CRAFTING_TABLE));
            
        }

        this.inputs = tempInputs;
        this.outputs = tempOutputs;
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
        return HearthfireREIPlugin.WOODWORKING;
    }
}