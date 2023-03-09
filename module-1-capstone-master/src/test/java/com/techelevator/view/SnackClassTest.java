package com.techelevator.view;

import com.techelevator.Snack;
import org.junit.Assert;
import org.junit.Test;

public class SnackClassTest {

    //Test Return Message for items that contain a sertain "SlotID"
    //refer to Snack class .getmessage method.
    Snack testmessage1 = new Snack("A1",null , 0, null);
    Snack testmessage2 = new Snack("B2", null, 0, null);
    Snack testmessage3 = new Snack("C3", null, 0, null);
    Snack testmessage4 = new Snack("D4", null, 0, null);



    @Test
    public void testmessage1() {
        String slotA = "Crunch Crunch, Yum!";
        Assert.assertEquals(slotA, testmessage1.getMessage());
    }

    @Test
    public void testmessage2() {
        String slotB = "Munch Munch, Yum!";
        Assert.assertEquals(slotB, testmessage2.getMessage());
    }


    @Test
        public void testmessage3() {
        String slotC = "Glug Glug, Yum!";
        Assert.assertEquals(slotC, testmessage3.getMessage());

    }
    @Test
    public void testmessage4() {
        String slotD = "Chew Chew, Yum!";
        Assert.assertEquals(slotD, testmessage4.getMessage());



    }
}
