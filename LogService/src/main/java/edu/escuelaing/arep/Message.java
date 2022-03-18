package edu.escuelaing.arep;

import java.util.Date;
/**
 *
 * @author medin
 */
public class Message {
    private String info;
    private Date date;

    public Message(String info){
        this.info=info;
        this.date = new Date();
    }

    public Message(String info,Date date){
        this.info=info;
        this.date = date;
    }

    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
