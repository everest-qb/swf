package sunspring.tests.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Embeddable
@Table(name="FND_LOOKUP_VALUES",schema="APPLSYS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class FunLookupValueKey implements Serializable {
	
	@XmlTransient
	private static final long serialVersionUID = 1L;

	@Column(name="LOOKUP_TYPE")
	private String lookupType;
	
	@Column(name="\"LANGUAGE\"")
	private String language;
	
	@Column(name="LOOKUP_CODE")
	private String lookupCode;
	
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getLookupCode() {
		return this.lookupCode;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	public String getLookupType() {
		return this.lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((lookupCode == null) ? 0 : lookupCode.hashCode());
		result = prime * result + ((lookupType == null) ? 0 : lookupType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FunLookupValueKey))
			return false;
		FunLookupValueKey other = (FunLookupValueKey) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (lookupCode == null) {
			if (other.lookupCode != null)
				return false;
		} else if (!lookupCode.equals(other.lookupCode))
			return false;
		if (lookupType == null) {
			if (other.lookupType != null)
				return false;
		} else if (!lookupType.equals(other.lookupType))
			return false;
		return true;
	}
}
