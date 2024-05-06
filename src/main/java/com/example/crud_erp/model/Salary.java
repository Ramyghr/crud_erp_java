package com.example.crud_erp.model;


public class Salary {
    int id, ref, paiment;
    double tax, deduction, net;
    String name, department;
    String paimentdate;



    public Salary(int id, int ref, int paiment, String name, String department, String paimentdate) {
        this.id = id;
        this.ref = ref;
        this.paiment = paiment;
        this.name = name;
        this.department = department;
        this.paimentdate = paimentdate;

    }

    public Salary(int id, int ref, int paiment, double tax, double deduction, double net, String name, String department, String paimentdate) {
        this.id = id;
        this.ref = ref;
        this.paiment = paiment;
        this.tax = tax;
        this.deduction = deduction;
        this.net = net;
        this.name = name;
        this.department = department;
        this.paimentdate = paimentdate;

    }

    public Salary(int ref, int paiment, double tax, double deduction, double net, String name, String department, String paimentdate) {
        this.ref = ref;
        this.paiment = paiment;
        this.tax = tax;
        this.deduction = deduction;
        this.net = net;
        this.name = name;
        this.department = department;
        this.paimentdate = paimentdate;

    }

    public Salary(int ref, int paiment, String name, String department, String paimentdate) {
        this.ref = ref;
        this.paiment = paiment;
        this.name = name;
        this.department = department;
        this.paimentdate = paimentdate;

    }

    public Salary() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getPaiment() {
        return paiment;
    }

    public void setPaiment(int paiment) {
        this.paiment = paiment;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPaimentdate() {
        return paimentdate;
    }

    public void setPaimentdate(String paimentdate) {
        this.paimentdate = paimentdate;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", ref=" + ref +
                ", paiment=" + paiment +
                ", tax=" + tax +
                ", deduction=" + deduction +
                ", net=" + net +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", paimentdate=" + paimentdate +
                '}';
    }
    // Method to calculate tax
    public double calculateTax(int paiment) {
        // Tax rate: 0.1
        double taxRate = 0.1;
        return  (taxRate * paiment);
    }

    // Method to calculate deduction
    public double calculateDeduction(int paiment) {
        // Deduction rate: 0.05
        double deductionRate = 0.05;
        return  (deductionRate * paiment);
    }

    // Method to calculate net
    public double calculateNet(int paiment) {
        // Calculate tax and deduction
        double tax = calculateTax(paiment);
        double deduction = calculateDeduction(paiment);

        // Calculate net
        return paiment - (tax + deduction);
    }


}
