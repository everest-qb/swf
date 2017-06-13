package sunspring.tests.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHR_DEPT_ENTRIES_ALL database table.
 * 
 */
@Entity
@Table(name="SHR_DEPT_ENTRIES_ALL",schema="SHR")
@NamedQuery(name="ShrDeptEntriesAll.findAll", query="SELECT s FROM ShrDeptEntriesAll s")
public class ShrDeptEntriesAll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHR_DEPT_ENTRIES_ALL_GENERATOR", sequenceName="SHR_DEPT_ENTRIES_ALL_S1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHR_DEPT_ENTRIES_ALL_GENERATOR")
	@Column(name="DEPT_ENTRIES_ID")
	private BigDecimal deptEntriesId;
	
	@Column(name="ATTRIBUTE_CATEGORY")
	private String attributeCategory;

	private String attribute1;

	private String attribute10;

	private String attribute11;

	private String attribute12;

	private String attribute13;

	private String attribute14;

	private String attribute15;

	private String attribute2;

	private String attribute3;

	private String attribute4;

	private String attribute5;

	private String attribute6;

	private String attribute7;

	private String attribute8;

	private String attribute9;

	@Column(name="COMPANY_ID")
	private BigDecimal companyId;

	@Column(name="CREATED_BY")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date creationDate;

	@Column(name="DEPARTMENT_ID")
	private BigDecimal departmentId;

	@Temporal(TemporalType.DATE)
	@Column(name="DISABLE_DATE")
	private Date disableDate;

	@Temporal(TemporalType.DATE)
	@Column(name="ENABLE_DATE")
	private Date enableDate;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name="LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Column(name="PARENT_DEPARTMENT_ID")
	private BigDecimal parentDepartmentId;

	private BigDecimal seq;

	public ShrDeptEntriesAll() {
	}

	public String getAttributeCategory() {
		return this.attributeCategory;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	public String getAttribute1() {
		return this.attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute10() {
		return this.attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute11() {
		return this.attribute11;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	public String getAttribute12() {
		return this.attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute13() {
		return this.attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	public String getAttribute14() {
		return this.attribute14;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	public String getAttribute15() {
		return this.attribute15;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	public String getAttribute2() {
		return this.attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return this.attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return this.attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return this.attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return this.attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	public String getAttribute7() {
		return this.attribute7;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	public String getAttribute8() {
		return this.attribute8;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	public String getAttribute9() {
		return this.attribute9;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	public BigDecimal getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(BigDecimal departmentId) {
		this.departmentId = departmentId;
	}

	public BigDecimal getDeptEntriesId() {
		return this.deptEntriesId;
	}

	public void setDeptEntriesId(BigDecimal deptEntriesId) {
		this.deptEntriesId = deptEntriesId;
	}

	public Date getDisableDate() {
		return this.disableDate;
	}

	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}

	public Date getEnableDate() {
		return this.enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(BigDecimal lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public BigDecimal getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public BigDecimal getParentDepartmentId() {
		return this.parentDepartmentId;
	}

	public void setParentDepartmentId(BigDecimal parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}