package com.webcheckers.appl;
import com.google.gson.Gson;

public class Message {
    private String text;
    public enum Type{
        info, error
    }
    private Type type;

    public Message(String text, String type){
        this.text = text;

        if(type.equals("info")){
            this.type = Type.info;
        }
        else{
            this.type = Type.error;
        }
    }

    public String getText(){
        return this.text;
    }

    public Type getType(){
        switch(this.type){
            case info:
                return Type.info;
            case error:
                return Type.error;
             default:
                 throw new UnsupportedOperationException();
        }
    }


    public String toJson(){
      return new Gson().toJson(this);
    }
}
