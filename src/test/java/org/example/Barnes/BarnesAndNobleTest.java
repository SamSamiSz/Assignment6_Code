package org.example.Barnes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BarnesAndNobleTest {

    @Test
    @DisplayName("Specification-Based Null Input -> Null Output")
    public void NullPriceForCart (){
        //dummy implementation of BookDatabase
        BookDatabase dummyDatabase = new BookDatabase() {
            @Override
            public Book findByISBN(String ISBN) {
                return null;
            }
        };

        //dummy implementation of BuyBookProcess-does nothing
        BuyBookProcess dummyProcess = new BuyBookProcess() {
            @Override
            public void buyBook(Book book, int amount) {

            }
        };
        //create instance of BarnesAndNoble with dummy objects
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(dummyDatabase,dummyProcess);

        //Calling getPriceForCart with null
        PurchaseSummary result = barnesAndNoble.getPriceForCart(null);
        Assertions.assertNull(result, "Expected getPriceForCaer(null) to return null");
    }
    @Test
    @DisplayName("Specification-Based Single Item -> Correct Price")
    public void testSingleItemPrice(){
        BookDatabase dummyDatabase = new BookDatabase() {
            @Override
            public Book findByISBN(String ISBN) {
                return new Book(ISBN,10,5);
            }
        };
        BuyBookProcess dummyProcess = new BuyBookProcess() {
            @Override
            public void buyBook(Book book, int amount) {
            }
        };

        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(dummyDatabase,dummyProcess);

        //getPriceForCart expects a Map <String,Integer>
        Map<String,Integer> order = new HashMap<>();
        order.put("12345",2);

        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);

        //verifying that the total price is correct
        Assertions.assertEquals(20,result.getTotalPrice(),"Total Price Should be 10*2=20");

        //verifying that the quanity of stock is correct
        Assertions.assertTrue(result.getUnavailable().isEmpty(),"Enough stock -> No iem should be unavailable");
    }

    @Test
    @DisplayName("Specification-Based Unavailable Stock")
    public void testInsufficientStock(){
        //dependency injection of "dummy" object "BookDatabase"
        BookDatabase dummyDatabase = new BookDatabase() {
            @Override
            public Book findByISBN(String ISBN) {
                return new Book(ISBN,10,5);
            }
        };

        final int [] recordedAmount = {0};
        //dependency injection of "dummy" object "BuyBookProcess"
        BuyBookProcess dummyProcess = new BuyBookProcess() {
            @Override
            public void buyBook(Book book, int amount) {
                recordedAmount[0] = amount;
            }
        };

        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(dummyDatabase,dummyProcess);

        //create an order with a quanity larger than the available amount
        Map<String,Integer> order = new HashMap<>();
        order.put("12345",7);

        //calling getPriceForCart with the order just created
        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);

        //Verify that total price is correct based on quanity
        Assertions.assertEquals(50,result.getTotalPrice());

        //Verify that 2 copies (7 requested - 5 available) are marked unavilable
        Book expectedBook = new Book("12345",10,5);
        Assertions.assertEquals(2,result.getUnavailable().get(expectedBook).intValue());

        //Verify that buyBook was called with available quanity(5)
        Assertions.assertEquals(5,recordedAmount[0],"buyBook should be called with available quanity");
    }

    @Test
    @DisplayName("Structural-Based Multiple Items-> Correct Processing")
    public void testMultipleItemsProcessing(){

        BookDatabase dummyDatabase = new BookDatabase() {
            @Override
            public Book findByISBN(String ISBN) {
                return new Book(ISBN,10,5);
            }
        };
        BuyBookProcess dummyProcess = new BuyBookProcess() {
            @Override
            public void buyBook(Book book, int amount) {
                //no action
            }
        };
        //creating instance of BarnesAndNoble and naming it barnesAndNoble--
        //this lets us call methods inside the BarnesAndNoble class such as getPriceForCart
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(dummyDatabase,dummyProcess);

        Map<String,Integer> order = new HashMap<>();
        order.put("11111",2);
        order.put("22222",7);

        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);
        //Expected Total Price is 70
        Assertions.assertEquals(70,result.getTotalPrice());

        //For ISBN "22222"
        //Expected unavailable is 7(Requested) - 5 (Available) = 2 Unavailable
        Book expectedBook = new Book("22222",10,5);
        Assertions.assertEquals(2,result.getUnavailable().get(expectedBook).intValue());

        //For ISBN "11111" no available copies
        Book ExpectedBook = new Book("11111",10,5);
        Assertions.assertNull(result.getUnavailable().get(ExpectedBook));
    }

}