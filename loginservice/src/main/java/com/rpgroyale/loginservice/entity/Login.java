package com.rpgroyale.loginservice.entity;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
public class Login {

    @Column
    @Id
    private String user;

    @Column
    private String status;

    @Column
    private boolean active;

    @Column
    private String sessionId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString()
    {
        return "user: " + user + " status: " + status.toString() + " active: " + active + " sessionId: " + sessionId;
    }
}
