package cat.kiwicat.jukeboxify.client;

import cat.kiwicat.jukeboxify.client.datagen.ModModelProvider;
import cat.kiwicat.jukeboxify.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class JukeboxifyDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
	}


	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
	}

	// Recipe generator
	private static class RecipeGen extends FabricRecipeProvider {


		public RecipeGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		public void generate(RecipeExporter exporter) {

			ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.REMOTE)
					.pattern("iii")
					.pattern("iri")
					.pattern("ili")
					.input('l', Items.STONE_BUTTON)
					.input('i', Items.COBBLESTONE)
					.input('r', Items.REDSTONE)
					.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE),
							FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
					.offerTo(exporter);

		}
	}


}
