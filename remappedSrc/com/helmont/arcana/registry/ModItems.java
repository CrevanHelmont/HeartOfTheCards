package com.helmont.arcana.registry;

import com.helmont.arcana.Arcana;
import com.helmont.arcana.creative_tabs.ArcanaTab;
import com.helmont.arcana.item_containers.CardHolder;
import com.helmont.arcana.items.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ModItems {

    public static final TarotItem TAROT_CARD = new TarotItem(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB));
    public static final Death DEATH = new Death(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final Judgement JUDGEMENT = new Judgement(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final Justice JUSTICE = new Justice(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final Strength STRENGTH = new Strength(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final Temperance TEMPERANCE = new Temperance(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheChariot THE_CHARIOT = new TheChariot(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheDevil THE_DEVIL = new TheDevil(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheEmperor THE_EMPEROR = new TheEmperor(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheEmpress THE_EMPRESS = new TheEmpress(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheFool THE_FOOL = new TheFool(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheHangedMan THE_HANGED_MAN = new TheHangedMan(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheHermit THE_HERMIT = new TheHermit(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheHierophant THE_HIEROPHANT = new TheHierophant(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheHighPriestess THE_HIGH_PRIESTESS = new TheHighPriestess(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheLovers THE_LOVERS = new TheLovers(new Item.Settings().maxDamage(50).rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB));
    public static final TheMagician THE_MAGICIAN = new TheMagician(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheMoon THE_MOON = new TheMoon(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheStar THE_STAR = new TheStar(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheSun THE_SUN = new TheSun(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheTower THE_TOWER = new TheTower(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final TheWorld THE_WORLD = new TheWorld(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final WheelOfFortune WHEEL_OF_FORTUNE = new WheelOfFortune(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final CardHolder CARD_HOLDER = new CardHolder(new Item.Settings().rarity(Rarity.EPIC).group(ArcanaTab.ARCANA_TAB).maxCount(1));
    public static final Item GOAT_HORN = new Item(new Item.Settings().group(ArcanaTab.ARCANA_MATS));
    public static final Item STARDUST = new Item(new Item.Settings().group(ArcanaTab.ARCANA_MATS));
    public static final Item ENRICHED_AMETHYST = new Item(new Item.Settings().group(ArcanaTab.ARCANA_MATS));

    public static void registerItems() {
        // Base Item
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "tarot_card"), TAROT_CARD);

        // Major Arcana
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "death"), DEATH);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "judgement"), JUDGEMENT);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "justice"), JUSTICE);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "strength"), STRENGTH);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "temperance"), TEMPERANCE);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_chariot"), THE_CHARIOT);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_devil"), THE_DEVIL);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_emperor"), THE_EMPEROR);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_empress"), THE_EMPRESS);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_fool"), THE_FOOL);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_hanged_man"), THE_HANGED_MAN);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_hermit"), THE_HERMIT);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_hierophant"), THE_HIEROPHANT);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_high_priestess"), THE_HIGH_PRIESTESS);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_lovers"), THE_LOVERS);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_magician"), THE_MAGICIAN);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_moon"), THE_MOON);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_star"), THE_STAR);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_sun"), THE_SUN);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_tower"), THE_TOWER);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "the_world"), THE_WORLD);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "wheel_of_fortune"), WHEEL_OF_FORTUNE);

        // Container
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "card_holder"), CARD_HOLDER);

        // Materials
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "goat_horn"), GOAT_HORN);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "stardust"), STARDUST);
        Registry.register(Registry.ITEM, new Identifier(Arcana.MOD_ID, "enriched_amethyst"), ENRICHED_AMETHYST);

    }
}
