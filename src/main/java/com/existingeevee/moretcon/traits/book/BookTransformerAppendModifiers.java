package com.existingeevee.moretcon.traits.book;

import java.util.List;

import com.existingeevee.moretcon.traits.modifiers.Shocking;

import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.BookRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import slimeknights.tconstruct.library.modifiers.Modifier;

// adapted from Tinkers' MEMES BookTransformerAppendModifiers
public class BookTransformerAppendModifiers extends SectionTransformer {

    private final BookRepository source;
    private final List<Modifier> modCollector;

    public BookTransformerAppendModifiers(BookRepository source, List<Modifier> modCollector) {
        super("modifiers");
        this.source = source;
        this.modCollector = modCollector;
    }

    @Override
    public void transform(BookData book, SectionData section) {
        ContentListing listing = (ContentListing) section.pages.get(0).content;
        for (Modifier mod : modCollector) {
        	PageData page = new PageData();
        	page.source = source;
        	page.parent = section;
        	page.type = mod instanceof Shocking ? "lightning_modifier" : "modifier";
        	page.data = "modifiers/" + mod.identifier.replaceFirst("moretcon.", "") + ".json";
        	section.pages.add(page);
        	page.load();
        	listing.addEntry(mod.getLocalizedName(), page);
        }
    }

}