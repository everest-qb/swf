package sunspring.swf.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SWF_STATION_RULE_ALL database table.
 * 
 */
@Entity
@Table(name="SWF_STATION_RULE_ALL",schema="SWF")
@NamedQuery(name="SwfStationRuleAll.findAll", query="SELECT s FROM SwfStationRuleAll s")
public class SwfStationRuleAll implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@Column(name="AUTO_FLAG")
	private String autoFlag;

	@Column(name="CREATED_BY")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date creationDate;

	@Column(name="DEPT_ID")
	private BigDecimal deptId;

	@Column(name="EMP_ID")
	private BigDecimal empId;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="ITEM_COMP_ID")
	private BigDecimal itemCompId;

	@Column(name="JOB_ID")
	private BigDecimal jobId;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name="LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Column(name="RULE_NAME")
	private String ruleName;

	@Column(name="RULE_VALUE")
	private String ruleValue;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	@Id
	@SequenceGenerator(name="SWF_STATION_RULE_ALL_STATIONRULEID_GENERATOR", sequenceName="SWF_STATION_RULE_ALL_S1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SWF_STATION_RULE_ALL_STATIONRULEID_GENERATOR")
	@Column(name="STATION_RULE_ID")
	private BigDecimal stationRuleId;

	@Column(name="TOP_LEVEL")
	private BigDecimal topLevel;

	public SwfStationRuleAll() {
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

	public String getAutoFlag() {
		return this.autoFlag;
	}

	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
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

	public BigDecimal getDeptId() {
		return this.deptId;
	}

	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}

	public BigDecimal getEmpId() {
		return this.empId;
	}

	public void setEmpId(BigDecimal empId) {
		this.empId = empId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getItemCompId() {
		return this.itemCompId;
	}

	public void setItemCompId(BigDecimal itemCompId) {
		this.itemCompId = itemCompId;
	}

	public BigDecimal getJobId() {
		return this.jobId;
	}

	public void setJobId(BigDecimal jobId) {
		this.jobId = jobId;
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

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleValue() {
		return this.ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getStationRuleId() {
		return this.stationRuleId;
	}

	public void setStationRuleId(BigDecimal stationRuleId) {
		this.stationRuleId = stationRuleId;
	}

	public BigDecimal getTopLevel() {
		return this.topLevel;
	}

	public void setTopLevel(BigDecimal topLevel) {
		this.topLevel = topLevel;
	}

}