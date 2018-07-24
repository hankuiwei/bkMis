package com.zc13.bkmis.mapping;



/**
 * CAccounttemplate entity. @author MyEclipse Persistence Tools
 */

public class CAccounttemplate  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String accountBank;
     private String account;
     private String currency;
     private String produceDate;
     private String closeType;
     private String closeMonth;
     private String closeDay;
     private String djqjlx;
     private String precision;
     private String rounding;
     private String formula;
     private String explanation;
     private String bankName;
     private Integer lpid;
     private Integer rootUser;

    // Constructors

    /** default constructor */
    public CAccounttemplate() {
    }

	public CAccounttemplate(Integer id, String accountBank, String account, String currency, String produceDate, String closeType, String closeMonth, String closeDay, String djqjlx, String precision, String rounding, String formula, String explanation, String bankName, Integer lpid, Integer rootUser) {
		super();
		this.id = id;
		this.accountBank = accountBank;
		this.account = account;
		this.currency = currency;
		this.produceDate = produceDate;
		this.closeType = closeType;
		this.closeMonth = closeMonth;
		this.closeDay = closeDay;
		this.djqjlx = djqjlx;
		this.precision = precision;
		this.rounding = rounding;
		this.formula = formula;
		this.explanation = explanation;
		this.bankName = bankName;
		this.lpid = lpid;
		this.rootUser = rootUser;
	}

	public Integer getRootUser() {
		return rootUser;
	}




	public void setRootUser(Integer rootUser) {
		this.rootUser = rootUser;
	}




	public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLpid() {
        return this.lpid;
    }
    
    public void setLpid(Integer lpid) {
        this.lpid = lpid;
    }

    public String getAccountBank() {
        return this.accountBank;
    }
    
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccount() {
        return this.account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProduceDate() {
        return this.produceDate;
    }
    
    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getCloseType() {
        return this.closeType;
    }
    
    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public String getCloseMonth() {
        return this.closeMonth;
    }
    
    public void setCloseMonth(String closeMonth) {
        this.closeMonth = closeMonth;
    }

    public String getCloseDay() {
        return this.closeDay;
    }
    
    public void setCloseDay(String closeDay) {
        this.closeDay = closeDay;
    }

    public String getDjqjlx() {
        return this.djqjlx;
    }
    
    public void setDjqjlx(String djqjlx) {
        this.djqjlx = djqjlx;
    }

    public String getPrecision() {
        return this.precision;
    }
    
    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getRounding() {
        return this.rounding;
    }
    
    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public String getFormula() {
        return this.formula;
    }
    
    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getExplanation() {
        return this.explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
   








}