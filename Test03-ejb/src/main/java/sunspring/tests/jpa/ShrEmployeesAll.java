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
 * The persistent class for the SHR_EMPLOYEES_ALL database table.
 * 
 */
@Entity
@Table(name="SHR_EMPLOYEES_ALL",schema="SHR")
@NamedQuery(name="ShrEmployeesAll.findAll", query="SELECT s FROM ShrEmployeesAll s")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShrEmployeesAll implements Serializable {
	@XmlTransient
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHR_EMPLOYEES_ALL_GENERATOR", sequenceName="SHR_EMPLOYEES_ALL_S1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHR_EMPLOYEES_ALL_GENERATOR")
	@Column(name="EMPLOYEE_ID")
	private BigDecimal employeeId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ARMY_IN_DATE")
	private Date armyInDate;

	@Temporal(TemporalType.DATE)
	@Column(name="ARMY_OUT_DATE")
	private Date armyOutDate;

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

	
	@Column(name="BANK_ID")
	private BigDecimal bankId;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name="BLOOD_TYPE_CODE")
	private String bloodTypeCode;

	@Column(name="BORN_FLAG")
	private String bornFlag;

	@Column(name="BRANK_ACCOUNT")
	private String brankAccount;

	@Column(name="CELLPHONE_NUMBER")
	private String cellphoneNumber;

	@Column(name="CELLPHONE_SHORT_NUMBER")
	private String cellphoneShortNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="CLEAR_DATE")
	private Date clearDate;

	@Column(name="COMPANY_EMAIL")
	private String companyEmail;

	@Column(name="COMPANY_ID")
	private BigDecimal companyId;

	@Column(name="COOPERATIVE_EDUCATION_YEAR")
	private BigDecimal cooperativeEducationYear;

	@Column(name="CREATED_BY")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date creationDate;

	@Column(name="CTBC_BANK_FLAG")
	private String ctbcBankFlag;

	@Column(name="CURRENT_YEAR_INTW_DAY")
	private BigDecimal currentYearIntwDay;

	private String description;

	@Column(name="E_MAIL")
	private String eMail;

	@Column(name="EDUCATIONAL_FLAG")
	private String educationalFlag;

	@Column(name="EMERGENCY_CONTACT_NUMBER_1")
	private String emergencyContactNumber1;

	@Column(name="EMERGENCY_CONTACT_NUMBER_2")
	private String emergencyContactNumber2;

	@Column(name="EMP_NOTICE_FLAG")
	private String empNoticeFlag;

	@Column(name="EMPLOYEE_KIND_CODE")
	private String employeeKindCode;

	@Column(name="EMPLOYEE_NAME")
	private String employeeName;

	@Column(name="EMPLOYEE_NUMBER")
	private String employeeNumber;

	@Column(name="EMPLOYEE_SORT_CODE")
	private String employeeSortCode;

	@Column(name="EMPLOYEE_TYPE_CODE")
	private String employeeTypeCode;

	@Column(name="EMPLOYEE_US_NAME")
	private String employeeUsName;

	@Column(name="EMPLOYMENT_TYPE_CODE")
	private String employmentTypeCode;

	@Column(name="ENTER_REASON")
	private String enterReason;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPAND_DATE")
	private Date expandDate;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPECT_POSITIVE_DATE")
	private Date expectPositiveDate;

	private BigDecimal extension;

	@Column(name="FAMILY_FLAG")
	private String familyFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="FIRST_INTW_DATE")
	private Date firstIntwDate;

	@Temporal(TemporalType.DATE)
	@Column(name="FORE_ENTER_DATE")
	private Date foreEnterDate;

	@Column(name="GENERAL_FLAG")
	private String generalFlag;

	@Temporal(TemporalType.DATE)
	private Date getoutdate;

	@Column(name="GUARANTEE_FLAG")
	private String guaranteeFlag;

	@Column(name="HEALTH_FLAG")
	private String healthFlag;

	@Column(name="HEALTH_REPORT_FLAG")
	private String healthReportFlag;

	private BigDecimal height;

	@Temporal(TemporalType.DATE)
	@Column(name="HIRE_DATE")
	private Date hireDate;

	@Column(name="HIRE_DESCRIPTION")
	private String hireDescription;

	@Column(name="HIRE_WAY_CODE")
	private String hireWayCode;

	@Column(name="HIREWAY_ID")
	private BigDecimal hirewayId;

	@Column(name="HOURSEHOLD_REGISTER_ADDERSS")
	private String hourseholdRegisterAdderss;

	@Column(name="HOURSEHOLD_REGISTER_PHONE")
	private String hourseholdRegisterPhone;

	@Column(name="HOUSEHOLD_TYPE_CODE")
	private String householdTypeCode;

	@Column(name="IDENTIFICATION_NUMBER_FLAG")
	private String identificationNumberFlag;

	@Column(name="IDENTITY_NO")
	private String identityNo;

	@Column(name="IDENTITY_NO_ORG")
	private String identityNoOrg;

	@Column(name="INDIVIDUAL_DATA_FLAG")
	private String individualDataFlag;

	@Column(name="INFO_CONFIRM_FLAG")
	private String infoConfirmFlag;

	@Column(name="INSURANCE_FLAG")
	private String insuranceFlag;

	@Column(name="INTRODUCTOR_ID")
	private BigDecimal introductorId;

	@Column(name="JOB_CERTIFICATE_FLAG")
	private String jobCertificateFlag;

	@Column(name="JOB_GRADE_CODE")
	private String jobGradeCode;

	@Column(name="JOB_KIND_CODE")
	private String jobKindCode;

	@Column(name="JOB_TYPE_CODE")
	private String jobTypeCode;

	@Column(name="LABOR_RETIRE_FLAG")
	private String laborRetireFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name="LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Column(name="LEAVE_REMARK")
	private String leaveRemark;

	@Temporal(TemporalType.DATE)
	@Column(name="LOCATION_DATE")
	private Date locationDate;

	@Column(name="LOCATION_DEPT")
	private String locationDept;

	@Column(name="LOCATION_JOB_LEVEL")
	private String locationJobLevel;

	@Column(name="LOCATION_JOB_TYPE")
	private String locationJobType;

	@Temporal(TemporalType.DATE)
	@Column(name="LOCATION_RETURN_DATE")
	private Date locationReturnDate;

	@Column(name="LOCATION_TYPE_CODE")
	private String locationTypeCode;

	@Column(name="LODGING_TYPE_CODE")
	private String lodgingTypeCode;

	@Column(name="MANAGER_EMPLOYEE_ID")
	private BigDecimal managerEmployeeId;

	@Column(name="MARRIAGE_STATUS_CODE")
	private String marriageStatusCode;

	@Column(name="MILITARY_FLAG")
	private String militaryFlag;

	@Column(name="MORALITY_FLAG")
	private String moralityFlag;

	@Column(name="NATIONAL_ATTRIBUTE_CATEGORY")
	private String nationalAttributeCategory;

	@Column(name="NATIONAL_ATTRIBUTE1")
	private String nationalAttribute1;

	@Column(name="NATIONAL_ATTRIBUTE10")
	private String nationalAttribute10;

	@Column(name="NATIONAL_ATTRIBUTE11")
	private String nationalAttribute11;

	@Column(name="NATIONAL_ATTRIBUTE12")
	private String nationalAttribute12;

	@Column(name="NATIONAL_ATTRIBUTE13")
	private String nationalAttribute13;

	@Column(name="NATIONAL_ATTRIBUTE14")
	private String nationalAttribute14;

	@Column(name="NATIONAL_ATTRIBUTE15")
	private String nationalAttribute15;

	@Column(name="NATIONAL_ATTRIBUTE2")
	private String nationalAttribute2;

	@Column(name="NATIONAL_ATTRIBUTE3")
	private String nationalAttribute3;

	@Column(name="NATIONAL_ATTRIBUTE4")
	private String nationalAttribute4;

	@Column(name="NATIONAL_ATTRIBUTE5")
	private String nationalAttribute5;

	@Column(name="NATIONAL_ATTRIBUTE6")
	private String nationalAttribute6;

	@Column(name="NATIONAL_ATTRIBUTE7")
	private String nationalAttribute7;

	@Column(name="NATIONAL_ATTRIBUTE8")
	private String nationalAttribute8;

	@Column(name="NATIONAL_ATTRIBUTE9")
	private String nationalAttribute9;

	@Column(name="NEW_ADD_SUB_SENIORITY")
	private BigDecimal newAddSubSeniority;

	@Temporal(TemporalType.DATE)
	@Column(name="NEW_SEN_DATE")
	private Date newSenDate;

	@Column(name="OLD_ADD_SUB_SENIORITY")
	private BigDecimal oldAddSubSeniority;

	@Column(name="OTHER_ATTACH_DOC")
	private String otherAttachDoc;

	@Column(name="OTHER_ATTACH_DOC_FLAG")
	private String otherAttachDocFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="OUT_DATE")
	private Date outDate;

	@Column(name="OUT_REASON")
	private String outReason;

	@Temporal(TemporalType.DATE)
	@Column(name="PASSPORT_DATE")
	private Date passportDate;

	@Column(name="PASSPORT_NO")
	private String passportNo;

	@Column(name="PAY_BANK_FLAG")
	private String payBankFlag;

	@Column(name="PAY_TYPE_CODE")
	private String payTypeCode;

	@Column(name="PEOPLE_CODE")
	private String peopleCode;

	@Column(name="PHOTO_FLAG")
	private String photoFlag;

	@Temporal(TemporalType.DATE)
	private Date placeindate;

	@Column(name="PLANT_TICKET_AMT")
	private BigDecimal plantTicketAmt;

	private String politics;

	@Column(name="POSTAL_ADDRESS")
	private String postalAddress;

	@Column(name="POSTAL_AREAID")
	private BigDecimal postalAreaid;

	@Column(name="POSTAL_CITY")
	private String postalCity;

	@Column(name="POSTAL_PHONE_NUMBER")
	private String postalPhoneNumber;

	@Column(name="PROBATION_TYPE_CODE")
	private String probationTypeCode;

	@Column(name="RECOMMENDER_ID")
	private BigDecimal recommenderId;

	@Column(name="REFERENCE_NUMBER")
	private String referenceNumber;

	@Column(name="RESIDENCE_PERMIT")
	private String residencePermit;

	@Column(name="SALARY_FLAG")
	private String salaryFlag;

	@Column(name="SECRET_FLAG")
	private String secretFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="SEN_DATE")
	private Date senDate;

	@Column(name="SEX_CODE")
	private String sexCode;

	@Column(name="SIGN_UP_FORM_FLAG")
	private String signUpFormFlag;

	@Column(name="SIMPLIFIED_NAME")
	private String simplifiedName;

	@Column(name="SIMPLIFIED_REGISTER_ADDRESS")
	private String simplifiedRegisterAddress;

	@Column(name="STATUS_CODE")
	private String statusCode;

	@Temporal(TemporalType.DATE)
	@Column(name="SUPPLY_END_DATE")
	private Date supplyEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="SUPPLY_START_DATE")
	private Date supplyStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name="TAKE_OFFICE_DATE")
	private Date takeOfficeDate;

	@Column(name="TEST_FLAG")
	private String testFlag;

	@Column(name="TMP_LODGING_COUNT")
	private BigDecimal tmpLodgingCount;

	@Column(name="TMP_LODGING_DAYS")
	private BigDecimal tmpLodgingDays;

	@Column(name="UNIFORM_DEDUCT_FLAG")
	private String uniformDeductFlag;

	private BigDecimal weight;

	@OneToOne
	@JoinTable(joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID"), schema = "SHR", name = "SHR_EMP_TXN_SUMMARIES_ALL", inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID"))
	private ShrEmpTxnSummariesAll summary;
	
	public ShrEmployeesAll() {
	}

	public Date getArmyInDate() {
		return this.armyInDate;
	}

	public void setArmyInDate(Date armyInDate) {
		this.armyInDate = armyInDate;
	}

	public Date getArmyOutDate() {
		return this.armyOutDate;
	}

	public void setArmyOutDate(Date armyOutDate) {
		this.armyOutDate = armyOutDate;
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

	public BigDecimal getBankId() {
		return this.bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBloodTypeCode() {
		return this.bloodTypeCode;
	}

	public void setBloodTypeCode(String bloodTypeCode) {
		this.bloodTypeCode = bloodTypeCode;
	}

	public String getBornFlag() {
		return this.bornFlag;
	}

	public void setBornFlag(String bornFlag) {
		this.bornFlag = bornFlag;
	}

	public String getBrankAccount() {
		return this.brankAccount;
	}

	public void setBrankAccount(String brankAccount) {
		this.brankAccount = brankAccount;
	}

	public String getCellphoneNumber() {
		return this.cellphoneNumber;
	}

	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}

	public String getCellphoneShortNumber() {
		return this.cellphoneShortNumber;
	}

	public void setCellphoneShortNumber(String cellphoneShortNumber) {
		this.cellphoneShortNumber = cellphoneShortNumber;
	}

	public Date getClearDate() {
		return this.clearDate;
	}

	public void setClearDate(Date clearDate) {
		this.clearDate = clearDate;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public BigDecimal getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCooperativeEducationYear() {
		return this.cooperativeEducationYear;
	}

	public void setCooperativeEducationYear(BigDecimal cooperativeEducationYear) {
		this.cooperativeEducationYear = cooperativeEducationYear;
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

	public String getCtbcBankFlag() {
		return this.ctbcBankFlag;
	}

	public void setCtbcBankFlag(String ctbcBankFlag) {
		this.ctbcBankFlag = ctbcBankFlag;
	}

	public BigDecimal getCurrentYearIntwDay() {
		return this.currentYearIntwDay;
	}

	public void setCurrentYearIntwDay(BigDecimal currentYearIntwDay) {
		this.currentYearIntwDay = currentYearIntwDay;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getEducationalFlag() {
		return this.educationalFlag;
	}

	public void setEducationalFlag(String educationalFlag) {
		this.educationalFlag = educationalFlag;
	}

	public String getEmergencyContactNumber1() {
		return this.emergencyContactNumber1;
	}

	public void setEmergencyContactNumber1(String emergencyContactNumber1) {
		this.emergencyContactNumber1 = emergencyContactNumber1;
	}

	public String getEmergencyContactNumber2() {
		return this.emergencyContactNumber2;
	}

	public void setEmergencyContactNumber2(String emergencyContactNumber2) {
		this.emergencyContactNumber2 = emergencyContactNumber2;
	}

	public String getEmpNoticeFlag() {
		return this.empNoticeFlag;
	}

	public void setEmpNoticeFlag(String empNoticeFlag) {
		this.empNoticeFlag = empNoticeFlag;
	}

	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeKindCode() {
		return this.employeeKindCode;
	}

	public void setEmployeeKindCode(String employeeKindCode) {
		this.employeeKindCode = employeeKindCode;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeSortCode() {
		return this.employeeSortCode;
	}

	public void setEmployeeSortCode(String employeeSortCode) {
		this.employeeSortCode = employeeSortCode;
	}

	public String getEmployeeTypeCode() {
		return this.employeeTypeCode;
	}

	public void setEmployeeTypeCode(String employeeTypeCode) {
		this.employeeTypeCode = employeeTypeCode;
	}

	public String getEmployeeUsName() {
		return this.employeeUsName;
	}

	public void setEmployeeUsName(String employeeUsName) {
		this.employeeUsName = employeeUsName;
	}

	public String getEmploymentTypeCode() {
		return this.employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	public Date getExpandDate() {
		return this.expandDate;
	}

	public void setExpandDate(Date expandDate) {
		this.expandDate = expandDate;
	}

	public Date getExpectPositiveDate() {
		return this.expectPositiveDate;
	}

	public void setExpectPositiveDate(Date expectPositiveDate) {
		this.expectPositiveDate = expectPositiveDate;
	}

	public BigDecimal getExtension() {
		return this.extension;
	}

	public void setExtension(BigDecimal extension) {
		this.extension = extension;
	}

	public String getFamilyFlag() {
		return this.familyFlag;
	}

	public void setFamilyFlag(String familyFlag) {
		this.familyFlag = familyFlag;
	}

	public Date getFirstIntwDate() {
		return this.firstIntwDate;
	}

	public void setFirstIntwDate(Date firstIntwDate) {
		this.firstIntwDate = firstIntwDate;
	}

	public Date getForeEnterDate() {
		return this.foreEnterDate;
	}

	public void setForeEnterDate(Date foreEnterDate) {
		this.foreEnterDate = foreEnterDate;
	}

	public String getGeneralFlag() {
		return this.generalFlag;
	}

	public void setGeneralFlag(String generalFlag) {
		this.generalFlag = generalFlag;
	}

	public Date getGetoutdate() {
		return this.getoutdate;
	}

	public void setGetoutdate(Date getoutdate) {
		this.getoutdate = getoutdate;
	}

	public String getGuaranteeFlag() {
		return this.guaranteeFlag;
	}

	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	public String getHealthFlag() {
		return this.healthFlag;
	}

	public void setHealthFlag(String healthFlag) {
		this.healthFlag = healthFlag;
	}

	public String getHealthReportFlag() {
		return this.healthReportFlag;
	}

	public void setHealthReportFlag(String healthReportFlag) {
		this.healthReportFlag = healthReportFlag;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getHireDescription() {
		return this.hireDescription;
	}

	public void setHireDescription(String hireDescription) {
		this.hireDescription = hireDescription;
	}

	public String getHireWayCode() {
		return this.hireWayCode;
	}

	public void setHireWayCode(String hireWayCode) {
		this.hireWayCode = hireWayCode;
	}

	public BigDecimal getHirewayId() {
		return this.hirewayId;
	}

	public void setHirewayId(BigDecimal hirewayId) {
		this.hirewayId = hirewayId;
	}

	public String getHourseholdRegisterAdderss() {
		return this.hourseholdRegisterAdderss;
	}

	public void setHourseholdRegisterAdderss(String hourseholdRegisterAdderss) {
		this.hourseholdRegisterAdderss = hourseholdRegisterAdderss;
	}

	public String getHourseholdRegisterPhone() {
		return this.hourseholdRegisterPhone;
	}

	public void setHourseholdRegisterPhone(String hourseholdRegisterPhone) {
		this.hourseholdRegisterPhone = hourseholdRegisterPhone;
	}

	public String getHouseholdTypeCode() {
		return this.householdTypeCode;
	}

	public void setHouseholdTypeCode(String householdTypeCode) {
		this.householdTypeCode = householdTypeCode;
	}

	public String getIdentificationNumberFlag() {
		return this.identificationNumberFlag;
	}

	public void setIdentificationNumberFlag(String identificationNumberFlag) {
		this.identificationNumberFlag = identificationNumberFlag;
	}

	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getIdentityNoOrg() {
		return this.identityNoOrg;
	}

	public void setIdentityNoOrg(String identityNoOrg) {
		this.identityNoOrg = identityNoOrg;
	}

	public String getIndividualDataFlag() {
		return this.individualDataFlag;
	}

	public void setIndividualDataFlag(String individualDataFlag) {
		this.individualDataFlag = individualDataFlag;
	}

	public String getInfoConfirmFlag() {
		return this.infoConfirmFlag;
	}

	public void setInfoConfirmFlag(String infoConfirmFlag) {
		this.infoConfirmFlag = infoConfirmFlag;
	}

	public String getInsuranceFlag() {
		return this.insuranceFlag;
	}

	public void setInsuranceFlag(String insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}

	public BigDecimal getIntroductorId() {
		return this.introductorId;
	}

	public void setIntroductorId(BigDecimal introductorId) {
		this.introductorId = introductorId;
	}

	public String getJobCertificateFlag() {
		return this.jobCertificateFlag;
	}

	public void setJobCertificateFlag(String jobCertificateFlag) {
		this.jobCertificateFlag = jobCertificateFlag;
	}

	public String getJobGradeCode() {
		return this.jobGradeCode;
	}

	public void setJobGradeCode(String jobGradeCode) {
		this.jobGradeCode = jobGradeCode;
	}

	public String getJobKindCode() {
		return this.jobKindCode;
	}

	public void setJobKindCode(String jobKindCode) {
		this.jobKindCode = jobKindCode;
	}

	public String getJobTypeCode() {
		return this.jobTypeCode;
	}

	public void setJobTypeCode(String jobTypeCode) {
		this.jobTypeCode = jobTypeCode;
	}

	public String getLaborRetireFlag() {
		return this.laborRetireFlag;
	}

	public void setLaborRetireFlag(String laborRetireFlag) {
		this.laborRetireFlag = laborRetireFlag;
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

	public String getLeaveRemark() {
		return this.leaveRemark;
	}

	public void setLeaveRemark(String leaveRemark) {
		this.leaveRemark = leaveRemark;
	}

	public Date getLocationDate() {
		return this.locationDate;
	}

	public void setLocationDate(Date locationDate) {
		this.locationDate = locationDate;
	}

	public String getLocationDept() {
		return this.locationDept;
	}

	public void setLocationDept(String locationDept) {
		this.locationDept = locationDept;
	}

	public String getLocationJobLevel() {
		return this.locationJobLevel;
	}

	public void setLocationJobLevel(String locationJobLevel) {
		this.locationJobLevel = locationJobLevel;
	}

	public String getLocationJobType() {
		return this.locationJobType;
	}

	public void setLocationJobType(String locationJobType) {
		this.locationJobType = locationJobType;
	}

	public Date getLocationReturnDate() {
		return this.locationReturnDate;
	}

	public void setLocationReturnDate(Date locationReturnDate) {
		this.locationReturnDate = locationReturnDate;
	}

	public String getLocationTypeCode() {
		return this.locationTypeCode;
	}

	public void setLocationTypeCode(String locationTypeCode) {
		this.locationTypeCode = locationTypeCode;
	}

	public String getLodgingTypeCode() {
		return this.lodgingTypeCode;
	}

	public void setLodgingTypeCode(String lodgingTypeCode) {
		this.lodgingTypeCode = lodgingTypeCode;
	}

	public BigDecimal getManagerEmployeeId() {
		return this.managerEmployeeId;
	}

	public void setManagerEmployeeId(BigDecimal managerEmployeeId) {
		this.managerEmployeeId = managerEmployeeId;
	}

	public String getMarriageStatusCode() {
		return this.marriageStatusCode;
	}

	public void setMarriageStatusCode(String marriageStatusCode) {
		this.marriageStatusCode = marriageStatusCode;
	}

	public String getMilitaryFlag() {
		return this.militaryFlag;
	}

	public void setMilitaryFlag(String militaryFlag) {
		this.militaryFlag = militaryFlag;
	}

	public String getMoralityFlag() {
		return this.moralityFlag;
	}

	public void setMoralityFlag(String moralityFlag) {
		this.moralityFlag = moralityFlag;
	}

	public String getNationalAttributeCategory() {
		return this.nationalAttributeCategory;
	}

	public void setNationalAttributeCategory(String nationalAttributeCategory) {
		this.nationalAttributeCategory = nationalAttributeCategory;
	}

	public String getNationalAttribute1() {
		return this.nationalAttribute1;
	}

	public void setNationalAttribute1(String nationalAttribute1) {
		this.nationalAttribute1 = nationalAttribute1;
	}

	public String getNationalAttribute10() {
		return this.nationalAttribute10;
	}

	public void setNationalAttribute10(String nationalAttribute10) {
		this.nationalAttribute10 = nationalAttribute10;
	}

	public String getNationalAttribute11() {
		return this.nationalAttribute11;
	}

	public void setNationalAttribute11(String nationalAttribute11) {
		this.nationalAttribute11 = nationalAttribute11;
	}

	public String getNationalAttribute12() {
		return this.nationalAttribute12;
	}

	public void setNationalAttribute12(String nationalAttribute12) {
		this.nationalAttribute12 = nationalAttribute12;
	}

	public String getNationalAttribute13() {
		return this.nationalAttribute13;
	}

	public void setNationalAttribute13(String nationalAttribute13) {
		this.nationalAttribute13 = nationalAttribute13;
	}

	public String getNationalAttribute14() {
		return this.nationalAttribute14;
	}

	public void setNationalAttribute14(String nationalAttribute14) {
		this.nationalAttribute14 = nationalAttribute14;
	}

	public String getNationalAttribute15() {
		return this.nationalAttribute15;
	}

	public void setNationalAttribute15(String nationalAttribute15) {
		this.nationalAttribute15 = nationalAttribute15;
	}

	public String getNationalAttribute2() {
		return this.nationalAttribute2;
	}

	public void setNationalAttribute2(String nationalAttribute2) {
		this.nationalAttribute2 = nationalAttribute2;
	}

	public String getNationalAttribute3() {
		return this.nationalAttribute3;
	}

	public void setNationalAttribute3(String nationalAttribute3) {
		this.nationalAttribute3 = nationalAttribute3;
	}

	public String getNationalAttribute4() {
		return this.nationalAttribute4;
	}

	public void setNationalAttribute4(String nationalAttribute4) {
		this.nationalAttribute4 = nationalAttribute4;
	}

	public String getNationalAttribute5() {
		return this.nationalAttribute5;
	}

	public void setNationalAttribute5(String nationalAttribute5) {
		this.nationalAttribute5 = nationalAttribute5;
	}

	public String getNationalAttribute6() {
		return this.nationalAttribute6;
	}

	public void setNationalAttribute6(String nationalAttribute6) {
		this.nationalAttribute6 = nationalAttribute6;
	}

	public String getNationalAttribute7() {
		return this.nationalAttribute7;
	}

	public void setNationalAttribute7(String nationalAttribute7) {
		this.nationalAttribute7 = nationalAttribute7;
	}

	public String getNationalAttribute8() {
		return this.nationalAttribute8;
	}

	public void setNationalAttribute8(String nationalAttribute8) {
		this.nationalAttribute8 = nationalAttribute8;
	}

	public String getNationalAttribute9() {
		return this.nationalAttribute9;
	}

	public void setNationalAttribute9(String nationalAttribute9) {
		this.nationalAttribute9 = nationalAttribute9;
	}

	public BigDecimal getNewAddSubSeniority() {
		return this.newAddSubSeniority;
	}

	public void setNewAddSubSeniority(BigDecimal newAddSubSeniority) {
		this.newAddSubSeniority = newAddSubSeniority;
	}

	public Date getNewSenDate() {
		return this.newSenDate;
	}

	public void setNewSenDate(Date newSenDate) {
		this.newSenDate = newSenDate;
	}

	public BigDecimal getOldAddSubSeniority() {
		return this.oldAddSubSeniority;
	}

	public void setOldAddSubSeniority(BigDecimal oldAddSubSeniority) {
		this.oldAddSubSeniority = oldAddSubSeniority;
	}

	public String getOtherAttachDoc() {
		return this.otherAttachDoc;
	}

	public void setOtherAttachDoc(String otherAttachDoc) {
		this.otherAttachDoc = otherAttachDoc;
	}

	public String getOtherAttachDocFlag() {
		return this.otherAttachDocFlag;
	}

	public void setOtherAttachDocFlag(String otherAttachDocFlag) {
		this.otherAttachDocFlag = otherAttachDocFlag;
	}

	public Date getOutDate() {
		return this.outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getOutReason() {
		return this.outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public Date getPassportDate() {
		return this.passportDate;
	}

	public void setPassportDate(Date passportDate) {
		this.passportDate = passportDate;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPayBankFlag() {
		return this.payBankFlag;
	}

	public void setPayBankFlag(String payBankFlag) {
		this.payBankFlag = payBankFlag;
	}

	public String getPayTypeCode() {
		return this.payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public String getPeopleCode() {
		return this.peopleCode;
	}

	public void setPeopleCode(String peopleCode) {
		this.peopleCode = peopleCode;
	}

	public String getPhotoFlag() {
		return this.photoFlag;
	}

	public void setPhotoFlag(String photoFlag) {
		this.photoFlag = photoFlag;
	}

	public Date getPlaceindate() {
		return this.placeindate;
	}

	public void setPlaceindate(Date placeindate) {
		this.placeindate = placeindate;
	}

	public BigDecimal getPlantTicketAmt() {
		return this.plantTicketAmt;
	}

	public void setPlantTicketAmt(BigDecimal plantTicketAmt) {
		this.plantTicketAmt = plantTicketAmt;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public BigDecimal getPostalAreaid() {
		return this.postalAreaid;
	}

	public void setPostalAreaid(BigDecimal postalAreaid) {
		this.postalAreaid = postalAreaid;
	}

	public String getPostalCity() {
		return this.postalCity;
	}

	public void setPostalCity(String postalCity) {
		this.postalCity = postalCity;
	}

	public String getPostalPhoneNumber() {
		return this.postalPhoneNumber;
	}

	public void setPostalPhoneNumber(String postalPhoneNumber) {
		this.postalPhoneNumber = postalPhoneNumber;
	}

	public String getProbationTypeCode() {
		return this.probationTypeCode;
	}

	public void setProbationTypeCode(String probationTypeCode) {
		this.probationTypeCode = probationTypeCode;
	}

	public BigDecimal getRecommenderId() {
		return this.recommenderId;
	}

	public void setRecommenderId(BigDecimal recommenderId) {
		this.recommenderId = recommenderId;
	}

	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getResidencePermit() {
		return this.residencePermit;
	}

	public void setResidencePermit(String residencePermit) {
		this.residencePermit = residencePermit;
	}

	public String getSalaryFlag() {
		return this.salaryFlag;
	}

	public void setSalaryFlag(String salaryFlag) {
		this.salaryFlag = salaryFlag;
	}

	public String getSecretFlag() {
		return this.secretFlag;
	}

	public void setSecretFlag(String secretFlag) {
		this.secretFlag = secretFlag;
	}

	public Date getSenDate() {
		return this.senDate;
	}

	public void setSenDate(Date senDate) {
		this.senDate = senDate;
	}

	public String getSexCode() {
		return this.sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getSignUpFormFlag() {
		return this.signUpFormFlag;
	}

	public void setSignUpFormFlag(String signUpFormFlag) {
		this.signUpFormFlag = signUpFormFlag;
	}

	public String getSimplifiedName() {
		return this.simplifiedName;
	}

	public void setSimplifiedName(String simplifiedName) {
		this.simplifiedName = simplifiedName;
	}

	public String getSimplifiedRegisterAddress() {
		return this.simplifiedRegisterAddress;
	}

	public void setSimplifiedRegisterAddress(String simplifiedRegisterAddress) {
		this.simplifiedRegisterAddress = simplifiedRegisterAddress;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getSupplyEndDate() {
		return this.supplyEndDate;
	}

	public void setSupplyEndDate(Date supplyEndDate) {
		this.supplyEndDate = supplyEndDate;
	}

	public Date getSupplyStartDate() {
		return this.supplyStartDate;
	}

	public void setSupplyStartDate(Date supplyStartDate) {
		this.supplyStartDate = supplyStartDate;
	}

	public Date getTakeOfficeDate() {
		return this.takeOfficeDate;
	}

	public void setTakeOfficeDate(Date takeOfficeDate) {
		this.takeOfficeDate = takeOfficeDate;
	}

	public String getTestFlag() {
		return this.testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}

	public BigDecimal getTmpLodgingCount() {
		return this.tmpLodgingCount;
	}

	public void setTmpLodgingCount(BigDecimal tmpLodgingCount) {
		this.tmpLodgingCount = tmpLodgingCount;
	}

	public BigDecimal getTmpLodgingDays() {
		return this.tmpLodgingDays;
	}

	public void setTmpLodgingDays(BigDecimal tmpLodgingDays) {
		this.tmpLodgingDays = tmpLodgingDays;
	}

	public String getUniformDeductFlag() {
		return this.uniformDeductFlag;
	}

	public void setUniformDeductFlag(String uniformDeductFlag) {
		this.uniformDeductFlag = uniformDeductFlag;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public ShrEmpTxnSummariesAll getSummary() {
		return summary;
	}

	public void setSummary(ShrEmpTxnSummariesAll summary) {
		this.summary = summary;
	}

}