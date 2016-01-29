package com.blackducksoftware.tools.appuseradjuster.add;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.blackducksoftware.tools.commonframework.core.exception.CommonFrameworkException;
import com.blackducksoftware.tools.connector.codecenter.application.ApplicationPojo;
import com.blackducksoftware.tools.connector.codecenter.application.ApplicationUserPojo;
import com.blackducksoftware.tools.connector.codecenter.application.IApplicationManager;
import com.blackducksoftware.tools.connector.codecenter.common.AttachmentDetails;
import com.blackducksoftware.tools.connector.codecenter.common.CodeCenterComponentPojo;
import com.blackducksoftware.tools.connector.codecenter.common.RequestPojo;
import com.blackducksoftware.tools.connector.codecenter.user.UserStatus;
import com.blackducksoftware.tools.connector.common.ApprovalStatus;

public class MockApplicationManager implements IApplicationManager {
    private SortedSet<String> addOperations;

    public MockApplicationManager() {
        addOperations = new TreeSet<>();
    }

    public synchronized SortedSet<String> getAddOperations() {
        return addOperations;
    }

    @Override
    public List<ApplicationPojo> getApplications(int firstRow, int lastRow, String searchString) throws CommonFrameworkException {
        List<ApplicationPojo> apps = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            String appName = searchString + "App" + i + "-PROD-CURRENT";
            ApplicationPojo app = new ApplicationPojo(appName, appName, "v100",
                    null,
                    ApprovalStatus.APPROVED, false);
            apps.add(app);
        }
        return apps;
    }

    @Override
    public List<ApplicationPojo> getApplications(int firstRow, int lastRow) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public ApplicationPojo getApplicationByNameVersion(String name, String version) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public ApplicationPojo getApplicationById(String id) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public List<RequestPojo> getRequestsByAppId(String appId) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public <T extends CodeCenterComponentPojo> List<T> getComponentsByAppId(Class<T> pojoClass, String appId, List<ApprovalStatus> limitToApprovalStatusValues,
            boolean recursive) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public List<ApplicationUserPojo> getAllUsersAssignedToApplication(String appId) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public void addUsersByIdToApplicationTeam(String appId, Set<String> userIds, Set<String> roleNames, boolean circumventLock) throws CommonFrameworkException {
        // TODO Auto-generated function stub

    }

    @Override
    public void addUsersByNameToApplicationTeam(String appId, Set<String> userNames, Set<String> roleNames, boolean circumventLock)
            throws CommonFrameworkException {
        System.out.println("addUsersByNameToApplicationTeam() called: " + appId + ": " + userNames);
        recordOperation(appId, userNames);
    }

    private synchronized void recordOperation(String appId, Set<String> userNames) {
        String operation = appId + ": " + asSortedList(userNames);
        addOperations.add(operation);
    }

    private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    @Override
    public void removeUserByIdFromApplicationTeam(String appId, String userId, String roleId, boolean circumventLock) throws CommonFrameworkException {
        // TODO Auto-generated function stub

    }

    @Override
    public List<UserStatus> removeUsersByNameFromApplicationAllRoles(String appId, Set<String> usernames, boolean circumventLock)
            throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public List<AttachmentDetails> searchAttachments(String applicationId, String searchString) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public File downloadAttachment(String applicationId, String filename, String targetDirPath) throws CommonFrameworkException {
        // TODO Auto-generated function stub
        return null;
    }

    @Override
    public void attachFile(String applicationId, String sourceFilePath, String description) throws CommonFrameworkException {
        // TODO Auto-generated function stub

    }

    @Override
    public void deleteAttachment(String applicationId, String filename) throws CommonFrameworkException {
        // TODO Auto-generated function stub

    }

}