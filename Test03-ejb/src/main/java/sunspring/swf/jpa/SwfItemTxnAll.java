package sunspring.swf.jpa;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SWF_ITEM_TXN_ALL database table.
 * 
 */
@Entity
@Table(name="SWF_ITEM_TXN_ALL",schema="SWF")
@NamedQuery(name="SwfItemTxnAll.findAll", query="SELECT s FROM SwfItemTxnAll s")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SwfItemTxnAll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="APPROVE_COMMENT")
	private String approveComment;

	@Column(name="APPROVE_TYPE")
	private String approveType;

	@Temporal(TemporalType.DATE)
	@Column(name="APPROVED_DATE")
	private Date approvedDate;

	@Column(name="APPROVER_ID")
	private BigDecimal approverId;

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

	@Column(name="CREATED_BY")
	private BigDecimal createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE")
	private Date creationDate;

	@Column(name="DELEG_ID")
	private BigDecimal delegId;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

/*	@Transient
	private BigDecimal hdrId;*/
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "HDR_ID", table = "SWF_ITEM_TXN_ALL")
	private SwfItemHdrAll itemHdr;

	@Id
	@SequenceGenerator(name="SWF_ITEM_TXN_ALL_ITEMTXNID_GENERATOR", sequenceName="SWF_ITEM_TXN_ALL_S1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SWF_ITEM_TXN_ALL_ITEMTXNID_GENERATOR")
	@Column(name="ITEM_TXN_ID")
	private BigDecimal itemTxnId;

	@Column(name="LAST_TXN_ID")
	private BigDecimal lastTxnId;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="LAST_UPDATE_LOGIN")
	private BigDecimal lastUpdateLogin;

	@Column(name="LAST_UPDATED_BY")
	private BigDecimal lastUpdatedBy;

	@Column(name="NEXT_TXN_ID")
	private BigDecimal nextTxnId;

	@Column(name="PROCESS_FLAG")
	private BigDecimal processFlag;

	@Temporal(TemporalType.DATE)
	@Column(name="PROCESS_TIME")
	private Date processTime;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	@Column(name="STATION_ID")
	private BigDecimal stationId;

	public SwfItemTxnAll() {
	}

	public String getApproveComment() {
		return this.approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getApproveType() {
		return this.approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public BigDecimal getApproverId() {
		return this.approverId;
	}

	public void setApproverId(BigDecimal approverId) {
		this.approverId = approverId;
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

	public BigDecimal getDelegId() {
		return this.delegId;
	}

	public void setDelegId(BigDecimal delegId) {
		this.delegId = delegId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/*public BigDecimal getHdrId() {
		return this.hdrId;
	}

	public void setHdrId(BigDecimal hdrId) {
		this.hdrId = hdrId;
	}*/

	public BigDecimal getItemTxnId() {
		return this.itemTxnId;
	}

	public void setItemTxnId(BigDecimal itemTxnId) {
		this.itemTxnId = itemTxnId;
	}

	public BigDecimal getLastTxnId() {
		return this.lastTxnId;
	}

	public void setLastTxnId(BigDecimal lastTxnId) {
		this.lastTxnId = lastTxnId;
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

	public BigDecimal getNextTxnId() {
		return this.nextTxnId;
	}

	public void setNextTxnId(BigDecimal nextTxnId) {
		this.nextTxnId = nextTxnId;
	}

	public BigDecimal getProcessFlag() {
		return this.processFlag;
	}

	public void setProcessFlag(BigDecimal processFlag) {
		this.processFlag = processFlag;
	}

	public Date getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getStationId() {
		return this.stationId;
	}

	public void setStationId(BigDecimal stationId) {
		this.stationId = stationId;
	}

	public SwfItemHdrAll getItemHdr() {
		return itemHdr;
	}

	public void setItemHdr(SwfItemHdrAll itemHdr) {
		this.itemHdr = itemHdr;
	}

}