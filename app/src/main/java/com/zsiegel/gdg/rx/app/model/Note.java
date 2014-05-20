package com.zsiegel.gdg.rx.app.model;

import android.os.SystemClock;

import java.util.Date;

/**
 * @author zsiegel (zac.s@akta.com)
 */
public class Note {

    private long id;
    private String name;
    private Date createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void save() {
        SystemClock.sleep(1500);
    }
}
