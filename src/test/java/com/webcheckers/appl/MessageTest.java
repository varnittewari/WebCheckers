package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.appl.Message;

@Tag("Application-tier")
public class MessageTest {

    /*
    Test the ability to make a Message object of a message type
     */
    @Test
    public void test_make_message1(){
        final Message messg = new Message("Hi! This is a message.","message");
        assertNotNull(messg);
    }

    /*
    Test the ability to make a Message object of an error type
     */
    @Test
    public void test_make_message2(){
        final Message error = new Message("Error!!", "error");
        assertNotNull(error);
    }

    /*
    Test to make sure the string in the Message object is the same
     */
    @Test
    public void test_match_message1(){
        final Message messg = new Message("Hi! This is a message.","message");
        assertEquals("Hi! This is a message.", messg.getText());
    }

    /*
    Test to make sure the string in the Message object is the same
     */
    @Test
    public void test_match_message2(){
        final Message error = new Message("Error!!", "error");
        assertEquals("Error!!", error.getText());
    }

    @Test
    public void test_match_message3(){
        final Message info = new Message("Hi, this is an info message.", "info");
        assertEquals("Hi, this is an info message.", info.getText());
    }

    @Test
    public void test_type(){
        final Message info = new Message("Hi, this is an info message.", "info");
        final Message error = new Message("Error!!", "error");
        //final Message wrong = new Message("Wrong", "wrong");
        assertNotNull(info.getType());
        assertNotNull(error.getType());
        //Don't know how to test UnsupportedOperationException.
    }

    @Test
    public void test_Json(){
        final Message info = new Message("Message", "info");
        assertNotNull(info.toJson());
    }


}
