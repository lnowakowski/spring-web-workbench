package org.ln.spring.web.dto;

import org.ln.spring.web.jpa.entities.SuperItem;

public class SuperItemBuilder {
	public static Builder create() {
		return new Builder();
	}

	public static class Builder {
		private SuperItem item = new SuperItem();

		public Builder withId(long id) {
			item.setId(id);

			return this;
		}

		public Builder withNumber(int number) {
			item.setNumber(number);

			return this;
		}

		public Builder withName(String name) {
			item.setName(name);

			return this;
		}

		public SuperItem build() {
			return item;
		}
	}
}
