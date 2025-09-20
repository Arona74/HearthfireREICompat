package io.arona74.hearthfirereicompat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class HearthfireREIPlugin implements REIClientPlugin {
    public static final Identifier WOODCUTTING_ID = new Identifier("hearthfire", "woodcutting");
    public static final Identifier WOODWORKING_ID = new Identifier("hearthfire", "woodworking");
    
    public static final CategoryIdentifier<HearthfireWoodcuttingDisplay> WOODCUTTING =
        CategoryIdentifier.of(new Identifier("hearthfirereicompat", "crhf_woodcutting"));
    
    public static final CategoryIdentifier<HearthfireWoodworkingDisplay> WOODWORKING =
        CategoryIdentifier.of(new Identifier("hearthfirereicompat", "crhf_woodworking"));
    
    public static final RecipeType<?> WOODCUTTING_TYPE = Registries.RECIPE_TYPE.get(WOODCUTTING_ID);
    public static final RecipeType<?> WOODWORKING_TYPE = Registries.RECIPE_TYPE.get(WOODWORKING_ID);

    @Override
    public void registerCategories(CategoryRegistry registry) {
        if (!FabricLoader.getInstance().isModLoaded("hearthfire")) {
            return;
        }

        // Add categories
        registry.add(new HearthfireWoodcuttingCategory());
        registry.add(new HearthfireWoodworkingCategory());

        // Add workstations
        registry.addWorkstations(WOODCUTTING,
            EntryStacks.of(Registries.ITEM.get(new Identifier("hearthfire", "sawhorse"))));
        registry.addWorkstations(WOODWORKING,
            EntryStacks.of(Registries.ITEM.get(new Identifier("hearthfire", "carpentry_table"))));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        if (!FabricLoader.getInstance().isModLoaded("hearthfire")) {
            return;
        }

        // Woodcutting recipes
        registry.registerFiller(
            Recipe.class,
            recipe -> recipe.getType() == Registries.RECIPE_TYPE.get(new Identifier("hearthfire", "woodcutting")),
            recipe -> new HearthfireWoodcuttingDisplay(recipe)
        );

        // Woodworking recipes
        registry.registerFiller(
            Recipe.class,
            recipe -> recipe.getType() == Registries.RECIPE_TYPE.get(new Identifier("hearthfire", "woodworking")),
            recipe -> new HearthfireWoodworkingDisplay(recipe)
        );
    }
}