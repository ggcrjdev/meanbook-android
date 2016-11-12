package com.brilgo.meanbookandroidapp.api.response;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	public final String id;
	public final String username;
	public final Date loginDate;
	private User(String id, String username, Date loginDate) {
		this.id = id;
		this.username = username;
		this.loginDate = loginDate;
	}
	public User(String username) {
		this(username, username, new Date(0L));
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
		User other = (User) obj;
		return equalsIdField(other);
	}
	
	private boolean equalsIdField(User other) {
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
