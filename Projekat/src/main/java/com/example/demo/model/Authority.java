package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="AUTHORITY")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="uloga")
    String uloga;

    @Override
    public String getAuthority() {
        return uloga;
    }
    
	public Authority() {

	}


	public Authority(String uloga) {
		super();
		this.uloga = uloga;
	}




	public void setName(String uloga) {
        this.uloga = uloga;
    }

    @JsonIgnore
    public String getUloga() {
        return uloga;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "Authority [id=" + id + ", uloga=" + uloga + "]";
	}

    
}
