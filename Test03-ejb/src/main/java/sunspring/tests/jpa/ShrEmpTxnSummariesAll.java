package sunspring.tests.jpa;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SHR_EMP_TXN_SUMMARIES_ALL database table.
 * 
 */
@Entity
@Table(name="SHR_EMP_TXN_SUMMARIES_ALL",schema="SHR")
@NamedQuery(name="ShrEmpTxnSummariesAll.findAll", query="SELECT s FROM ShrEmpTxnSummariesAll s")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShrEmpTxnSummariesAll implements Serializable {
	@XmlTransient
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHR_EMP_TXN_SUMMARIES_ALL_GENERATOR", sequenceName="SHR_EMP_TXN_SUMMARIES_ALL_S1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHR_EMP_TXN_SUMMARIES_ALL_GENERATOR")
	@Column(name="SUMMARY_ID")
	private BigDecimal summaryId;
	
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

	@Column(name="CALENDAR_ID")
	private BigDecimal calendarId;

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

	@Column(name="EMPLOYEE_ID")
	private BigDecimal employeeId;

	@Column(name="EMPLOYEE_TYPE_CODE")
	private String employeeTypeCode;

	@Column(name="EMPLOYMENT_TYPE_CODE")
	private String employmentTypeCode;

	@Temporal(TemporalType.DATE)
	@Column(name="ENABLE_DATE")
	private Date enableDate;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPECT_EFFECTIVE_DATE")
	private Date expectEffectiveDate;

	@Column(name="HOUSEHOLD_TYPE_CODE")
	private String householdTypeCode;

	@Column(name="INTERDICT_CODE")
	private String interdictCode;

	@Temporal(TemporalType.DATE)
	@Column(name="INTERDICT_TERM")
	private Date interdictTerm;

	@Column(name="JOB_ID")
	private BigDecimal jobId;

	@Column(name="JOB_KIND_CODE")
	private String jobKindCode;

	@Column(name="JOB_LEVEL_CODE")
	private String jobLevelCode;

	@Column(name="JOB_TYPE_CODE")
	private String jobTypeCode;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_ASSESS_DATE")
	private Date lastAssessDate;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name="LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="LEAVE_OFFICE_DATE")
	private Date leaveOfficeDate;

	@Column(name="LEAVE_REMARK")
	private String leaveRemark;

	@Column(name="LEAVE_TYPE_CODE")
	private String leaveTypeCode;

	@Column(name="MNC_COMPANY_ID")
	private BigDecimal mncCompanyId;

	@Column(name="MNC_DEPT_ID_FM")
	private BigDecimal mncDeptIdFm;

	@Column(name="MNC_DEPT_ID_TO")
	private BigDecimal mncDeptIdTo;

	@Column(name="MNC_SHIFT_GROUP_ID")
	private BigDecimal mncShiftGroupId;

	@Column(name="MNC_SHIFT_ID")
	private BigDecimal mncShiftId;

	private String reason;

	@Temporal(TemporalType.DATE)
	@Column(name="REGULAR_DATE")
	private Date regularDate;

	@Column(name="RETURN_CARD_FLAG")
	private String returnCardFlag;

	@Column(name="SALARY_TYPE_ID")
	private BigDecimal salaryTypeId;

	@Temporal(TemporalType.DATE)
	@Column(name="SCHEDULE_REGULAR_DATE")
	private Date scheduleRegularDate;

	@Column(name="SHIFT_GROUP_ID")
	private BigDecimal shiftGroupId;

	@Column(name="SHIFT_ID")
	private BigDecimal shiftId;

	@Column(name="SOURCE_TYPE_CODE")
	private String sourceTypeCode;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Temporal(TemporalType.DATE)
	@Column(name="WK_RETRY_DATE")
	private Date wkRetryDate;

	@Column(name="WKTYPE_ID")
	private BigDecimal wktypeId;

	@Column(name="WORK_CLOTHES_FLAG")
	private String workClothesFlag;

	public ShrEmpTxnSummariesAll() {
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

	public BigDecimal getCalendarId() {
		return this.calendarId;
	}

	public void setCalendarId(BigDecimal calendarId) {
		this.calendarId = calendarId;
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

	public Date getDisableDate() {
		return this.disableDate;
	}

	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeTypeCode() {
		return this.employeeTypeCode;
	}

	public void setEmployeeTypeCode(String employeeTypeCode) {
		this.employeeTypeCode = employeeTypeCode;
	}

	public String getEmploymentTypeCode() {
		return this.employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public Date getEnableDate() {
		return this.enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public Date getExpectEffectiveDate() {
		return this.expectEffectiveDate;
	}

	public void setExpectEffectiveDate(Date expectEffectiveDate) {
		this.expectEffectiveDate = expectEffectiveDate;
	}

	public String getHouseholdTypeCode() {
		return this.householdTypeCode;
	}

	public void setHouseholdTypeCode(String householdTypeCode) {
		this.householdTypeCode = householdTypeCode;
	}

	public String getInterdictCode() {
		return this.interdictCode;
	}

	public void setInterdictCode(String interdictCode) {
		this.interdictCode = interdictCode;
	}

	public Date getInterdictTerm() {
		return this.interdictTerm;
	}

	public void setInterdictTerm(Date interdictTerm) {
		this.interdictTerm = interdictTerm;
	}

	public BigDecimal getJobId() {
		return this.jobId;
	}

	public void setJobId(BigDecimal jobId) {
		this.jobId = jobId;
	}

	public String getJobKindCode() {
		return this.jobKindCode;
	}

	public void setJobKindCode(String jobKindCode) {
		this.jobKindCode = jobKindCode;
	}

	public String getJobLevelCode() {
		return this.jobLevelCode;
	}

	public void setJobLevelCode(String jobLevelCode) {
		this.jobLevelCode = jobLevelCode;
	}

	public String getJobTypeCode() {
		return this.jobTypeCode;
	}

	public void setJobTypeCode(String jobTypeCode) {
		this.jobTypeCode = jobTypeCode;
	}

	public Date getLastAssessDate() {
		return this.lastAssessDate;
	}

	public void setLastAssessDate(Date lastAssessDate) {
		this.lastAssessDate = lastAssessDate;
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

	public Date getLeaveOfficeDate() {
		return this.leaveOfficeDate;
	}

	public void setLeaveOfficeDate(Date leaveOfficeDate) {
		this.leaveOfficeDate = leaveOfficeDate;
	}

	public String getLeaveRemark() {
		return this.leaveRemark;
	}

	public void setLeaveRemark(String leaveRemark) {
		this.leaveRemark = leaveRemark;
	}

	public String getLeaveTypeCode() {
		return this.leaveTypeCode;
	}

	public void setLeaveTypeCode(String leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}

	public BigDecimal getMncCompanyId() {
		return this.mncCompanyId;
	}

	public void setMncCompanyId(BigDecimal mncCompanyId) {
		this.mncCompanyId = mncCompanyId;
	}

	public BigDecimal getMncDeptIdFm() {
		return this.mncDeptIdFm;
	}

	public void setMncDeptIdFm(BigDecimal mncDeptIdFm) {
		this.mncDeptIdFm = mncDeptIdFm;
	}

	public BigDecimal getMncDeptIdTo() {
		return this.mncDeptIdTo;
	}

	public void setMncDeptIdTo(BigDecimal mncDeptIdTo) {
		this.mncDeptIdTo = mncDeptIdTo;
	}

	public BigDecimal getMncShiftGroupId() {
		return this.mncShiftGroupId;
	}

	public void setMncShiftGroupId(BigDecimal mncShiftGroupId) {
		this.mncShiftGroupId = mncShiftGroupId;
	}

	public BigDecimal getMncShiftId() {
		return this.mncShiftId;
	}

	public void setMncShiftId(BigDecimal mncShiftId) {
		this.mncShiftId = mncShiftId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getRegularDate() {
		return this.regularDate;
	}

	public void setRegularDate(Date regularDate) {
		this.regularDate = regularDate;
	}

	public String getReturnCardFlag() {
		return this.returnCardFlag;
	}

	public void setReturnCardFlag(String returnCardFlag) {
		this.returnCardFlag = returnCardFlag;
	}

	public BigDecimal getSalaryTypeId() {
		return this.salaryTypeId;
	}

	public void setSalaryTypeId(BigDecimal salaryTypeId) {
		this.salaryTypeId = salaryTypeId;
	}

	public Date getScheduleRegularDate() {
		return this.scheduleRegularDate;
	}

	public void setScheduleRegularDate(Date scheduleRegularDate) {
		this.scheduleRegularDate = scheduleRegularDate;
	}

	public BigDecimal getShiftGroupId() {
		return this.shiftGroupId;
	}

	public void setShiftGroupId(BigDecimal shiftGroupId) {
		this.shiftGroupId = shiftGroupId;
	}

	public BigDecimal getShiftId() {
		return this.shiftId;
	}

	public void setShiftId(BigDecimal shiftId) {
		this.shiftId = shiftId;
	}

	public String getSourceTypeCode() {
		return this.sourceTypeCode;
	}

	public void setSourceTypeCode(String sourceTypeCode) {
		this.sourceTypeCode = sourceTypeCode;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public BigDecimal getSummaryId() {
		return this.summaryId;
	}

	public void setSummaryId(BigDecimal summaryId) {
		this.summaryId = summaryId;
	}

	public Date getWkRetryDate() {
		return this.wkRetryDate;
	}

	public void setWkRetryDate(Date wkRetryDate) {
		this.wkRetryDate = wkRetryDate;
	}

	public BigDecimal getWktypeId() {
		return this.wktypeId;
	}

	public void setWktypeId(BigDecimal wktypeId) {
		this.wktypeId = wktypeId;
	}

	public String getWorkClothesFlag() {
		return this.workClothesFlag;
	}

	public void setWorkClothesFlag(String workClothesFlag) {
		this.workClothesFlag = workClothesFlag;
	}

}