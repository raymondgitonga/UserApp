package com.poolke.UserApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User implements Persistable<Integer>{
    @Id
    @Column("user_id")
    private String userId;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("phone_number")
    private String phoneNumber;
    @Column("phone_verified")
    private int phoneNumberVerified = 0;
    @Column("password")
    private String password;
    @Column("is_active")
    private int isActive = 1;

    @Override
    public Integer getId() {
        return null;
    }

    @Transient
    private boolean newUser;

    @Override
    @Transient
    public boolean isNew() {
        return this.newUser || userId == null;
    }

    public User setAsNew(){
        this.newUser = true;
        return this;
    }
}
