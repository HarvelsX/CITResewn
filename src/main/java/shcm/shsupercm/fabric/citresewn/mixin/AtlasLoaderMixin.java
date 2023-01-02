package shcm.shsupercm.fabric.citresewn.mixin;

import net.minecraft.client.texture.atlas.AtlasLoader;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.AtlasSourceType;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import shcm.shsupercm.fabric.citresewn.CITResewn;
import shcm.shsupercm.fabric.citresewn.pack.PackParser;

import java.util.List;
import java.util.Map;

@Mixin(AtlasLoader.class)
public class AtlasLoaderMixin {
    @Inject(method = "of", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void citresewn$atlasSource(ResourceManager resourceManager, Identifier id, CallbackInfoReturnable<AtlasLoader> cir, Identifier identifier, List<AtlasSource> list) {
        if (id.getPath().equals("blocks") && id.getNamespace().equals("minecraft")) {
            list.add(new AtlasSource() {
                @Override
                public void load(ResourceManager resourceManager, SpriteRegions regions) {
                    for (String root : PackParser.ROOTS) {
                        ResourceFinder resourceFinder = new ResourceFinder(root + "/cit", ".png");
                        for (Map.Entry<Identifier, Resource> entry : resourceFinder.findResources(resourceManager).entrySet())
                            regions.add(resourceFinder.toResourceId(entry.getKey()).withPrefixedPath(root + "/cit/"), entry.getValue());
                    }
                }

                @Override
                public AtlasSourceType getType() {
                    return null;
                }
            });
        }
    }
}
