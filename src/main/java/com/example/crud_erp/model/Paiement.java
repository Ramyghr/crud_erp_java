package com.example.crud_erp.model;



public class Paiement {
    int id, total;
    double grand, due;
    String code, date, name, method,mail;

    public Paiement(int id, int total, double grand, double due, String code, String date, String name, String method,String mail) {
        this.id = id;
        this.total = total;
        this.grand = grand;
        this.due = due;
        this.code = code;
        this.date = date;
        this.name = name;
        this.method = method;
        this.mail = mail;
    }

    public Paiement(int total, double grand, double due, String code, String date, String name, String method,String mail) {
        this.total = total;
        this.grand = grand;
        this.due = due;
        this.code = code;
        this.date = date;
        this.name = name;
        this.method = method;
        this.mail = mail;
    }

    public Paiement(int id, String code, String date, String method, String name, int total,String mail) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.method = method;
        this.name = name;
        this.total = total;
        this.mail=mail;
    }

    public Paiement(String code, String date, String method, String name, int total,String mail) {
        this.code = code;
        this.date = date;
        this.method = method;
        this.name = name;
        this.total = total;
        this.mail=mail;
    }

    public Paiement() {
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getGrand() {
        return grand;
    }

    public void setGrand(double grand) {
        this.grand = grand;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public void calculateGrand() {
        // Assuming grand is calculated as 10% of the total
        this.grand = (int) (this.total * 0.1); // You can adjust the percentage as needed
    }

    // Method to calculate the due amount based on the total and grand
    public void calculateDue() {
        this.due = this.total - this.grand;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", total=" + total +
                ", grand=" + grand +
                ", due=" + due +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}

