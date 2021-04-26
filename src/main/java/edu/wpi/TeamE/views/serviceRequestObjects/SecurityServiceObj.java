package edu.wpi.TeamE.views.serviceRequestObjects;

public class SecurityServiceObj extends ServiceRequestObjs {

    private String securityLevel;
    private String urgencyLevel;
    private String reason;

    public SecurityServiceObj(String nodeID, int userID, int assigneeID, String securityLevel, String urgencyLevel, String reason) {

        super.nodeID = nodeID;
        super.assigneeID = assigneeID;
        super.userID = userID;
        this.securityLevel = securityLevel;
        this.urgencyLevel = urgencyLevel;
        this.reason = reason;

    }

}
