package net.javaguides.springmvc.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Where;


@Entity
@Where(clause="is_active=1")
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    
    @Version
    @Column(name = "version", length = 1)
    private int version = 0;
    
    @Column(name = "data_of_birth")
    private int dataOFbirth;
    
    @Column(name = "last_updated")
    LocalDateTime lastUpdated;
    
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    
    @Column(name = "is_active")
    private Boolean active;

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int version() {
		return version;
	}


	public int getDataOFbirth() {
		return dataOFbirth;
	}

	public void setDataOFbirth(int dataOFbirth) {
		this.dataOFbirth = dataOFbirth;
	}

	public void LastUpdated() {
		lastUpdated = LocalDateTime.now();
	}

	public void LastUpdated(LocalDateTime localDateTime) {
		lastUpdated = localDateTime;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}



	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", version=" + version + ", dataOFbirth=" + dataOFbirth + ", lastUpdatedBy=" + lastUpdatedBy + ", is_active=" + active + ",email=" + email + "]";
    }
}