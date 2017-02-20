package com.winsigns.investment.frame.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.hateoas.Identifiable;

@MappedSuperclass
public abstract class DictionaryAbstractEntity implements Identifiable<Long> {
	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DictionaryAbstractEntity that = (DictionaryAbstractEntity) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
