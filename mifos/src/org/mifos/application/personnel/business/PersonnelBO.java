package org.mifos.application.personnel.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mifos.application.customer.business.CustomFieldView;
import org.mifos.application.customer.util.helpers.CustomerConstants;
import org.mifos.application.master.business.SupportedLocalesEntity;
import org.mifos.application.master.persistence.MasterPersistence;
import org.mifos.application.office.business.OfficeBO;
import org.mifos.application.office.util.helpers.OfficeLevel;
import org.mifos.application.personnel.exceptions.PersonnelException;
import org.mifos.application.personnel.persistence.PersonnelPersistence;
import org.mifos.application.personnel.util.helpers.LockStatus;
import org.mifos.application.personnel.util.helpers.PersonnelConstants;
import org.mifos.application.personnel.util.helpers.PersonnelLevel;
import org.mifos.application.personnel.util.helpers.PersonnelStatus;
import org.mifos.application.rolesandpermission.util.valueobjects.Role;
import org.mifos.application.util.helpers.YesNoFlag;
import org.mifos.framework.business.BusinessObject;
import org.mifos.framework.business.util.Address;
import org.mifos.framework.business.util.Name;
import org.mifos.framework.components.logger.LoggerConstants;
import org.mifos.framework.components.logger.MifosLogManager;
import org.mifos.framework.components.logger.MifosLogger;
import org.mifos.framework.exceptions.EncryptionException;
import org.mifos.framework.exceptions.PersistenceException;
import org.mifos.framework.exceptions.SystemException;
import org.mifos.framework.security.authentication.EncryptionService;
import org.mifos.framework.struts.tags.DateHelper;
import org.mifos.framework.util.helpers.Constants;
import org.mifos.framework.util.helpers.StringUtils;

public class PersonnelBO extends BusinessObject {

	private final Short personnelId;

	private PersonnelLevelEntity level;

	private String globalPersonnelNum;

	private OfficeBO office;

	private Integer title;

	private String displayName;

	private String searchId;

	private Integer maxChildCount;

	private byte[] encriptedPassword;

	private final String userName;

	private String emailId;

	private Short passwordChanged;

	private Date lastLogin;

	private Short locked;

	private Short noOfTries;

	private PersonnelStatusEntity status;

	private SupportedLocalesEntity preferredLocale;

	private PersonnelDetailsEntity personnelDetails;

	private Set<PersonnelRoleEntity> personnelRoles;

	private Set<PersonnelCustomFieldEntity> customFields;

	private Set<PersonnelMovementEntity> personnelMovements;

	private Set<PersonnelNotesEntity> personnelNotes;

	private MifosLogger logger = MifosLogManager.getLogger(LoggerConstants.PERSONNEL_LOGGER);

	protected PersonnelBO() {
		this.level = null;
		this.personnelDetails = new PersonnelDetailsEntity();
		this.preferredLocale = new SupportedLocalesEntity();
		this.customFields = new HashSet<PersonnelCustomFieldEntity>();
		this.personnelNotes = new HashSet<PersonnelNotesEntity>();
		this.personnelId = null;
		this.userName = null;
	}

	public PersonnelBO(PersonnelLevel level, OfficeBO office, Integer title,
			Short preferredLocale, String password, String userName,
			String emailId, List<Role> roles,
			List<CustomFieldView> customFields, Name name,
			String governmentIdNumber, Date dob, Integer maritalStatus,
			Integer gender, Date dateOfJoiningMFI, Date dateOfJoiningBranch,
			Address address, Short createdBy) throws PersonnelException {
		super();
		setCreateDetails(createdBy, new Date());
		logger = MifosLogManager.getLogger(LoggerConstants.PERSONNEL_LOGGER);
		this.displayName = name.getDisplayName();
		verifyFields(userName, governmentIdNumber, dob);
		this.level = new PersonnelLevelEntity(level);
		this.office = office;
		this.title = title;
		this.preferredLocale = new SupportedLocalesEntity(preferredLocale);
		this.userName = userName;
		this.emailId = emailId;
		this.personnelDetails = new PersonnelDetailsEntity(name,
				governmentIdNumber, dob, maritalStatus, gender,
				dateOfJoiningMFI, dateOfJoiningBranch, this, address);
		this.personnelRoles = new HashSet();
		if (roles != null) {
			for (Role role : roles) {
				this.personnelRoles.add(new PersonnelRoleEntity(role, this));
			}
		}
		this.customFields = new HashSet<PersonnelCustomFieldEntity>();
		this.personnelMovements = new HashSet<PersonnelMovementEntity>();
		this.personnelNotes = new HashSet<PersonnelNotesEntity>();
		this.personnelId = null;
		this.globalPersonnelNum = "XX";
		if (customFields != null)
			for (CustomFieldView view : customFields) {
				this.customFields.add(new PersonnelCustomFieldEntity(view
						.getFieldValue(), view.getFieldId(), this));
			}

		this.status = new PersonnelStatusEntity(PersonnelStatus.ACTIVE);
		this.passwordChanged = Constants.NO;
		this.locked = LockStatus.UNLOCK.getValue();
		this.noOfTries = Short.valueOf("0");
		this.encriptedPassword = getEncryptedPassword(password);
		this.status = new PersonnelStatusEntity(PersonnelStatus.ACTIVE);
	}

	public String getAge() {
		if (this.personnelDetails != null
				&& this.personnelDetails.getDob() != null
				&& !this.personnelDetails.getDob().equals("")) {
			return String.valueOf(DateHelper.DateDiffInYears(new java.sql.Date(
					this.personnelDetails.getDob().getTime())));
		} else
			return "";
	}

	public Set<PersonnelCustomFieldEntity> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Set<PersonnelCustomFieldEntity> customFields) {
		this.customFields = customFields;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getGlobalPersonnelNum() {
		return globalPersonnelNum;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public PersonnelLevelEntity getLevel() {
		return level;
	}

	public Integer getMaxChildCount() {
		return maxChildCount;
	}

	public OfficeBO getOffice() {
		return office;
	}

	public boolean isPasswordChanged() {
		return this.passwordChanged > 0;
	}

	public PersonnelDetailsEntity getPersonnelDetails() {
		return personnelDetails;
	}

	public void setPersonnelDetails(PersonnelDetailsEntity personnelDetails) {
		this.personnelDetails = personnelDetails;
	}

	public Short getPersonnelId() {
		return personnelId;
	}

	public SupportedLocalesEntity getPreferredLocale() {
		return preferredLocale;
	}

	public Integer getTitle() {
		return title;
	}

	public String getUserName() {
		return userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isLocked() {
		return this.locked > 0;
	}

	private void lock() {
		this.locked = LockStatus.LOCK.getValue();
	}

	private void unLock() {
		this.locked = LockStatus.UNLOCK.getValue();
	}

	public Set<PersonnelRoleEntity> getPersonnelRoles() {
		return personnelRoles;
	}

	public void setPersonnelRoles(Set<PersonnelRoleEntity> personnelRoles) {
		this.personnelRoles = personnelRoles;
	}

	public Set<PersonnelMovementEntity> getPersonnelMovements() {
		return personnelMovements;
	}

	public Set<PersonnelNotesEntity> getPersonnelNotes() {
		return personnelNotes;
	}

	public Short getNoOfTries() {
		return noOfTries;
	}

	public void setNoOfTries(Short noOfTries) {
		this.noOfTries = noOfTries;
	}

	public Short getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(Short passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public byte[] getEncriptedPassword() {
		return encriptedPassword;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setGlobalPersonnelNum(String globalPersonnelNum) {
		this.globalPersonnelNum = globalPersonnelNum;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setLevel(PersonnelLevelEntity level) {
		this.level = level;
	}

	public void setMaxChildCount(Integer maxChildCount) {
		this.maxChildCount = maxChildCount;
	}

	public void setOffice(OfficeBO office) {
		this.office = office;
	}
	public void setPersonnelMovements(
			Set<PersonnelMovementEntity> personnelMovements) {
		this.personnelMovements = personnelMovements;
	}

	public void setPersonnelNotes(Set<PersonnelNotesEntity> personnelNotes) {
		this.personnelNotes = personnelNotes;
	}

	public void setPreferredLocale(SupportedLocalesEntity preferredLocale) {
		this.preferredLocale = preferredLocale;
	}

	public void setStatus(PersonnelStatusEntity status) {
		this.status = status;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	private void updateCustomFields(List<CustomFieldView> customfields) {
		if (this.customFields != null && customfields != null) {
			for (CustomFieldView fieldView : customfields)
				for (PersonnelCustomFieldEntity fieldEntity : this.customFields)
					if (fieldView.getFieldId().equals(fieldEntity.getFieldId()))
						fieldEntity.setFieldValue(fieldView.getFieldValue());
		}
	}

	public void setEncriptedPassword(byte[] encriptedPassword) {
		this.encriptedPassword = encriptedPassword;
	}

	public PersonnelStatusEntity getStatus() {
		return status;
	}

	public void addPersonnelMovement(PersonnelMovementEntity personnelMovement) {
		if (personnelMovement != null) {
			this.personnelMovements.add(personnelMovement);
		}
	}

	private void addPersonnelNotes(PersonnelNotesEntity personnelNotes) {
		this.personnelNotes.add(personnelNotes);
	}

	public void save() throws PersonnelException {
		try {
			PersonnelPersistence persistence = new PersonnelPersistence();
			persistence.createOrUpdate(this);
			this.globalPersonnelNum = generateGlobalPersonnelNum(this.office
					.getGlobalOfficeNum(), this.personnelId);
			persistence.createOrUpdate(this);
		} catch (PersistenceException e) {
			throw new PersonnelException(e);

		}
	}

	public void addNotes(Short userId, PersonnelNotesEntity personnelNotes)
			throws PersonnelException {
		setUpdateDetails(userId);
		addPersonnelNotes(personnelNotes);
		try {
			new PersonnelPersistence().createOrUpdate(this);
		} catch (PersistenceException e) {
			throw new PersonnelException(e);
		}
	}

	private String generateGlobalPersonnelNum(String officeGlobalNum,
			int maxPersonnelId) {
		logger.debug("Passed office global no is : ".concat(officeGlobalNum)
				.concat(" and maxpersonnelid is : " + maxPersonnelId));
		String userId = "";
		int numberOfZeros = 5 - String.valueOf(maxPersonnelId).length();
		for (int i = 0; i < numberOfZeros; i++) {
			userId = userId + "0";
		}
		userId = userId + maxPersonnelId;
		String userGlobalNum = officeGlobalNum + "-" + userId;
		logger.debug("Generated userGlobalNum is : ".concat(userGlobalNum));
		return userGlobalNum;
	}

	private byte[] getEncryptedPassword(String password)
			throws PersonnelException {
		byte[] encryptedPassword = null;
		try {

			encryptedPassword = EncryptionService.getInstance()
					.createEncryptedPassword(password);
		} catch (EncryptionException e) {
			throw new PersonnelException(e);
		} catch (SystemException e) {
			throw new PersonnelException(e);
		}

		return encryptedPassword;
	}

	private void verifyFields(String userName, String governmentIdNumber,
			Date dob) throws PersonnelException {

		try {
			PersonnelPersistence persistence = new PersonnelPersistence();
			if (StringUtils.isNullOrEmpty(userName))
				throw new PersonnelException(PersonnelConstants.ERRORMANDATORY);
			if (persistence.isUserExist(userName)) {
				System.out.println("Duplicate user found");
				throw new PersonnelException(PersonnelConstants.DUPLICATE_USER,
						new Object[] { userName });

			}
			if (!StringUtils.isNullOrEmpty(governmentIdNumber)) {
				if (persistence.isUserExistWithGovernmentId(governmentIdNumber))
					throw new PersonnelException(
							PersonnelConstants.DUPLICATE_GOVT_ID,
							new Object[] { governmentIdNumber });
			} else {
				if (persistence.isUserExist(displayName, dob))
					throw new PersonnelException(
							PersonnelConstants.DUPLICATE_USER_NAME_OR_DOB,
							new Object[] { displayName });
			}
		} catch (PersistenceException e) {
			throw new PersonnelException(e);
		}
	}

	public void update(PersonnelStatus newStatus, PersonnelLevel newLevel,
			OfficeBO office, Integer title, Short preferredLocale,
			String password, String emailId, List<Role> roles,
			List<CustomFieldView> customFields, Name name,
			Integer maritalStatus, Integer gender, Address address,
			Short updatedById) throws PersonnelException {
		logger.debug("update in personnelBO called with values: " +newLevel+office.getOfficeId()+title+" "+preferredLocale  
				+" "+updatedById);
		MasterPersistence masterPersistence = new MasterPersistence();
		validateForUpdate(newStatus, office, newLevel);
		logger.debug("validation successful for status, office and level: "+newStatus+" "+office+" "+newLevel);
		Date dateOfJoiningBranch = null;
		if (!this.office.getOfficeId().equals(office.getOfficeId())) {
			makePersonalMovementEntries(office, updatedById);
			dateOfJoiningBranch = new Date();

		}
		PersonnelStatusEntity personnelStatus = (PersonnelStatusEntity) masterPersistence
				.findById(PersonnelStatusEntity.class, newStatus.getValue());
		this.status = personnelStatus;
		PersonnelLevelEntity personnelLevel = (PersonnelLevelEntity) masterPersistence
				.findById(PersonnelLevelEntity.class, newLevel.getValue());
		this.level = personnelLevel;
		this.displayName = name.getDisplayName();
		this.preferredLocale=new SupportedLocalesEntity(preferredLocale);
		
		if (StringUtils.isNullAndEmptySafe(password)) {
			this.encriptedPassword = getEncryptedPassword(password);
			this.unLock();
			this.noOfTries = YesNoFlag.NO.getValue();
			this.passwordChanged = YesNoFlag.YES.getValue();
		}
		this.emailId = emailId;
		if (title != null && title.intValue() == 0) {
			this.title = null;
		} else
			this.title = title;
		updatePersonnelRoles(roles);
		updatePersonnelDetails(name, maritalStatus, gender, address,dateOfJoiningBranch);
		updateCustomFields(customFields);
		try {
			setUpdateDetails(updatedById);
			new PersonnelPersistence().createOrUpdate(this);
			logger.debug("update successful");
		} catch (PersistenceException e) {
			throw new PersonnelException(
					CustomerConstants.UPDATE_FAILED_EXCEPTION, e);
		}
	}

	private void updatePersonnelRoles(List<Role> roles) {
		this.personnelRoles.clear();
		if (roles != null) {
			for (Role role : roles) {
				this.personnelRoles.add(new PersonnelRoleEntity(role, this));
			}
		}
		
	}

	public void update(String emailId, Name name, Integer maritalStatus,
			Integer gender, Address address, Short preferredLocale)
			throws PersonnelException {

		this.emailId = emailId;
		/*
		 * if(preferredLocale != null && preferredLocale != 0){
		 * this.preferredLocale = new SupportedLocalesEntity(preferredLocale); }
		 */
		updatePersonnelDetails(name, maritalStatus, gender, address, null);
		try {
			new PersonnelPersistence().createOrUpdate(this);
		} catch (PersistenceException pe) {
			throw new PersonnelException(PersonnelConstants.UPDATE_FAILED, pe);
		}
	}

	public void updatePersonnelDetails(Name name, Integer maritalStatus,
			Integer gender, Address address, Date dateOfJoiningBranch)
			throws PersonnelException {
		if (getPersonnelDetails() != null) {
			getPersonnelDetails().updateDetails(name, maritalStatus, gender,
					address, dateOfJoiningBranch);
		}
	}

	public List<PersonnelNotesEntity> getRecentPersonnelNotes() {
		List<PersonnelNotesEntity> notes = new ArrayList<PersonnelNotesEntity>();
		int count = 0;
		for (PersonnelNotesEntity personnelNotes : getPersonnelNotes()) {
			if (count > 2)
				break;
			notes.add(personnelNotes);
			count++;
		}
		return notes;
	}

	private void validateForUpdate(PersonnelStatus newStatus,
			OfficeBO newOffice, PersonnelLevel newLevel)
			throws PersonnelException {

		if (!level.getId().equals(newLevel.getValue()))
			validateUserHierarchyChange(newLevel, newOffice);
		if (!office.getOfficeId().equals(newOffice.getOfficeId())) {
			validateOfficeTransfer(newOffice, newLevel);
		}
		if (!status.getId().equals(newStatus.getValue())) {
			validateStatusChange(newStatus, newLevel, newOffice);
		}

	}

	private void validateStatusChange(PersonnelStatus newStatus,
			PersonnelLevel newLevel, OfficeBO newOffice)
			throws PersonnelException {

		try {
			if (status.getId().equals(PersonnelStatus.ACTIVE.getValue())
					&& newStatus.equals(PersonnelStatus.INACTIVE)
					&& newLevel.equals(PersonnelLevel.LOAN_OFFICER)) {
				if (new PersonnelPersistence().getActiveChildrenForLoanOfficer(
						personnelId, office.getOfficeId())) {
					Object values[] = new Object[1];
					values[0] = globalPersonnelNum;
					throw new PersonnelException(
							PersonnelConstants.STATUS_CHANGE_EXCEPTION, values);
				}
			} else if (status.getId().equals(
					PersonnelStatus.INACTIVE.getValue())
					&& newStatus.equals(PersonnelStatus.ACTIVE)
					&& !(newOffice.isActive())) {
				Object values[] = new Object[1];
				values[0] = newOffice.getOfficeId();
				throw new PersonnelException(
						PersonnelConstants.INACTIVE_BRANCH, values);
			}
		} catch (PersistenceException e) {
			throw new PersonnelException(e);

		}
	}

	private void validateOfficeTransfer(OfficeBO newOffice,
			PersonnelLevel newLevel) throws PersonnelException {
		try {

			if (newLevel.equals(PersonnelLevel.LOAN_OFFICER)) {
				if (!newOffice.getLevel().getId().equals(
						OfficeLevel.BRANCHOFFICE.getValue())) {
					Object values[] = new Object[1];
					values[0] = globalPersonnelNum;
					throw new PersonnelException(
							PersonnelConstants.LO_ONLY_IN_BRANCHES, values);
				}
			}
			if (new PersonnelPersistence().getActiveChildrenForLoanOfficer(
					personnelId, office.getOfficeId())) {
				Object values[] = new Object[1];
				values[0] = globalPersonnelNum;
				throw new PersonnelException(
						PersonnelConstants.TRANSFER_NOT_POSSIBLE_EXCEPTION,
						values);
			}
		} catch (PersistenceException e) {
			throw new PersonnelException(e);
		}

	}

	private void validateUserHierarchyChange(PersonnelLevel newLevel,
			OfficeBO officeId) throws PersonnelException {
		try {
			if (level.getId().equals(PersonnelLevel.LOAN_OFFICER.getValue())
					&& newLevel.equals(PersonnelLevel.NON_LOAN_OFFICER)) {
				if (new PersonnelPersistence().getAllChildrenForLoanOfficer(
						personnelId, getOffice().getOfficeId())) {
					Object values[] = new Object[1];
					values[0] = globalPersonnelNum;
					throw new PersonnelException(
							PersonnelConstants.HIERARCHY_CHANGE_EXCEPTION,
							values);
				}
			} else if (level.getId().equals(
					PersonnelLevel.NON_LOAN_OFFICER.getValue())
					&& newLevel.equals(PersonnelLevel.LOAN_OFFICER)
					&& (!officeId.getLevel().getId().equals(
							OfficeLevel.BRANCHOFFICE.getValue()))) {
				Object values[] = new Object[1];
				values[0] = globalPersonnelNum;
				throw new PersonnelException(
						PersonnelConstants.LO_ONLY_IN_BRANCHES, values);

			}
		} catch (PersistenceException e) {
			throw new PersonnelException(e);
		}

	}

	public PersonnelMovementEntity getActiveCustomerMovement() {
		PersonnelMovementEntity movement = null;
		for (PersonnelMovementEntity personnelMovementEntity : personnelMovements) {
			if (personnelMovementEntity.isActive()) {
				movement = personnelMovementEntity;
				break;
			}
		}
		return movement;
	}

	private void makePersonalMovementEntries(OfficeBO newOffice,
			Short updatedById) {
		PersonnelMovementEntity currentPersonnelMovement = getActiveCustomerMovement();
		if (currentPersonnelMovement == null) {
			currentPersonnelMovement = new PersonnelMovementEntity(this,
					getCreatedDate());
			this.addPersonnelMovement(currentPersonnelMovement);
		}

		currentPersonnelMovement.makeInActive(updatedById);
		this.office = newOffice;
		PersonnelMovementEntity newPersonnelMovement = new PersonnelMovementEntity(
				this, new Date());
		this.addPersonnelMovement(newPersonnelMovement);
	}
}
