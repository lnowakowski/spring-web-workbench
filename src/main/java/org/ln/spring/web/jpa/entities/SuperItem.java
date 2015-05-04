package org.ln.spring.web.jpa.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "items", uniqueConstraints = {
		@UniqueConstraint(columnNames = "number"),
		@UniqueConstraint(columnNames = "name")
})
@EntityListeners(PersistListener.class)
public class SuperItem implements Comparable<SuperItem> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Min(value = 10)
	@NotNull
	@Column(unique = true)
	private int number;
	@Length(max = 50)
	@NotEmpty
	@Column(unique = true)
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Version
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public Integer getVersion() {
		return version;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperItem other = (SuperItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(SuperItem o) {
		return name.compareToIgnoreCase(o.name);
	}

	@Override
	public String toString() {
		return "SuperItem [id=" + id + ", number=" + number + ", name=" + name
				+ ", created=" + created + ", version=" + version + "]";
	}
}
