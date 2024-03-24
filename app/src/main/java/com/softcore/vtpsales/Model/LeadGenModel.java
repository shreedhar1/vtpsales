package com.softcore.vtpsales.Model;

import java.util.List;

public class LeadGenModel {
    private String CardCode;
    private String CardName;
    private String CardType;
    private int GroupCode;
    private String Address;
    private String ZipCode;
    private String MailAddress;
    private String MailZipCode;
    private String ContactPerson;
    private int SalesPersonCode;
    private String Currency;
    private String Block;
    private String BillToState;
    private String ShipToState;
    private String ShipToDefault;
    private String U_CollectionPerson;
    private String Valid;
    private String Frozen;
    private String U_TransType;
    private List<BPAddress> BPAddresses;
    private List<ContactEmployee> ContactEmployees;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public int getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(int groupCode) {
        GroupCode = groupCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    public String getMailZipCode() {
        return MailZipCode;
    }

    public void setMailZipCode(String mailZipCode) {
        MailZipCode = mailZipCode;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public int getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(int salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getBillToState() {
        return BillToState;
    }

    public void setBillToState(String billToState) {
        BillToState = billToState;
    }

    public String getShipToState() {
        return ShipToState;
    }

    public void setShipToState(String shipToState) {
        ShipToState = shipToState;
    }

    public String getShipToDefault() {
        return ShipToDefault;
    }

    public void setShipToDefault(String shipToDefault) {
        ShipToDefault = shipToDefault;
    }

    public String getU_CollectionPerson() {
        return U_CollectionPerson;
    }

    public void setU_CollectionPerson(String u_CollectionPerson) {
        U_CollectionPerson = u_CollectionPerson;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String valid) {
        Valid = valid;
    }

    public String getFrozen() {
        return Frozen;
    }

    public void setFrozen(String frozen) {
        Frozen = frozen;
    }

    public String getU_TransType() {
        return U_TransType;
    }

    public void setU_TransType(String u_TransType) {
        U_TransType = u_TransType;
    }

    public List<BPAddress> getBPAddresses() {
        return BPAddresses;
    }

    public void setBPAddresses(List<BPAddress> BPAddresses) {
        this.BPAddresses = BPAddresses;
    }

    public List<ContactEmployee> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(List<ContactEmployee> contactEmployees) {
        ContactEmployees = contactEmployees;
    }

    public static class BPAddress {
        private String AddressName;
        private String Street;
        private String Block;
        private String ZipCode;
        private String City;
        private String Country;
        private String State;
        private String AddressType;


        public String getAddressName() {
            return AddressName;
        }

        public void setAddressName(String addressName) {
            AddressName = addressName;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        public String getBlock() {
            return Block;
        }

        public void setBlock(String block) {
            Block = block;
        }

        public String getZipCode() {
            return ZipCode;
        }

        public void setZipCode(String zipCode) {
            ZipCode = zipCode;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getAddressType() {
            return AddressType;
        }

        public void setAddressType(String addressType) {
            AddressType = addressType;
        }

    }
    public static class ContactEmployee {
        private String CardCode;
        private String Name;
        private String MobilePhone;
        private String DateOfBirth;
        private String U_AnnDate;

        // Getters and setters

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String cardCode) {
            CardCode = cardCode;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getMobilePhone() {
            return MobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            MobilePhone = mobilePhone;
        }

        public String getDateOfBirth() {
            return DateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            DateOfBirth = dateOfBirth;
        }

        public String getU_AnnDate() {
            return U_AnnDate;
        }

        public void setU_AnnDate(String u_AnnDate) {
            U_AnnDate = u_AnnDate;
        }
        // Add additional methods as needed
    }
}
