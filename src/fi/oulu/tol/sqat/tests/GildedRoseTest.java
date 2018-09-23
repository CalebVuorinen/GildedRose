package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	// Example scenarios for testing
	//   Item("+5 Dexterity Vest", 10, 20));
	//   Item("Aged Brie", 2, 0));
	//   Item("Elixir of the Mongoose", 5, 7));
	//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
	//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
	//   Item("Conjured Mana Cake", 3, 6));

	@Test
	public void testUpdateEndOfDay_Qualities() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		store.addItem(new Item("Elixir of the Mongoose", 5, 7) );
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) );
		store.addItem(new Item("Conjured Mana Cake", 3, 6) );
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item itemMongoose = items.get(1);
		Item itemRagnaros = items.get(2);
		Item itemBackstagePass = items.get(3);
		Item itemCake = items.get(4);
		Item itemVest = items.get(5);
		
		assertEquals(11, itemBrie.getQuality());
		assertEquals(6, itemMongoose.getQuality());
		assertEquals(80, itemRagnaros.getQuality());
		assertEquals(21, itemBackstagePass.getQuality());
		assertEquals(5, itemCake.getQuality());
		assertEquals(19, itemVest.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_2days_Qualities() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		store.addItem(new Item("Elixir of the Mongoose", 5, 7) );
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) );
		store.addItem(new Item("Conjured Mana Cake", 3, 6) );
		store.addItem(new Item("+5 Dexterity Vest", 10, 20));
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item itemMongoose = items.get(1);
		Item itemRagnaros = items.get(2);
		Item itemBackstagePass = items.get(3);
		Item itemCake = items.get(4);
		Item itemVest = items.get(5);
		
		assertEquals(12, itemBrie.getQuality());
		assertEquals(5, itemMongoose.getQuality());
		assertEquals(80, itemRagnaros.getQuality());
		assertEquals(22, itemBackstagePass.getQuality());
		assertEquals(4, itemCake.getQuality());
		assertEquals(18, itemVest.getQuality());
	}
	
	
	@Test
	public void testUpdateEndOfDay_noNegatives() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 0) );
		store.addItem(new Item("Elixir of the Mongoose", 5, 0) );
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 0) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 0) );
		store.addItem(new Item("Conjured Mana Cake", 3, 0) );
		store.addItem(new Item("+5 Dexterity Vest", 10, 0));
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item itemMongoose = items.get(1);
		Item itemRagnaros = items.get(2);
		Item itemBackstagePass = items.get(3);
		Item itemCake = items.get(4);
		Item itemVest = items.get(5);
		
		assertEquals(2, itemBrie.getQuality());
		assertEquals(0, itemMongoose.getQuality());
		assertEquals(0, itemRagnaros.getQuality());
		assertEquals(2, itemBackstagePass.getQuality());
		assertEquals(0, itemCake.getQuality());
		assertEquals(0, itemVest.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_QualityCantGoOver50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 49) );
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		
		assertEquals(50, itemBrie.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Sellings() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 12, 10) );
		store.addItem(new Item("Elixir of the Mongoose", 15, 7) );
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 10, 80) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 115, 20) );
		store.addItem(new Item("Conjured Mana Cake", 13, 6) );
		store.addItem(new Item("+5 Dexterity Vest", 110, 20));
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		Item itemMongoose = items.get(1);
		Item itemRagnaros = items.get(2);
		Item itemBackstagePass = items.get(3);
		Item itemCake = items.get(4);
		Item itemVest = items.get(5);
		
		// Decreases 0 per day
		assertEquals(10, itemRagnaros.getSellIn());

		// Decreases 1 per day
		assertEquals(9, itemBrie.getSellIn());
		assertEquals(12, itemMongoose.getSellIn());
		assertEquals(112, itemBackstagePass.getSellIn());
		assertEquals(10, itemCake.getSellIn());
		assertEquals(107, itemVest.getSellIn());
		
		
	}
	
	@Test
	public void testUpdateEndOfDay_StagePassQualitys() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 3, 10) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10) );
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 10) );
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePass3 = items.get(0);
		Item itemBackstagePass6 = items.get(1);
		Item itemBackstagePass9 = items.get(2);
		
		assertEquals(0, itemBackstagePass3.getQuality());
		assertEquals(21, itemBackstagePass6.getQuality());
		assertEquals(18, itemBackstagePass9.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_FastRottingItem() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("FastRottingItem", 0, 10) );
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item test = items.get(0);
		
		// 2 per day
		assertEquals(2, test.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_MediumRottingItem() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("MediumRottingItem", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item test = items.get(0);
		
		// 1, 1, 2 ,4 = 10 - 6 = 4
		assertEquals(4, test.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_ElixirOfTheMongoose_Quality_7_6() {
		// Item("Elixir of the Mongoose", 5, 7));
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Elixir of the Mongoose", 5, 7) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store .getItems();
		Item itemElix = items.get(0);
		assertEquals(6, itemElix.getQuality());
	}
	
	
	
	
    
}
