package com.etnetera.hr.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */

@Entity
public class JavaScriptFramework {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Size(max = 30)
	@Column(nullable = false, length = 30)
	private String name;
	

	@Column(nullable = false)
	private Double version;
	

	@Column(nullable = false)
	private String deprecationDate;
	

	@Column(nullable = false)
	private int hypeLevel;

	public JavaScriptFramework(String name, Double version, String deprecationDate, int i) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = i;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}

	public String getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(String deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public int getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(int hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	public JavaScriptFramework() {
	}
		
	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + ", version =" + version + ", deprecationDate =" + deprecationDate + ", hypeLevel =" + hypeLevel + "]";
	}

	
}
