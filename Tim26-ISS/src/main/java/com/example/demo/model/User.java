package com.example.demo.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="users_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="surname", nullable = false)
    private String surname;
    @Column(name="profilePicture", nullable = true)
    private String profilePicture;
    @Column(name="telephoneNumber", nullable = false)
    private String telephoneNumber;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="blocked_flag", nullable = false)
    private boolean blocked;
    @Column(name="active_flag", nullable = false)
    private boolean active;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<RoleAndrej> roles = new ArrayList<>();
    @Column(name = "role", nullable = true)
    private String role;

//    @Column(name="password_date", nullable = false)
    private Date lastPasswordResetDate;

    @Column(name="password_code", nullable = true)
    private Integer code;

    public User() {
    }

    public User(String name, String surname, String profilePicture, String telephoneNumber,
                String email, String address, String password, boolean blocked, boolean active) {
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = blocked;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                ", active=" + active +
                '}';
    }
}


