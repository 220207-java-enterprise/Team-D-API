package com.revature.technology.dtos.prism.requests;

public class AddEmployeeToOrgRequest {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private AccountInfo accountInfo;

    public AddEmployeeToOrgRequest(){super();}

    public AddEmployeeToOrgRequest(String firstName, String lastName, String emailAddress, AccountInfo accountInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.accountInfo = accountInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    @Override
    public String toString() {
        return "AddEmployeeToOrgRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", accountInfo=" + accountInfo +
                '}';
    }

    public static class AccountInfo {
        private String institutionName;
        private String accountNumber;
        private String routingNumber;

        public AccountInfo(){super();}

        public AccountInfo(String institutionName, String accountNumber, String routingNumber) {
            this.institutionName = institutionName;
            this.accountNumber = accountNumber;
            this.routingNumber = routingNumber;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getRoutingNumber() {
            return routingNumber;
        }

        public void setRoutingNumber(String routingNumber) {
            this.routingNumber = routingNumber;
        }

        @Override
        public String toString() {
            return "AccountInfo{" +
                    "institutionName='" + institutionName + '\'' +
                    ", accountNumber='" + accountNumber + '\'' +
                    ", routing='" + routingNumber + '\'' +
                    '}';
        }
    }


}
